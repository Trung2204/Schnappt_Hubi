package main;

public class Ghost extends GridCell{

	private static int x = -1;
	private static int y = -1;
	
//	public Ghost(int x, int y) {
//		Ghost.x = x;
//		Ghost.y = y;
//	}
//	
//	public Ghost(int[] ghostPosition) {
//		Ghost.x = ghostPosition[0];
//		Ghost.y = ghostPosition[1];
//	}
	
	public static int getX() { return Ghost.x; }
	public static int getY() { return Ghost.y; }
	
	public static void setX(int x) { Ghost.x = x; }
	public static void setY(int y) { Ghost.y = y; }
	
	public static void updateGhost(int x, int y) {
		Ghost.setX(x);
		Ghost.setY(y);
	}
}
