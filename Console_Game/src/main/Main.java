package main;
import java.util.*;
public class Main {
	private static int numberOfPlayers = 0;
	private static Player[] listOfPlayers;
	static Scanner scanner = new Scanner(System.in);
	
	public static void settingMenu() {
		System.out.print("Number of player: ");
		numberOfPlayers = scanner.nextInt();
		scanner.nextLine();
		
		listOfPlayers = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			listOfPlayers[i] = new Player();
		}
		
		for (int i = 0; i < numberOfPlayers; i++) {
			System.out.println("Player "+(i+1)+" turn: ");
			String character = null;
//			do {
			// Choose character
			System.out.print("Choose character (Rabbit or Mouse): ");
			character = scanner.nextLine();
			listOfPlayers[i].setCharacter(character);
			
			// Choose starting position
			System.out.print("Choose staring position: ");
			listOfPlayers[i].setX(scanner.nextInt());
			listOfPlayers[i].setY(scanner.nextInt());
			scanner.nextLine();
				
//			}while(character.equalsIgnoreCase("Rabbit") || character.equalsIgnoreCase("Mouse"));
		}
		
		for (int i = 0; i < numberOfPlayers; i++) {
			listOfPlayers[i].print();
		}
	}
	public static void setup() {
		Board.setSize(5);
		Board.init();
		Board.print();
		
		System.out.println("Current time: "+Clock.getTime());
		System.out.println();
	}
	public static boolean checkPlayerPositionRemain(int beforeX, int beforeY, int currentX, int currentY) {
		if (beforeX == currentX && beforeY == currentY) return true;
		else return false;
	}
	
	public static void play() {
//		while(true) {
//			if (Clock.getTime() == 12) break;
			for (int i = 0; i < numberOfPlayers; i++) {
				System.out.println("Player "+(i+1)+" turn: ");
				Compass.spin();
				Compass.updateClock();
				System.out.println("Current time: "+Clock.getTime());
				listOfPlayers[i].setNumberOfAction();
				System.out.println("Field: "+Compass.getFieldType());
				System.out.println("Player "+(i+1)+" gains: "+listOfPlayers[i].getNumberOfAction()+" actions.");
				for (int j = 0; j < listOfPlayers[i].getNumberOfAction(); j++) {
//				for (int j = 0; j < 4; j++) {
					String action;
					System.out.println("Choose action type(Move/ View Curtain): ");
					action = scanner.nextLine();
					int posXbeforeAction = listOfPlayers[i].getX();
					int posYbeforeAction = listOfPlayers[i].getY();
					if (action.equalsIgnoreCase("Move up")) {
						listOfPlayers[i].moveUp();
						if (checkPlayerPositionRemain(posXbeforeAction,posYbeforeAction,listOfPlayers[i].getX(),listOfPlayers[i].getY())) j--;
					}
					else if (action.equalsIgnoreCase("Move down")) {
						listOfPlayers[i].moveDown();
						if (checkPlayerPositionRemain(posXbeforeAction,posYbeforeAction,listOfPlayers[i].getX(),listOfPlayers[i].getY())) j--;
					}
					else if (action.equalsIgnoreCase("Move left")) {
						listOfPlayers[i].moveLeft();
						if (checkPlayerPositionRemain(posXbeforeAction,posYbeforeAction,listOfPlayers[i].getX(),listOfPlayers[i].getY())) j--;
					}
					else if (action.equalsIgnoreCase("Move right")) {
						listOfPlayers[i].moveRight();
						if (checkPlayerPositionRemain(posXbeforeAction,posYbeforeAction,listOfPlayers[i].getX(),listOfPlayers[i].getY())) j--;
					}
					else if (action.equalsIgnoreCase("View curtain up")) {
						posXbeforeAction--;
						listOfPlayers[i].setX(posXbeforeAction);
						listOfPlayers[i].setCurrentPosition();
//						System.out.println(listOfPlayers[i].getCurrentPosition().getType());
						
						listOfPlayers[i].viewCurtain();
						
						posXbeforeAction++;
						listOfPlayers[i].setX(posXbeforeAction);
						listOfPlayers[i].setCurrentPosition();
					}
					else if (action.equalsIgnoreCase("View curtain down")) {
						posXbeforeAction++;
						listOfPlayers[i].setX(posXbeforeAction);
						listOfPlayers[i].setCurrentPosition();
//						System.out.println(listOfPlayers[i].getCurrentPosition().getType());
						
						listOfPlayers[i].viewCurtain();
						
						posXbeforeAction--;
						listOfPlayers[i].setX(posXbeforeAction);
						listOfPlayers[i].setCurrentPosition();
					}
					else if (action.equalsIgnoreCase("View curtain left")) {
						posYbeforeAction--;
						listOfPlayers[i].setY(posYbeforeAction);
						listOfPlayers[i].setCurrentPosition();
//						System.out.println(listOfPlayers[i].getCurrentPosition().getType());
						
						listOfPlayers[i].viewCurtain();
						
						posYbeforeAction++;
						listOfPlayers[i].setY(posYbeforeAction);
						listOfPlayers[i].setCurrentPosition();
					}
					else if (action.equalsIgnoreCase("View curtain right")) {
						posYbeforeAction++;
						listOfPlayers[i].setY(posYbeforeAction);
						listOfPlayers[i].setCurrentPosition();
//						System.out.println(listOfPlayers[i].getCurrentPosition().getType());
						
						listOfPlayers[i].viewCurtain();
						
						posYbeforeAction--;
						listOfPlayers[i].setY(posYbeforeAction);
						listOfPlayers[i].setCurrentPosition();
					}
					
					System.out.println("Player's current position: "+listOfPlayers[i].getX()+" "+listOfPlayers[i].getY());
					Board.print();
				}
				
				System.out.println();
			}
			
//		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		settingMenu();
		setup();
		play();
		
		
//		final Player player1 = new Player();
////		final Clock clock = new Clock();
////		final Compass compass =  new Compass();
//
//		Board.setSize(5);
//		Board.init();
////		compass.setClock(clock);
//		
//		
//		player1.setCharacter("Rabbit");
////		player1.setX(0);
////		player1.setY(2);
////		player1.setCurrentPosition();
//		
//		Board.print();
//		System.out.println("Board.wallSize = " + Board.wallSize);
//		System.out.println("Board.tokenSize = " + Board.tokenSize);
////		player1.viewToken();
//
//		System.out.println();
//		
////		System.out.println(Ghost.getX());
////		System.out.println(Ghost.getY());
////		boolean flag = true;
////		while(flag) {
////			for (int i = 0; i < Board.size; i++) {
////	    		for (int j = 0; j < Board.size; j++) {
////	    			if (i == Board.size-1 && j == Board.size-1 && !GridCell.isGhostFound) {
////	    				Board.gridCells[Board.size-1][Board.size-1].setType(Type.GHOST); 
////	    				break;
////	    			}
////    				player1.setX(i);
////    				player1.setY(j);
////    				player1.setCurrentPosition();
////    				player1.viewToken();
////					System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
////    				System.out.println("Ghost is found: "+GridCell.isGhostFound);
////    				if (GridCell.isGhostFound) {
////    					Ghost.updateGhost(player1.getX(), player1.getY());
////    					System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
////    					flag = false;
////    					break;
////    				}
////    			}
////	    		System.out.println();    			
////	    		
////	    		if(!flag) break;
////	    	}
////		}
//		player1.setX(0);
//		player1.setY(0);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//		
//		player1.setX(0);
//		player1.setY(2);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());		
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//		
//		player1.setX(0);
//		player1.setY(4);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//		
//		player1.setX(2);
//		player1.setY(0);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//		
//		player1.setX(2);
//		player1.setY(2);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//		
//		player1.setX(2);
//		player1.setY(4);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//
//		player1.setX(4);
//		player1.setY(0);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//
//		player1.setX(4);
//		player1.setY(2);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println();
//
//		player1.setX(4);
//		player1.setY(4);
//		player1.setCurrentPosition();
//		player1.viewToken();
//		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
//			
//		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//		if (GridCell.getIsGhostFound()) {
//			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
//				Ghost.updateGhost(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
//				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
//			}
//		}
//		System.out.println(Board.getTokenSize());
//		System.out.println(Board.checkGhostPosition());
//		System.out.println();
//		
////		GridCell currentGridcell = board.getCurrentGridCells(0, 0);
////		GridCell otherGridcell = board.getCurrentGridCells(0, 2);
////	
////		System.out.println();
////		System.out.println();
////		GridCell.swapToken(currentGridcell,otherGridcell);
//		
//		System.out.println();
//		Board.print();
////		
////		Compass.spin();
////		Compass.updateClock();
////		System.out.println("Field: "+Compass.getFieldType());
////		System.out.println("Action: "+Compass.getNumberOfAction());
////		if(Compass.checkGhostField()) {
////			Compass.moveGhost();
////		}
//		
////		player1.setNumberOfAction();
////		System.out.println("Action: "+player1.getNumberOfAction());
////		player1.moveUp();
////		player1.print();
//		
//		System.out.println();
//		
//		player1.setX(0);
//		player1.setY(1);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(0);
//		player1.setY(3);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(1);
//		player1.setY(0);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(1);
//		player1.setY(2);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		
//		player1.setX(1);
//		player1.setY(4);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(2);
//		player1.setY(1);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(2);
//		player1.setY(3);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(3);
//		player1.setY(0);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(3);
//		player1.setY(2);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(3);
//		player1.setY(4);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(4);
//		player1.setY(1);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		player1.setX(4);
//		player1.setY(3);
//		player1.setCurrentPosition();
//		player1.viewCurtain();
//		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
//		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//		if (GridCell.getIsMagicDoorAdded()) {
//			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
//				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
//				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
//			}
//		}
//		System.out.println("Board.wallSize = " + Board.getWallSize());
//		System.out.println();
//		
//		Board.print();
		
		
	}
}
