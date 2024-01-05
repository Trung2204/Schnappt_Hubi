package main;

public class MagicDoor extends GridCell {
	private int x;
	private int y;
	private DirectionType side;
	private DirectionType otherSide;
	
	public MagicDoor(int x, int y, DirectionType side) {
		super.setCellType(CellType.MAGIC_DOOR_WALL);
		this.x = x;
		this.y = y;
		this.side = side;
		if(side == DirectionType.UP) this.otherSide = DirectionType.DOWN;
		if(side == DirectionType.DOWN) this.otherSide = DirectionType.UP;
		if(side == DirectionType.LEFT) this.otherSide = DirectionType.RIGHT;
		if(side == DirectionType.RIGHT) this.otherSide = DirectionType.LEFT;
	}
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setSide(DirectionType side) { this.side = side; }
	public void setOtherSide(DirectionType otherSide) { this.otherSide = otherSide; }
	public void changeMagicDoorToOpenWall() { super.setCellType(CellType.OPEN_WALL); }
	
	public int getX() { return x; }
	public int getY() { return y; }
	public DirectionType getSide() { return side; }
	public DirectionType getOtherSide() { return otherSide; }

	public void print() {
		System.out.println("Magic door is found: ("+x+","+y+"), Side: "+side);
	}
	
}