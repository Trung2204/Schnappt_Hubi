package com.guigame.application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ManualController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToInitialMenuScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("InitialMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}