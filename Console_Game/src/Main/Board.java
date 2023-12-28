package Main;

import java.util.Random;

public class Board {
	
	private static Random random = new Random();
	private static int ghostX;
	private static int ghostY;
	protected static int size;
	protected static GridCell[][] gridCells;
	
	Board(int size) {
		Board.size = size;
	}
	
	public void init() {
		gridCells = new GridCell[size][size];
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			if (i % 2 == 0 && j % 2 == 0) {
    				Type randomType = GridCell.tokenTypes[random.nextInt(GridCell.tokenTypes.length)];
    				gridCells[i][j] = new GridCell(randomType);
    			} else if (i % 2 != 0 && j % 2 != 0) {
    				gridCells[i][j] = new GridCell();
    			} else {
    				gridCells[i][j] = new GridCell(Type.CURTAIN_WALL);
    			}
    		}
    	}
	}
	
	public int getSize() { return size; }
	public GridCell getCurrentGridCells(int currentX, int currentY) { return gridCells[currentX][currentY]; }
	

	public void findGhostPosition() {
		if (GridCell.isGhostFound) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if(gridCells[i][j].getType() == Type.GHOST) 
					{
						ghostX = i;
						ghostY = j;
					}
				}
			}
		}
	}
	public static GridCell getGhostCell() { return gridCells[ghostX][ghostY]; }
	public static GridCell getAdjacentCellUp() { return gridCells[ghostX++][ghostY]; }
	public static int getGhostX() { return ghostX; }
	public static int getGhostY() { return ghostY; }
	
	public void print() {
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			System.out.print(gridCells[i][j].getType() + "           ");
    		}
    		System.out.println();
    	}
	}
	
}
