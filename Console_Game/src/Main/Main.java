package Main;


public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Board board = new Board(5);
		board.init();
		Player player1 = new Player();
		player1.setCharacter("Rabbit");
		player1.setX(0);
		player1.setY(2);
		player1.setCurrentPosition();
		
		board.print();
		
		player1.viewToken(player1.getX(),player1.getY());
		
//		GridCell currentGridcell = board.getCurrentGridCells(0, 0);
//		GridCell otherGridcell = board.getCurrentGridCells(0, 2);
//	
//		System.out.println();
//		System.out.println();
//		GridCell.swapToken(currentGridcell,otherGridcell);
		
		System.out.println();
		board.print();
		
		
		Clock clock = new Clock();
		Compass compass =  new Compass();
		compass.setClock(clock);
		compass.spin();
		compass.updateClock();
		System.out.println("Field: "+compass.getFieldType());
		System.out.println("Action: "+compass.getNumberOfAction());
		if(compass.checkGhostField()) {
			compass.moveGhost();
		}
		
//		player1.setNumberOfAction();
//		System.out.println("Action: "+player1.getNumberOfAction());
//		player1.moveUp();
//		player1.print();
		
	}
}
