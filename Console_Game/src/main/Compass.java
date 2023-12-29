package main;
import java.util.Random;

public class Compass {

//    protected static FieldType[] fields;
    // private String[] fields = {"clock", "two", "three", "clock", "ghost"};
    // private int[] numberOfAction = {1, 2, 3, 1, 1};
//    private static Clock clock;
    private static final Random random = new Random();
    
    private static FieldType fieldType;

//    Compass()   {
//        fields = FieldType.values();
//    };

//    public void setClock(Clock clock) {
//        Compass.clock = clock;
//    }

    public static void spin() {
        int angle = random.nextInt(360);

        if (angle <= 72) { // CLOCK1
            Compass.fieldType = FieldType.CLOCK1;
        } else if (73 <= angle && angle <= 126) { // TWO
        	Compass.fieldType = FieldType.TWO;
        } else if (127 <= angle && angle <= 180) { // THREE
        	Compass.fieldType = FieldType.THREE;
        } else if (181 <= angle && angle <= 252) { // CLOCK2
        	Compass.fieldType = FieldType.CLOCK2;
        } else { // GHOST
        	Compass.fieldType = FieldType.GHOST;
        }
    }
    
    public static FieldType getFieldType() {
    	return fieldType;
    }
    public static int getNumberOfAction() {
        return fieldType.getAction();
    }

//    public Clock getClock() {
//        return clock;
//    }

    public static FieldType updateClock() {
//        FieldType fieldType = this.spin();
        Clock.increaseTime(fieldType);
        return fieldType;
    }
    
    public static boolean checkGhostField() {
    	if (fieldType == FieldType.GHOST)
    		return true;
    	return false;
    }
    public static boolean checkGhostMoveUp() {
    	int temp = Ghost.getX();
    	if (--temp >= 0) return true;
		return false;
    }
    public static boolean checkGhostMoveDown() {
    	int temp = Ghost.getX();
    	if (++temp <= Board.size) return true;
		return false;
    }
    public static boolean checkGhostMoveLeft() {
    	int temp = Ghost.getY();
    	if (--temp >= 0) return true;
		return false;
    }
    public static boolean checkGhostMoveRight() {
    	int temp = Ghost.getY();
    	if (++temp <= Board.size) return true;
		return false;
    }
    public static void moveGhost() {
    	if (checkGhostField()) {
    		if (GridCell.isGhostFound) {
    			
    			
    			
    			if (checkGhostMoveUp()) {
    				GridCell.swapToken(Board.getGhostCell(), Board.getAdjacentCellUp());
    			}
    			
    			
    			
    			
    			
    			
    		}
    			
    	}
    }
}