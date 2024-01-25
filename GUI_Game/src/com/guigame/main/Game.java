package com.guigame.main;

import com.guigame.player.Clock;
import com.guigame.player.Compass;
import com.guigame.player.Player;
import com.guigame.player.Character;

import java.util.ArrayList;
import java.util.List;

import com.guigame.board.Board;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
	
	private static Board board;
	private static Clock clock;
	private static Compass compass;
	
	private static Player[] listOfPlayers;
	private static int numberOfPlayers = 0;
	private static int numberOfRabbits = 0;
	private static int numberOfMice    = 0;
	
	private static List<String> positionsChosen = new ArrayList<>();

    public Scene start(Stage primaryStage) {
        // Create the Initial Menu scene
        Button playButton = new Button("Play");
        Button manualButton = new Button("Reading manual");
        Button aboutUsButton = new Button("About Us");
        VBox menuLayout = new VBox(playButton,manualButton, aboutUsButton);
        Scene menuScene = new Scene(menuLayout, 200, 100);
        
        // Create the Setting Screen scene
        Label settingLabel = new Label("Setting Screen\nPlease choose the number of players");
        ToggleGroup group = new ToggleGroup();
        RadioButton rb2 = new RadioButton("2");
        RadioButton rb3 = new RadioButton("3");
        RadioButton rb4 = new RadioButton("4");
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        rb4.setToggleGroup(group);
        Button submitButton = new Button("Submit");
        Button nextButton = new Button("Next");
        nextButton.setDisable(true); // Disable the button initially
        Button returnSettingButton = new Button("Return");
        
        VBox vbox = new VBox(rb2, rb3, rb4, submitButton);
        vbox.setAlignment(Pos.CENTER_LEFT);

        BorderPane settingLayout = new BorderPane();
        settingLayout.setTop(settingLabel);
        settingLayout.setCenter(vbox);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(nextButton,returnSettingButton);
        AnchorPane.setBottomAnchor(nextButton, 10.0);
        AnchorPane.setRightAnchor(nextButton, 10.0);
        AnchorPane.setBottomAnchor(returnSettingButton, 10.0);
        AnchorPane.setLeftAnchor(returnSettingButton, 10.0);

        settingLayout.setBottom(anchorPane);
        
        Scene settingScene = new Scene(settingLayout, 200, 100);
        submitButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            if (selectedRadioButton != null) {
                numberOfPlayers = Integer.parseInt(selectedRadioButton.getText());
                // Create an array to store players
                listOfPlayers = new Player[numberOfPlayers];
                // Enable the "Let's Go!" button
                nextButton.setDisable(false);
            } else {
                settingLabel.setText("Please select the number of players.");
            }
        });
        nextButton.setOnAction(event -> {
            createPlayerScene(primaryStage,0);
        });

        // Create the Manual Screen scene
        Label manualLabel = new Label("Manual Screen");
        Button returnManualButton = new Button("Return");
        VBox manualLayout = new VBox(manualLabel, returnManualButton);
        manualLayout.setAlignment(Pos.BASELINE_CENTER);
        Scene manualScene = new Scene(manualLayout, 200, 100);
        
        // Create the Information Screen scene
        Label infoLabel = new Label("Information Screen");
        Button returnInfoButton = new Button("Return");
        VBox infoLayout = new VBox(infoLabel, returnInfoButton);
        infoLayout.setAlignment(Pos.BASELINE_CENTER);
        Scene infoScene = new Scene(infoLayout, 200, 100);

        // Set up the buttons to switch to the appropriate scenes when clicked
        playButton.setOnAction(event -> primaryStage.setScene(settingScene));
        returnSettingButton.setOnAction(event -> primaryStage.setScene(menuScene));
        aboutUsButton.setOnAction(event -> primaryStage.setScene(infoScene));
        returnInfoButton.setOnAction(event -> primaryStage.setScene(menuScene));
        manualButton.setOnAction(event -> primaryStage.setScene(manualScene));
        returnManualButton.setOnAction(event -> primaryStage.setScene(menuScene));

        return menuScene;
    }
    private void createPlayerScene(Stage primaryStage, int playerIndex) {
        if (playerIndex >= numberOfPlayers) {
            return; // All players have made their selection
        }

        // Create a new scene for the player to choose their character and starting position
        Label characterSelectionLabel = new Label("Player " + (playerIndex+1) + " chooses character:");
        ToggleGroup characterGroup = new ToggleGroup();
        RadioButton rbRabbit = new RadioButton("RABBIT");
        RadioButton rbMouse = new RadioButton("MOUSE");
        rbRabbit.setToggleGroup(characterGroup);
        rbMouse.setToggleGroup(characterGroup);

        // Disable the rabbit or mouse radio button if the maximum number is reached
        rbRabbit.setDisable(numberOfRabbits >= 2);
        rbMouse.setDisable(numberOfMice >= 2);

        Label startingPositionLabel = new Label("Choose starting position:");
        ToggleGroup positionGroup = new ToggleGroup();
        RadioButton rbTopLeft = new RadioButton("Top Left");
        RadioButton rbTopRight = new RadioButton("Top Right");
        RadioButton rbBottomLeft = new RadioButton("Bottom Left");
        RadioButton rbBottomRight = new RadioButton("Bottom Right");
        rbTopLeft.setToggleGroup(positionGroup);
        rbTopRight.setToggleGroup(positionGroup);
        rbBottomLeft.setToggleGroup(positionGroup);
        rbBottomRight.setToggleGroup(positionGroup);

        // Disable the position radio button if the position has been chosen
        rbTopLeft.setDisable(positionsChosen.contains("TOP LEFT"));
        rbTopRight.setDisable(positionsChosen.contains("TOP RIGHT"));
        rbBottomLeft.setDisable(positionsChosen.contains("BOTTOM LEFT"));
        rbBottomRight.setDisable(positionsChosen.contains("BOTTOM RIGHT"));

        Button submitCharacterAndPositionButton = new Button("Submit");
        Button returnButton = new Button("Return");
        Button letsGoButton = new Button("Let's Go!");
        letsGoButton.setVisible(false); // Only show when the last player finishes the selection
        returnButton.setVisible(playerIndex != 0); // Don't show for the first player

        VBox vbox = new VBox(characterSelectionLabel, rbRabbit, rbMouse, startingPositionLabel, rbTopLeft, rbTopRight, rbBottomLeft, rbBottomRight, submitCharacterAndPositionButton, returnButton, letsGoButton);
        vbox.setAlignment(Pos.CENTER_LEFT);

        Scene characterAndPositionScene = new Scene(vbox, 200, 100);
        primaryStage.setScene(characterAndPositionScene); // Switch to the new scene

        submitCharacterAndPositionButton.setOnAction(characterAndPositionEvent -> {
            // Handle character selection and starting position input
            String characterInput = ((RadioButton) characterGroup.getSelectedToggle()).getText().toUpperCase();
            Character character = Character.valueOf(characterInput);
            String positionInput = ((RadioButton) positionGroup.getSelectedToggle()).getText().toUpperCase();
            int x,y;
            switch (positionInput) {
            case "TOP LEFT":
                x = 0;
                y = 0;
                break;
            case "TOP RIGHT":
                x = 0;
                y = 4;
                break;
            case "BOTTOM LEFT":
                x = 4;
                y = 0;
                break;
            case "BOTTOM RIGHT":
                x = 4;
                y = 4;
                break;
            default:
                throw new IllegalArgumentException("Invalid position: " + positionInput);
            }

            // Add the player to the array
            Player player = new Player(x,y,character);
            listOfPlayers[playerIndex] = player;
            listOfPlayers[playerIndex].print();

            // Count the number of rabbits and mice chosen so far
            if (character == Character.RABBIT) numberOfRabbits++;
            else if (character == Character.MOUSE) numberOfMice++;
            
            // Add the chosen position to the list of positions chosen
            positionsChosen.add(positionInput);

            // If it's the last player, make the "Let's Go!" button visible
            if (playerIndex == numberOfPlayers - 1) {
                letsGoButton.setVisible(true);
            }

            // Create the scene for the next player
            if (playerIndex < numberOfPlayers - 1) {
                createPlayerScene(primaryStage, playerIndex + 1);
            }
        });

        returnButton.setOnAction(returnEvent -> {
            // Return to the previous player's scene
            createPlayerScene(primaryStage, playerIndex - 1);
        });

        letsGoButton.setOnAction(goEvent -> {
            // Enter the Main Game Screen scene
            // Create the Main Game Screen scene
            Label gameLabel = new Label("Main Game Screen");
            board = new Board(numberOfRabbits,numberOfMice);
            board.setOnCellClicked();
            
            clock = new Clock();
            compass = new Compass();

            VBox gameLayout = new VBox(gameLabel,board.drawBoard());
            Scene gameScene = new Scene(gameLayout, 200, 100);
            primaryStage.setScene(gameScene); // Switch to the Main Game Screen scene
        });
    }
}