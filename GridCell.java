package Main;

import java.util.Random;

public class GridCell {
	// Type of a cell
	private Type type;
	// When reveal the CURTAIN_WALL, the following walls will be revealed if the MAGIC_DOOR_WALL is not revealed yet.
	private static Type[] wallTypesWithMagicDoor = {Type.MAGIC_DOOR_WALL, 
			Type.MOUSEHOLE_WALL, Type.WINDOW_WALL, Type.OPEN_WALL};
	// When reveal the CURTAIN_WALL, the following walls will be revealed if the MAGIC_DOOR_WALL is already revealed.
	private static Type[] wallTypesWithoutMagicDoor = {Type.MOUSEHOLE_WALL, Type.WINDOW_WALL, Type.OPEN_WALL};
	// When reveal the CARROT_TOKEN or CHEESE_TOKEN, the following walls will be revealed if the GHOST is not revealed yet.
	private static Type[] tokenTypesWithGhost = {Type.GHOST, 
			Type.WHITE_BAT, Type.WHITE_CATERPILLAR, Type.WHITE_FROG, Type.WHITE_OWL};
	// When reveal the CARROT_TOKEN or CHEESE_TOKEN, the following walls will be revealed if the GHOST is already revealed.
	private static Type[] tokenTypesWithoutGhost = {Type.DARK_BAT, Type.DARK_CATERPILLAR, Type.DARK_FROG, Type.DARK_OWL};
			
	// When the MAGIC_DOOR_WALL is revealed, set isMagicDoorAdded to true.		
	private static boolean isMagicDoorAdded;
	// When the GHOST is revealed, set isGhostFound to true.
	protected static boolean isGhostFound;
	// Random number generator
  private static final Random random = new Random();
  // When initializing the board, the following tokens will be randomly assigned to the grid cells.
  protected static Type[] tokenTypes = {Type.CARROT_TOKEN, Type.CHEESE_TOKEN};

	// Constructor
	public GridCell() {
		this.type = Type.NONE_WALL;
	}
	
	public GridCell(Type type) {
		this.type = type;
	}

	// Get the type of a cell
	public Type getType() { return type; }
	// Set the type of a cell
	public void setType(Type type) { this.type = type; }
	
	// Change the wall type of a CURTAIN_WALL
	public void changeWallType() {
		if (this.type == Type.CURTAIN_WALL) {
			Type[] wallTypes;
			if (isMagicDoorAdded) {
				wallTypes = wallTypesWithoutMagicDoor;
			} else {
				wallTypes = wallTypesWithMagicDoor;

			}
			Type randomWallType = wallTypes[random.nextInt(wallTypes.length)];
			this.type = randomWallType;
			if (this.type == Type.MAGIC_DOOR_WALL) {
				isMagicDoorAdded = true;
			}
		} else {
			System.out.println("Cannot change the Wall Type since the GridCell is not of a Type.CURTAIN_WALL");
		}		
	}
	
	// ----------------------------------------------------------------
	// Change the type of a CARROT_TOKEN or CHEESE_TOKEN
	public void flipToken() {
		if (this.type == Type.CARROT_TOKEN || this.type == Type.CHEESE_TOKEN) {
			Type[] types;
			if (isGhostFound) {
				types = tokenTypesWithoutGhost;
			} else {
				types = tokenTypesWithGhost;
			}
			Type randomTokenType = types[random.nextInt(types.length)];
			this.type = randomTokenType;
			if (type == Type.GHOST) {
				isGhostFound = true;
				
			}
		} else {
			System.out.println("Cannot flip the Token since the GridCell is not of a Type.Token");
		}
	}

	// ----------------------------------------------------------------
	// Swaps the type of two tokens
	public static void swapToken(GridCell TokenA, GridCell TokenB) {
		Type temp = TokenA.getType();
		TokenA.setType(TokenB.getType());
		TokenB.setType(temp);
//		System.out.println(otherToken.getType());
	}

	// Prints the type of a cell
	public void print() {
		System.out.println(getType());
	}
}
