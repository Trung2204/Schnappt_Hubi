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
	public void setNumberOfAction() { this.numberOfActions = Compass.fieldType.getAction(); }
	public void setCurrentPosition() { this.currentPosition = Board.gridCells[x][y]; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public String getCharacter() {return character;}
	public int getNumberOfAction() {return numberOfActions;}
	public GridCell getCurrentPosition() { return currentPosition; }
	
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
