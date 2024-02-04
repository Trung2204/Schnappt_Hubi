package com.guigame.application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreditController implements Initializable {
	public ImageView creditBackground;
	public ImageView HMTimage;
	public ImageView DMTimage;
	@FXML
	private Label nptpLabel;
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void initialize(URL location, ResourceBundle resources) {
		// Load the background image using getClass().getResource()
		Image backgroundImage = new Image(getClass().getResource("/resources/start_scene_background.jpeg").toExternalForm());
		creditBackground.setImage(backgroundImage);

		Image hmtImage = new Image(getClass().getResource("/resources/void blep.jpg").toExternalForm());
		HMTimage.setImage(hmtImage);
		DMTimage.setImage(hmtImage);

		nptpLabel.setText("Nguyen Phuoc\nThien Phu");
//
//		setGameRulesText();
	}
	public void switchToInitialMenuScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/InitialMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}