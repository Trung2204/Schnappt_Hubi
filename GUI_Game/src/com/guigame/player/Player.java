package com.guigame.player;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.guigame.board.Board;
import com.guigame.board.GridCell;
import com.guigame.helper.type.ActionType;
import com.guigame.helper.type.CharacterType;
import com.guigame.helper.type.DirectionType;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player {

	private int x;
	private int y;
	private CharacterType character;
	private int numberOfActions;
	
	public Player(int x, int y, CharacterType character) {
		this.x = x;
		this.y = y;
		this.character = character;
	}
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public void setCharacter(CharacterType character) { this.character = character; }
	public void setNumberOfActions(int numberOfActions) { this.numberOfActions = numberOfActions; }
	
	public int getX() {return x;}
	public int getY() {return y;}
	public CharacterType getCharacter() { return character;}
	public int getNumberOfActions() { return numberOfActions;}

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
    public void viewCurtain(GridCell curtainCell,Board board, int newX, int newY) {
    	curtainCell.changeWall(board,newX, newY);
    }
    public void viewToken(GridCell tokenCell,Board board, int newX, int newY) {
    	tokenCell.changeToken(board,newX, newY);
    }
	public void openMagicDoor(GridCell magicDoor) {
		magicDoor.changeMagicDoor();
	}
	public void print() {
		System.out.println("("+x+","+y+")"+ "\t" +character+" has "+numberOfActions+" action(s)");
	}

	public void decrementNumberOfActions() {
		if(this.numberOfActions > 0) this.numberOfActions--;	
	}
}
