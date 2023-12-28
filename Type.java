package Main;

// Type of a cell
public enum Type {
	NONE_WALL, // NONE_WALL is for preventing players from moving diagonally
	
	CARROT_TOKEN, // CARROT_TOKEN is for player Rabbit
	CHEESE_TOKEN, // CHEESE_TOKEN is for player Mouse
	
	CURTAIN_WALL, // CURTAIN_WALL is for phase 1 when initializing the board
	
	// When reveal the CURTAIN_WALL, the following walls will be revealed:
	// MAGIC_DOOR_WALL can exist only once in the board

	WINDOW_WALL, // WINDOW_WALL is for player Rabbit
	MOUSEHOLE_WALL, // MOUSEHOLE_WALL is for player Mouse
	OPEN_WALL, // OPEN_WALL is for player Rabbit and player Mouse
	MAGIC_DOOR_WALL, // MAGIC_DOOR_WALL requires both players to stand on each side of the door to open it
	

	// When flip token in phase 2
	GHOST, // GHOST can exist only once in the board
	
	// If not GHOST, then the rest of the types will be the following:
	DARK_CATERPILLAR, 
	DARK_FROG,
	DARK_BAT,
	DARK_OWL,
	
	WHITE_CATERPILLAR,
	WHITE_FROG,
	WHITE_BAT,
	WHITE_OWL;	
}