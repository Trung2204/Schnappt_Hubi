package main;
import java.util.*;
public class Main {

	private static int boardSize = 5;
	private static Board board = new Board(boardSize);
	
	private static Clock clock = new Clock();
	
	private static Compass compass = new Compass();
	
	private static MagicDoor magicDoor;
	private static boolean isMagicDoorFound = false;
	
	private static int numberOfPlayers = 0;
	private static int numberOfRabbits = 0;
	private static int numberOfMice    = 0;
	private static Player[] listOfPlayers;
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static boolean isMagicDoorOpened(Player playerA, Player playerB, GridCell playerBPosition) {
		return true;
	}
	
	public static void settingMenu() {
		// Get number of players
		while (numberOfPlayers < 2 || numberOfPlayers > 4) {
			System.out.print("Number of player (2-4): ");
			try {
				numberOfPlayers = scanner.nextInt();
				scanner.nextLine(); //Consume newline left-over
				if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                    System.out.println("Invalid number of players. Please enter a number between 2 and 4.");
                }
			} catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
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
						System.out.println("Maximum number of " + character + "s reached. Please enter a different character.");
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
			            System.out.println("There must be at least one rabbit and one mouse. Please choose the missing character.");
			            character = null; // reset character to null to continue the loop
			            // Reset the count of each character type
			            if (character == Character.RABBIT)
			                numberOfRabbits--;
			            else if (character == Character.MOUSE)
			                numberOfMice--;
			        }
				} catch (IllegalArgumentException e) {
	                System.out.println("Invalid character. Please enter RABBIT or MOUSE.");
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
							System.out.println("These coordinates have been used. Please enter different coordinates.");
						}
					} else {
						System.out.println("Coordinates must be at the corners of the board (0,0 or 0,4 or 4,0 or 4,4). Please enter valid coordinates.");
					}
				} catch (Exception e) {
					System.out.println("Invalid input. Please enter a number.");
					scanner.next(); // discard the invalid input
				}
			}
			
			Player player = new Player(x,y,character);
            
            // Add the player to the array
            listOfPlayers[i] = player;
			System.out.println();
		}
		
		// Display players' information
		for (int i = 0; i < numberOfPlayers; i++) {
			listOfPlayers[i].print();
		}
		System.out.println();
	}
	public static void setup() {
		board.print();
		System.out.println("\nStaring time: "+clock.getTime());
		System.out.println();
	}
	public static void play() {
		while(true) {
			if (clock.getTime() == 12) break;
			for (int i = 0; i < numberOfPlayers; i++) {
				System.out.print("\n#### Player "+(i+1)+"'s turn ####\n");
				System.out.println( "\n" + "Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
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
					// If magicDoor has not been found yet, there are 2 types of action: move/ view curtain
					if (!isMagicDoorFound) {
						ActionType actionType;
						DirectionType directionType;

						System.out.print( "\n" + "Player's number of action: " + (listOfPlayers[i].getNumberOfAction() - j));
						// Choose action: Move or View curtain
						System.out.print( "\n" + "Choose action type(MOVE/ VIEW_CURTAIN): ");
						String actionInput = scanner.nextLine().toUpperCase();
						System.out.println("Player choose action: " + actionInput);
						// Choose direction
						System.out.print("Choose direction (UP/ DOWN/ LEFT/ RIGHT): ");
						String directionInput = scanner.nextLine().toUpperCase();
						System.out.println("Player choose direction: " + directionInput);
						
						// Switch actionType based on actionInput
						switch (actionInput) {
						case "MOVE": 
							actionType = ActionType.MOVE;	
							break;
						case "VIEW CURTAIN": 
							actionType = ActionType.VIEW_CURTAIN;
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + actionInput);
						}
						
						// Switch directionType based on directionInput
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
							throw new IllegalArgumentException("Unexpected value: " + directionInput);
						}
						
						if (actionType == ActionType.MOVE) {
							System.out.println("Player moves...");
							int tempX = listOfPlayers[i].getX();
							int tempY = listOfPlayers[i].getY();
							int newX = listOfPlayers[i].getX();
							int newY = listOfPlayers[i].getY();
							System.out.println("Before: "+newX+" "+newY);
							System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
							switch (directionType) {
							case UP:
								newX -= 1;
								System.out.println("Up "+newX+" "+newY);
								break;
							case DOWN:
								newX += 1;
								System.out.println("Down "+newX+" "+newY);
								break;
							case LEFT:
								newY -= 1;
								System.out.println("Left "+newX+" "+newY);
								break;
							case RIGHT:
								newY += 1;
								System.out.println("Right "+newX+" "+newY);
								break;
							}
							// Check if adjacent cell is within bounds
							if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
								GridCell adjacentCell = board.getGridCellAt(newX,newY);
								listOfPlayers[i].move(directionType,adjacentCell);
							} else {
								System.out.println("\n" + "MOVE OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
							}
							if (listOfPlayers[i].getX() == tempX && listOfPlayers[i].getY() == tempY) {
								j--;
							}
						} 
						else if (actionType == ActionType.VIEW_CURTAIN) {
							System.out.println("Player views curtain...");
							int newX = listOfPlayers[i].getX();
							int newY = listOfPlayers[i].getY();
							System.out.println("Before: "+newX+" "+newY);
							System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
							switch (directionType) {
							case UP:
								newX -= 1;
								System.out.println("Up "+newX+" "+newY);
								break;
							case DOWN:
								newX += 1;
								System.out.println("Down "+newX+" "+newY);
								break;
							case LEFT:
								newY -= 1;
								System.out.println("Left "+newX+" "+newY);
								break;
							case RIGHT:
								newY += 1;
								System.out.println("Right "+newX+" "+newY);
								break;
							}
							// Check if adjacent cell is within bounds
							if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
								listOfPlayers[i].viewCurtain(board.getGridCellAt(newX, newY), newX, newY);
								// If magic door is found, create MagicDoor instance
				            	if (board.getGridCellAt(newX, newY).getCellType() == CellType.MAGIC_DOOR_WALL)
				            	{
				            		System.out.println("Magic door is found, needs at least 1 player on each side and use 1 action to open!");
				            		magicDoor = new MagicDoor(newX,newY,directionType);
				            		isMagicDoorFound = true;
				            		magicDoor.print();
				            	}
							} else {
								System.out.println( "\n" + "VIEW CURTAIN OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
								j--;
							}
						}
						
						System.out.println( "\n" + "Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
						board.print();
					}
					// If magic door is found, add one more type of action: open magic door
					else {
						System.out.println("### Magic door is found, one more action is added! ###");
						ActionType actionType;
						DirectionType directionType;

						System.out.print( "\n" + "Player's number of action: " + (listOfPlayers[i].getNumberOfAction() - j));
						// Choose action: Move or View curtain
						System.out.print( "\n" + "Choose action type(MOVE/ VIEW_CURTAIN/ OPEN MAGIC DOOR): ");
						String actionInput = scanner.nextLine().toUpperCase();
						System.out.println("Player choose action: " + actionInput);
						// Choose direction
						System.out.print("Choose direction (UP/ DOWN/ LEFT/ RIGHT): ");
						String directionInput = scanner.nextLine().toUpperCase();
						System.out.println("Player choose direction: " + directionInput);
						
						// Switch actionType based on actionInput
						switch (actionInput) {
						case "MOVE": 
							actionType = ActionType.MOVE;	
							break;
						case "VIEW CURTAIN": 
							actionType = ActionType.VIEW_CURTAIN;
							break;
						case "OPEN MAGIC DOOR":
							actionType = ActionType.VIEW_CURTAIN;
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + actionInput);
						}
						
						// Switch directionType based on directionInput
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
							throw new IllegalArgumentException("Unexpected value: " + directionInput);
						}
						
						if (actionType == ActionType.MOVE) {
							System.out.println("Player moves...");
							int tempX = listOfPlayers[i].getX();
							int tempY = listOfPlayers[i].getY();
							int newX = listOfPlayers[i].getX();
							int newY = listOfPlayers[i].getY();
							System.out.println("Before: "+newX+" "+newY);
							System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
							switch (directionType) {
							case UP:
								newX -= 1;
								System.out.println("Up "+newX+" "+newY);
								break;
							case DOWN:
								newX += 1;
								System.out.println("Down "+newX+" "+newY);
								break;
							case LEFT:
								newY -= 1;
								System.out.println("Left "+newX+" "+newY);
								break;
							case RIGHT:
								newY += 1;
								System.out.println("Right "+newX+" "+newY);
								break;
							}
							// Check if adjacent cell is within bounds
							if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
								GridCell adjacentCell = board.getGridCellAt(newX,newY);
								listOfPlayers[i].move(directionType,adjacentCell);
							} else {
								System.out.println("\n" + "MOVE OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
							}
							if (listOfPlayers[i].getX() == tempX && listOfPlayers[i].getY() == tempY) {
								j--;
							}
						} 
						else if (actionType == ActionType.VIEW_CURTAIN) {
							System.out.println("Player views curtain...");
							int newX = listOfPlayers[i].getX();
							int newY = listOfPlayers[i].getY();
							System.out.println("Before: "+newX+" "+newY);
							System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
							switch (directionType) {
							case UP:
								newX -= 1;
								System.out.println("Up "+newX+" "+newY);
								break;
							case DOWN:
								newX += 1;
								System.out.println("Down "+newX+" "+newY);
								break;
							case LEFT:
								newY -= 1;
								System.out.println("Left "+newX+" "+newY);
								break;
							case RIGHT:
								newY += 1;
								System.out.println("Right "+newX+" "+newY);
								break;
							}
							// Check if adjacent cell is within bounds
							if (newX >= 0 && newX < boardSize && newY >= 0 && newY < boardSize) {
								listOfPlayers[i].viewCurtain(board.getGridCellAt(newX, newY), newX, newY);
							} else {
								System.out.println( "\n" + "VIEW CURTAIN OUT OF BOUNDS. PLEASE TRY AGAIN." + "\n");
								j--;
							}
						}
						else if (actionType == ActionType.OPEN_MAGIC_DOOR) {
							System.out.println("Player opens magic door...");
							/*
							 * ...
							 */
							listOfPlayers[i].openMagicDoor();
						}
						
						System.out.println( "\n" + "Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
						board.print();
					}
					
				}
				/*
				 * PHASE 2
				 */
				
				// If magic door is opened
				// check compass.
				if(magicDoor != null) {
					
				}
				System.out.println();
			}
			
//		// !!! For testing purpose: view all curtains and tokens
//		for (int i = 0; i < boardSize; i++) {
//			for (int j = 0; j < boardSize; j++) {
//				if (board.getGridCellAt(i, j).getCellType() == CellType.CURTAIN_WALL)
//					board.getGridCellAt(i, j).changeWall(i,j);
//				else if(board.getGridCellAt(i, j).getCellType() == CellType.CARROT_TOKEN ||
//						board.getGridCellAt(i, j).getCellType() == CellType.CHEESE_TOKEN)
//					board.getGridCellAt(i, j).changeToken(i,j);
//			}
		}
//		board.print();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		settingMenu();
		setup();
		play();
		
	}
}
