package com.guigame.board;

import java.util.*;
import com.guigame.helper.type.CellType;

public class GridCell {
	private CellType cellType;
	
	private static ArrayList<CellType> innerWalls = 
			new ArrayList<>(Arrays.asList(CellType.OPEN_WALL, CellType.MOUSEHOLE_WALL, CellType.WINDOW_WALL));
	
	private static ArrayList<CellType> outerWalls = 
			new ArrayList<>(Arrays.asList(
					CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL, CellType.OPEN_WALL,
					CellType.MOUSEHOLE_WALL, CellType.WINDOW_WALL));
	private static ArrayList<CellType> flippedTokens = 
			new ArrayList<>(Arrays.asList(
					CellType.WHITE_BAT, CellType.DARK_BAT,
					CellType.WHITE_CATERPILLAR, CellType.DARK_CATERPILLAR,
					CellType.WHITE_FROG, CellType.DARK_FROG, 
					CellType.WHITE_OWL, CellType.DARK_OWL));
	
	public GridCell(CellType cellType) {
		this.cellType = cellType;
	}

	public void setCellType(CellType cellType) { this.cellType = cellType; }
    public CellType getCellType() { return this.cellType; }

    public void changeWall(Board board,int x, int y) {
		// Check for Magic Door Wall
    	if (x == board.getMagicDoorRow() && y == board.getMagicDoorCol()) {
    		this.cellType = CellType.MAGIC_DOOR_WALL;
    	} 
    	// Check for outer walls 
		else if (x == 0 || x == 4 || (x != 2 && y != 2)) {
			Random random = new Random();
			int index = random.nextInt(outerWalls.size());
			CellType newCellType = outerWalls.get(index);
			this.cellType = newCellType;
			outerWalls.remove(index);
    	}
    	// Check for inner walls
    	else {
    		Random random = new Random();
    		int index = random.nextInt(innerWalls.size());
    		CellType newCellType = innerWalls.get(index);
    		this.cellType = newCellType;	
    		innerWalls.remove(index);
    	}
    }
    
    public void changeMagicDoor() {
    	if (this.cellType == CellType.MAGIC_DOOR_WALL) {
        	this.cellType = CellType.OPEN_WALL;
        }
    }
    
    public void changeToken(Board board,int x, int y) {

    	if (x == board.getGhostRow() && y == board.getGhostCol()) {
    		this.cellType = CellType.GHOST;
    		board.setGhostColNull();
    		board.setGhostRowNull();
    	} else {
            Random random = new Random();
            int index = random.nextInt(flippedTokens.size());
            CellType newCellType = flippedTokens.get(index);
            this.cellType = newCellType;
            flippedTokens.remove(index);
    	}
        
    }
}
