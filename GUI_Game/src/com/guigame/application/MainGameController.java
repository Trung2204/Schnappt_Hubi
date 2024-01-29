package com.guigame.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.guigame.board.GridCell;
import com.guigame.helper.type.ActionType;
import com.guigame.helper.type.CellType;
import com.guigame.helper.type.CharacterType;
import com.guigame.helper.type.DirectionType;
import com.guigame.player.Player;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainGameController {
    @FXML
    private AnchorPane mainGamePane;
    @FXML
    private Pane player1ActionSelection,player2ActionSelection,player3ActionSelection,player4ActionSelection;
    
    
    
    
    private GridPane gridPaneBoard;
    private VBox clockCompassBox;
    
    private AnimationTimer gameLoop;
    
    private static boolean win = false;
    private int currentPlayerIndex = 0;
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
    private static List<Pair> findAdjacentPairs() {
        // Iterate through the array
        for (int x = 0; x < Game.board.getBoardSize(); x = x + 2) {
            for (int y = 0; y < Game.board.getBoardSize(); y = y + 2) {
                // Check right neighbor
                int x1 = x + 2;
                int y1 = y + 2;
                CellType a = Game.board.getGridCellAt(x,y).getCellType();

                // Check right neighbor
                if (x < Game.board.getBoardSize() && y1 < Game.board.getBoardSize()) {
                    CellType b = Game.board.getGridCellAt(x, y1).getCellType();
                    if (a == b) {
                        adjacentPairs.add(new Pair(x, y, x, y1));
                    }
                }

                // Check bottom neighbor
                if (x1 < Game.board.getBoardSize() && y < Game.board.getBoardSize()) {
                    CellType c = Game.board.getGridCellAt(x1, y).getCellType();
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
	    if (newX >= 0 && newX < Game.board.getBoardSize() && newY >= 0 && newY < Game.board.getBoardSize()) {
	        GridCell adjacentCell = Game.board.getGridCellAt(newX,newY);
	        // Check if the adjacent cell is a curtain wall
	        if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
	            System.err.println("\n" + "CANNOT MOVE BECAUSE OF CURTAIN!" + "\n");
	            return false;
	        }
	        // If not a curtain wall, then move the player
	        else {
	            // Rabbit can only move through Window and Open wall
	            if (currentPlayer.getCharacter() == CharacterType.RABBIT) {
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
	            if (currentPlayer.getCharacter() == CharacterType.MOUSE) {
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
	    if (newX >= 0 && newX < Game.board.getBoardSize() && newY >= 0 && newY < Game.board.getBoardSize()) {
	        GridCell adjacentCell = Game.board.getGridCellAt(newX,newY);
	        // Check if the adjacent cell is a curtain wall, then reveal the curtain
	        if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
	            System.out.println("Player's viewing curtain...");
	            currentPlayer.viewCurtain(adjacentCell, newX, newY);
	            // If magic door is found, set isMagicDoorFound = true
	            if (Game.board.getGridCellAt(newX, newY).getCellType() == CellType.MAGIC_DOOR_WALL)
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
	    GridCell newCell = Game.board.getGridCellAt(newX, newY);
	    // Rabbit can only view carrot tokens
	    if (currentPlayer.getCharacter() == CharacterType.RABBIT ) {
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
	    if (currentPlayer.getCharacter() == CharacterType.MOUSE) {
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
	        if (newX >= 0 && newX < Game.board.getBoardSize() && newY >= 0 && newY < Game.board.getBoardSize()) {
	            GridCell adjacentCell = Game.board.getGridCellAt(newX,newY);
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
	                for (Player player : Game.listOfPlayers) {
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
                    	Game.board.swapGridCells(x, y, (x - 2), y);
                        ghostX = x - 2;
                    } else {
                        repeat = true;
                    }
                    break;

                case 2:
                    // Handle case DOWN
                    if ((x + 2) < Game.board.getBoardSize()) {
                    	Game.board.swapGridCells(x, y, (x + 2), y);
                        ghostX = x + 2;
                    } else {
                        repeat = true;
                    }
                    break;

                case 3:
                    // Handle case LEFT
                    if ((y - 2) >= 0) {
                    	Game.board.swapGridCells(x, y, x, (y - 2));
                        ghostY = y - 2;
                    } else {
                        repeat = true;
                    }
                    break;

                case 4:
                    // Handle case RIGHT
                    if ((y + 2) < Game.board.getBoardSize()) {
                    	Game.board.swapGridCells(x, y, x, (y + 2));
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
        for (int i = 0; i < Game.numberOfPlayers; i++) {
            int playerX = Game.listOfPlayers[i].getX();
            int playerY = Game.listOfPlayers[i].getY();

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
    
    @FXML
    public void initialize() {
        gridPaneBoard = Game.drawBoard();
        clockCompassBox = Game.drawClockCompass();
        // Set the VBox to the top right corner
        AnchorPane.setTopAnchor(clockCompassBox, 0.0);
        AnchorPane.setRightAnchor(clockCompassBox, 0.0);
        mainGamePane.getChildren().addAll(gridPaneBoard, clockCompassBox);
        
        // Show player action selection panel based on the number of players
        if (Game.numberOfPlayers == 2) {
        	player3ActionSelection.setVisible(false);
        	player4ActionSelection.setVisible(false);
        }
        if (Game.numberOfPlayers == 3) {
        	player4ActionSelection.setVisible(false);
        }
        
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if (win) {
                    System.out.println("You win! Congratulations!");
                    this.stop();
                }
                if (Game.clock.getTime() == 12) {
                    System.err.println("\nTIME'S UP");
                    if (!win) {
                        System.out.println("You lose! Better luck next time");
                        this.stop();
                    }
                }
                for (int i = 0; i < Game.numberOfPlayers; i++) {
                	Player currentPlayer = Game.listOfPlayers[currentPlayerIndex];
                	System.out.println("\n#### Player "+(i+1)+": "+currentPlayer.getCharacter()+"'s turn ####");
                    
                	// Spin compass
                    Game.compass.spin();
                    System.out.println("Compass field: "+Game.compass.getFieldType());
                    
                    // If hitting ghost field, ghost move
                    // ...
                    
                    // Update time
                    Game.clock.increaseTime(Game.compass.getFieldType());
                    System.out.println("Current time: "+Game.clock.getTime());
                    
                    // Player gains number of actions from spinning the compass
                    currentPlayer.setNumberOfActions(Game.compass.getNumberOfAction());
                    System.out.println("Player "+ (currentPlayerIndex+1) +" gains "+currentPlayer.getNumberOfActions()+" action(s).");
                    
//                    // Each player plays their turn with the corresponding number of actions
//                    int actionsPerformed = 0;
//                    while (actionsPerformed < currentPlayer.getNumberOfActions()) {
//                        if (!isGhostActivated) {
//                            System.out.println("\nMagic Door is not opened! Continuing phase 1...");
//                        } else {
//                            System.out.println("\nMagic Door is opened! Continuing phase 2...");
//                            if (isGhostFound) {
//                                System.out.println("Ghost is found at ("+ghostX+","+ghostY+")");
//                            }
//                        }
//                        
//                        boolean actionSuccessful = false;
//                        System.out.println();
//                        ActionType action = currentPlayer.chooseAction(isGhostActivated, isMagicDoorFound);
//                        DirectionType direction;
//                        if (action != ActionType.NONE && action != ActionType.VIEW_TOKEN) {
//                            direction = currentPlayer.chooseDirection();
//                        } else if (action == ActionType.VIEW_TOKEN) {
//                            direction = DirectionType.NONE;
//                        } else {
//                            direction = DirectionType.NONE;
//                        }
//                        
//                        // Phase 1
//                        if (!isGhostActivated) {
//                            actionSuccessful = handlePhaseOne(currentPlayer, action, direction);
//                            System.out.println( "\n" + "Player " +(currentPlayerIndex+1)+": "+currentPlayer.getCharacter()+"'s current position: "+currentPlayer.getX()+" "+currentPlayer.getY()+"\n");
//                            Game.board.print();
//                        }
//                        // Phase 2
//                        else {
//                            actionSuccessful = handlePhaseTwo(currentPlayer, action, direction);
//                            System.out.println( "\n" + "Player " +(currentPlayerIndex+1)+": "+currentPlayer.getCharacter()+"'s current position: "+currentPlayer.getX()+" "+currentPlayer.getY()+"\n");
//                            Game.board.print();
//                            scanForWin();
//                            if (win)	{
//                                break;
//                            }
//                        }
//                        if (actionSuccessful) {
//                            actionsPerformed++;
//                        }
//                    }
                }
                
                // Remove the old VBox and GridPane from the pane
                mainGamePane.getChildren().removeAll(gridPaneBoard, clockCompassBox);

                // Redraw the VBox and GridPane and add them to the pane
                clockCompassBox = Game.drawClockCompass();
                AnchorPane.setTopAnchor(clockCompassBox, 0.0);
                AnchorPane.setRightAnchor(clockCompassBox, 0.0);
                mainGamePane.getChildren().addAll(gridPaneBoard, clockCompassBox);
            }
        };
        startGame();
    }

    public void startGame() {
        gameLoop.start();
    }

    public void stopGame() {
        gameLoop.stop();
    }
}
