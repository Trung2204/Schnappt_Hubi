package com.guigame.application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InitialMenuController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToManualScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("Manual.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setWidth(1280);
		stage.setHeight(720);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToCreditScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("Credit.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setWidth(1280);
		stage.setHeight(720);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToPlayScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("Play.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setWidth(1280);
		stage.setHeight(720);
		stage.setScene(scene);
		stage.show();
	}
}
