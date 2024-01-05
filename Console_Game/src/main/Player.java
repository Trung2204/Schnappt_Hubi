package main;

public class Player {

	private int x;
	private int y;
	private Character character;
	private int numberOfActions;
	private ActionType actionType;
	private DirectionType directionType;
	
	public Player(int x, int y, Character character) {
		this.x = x;
		this.y = y;
		this.character = character;
	}
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setCharacter(Character character) { this.character = character; }
	public void setNumberOfAction(int numberOfActions) { this.numberOfActions = numberOfActions; }
	public void setAction(ActionType action) { this.actionType = action; }
	public void setDirection(DirectionType directionType) { this.directionType = directionType; }
//	public void setCurrentPosition() { this.currentPosition = Board.getGridCells()[x][y]; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Character getCharacter() { return character;}
	public int getNumberOfAction() { return numberOfActions;}
	public ActionType getAction() { return actionType; }
	public DirectionType getDirection() { return directionType; }
//	public GridCell getCurrentPosition() { return currentPosition; }
	
	// Method to map player's coordinates to board's coordinates
	public int getBoardX() { return x * 2; }
    public int getBoardY() { return y * 2; }
    
    public void move(DirectionType directionType, GridCell adjacentCell) {

		int newX = this.x;
        int newY = this.y;
        
        switch (directionType) {
            case UP:
                newX -= 2;
                break;
            case DOWN:
                newX += 2;
                break;
            case LEFT:
                newY -= 2;
                break;
            case RIGHT:
                newY += 2;
                break;
        }
            
        // Check if the adjacent cell is a curtain wall
        if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
            System.out.println("Cannot move because of curtain!");
        } else {
            // If not a curtain wall, then move the player
        	// Rabbit can only move through Window and Open wall
        	if ((this.character == Character.RABBIT && adjacentCell.getCellType() == CellType.WINDOW_WALL) ||
     			(this.character == Character.RABBIT && adjacentCell.getCellType() == CellType.OPEN_WALL)) {
        		this.x = newX;
                this.y = newY;
        	} // Mouse can only move through Mouse hole and Open wall
        	else if ((this.character == Character.MOUSE && adjacentCell.getCellType() == CellType.MOUSEHOLE_WALL) ||
        			   (this.character == Character.MOUSE && adjacentCell.getCellType() == CellType.OPEN_WALL)) {
        		this.x = newX;
                this.y = newY;
        	} else { 
        		System.out.println("Rabbit can only move through WINDOW or OPEN wall");
        		System.out.println("Mouse can only move through MOUSE HOLE or OPEN wall"); return; }
        	
        }
    }
    public void viewCurtain(GridCell curtainCell, int newX, int newY) {
    	curtainCell.changeWall(newX, newY);
    }
    public void viewToken(ActionType actionType) {
    	System.out.print("Player views token ");
    }
    public void openMagicDoor() {
    	System.out.println("Player opens magic door");
    }
	
	public void print() {
		System.out.println("("+x+","+y+")"+ "\t" +character+" "+numberOfActions);
	}
}
