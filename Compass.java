package Main;
import java.util.Random;

public class Compass {
// This class represents a compass that generates random field types


    protected static FieldType fieldType;       // Protected static variable 'fieldType' to store the current field type
    private static Clock clock;         // Private static variable 'clock' to store the associated clock object
    private static Random random = new Random();        // Private static variable 'clock' to store the associated clock object

    // Method to set the associated clock object
    public void setClock(Clock clock) {
        Compass.clock = clock;      // Assign the provided clock object to 'clock'
    }


    // Method to simulate the rotation of the compass
    public void spin() {
        
        int angle = random.nextInt(360);        // Generate a random angle between 0 and 360 degrees

        if (angle <= 72) {
            Compass.fieldType = FieldType.CLOCK1;       // Field type CLOCK1
        } else if (73 <= angle && angle <= 126) {
        	Compass.fieldType = FieldType.TWO;          // Field type TWO
        } else if (127 <= angle && angle <= 180) {
        	Compass.fieldType = FieldType.THREE;        // Field type THREE
        } else if (181 <= angle && angle <= 252) {
        	Compass.fieldType = FieldType.CLOCK2;       // Field type CLOCK2
        } else {
        	Compass.fieldType = FieldType.GHOST;        // Field type GHOST
        }
    }
    

    // Method to retrieve the current field type
    public FieldType getFieldType() {
    	return fieldType;       // Return the current field type
    }


    // Method to get the number of actions associated with the current field type
    public int getNumberOfAction() {
        return fieldType.getAction();       // Get the action value from the field type enum
    }


    // Method to retrieve the associated clock object
    public Clock getClock() {
        return clock;       // Return the 'clock' reference
    }


    // Method to update the clock's time based on the current field type
    public FieldType updateClock() {
        clock.increaseTime(fieldType);      // Increase the clock's time
        return fieldType;       // Return the updated field type
    }
    

    // Method to check if the current field type is GHOST
    public boolean checkGhostField() {
    	if (fieldType == FieldType.GHOST)
    		return true;        // The field type is GHOST      
    	return false;       // The field type is not GHOST
    }

    // Methods to check if the ghost can move in a specific direction   
    public boolean checkGhostMoveUp() {
    	int temp = Board.getGhostX();       // Get the current ghost's x-coordinate
    	if (--temp >= 0)        // Check if moving up is possible
            return true;       // Movement allowed
		return false;       // Movement not allowed
    }


    public boolean checkGhostMoveDown() {
    	int temp = Board.getGhostX();       // Get the current ghost's x-coordinate
    	if (++temp <= Board.size)       // Check if moving down is possible
            return true;        // Movement allowed
		return false;           // Movement not allowed
    }


    public boolean checkGhostMoveLeft() {
    	int temp = Board.getGhostY();       // Get the current ghost's y-coordinate
    	if (--temp >= 0)        // Check if moving left is possible
            return true;        // Movement allowed
		return false;       // Movement not allowed
    }


    public boolean checkGhostMoveRight() {
    	int temp = Board.getGhostX();       // Get the current ghost's y-coordinate
    	if (++temp <= Board.size)       // Check if moving right is possible
            return true;        // Movement allowed
		return false;       // Movement not allowed
    }


    // Method to move the ghost based on the field type and ghost's location
    public void moveGhost() {

        // If the current field type is GHOST (indicating the presence of a ghost)
        if (checkGhostField()) {

            // Check if the ghost has been found (either by the player or by the compass)
            if (GridCell.isGhostFound) {

                // Check if the ghost can move up
                if (checkGhostMoveUp()) {

                    // Swap the positions of the ghost cell (Board.getGhostCell()) and the adjacent cell up (Board.getAdjacentCellUp())
                    GridCell.swapToken(Board.getGhostCell(), Board.getAdjacentCellUp());
                }
            }
        }
    }

}