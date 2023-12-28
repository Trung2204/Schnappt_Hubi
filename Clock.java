package Main;
public class Clock {
// This class represents a clock with a time value and an attached compass

    private static int time;    // Private static variable 'time' to store the current time (hour)
    private static Compass compass;     // Private static variable 'compass' to store the associated compass object
    
    // Default constructor for creating a new Clock instance
    public Clock() {
        Clock.time = 0;     // Initialize time to 0
    }

    // Method to set the associated compass object
    public void setCompass(Compass compass) {
        Clock.compass = compass;    // Assign the provided compass object to 'compass'
    }

    // Method to increase the clock's time based on the specified field type
    public void increaseTime(FieldType fieldType) {
        // Ensure time is less than 12 and the field type is valid
        if (time < 12 && (fieldType == FieldType.CLOCK1 || fieldType == FieldType.CLOCK2))
            time++;     // Increment the time by 1
        else System.out.println("can't increase time");     // Notify that the time cannot be increased
        return;
    }

    // Method to retrieve the current time
    public int getTime() {
        return time;    // Return the current time value
    }

    // Method to get the associated compass object
    public Compass getCompass() {
        return compass;     // Return the 'compass' reference
    }
}