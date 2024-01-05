package main;
import java.util.*;

public class GridCell {

	private CellType cellType;
    
	public GridCell() {}
	public GridCell(CellType cellType) {
		this.cellType = cellType;
	}

	public void setCellType(CellType cellType) { this.cellType = cellType; }
    public CellType getCellType() { return cellType; }

//    private static boolean magicDoorCreated = false;
//    private static boolean ghostCreated = false;

    public void changeWall(int x, int y) {
        if (this.cellType == CellType.CURTAIN_WALL) {
        	if (x == Board.getMagicDoorRow() && y == Board.getMagicDoorCol()) {
        		this.cellType = CellType.MAGIC_DOOR_WALL;
        	} else {
        		CellType[] wallTypes = {CellType.WINDOW_WALL, CellType.MOUSEHOLE_WALL, CellType.OPEN_WALL};
        		Random random = new Random();
                CellType newCellType = wallTypes[random.nextInt(wallTypes.length)];
                this.cellType = newCellType;
        	}
        }
		else {
			System.out.println( "\n" + "The wall has already been revealed" + "\n");
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
