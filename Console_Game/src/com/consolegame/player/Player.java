package com.consolegame.player;

import com.consolegame.board.GridCell;
import com.consolegame.helper.type.ActionType;
import com.consolegame.helper.type.CellType;
import com.consolegame.helper.type.DirectionType;

public class Player {

	private int x;
	private int y;
	private Character character;
	private int numberOfActions;
	private ActionType actionType;
	private DirectionType directionType;
	
	public Player(int x, int y, Character character) {
		this.x = x;
		this.y = y;
		this.character = character;
	}
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setCharacter(Character character) { this.character = character; }
	public void setNumberOfAction(int numberOfActions) { this.numberOfActions = numberOfActions; }
	public void setAction(ActionType action) { this.actionType = action; }
	public void setDirection(DirectionType directionType) { this.directionType = directionType; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Character getCharacter() { return character;}
	public int getNumberOfAction() { return numberOfActions;}
	public ActionType getAction() { return actionType; }
	public DirectionType getDirection() { return directionType; }

    
    public void move(DirectionType directionType, GridCell adjacentCell) {
		int newX = this.x;
        int newY = this.y;
        switch (directionType) {
            case UP:
                newX -= 2;
                break;
            case DOWN:
                newX += 2;
                break;
            case LEFT:
                newY -= 2;
                break;
            case RIGHT:
                newY += 2;
                break;
            default:
				break;
        }
        // Check if the adjacent cell is a curtain wall
        if (adjacentCell.getCellType() == CellType.CURTAIN_WALL) {
            System.err.println("\n" + "CANNOT MOVE BECAUSE OF CURTAIN!" + "\n");
        }
        // If not a curtain wall, then move the player
        else {
        	this.x = newX;
            this.y = newY;
        }
    }
    public void viewCurtain(GridCell curtainCell, int newX, int newY) {
    	curtainCell.changeWall(newX, newY);
    }
    public void viewToken(GridCell tokenCell, int newX, int newY) {
    	tokenCell.changeToken(newX, newY);
    }
	public void openMagicDoor(GridCell magicDoor) {
		magicDoor.changeMagicDoor();
	}
	public void print() {
		System.out.println("("+x+","+y+")"+ "\t" +character+" has "+numberOfActions+" action(s)");
	}
}
