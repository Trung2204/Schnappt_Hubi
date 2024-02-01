package com.guigame.board;

import java.util.*;
import com.guigame.helper.type.CellType;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GridCell {
	private CellType cellType;
//	private static final int CELL_SIZE = 100;
//    private Rectangle rectangle;
//    private Rectangle imageRectangle = new Rectangle(32, 32);
	
	private static ArrayList<CellType> innerWalls = 
			new ArrayList<>(Arrays.asList(CellType.OPEN_WALL, CellType.MOUSEHOLE_WALL, CellType.WINDOW_WALL));
	
	private static ArrayList<CellType> outerWalls = 
			new ArrayList<>(Arrays.asList(
					CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL,
					CellType.MOUSEHOLE_WALL, CellType.WINDOW_WALL));
	private static ArrayList<CellType> flippedTokens = 
			new ArrayList<>(Arrays.asList(
					CellType.WHITE_BAT, CellType.DARK_BAT,
					CellType.WHITE_CATERPILLAR, CellType.DARK_CATERPILLAR,
					CellType.WHITE_FROG, CellType.DARK_FROG, 
					CellType.WHITE_OWL, CellType.DARK_OWL));
	
//	public Group drawGridCell(int i, int j, int magicDoorX, int magicDoorY) {
//		switch (cellType) {
//			case NONE_WALL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image noneWallImage = new Image(getClass().getResource("/resources/none_wall.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(noneWallImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case CARROT_TOKEN: {
//				Image carrotImage = new Image(getClass().getResource("/resources/carrot_token.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(carrotImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case CHEESE_TOKEN: {
//	//			rectangle.setFill(Color.LIGHTYELLOW);
//	//			break;
//				Image cheeseImage = new Image(getClass().getResource("/resources/cheese_token.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(cheeseImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case CURTAIN_WALL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image curtainImage = null;
//				if (i == 1 || i == 3)	{
//					curtainImage = new Image(getClass().getResource("/resources/horizontal_curtain_wall.png").toExternalForm()); // replace with your image path
//				}
//				else {
//					curtainImage = new Image(getClass().getResource("/resources/vertical_curtain_wall.png").toExternalForm()); // replace with your image path
//				}
//				imageRectangle.setFill(new ImagePattern(curtainImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case WINDOW_WALL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image windowWallImage = null;
//				Random random = new Random();
//				int randomValue = random.nextInt(2); // Generates either 0 or 1
//
//				if (i == 1 || i == 3)	{
//					windowWallImage = new Image(getClass().getResource("/resources/horizontal_window_wall.png").toExternalForm()); // replace with your image path
//				}
//				else {
//					if (randomValue == 0) {
//						windowWallImage = new Image(getClass().getResource("/resources/vertical_open_window_wall.png").toExternalForm());
//					} else {
//						windowWallImage = new Image(getClass().getResource("/resources/vertical_closed_window_wall.png").toExternalForm());
//					}
//				}
//				imageRectangle.setFill(new ImagePattern(windowWallImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case MOUSEHOLE_WALL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image mouseHoleImage = null;
//				if (i == 1 || i == 3)	{
//					mouseHoleImage = new Image(getClass().getResource("/resources/horizontal_mousehole_wall.png").toExternalForm()); // replace with your image path
//				}
//				else {
//					mouseHoleImage = new Image(getClass().getResource("/resources/vertical_mousehole_wall.png").toExternalForm()); // replace with your image path
//				}
//				imageRectangle.setFill(new ImagePattern(mouseHoleImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case OPEN_WALL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image openWallImage = null;
//				if (i == 1 || i == 3){
//					if (i == magicDoorX && j == magicDoorY)	{
//						openWallImage = new Image(getClass().getResource("/resources/horizontal_open_magic_door_wall.png").toExternalForm()); // replace with your image path
//					}
//					else {
//						openWallImage = new Image(getClass().getResource("/resources/horizontal_open_wall.png").toExternalForm()); // replace with your image path
//					}
//				}
//				else {
//					if (i == magicDoorX && j == magicDoorY) {
//						openWallImage = new Image(getClass().getResource("/resources/vertical_open_magic_door_wall.png").toExternalForm()); // replace with your image path
//					} else {
//						openWallImage = new Image(getClass().getResource("/resources/vertical_open_wall.png").toExternalForm()); // replace with your image path
//					}
//				}
//				imageRectangle.setFill(new ImagePattern(openWallImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case MAGIC_DOOR_WALL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image magicDoorImage = null;
//				if (i == 1 || i == 3)	{
//					magicDoorImage = new Image(getClass().getResource("/resources/horizontal_closed_magic_door_wall_curtain.png").toExternalForm()); // replace with your image path
//				}
//				else {
//					magicDoorImage = new Image(getClass().getResource("/resources/vertical_closed_magic_door_wall_curtain.png").toExternalForm()); // replace with your image path
//				}
//				imageRectangle.setFill(new ImagePattern(magicDoorImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//
//			case GHOST: {
//				imageRectangle = new Rectangle(100, 100);
//				Image ghostImage = new Image(getClass().getResource("/resources/ghost.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(ghostImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//
//			case DARK_CATERPILLAR: {
//				imageRectangle = new Rectangle(100, 100);
//				Image darkCaterImage = new Image(getClass().getResource("/resources/black_caterpillar.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(darkCaterImage));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//
//			case DARK_FROG: {
//				imageRectangle = new Rectangle(100, 100);
//				Image darkFrogIM = new Image(getClass().getResource("/resources/black_frog.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(darkFrogIM));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case DARK_BAT: {
//				imageRectangle = new Rectangle(100, 100);
//				Image darkBatIm = new Image(getClass().getResource("/resources/black_bat.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(darkBatIm));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case DARK_OWL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image darkOwlIm = new Image(getClass().getResource("/resources/black_owl.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(darkOwlIm));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//
//			case WHITE_BAT: {
//				imageRectangle = new Rectangle(100, 100);
//				Image whiteBatIm = new Image(getClass().getResource("/resources/white_bat.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(whiteBatIm));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case WHITE_CATERPILLAR: {
//				imageRectangle = new Rectangle(100, 100);
//				Image whiteCaterIm = new Image(getClass().getResource("/resources/white_caterpillar.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(whiteCaterIm));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case WHITE_FROG: {
//				imageRectangle = new Rectangle(100, 100);
//				Image whiteFrogIm = new Image(getClass().getResource("/resources/white_frog.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(whiteFrogIm));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			case WHITE_OWL: {
//				imageRectangle = new Rectangle(100, 100);
//				Image whiteOwlIm = new Image(getClass().getResource("/resources/white_owl.png").toExternalForm()); // replace with your image path
//				imageRectangle.setFill(new ImagePattern(whiteOwlIm));
//				imageRectangle.setLayoutX((rectangle.getWidth() - imageRectangle.getWidth()) / 2);
//				imageRectangle.setLayoutY((rectangle.getHeight() - imageRectangle.getHeight()) / 2);
//				break;
//			}
//			default:
//				rectangle.setFill(Color.BLACK);
//				break;
//		}
////        return rectangle;
//		Group group = new Group(rectangle, imageRectangle);
//	    return group;
//    }
//	public void setOnMouseClicked(EventHandler<MouseEvent> handler) {
//        this.rectangle.setOnMouseClicked(handler);
//    }
	public GridCell(CellType cellType) {
		this.cellType = cellType;
//		this.rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.WHITE);
	}

	public void setCellType(CellType cellType) { this.cellType = cellType; }
    public CellType getCellType() { return this.cellType; }

    public void changeWall(Board board,int x, int y) {
		// Check for Magic Door Wall
    	if (x == board.getMagicDoorRow() && y == board.getMagicDoorCol()) {
    		this.cellType = CellType.MAGIC_DOOR_WALL;
    	} 
    	// Check for outer walls 
		else if (x == 0 || x == 4 || (x != 2 && y != 2)) {
			Random random = new Random();
			int index = random.nextInt(outerWalls.size());
			CellType newCellType = outerWalls.get(index);
			this.cellType = newCellType;
			outerWalls.remove(index);
    	}
    	// Check for inner walls
    	else {
    		Random random = new Random();
    		int index = random.nextInt(innerWalls.size());
    		CellType newCellType = innerWalls.get(index);
    		this.cellType = newCellType;	
    		innerWalls.remove(index);
    	}
    }
    
    public void changeMagicDoor() {
    	if (this.cellType == CellType.MAGIC_DOOR_WALL) {
        	this.cellType = CellType.OPEN_WALL;
        }
    }
    
    public void changeToken(Board board,int x, int y) {

    	if (x == board.getGhostRow() && y == board.getGhostCol()) {
    		this.cellType = CellType.GHOST;
    	} else {
            Random random = new Random();
            int index = random.nextInt(flippedTokens.size());
            CellType newCellType = flippedTokens.get(index);
            this.cellType = newCellType;
            flippedTokens.remove(index);
    	}
        
    }
}
