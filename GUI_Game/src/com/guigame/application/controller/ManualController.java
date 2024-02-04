package com.guigame.application.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ResourceBundle;

public class ManualController implements Initializable {
	public TextArea gameRulesTextArea;
	@FXML
	private ImageView manualBackground;
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void initialize(URL location, ResourceBundle resources) {
		// Load the background image using getClass().getResource()
		Image backgroundImage = new Image(getClass().getResource("/resources/start_scene_background.jpeg").toExternalForm());
		manualBackground.setImage(backgroundImage);

		setGameRulesText();
	}

	private void setGameRulesText() {
		String gameRules = "Schnappt Hubi is a grid consisting of 3x3 cells.\n\n" +
				"\tFirst, the board requires each player to choose a character type, either a rabbit or a mouse. The number of players participating ranges from 2 to 4. If there are two or three players, there must be at least a rabbit player and a mouse player. If there are four players, there must be two rabbit players and two mouse players.\n\n" +
				"\tThen the tokens must be placed on the board according to the number of players. The ghost token must be chosen only once, and the other one is excluded from the game. If there are two players, the number of carrot and cheese tokens is equal to each other. If there are three players, whether there is one more rabbit or mouse player, then remove a cheese token and add a carrot token for the former, and vice versa. The wall sets are covered by curtains and set on the board.\n\n" +
				"\tThe ghost is captured when two players are in the same cell as the ghost.\n\n" +
				"\tSecond, the players play the game turn-by-turn. For each player, the compass must be spun to get the appropriate number of moves for that player, and if the ghost field is hit in the compass, the clock increases time by one. If the clock is at midnight, all the players have played their turns, and the ghost is not captured by then, the game ends, and all players lose their game.\n\n" +
				"\tThe game's progress is in two phases. The first one is unveiling the curtains and finding the magic door to open. The second phase, after opening the magic door, is to flip the tokens to find the ghost, and the ghost must be captured by at least two players that are in the same cell as it.\n\n" +
				"\tFor each wall, only the rabbit player can jump (pass) through the window wall, and only the mouse player can pass through the mouse-hole wall. The open wall allows both the mouse and rabbit to move through. The magic door wall, after having been opened, is considered to work like the open wall, allowing both rabbits and mice to pass through it.\n\n" +
				"\tOnly in phase two, when hitting the ghost field, does the ghost make a move. Compared to phase one, the field only needs to be considered in terms of the number of actions. When the ghost makes a move, if the ghost token is not found yet (by flipping the token up), then two tokens of the adjacent cells of the same kind, either two carrot or two cheese tokens, are swapped. If it is not possible, then nothing happens. If Hubi the ghost is found, the ghost token is swapped with the token in any other adjacent cell.";

		gameRulesTextArea.setText(gameRules);
	}


	public void switchToInitialMenuScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/InitialMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}