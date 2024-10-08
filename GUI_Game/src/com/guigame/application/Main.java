package com.guigame.application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Schnappt Hubi!");
			primaryStage.getIcons().add(new Image("/Logo.png"));
			primaryStage.setMinWidth(800);
			primaryStage.setMinHeight(600);
			primaryStage.setWidth(1280); // 1280
	        primaryStage.setHeight(720); // 720
	        primaryStage.setResizable(false);
			
	        AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/InitialMenu.fxml"));
			Scene scene = new Scene(root,1280,720);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
	            switch(event.getCode().getCode()) {
	                case 27 : { // 27 = ESC key
	                    primaryStage.close();
	                    break;
	                }
	                default:  {
	                    System.out.println("Unrecognized key");
	                }
	            }
	        });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
