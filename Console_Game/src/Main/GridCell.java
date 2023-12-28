package Main;

import java.util.Random;

public class GridCell {

	private Type type;
	
	private static Type[] wallTypesWithMagicDoor = {Type.MAGIC_DOOR_WALL, 
			Type.MOUSEHOLE_WALL, Type.WINDOW_WALL, Type.OPEN_WALL};
	private static Type[] wallTypesWithoutMagicDoor = {Type.MOUSEHOLE_WALL, Type.WINDOW_WALL, Type.OPEN_WALL};
	
	private static Type[] tokenTypesWithGhost = {Type.GHOST, 
			Type.WHITE_BAT, Type.WHITE_CATERPILLAR, Type.WHITE_FROG, Type.WHITE_OWL};
	private static Type[] tokenTypesWithoutGhost = {Type.DARK_BAT, Type.DARK_CATERPILLAR, Type.DARK_FROG, Type.DARK_OWL};
			
			
	private static boolean isMagicDoorAdded;
	protected static boolean isGhostFound;
    private static final Random random = new Random();
    
    protected static Type[] tokenTypes = {Type.CARROT_TOKEN, Type.CHEESE_TOKEN};

	public GridCell() {
		this.type = Type.NONE_WALL;
	}
	
	public GridCell(Type type) {
		this.type = type;
	}

	public Type getType() { return type; }
	public void setType(Type type) { this.type = type; }
	
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
	public static void swapToken(GridCell TokenA, GridCell TokenB) {
		Type temp = TokenA.getType();
		TokenA.setType(TokenB.getType());
		TokenB.setType(temp);
//		System.out.println(otherToken.getType());
	}
	public void print() {
		System.out.println(getType());
	}
}
