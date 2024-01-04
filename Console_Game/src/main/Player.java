package main;

public class Player {

	private int x;
	private int y;
	private Character character;
	private int numberOfActions;
	private ActionType actionType;
	private Direction direction;
	
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
	public void setDirection(Direction direction) { this.direction = direction; }
//	public void setCurrentPosition() { this.currentPosition = Board.getGridCells()[x][y]; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Character getCharacter() { return character;}
	public int getNumberOfAction() { return numberOfActions;}
	public ActionType getAction() { return actionType; }
	public Direction getDirection() { return direction; }
//	public GridCell getCurrentPosition() { return currentPosition; }
	
	// Method to map player's coordinates to board's coordinates
	public int getBoardX() { return x * 2; }
    public int getBoardY() { return y * 2; }
    
    public void move(Direction direction, GridCell adjacentCell) {

		int newX = this.x;
        int newY = this.y;
        
        switch (direction) {
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
            // Mouse can only move through Mouse hole and Open wall
        	} else if ((this.character == Character.MOUSE && adjacentCell.getCellType() == CellType.MOUSEHOLE_WALL) ||
        			   (this.character == Character.MOUSE && adjacentCell.getCellType() == CellType.OPEN_WALL)) {
        		this.x = newX;
                this.y = newY;
        	}
        }
    }
    public void viewCurtain(ActionType actionType, Direction direction) {
    	System.out.print("Player views curtain ");
    	if (direction == Direction.UP) System.out.print("up");
    	else if (direction == Direction.DOWN) System.out.print("down");
    	else if (direction == Direction.LEFT) System.out.print("left");
    	else if (direction == Direction.RIGHT) System.out.print("right");
    }
    public void viewToken(ActionType actionType) {
    	System.out.print("Player views token ");
    }
	
//	public void moveUp() {
//		int temp = x;
//		if (--temp >= 0) { 
//			x--;
//			setCurrentPosition();
//			if (currentPosition.getType() == Type.CURTAIN_WALL) {
//				System.out.println("Cannot move because of curtain!");
//				x++;
//				setCurrentPosition();
//				return;
//			}
//			else {
//				x = temp;
//				x-=1;
//			}
//		}
//		else System.out.println("Cannot move out of board!");
//	}
//	public void moveDown() {
//		int temp = x;
//		if (++temp <Board.size) { 
//			x++;
//			setCurrentPosition();
//			if (currentPosition.getType() == Type.CURTAIN_WALL) {
//				System.out.println("Cannot move because of curtain!");
//				x--;
//				setCurrentPosition();
//				return;
//			}
//			else {
//				x = temp;
//				x+=1;
//			}
//		}
//		else System.out.println("Cannot move out of board!");
//	}
//	public void moveLeft()  {
//		int temp = y;
//		if (--temp >= 0) { 
//			y--;
//			setCurrentPosition();
//			if (currentPosition.getType() == Type.CURTAIN_WALL) {
//				System.out.println("Cannot move because of curtain!");
//				y++;
//				setCurrentPosition();
//				return;
//			}
//			else {
//				y = temp;
//				y-=1;
//			}
//		}
//		else System.out.println("Cannot move out of board!");
//	}
//	public void moveRight() {
//		int temp = y;
//		if (++temp < Board.size) {
//			y++;
//			setCurrentPosition();
//			if (currentPosition.getType() == Type.CURTAIN_WALL) {
//				System.out.println("Cannot move because of curtain!");
//				y--;
//				setCurrentPosition();
//				return;
//			}
//			else {
//				y = temp;
//				y+=1;
//			}
//		}
//		else System.out.println("Cannot move out of board!");
//	}
	
	public void print() {
		System.out.println("("+x+","+y+")"+ "\t" +character+" "+numberOfActions);
	}
}
