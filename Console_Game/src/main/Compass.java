package main;
import java.util.Random;

public class Compass {
    private FieldType fieldType;
    
    public FieldType getFieldType() { return fieldType; }
    public int getNumberOfAction() { return fieldType.getAction(); }
    public void spin() {
    	Random random = new Random();
        int angle = random.nextInt(360);

        if (angle <= 72) { // CLOCK1
        	this.fieldType = FieldType.CLOCK1;
        } else if (73 <= angle && angle <= 126) { // TWO
        	this.fieldType = FieldType.TWO;
        } else if (127 <= angle && angle <= 180) { // THREE
        	this.fieldType = FieldType.THREE;
        } else if (181 <= angle && angle <= 252) { // CLOCK2
        	this.fieldType = FieldType.CLOCK2;
        } else { // GHOST
        	this.fieldType = FieldType.GHOST;
        }
    }
    
    

//    public Clock getClock() {
//        return clock;
//    }

//    public static FieldType updateClock() {
////        FieldType fieldType = this.spin();
//        Clock.increaseTime(fieldType);
//        return fieldType;
//    }
    
//    public static boolean checkGhostField() {
//    	if (fieldType == FieldType.GHOST)
//    		return true;
//    	return false;
//    }
//    public static boolean checkGhostMoveUp() {
//    	int temp = Ghost.getX();
//    	if (--temp >= 0) return true;
//		return false;
//    }
//
//    public static boolean checkGhostMoveLeft() {
//    	int temp = Ghost.getY();
//    	if (--temp >= 0) return true;
//		return false;
//    }

}