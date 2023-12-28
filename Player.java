package Main;

// This class is used for create player's instances
public class Player {

	// Position of player
	private int x;
	private int y;
	// Character of player (Rabbit or Mouse)
	private String character;
	// Number of actions of one player
	private int numberOfActions;
	// Current cell of player
	private GridCell currentPosition;
	
	// Set value of x
	public void setX(int x) { this.x = x; }
	// Set value of y
	public void setY(int y) { this.y = y; }
	// Set value of character
	public void setCharacter(String character) { this.character = character; }
	// Set value of number of actions
	public void setNumberOfAction() { this.numberOfActions = Compass.fieldType.getAction(); }
	// Set current cell of player
	public void setCurrentPosition() { this.currentPosition = Board.gridCells[x][y]; }
	
	// Get player's x
	public int getX() {return x;}
	// Get player's y
	public int getY() {return y;}
	// Get player's character
	public String getCharacter() {return character;}
	// Get player's number of actions
	public int getNumberOfAction() {return numberOfActions;}
	// Get player's current cell
	public GridCell getCurrentPosition() { return currentPosition; }
	
	// Move player up
	public void moveUp() {
		int temp = x;
		if (--temp >= 0) { 
			temp = --x;
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				return;
			}
			else x-=2;
		}
		else System.out.println("Cannot move out of board!");
	}
	// Move player down
	public void moveDown() {
		int temp = x;
		if (++temp <= Board.size) { 
			temp = ++x;
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				return;
			}
			else x+=2;
		}
		else System.out.println("Cannot move out of board!");
	}
	// Move player left
	public void moveLeft()  {
		int temp = y;
		if (--temp <= Board.size) { 
			temp = y--;
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				return;
			}
			else y-=2;
		}
		else System.out.println("Cannot move out of board!");
	}
	// Move player right
	public void moveRight() {
		int temp = y;
		if (++temp <= Board.size) { 
			temp = ++x;
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				return;
			}
			else y+=2;
		}
		else System.out.println("Cannot move out of board!");
	}
	
	// Player can view curtain
	public void viewCurtain(int x, int y) {
		currentPosition.changeWallType();
	}
	// Player can view token
	public void viewToken(int x, int y) {
		currentPosition.flipToken();
	}
	
	public void print() {
		System.out.println(x+" "+y+"\n"+character+" "+numberOfActions);
	}
}
