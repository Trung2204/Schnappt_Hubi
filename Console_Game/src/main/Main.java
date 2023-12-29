package main;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final Player player1 = new Player();
//		final Clock clock = new Clock();
//		final Compass compass =  new Compass();

		Board.setSize(5);
		Board.init();
//		compass.setClock(clock);
		
		
		player1.setCharacter("Rabbit");
//		player1.setX(0);
//		player1.setY(2);
//		player1.setCurrentPosition();
		
		Board.print();
		System.out.println("Board.wallSize = " + Board.wallSize);
		System.out.println("Board.tokenSize = " + Board.tokenSize);
//		player1.viewToken();

		System.out.println();
		
//		System.out.println(Ghost.getX());
//		System.out.println(Ghost.getY());
//		boolean flag = true;
//		while(flag) {
//			for (int i = 0; i < Board.size; i++) {
//	    		for (int j = 0; j < Board.size; j++) {
//	    			if (i == Board.size-1 && j == Board.size-1 && !GridCell.isGhostFound) {
//	    				Board.gridCells[Board.size-1][Board.size-1].setType(Type.GHOST); 
//	    				break;
//	    			}
//    				player1.setX(i);
//    				player1.setY(j);
//    				player1.setCurrentPosition();
//    				player1.viewToken();
//					System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//    				System.out.println("Ghost is found: "+GridCell.isGhostFound);
//    				if (GridCell.isGhostFound) {
//    					Ghost.updateGhost(player1.getX(), player1.getY());
//    					System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
//    					flag = false;
//    					break;
//    				}
//    			}
//	    		System.out.println();    			
//	    		
//	    		if(!flag) break;
//	    	}
//		}
		player1.setX(0);
		player1.setY(0);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();
		
		player1.setX(0);
		player1.setY(2);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());		
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();
		
		player1.setX(0);
		player1.setY(4);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();
		
		player1.setX(2);
		player1.setY(0);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();
		
		player1.setX(2);
		player1.setY(2);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();
		
		player1.setX(2);
		player1.setY(4);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();

		player1.setX(4);
		player1.setY(0);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();

		player1.setX(4);
		player1.setY(2);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println();

		player1.setX(4);
		player1.setY(4);
		player1.setCurrentPosition();
		player1.viewToken();
		System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
		System.out.println("Ghost is found: "+GridCell.getIsGhostFound());
			
		System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
		if (GridCell.getIsGhostFound()) {
			if (Ghost.getX() == -1 && Ghost.getY() == -1) {
				Ghost.updateGhost(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+", " +player1.getY());
				System.out.println("Ghost(x,y) = " + Ghost.getX() + ", " + Ghost.getY());
			}
		}
		System.out.println(Board.getTokenSize());
		System.out.println(Board.checkGhostPosition());
		System.out.println();
		
//		GridCell currentGridcell = board.getCurrentGridCells(0, 0);
//		GridCell otherGridcell = board.getCurrentGridCells(0, 2);
//	
//		System.out.println();
//		System.out.println();
//		GridCell.swapToken(currentGridcell,otherGridcell);
		
		System.out.println();
		Board.print();
//		
//		Compass.spin();
//		Compass.updateClock();
//		System.out.println("Field: "+Compass.getFieldType());
//		System.out.println("Action: "+Compass.getNumberOfAction());
//		if(Compass.checkGhostField()) {
//			Compass.moveGhost();
//		}
		
//		player1.setNumberOfAction();
//		System.out.println("Action: "+player1.getNumberOfAction());
//		player1.moveUp();
//		player1.print();
		
		System.out.println();
		
		player1.setX(0);
		player1.setY(1);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(0);
		player1.setY(3);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(1);
		player1.setY(0);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(1);
		player1.setY(2);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		
		player1.setX(1);
		player1.setY(4);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(2);
		player1.setY(1);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(2);
		player1.setY(3);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(3);
		player1.setY(0);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(3);
		player1.setY(2);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(3);
		player1.setY(4);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(4);
		player1.setY(1);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		player1.setX(4);
		player1.setY(3);
		player1.setCurrentPosition();
		player1.viewCurtain();
		System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
		System.out.println("MAGIC_DOOR_WALL is found: " + GridCell.getIsMagicDoorAdded());
		System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
		if (GridCell.getIsMagicDoorAdded()) {
			if (MagicDoor.getX() == -1 && MagicDoor.getY() == -1) {
				MagicDoor.updateMagicDoor(player1.getX(), player1.getY());
				System.out.println("Player (x,y) = "+player1.getX()+" " +player1.getY());
				System.out.println("MAGIC_DOOR_WALL(x,y) = " + MagicDoor.getX() + ", " + MagicDoor.getY());
			}
		}
		System.out.println("Board.wallSize = " + Board.getWallSize());
		System.out.println();
		
		Board.print();
	}
}
