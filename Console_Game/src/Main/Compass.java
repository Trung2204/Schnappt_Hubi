package Main;
import java.util.Random;

public class Compass {

//    protected static FieldType[] fields;
    protected static FieldType fieldType;
    // private String[] fields = {"clock", "two", "three", "clock", "ghost"};
    // private int[] numberOfAction = {1, 2, 3, 1, 1};
    private static Clock clock;
    private static Random random = new Random();

//    Compass()   {
//        fields = FieldType.values();
//    };

    public void setClock(Clock clock) {
        Compass.clock = clock;
    }

    public void spin() {
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
    
    public FieldType getFieldType() {
    	return fieldType;
    }
    public int getNumberOfAction() {
        return fieldType.getAction();
    }

    public Clock getClock() {
        return clock;
    }

    public FieldType updateClock() {
//        FieldType fieldType = this.spin();
        clock.increaseTime(fieldType);
        return fieldType;
    }
    
    public boolean checkGhostField() {
    	if (fieldType == FieldType.GHOST)
    		return true;
    	return false;
    }
    public boolean checkGhostMoveUp() {
    	int temp = Board.getGhostX();
    	if (--temp >= 0) return true;
		return false;
    }
    public boolean checkGhostMoveDown() {
    	int temp = Board.getGhostX();
    	if (++temp <= Board.size) return true;
		return false;
    }
    public boolean checkGhostMoveLeft() {
    	int temp = Board.getGhostY();
    	if (--temp >= 0) return true;
		return false;
    }
    public boolean checkGhostMoveRight() {
    	int temp = Board.getGhostX();
    	if (++temp <= Board.size) return true;
		return false;
    }
    public void moveGhost() {
    	if (checkGhostField()) {
    		if (GridCell.isGhostFound) {
    			
    			
    			
    			if (checkGhostMoveUp()) {
    				GridCell.swapToken(Board.getGhostCell(), Board.getAdjacentCellUp());
    			}
    			
    			
    			
    			
    			
    			
    		}
    			
    	}
    }
}