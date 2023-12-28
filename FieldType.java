package Main;
public enum FieldType {
    // Enumeration representing the different field types

    CLOCK1(1),  // Field Clock with 1 action
    CLOCK2(1),  // Field Clock with 2 actions
    GHOST(1),   // Field Ghost with 1 action
    TWO(2),    // Field Two with 2 actions
    THREE(3);   // Field Three with 3 actions

    // Private attribute to store the action associated with each field type
    private final int action;

    // Constructor to initialize the action value
    FieldType(int action) {
        this.action = action;       // Assign the provided action value to the 'action' attribute
    }

    // Getter method to retrieve the action associated with the current field type
    public int getAction() {
        return action;      // Return the 'action' attribute representing the number of actions for the current field
    }
}
