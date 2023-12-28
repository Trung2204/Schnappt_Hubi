package Main;

import java.util.Random;

public class Board {
	// Random number generator
	private static Random random = new Random();
	// Ghost position x
	private static int ghostX;
	// Ghost position y
	private static int ghostY;
	// Board size
	protected static int size;
	// Grid cells of the board
	protected static GridCell[][] gridCells;
	
	// Constructor
	Board(int size) {
		Board.size = size;
	}
	
	// Initialize the board
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
	// Get the size of the board
	public int getSize() { return size; }
	// Get the grid cells of the board with the given x and y
	public GridCell getCurrentGridCells(int currentX, int currentY) { return gridCells[currentX][currentY]; }
	
	// Find the position of the ghost
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

	// Get the GridCell of the position of the ghost in the board
	public static GridCell getGhostCell() { return gridCells[ghostX][ghostY]; }
	// Get the GridCell of the position that is above of the ghost in the board 
	public static GridCell getAdjacentCellUp() { return gridCells[ghostX++][ghostY]; }
	// Get ghost position x
	public static int getGhostX() { return ghostX; }
	// Get ghost position y
	public static int getGhostY() { return ghostY; }
	
	// Print the board
	public void print() {
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) {
    			System.out.print(gridCells[i][j].getType() + "           ");
    		}
    		System.out.println();
    	}
	}
	
}
