package main;
import java.util.*;
public class Main {

	private static int boardSize = 5;
	private static Board board = new Board(boardSize);
	
	private static Clock clock = new Clock();
	
	private static Compass compass = new Compass();
	
	private static boolean isMagicDoorFound = false;
	private static boolean isGhostActivated = false;
	
	private static int numberOfPlayers = 0;
	private static int numberOfRabbits = 0;
	private static int numberOfMice    = 0;
	private static Player[] listOfPlayers;
	
	public static Scanner scanner = new Scanner(System.in);
	
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
		// Print board and starting time
		board.print();
		System.out.println("\nStaring time: "+clock.getTime());
		System.out.println();
	}
	public static void play() {
		while(true) {
			if (clock.getTime() == 12) break;
			for (int i = 0; i < numberOfPlayers; i++) {
				/*
				* PHASE 1: FIND AND OPEN MAGIC DOOR
				* 
				* Players take turns spinning the compass,
				* performing actions to VIEW CURTAINS or MOVE or OPEN MAGIC DOOR
				* 
				*/
				if (!isGhostActivated) {
					System.out.println("\n#### Player "+(i+1)+": "+listOfPlayers[i].getCharacter()+"'s turn ####");
					System.out.println( "\n Current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
					System.out.println("Compass is spinning...");
					// Spin the compass
					compass.spin();
					System.out.println("### Compass ###");
					System.out.println("Compass field: "+compass.getFieldType());
					// Update time
					clock.increaseTime(compass.getFieldType());
					System.out.println("Current time: "+clock.getTime());
					// Player gains number of actions from spinning the compass
					listOfPlayers[i].setNumberOfAction(compass.getNumberOfAction());
					System.out.println("Player "+(i+1)+" gains "+listOfPlayers[i].getNumberOfAction()+" action(s).");
					
					// Each player plays their turn with the corresponding number of actions
					for (int j = 0; j < listOfPlayers[i].getNumberOfAction(); j++) {
						// If magicDoor has not been found yet, there are 2 types of action: MOVE/ VIEW CURTAIN
						if (!isMagicDoorFound) {
							ActionType actionType = null;
							DirectionType directionType = null;
							while (true) {
								try {
									System.out.print( "\n" + "Player's number of action: " + (listOfPlayers[i].getNumberOfAction() - j));
									// Choose action: MOVE or VIEW CURTAIN
									System.out.print( "\n" + "Choose action type(MOVE/ VIEW_CURTAIN/ NONE): ");
									String actionInput = scanner.nextLine().trim().toUpperCase();
//									System.out.println("Player choose action: " + actionInput);
									
									if (actionInput.equals("MOVE")) {
										 actionType = ActionType.MOVE;
									} else if (actionInput.equals("VIEW CURTAIN")) {
										actionType = ActionType.VIEW_CURTAIN;
									// if player chooses action as NONE, nothing happens
									} else if (actionInput.equals("NONE")) {
										actionType = ActionType.NONE;
										break;
									} else {
										throw new IllegalArgumentException("Invalid action input. Please enter MOVE, VIEW_CURTAIN, or NONE");
									}
									
									// Choose direction
									System.out.print("Choose direction (UP/ DOWN/ LEFT/ RIGHT): ");
									String directionInput = scanner.nextLine().trim().toUpperCase();
//									System.out.println("Player choose direction: " + directionInput);
									
									switch (directionInput) {
									case "UP":
										directionType = DirectionType.UP;
										break;
									case "DOWN":
										directionType = DirectionType.DOWN;
										break;
									case "LEFT":
										directionType = DirectionType.LEFT;
										break;
									case "RIGHT":
										directionType = DirectionType.RIGHT;
										break;
									default:
										throw new IllegalArgumentException("Invalid direction input. Please enter UP, DOWN, LEFT, or RIGHT.");
									}
									
							        // If both actionType and directionType are valid, break the loop
							        if (actionType != null && directionType != null) {
							            break;
							        }
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
							}
							
							if (actionType == ActionType.MOVE) {
								System.out.println("Player's moving...");
								int tempX = listOfPlayers[i].getX();
								int tempY = listOfPlayers[i].getY();
								int newX = listOfPlayers[i].getX();
								int newY = listOfPlayers[i].getY();
//								System.out.println("Before: "+newX+" "+newY);
//								System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
								switch (directionType) {
								case UP:
									newX -= 1;
//									System.out.println("Up "+newX+" "+newY);
									break;
								case DOWN:
									newX += 1;
//									System.out.println("Down "+newX+" "+newY);
									break;
								case LEFT:
									newY -= 1;
//									System.out.println("Left "+newX+" "+newY);
									break;
								case RIGHT:
									newY += 1;
//									System.out.println("Right "+newX+" "+newY);
									break;
								default:
									break;
								}
								// Check if adjacent cell is within bounds
								if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
									GridCell adjacentCell = board.getGridCellAt(newX,newY);
									listOfPlayers[i].move(directionType,adjacentCell);
								} else {
									System.err.println("\n" + "MOVE OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
								}
								if (listOfPlayers[i].getX() == tempX && listOfPlayers[i].getY() == tempY) {
									j--;
								}
							} 
							else if (actionType == ActionType.VIEW_CURTAIN) {
								System.out.println("Player's viewing curtain...");
								int newX = listOfPlayers[i].getX();
								int newY = listOfPlayers[i].getY();
//								System.out.println("Before: "+newX+" "+newY);
//								System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
								switch (directionType) {
								case UP:
									newX -= 1;
//									System.out.println("Up "+newX+" "+newY);
									break;
								case DOWN:
									newX += 1;
//									System.out.println("Down "+newX+" "+newY);
									break;
								case LEFT:
									newY -= 1;
//									System.out.println("Left "+newX+" "+newY);
									break;
								case RIGHT:
									newY += 1;
//									System.out.println("Right "+newX+" "+newY);
									break;
								default:
									break;
								}
								// Check if adjacent cell is within bounds
								if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
									listOfPlayers[i].viewCurtain(board.getGridCellAt(newX, newY), newX, newY);
									// If magic door is found, create MagicDoor instance
					            	if (board.getGridCellAt(newX, newY).getCellType() == CellType.MAGIC_DOOR_WALL)
					            	{
					            		System.err.println("Magic door is found, needs at least 1 player on each side and use 1 action to open!");
					            		isMagicDoorFound = true;
					            	}
								} else {
									System.err.println( "\n" + "VIEW CURTAIN OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
									j--;
								}
							}
							else {
								System.out.println("Player choose to do nothing.");
							}
							System.out.println( "\n" + "Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY()+"\n");
							board.print();
						}
						// If magic door is found, add one more type of action: OPEN MAGIC DOOR
						else {
							ActionType actionType = null;
							DirectionType directionType = null;
							while (true) {
							    try {
							        System.out.print("\nPlayer's number of action: " + (listOfPlayers[i].getNumberOfAction() - j));
							        // Choose action: Move or View curtain
							        System.out.print("\nChoose action type(MOVE/ VIEW_CURTAIN/ OPEN_MAGIC_DOOR/ NONE): ");
							        String actionInput = scanner.nextLine().trim().toUpperCase();
							        System.out.println("Player choose action: " + actionInput);
							        
							        if (actionInput.equals("MOVE")) {
							            actionType = ActionType.MOVE;
							        } else if (actionInput.equals("VIEW CURTAIN")) {
							            actionType = ActionType.VIEW_CURTAIN;
							        } else if (actionInput.equals("NONE")) {
							            actionType = ActionType.NONE;
							            break;  // Skip the rest of the loop if ActionType is NONE
							        } else if (actionInput.equals("OPEN MAGIC DOOR")) {
							            actionType = ActionType.OPEN_MAGIC_DOOR;
							        } else {
							            throw new IllegalArgumentException("Invalid action input. Please enter MOVE, VIEW_CURTAIN, OPEN_MAGIC_DOOR, or NONE.");
							        }

							        // Choose direction
							        System.out.print("Choose direction (UP/ DOWN/ LEFT/ RIGHT): ");
							        String directionInput = scanner.nextLine().trim().toUpperCase();
							        System.out.println("Player choose direction: " + directionInput);
							        
							        if (directionInput.equals("UP")) {
							            directionType = DirectionType.UP;                                    
							        } else if (directionInput.equals("DOWN")) {
							            directionType = DirectionType.DOWN;
							        } else if (directionInput.equals("LEFT")) {
							            directionType = DirectionType.LEFT;
							        } else if (directionInput.equals("RIGHT")) {
							            directionType = DirectionType.RIGHT;
							        } else {
							            throw new IllegalArgumentException("Invalid direction input. Please enter UP, DOWN, LEFT, or RIGHT.");
							        }
							        
							        // If both actionType and directionType are valid, break the loop
							        if (actionType != null && directionType != null) {
							            break;
							        }
							    } catch (Exception e) {
							        System.err.println(e.getMessage());
							    }
							}
							
							if (actionType == ActionType.MOVE) {
								System.out.println("Player's moving...");
								int tempX = listOfPlayers[i].getX();
								int tempY = listOfPlayers[i].getY();
								int newX = listOfPlayers[i].getX();
								int newY = listOfPlayers[i].getY();
//								System.out.println("Before: "+newX+" "+newY);
//								System.out.println("Player: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
								switch (directionType) {
								case UP:
									newX -= 1;
//									System.out.println("Up "+newX+" "+newY);
									break;
								case DOWN:
									newX += 1;
//									System.out.println("Down "+newX+" "+newY);
									break;
								case LEFT:
									newY -= 1;
//									System.out.println("Left "+newX+" "+newY);
									break;
								case RIGHT:
									newY += 1;
//									System.out.println("Right "+newX+" "+newY);
									break;
								default:
									break;
								}
								// Check if adjacent cell is within bounds
								if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
									GridCell adjacentCell = board.getGridCellAt(newX,newY);
									listOfPlayers[i].move(directionType,adjacentCell);
								} else {
									System.err.println("\n" + "MOVE OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
								}
								if (listOfPlayers[i].getX() == tempX && listOfPlayers[i].getY() == tempY) {
									j--;
								}
							} 
							else if (actionType == ActionType.VIEW_CURTAIN) {
								System.out.println("Player's viewing curtain...");
								int newX = listOfPlayers[i].getX();
								int newY = listOfPlayers[i].getY();
								System.out.println("Before: "+newX+" "+newY);
								System.out.println("Player: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
								switch (directionType) {
								case UP:
									newX -= 1;
//									System.out.println("Up "+newX+" "+newY);
									break;
								case DOWN:
									newX += 1;
//									System.out.println("Down "+newX+" "+newY);
									break;
								case LEFT:
									newY -= 1;
//									System.out.println("Left "+newX+" "+newY);
									break;
								case RIGHT:
									newY += 1;
//									System.out.println("Right "+newX+" "+newY);
									break;
								default:
									break;
								}
								// Check if adjacent cell is within bounds
								if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
									listOfPlayers[i].viewCurtain(board.getGridCellAt(newX, newY), newX, newY);
								} else {
									System.err.println( "\n" + "VIEW CURTAIN OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
									j--;
								}
							}
							else if (actionType == ActionType.OPEN_MAGIC_DOOR) {
								System.out.println("Player's opening magic door...");
								int newX = listOfPlayers[i].getX();
								int newY = listOfPlayers[i].getY();
//								System.out.println("Before: "+newX+" "+newY);
//								System.out.println("Player: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
								switch (directionType) {
								case UP:
									newX -= 1;
//									System.out.println("Up "+newX+" "+newY);
									break;
								case DOWN:
									newX += 1;
//									System.out.println("Down "+newX+" "+newY);
									break;
								case LEFT:
									newY -= 1;
//									System.out.println("Left "+newX+" "+newY);
									break;
								case RIGHT:
									newY += 1;
//									System.out.println("Right "+newX+" "+newY);
									break;
								}
								// Check if adjacent cell is within bounds
								if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
									int otherSideX = newX;
									int otherSideY = newY;
									switch (directionType) {
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
										listOfPlayers[i].openMagicDoor(board.getGridCellAt(newX, newY));
										// Now the Magic Door is opened, starting phase 2 
										isGhostActivated = true;
									} else {
										System.err.println("THERE IS NO PLAYER ON THE OTHER SIDE OF THE BOARD. PLEASE TRY AGAIN.");
									}
								} else {
									System.err.println( "\n" + "OPEN MAGIC DOOR OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
									j--;
								}
							}
							else {
								System.out.println("Player choose to do nothing.");
							}
							System.out.println( "\n" + "Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY()+"\n");
							board.print();
						}
					}
					System.out.println();
				}
				/*
				 * PHASE 2: FIND GHOST
				 * 
				 * Players continue to take turns spinning the compass, one more action is added: view the token.
				 * The game automatically performs one more action: swap token when hitting ghost's field
				 * 
				 */
				else {
					// In this phase, the magic door is now open wall, no need to consider it anymore
					System.out.println("Starting phase 2....Now players can view tokens!");
					clock.setTime(12);
					/*
					 * ...
					 */
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		settingMenu();
		setup();
		play();
	}
}
