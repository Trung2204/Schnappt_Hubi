package com.guigame.main;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
 
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	
    	Game game = new Game();
        primaryStage.setScene(game.start(primaryStage));
        primaryStage.setTitle("Schnappt Hubi!");
        primaryStage.setWidth(700);
        primaryStage.setHeight(700);
        primaryStage.show();
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,  (event) -> {
            System.out.println("Key pressed: " + event.toString());

            switch(event.getCode().getCode()) {
                case 27 : { // 27 = ESC key
                    primaryStage.close();
                    break;
                }
                case 10 : { // 10 = Return
                    primaryStage.setWidth( primaryStage.getWidth() * 2);
                }
                default:  {
                    System.out.println("Unrecognized key");
                }
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}