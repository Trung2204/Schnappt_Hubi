package com.guigame.application;

import java.util.Stack;

import com.guigame.board.Board;
import com.guigame.helper.type.CharacterType;
import com.guigame.player.Clock;
import com.guigame.player.Compass;
import com.guigame.player.Player;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Game {
	public static Board board;
	public static Clock clock = new Clock();
	public static Compass compass = new Compass();
	
	public static Player[] listOfPlayers;
	public static int numberOfPlayers = 0;
	public static int numberOfRabbits = 0;
	public static int numberOfMice    = 0;
	
	public Game(int numberOfPlayers) {
		Game.numberOfPlayers = numberOfPlayers;
		Game.listOfPlayers = new Player[numberOfPlayers];
	}
	
//	public static GridPane drawBoard() {
//		for (Player player : listOfPlayers) {
//			if (player.getCharacter() == CharacterType.MOUSE) numberOfMice++;
//			else if (player.getCharacter() == CharacterType.RABBIT) numberOfRabbits++;
//		}
//		board = new Board(numberOfRabbits,numberOfMice);
//		return board.drawBoard();
//	}

	public static VBox drawClockCompass() {
		VBox vbox = new VBox();
		Label clockLabel = new Label("CLOCK");
		Label timeLabel = new Label();
		timeLabel.setText("Time: "+clock.getTime());
		Label compassLabel = new Label("COMPASS");
		Label fieldLabel = new Label();
		fieldLabel.setText("Field: "+compass.getFieldType());
		Label actionNumberLabel = new Label();
		actionNumberLabel.setText("Number of actions: "+compass.getNumberOfAction());
		vbox.getChildren().addAll(clockLabel,timeLabel,compassLabel,fieldLabel,actionNumberLabel);
		return vbox;
	}
	
	public static void updateState() {
		
	}
}