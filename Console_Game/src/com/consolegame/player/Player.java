package com.consolegame.player;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.consolegame.board.GridCell;
import com.consolegame.helper.type.ActionType;
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
	public void setNumberOfActions(int numberOfActions) { this.numberOfActions = numberOfActions; }
	public void setAction(ActionType action) { this.actionType = action; }
	public void setDirection(DirectionType directionType) { this.directionType = directionType; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Character getCharacter() { return character;}
	public int getNumberOfActions() { return numberOfActions;}
	public ActionType getAction() { return actionType; }
	public DirectionType getDirection() { return directionType; }

	@SuppressWarnings("resource")
	public ActionType chooseAction(boolean isGhostActivated, boolean isMagicDoorFound) {
		// Determine the set of available actions based on the game state
		ActionType[] availableActions;
		if (isGhostActivated) {
			availableActions = new ActionType[] {ActionType.MOVE, ActionType.VIEW_CURTAIN, ActionType.VIEW_TOKEN, ActionType.NONE};
		} else {
			availableActions = isMagicDoorFound ? new ActionType[] {ActionType.MOVE, ActionType.VIEW_CURTAIN, ActionType.OPEN_MAGIC_DOOR, ActionType.NONE} : new ActionType[] {ActionType.MOVE, ActionType.VIEW_CURTAIN, ActionType.NONE};
		}
		// Display available actions to the user
	    System.out.println("Available actions:");
	    for (int i = 0; i < availableActions.length; i++) {
	        System.out.println((i + 1) + ". " + availableActions[i]);
	    }
	    // Get user input
	    Scanner scanner = new Scanner(System.in);
	    int chosenActionIndex = - 1;

	    // Validate user input
	    while (chosenActionIndex < 0 || chosenActionIndex >= availableActions.length) {
	        try {
	            System.out.print("Enter a number between 1 and " + availableActions.length+": ");
	            chosenActionIndex = scanner.nextInt() - 1;
	        } catch (InputMismatchException e) {
	            System.err.println("Invalid input. Please enter a number.");
	            scanner.next(); // discard the invalid input
	        }
	    }

	    // Return the chosen action
	    return availableActions[chosenActionIndex];
	}
	@SuppressWarnings("resource")
	public DirectionType chooseDirection() {
		// Determine the set of available directions
		DirectionType[] availableDirections = new DirectionType[] {DirectionType.UP, DirectionType.DOWN,
																DirectionType.LEFT, DirectionType.RIGHT};
		// Display available actions to the user
	    System.out.println("Available directions:");
	    for (int i = 0; i < availableDirections.length; i++) {
	        System.out.println((i + 1) + ". " + availableDirections[i]);
	    }
	    // Get user input
	    Scanner scanner = new Scanner(System.in);
	    int chosenDirectionIndex = - 1;

	    // Validate user input
	    while (chosenDirectionIndex < 0 || chosenDirectionIndex >= availableDirections.length) {
	        try {
	            System.out.print("Enter a number between 1 and " + availableDirections.length+": ");
	            chosenDirectionIndex = scanner.nextInt() - 1;
	        } catch (InputMismatchException e) {
	            System.err.println("Invalid input. Please enter a number.");
	            scanner.next(); // discard the invalid input
	        }
	    }

	    // Return the chosen direction
	    return availableDirections[chosenDirectionIndex];
	}
    public void move(DirectionType directionType) {
        switch (directionType) {
            case UP:
                this.x -= 2;
                break;
            case DOWN:
                this.x += 2;
                break;
            case LEFT:
                this.y -= 2;
                break;
            case RIGHT:
                this.y += 2;
                break;
            default:
				break;
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
