package com.guigame.application;

import java.util.*;
import com.guigame.board.*;
import com.guigame.helper.type.*;
import com.guigame.player.*;
import com.guigame.player.Character;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
    private static boolean win = false;
    
    private static Board board;
	private static Clock clock;
	private static Compass compass;
	
	private static Player[] listOfPlayers;
	private static int numberOfPlayers = 0;
	private static int numberOfRabbits = 0;
	private static int numberOfMice    = 0;

    private static int ghostX = -1;
	private static int ghostY = -1;
	private static boolean isGhostFound = false;
	private static boolean isMagicDoorFound = false;
	private static boolean isGhostActivated = false;

	private static class Pair {
        int row1, col1, row2, col2;

        Pair(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
    }
    private static List<Pair> adjacentPairs = new ArrayList<>();
	private static List<String> positionsChosen = new ArrayList<>();
    private static List<Pair> findAdjacentPairs() {
        // Iterate through the array
        for (int x = 0; x < board.getBoardSize(); x = x + 2) {
            for (int y = 0; y < board.getBoardSize(); y = y + 2) {
                // Check right neighbor
                int x1 = x + 2;
                int y1 = y + 2;
                CellType a = board.getGridCellAt(x,y).getCellType();

                // Check right neighbor
                if (x < board.getBoardSize() && y1 < board.getBoardSize()) {
                    CellType b = board.getGridCellAt(x, y1).getCellType();
                    if (a == b) {
                        adjacentPairs.add(new Pair(x, y, x, y1));
                    }
                }

                // Check bottom neighbor
                if (x1 < board.getBoardSize() && y < board.getBoardSize()) {
                    CellType c = board.getGridCellAt(x1, y).getCellType();
                    if (a == c) {
                        adjacentPairs.add(new Pair(x, y, x1, y));
                    }
                }
            }
        }

        return adjacentPairs;
    }
    private static void removePairs(List<Pair> adjacentPairs, int x, int y) {
        // Create a copy of the list to avoid ConcurrentModificationException
        List<Pair> pairsToRemove = new ArrayList<>();

        // Scan the list for pairs with the specified coordinates
        for (Pair pair : adjacentPairs) {
            if ((pair.row1 == x && pair.col1 == y) || (pair.row2 == x && pair.col2 == y)) {
                pairsToRemove.add(pair);
            }
        }

        // Remove the identified pairs
        adjacentPairs.removeAll(pairsToRemove);
    }
    
    public static void swapGhost(int x, int y) {
        // Create a Random object
        Random random = new Random();

        // Flag to indicate whether to repeat the switch statement
        boolean repeat;

        do {
            // Set the flag to false initially
            repeat = false;

            // Get a random choice
            int choice = random.nextInt(4) + 1; // Assuming 5 choices for demonstration

            // Your switch statement based on the current choice
            switch (choice) {
                case 1:
                    // Handle case UP
                    if ((x - 2) >= 0) {
                        board.swapGridCells(x, y, (x - 2), y);
                        ghostX = x - 2;
                    } else {
                        repeat = true;
                    }
                    break;

                case 2:
                    // Handle case DOWN
                    if ((x + 2) < board.getBoardSize()) {
                        board.swapGridCells(x, y, (x + 2), y);
                        ghostX = x + 2;
                    } else {
                        repeat = true;
                    }
                    break;

                case 3:
                    // Handle case LEFT
                    if ((y - 2) >= 0) {
                        board.swapGridCells(x, y, x, (y - 2));
                        ghostY = y - 2;
                    } else {
                        repeat = true;
                    }
                    break;

                case 4:
                    // Handle case RIGHT
                    if ((y + 2) < board.getBoardSize()) {
                        board.swapGridCells(x, y, x, (y + 2));
                        ghostY = y + 2;
                    } else {
                        repeat = true;
                    }
                    break;


                default:
                    // Handle default case
            }
        } while (repeat);
    }
    public static void scanForWin()	{
        int count = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            int playerX = listOfPlayers[i].getX();
            int playerY = listOfPlayers[i].getY();

            if (playerX == ghostX && playerY == ghostY && isGhostFound) {
                // Players and ghost are at the same coordinates
                count++;
            }
            if (count == 2) {
                win = true;
                break;
            }
        }
    }
    
    public static void play() {
        findAdjacentPairs();

        while(true) {
            if (win)	{
                System.out.println("You win! Congratulations!");
                break;
            }

            if (clock.getTime() == 12) {
                System.err.println("\nTIME'S UP");
                if (!win) {
                    System.out.println("You lose! Better luck next time");
                    break;
                }
            }
            for (int i = 0; i < numberOfPlayers; i++) {
                Player currentPlayer = listOfPlayers[i];
                System.out.println("\n#### Player "+(i+1)+": "+currentPlayer.getCharacter()+"'s turn ####");
                System.out.println( "\nCurrent position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
                // Spin the compass
                compass.spin();
                System.out.println("### Compass ###");
                System.out.println("Compass field: "+compass.getFieldType());
                // If hitting ghost field, ghost moves
                if (compass.getFieldType() == FieldType.GHOST) {
                    // If ghost is not found, moving 2 tokens of the same type
                    if (!isGhostFound) {
                        Random random = new Random();
                        int randomIndex = random.nextInt(adjacentPairs.size());
                        Pair chosenPair = adjacentPairs.get(randomIndex);
                        int row1 = chosenPair.row1;
                        int col1 = chosenPair.col1;
                        int row2 = chosenPair.row2;
                        int col2 = chosenPair.col2;

                        board.swapGridCells(row1, col1, row2, col2);
                    }
                    // If ghost is found, moving ghost token with 1 arbitrary adjacent token
                    else {

                        swapGhost(ghostX, ghostY);
                    }
                }
                // Update time
                clock.increaseTime(compass.getFieldType());
                System.out.println("Current time: "+clock.getTime());
                // Player gains number of actions from spinning the compass
                currentPlayer.setNumberOfActions(compass.getNumberOfAction());
                System.out.println("Player "+(i+1)+" gains "+currentPlayer.getNumberOfActions()+" action(s).");
                // Each player plays their turn with the corresponding number of actions
                int actionsPerformed = 0;
                while (actionsPerformed < currentPlayer.getNumberOfActions()) {
                    if (!isGhostActivated) {
                        System.out.println("\nMagic Door is not opened! Continuing phase 1...");
                    } else {
                        System.out.println("\nMagic Door is opened! Continuing phase 2...");
                        if (isGhostFound) {
                            System.out.println("Ghost is found at ("+ghostX+","+ghostY+")");
                        }
                    }

                    boolean actionSuccessful = false;
                    System.out.println();
                    ActionType action = currentPlayer.chooseAction(isGhostActivated, isMagicDoorFound);
                    DirectionType direction;
                    if (action != ActionType.NONE && action != ActionType.VIEW_TOKEN) {
                        direction = currentPlayer.chooseDirection();
                    } else if (action == ActionType.VIEW_TOKEN) {
                        direction = DirectionType.NONE;
                    } else {
                        direction = DirectionType.NONE;
                    }

                    // Phase 1
                    if (!isGhostActivated) {
                        actionSuccessful = handlePhaseOne(currentPlayer, action, direction);
                        System.out.println( "\n" + "Player " +(i+1)+": "+currentPlayer.getCharacter()+"'s current position: "+currentPlayer.getX()+" "+currentPlayer.getY()+"\n");
                        board.print();
                    }
                    // Phase 2
                    else {
                        actionSuccessful = handlePhaseTwo(currentPlayer, action, direction);
                        System.out.println( "\n" + "Player " +(i+1)+": "+currentPlayer.getCharacter()+"'s current position: "+currentPlayer.getX()+" "+currentPlayer.getY()+"\n");
                        board.print();
                        scanForWin();
                        if (win)	{
                            break;
                        }
                    }
                    if (actionSuccessful) {
                        actionsPerformed++;
                    }
                }
                if (win)	{
                    break;
                }
            }
        }
    }
    private static boolean handlePhaseOne(Player currentPlayer, ActionType action, DirectionType direction) {
        // If Magic Door has not been found yet, players can only view the curtain or move
        if (!isMagicDoorFound) {
            if (action == ActionType.MOVE) {
                return attemptMove(currentPlayer, direction);
            }
            else if (action == ActionType.VIEW_CURTAIN) {
                return attemptViewCurtain(currentPlayer, direction);
            }
            else return passAction();
        }
        // If Magic Door has been found, the action Open Magic Door is added
        else {
            if (action == ActionType.MOVE) {
                return attemptMove(currentPlayer, direction);
            }
            else if (action == ActionType.VIEW_CURTAIN) {
                return attemptViewCurtain(currentPlayer, direction);
            }
            else if (action == ActionType.OPEN_MAGIC_DOOR) {
                return attemptOpenMagicDoor(currentPlayer, direction);
            }
            else return passAction();
        }
//	    // Default return statement
//		return false;
    }
    private static boolean handlePhaseTwo(Player currentPlayer, ActionType action, DirectionType direction) {
        // In this phase, the magic door is now open wall, no need to consider it anymore
        if (action == ActionType.MOVE) {
            return attemptMove(currentPlayer, direction);
        }
        else if (action == ActionType.VIEW_CURTAIN) {
            return attemptViewCurtain(currentPlayer, direction);
        }
        else if (action == ActionType.VIEW_TOKEN) {
            return attemptViewToken(currentPlayer);
        }
        else return passAction();
//	    // Default return statement
//		return false;
    }
    private static boolean attemptMove(Player currentPlayer, DirectionType direction) {
        int newX = currentPlayer.getX();
        int newY = currentPlayer.getY();

        switch (direction) {
            case UP:
                newX -= 1;
                break;
            case DOWN:
                newX += 1;
                break;
            case LEFT:
                newY -= 1;
                break;
            case RIGHT:
                newY += 1;
                break;
            default:
                break;
        }

        // Check if adjacent cell is within bounds
        if (newX >= 0 && newX < board.getBoardSize() && newY >= 0 && newY < board.getBoardSize()) {
            GridCell adjacentCell = board.getGridCellAt(newX,newY);
            // Check if the adjacent cell is a curtain wall
            if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
                System.err.println("\n" + "CANNOT MOVE BECAUSE OF CURTAIN!" + "\n");
                return false;
            }
            // If not a curtain wall, then move the player
            else {
                // Rabbit can only move through Window and Open wall
                if (currentPlayer.getCharacter() == Character.RABBIT) {
                    if (adjacentCell.getCellType() == CellType.WINDOW_WALL || adjacentCell.getCellType() == CellType.OPEN_WALL) {
                        System.out.println("Player's moving...");
                        currentPlayer.move(direction);
                        return true;
                    } else {
                        System.err.println("\n" + "Rabbit can only move through WINDOW or OPEN wall");
                        return false;
                    }
                }
                // Mouse can only move through Mouse hole and Open wall
                if (currentPlayer.getCharacter() == Character.MOUSE) {
                    if (adjacentCell.getCellType() == CellType.MOUSEHOLE_WALL || adjacentCell.getCellType() == CellType.OPEN_WALL) {
                        System.out.println("Player's moving...");
                        currentPlayer.move(direction);
                        return true;
                    } else {
                        System.err.println("\n" + "Mouse can only move through MOUSE HOLE or OPEN wall");
                        return false;
                    }
                }
            }
        } else {
            System.err.println("\n" + "MOVE OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
            return false;
        }
        // Default return statement
        return false;
    }
    private static boolean attemptViewCurtain(Player currentPlayer, DirectionType direction) {
        int newX = currentPlayer.getX();
        int newY = currentPlayer.getY();
        switch (direction) {
            case UP:
                newX -= 1;
                break;
            case DOWN:
                newX += 1;
                break;
            case LEFT:
                newY -= 1;
                break;
            case RIGHT:
                newY += 1;
                break;
            default:
                break;
        }
        // Check if adjacent cell is within bounds
        if (newX >= 0 && newX < board.getBoardSize() && newY >= 0 && newY < board.getBoardSize()) {
            GridCell adjacentCell = board.getGridCellAt(newX,newY);
            // Check if the adjacent cell is a curtain wall, then reveal the curtain
            if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
                System.out.println("Player's viewing curtain...");
                currentPlayer.viewCurtain(adjacentCell, newX, newY);
                // If magic door is found, set isMagicDoorFound = true
                if (board.getGridCellAt(newX, newY).getCellType() == CellType.MAGIC_DOOR_WALL)
                {
                    System.err.println("Magic door is found, needs at least 1 player on each side and use 1 action to open!");
                    isMagicDoorFound = true;
                }
                return true;
            }
            // If not a curtain wall, cannot perform action
            else {
                System.err.println( "\n" + "The wall has already been revealed" + "\n");
                return false;
            }
        } else {
            System.err.println( "\n" + "VIEW CURTAIN OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
            return false;
        }
    }
    private static boolean attemptViewToken (Player currentPlayer) {
        int newX = currentPlayer.getX();
        int newY = currentPlayer.getY();
        GridCell newCell = board.getGridCellAt(newX, newY);
        // Rabbit can only view carrot tokens
        if (currentPlayer.getCharacter() == Character.RABBIT ) {
            if(newCell.getCellType() == CellType.CARROT_TOKEN) {
                System.out.println("Player's viewing token...");
                currentPlayer.viewToken(newCell, newX, newY);
                removePairs(adjacentPairs, newX, newY);
                // If player find ghost, set isGhostFound = true; and update ghost's coordinates
                if (isGhostFound == false && newCell.getCellType() == CellType.GHOST) {
                    isGhostFound = true;
                    ghostX = newX;
                    ghostY = newY;
                    System.out.println("Ghost is found at ("+ghostX+","+ghostY+")");
                }
                return true;
            } else if (newCell.getCellType() == CellType.CHEESE_TOKEN){
                System.err.println("\n" + "Rabbit can only view carrot tokens");
                return false;
            } else {
                System.out.println("\n" + "The token has already been revealed");
                return false;
            }
        }
        // Mouse can only view cheese tokens
        if (currentPlayer.getCharacter() == Character.MOUSE) {
            if (newCell.getCellType() == CellType.CHEESE_TOKEN) {
                System.out.println("Player's viewing token...");
                currentPlayer.viewToken(newCell, newX, newY);
                removePairs(adjacentPairs, newX, newY);
                // If player find ghost, set isGhostFound = true; and update ghost's coordinates
                if (isGhostFound == false && newCell.getCellType() == CellType.GHOST) {
                    isGhostFound = true;
                    ghostX = newX;
                    ghostY = newY;
                    System.out.println("Ghost is found at ("+ghostX+","+ghostY+")");
                }
                return true;
            } else if (newCell.getCellType() == CellType.CARROT_TOKEN) {
                System.err.println("Mouse can only view cheese tokens");
                return false;
            } else {
                System.out.println("The token has already been revealed");
                return false;
            }
        }
        // Default return statement
        return false;
    }
    private static boolean attemptOpenMagicDoor(Player currentPlayer, DirectionType direction) {
        int newX = currentPlayer.getX();
        int newY = currentPlayer.getY();
        switch (direction) {
            case UP:
                newX -= 1;
                break;
            case DOWN:
                newX += 1;
                break;
            case LEFT:
                newY -= 1;
                break;
            case RIGHT:
                newY += 1;
                break;
            default:
                break;
        }
        // Check if adjacent cell is within bounds
        if (newX >= 0 && newX < board.getBoardSize() && newY >= 0 && newY < board.getBoardSize()) {
            GridCell adjacentCell = board.getGridCellAt(newX,newY);
            // Check if the adjacent cell is a Magic Door
            if (adjacentCell.getCellType() == CellType.MAGIC_DOOR_WALL) {
                int otherSideX = newX;
                int otherSideY = newY;
                switch (direction) {
                    // calculate the position of the other side of the MAGIC_DOOR_WALL
                    case UP:
                        --otherSideX;
                        break;
                    case DOWN:
                        ++otherSideX;
                        break;
                    case LEFT:
                        --otherSideY;
                        break;
                    case RIGHT:
                        ++otherSideY;
                        break;
                    default:
                        break;
                }
                boolean isPlayerOnTheOtherSide = false;
                for (Player player : listOfPlayers) {
                    // check if other player position is available on the other side of the MAGIC_DOOR_WALL
                    if (player.getX() == otherSideX && player.getY() == otherSideY) {
                        isPlayerOnTheOtherSide = true;
                    }
                }
                if (isPlayerOnTheOtherSide) {
                    System.out.println("Player's opening magic door...");
                    currentPlayer.openMagicDoor(adjacentCell);
                    // Now the Magic Door is opened, starting phase 2
                    if (isGhostActivated == false) {
                        isGhostActivated = true;
                    }
                    return true;
                } else {
                    System.err.println("THERE IS NO PLAYER ON THE OTHER SIDE OF THE BOARD. PLEASE TRY AGAIN.");
                    return false;
                }
            }
            // If it's not a magic door, player cannot open it
            else {
                System.err.println("This is not the magic door! Try to move to the magic door to open it!");
                return false;
            }
        } else {
            System.err.println( "\n" + "OPEN MAGIC DOOR OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
            return false;
        }
//		// Default return statement
//		return false;
    }
    private static boolean passAction() {
        System.out.println("Player do nothing...");
        return true;
    }

    public Scene start(Stage primaryStage) {
        // Create the Initial Menu scene
        Button playButton = new Button("Play");
        Button manualButton = new Button("Reading manual");
        Button aboutUsButton = new Button("About Us");
        VBox menuLayout = new VBox(playButton,manualButton, aboutUsButton);
        Scene menuScene = new Scene(menuLayout, 200, 100);
        
        // Create the Setting Screen scene
        Label settingLabel = new Label("Setting Screen\nPlease choose the number of players");
        ToggleGroup group = new ToggleGroup();
        RadioButton rb2 = new RadioButton("2");
        RadioButton rb3 = new RadioButton("3");
        RadioButton rb4 = new RadioButton("4");
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);
        Button submitButton = new Button("Submit");
        Button nextButton = new Button("Next");
        nextButton.setDisable(true); // Disable the button initially
        Button returnSettingButton = new Button("Return");
        
        VBox vbox = new VBox(rb2, rb3, rb4, submitButton);
        vbox.setAlignment(Pos.CENTER_LEFT);

        BorderPane settingLayout = new BorderPane();
        settingLayout.setTop(settingLabel);
        settingLayout.setCenter(vbox);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(nextButton,returnSettingButton);
        AnchorPane.setBottomAnchor(nextButton, 10.0);
        AnchorPane.setRightAnchor(nextButton, 10.0);
        AnchorPane.setBottomAnchor(returnSettingButton, 10.0);
        AnchorPane.setLeftAnchor(returnSettingButton, 10.0);

        settingLayout.setBottom(anchorPane);
        
        Scene settingScene = new Scene(settingLayout, 200, 100);
        submitButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            if (selectedRadioButton != null) {
                numberOfPlayers = Integer.parseInt(selectedRadioButton.getText());
                // Create an array to store players
                listOfPlayers = new Player[numberOfPlayers];
                // Enable the "Let's Go!" button
                nextButton.setDisable(false);
            } else {
                settingLabel.setText("Please select the number of players.");
            }
        });
        nextButton.setOnAction(event -> {
            createPlayerScene(primaryStage,0);
        });

        // Create the Manual Screen scene
        Label manualLabel = new Label("Manual Screen");
        Button returnManualButton = new Button("Return");
        VBox manualLayout = new VBox(manualLabel, returnManualButton);
        manualLayout.setAlignment(Pos.BASELINE_CENTER);
        Scene manualScene = new Scene(manualLayout, 200, 100);
        
        // Create the Information Screen scene
        Label infoLabel = new Label("Information Screen");
        Button returnInfoButton = new Button("Return");
        VBox infoLayout = new VBox(infoLabel, returnInfoButton);
        infoLayout.setAlignment(Pos.BASELINE_CENTER);
        Scene infoScene = new Scene(infoLayout, 200, 100);

        // Set up the buttons to switch to the appropriate scenes when clicked
        playButton.setOnAction(event -> primaryStage.setScene(settingScene));
        returnSettingButton.setOnAction(event -> primaryStage.setScene(menuScene));
        aboutUsButton.setOnAction(event -> primaryStage.setScene(infoScene));
        returnInfoButton.setOnAction(event -> primaryStage.setScene(menuScene));
        manualButton.setOnAction(event -> primaryStage.setScene(manualScene));
        returnManualButton.setOnAction(event -> primaryStage.setScene(menuScene));

        return menuScene;
    }
    private void createPlayerScene(Stage primaryStage, int playerIndex) {
        if (playerIndex >= numberOfPlayers) {
            return; // All players have made their selection
        }

        // Create a new scene for the player to choose their character and starting position
        Label characterSelectionLabel = new Label("Player " + (playerIndex+1) + " chooses character:");
        ToggleGroup characterGroup = new ToggleGroup();
        RadioButton rbRabbit = new RadioButton("RABBIT");
        RadioButton rbMouse = new RadioButton("MOUSE");
        rbRabbit.setToggleGroup(characterGroup);
        rbMouse.setToggleGroup(characterGroup);

        // Disable the rabbit or mouse radio button if the maximum number is reached
        rbRabbit.setDisable(numberOfRabbits >= 2);
        rbMouse.setDisable(numberOfMice >= 2);

        Label startingPositionLabel = new Label("Choose starting position:");
        ToggleGroup positionGroup = new ToggleGroup();
        RadioButton rbTopLeft = new RadioButton("Top Left");
        RadioButton rbTopRight = new RadioButton("Top Right");
        RadioButton rbBottomLeft = new RadioButton("Bottom Left");
        RadioButton rbBottomRight = new RadioButton("Bottom Right");
        rbTopLeft.setToggleGroup(positionGroup);
        rbTopRight.setToggleGroup(positionGroup);
        rbBottomLeft.setToggleGroup(positionGroup);
        rbBottomRight.setToggleGroup(positionGroup);

        // Disable the position radio button if the position has been chosen
        rbTopLeft.setDisable(positionsChosen.contains("TOP LEFT"));
        rbTopRight.setDisable(positionsChosen.contains("TOP RIGHT"));
        rbBottomLeft.setDisable(positionsChosen.contains("BOTTOM LEFT"));
        rbBottomRight.setDisable(positionsChosen.contains("BOTTOM RIGHT"));

        Button submitCharacterAndPositionButton = new Button("Submit");
        Button returnButton = new Button("Return");
        Button letsGoButton = new Button("Let's Go!");
        letsGoButton.setVisible(false); // Only show when the last player finishes the selection
        returnButton.setVisible(playerIndex != 0); // Don't show for the first player

        VBox vbox = new VBox(characterSelectionLabel, rbRabbit, rbMouse, startingPositionLabel, rbTopLeft, rbTopRight, rbBottomLeft, rbBottomRight, submitCharacterAndPositionButton, returnButton, letsGoButton);
        vbox.setAlignment(Pos.CENTER_LEFT);

        Scene characterAndPositionScene = new Scene(vbox, 200, 100);
        primaryStage.setScene(characterAndPositionScene); // Switch to the new scene

        submitCharacterAndPositionButton.setOnAction(characterAndPositionEvent -> {
            // Handle character selection and starting position input
            String characterInput = ((RadioButton) characterGroup.getSelectedToggle()).getText().toUpperCase();
            Character character = Character.valueOf(characterInput);
            String positionInput = ((RadioButton) positionGroup.getSelectedToggle()).getText().toUpperCase();
            int x,y;
            switch (positionInput) {
            case "TOP LEFT":
                x = 0;
                y = 0;
                break;
            case "TOP RIGHT":
                x = 0;
                y = 4;
                break;
            case "BOTTOM LEFT":
                x = 4;
                y = 0;
                break;
            case "BOTTOM RIGHT":
                x = 4;
                y = 4;
                break;
            default:
                throw new IllegalArgumentException("Invalid position: " + positionInput);
            }

            // Add the player to the array
            Player player = new Player(x,y,character);
            listOfPlayers[playerIndex] = player;
            listOfPlayers[playerIndex].print();

            // Count the number of rabbits and mice chosen so far
            if (character == Character.RABBIT) numberOfRabbits++;
            else if (character == Character.MOUSE) numberOfMice++;
            
            // Add the chosen position to the list of positions chosen
            positionsChosen.add(positionInput);

            // If it's the last player, make the "Let's Go!" button visible
            if (playerIndex == numberOfPlayers - 1) {
                letsGoButton.setVisible(true);
            }

            // Create the scene for the next player
            if (playerIndex < numberOfPlayers - 1) {
                createPlayerScene(primaryStage, playerIndex + 1);
            }
        });

        returnButton.setOnAction(returnEvent -> {
            // Return to the previous player's scene
            createPlayerScene(primaryStage, playerIndex - 1);
        });

        letsGoButton.setOnAction(goEvent -> {
            // Enter the Main Game Screen scene
            // Create the Main Game Screen scene
            Label gameLabel = new Label("Main Game Screen");
            board = new Board(numberOfRabbits,numberOfMice);
            board.setOnCellClicked();
            
            clock = new Clock();
            compass = new Compass();
            VBox gameLayout = new VBox(gameLabel,board.drawBoard());
            Scene gameScene = new Scene(gameLayout, 200, 100);
            primaryStage.setScene(gameScene); // Switch to the Main Game Screen scene
            play();
        });
    }
}