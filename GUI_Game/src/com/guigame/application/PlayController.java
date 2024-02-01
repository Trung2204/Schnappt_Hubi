package com.guigame.application;

import java.io.IOException;

import com.guigame.helper.type.CharacterType;
import com.guigame.player.Player;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlayController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
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
	@FXML
	private void handleSubmitButtonAction(ActionEvent event) {
		numberOfPlayers = Integer.parseInt(numberOfPlayersField.getText());
        nextButton.setDisable(false);
        nextButton.setOpacity(1);
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
	
	// Handler for player 1's selection of character and position
	private Player player1;
	private CharacterType selectedCharacterPlayer1 = null;
	private int selectedXPlayer1 = -1, selectedYPlayer1 = -1;
	@FXML
	private void handleRabbitSelectionPlayer1(ActionEvent event) {
		player1Character.setText("RABBIT");
		selectedCharacterPlayer1 = CharacterType.RABBIT;
		System.out.println("Player 1 selected Rabbit");
	}
	@FXML
	private void handleMouseSelectionPlayer1(ActionEvent event) {
		player1Character.setText("MOUSE");
		selectedCharacterPlayer1 = CharacterType.MOUSE;
		System.out.println("Player 1 selected Mouse");
	}
	@FXML
    private void handleTopLeftSelectionPlayer1(ActionEvent event) {
		player1Position.setText("TOP LEFT");
		selectedXPlayer1 = 0;
		selectedYPlayer1 = 0;
        System.out.println("Player 1 selected Top left");
    }
	@FXML
    private void handleTopRightSelectionPlayer1(ActionEvent event) {
		player1Position.setText("TOP RIGHT");
		selectedXPlayer1 = 0;
		selectedYPlayer1 = 4;
        System.out.println("Player 1 selected Top right");
    }
	@FXML
    private void handleBottomLeftSelectionPlayer1(ActionEvent event) {
		player1Position.setText("BOTTOM LEFT");
		selectedXPlayer1 = 4;
		selectedYPlayer1 = 0;
        System.out.println("Player 1 selected Bottom left");
    }
	@FXML
    private void handleBottomRightSelectionPlayer1(ActionEvent event) {
		player1Position.setText("BOTTOM RIGHT");
		selectedXPlayer1 = 4;
		selectedYPlayer1 = 4;
        System.out.println("Player 1 selected Bottom right");
    }
	@FXML
	private void handleOKButtonPlayer1(ActionEvent event) {
		createPlayer1IfPossible();
	}
	private void createPlayer1IfPossible() {
        if (selectedCharacterPlayer1 != null && selectedXPlayer1 != -1 && selectedYPlayer1 != -1) {
            // Create a new player
            player1 = new Player(selectedXPlayer1, selectedYPlayer1, selectedCharacterPlayer1);
            player1.print();
        }
    }
		
	// Handler for player 2's selection of character and position
	private Player player2;
	private CharacterType selectedCharacterPlayer2 = null;
	private int selectedXPlayer2 = -1, selectedYPlayer2 = -1;
	@FXML
	private void handleRabbitSelectionPlayer2(ActionEvent event) {
		player2Character.setText("RABBIT");
		selectedCharacterPlayer2 = CharacterType.RABBIT;
		System.out.println("Player 2 selected Rabbit");
	}
	@FXML
	private void handleMouseSelectionPlayer2(ActionEvent event) {
		player2Character.setText("MOUSE");
		selectedCharacterPlayer2 = CharacterType.MOUSE;
		System.out.println("Player 2 selected Mouse");
	}
	@FXML
    private void handleTopLeftSelectionPlayer2(ActionEvent event) {
		player2Position.setText("TOP LEFT");
		selectedXPlayer2 = 0;
		selectedYPlayer2 = 0;
        System.out.println("Player 2 selected Top left");
    }
	@FXML
    private void handleTopRightSelectionPlayer2(ActionEvent event) {
		player2Position.setText("TOP RIGHT");
		selectedXPlayer2 = 0;
		selectedYPlayer2 = 4;
        System.out.println("Player 2 selected Top right");
    }
	@FXML
    private void handleBottomLeftSelectionPlayer2(ActionEvent event) {
		player2Position.setText("BOTTOM LEFT");
		selectedXPlayer2 = 4;
		selectedYPlayer2 = 0;
        System.out.println("Player 2 selected Bottom left");
    }
	@FXML
    private void handleBottomRightSelectionPlayer2(ActionEvent event) {
		player2Position.setText("BOTTOM RIGHT");
		selectedXPlayer2 = 4;
		selectedYPlayer2 = 4;
        System.out.println("Player 2 selected Bottom right");
    }
	@FXML
	private void handleOKButtonPlayer2(ActionEvent event) {
		createPlayer2IfPossible();
	}
	private void createPlayer2IfPossible() {
        if (selectedCharacterPlayer2 != null && selectedXPlayer2 != -1 && selectedYPlayer2 != -1) {
            // Create a new player
            player2 = new Player(selectedXPlayer2, selectedYPlayer2, selectedCharacterPlayer2);
            player2.print();
        }
    }
		
	// Handler for player 3's selection of character and position
	private Player player3;
	private CharacterType selectedCharacterPlayer3 = null;
	private int selectedXPlayer3 = -1, selectedYPlayer3 = -1;
	@FXML
	private void handleRabbitSelectionPlayer3(ActionEvent event) {
		player3Character.setText("RABBIT");
		selectedCharacterPlayer3 = CharacterType.RABBIT;
		System.out.println("Player 3 selected Rabbit");
	}
	@FXML
	private void handleMouseSelectionPlayer3(ActionEvent event) {
		player3Character.setText("MOUSE");
		selectedCharacterPlayer3 = CharacterType.MOUSE;
		System.out.println("Player 3 selected Mouse");
	}
	@FXML
    private void handleTopLeftSelectionPlayer3(ActionEvent event) {
		player3Position.setText("TOP LEFT");
		selectedXPlayer3 = 0;
		selectedYPlayer3 = 0;
        System.out.println("Player 3 selected Top left");
    }
	@FXML
    private void handleTopRightSelectionPlayer3(ActionEvent event) {
		player3Position.setText("TOP RIGHT");
		selectedXPlayer3 = 0;
		selectedYPlayer3 = 4;
        System.out.println("Player 3 selected Top right");
    }
	@FXML
    private void handleBottomLeftSelectionPlayer3(ActionEvent event) {
		player3Position.setText("BOTTOM LEFT");
		selectedXPlayer3 = 4;
		selectedYPlayer3 = 0;
        System.out.println("Player 3 selected Bottom left");
    }
	@FXML
    private void handleBottomRightSelectionPlayer3(ActionEvent event) {
		player3Position.setText("BOTTOM RIGHT");
		selectedXPlayer3 = 4;
		selectedYPlayer3 = 4;
        System.out.println("Player 3 selected Bottom right");
    }
	@FXML
	private void handleOKButtonPlayer3(ActionEvent event) {
		createPlayer3IfPossible();
	}
	private void createPlayer3IfPossible() {
        if (selectedCharacterPlayer3 != null && selectedXPlayer3 != -1 && selectedYPlayer3 != -1) {
            // Create a new player
            player3 = new Player(selectedXPlayer3, selectedYPlayer3, selectedCharacterPlayer3);
            player3.print();
        }
    }
	
	// Handler for player 4's selection of character and position
	private Player player4;
	private CharacterType selectedCharacterPlayer4 = null;
	private int selectedXPlayer4 = -1, selectedYPlayer4 = -1;
	@FXML
	private void handleRabbitSelectionPlayer4(ActionEvent event) {
		player4Character.setText("RABBIT");
		selectedCharacterPlayer4 = CharacterType.RABBIT;
		System.out.println("Player 4 selected Rabbit");
	}
	@FXML
	private void handleMouseSelectionPlayer4(ActionEvent event) {
		player4Character.setText("MOUSE");
		selectedCharacterPlayer4 = CharacterType.MOUSE;
		System.out.println("Player 4 selected Mouse");
	}
	@FXML
    private void handleTopLeftSelectionPlayer4(ActionEvent event) {
		player4Position.setText("TOP LEFT");
		selectedXPlayer4 = 0;
		selectedYPlayer4 = 0;
        System.out.println("Player 4 selected Top left");
    }
	@FXML
    private void handleTopRightSelectionPlayer4(ActionEvent event) {
		player4Position.setText("TOP RIGHT");
		selectedXPlayer4 = 0;
		selectedYPlayer4 = 4;
        System.out.println("Player 4 selected Top right");
    }
	@FXML
    private void handleBottomLeftSelectionPlayer4(ActionEvent event) {
		player4Position.setText("BOTTOM LEFT");
		selectedXPlayer4 = 4;
		selectedYPlayer4 = 0;
        System.out.println("Player 4 selected Bottom left");
    }
	@FXML
    private void handleBottomRightSelectionPlayer4(ActionEvent event) {
		player4Position.setText("BOTTOM RIGHT");
		selectedXPlayer4 = 4;
		selectedYPlayer4 = 4;
        System.out.println("Player 4 selected Bottom right");
    }
	@FXML
	private void handleOKButtonPlayer4(ActionEvent event) {
		createPlayer4IfPossible();
	}
	private void createPlayer4IfPossible() {
        if (selectedCharacterPlayer4 != null && selectedXPlayer4 != -1 && selectedYPlayer4 != -1) {
            // Create a new player
            player4 = new Player(selectedXPlayer4, selectedYPlayer4, selectedCharacterPlayer4);
            player4.print();
        }
    }
	
	public void switchToMainGameScene(ActionEvent event) throws IOException {
		Player[] listOfPlayers = new Player[numberOfPlayers];
		Player[] allPlayers = {player1,player2,player3,player4};
		for (int i = 0; i < numberOfPlayers; i++) {
		    listOfPlayers[i] = allPlayers[i];
		}
		int numberOfRabbits = 0;
		int numberOfMice = 0;
		for (Player player : listOfPlayers) {
			if (player.getCharacter() == CharacterType.MOUSE) numberOfMice++;
			else if (player.getCharacter() == CharacterType.RABBIT) numberOfRabbits++;
		}
		// Create the MainGameModel with the user input
        MainGameModel model = new MainGameModel(numberOfPlayers,listOfPlayers,numberOfRabbits,numberOfMice);

        // Create the MainGameView with the model
        MainGameView view = new MainGameView(model);

        // Create the MainGameController with the model and view
        MainGameController controller = new MainGameController();
        controller.setModel(model);
        controller.setView(view);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGame.fxml"));
		root = (AnchorPane)loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToInitialMenuScene(ActionEvent event) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("InitialMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}