package com.guigame.application.view;

import java.util.ArrayList;
import java.util.Arrays;

import com.guigame.application.model.MainGameModel;
import com.guigame.board.GridCell;
import com.guigame.player.Player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.io.InputStream;

public class MainGameView {
	private MainGameModel model;
	private GridPane gameBoard;
	private Label clock;
	private Label compass;
	
	// Constructor
    public MainGameView(MainGameModel model) {
        this.model = model;
        this.gameBoard = new GridPane();
        
        InputStream is = getClass().getResourceAsStream("/resources/digital-7 (mono).ttf");
		Font font = Font.loadFont(is, 50);
		BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(0, 0, 0, 0.65), CornerRadii.EMPTY, Insets.EMPTY);
		Background background = new Background(backgroundFill);
        this.clock = new Label();
        clock.setFont(font);
        clock.setTextFill(Color.LIMEGREEN);
        clock.setBackground(background);
        clock.setPadding(new Insets(10));
        
        BackgroundFill backgroundFill1 = new BackgroundFill(Color.rgb(211, 211, 211, 0.75), CornerRadii.EMPTY, Insets.EMPTY);
		Background background1 = new Background(backgroundFill1);
        this.compass = new Label();
        compass.setFont(font);
        compass.setTextFill(Color.BLACK);
        compass.setBackground(background1);
        clock.setPadding(new Insets(10));
    }
    public GridPane getGameBoard() { return gameBoard; }
    public Label getClock() { return clock; } 
    public Label getCompass() { return compass; }
    
    // Methods to draw the GUI based on the model state...
    public void update() {
        // Clear the game board
        gameBoard.getChildren().clear();

        // Redraw the game board based on the model's state
        drawBoard();
    	drawClock();
    	drawCompass();
    }
    private void drawBoard() {
        ArrayList<Image> rabbitImages = createRabbitImageList();
        ArrayList<Image> mouseImages = createMouseImageList();
        for (int i = 0; i < model.getBoard().getBoardSize(); i++) {
            for (int j = 0; j < model.getBoard().getBoardSize(); j++) {
                GridCell cell = model.getBoard().getGridCellAt(i, j);
                Node cellNodeBoard = drawGridCell(cell,i,j, model.getBoard().getMagicDoorRow(), model.getBoard().getMagicDoorCol());   
                Pane pane = new Pane(cellNodeBoard);    
                
                int playerCount = 0; // Keep track of the number of players in each cell
                int playerIndex = 0;
                for (Player player : model.getListOfPlayers()) {
                	if (player.getX() == i && player.getY() == j) {
                		Node cellNodePlayer = drawPlayer(player, rabbitImages, mouseImages);
                		Tooltip tooltip = new Tooltip("PLAYER "+(playerIndex+1));
                		tooltip.setShowDelay(Duration.seconds(0.3));
                		Tooltip.install(cellNodePlayer, tooltip);
                		
                		switch (playerCount) {
                        case 0: // Top left corner
                            cellNodePlayer.relocate(0, 0);
                            break;
                        case 1: // Top right corner
                            cellNodePlayer.relocate(52, 0);
                            break;
                        case 2: // Bottom left corner
                            cellNodePlayer.relocate(0, 52);
                            break;
                        case 3: // Bottom right corner
                            cellNodePlayer.relocate(52, 52);
                            break;
                		}
                		pane.getChildren().add(cellNodePlayer);
                		playerCount++;
                	}
                	playerIndex++;
                }
                gameBoard.add(pane, j, i);
                gameBoard.setLayoutX(70);
                gameBoard.setLayoutY(70);
            }
        }
    }
    private ArrayList<Image> createMouseImageList() {
        Image mouseImage1 = new Image(getClass().getResource("/resources/mouse_1.png").toExternalForm());
        Image mouseImage2 = new Image(getClass().getResource("/resources/mouse_2.png").toExternalForm());
   
		return new ArrayList<Image>(Arrays.asList(mouseImage1, mouseImage2));
	}
	private ArrayList<Image> createRabbitImageList() {
        Image rabbitImage1 = new Image(getClass().getResource("/resources/rabbit_1.png").toExternalForm());
        Image rabbitImage2 = new Image(getClass().getResource("/resources/rabbit_2.png").toExternalForm());

    	return new ArrayList<Image>(Arrays.asList(rabbitImage1, rabbitImage2));
    }
    private StackPane drawWalls(Image wallImage, Rectangle background, Tooltip tooltip) {
    	StackPane stackPane = new StackPane();
    	ImageView imageView = new ImageView();
    	Image backgroundImage = new Image(getClass().getResource("/resources/wall_background2.jpeg").toExternalForm());
    	ImagePattern imagePattern = new ImagePattern(backgroundImage);

    	background.setFill(imagePattern);
    	imageView.setImage(wallImage);
    	Tooltip.install(imageView, tooltip);
    	stackPane.getChildren().addAll(background, imageView);
    	
    	return stackPane;
    }
    private StackPane drawTokens(Image tokenImage, Rectangle background) {
    	StackPane stackPane = new StackPane();
    	ImageView imageView = new ImageView();
        Image backgroundImage = new Image(getClass().getResource("/resources/wall_background2.jpeg").toExternalForm());
        ImagePattern imagePattern = new ImagePattern(backgroundImage);
        
        background.setFill(imagePattern);
        
    	imageView.setFitHeight(32);
    	imageView.setFitWidth(32);
    	imageView.setImage(tokenImage);
    	stackPane.getChildren().addAll(background, imageView);
    	StackPane.setAlignment(imageView, Pos.CENTER);
    	return stackPane;
    }
    private StackPane drawGridCell(GridCell cell, int i, int j, int magicDoorX, int magicDoorY) {
        // Create a new Node to represent the cell
        StackPane stackPane = new StackPane();
        Rectangle background = new Rectangle(100, 100);

        switch (cell.getCellType()) {
			case CARROT_TOKEN: {
				Image carrotImage = new Image(getClass().getResource("/resources/carrot_token.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(carrotImage, background);
				break;
			}
			case CHEESE_TOKEN: {
				Image cheeseImage = new Image(getClass().getResource("/resources/cheese_token.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(cheeseImage, background);
				break;
			}
			
	        case NONE_WALL: {
				Image noneWallImage = new Image(getClass().getResource("/resources/none_wall.png").toExternalForm()); // replace with your image path
				Tooltip tooltip = new Tooltip("This wall cannot be accessed.");
				tooltip.setShowDelay(Duration.seconds(0.3));
				stackPane = drawWalls(noneWallImage,background,tooltip);
				break;
			}
	        
			case CURTAIN_WALL: {
				Image curtainImage = null;
				if (i == 1 || i == 3)	{
					curtainImage = new Image(getClass().getResource("/resources/horizontal_curtain_wall.png").toExternalForm()); // replace with your image path
				}
				else {
					curtainImage = new Image(getClass().getResource("/resources/vertical_curtain_wall.png").toExternalForm()); // replace with your image path
				}
				Tooltip tooltip = new Tooltip("This is a curtain, requiring an action to reveal.");
				tooltip.setShowDelay(Duration.seconds(0.3));
				stackPane = drawWalls(curtainImage, background, tooltip);
				break;
			}
			case WINDOW_WALL: {
				Image windowWallImage;
	
				if (i == 1 || i == 3)	{
					windowWallImage = new Image(getClass().getResource("/resources/horizontal_window_wall.png").toExternalForm()); // replace with your image path
				}
				else {
					windowWallImage = new Image(getClass().getResource("/resources/vertical_open_window_wall.png").toExternalForm());
				}
				Tooltip tooltip = new Tooltip("This is a window, only rabbits can jump through.");
				tooltip.setShowDelay(Duration.seconds(0.3));
				stackPane = drawWalls(windowWallImage, background, tooltip);
				break;
			}
			case MOUSEHOLE_WALL: {
				Image mouseHoleImage;
				if (i == 1 || i == 3)	{
					mouseHoleImage = new Image(getClass().getResource("/resources/horizontal_mousehole_wall.png").toExternalForm()); // replace with your image path
				}
				else {
					mouseHoleImage = new Image(getClass().getResource("/resources/vertical_mousehole_wall.png").toExternalForm()); // replace with your image path
				}
				Tooltip tooltip = new Tooltip("This is a mousehole, only mice can get through.");
				tooltip.setShowDelay(Duration.seconds(0.3));
				stackPane = drawWalls(mouseHoleImage, background, tooltip);
				break;
			}
			case OPEN_WALL: {
				Image openWallImage = null;
				if (i == 1 || i == 3){
					if (i == magicDoorX && j == magicDoorY)	{
						openWallImage = new Image(getClass().getResource("/resources/horizontal_open_magic_door_wall.png").toExternalForm()); // replace with your image path
					}
					else {
						openWallImage = new Image(getClass().getResource("/resources/horizontal_open_wall.png").toExternalForm()); // replace with your image path
					}
				}
				else {
					if (i == magicDoorX && j == magicDoorY) {
						openWallImage = new Image(getClass().getResource("/resources/vertical_open_magic_door_wall.png").toExternalForm()); // replace with your image path
					} else {
						openWallImage = new Image(getClass().getResource("/resources/vertical_open_wall.png").toExternalForm()); // replace with your image path
					}
				}
				Tooltip tooltip = new Tooltip("This is an opened wall, both rabbits and mice can get through.");
				tooltip.setShowDelay(Duration.seconds(0.3));
				stackPane = drawWalls(openWallImage, background, tooltip);
				break;
			}
			case MAGIC_DOOR_WALL: {
				Image magicDoorImage;
				if (i == 1 || i == 3)	{
					magicDoorImage = new Image(getClass().getResource("/resources/horizontal_closed_magic_door_wall_curtain.png").toExternalForm()); // replace with your image path
				}
				else {
					magicDoorImage = new Image(getClass().getResource("/resources/vertical_closed_magic_door_wall_curtain.png").toExternalForm()); // replace with your image path
				}
				Tooltip tooltip = new Tooltip("This is a Magic Door, requiring one player on each side and an action to open.");
				tooltip.setShowDelay(Duration.seconds(0.3));
				stackPane = drawWalls(magicDoorImage, background, tooltip);
				break;
			}
	
			case GHOST: {
				Image ghostImage = new Image(getClass().getResource("/resources/ghost.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(ghostImage, background);
				break;
			}
	
			case DARK_CATERPILLAR: {
				Image darkCaterImage = new Image(getClass().getResource("/resources/black_caterpillar.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(darkCaterImage, background);
				break;
			}
	
			case DARK_FROG: {
				Image darkFrogImage = new Image(getClass().getResource("/resources/black_frog.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(darkFrogImage, background);
				break;
			}
			case DARK_BAT: {
				Image darkBatImage = new Image(getClass().getResource("/resources/black_bat.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(darkBatImage, background);
				break;
			}
			case DARK_OWL: {
				Image darkOwlImage = new Image(getClass().getResource("/resources/black_owl.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(darkOwlImage, background);
				break;
			}
	
			case WHITE_BAT: {
				Image whiteBatImage = new Image(getClass().getResource("/resources/white_bat.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(whiteBatImage, background);
				break;
			}
			case WHITE_CATERPILLAR: {
				Image whiteCaterImage = new Image(getClass().getResource("/resources/white_caterpillar.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(whiteCaterImage, background);
				break;
			}
			case WHITE_FROG: {
				Image whiteFrogImage = new Image(getClass().getResource("/resources/white_frog.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(whiteFrogImage, background);
				break;
			}
			case WHITE_OWL: {
				Image whiteOwlImage = new Image(getClass().getResource("/resources/white_owl.png").toExternalForm()); // replace with your image path
				stackPane = drawTokens(whiteOwlImage, background);
				break;
			}
			default:
				break;
        }
	    return stackPane;
    }
    
    private Node drawPlayer(Player player, ArrayList<Image> rabbitImages, ArrayList<Image> mouseImages) {
    	// Create a new Node to represent the cell
        Rectangle rectangle = new Rectangle(48, 48, Color.BLACK);
        switch (player.getCharacter()) {
	        case RABBIT: {
	        	if (!rabbitImages.isEmpty()) {
	        		// set the first player image in the image list to the rectangle background
					rectangle.setFill(new ImagePattern(rabbitImages.getFirst()));
					rabbitImages.removeFirst();
	        	} else {
	        		System.err.println("Rabbit Images is empty!");
	        	}
		        break;
			}
	        case MOUSE: {
	        	if (!mouseImages.isEmpty()) {
	        		// set the first player image in the image list to the rectangle background
					rectangle.setFill(new ImagePattern(mouseImages.getFirst()));
					mouseImages.removeFirst();				
	        	} else {
	        		System.err.println("Mouse Images is empty!");
	        	}
		        break;
			}
			default:
				rectangle.setFill(Color.CYAN);
				break;
		}
        return rectangle;
    }

	private void drawClock() {
		if (model.getClock().getTime() < 9) {
			clock.setText("0"+model.getClock().getTime()+":00");
		}else {
			clock.setText(model.getClock().getTime()+":00");
		}
	}
	private void drawCompass() {
		compass.setText("Hit field:"+model.getCompass().getFieldType());
	}
}
