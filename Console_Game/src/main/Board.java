package main;

import java.util.Random;

public class Board {
	
	private static final Random random = new Random();
//	private static int ghostX;
//	private static int ghostY;
	
	protected static int size;
	protected static GridCell[][] gridCells;
	protected static int tokenSize;
	protected static int wallSize;
	
//	Board(int size) {
//		Board.size = size;
//	}
	
	public static void init() {
		gridCells = new GridCell[size][size];
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			if (i % 2 == 0 && j % 2 == 0) {
    				Type randomType = GridCell.tokenTypes[random.nextInt(GridCell.tokenTypes.length)];
    				gridCells[i][j] = new GridCell(randomType);
    				tokenSize++;
    			} else if (i % 2 != 0 && j % 2 != 0) {
    				gridCells[i][j] = new GridCell();
    			} else {
    				gridCells[i][j] = new GridCell(Type.CURTAIN_WALL);
    				wallSize++;
    			}
    		}
    	}
	}
	
	public static int getSize() { return size; }
	public static GridCell getCurrentGridCells(int currentX, int currentY) { return gridCells[currentX][currentY]; }
	public static GridCell getGhostCell() { return gridCells[Ghost.getX()][Ghost.getY()]; }
	public static GridCell[][] getGridCells() { return gridCells; }
	public static GridCell getAdjacentCellUp() { 
		int temp = Ghost.getX();
		return gridCells[temp++][Ghost.getY()]; 
	}
	public static int getWallSize() { return Board.wallSize; }
	public static int getTokenSize() { return Board.tokenSize; }
	
	public static void setSize(int size) { Board.size = size; }
	
	public static boolean checkGhostPosition() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(gridCells[i][j].getType() == Type.GHOST) 
				{
					return true;
				}
				
			}
		}
		return false;
	}


//	public static int getGhostX() { return ghostX; }
//	public static int getGhostY() { return ghostY; }
	
	public static void print() {
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			System.out.print(gridCells[i][j].getType() + "           ");
    		}
    		System.out.println();
    	}
	}
	
}
