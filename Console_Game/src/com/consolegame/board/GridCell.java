package com.consolegame.board;
import java.util.*;

import com.consolegame.helper.type.CellType;

public class GridCell {
	private CellType cellType;
    
	public GridCell() {}
	public GridCell(CellType cellType) {
		this.cellType = cellType;
	}

	public void setCellType(CellType cellType) { this.cellType = cellType; }
    public CellType getCellType() { return cellType; }

    public void changeWall(int x, int y) {
    	if (x == Board.getMagicDoorRow() && y == Board.getMagicDoorCol()) {
    		this.cellType = CellType.MAGIC_DOOR_WALL;
    	} else {
    		CellType[] wallTypes = {CellType.WINDOW_WALL, CellType.MOUSEHOLE_WALL, CellType.OPEN_WALL};
    		Random random = new Random();
            CellType newCellType = wallTypes[random.nextInt(wallTypes.length)];
            this.cellType = newCellType;
    	}
    }
    public void changeMagicDoor() {
    	if (this.cellType == CellType.MAGIC_DOOR_WALL) {
        	this.cellType = CellType.OPEN_WALL;
        }
    }
    public void changeToken(int x, int y) {
        if ((this.cellType == CellType.CARROT_TOKEN || this.cellType == CellType.CHEESE_TOKEN)) {
        	if (x == Board.getGhostRow() && y == Board.getGhostCol()) {
        		this.cellType = CellType.GHOST;
        	} else {
        		CellType[] tokenTypes = {CellType.GHOST, CellType.DARK_CATERPILLAR, CellType.DARK_FROG, CellType.DARK_BAT, CellType.DARK_OWL, CellType.WHITE_CATERPILLAR, CellType.WHITE_FROG, CellType.WHITE_BAT, CellType.WHITE_OWL};
                Random random = new Random();
                CellType newCellType = tokenTypes[random.nextInt(tokenTypes.length)];
                this.cellType = newCellType;
        	}
        }
    }
	public static void swapToken(GridCell TokenA, GridCell TokenB) {
		
	}
}
