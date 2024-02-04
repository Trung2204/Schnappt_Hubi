package com.guigame.application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class InitialMenuController implements Initializable {
	@FXML
	private ImageView initialBackground;
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void initialize(URL location, ResourceBundle resources) {
		// Load the background image using getClass().getResource()
		Image backgroundImage = new Image(getClass().getResource("/resources/start_scene_background.jpeg").toExternalForm());
		initialBackground.setImage(backgroundImage);
	}

	public void switchToManualScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/Manual.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setWidth(1280);
		stage.setHeight(720);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToCreditScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/Credit.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setWidth(1280);
		stage.setHeight(720);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToPlayScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/Play.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setWidth(1280);
		stage.setHeight(720);
		stage.setScene(scene);
		stage.show();
	}
}
