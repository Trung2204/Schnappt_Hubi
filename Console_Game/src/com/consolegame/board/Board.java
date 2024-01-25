package com.consolegame.board;

import java.util.*;
import com.consolegame.helper.type.CellType;

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
	
	public Board(int size,int rabbitsNumber, int miceNumber) {
		if (gridCells == null) {
	        gridCells = new GridCell[size][size];
	        setSpecialCells(rabbitsNumber, miceNumber);
	    }
	}
	
	protected static int getMagicDoorRow() { return magicDoorRow; }
	protected static int getMagicDoorCol() { return magicDoorCol; }
	protected static int getGhostRow() { return ghostRow; }
	protected static int getGhostCol() { return ghostCol; }
	
	public GridCell[][] getGrideCells() { return this.gridCells; }
	
//	public void initializeBoard(int rabbitsNumber, int miceNumber) {
//		Random random = new Random();
//		if (!tokens.isEmpty()) {
//			// calculate the difference in the rabbitsNumber and miceNumber
//			int characterNumberDifference = rabbitsNumber - miceNumber;
//			CellType temp = null;
//			
//			switch (characterNumberDifference) {
//			// If rabbitsNumber is larger than miceNumber
//				case 1: {
//					// add 1 more CARROT_TOKEN
//					temp = CellType.CARROT_TOKEN;
//					break;
//				}
//				// If rabbitsNumber is larger than miceNumber
//				case -1: {
//					// add 1 more CHEESE_TOKEN
//					temp = CellType.CHEESE_TOKEN;
//					break;
//				}
//				default:
//			}
//			
//			if (temp != null) {
//				switch (temp) {
//					// If it is CARROT_TOKEN
//					case CARROT_TOKEN: {
//						// then replace a CHEESE_TOKEN from tokens with a temp = CARROT_TOKEN
//						// Find index of first occurence of CHEESE_TOKEN
//						int index = tokens.indexOf(CellType.CHEESE_TOKEN);
//						// then set temp at the index
//						if (index != -1) { tokens.set(index, temp); }
//						
//						break;
//					}
//					// If it is CHEESE_TOKEN 
//					case CHEESE_TOKEN: {
//						// then replace a CARROT_TOKEN from tokens with a temp = CHEESE_TOKEN
//						// Find index of first occurence of CARROT_TOKEN
//						int index = tokens.indexOf(CellType.CARROT_TOKEN);
//						if (index != -1) { tokens.set(index, temp); }
//						break;
//					}
//					default:
//				}
//			}
//		}
//		// Iterate through rows
//		for (int i = 0; i < gridCells.length; i++) {
//			// Iterate through columns
//    		for (int j = 0; j < gridCells[i].length; j++) {
//    			// The squares can be moved in
//    			if (i % 2 == 0 && j % 2 == 0) {
//    				// check if gridCells[i][j] is not ghost
//    				if (i == ghostRow && j == ghostCol) {
//    					gridCells[i][j] = new GridCell(random.nextBoolean() ? CellType.CARROT_TOKEN : CellType.CHEESE_TOKEN);
//    				}
//    				// else if it is ghost
//    				else {
//    					int index = random.nextInt(tokens.size());
//    					gridCells[i][j] = new GridCell(tokens.get(index));
//    					tokens.remove(index);
//     				}
//    			} else if (i % 2 != 0 && j % 2 != 0) {
//                    // Assign NONE_WALL to the cross between walls
//                    gridCells[i][j] = new GridCell(CellType.NONE_WALL);
//    			} else {
//    				gridCells[i][j] = new GridCell(CellType.CURTAIN_WALL);
//    			}
//    		}
//    	}
//	}
	
	private void setSpecialCells(int rabbitsNumber, int miceNumber) {
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
	public void swapGridCells(int x1, int y1, int x2, int y2) {
		GridCell temp = gridCells[x1][y1];
		gridCells[x1][y1] = gridCells[x2][y2];
		gridCells[x2][y2] = temp;
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