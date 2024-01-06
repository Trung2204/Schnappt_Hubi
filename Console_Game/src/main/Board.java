package main;

import java.util.*;

public class Board {
	private GridCell[][] gridCells;
	private static int magicDoorRow;
    private static int magicDoorCol;
    private static int ghostRow;
    private static int ghostCol;
	
	public Board(int size) {
		if (gridCells == null) {
            gridCells = new GridCell[size][size];
            initializeBoard();
            setSpecialCells();
        }
	}
	
	public static int getMagicDoorRow() { return magicDoorRow; }
	public static int getMagicDoorCol() { return magicDoorCol; }
	public static int getGhostRow() { return ghostRow; }
	public static int getGhostCol() { return ghostCol; }
	public GridCell getGridCellAt(int row, int col) {
		if (row >= 0 && row < gridCells.length && col >= 0 && col < gridCells[row].length) {
            return gridCells[row][col];
        } else {
            throw new IllegalArgumentException("Invalid position");
        }
	}
	public void print() {
		for (int i = 0; i < gridCells.length; i++) {
    		for (int j = 0; j < gridCells[i].length; j++) {
    			System.out.print(gridCells[i][j].getCellType() + "           ");
    		}
    		System.out.println();
    	}
		System.out.println("\nMagic door("+magicDoorRow+","+magicDoorCol+")");
		System.out.println("Ghost("+ghostRow+","+ghostCol+")");
	}
	
	private void initializeBoard() {
		Random random = new Random();
		for (int i = 0; i < gridCells.length; i++) {
    		for (int j = 0; j < gridCells[i].length; j++) {
    			// The squares can be moved in
    			if (i % 2 == 0 && j % 2 == 0) {
    				CellType cellType = random.nextBoolean() ? CellType.CARROT_TOKEN : CellType.CHEESE_TOKEN;
                    gridCells[i][j] = new GridCell(cellType);
    			} else if (i % 2 != 0 && j % 2 != 0) {
                    // Assign NONE_WALL to the cross between walls
                    gridCells[i][j] = new GridCell(CellType.NONE_WALL);
    			} else {
    				gridCells[i][j] = new GridCell(CellType.CURTAIN_WALL);
    			}
    		}
    	}
	}
	private void setSpecialCells() {
	    Random random = new Random();

	    // Pre-calculate the valid cells for MAGIC_DOOR_WALL and GHOST
	    List<int[]> validCellsMagicDoor = new ArrayList<>();
	    for (int i = 0; i < 5; i++) {
	        for (int j = 0; j < 5; j++) {
	            if (!(i % 2 != 0 && j % 2 != 0) && !(i % 2 == 0 && j % 2 == 0)) {
	            	validCellsMagicDoor.add(new int[]{i, j});
	            }
	        }
	    }
	    List<int[]> validCellsGhost = new ArrayList<>();
	    for (int i = 0; i < 5; i++) {
	        for (int j = 0; j < 5; j++) {
	            if (i % 2 == 0 && j % 2 == 0) {
	            	validCellsGhost.add(new int[]{i, j});
	            }
	        }
	    }

	    // Select a random cell for MAGIC_DOOR_WALL
	    int index = random.nextInt(validCellsMagicDoor.size());
	    int[] magicDoorCell = validCellsMagicDoor.get(index);

	    // Select a random cell for GHOST
	    index = random.nextInt(validCellsGhost.size());
	    int[] ghostCell = validCellsGhost.get(index);

	    // Set the cells
	    magicDoorRow = magicDoorCell[0];
	    magicDoorCol = magicDoorCell[1];
	    ghostRow = ghostCell[0];
	    ghostCol = ghostCell[1];
	}
}
