package main;
import java.util.*;
public class Main {
	/*
	 * Test github adasda
	 * adasd
	 * aasdasdas
	 * dasdas
	 * 
	 */
	private static int boardSize = 5;
	private static Board board = new Board(boardSize);
	
	private static Clock clock = new Clock();
	
	private static Compass compass = new Compass();
	
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
//		while(true) {
//			if (Clock.getTime() == 12) break;
			for (int i = 0; i < numberOfPlayers; i++) {
				System.out.print("\n#### Player "+(i+1)+"'s turn ####\n");
				System.out.println("Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
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
					// Choose action: Move or View curtain
					System.out.print("Choose action type(MOVE/ VIEW_CURTAIN): ");
					ActionType actionType;
					String actionInput = scanner.nextLine().toUpperCase();
					System.out.print("Choose direction (UP/ DOWN/ LEFT/ RIGHT): ");
					Direction direction;
					String directionInput = scanner.nextLine().toUpperCase();
					
					actionType = ActionType.valueOf(actionInput);
					direction = Direction.valueOf(directionInput);
					
					if (actionType == ActionType.MOVE) {
						int newX = listOfPlayers[i].getX();
						int newY = listOfPlayers[i].getY();
						System.out.println("Before: "+newX+" "+newY);
						System.out.println("PLayer: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
						switch (direction) {
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
			            	listOfPlayers[j].move(direction,adjacentCell);
			            } else {
			                System.out.println("Move out of bounds. Please try again.");
			            }
					} else {
						listOfPlayers[i].viewCurtain(actionType,direction);
					}
					
					System.out.println("Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
					board.print();
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
//		}
//		board.print();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		settingMenu();
		setup();
		play();
		
	}
}
