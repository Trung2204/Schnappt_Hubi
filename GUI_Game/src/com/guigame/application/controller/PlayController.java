package com.guigame.application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.guigame.application.model.MainGameModel;
import com.guigame.application.view.MainGameView;
import com.guigame.helper.type.CharacterType;
import com.guigame.player.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class PlayController implements Initializable {
	public ImageView playBackground;
	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	
	@FXML
	private Label player1Label,player2Label,player3Label,player4Label;
	@FXML
	private MenuButton player1Character,player1Position,player2Character,player2Position,
					player3Character,player3Position,player4Character,player4Position;
	@FXML
    private Button nextButton,player1OKButton,player2OKButton,player3OKButton,player4OKButton;
	
	
	private int numberOfPlayers = 0;
	@FXML
	private TextField numberOfPlayersField;
	public void initialize() {
		// Only allow numbers in the TextField
	    TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
	        String newText = change.getControlNewText();
	        if (newText.matches("\\d*")) { // allows empty string or any number of digits
	            return change;
	        }
	        return null;
	    });
	    numberOfPlayersField.setTextFormatter(formatter);
	}
	@FXML
	private void handleSubmitButtonAction(ActionEvent event) {
	    String input = numberOfPlayersField.getText();
	    if (input.matches("\\d+")) { // Check if the input is numeric
	        numberOfPlayers = Integer.parseInt(input);
	        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
	            showAlert();
	        } else {
	            handleValidInput();
	        }
	    } else {
	        showAlert();
	    }
	}
	private void showAlert() {
	    // Show an alert to the user
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("Invalid Input");
	    alert.setHeaderText(null);
	    alert.setContentText("Please enter a number between 2 and 4.");

	    alert.showAndWait();

	    // Clear the TextField
	    numberOfPlayersField.clear();
	    
	    // Hide the player labels
	    hidePlayerLabels();
	}
	private void handleValidInput() {
	    // Handle the valid input...
	    nextButton.setDisable(false);
	    nextButton.setOpacity(1);
	    hidePlayerLabels();
	    // Show player labels based on the number of players
	    if (numberOfPlayers >= 2) {
	        player1Label.setOpacity(1);
	        player1Character.setDisable(false);
	        player1Character.setOpacity(1);
	        player1Position.setDisable(false);
	        player1Position.setOpacity(1);
	        player1OKButton.setDisable(false);
	        player1OKButton.setOpacity(1);
	        
	        player2Label.setOpacity(1);
	        player2Character.setDisable(false);
	        player2Character.setOpacity(1);
	        player2Position.setDisable(false);
	        player2Position.setOpacity(1);
	        player2OKButton.setDisable(false);
	        player2OKButton.setOpacity(1);
	    }
	    if (numberOfPlayers >= 3) {
	        player3Label.setOpacity(1);
	        player3Character.setDisable(false);
	        player3Character.setOpacity(1);
	        player3Position.setDisable(false);
	        player3Position.setOpacity(1);
	        player3OKButton.setDisable(false);
	        player3OKButton.setOpacity(1);
	    }
	    if (numberOfPlayers >= 4) {
	        player4Label.setOpacity(1);
	        player4Character.setDisable(false);
	        player4Character.setOpacity(1);
	        player4Position.setDisable(false);
	        player4Position.setOpacity(1);
	        player4OKButton.setDisable(false);
	        player4OKButton.setOpacity(1);
	    }
	}
	private void hidePlayerLabels() {
	    player1Label.setOpacity(0.5);
	    player1Character.setDisable(true);
	    player1Character.setOpacity(0.5);
	    player1Position.setDisable(true);
	    player1Position.setOpacity(0.5);
	    player1OKButton.setDisable(true);
	    player1OKButton.setOpacity(0.5);
	    
	    player2Label.setOpacity(0.5);
	    player2Character.setDisable(true);
	    player2Character.setOpacity(0.5);
	    player2Position.setDisable(true);
	    player2Position.setOpacity(0.5);
	    player2OKButton.setDisable(true);
	    player2OKButton.setOpacity(0.5);
	    
	    player3Label.setOpacity(0.5);
	    player3Character.setDisable(true);
	    player3Character.setOpacity(0.5);
	    player3Position.setDisable(true);
	    player3Position.setOpacity(0.5);
	    player3OKButton.setDisable(true);
	    player3OKButton.setOpacity(0.5);
	    
	    player4Label.setOpacity(0.5);
	    player4Character.setDisable(true);
	    player4Character.setOpacity(0.5);
	    player4Position.setDisable(true);
	    player4Position.setOpacity(0.5);
	    player4OKButton.setDisable(true);
	    player4OKButton.setOpacity(0.5);
	}

	// Keep track of the number of each character type and the selected positions
	private int numberOfRabbits = 0;
	private int numberOfMice = 0;
	private boolean[][] positions = new boolean[5][5]; // Initialize to false
	
	// Handlers for the character and position selection of player 1
	private Player player1;
	private CharacterType selectedCharacterPlayer1 = null;
	private int selectedXPlayer1 = -1, selectedYPlayer1 = -1;
	@FXML
	private void handleRabbitSelectionPlayer1(ActionEvent event) {
	    handleCharacterSelectionPlayer1(CharacterType.RABBIT);
	}
	@FXML
	private void handleMouseSelectionPlayer1(ActionEvent event) {
	    handleCharacterSelectionPlayer1(CharacterType.MOUSE);
	}
	@FXML
	private void handleTopLeftSelectionPlayer1(ActionEvent event) {
	    handlePositionSelectionPlayer1(0, 0);
	}
	@FXML
	private void handleTopRightSelectionPlayer1(ActionEvent event) {
	    handlePositionSelectionPlayer1(0, 4);
	}
	@FXML
	private void handleBottomLeftSelectionPlayer1(ActionEvent event) {
	    handlePositionSelectionPlayer1(4, 0);
	}
	@FXML
	private void handleBottomRightSelectionPlayer1(ActionEvent event) {
	    handlePositionSelectionPlayer1(4, 4);
	}
	@FXML
	private void handleOKButtonPlayer1(ActionEvent event) {
	    createPlayer1IfPossible();
	}
	private void handleCharacterSelectionPlayer1(CharacterType type) {
		// Temporarily update the character count
	    int tempNumberOfRabbits = numberOfRabbits;
	    int tempNumberOfMice = numberOfMice;
	    if (selectedCharacterPlayer1 == CharacterType.RABBIT) tempNumberOfRabbits--;
	    if (selectedCharacterPlayer1 == CharacterType.MOUSE) tempNumberOfMice--;
	    if (type == CharacterType.RABBIT) tempNumberOfRabbits++;
	    if (type == CharacterType.MOUSE) tempNumberOfMice++;

	    // Check if there are maximum 2 rabbits and 2 mice
	    if (tempNumberOfRabbits > 2 || tempNumberOfMice > 2) {
	        showAlert("There can be maximum 2 rabbits and 2 mice.");
	        return;
	    }

	    // Update the character count
	    if (selectedCharacterPlayer1 == CharacterType.RABBIT) numberOfRabbits--;
	    if (selectedCharacterPlayer1 == CharacterType.MOUSE) numberOfMice--;
	    if (type == CharacterType.RABBIT) numberOfRabbits++;
	    if (type == CharacterType.MOUSE) numberOfMice++;

	    // Update the selected character
	    selectedCharacterPlayer1 = type;
	    player1Character.setText(type.toString());
	    System.out.println("Player 1 selected " + type);
	}
	private void handlePositionSelectionPlayer1(int x, int y) {
	    if (positions[x][y]) {
	        showAlert("This position has already been selected by another player.");
	        return;
	    }

	    // Update the selected position
	    if (selectedXPlayer1 != -1 && selectedYPlayer1 != -1) {
	        positions[selectedXPlayer1][selectedYPlayer1] = false;
	    }
	    positions[x][y] = true;
	    selectedXPlayer1 = x;
	    selectedYPlayer1 = y;
	    player1Position.setText(getPositionName(x, y));
	    System.out.println("Player 1 selected " + getPositionName(x, y));
	}
	private void createPlayer1IfPossible() {
	    if (selectedCharacterPlayer1 != null && selectedXPlayer1 != -1 && selectedYPlayer1 != -1) {
	        // Create a new player
	        player1 = new Player(selectedXPlayer1, selectedYPlayer1, selectedCharacterPlayer1);
	        player1.print();
	    }
	}
	// Handlers for the character and position selection of player 2
	private Player player2;
	private CharacterType selectedCharacterPlayer2 = null;
	private int selectedXPlayer2 = -1, selectedYPlayer2 = -1;
	@FXML
	private void handleRabbitSelectionPlayer2(ActionEvent event) {
	    handleCharacterSelectionPlayer2(CharacterType.RABBIT);
	}
	@FXML
	private void handleMouseSelectionPlayer2(ActionEvent event) {
	    handleCharacterSelectionPlayer2(CharacterType.MOUSE);
	}
	@FXML
	private void handleTopLeftSelectionPlayer2(ActionEvent event) {
	    handlePositionSelectionPlayer2(0, 0);
	}
	@FXML
	private void handleTopRightSelectionPlayer2(ActionEvent event) {
	    handlePositionSelectionPlayer2(0, 4);
	}
	@FXML
	private void handleBottomLeftSelectionPlayer2(ActionEvent event) {
	    handlePositionSelectionPlayer2(4, 0);
	}
	@FXML
	private void handleBottomRightSelectionPlayer2(ActionEvent event) {
	    handlePositionSelectionPlayer2(4, 4);
	}
	@FXML
	private void handleOKButtonPlayer2(ActionEvent event) {
	    createPlayer2IfPossible();
	}
	private void handleCharacterSelectionPlayer2(CharacterType type) {
		// Temporarily update the character count
	    int tempNumberOfRabbits = numberOfRabbits;
	    int tempNumberOfMice = numberOfMice;
	    if (selectedCharacterPlayer2 == CharacterType.RABBIT) tempNumberOfRabbits--;
	    if (selectedCharacterPlayer2 == CharacterType.MOUSE) tempNumberOfMice--;
	    if (type == CharacterType.RABBIT) tempNumberOfRabbits++;
	    if (type == CharacterType.MOUSE) tempNumberOfMice++;

	    // Check if there are maximum 2 rabbits and 2 mice
	    if (tempNumberOfRabbits > 2 || tempNumberOfMice > 2) {
	        showAlert("There can be maximum 2 rabbits and 2 mice.");
	        return;
	    }

	    // Update the character count
	    if (selectedCharacterPlayer2 == CharacterType.RABBIT) numberOfRabbits--;
	    if (selectedCharacterPlayer2 == CharacterType.MOUSE) numberOfMice--;
	    if (type == CharacterType.RABBIT) numberOfRabbits++;
	    if (type == CharacterType.MOUSE) numberOfMice++;

	    // Update the selected character
	    selectedCharacterPlayer2 = type;
	    player2Character.setText(type.toString());
	    System.out.println("Player 2 selected " + type);
	}
	private void handlePositionSelectionPlayer2(int x, int y) {
	    if (positions[x][y]) {
	        showAlert("This position has already been selected by another player.");
	        return;
	    }

	    // Update the selected position
	    if (selectedXPlayer2 != -1 && selectedYPlayer2 != -1) {
	        positions[selectedXPlayer2][selectedYPlayer2] = false;
	    }
	    positions[x][y] = true;
	    selectedXPlayer2 = x;
	    selectedYPlayer2 = y;
	    player2Position.setText(getPositionName(x, y));
	    System.out.println("Player 2 selected " + getPositionName(x, y));
	}
	private void createPlayer2IfPossible() {
	    if (selectedCharacterPlayer2 != null && selectedXPlayer2 != -1 && selectedYPlayer2 != -1) {
	        // Create a new player
	        player2 = new Player(selectedXPlayer2, selectedYPlayer2, selectedCharacterPlayer2);
	        player2.print();
	    }
	}
	// Handlers for the character and position selection of player 3
	private Player player3;
	private CharacterType selectedCharacterPlayer3 = null;
	private int selectedXPlayer3 = -1, selectedYPlayer3 = -1;
	@FXML
	private void handleRabbitSelectionPlayer3(ActionEvent event) {
	    handleCharacterSelectionPlayer3(CharacterType.RABBIT);
	}
	@FXML
	private void handleMouseSelectionPlayer3(ActionEvent event) {
	    handleCharacterSelectionPlayer3(CharacterType.MOUSE);
	}
	@FXML
	private void handleTopLeftSelectionPlayer3(ActionEvent event) {
	    handlePositionSelectionPlayer3(0, 0);
	}
	@FXML
	private void handleTopRightSelectionPlayer3(ActionEvent event) {
	    handlePositionSelectionPlayer3(0, 4);
	}
	@FXML
	private void handleBottomLeftSelectionPlayer3(ActionEvent event) {
	    handlePositionSelectionPlayer3(4, 0);
	}
	@FXML
	private void handleBottomRightSelectionPlayer3(ActionEvent event) {
	    handlePositionSelectionPlayer3(4, 4);
	}
	@FXML
	private void handleOKButtonPlayer3(ActionEvent event) {
	    createPlayer3IfPossible();
	}
	private void handleCharacterSelectionPlayer3(CharacterType type) {
		// Temporarily update the character count
	    int tempNumberOfRabbits = numberOfRabbits;
	    int tempNumberOfMice = numberOfMice;
	    if (selectedCharacterPlayer3 == CharacterType.RABBIT) tempNumberOfRabbits--;
	    if (selectedCharacterPlayer3 == CharacterType.MOUSE) tempNumberOfMice--;
	    if (type == CharacterType.RABBIT) tempNumberOfRabbits++;
	    if (type == CharacterType.MOUSE) tempNumberOfMice++;

	    // Check if there are maximum 2 rabbits and 2 mice
	    if (tempNumberOfRabbits > 2 || tempNumberOfMice > 2) {
	        showAlert("There can be maximum 2 rabbits and 2 mice.");
	        return;
	    }

	    // Update the character count
	    if (selectedCharacterPlayer3 == CharacterType.RABBIT) numberOfRabbits--;
	    if (selectedCharacterPlayer3 == CharacterType.MOUSE) numberOfMice--;
	    if (type == CharacterType.RABBIT) numberOfRabbits++;
	    if (type == CharacterType.MOUSE) numberOfMice++;

	    // Update the selected character
	    selectedCharacterPlayer3 = type;
	    player3Character.setText(type.toString());
	    System.out.println("Player 3 selected " + type);
	}
	private void handlePositionSelectionPlayer3(int x, int y) {
	    if (positions[x][y]) {
	        showAlert("This position has already been selected by another player.");
	        return;
	    }

	    // Update the selected position
	    if (selectedXPlayer3 != -1 && selectedYPlayer3 != -1) {
	        positions[selectedXPlayer3][selectedYPlayer3] = false;
	    }
	    positions[x][y] = true;
	    selectedXPlayer3 = x;
	    selectedYPlayer3 = y;
	    player3Position.setText(getPositionName(x, y));
	    System.out.println("Player 3 selected " + getPositionName(x, y));
	}
	private void createPlayer3IfPossible() {
	    if (selectedCharacterPlayer3 != null && selectedXPlayer3 != -1 && selectedYPlayer3 != -1) {
	        // Create a new player
	        player3 = new Player(selectedXPlayer3, selectedYPlayer3, selectedCharacterPlayer3);
	        player3.print();
	    }
	}
	// Handlers for the character and position selection of player 4
	private Player player4;
	private CharacterType selectedCharacterPlayer4 = null;
	private int selectedXPlayer4 = -1, selectedYPlayer4 = -1;
	@FXML
	private void handleRabbitSelectionPlayer4(ActionEvent event) {
	    handleCharacterSelectionPlayer4(CharacterType.RABBIT);
	}
	@FXML
	private void handleMouseSelectionPlayer4(ActionEvent event) {
	    handleCharacterSelectionPlayer4(CharacterType.MOUSE);
	}
	@FXML
	private void handleTopLeftSelectionPlayer4(ActionEvent event) {
	    handlePositionSelectionPlayer4(0, 0);
	}
	@FXML
	private void handleTopRightSelectionPlayer4(ActionEvent event) {
	    handlePositionSelectionPlayer4(0, 4);
	}
	@FXML
	private void handleBottomLeftSelectionPlayer4(ActionEvent event) {
	    handlePositionSelectionPlayer4(4, 0);
	}
	@FXML
	private void handleBottomRightSelectionPlayer4(ActionEvent event) {
	    handlePositionSelectionPlayer4(4, 4);
	}
	@FXML
	private void handleOKButtonPlayer4(ActionEvent event) {
	    createPlayer4IfPossible();
	}
	private void handleCharacterSelectionPlayer4(CharacterType type) {
		// Temporarily update the character count
	    int tempNumberOfRabbits = numberOfRabbits;
	    int tempNumberOfMice = numberOfMice;
	    if (selectedCharacterPlayer4 == CharacterType.RABBIT) tempNumberOfRabbits--;
	    if (selectedCharacterPlayer4 == CharacterType.MOUSE) tempNumberOfMice--;
	    if (type == CharacterType.RABBIT) tempNumberOfRabbits++;
	    if (type == CharacterType.MOUSE) tempNumberOfMice++;

	    // Check if there are maximum 2 rabbits and 2 mice
	    if (tempNumberOfRabbits > 2 || tempNumberOfMice > 2) {
	        showAlert("There can be maximum 2 rabbits and 2 mice.");
	        return;
	    }

	    // Update the character count
	    if (selectedCharacterPlayer4 == CharacterType.RABBIT) numberOfRabbits--;
	    if (selectedCharacterPlayer4 == CharacterType.MOUSE) numberOfMice--;
	    if (type == CharacterType.RABBIT) numberOfRabbits++;
	    if (type == CharacterType.MOUSE) numberOfMice++;

	    // Update the selected character
	    selectedCharacterPlayer4 = type;
	    player4Character.setText(type.toString());
	    System.out.println("Player 4 selected " + type);
	}
	private void handlePositionSelectionPlayer4(int x, int y) {
	    if (positions[x][y]) {
	        showAlert("This position has already been selected by another player.");
	        return;
	    }

	    // Update the selected position
	    if (selectedXPlayer4 != -1 && selectedYPlayer4 != -1) {
	        positions[selectedXPlayer4][selectedYPlayer4] = false;
	    }
	    positions[x][y] = true;
	    selectedXPlayer4 = x;
	    selectedYPlayer4 = y;
	    player4Position.setText(getPositionName(x, y));
	    System.out.println("Player 4 selected " + getPositionName(x, y));
	}
	private void createPlayer4IfPossible() {
	    if (selectedCharacterPlayer4 != null && selectedXPlayer4 != -1 && selectedYPlayer4 != -1) {
	        // Create a new player
	        player4 = new Player(selectedXPlayer4, selectedYPlayer4, selectedCharacterPlayer4);
	        player4.print();
	    }
	}
	
	private void showAlert(String message) {
	    // Show an alert to the user
	    Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("Invalid Selection");
	    alert.setHeaderText(null);
	    alert.setContentText(message);

	    alert.showAndWait();
	}
	private String getPositionName(int x, int y) {
	    String positionName = "";
	    if (x == 0 && y == 0) {
	        positionName = "TOP LEFT";
	    } else if (x == 0 && y == 4) {
	        positionName = "TOP RIGHT";
	    } else if (x == 4 && y == 0) {
	        positionName = "BOTTOM LEFT";
	    } else if (x == 4 && y == 4) {
	        positionName = "BOTTOM RIGHT";
	    }
	    return positionName;
	}
	
	public void switchToMainGameScene(ActionEvent event) throws IOException {
	    Player[] listOfPlayers = new Player[numberOfPlayers];
	    Player[] allPlayers = {player1,player2,player3,player4};
	    for (int i = 0; i < numberOfPlayers; i++) {
	        if (allPlayers[i] == null) {
	            // Show an alert to the user
	            Alert alert = new Alert(AlertType.WARNING);
	            alert.setTitle("Incomplete Selection");
	            alert.setHeaderText(null);
	            alert.setContentText("Player " + (i+1) + " has not finished their selection.");

	            alert.showAndWait();
	            return;
	        }
	        listOfPlayers[i] = allPlayers[i];
	    }
	    // Check if there is at least one rabbit and one mouse
	    if (numberOfRabbits < 1 || numberOfMice < 1) {
	        // Show an alert to the user
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setTitle("Invalid Selection");
	        alert.setHeaderText(null);
	        alert.setContentText("There must be at least one rabbit and one mouse.");

	        alert.showAndWait();
	        return;
	    }

		// Create the MainGameModel with the user input
        MainGameModel model = new MainGameModel(numberOfPlayers,listOfPlayers,numberOfRabbits,numberOfMice);

        // Create the MainGameView with the model
        MainGameView view = new MainGameView(model);
		
     	// Load the FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/guigame/application/view/MainGame.fxml"));
		root = (AnchorPane)loader.load();
		MainGameController controller = loader.getController(); // Get the controller instance created by FXMLLoader
		controller.setModel(model);
		controller.setView(view);
		controller.initializeGameLoop(); 
		// Draw the game before starting game loop
		view.update();
        GridPane gridPaneBoard = view.getGameBoard();
        Label clock = view.getClock();
        Label compass = view.getCompass();

        AnchorPane.setTopAnchor(clock, 20.0);
        AnchorPane.setRightAnchor(clock, 47.0);
        AnchorPane.setBottomAnchor(compass, 100.0);
        AnchorPane.setRightAnchor(compass, 40.0);
        root.getChildren().addAll(gridPaneBoard, clock, compass);
        
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToInitialMenuScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/InitialMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void initialize(URL location, ResourceBundle resources) {
		// Load the background image using getClass().getResource()
		Image backgroundImage = new Image("/playBackground.jpg");
		playBackground.setImage(backgroundImage);
	}
}