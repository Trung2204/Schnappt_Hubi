package com.guigame.board;

import java.util.*;
import com.guigame.helper.type.CellType;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridCell {
	private CellType cellType;
	private static final int CELL_SIZE = 50;
    private Rectangle rectangle;
	
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
	
	public Rectangle drawGridCell() {
		switch (cellType) {
		case CARROT_TOKEN: {
			rectangle.setFill(Color.ORANGE);
			break;
		}
		case CHEESE_TOKEN: {
			rectangle.setFill(Color.LIGHTYELLOW);
			break;
		}
		case CURTAIN_WALL: {
			rectangle.setFill(Color.ANTIQUEWHITE);
			break;
		}
		case NONE_WALL: {
			rectangle.setFill(Color.BLACK);
			break;
		}
		default:
			rectangle.setFill(Color.BLACK);
			break;
		}
        return rectangle;
    }
	public void setOnMouseClicked(EventHandler<MouseEvent> handler) {
        rectangle.setOnMouseClicked(handler);
    }
	public GridCell(CellType cellType) {
		this.cellType = cellType;
		rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.WHITE);
	}

	public void setCellType(CellType cellType) { this.cellType = cellType; }
    public CellType getCellType() { return this.cellType; }

    public void changeWall(int x, int y) {
		// Check for Magic Door Wall
    	if (x == Board.getMagicDoorRow() && y == Board.getMagicDoorCol()) {
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
    
    public void changeToken(int x, int y) {

    	if (x == Board.getGhostRow() && y == Board.getGhostCol()) {
    		this.cellType = CellType.GHOST;
    	} else {
            Random random = new Random();
            int index = random.nextInt(flippedTokens.size());
            CellType newCellType = flippedTokens.get(index);
            this.cellType = newCellType;
            flippedTokens.remove(index);
    	}
        
    }
}
