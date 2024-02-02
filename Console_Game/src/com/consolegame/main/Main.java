package com.consolegame.main;

import java.util.*;
import com.consolegame.board.Board;
import com.consolegame.board.GridCell;
import com.consolegame.helper.type.ActionType;
import com.consolegame.helper.type.CellType;
import com.consolegame.helper.type.DirectionType;
import com.consolegame.helper.type.FieldType;
import com.consolegame.player.Character;
import com.consolegame.player.Clock;
import com.consolegame.player.Compass;
import com.consolegame.player.Player;

public class Main {

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

	private static int boardSize = 5;
	private static Board board;
	private static Clock clock;
	private static Compass compass;
	
	private static int numberOfMice    = 0;
	private static Player[] listOfPlayers;
	private static int numberOfPlayers = 0;
	private static int numberOfRabbits = 0;
	
	private static boolean win = false;
	private static boolean isMagicDoorFound = false;
	private static boolean isGhostActivated = false;
	private static boolean isGhostFound = false;
	
	private static int ghostX = -1;
	private static int ghostY = -1;
	
	public static Scanner scanner = new Scanner(System.in);

	private static List<Pair> findAdjacentPairs() {

		// Iterate through the array
		for (int x = 0; x < boardSize; x = x + 2) {
			for (int y = 0; y < boardSize; y = y + 2) {
				// Check right neighbor
				int x1 = x + 2;
				int y1 = y + 2;
				CellType a = board.getGridCellAt(x,y).getCellType();

				// Check right neighbor
				if (x < boardSize && y1 < boardSize) {
					CellType b = board.getGridCellAt(x, y1).getCellType();
					if (a == b) {
						adjacentPairs.add(new Pair(x, y, x, y1));
					}
				}

				// Check bottom neighbor
				if (x1 < boardSize && y < boardSize) {
					CellType c = board.getGridCellAt(x1, y).getCellType();
					if (a == c) {
						adjacentPairs.add(new Pair(x, y, x1, y));
					}
				}
			}
		}

		return adjacentPairs;
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
					if ((x + 2) < boardSize) {
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
					if ((y + 2) < boardSize) {
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

	public static void settingMenu() {
		// Get number of players
		while (numberOfPlayers < 2 || numberOfPlayers > 4) {
			System.out.print("Number of player (2-4): ");
			try {
				numberOfPlayers = scanner.nextInt();
				scanner.nextLine(); //Consume newline left-over
				if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                    System.err.println("Invalid number of players. Please enter a number between 2 and 4.");
                }
			} catch (Exception e) {
                System.err.println("Invalid input. Please enter a number.");
                scanner.next(); // discard the invalid input
            }
		}
		
		// Create an array to store players
		listOfPlayers = new Player[numberOfPlayers];
		
		// Use a set to store used coordinates
        HashSet<String> usedCoordinates = new HashSet<>();
		
		for (int i = 0; i < numberOfPlayers; i++) {
			System.out.print("\n#### Player "+(i+1)+" chooses character ####\n");
			
			// Choose character
			Character character = null;
			while (character == null) {
				System.out.print("1)Choose character (RABBIT or MOUSE): ");
				String characterInput = scanner.nextLine().toUpperCase();
				try {
					character = Character.valueOf(characterInput);
					if ((character == Character.RABBIT && numberOfRabbits >= 2) || (character == Character.MOUSE && numberOfMice >= 2)) {
						System.err.println("Maximum number of " + character + "s reached. Please enter a different character.");
						character = null; // reset character to null to continue the loop
					} else {
			            // Update the count of each character type
			            if (character == Character.RABBIT)
			                numberOfRabbits++;
			            else if (character == Character.MOUSE)
			                numberOfMice++;
			        }
					// If this is the last player and there is no rabbit or mouse yet, force them to choose the missing character
			        if (i == numberOfPlayers - 1 && (numberOfRabbits == 0 || numberOfMice == 0)) {
			            System.err.println("There must be at least one rabbit and one mouse. Please choose the missing character.");
			            character = null; // reset character to null to continue the loop
			            // Reset the count of each character type
			            if (character == Character.RABBIT)
			                numberOfRabbits--;
			            else if (character == Character.MOUSE)
			                numberOfMice--;
			        }
				} catch (IllegalArgumentException e) {
	                System.err.println("Invalid character. Please enter RABBIT or MOUSE.");
				}
			}
			
			// Choose starting position
			System.out.println("2)Choose staring position:");
			int x = -1, y = -1;
			while (true) {
				try {
					System.out.print("Enter x coordinate: ");
					x = scanner.nextInt();

					System.out.print("Enter y coordinate: ");
					y = scanner.nextInt();
					scanner.nextLine(); //Consume newline left-over
					
					// Check if these coordinates are at the corners of the board
					if ((x == 0 || x == 4) && (y == 0 || y == 4)) {
						// Check if these coordinates have been used
						if (!usedCoordinates.contains(x + "," + y)) {
							// If not, add them to the set and break the loop
							usedCoordinates.add(x + "," + y);
							break;
						} else {
							// If yes, ask for the coordinates again
							System.err.println("These coordinates have been used. Please enter different coordinates.");
						}
					} else {
						System.err.println("Coordinates must be at the corners of the board (0,0 or 0,4 or 4,0 or 4,4). Please enter valid coordinates.");
					}
				} catch (Exception e) {
					System.err.println("Invalid input. Please enter a number.");
					scanner.next(); // discard the invalid input
				}
			}
			
			Player player = new Player(x,y,character);
            
            // Add the player to the array
            listOfPlayers[i] = player;
			System.out.println();
		}		
		// Display players' information
		System.out.println("Players' information:");
		for (int i = 0; i < numberOfPlayers; i++) {
			listOfPlayers[i].print();
		}
		System.out.println();
	}
	public static void setup() {
		// Initialize board with the numberOfRabbits and numberOfMice
		board = new Board(boardSize,numberOfRabbits,numberOfMice);
		clock = new Clock();
		compass = new Compass();
		// Print board and starting time
		board.print();
		System.out.println("\nStaring time: "+clock.getTime());
		System.out.println();
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
	/*
	* PHASE 1: FIND AND OPEN MAGIC DOOR
	* 
	* Players take turns spinning the compass,
	* performing actions to VIEW CURTAINS or MOVE or OPEN MAGIC DOOR
	* 
	*/
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
    /*
	 * PHASE 2: FIND GHOST
	 * 
	 * Players continue to take turns spinning the compass, one more action is added: view the token.
	 * The game automatically performs one more action: swap token when hitting ghost's field
	 * 
	 */
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
	    if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
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
		if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
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
		if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		settingMenu();
		setup();
		play();
	}
}