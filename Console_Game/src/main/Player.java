package main;

public class Player {

	private int x;
	private int y;
	private String character;
	private int numberOfActions;
	private GridCell currentPosition;
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setCharacter(String character) { this.character = character; }
	public void setNumberOfAction() { this.numberOfActions = Compass.getFieldType().getAction(); }
	public void setCurrentPosition() { this.currentPosition = Board.gridCells[x][y]; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public String getCharacter() {return character;}
	public int getNumberOfAction() {return numberOfActions;}
	public GridCell getCurrentPosition() { return currentPosition; }
	
	public void moveUp() {
		int temp = x;
		if (--temp >= 0) { 
			x--;
			setCurrentPosition();
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				x++;
				setCurrentPosition();
				return;
			}
			else {
				x = temp;
				x-=1;
			}
		}
		else System.out.println("Cannot move out of board!");
	}
	public void moveDown() {
		int temp = x;
		if (++temp <Board.size) { 
			x++;
			setCurrentPosition();
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				x--;
				setCurrentPosition();
				return;
			}
			else {
				x = temp;
				x+=1;
			}
		}
		else System.out.println("Cannot move out of board!");
	}
	public void moveLeft()  {
		int temp = y;
		if (--temp >= 0) { 
			y--;
			setCurrentPosition();
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				y++;
				setCurrentPosition();
				return;
			}
			else {
				y = temp;
				y-=1;
			}
		}
		else System.out.println("Cannot move out of board!");
	}
	public void moveRight() {
		int temp = y;
		if (++temp < Board.size) {
			y++;
			setCurrentPosition();
			if (currentPosition.getType() == Type.CURTAIN_WALL) {
				System.out.println("Cannot move because of curtain!");
				y--;
				setCurrentPosition();
				return;
			}
			else {
				y = temp;
				y+=1;
			}
		}
		else System.out.println("Cannot move out of board!");
	}
	
	
	public void viewCurtain() {
		currentPosition.changeWallType();
	}
	public void viewToken() {
		currentPosition.flipToken();
	}
	
	public void print() {
		System.out.println(x+" "+y+"\n"+character+" "+numberOfActions);
	}
}
