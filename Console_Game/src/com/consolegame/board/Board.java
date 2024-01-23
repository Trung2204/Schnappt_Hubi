package com.consolegame.board;

import java.util.*;

import com.consolegame.helper.type.CellType;
import com.consolegame.helper.type.DirectionType;

public class Board {
	private GridCell[][] gridCells;
	private static int magicDoorRow;
    private static int magicDoorCol;
    private static int ghostRow;
    private static int ghostCol;
	private static ArrayList<CellType> tokens = new ArrayList<>(Arrays.asList(
			CellType.CHEESE_TOKEN, CellType.CARROT_TOKEN,
			CellType.CHEESE_TOKEN, CellType.CARROT_TOKEN,
			CellType.CHEESE_TOKEN, CellType.CARROT_TOKEN,
			CellType.CHEESE_TOKEN, CellType.CARROT_TOKEN));
	
	public Board(int size) {
		if (gridCells == null) {
	        gridCells = new GridCell[size][size];
	        setSpecialCells();
	    }
	}
	
	protected static int getMagicDoorRow() { return magicDoorRow; }
	protected static int getMagicDoorCol() { return magicDoorCol; }
	protected static int getGhostRow() { return ghostRow; }
	protected static int getGhostCol() { return ghostCol; }
	
	public GridCell[][] getGrideCells() { return this.gridCells; }
	
	public void initializeBoard(int rabbitsNumber, int miceNumber) {
		Random random = new Random();
		if (!tokens.isEmpty()) {
			// calculate the difference in the rabbitsNumber and miceNumber
			int characterNumberDifference = rabbitsNumber - miceNumber;
			CellType temp = null;
			
			switch (characterNumberDifference) {
			// If rabbitsNumber is larger than miceNumber
				case 1: {
					// add 1 more CARROT_TOKEN
					temp = CellType.CARROT_TOKEN;
					break;
				}
				// If rabbitsNumber is larger than miceNumber
				case -1: {
					// add 1 more CHEESE_TOKEN
					temp = CellType.CHEESE_TOKEN;
					break;
				}
				default:
			}
			
			if (temp != null) {
				switch (temp) {
					// If it is CARROT_TOKEN
					case CARROT_TOKEN: {
						// then replace a CHEESE_TOKEN from tokens with a temp = CARROT_TOKEN
						// Find index of first occurence of CHEESE_TOKEN
						int index = tokens.indexOf(CellType.CHEESE_TOKEN);
						// then set temp at the index
						if (index != -1) { tokens.set(index, temp); }
						
						break;
					}
					// If it is CHEESE_TOKEN 
					case CHEESE_TOKEN: {
						// then replace a CARROT_TOKEN from tokens with a temp = CHEESE_TOKEN
						// Find index of first occurence of CARROT_TOKEN
						int index = tokens.indexOf(CellType.CARROT_TOKEN);
						if (index != -1) { tokens.set(index, temp); }
						break;
					}
					default:
				}
			}
		}
		// Iterate through rows
		for (int i = 0; i < gridCells.length; i++) {
			// Iterate through columns
    		for (int j = 0; j < gridCells[i].length; j++) {
    			// The squares can be moved in
    			if (i % 2 == 0 && j % 2 == 0) {
    				// check if gridCells[i][j] is not ghost
    				if (i == ghostRow && j == ghostCol) {
    					gridCells[i][j] = new GridCell(random.nextBoolean() ? CellType.CARROT_TOKEN : CellType.CHEESE_TOKEN);
    				}
    				// else if it is ghost
    				else {
    					int index = random.nextInt(tokens.size());
    					gridCells[i][j] = new GridCell(tokens.get(index));
    					tokens.remove(index);
     				}
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
	    List<int[]> validCellsMagicDoor = new ArrayList<>(Arrays.asList(
	    		new int[] {1,2},
	    		new int[] {2,1},
	    		new int[] {2,3},
	    		new int[] {3,2}
	    		));
	    
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
	    // gridCell[...Row][...Col]
	    // magicDoorRow == gridCells[x][]
	    magicDoorRow = magicDoorCell[0];
	    // magicDoorCol == gridCells[][y]
	    magicDoorCol = magicDoorCell[1];
	    // ghostRow == gridCells[x][]
	    ghostRow = ghostCell[0];
	    // ghostCol == gridCells[][y]
	    ghostCol = ghostCell[1];
	}

	public GridCell getGridCellAt(int row, int col) {
		if (row >= 0 && row < gridCells.length && col >= 0 && col < gridCells[row].length) {
	        return gridCells[row][col];
	    } else {
	        throw new IllegalArgumentException("Invalid position");
	    }
	}
	
    // For ghost's movement when ghost is not found
	public void moveRandomTokens() {
//		Map<List<Integer>, GridCell> cheeseCells = new HashMap<>();
//		Map<List<Integer>, GridCell> carrotCells = new HashMap<>();
//		
//		for (int i = 0; i < gridCells.length; i++) {
//			for (int j = 0; j < gridCells[i].length; j++) {
//				if (i % 2 == 0 && j % 2 == 0) {
//					List<Integer> position = Arrays.asList(i, j);
//					switch(gridCells[i][j].getCellType()) {
//					case CARROT_TOKEN:
//						carrotCells.put(position, gridCells[i][j]);
//						break;
//					case CHEESE_TOKEN:
//						cheeseCells.put(position, gridCells[i][j]);
//						break;
//					default:
//						break;
//					}
//				}
//			}
//		}
//		findAdjacentCells(gridCells, carrotCells);
//        findAdjacentCells(gridCells, cheeseCells);
		System.out.println("Ghost has not been found, moving 2 tokens of the same type...");
	}
//    private static boolean isValidAndSameType(GridCell[][] grid, int i, int j, CellType type) {
//        return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length && grid[i][j].getCellType() == type;
//    }
//
//    // Find adjacent cells of the same type in a list of cells
//    private static void findAdjacentCells(GridCell[][] grid, Map<List<Integer>, GridCell> cells) {
//        Map<List<Integer>, List<List<Integer>>> adjacentCells = new HashMap<>();
//
//        for (List<Integer> cell : cells.keySet()) {
//            List<List<Integer>> neighbors = new ArrayList<>();
//
//            // Check the four neighboring cells (up, down, left, right)
//            if (isValidAndSameType(grid, cell.get(0) - 1, cell.get(1), cells.get(cell).getCellType())) {  // Up
//                neighbors.add(Arrays.asList(cell.get(0) - 1, cell.get(1)));
//            }
//            if (isValidAndSameType(grid, cell.get(0) + 1, cell.get(1), cells.get(cell).getCellType())) {  // Down
//                neighbors.add(Arrays.asList(cell.get(0) + 1, cell.get(1)));
//            }
//            if (isValidAndSameType(grid, cell.get(0), cell.get(1) - 1, cells.get(cell).getCellType())) {  // Left
//                neighbors.add(Arrays.asList(cell.get(0), cell.get(1) - 1));
//            }
//            if (isValidAndSameType(grid, cell.get(0), cell.get(1) + 1, cells.get(cell).getCellType())) {  // Right
//                neighbors.add(Arrays.asList(cell.get(0), cell.get(1) + 1));
//            }
//
//            if (!neighbors.isEmpty()) {
//                adjacentCells.put(cell, neighbors);
//            }
//        }
//
//        // Print the positions of adjacent cells of the same type
//        for (Map.Entry<List<Integer>, List<List<Integer>>> entry : adjacentCells.entrySet()) {
//            System.out.println("The cell at position " + entry.getKey() + " has neighbors of the same type at the following positions:");
//            for (List<Integer> pos : entry.getValue()) {
//                System.out.println(pos);
//            }
//        }
//    }
	
	public void swapGridCells(int x1, int y1, int x2, int y2) {
		GridCell temp = gridCells[x1][y1];
		gridCells[x1][y1] = gridCells[x2][y2];
		gridCells[x2][y2] = temp;
	}
	
	// For ghost's movement when ghost is found
	public void moveAdjacentToken(DirectionType direction) {
		System.out.println("Ghost has been found, moving ghost with token "+direction);
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
}
