package main;

public class MagicDoor extends GridCell{
	
	protected static int x = -1;
	protected static int y = -1;
	
	public static void setX(int x) { MagicDoor.x = x; }
	public static void setY(int y) { MagicDoor.y = y; }
	
	public static int getX() { return MagicDoor.x; }
	public static int getY() { return MagicDoor.y; }
	
	public static void updateMagicDoor(int x, int y) { 
		MagicDoor.setX(x); 
		MagicDoor.setY(y); 
	} 
}
