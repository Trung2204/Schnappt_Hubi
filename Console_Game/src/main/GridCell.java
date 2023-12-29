package main;

import java.util.Random;

public class GridCell {

	private Type type;
    private static final Random random = new Random();
	private static Type[] wallTypesWithMagicDoor = {Type.MAGIC_DOOR_WALL, 
			Type.MOUSEHOLE_WALL, Type.WINDOW_WALL, Type.OPEN_WALL};
	private static Type[] wallTypesWithoutMagicDoor = {Type.MOUSEHOLE_WALL, Type.WINDOW_WALL, Type.OPEN_WALL};
	private static Type[] tokenTypesWithGhost = {Type.GHOST, 
			Type.WHITE_BAT, Type.WHITE_CATERPILLAR, Type.WHITE_FROG, Type.WHITE_OWL};
	private static Type[] tokenTypesWithoutGhost = {Type.DARK_BAT, Type.DARK_CATERPILLAR, Type.DARK_FROG, Type.DARK_OWL};		
	
	protected static boolean isMagicDoorAdded;
	protected static boolean isGhostFound;
    protected static Type[] tokenTypes = {Type.CARROT_TOKEN, Type.CHEESE_TOKEN};

	public GridCell() {
		this.type = Type.NONE_WALL;
	}
	
	public GridCell(Type type) {
		this.type = type;
	}
	
	public GridCell(GridCell other) { this.type = other.type; }

	public Type getType() { return type; }
	public void setType(Type type) { this.type = type; }
	public GridCell getGridCell() { return this; }
	public static boolean getIsMagicDoorAdded() { return GridCell.isMagicDoorAdded; }
	public static boolean getIsGhostFound() { return GridCell.isGhostFound; }
	
	public void changeWallType() {
		if (this.type != Type.CURTAIN_WALL) {
			return;
		}
		
		if (Board.wallSize == 1 && !isMagicDoorAdded) {
			this.type = Type.MAGIC_DOOR_WALL;
			isMagicDoorAdded = true;
			System.out.println("MAGIC_DOOR_WALL");
		} else if (Board.wallSize > 1) {
			Type[] types = isMagicDoorAdded ? wallTypesWithoutMagicDoor : wallTypesWithMagicDoor;
			System.out.println(isMagicDoorAdded ? "wallTypesWithoutMagicDoor" : "wallTypesWithMagicDoor");
			this.type = types[random.nextInt(types.length)];
			if (this.type == Type.MAGIC_DOOR_WALL) {
				isMagicDoorAdded = true;
			}
		} else {
			Type[] types = wallTypesWithoutMagicDoor;
			this.type = types[random.nextInt(types.length)];
			System.out.println("wallTypesWithoutMagicDoor_else");
		}
		Board.wallSize--;
	}
	
//	public void flipToken() {
//		if (Board.tokenSize > 1) {
//			if (this.type == Type.CARROT_TOKEN || this.type == Type.CHEESE_TOKEN) {
//				Type[] types;
//				if (isGhostFound) {
//
//					types = tokenTypesWithoutGhost;
//					System.out.println("tokenTypesWithoutGhost");
//				}
//				else {
//					types = tokenTypesWithGhost;
//					System.out.println("tokenTypesWithGhost");
//				}
//				Type randomTokenType = types[random.nextInt(types.length)];
//				this.type = randomTokenType;
//				if (type == Type.GHOST) {
//					isGhostFound = true;
//				}
//				Board.tokenSize--;
//			} 
//		} else if (Board.tokenSize == 1 && !isGhostFound) {
//			if (this.type == Type.CARROT_TOKEN || this.type == Type.CHEESE_TOKEN) {
//				this.type = Type.GHOST;
//				isGhostFound = true;
//				System.out.println("tokenGhost_elseif");
//				Board.tokenSize--;
//			}
//		} else {
//			if (this.type == Type.CARROT_TOKEN || this.type == Type.CHEESE_TOKEN) {
//				Type[] types = tokenTypesWithoutGhost;
//				Type randomType = types[random.nextInt(types.length)];
//				this.type = randomType;
//				System.out.println("tokensTypesWithoutGhost_else");
//				Board.tokenSize--;
//			}
//		}
////		else {
////			System.out.println("Cannot flipToken since the Type is not Type.CARROT_TOKEN or Type.CHEESE_TOKEN!");
////		}
//	}
	public void flipToken() {
	    if (this.type != Type.CARROT_TOKEN && this.type != Type.CHEESE_TOKEN) {
	        return;
	    }

	    if (Board.tokenSize == 1 && !isGhostFound) {
	        this.type = Type.GHOST;
	        isGhostFound = true;
	        System.out.println("tokenGhost_if");
	    } else if (Board.tokenSize > 1) {
	        Type[] types = isGhostFound ? tokenTypesWithoutGhost : tokenTypesWithGhost;
	        System.out.println(isGhostFound ? "tokenTypesWithoutGhost" : "tokenTypesWithGhost");
	        this.type = types[random.nextInt(types.length)];
	        if (this.type == Type.GHOST) {
	            isGhostFound = true;
	        }
	    } else {
	        Type[] types = tokenTypesWithoutGhost;
	        this.type = types[random.nextInt(types.length)];
	        System.out.println("tokensTypesWithoutGhost_else");
	    }

	    Board.tokenSize--;
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
