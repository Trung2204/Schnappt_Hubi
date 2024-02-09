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
	@FXML
	public TextArea gameRulesTextArea;
	@FXML
	private ImageView manualBackground;
	@FXML
	private ImageView setNoPlayers;
	@FXML
	private ImageView setPlayers;
	@FXML
	private ImageView buttonSetting;
	@FXML
	private ImageView startButton;
	@FXML
	private ImageView actionBox;
	@FXML
	private ImageView horizontal_curtain_wall;
	@FXML
	private ImageView vertical_curtain_wall;
	@FXML
	private ImageView horizontal_closed_magic_door_wall_curtain;
	@FXML
	private ImageView horizontal_open_magic_door_wall;
	@FXML
	private ImageView vertical_closed_magic_door_wall_curtain;
	@FXML
	private ImageView vertical_open_magic_door_wall;
	@FXML
	private ImageView horizontal_mousehole_wall;
	@FXML
	private ImageView vertical_mousehole_wall;
	@FXML
	private ImageView vertical_open_wall;
	@FXML
	private ImageView horizontal_open_wall;
	@FXML
	private ImageView horizontal_window_wall;
	@FXML
	private ImageView vertical_open_window_wall;

	private Stage stage;
	private Scene scene;
	private Parent root;

	public void initialize(URL location, ResourceBundle resources) {
		// Load the background image using getClass().getResource()
		Image backgroundImage = new Image("/start_scene_background_1_1.jpeg");
		manualBackground.setImage(backgroundImage);

		Image noOfPlayers = new Image("/setNoPlayers.png");
		setNoPlayers.setImage(noOfPlayers);

		Image setPLayers = new Image("/setPlayers.png");
		setPlayers.setImage(setPLayers);

		Image settingButton = new Image("/buttonSetting.png");
		buttonSetting.setImage(settingButton);

		Image buttonStart = new Image("/startButton.png");
		startButton.setImage(buttonStart);

		Image boxAction = new Image("/actionBox.png");
		actionBox.setImage(boxAction);

		Image horicurt = new Image("/horizontal_curtain_wall.png");
		horizontal_curtain_wall.setImage(horicurt);

		Image vertcurt = new Image("/vertical_curtain_wall.png");
		vertical_curtain_wall.setImage(vertcurt);

		Image horiclosedmagic = new Image("/horizontal_closed_magic_door_wall_curtain.png");
		horizontal_closed_magic_door_wall_curtain.setImage(horiclosedmagic);

		Image horiopenmagic = new Image("/horizontal_open_magic_door_wall.png");
		horizontal_open_magic_door_wall.setImage(horiopenmagic);

		Image vertclosedmagic = new Image("/vertical_closed_magic_door_wall_curtain.png");
		vertical_closed_magic_door_wall_curtain.setImage(vertclosedmagic);

		Image vertopenmagic = new Image("/vertical_open_magic_door_wall.png");
		vertical_open_magic_door_wall.setImage(vertopenmagic);

		Image horimouse = new Image("/horizontal_mousehole_wall.png");
		horizontal_mousehole_wall.setImage(horimouse);

		Image vertmouse = new Image("/vertical_mousehole_wall.png");
		vertical_mousehole_wall.setImage(vertmouse);

		Image vertopen = new Image("/vertical_open_wall.png");
		vertical_open_wall.setImage(vertopen);

		Image horiopen = new Image("/horizontal_open_wall.png");
		horizontal_open_wall.setImage(horiopen);

		Image horiwindow = new Image("/horizontal_window_wall.png");
		horizontal_window_wall.setImage(horiwindow);

		Image vertopenwindow = new Image("/vertical_open_window_wall.png");
		vertical_open_window_wall.setImage(vertopenwindow);

		setGameRulesText();
	}

	private void setGameRulesText() {
		String gameRules = "\t\tSchnappt Hubi is played on a 3x3 grid.\n\n" +
				"\tFirst, players choose between being a rabbit or a mouse. The game supports 2 to 4 players, with a combination of rabbits and mice.\n\n" +
				"\tAt the start, all walls are hidden behind curtains. Players must uncover them to reveal the walls. The goal is to catch Hubi the ghost, but first, players must find and open the magic door for Hubi to appear.\n\n" +
				"\tEach wall type has specific rules: only rabbits can jump through windows, only mice can pass through mouse-holes, and both can pass through open walls. Once the magic door is open, it functions like an open wall, allowing both rabbits and mice to pass through.\n\n" +
				"\tPlayers take turns, and a compass automatically determines their moves. If the compass hits the clock field, time increases by one. If it reaches midnight without capturing the ghost, the game is lost.\n\n" +
				"\tOnce the magic door location is known, players must position themselves on each side to open it.\n\n" +
				"\tAfter opening the magic door, players can reveal tokens to find Hubi hiding underneath.\n\n" +
				"\tIf the compass hits the ghost field after the magic door is open, Hubi tries to move. If not found, adjacent tokens of the same type are swapped. If Hubi is found, it moves randomly in one direction.\n\n" +
				"\tThe ghost is captured when two players occupy the same cell as the ghost." +
				"\tThe other pages are the guides on how to navigate the game.\n\n";
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