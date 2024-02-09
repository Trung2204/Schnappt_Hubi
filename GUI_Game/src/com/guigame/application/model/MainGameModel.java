package com.guigame.application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.guigame.application.model.board.Board;
import com.guigame.application.model.board.Clock;
import com.guigame.application.model.board.Compass;
import com.guigame.application.model.board.GridCell;
import com.guigame.application.model.helper.type.ActionType;
import com.guigame.application.model.helper.type.CellType;
import com.guigame.application.model.helper.type.CharacterType;
import com.guigame.application.model.helper.type.DirectionType;
import com.guigame.application.model.helper.type.FieldType;
import com.guigame.application.model.helper.Pair;
import com.guigame.application.model.player.Player;

public class MainGameModel {
	private Board board;
    private Clock clock;
    private Compass compass;
    private Player[] listOfPlayers;
    private int currentPlayerIndex = 0;
    private int numberOfPlayers = 0;
    private int numberOfRabbits = 0;
    private int numberOfMice    = 0;
   
    // Additional game state variables
    private static boolean win = false; 
    private static int ghostX = -1;
	private static int ghostY = -1;
	private static boolean isGhostFound = false;
	private static boolean isMagicDoorFound = false;
	private static boolean isGhostActivated = false;

    private static List<Pair> adjacentPairs = new ArrayList<>();
    public void findAdjacentPairs() {
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
    }
    private void removePairs(int x, int y) {
        // Create a copy of the list to avoid ConcurrentModificationException
        List<Pair> pairsToRemove = new ArrayList<>();

        // Scan the list for pairs with the specified coordinates
        for (Pair pair : adjacentPairs) {
            if ((pair.getRow1() == x && pair.getCol1() == y) || (pair.getRow2() == x && pair.getCol2() == y)) {
                pairsToRemove.add(pair);
            }
        }

        // Remove the identified pairs
        adjacentPairs.removeAll(pairsToRemove);
    }
    
    // Game states
	public enum GameState{
        SPIN_COMPASS,
        UPDATE_CLOCK,
        UPDATE_PLAYER_ACTIONS,
        PERFORM_PLAYER_ACTION,
        NEXT_PLAYER,
        CHECK_GAME_OVER,
        WIN,
        LOSE
	}
	private GameState currentState;
    private boolean viewUpdated;
    private boolean actionPerformed;
	
	private void handlePhaseOne(Player currentPlayer, ActionType action, DirectionType direction) {
    	// If Magic Door has not been found yet, players can only view the curtain or move
	    if (!isMagicDoorFound) {
	    	switch (action) {
	    	case MOVE:
	    		attemptMove(currentPlayer,direction);
	    		break;
	    	case VIEW_CURTAIN:
	        	attemptViewCurtain(currentPlayer, direction);
	            break;
			default:
				break;
	    	}
	    }
	    // If Magic Door has been found, the action Open Magic Door is added
	    else {
	    	switch (action) {
	    	case MOVE:
	    		attemptMove(currentPlayer,direction);
	    		break;
	    	case VIEW_CURTAIN:
	        	attemptViewCurtain(currentPlayer, direction);
	            break;
	    	case OPEN_MAGIC_DOOR:
	    		attemptOpenMagicDoor(currentPlayer, direction);
	    		break;
			default:
				break;
	    	}
	    }
    }
    private void handlePhaseTwo(Player currentPlayer, ActionType action, DirectionType direction) {
    	switch (action) {
    	case MOVE:
    		attemptMove(currentPlayer,direction);
    		break;
    	case VIEW_CURTAIN:
        	attemptViewCurtain(currentPlayer, direction);
            break;
    	case VIEW_TOKEN:
    		attemptViewToken(currentPlayer);
		default:
			break;
    	}
    }
	
    // Track unsuccessful actions
    private boolean moveOutOfBound = false;
	private boolean moveThroughCurtain = false;
	private boolean rabbitsMoveThroughMouseHole = false;
	private boolean miceMoveThroughWindow = false;
	
	private boolean viewCurtainOutOfBound = false;
	private boolean viewOpenedCurtain = false;
	
	private boolean openMagicDoorOutOfBound = false;
	private boolean openNotMagicDoor = false;
	private boolean noPlayerOtherSide = false;
	
	private boolean viewRevealedToken = false;
	private boolean rabbitsViewCheese = false;
	private boolean miceViewCarrot = false;
	
	public boolean isMoveOutOfBound() { return this.moveOutOfBound; }
	public boolean isMoveThroughCurtain() { return this.moveThroughCurtain; }
	public boolean isRabbitsMoveThroughMouseHole() { return this.rabbitsMoveThroughMouseHole; }
	public boolean isMiceMoveThroughWindow() { return this.miceMoveThroughWindow; }
	
	public boolean isViewCurtainOutOfBound() { return this.viewCurtainOutOfBound; }
	public boolean isViewOpenedCurtain() { return this.viewOpenedCurtain; }
	
	public boolean isOpenMagicDoorOutOfBound() { return this.openMagicDoorOutOfBound; }
	public boolean isOpenNotMagicDoor() { return this.openNotMagicDoor; }
	public boolean isNoPlayerOtherSide() { return this.noPlayerOtherSide; }
	
	public boolean isViewRevealedToken() { return this.viewRevealedToken; }
	public boolean isRabbitsViewCheese() { return this.rabbitsViewCheese; }
	public boolean isMiceViewCarrot() { return this.miceViewCarrot; }
	
    private void attemptMove(Player currentPlayer, DirectionType direction) {
    	this.viewCurtainOutOfBound = false;
    	this.viewOpenedCurtain = false;
    	
    	this.openMagicDoorOutOfBound = false;
    	this.openNotMagicDoor = false;
    	this.noPlayerOtherSide = false;
    	
    	this.viewRevealedToken = false;
    	this.rabbitsViewCheese = false;
    	this.miceViewCarrot = false;
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
	    	this.moveOutOfBound = false;
	        GridCell adjacentCell = board.getGridCellAt(newX,newY);
	        // Check if the adjacent cell is a curtain wall
	        if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
//	            System.err.println("\n" + "CANNOT MOVE BECAUSE OF CURTAIN!" + "\n");
	            this.moveThroughCurtain = true;
	            this.rabbitsMoveThroughMouseHole = false;
	            this.miceMoveThroughWindow = false;
	            this.actionPerformed = false;
	        }
	        // If not a curtain wall, then move the player
	        else {
	        	this.moveThroughCurtain = false;
	            // Rabbit can only move through Window and Open wall
	            if (currentPlayer.getCharacter() == CharacterType.RABBIT) {
	                if (adjacentCell.getCellType() == CellType.WINDOW_WALL || adjacentCell.getCellType() == CellType.OPEN_WALL) {
//	                    System.out.println("Player's moving...");
	                    currentPlayer.move(direction);
	                    this.actionPerformed = true;
	    	            this.rabbitsMoveThroughMouseHole = false;
	    	            this.miceMoveThroughWindow = false;
	                } else {
//	                    System.err.println("\n" + "Rabbit can only move through WINDOW or OPEN wall");
	                    this.rabbitsMoveThroughMouseHole = true;
	                    this.miceMoveThroughWindow = false;
	                    this.actionPerformed = false;
	                }
	            }
	            // Mouse can only move through Mouse hole and Open wall
	            if (currentPlayer.getCharacter() == CharacterType.MOUSE) {
	                if (adjacentCell.getCellType() == CellType.MOUSEHOLE_WALL || adjacentCell.getCellType() == CellType.OPEN_WALL) {
//	                    System.out.println("Player's moving...");
	                    currentPlayer.move(direction);
	                    this.actionPerformed = true;
	    	            this.rabbitsMoveThroughMouseHole = false;
	    	            this.miceMoveThroughWindow = false;
	                } else {
//	                    System.err.println("\n" + "Mouse can only move through MOUSE HOLE or OPEN wall");
	                    this.miceMoveThroughWindow = true;
	                    this.rabbitsMoveThroughMouseHole = false;
	                    this.actionPerformed = false;
	                }
	            }
	        }
	    } else {
//	        System.err.println("\n" + "MOVE OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
	        this.moveOutOfBound = true;
	        this.moveThroughCurtain = false;
	        this.rabbitsMoveThroughMouseHole = false;
            this.miceMoveThroughWindow = false;
	        this.actionPerformed = false;
	    }
	}
	private void attemptViewCurtain(Player currentPlayer, DirectionType direction) {
		this.moveOutOfBound = false;
        this.moveThroughCurtain = false;
        this.rabbitsMoveThroughMouseHole = false;
        this.miceMoveThroughWindow = false;
        
        this.openMagicDoorOutOfBound = false;
        this.openNotMagicDoor = false;
        this.noPlayerOtherSide = false;
        
        this.viewRevealedToken = false;
    	this.rabbitsViewCheese = false;
    	this.miceViewCarrot = false;
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
	    	this.viewCurtainOutOfBound = false;
	        GridCell adjacentCell = board.getGridCellAt(newX,newY);
	        // Check if the adjacent cell is a curtain wall, then reveal the curtain
	        if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
//	            System.out.println("Player's viewing curtain...");
	            currentPlayer.viewCurtain(adjacentCell,board, newX, newY);
	            // If magic door is found, set isMagicDoorFound = true
	            if (board.getGridCellAt(newX, newY).getCellType() == CellType.MAGIC_DOOR_WALL)
	            {
//	                System.err.println("Magic door is found, needs at least 1 player on each side and use 1 action to open!");
	                isMagicDoorFound = true;
	            }
	            this.actionPerformed = true;
	            this.viewOpenedCurtain = false;
	        }
	        // If not a curtain wall, cannot perform action
	        else {
//	            System.err.println( "\n" + "The wall has already been revealed" + "\n");
	            this.viewOpenedCurtain = true;
	            this.actionPerformed = false;
	        }
	    } else {
//	        System.err.println( "\n" + "VIEW CURTAIN OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
	        this.viewCurtainOutOfBound = true;
	        this.viewOpenedCurtain = false;
	        this.actionPerformed = false;
	    }
	}
	private void attemptViewToken (Player currentPlayer) {
		this.moveOutOfBound = false;
		this.moveThroughCurtain = false;
		this.rabbitsMoveThroughMouseHole = false;
		this.miceMoveThroughWindow = false;
		
		this.viewCurtainOutOfBound = false;
		this.viewOpenedCurtain = false;
		
		this.openMagicDoorOutOfBound = false;
		this.openNotMagicDoor = false;
		this.noPlayerOtherSide = false;
	    int newX = currentPlayer.getX();
	    int newY = currentPlayer.getY();
	    GridCell newCell = board.getGridCellAt(newX, newY);
	    // Rabbit can only view carrot tokens
	    if (currentPlayer.getCharacter() == CharacterType.RABBIT ) {
	        if(newCell.getCellType() == CellType.CARROT_TOKEN) {
//	            System.out.println("Player's viewing token...");
	            currentPlayer.viewToken(newCell,board, newX, newY);
	            removePairs(newX, newY);
	            // If player find ghost, set isGhostFound = true; and update ghost's coordinates
	            if (isGhostFound == false && newCell.getCellType() == CellType.GHOST) {
	                isGhostFound = true;
	                ghostX = newX;
	                ghostY = newY;
//	                System.out.println("Ghost is found at ("+ghostX+","+ghostY+")");
	            }
	            this.actionPerformed = true;
	            this.rabbitsViewCheese = false;
	            this.miceViewCarrot = false;
	            this.viewRevealedToken = false;
	        } else if (newCell.getCellType() == CellType.CHEESE_TOKEN){
//	            System.err.println("\n" + "Rabbit can only view carrot tokens");
	            this.rabbitsViewCheese = true;
	            this.miceViewCarrot = false;
	            this.viewRevealedToken = false;
	            this.actionPerformed = false;
	        } else {
//	            System.out.println("\n" + "The token has already been revealed");
	            this.viewRevealedToken = true;
	            this.rabbitsViewCheese = false;
	            this.miceViewCarrot = false;
	            this.actionPerformed = false;
	        }
	    }
	    // Mouse can only view cheese tokens
	    if (currentPlayer.getCharacter() == CharacterType.MOUSE) {
	        if (newCell.getCellType() == CellType.CHEESE_TOKEN) {
//	            System.out.println("Player's viewing token...");
	            currentPlayer.viewToken(newCell,board, newX, newY);
	            removePairs(newX, newY);
	            // If player find ghost, set isGhostFound = true; and update ghost's coordinates
	            if (isGhostFound == false && newCell.getCellType() == CellType.GHOST) {
	                isGhostFound = true;
	                ghostX = newX;
	                ghostY = newY;
//	                System.out.println("Ghost is found at ("+ghostX+","+ghostY+")");
	            }
	            this.actionPerformed = true;
	            this.rabbitsViewCheese = false;
	            this.miceViewCarrot = false;
	            this.viewRevealedToken = false;
	        } else if (newCell.getCellType() == CellType.CARROT_TOKEN) {
//	            System.err.println("Mouse can only view cheese tokens");
	            this.miceViewCarrot = true;
	            this.rabbitsViewCheese = false;
	            this.viewRevealedToken = false;
	            this.actionPerformed = false;
	        } else {
//	            System.out.println("The token has already been revealed");
	            this.viewRevealedToken = true;
	            this.rabbitsViewCheese = false;
	            this.miceViewCarrot = false;
	            this.actionPerformed = false;
	        }
	    }
	}
	private void attemptOpenMagicDoor(Player currentPlayer, DirectionType direction) {
		this.moveOutOfBound = false;
		this.moveThroughCurtain = false;
		this.rabbitsMoveThroughMouseHole = false;
		this.miceMoveThroughWindow = false;
		
		this.viewCurtainOutOfBound = false;
		this.viewOpenedCurtain = false;
		
		this.viewRevealedToken = false;
    	this.rabbitsViewCheese = false;
    	this.miceViewCarrot = false;
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
        	this.openMagicDoorOutOfBound = false;
            GridCell adjacentCell = board.getGridCellAt(newX,newY);
            // Check if the adjacent cell is a Magic Door
            if (adjacentCell.getCellType() == CellType.MAGIC_DOOR_WALL) {
            	this.openNotMagicDoor = false;
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
//                    System.out.println("Player's opening magic door...");
                    currentPlayer.openMagicDoor(adjacentCell);
                    // Now the Magic Door is opened, starting phase 2
                    if (isGhostActivated == false) {
                        isGhostActivated = true;
                    }
                    this.actionPerformed = true;
                    this.noPlayerOtherSide = false;
                } else {
//                    System.err.println("THERE IS NO PLAYER ON THE OTHER SIDE OF THE BOARD. PLEASE TRY AGAIN.");
                    this.noPlayerOtherSide = true;
                    this.actionPerformed = false;
                }
            }
            // If it's not a magic door, player cannot open it
            else {
//                System.err.println("This is not the magic door! Try to move to the magic door to open it!");
                this.openNotMagicDoor = true;
                this.noPlayerOtherSide = false;
                this.actionPerformed = false;
            }
        } else {
//            System.err.println( "\n" + "OPEN MAGIC DOOR OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
            this.openMagicDoorOutOfBound = true;
            this.openNotMagicDoor = false;
            this.noPlayerOtherSide = false;
            this.actionPerformed = false;
        }
    }
	private void swapGhost(int x, int y) {
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
	private void scanForWin() {
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

    // Constructor
    public MainGameModel(int numberOfPlayers, Player[] listOfPlayers, int numberOfRabbits, int numberOfMice) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfRabbits = numberOfRabbits;
    	this.numberOfMice = numberOfMice;
        this.listOfPlayers = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
		    this.listOfPlayers[i] = listOfPlayers[i];
		} 
		this.board = new Board(numberOfRabbits,numberOfMice);
		this.clock = new Clock();
		this.compass = new Compass();
        // Initialize the game state
        this.currentState = GameState.SPIN_COMPASS;
        this.viewUpdated = false;
        this.actionPerformed = false;
    }
    
    // Getter methods
    public Board getBoard() { return board; }
    public Clock getClock() { return clock; }
    public Compass getCompass() { return compass; }
    public Player[] getListOfPlayers() { return listOfPlayers; }
    public int getCurrentPlayerIndex() { return currentPlayerIndex; }
    public int getNumberOfPlayers() { return numberOfPlayers; }
    public int getNumberOfRabbits() { return numberOfRabbits; }
    public int getNumberOfMice() { return numberOfMice; }
    public boolean isGhostActivated() { return isGhostActivated; }
    public boolean isMagicDoorFound() { return isMagicDoorFound; }
    public boolean isGhostFound() { return isGhostFound; }
    public GameState getCurrentState() { return currentState; }
    public boolean getWin() { return win; }
    public boolean isViewUpdated() { return this.viewUpdated; }
    public boolean isActionPerformed() { return this.actionPerformed; }
    // Setter methods
    public void setViewUpdated(boolean viewUpdated) { this.viewUpdated = viewUpdated; }
    public void setCurrentState(GameState currentState) { this.currentState = currentState; }
    
    // Other methods to update the game state...
    public void spinCompass() {
        // Spin the compass...
    	compass.spin();
    	// In phase 2, when the magic door was opened, if hitting ghost field, ghost moves
    	if (isGhostActivated) {
        	if (compass.getFieldType() == FieldType.GHOST) {
        		// If ghost is not found, moving 2 tokens of the same type
    	        if (!isGhostFound) {
    				Random random = new Random();
    				int randomIndex = random.nextInt(adjacentPairs.size());
    				Pair chosenPair = adjacentPairs.get(randomIndex);
    				int row1 = chosenPair.getRow1();
    				int col1 = chosenPair.getCol1();
    				int row2 = chosenPair.getRow2();
    				int col2 = chosenPair.getCol2();

    	        	board.swapGridCells(row1, col1, row2, col2);
    	        }
    	        // If ghost is found, moving ghost token with 1 arbitrary adjacent token
    	        else {
    				swapGhost(ghostX, ghostY);
    	        }
        	}
    	}
        currentState = GameState.UPDATE_CLOCK;
    }

    public void updateClock() {
        // Update the clock...
    	clock.increaseTime(compass.getFieldType());
        currentState = GameState.UPDATE_PLAYER_ACTIONS;
    }

    public void updatePlayerActions() {
        // Update the number of actions for the current player...
    	// Get the current player
        Player currentPlayer = listOfPlayers[currentPlayerIndex];
        // Update the number of actions for the current player
        currentPlayer.setNumberOfActions(compass.getNumberOfAction());
        currentState = GameState.PERFORM_PLAYER_ACTION; 
    }
   
    public void performPlayerAction(ActionType action, DirectionType direction) {
        // Perform the chosen action for the current player...
    	Player currentPlayer = listOfPlayers[currentPlayerIndex];
    	// Phase 1
		if (!isGhostActivated) {
			handlePhaseOne(currentPlayer, action, direction);
		}
		// Phase 2
		else {
			handlePhaseTwo(currentPlayer, action, direction);
			scanForWin();
		}
    	currentState = GameState.PERFORM_PLAYER_ACTION;
    }

    public void nextPlayer() {
        // Move to the next player...
        currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
        currentState = GameState.CHECK_GAME_OVER;
    }

    public void checkGameOver() {
        // Check if the game is over...
    	// Phase 1
		if (!isGhostActivated) {
			if (clock.getTime() == 12) {
	            currentState = GameState.LOSE;
	        } else {
	            currentState = GameState.SPIN_COMPASS;
	        }
		}
		// Phase 2
		else {
			if (win) {
	        	currentState = GameState.WIN;
	        }else if (clock.getTime() == 12 && !win) {
	            currentState = GameState.LOSE;
	        }else {
	            currentState = GameState.SPIN_COMPASS;
	        }
		}
    }
}
