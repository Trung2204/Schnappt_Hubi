package com.guigame.application;

import java.io.IOException;

import com.guigame.helper.type.ActionType;
import com.guigame.helper.type.DirectionType;
import com.guigame.helper.type.FieldType;
import com.guigame.player.Player;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGameController {
	private MainGameModel model;
	private MainGameView view;
	private AnimationTimer gameLoop;
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private Button pressToStart;
	@FXML
    public void startGameLoop(ActionEvent event) {
    	Platform.runLater(() -> {
    		startGame();
    	});
    }
	
	private ActionType actionChosen = null;
	private DirectionType directionChosen = null;
	// Handle player 1 action performance
	@FXML
	private MenuButton player1ActionTypes;
	@FXML
    public void handleMoveActionSelectionPlayer1(ActionEvent event) {
		player1ActionTypes.setText("Move");
		actionChosen = ActionType.MOVE;
		System.out.println("Player selected Move");
	}
	@FXML
	public void handleViewCurtainActionSelectionPlayer1(ActionEvent event) {
		actionChosen = ActionType.VIEW_CURTAIN;
		System.out.println("Player selected View Curtain");
	}
	@FXML
	public void handleDownDirectionSelectionPlayer1(ActionEvent event) {
		directionChosen = DirectionType.DOWN;
		System.out.println("Player selected Down");
	}
	
	// No-argument constructor
	public MainGameController() {}

	// Setter methods
    public void setModel(MainGameModel model) { this.model = model; }
    public void setView(MainGameView view) { this.view = view; }
    
    public void initializeGameLoop() {
		// Initialize game loop
		this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	Platform.runLater(() -> {
            		if (model.getWin()) {
            			System.out.println("You win! Congratulations!");
            			stopGame();
            		}
            		if (model.getClock().getTime() == 12) {
            			System.err.println("\nTIME'S UP");
            			if (!model.getWin()) {
            				System.out.println("You lose! Better luck next time");
            				stopGame();
            			}
            		}
            		// Update the model based on the current game state
                    switch (model.getCurrentState()) {
                    	case FIND_ADJACENT_PAIRS:
                            model.findAdjacentPairsState();
                            break;
                        case SPIN_COMPASS:
                            model.spinCompass();
                            // Set viewUpdated to true as the game state has changed
                            model.setViewUpdated(true);
                            break;
                        case SWAP_GHOST:
                            model.swapGhost();
                            break;
                        case UPDATE_CLOCK:
                            model.updateClock();
                            // Set viewUpdated to true as the game state has changed
                            model.setViewUpdated(true);
                            break;
                        case UPDATE_PLAYER_ACTIONS:
                            model.updatePlayerActions();
                            // Set viewUpdated to true as the game state has changed
                            model.setViewUpdated(true);
                            break;
                        case PERFORM_PLAYER_ACTION:
                        	Player currentPlayer = model.getListOfPlayers()[model.getCurrentPlayerIndex()];
                        	while (currentPlayer.getNumberOfActions() > 0 && actionChosen != null && directionChosen != null) {
                        		model.performPlayerAction(actionChosen, directionChosen);
                                // Reset the action and direction
                                actionChosen = null;
                                directionChosen = null;
                                // Decrement the number of actions
                                currentPlayer.decrementNumberOfActions();
                                // Set viewUpdated to true as the game state has changed
                                model.setViewUpdated(true);
                        	}
                            break;
                        case REMOVE_PAIRS:
                            model.removePairsState(model.getListOfPlayers()[model.getCurrentPlayerIndex()].getX(), model.getListOfPlayers()[model.getCurrentPlayerIndex()].getY());
                            break;
                        case NEXT_PLAYER:
                            model.nextPlayer();
                            break;
                        case CHECK_GAME_OVER:
                            model.checkGameOver();
                            break;
                    }
                    // Check if the view needs to be updated
                    if (model.isViewUpdated()) {
                        // Redraw the view based on the updated model
                        view.update();
                        GridPane gridPaneBoard = view.getGameBoard();
                        VBox clockCompassBox = view.getClockAndCompass();
                        // Set the VBox to the top right corner
                        AnchorPane.setTopAnchor(clockCompassBox, 0.0);
                        AnchorPane.setRightAnchor(clockCompassBox, 0.0);
                        
                        // Remove the old nodes
                        root.getChildren().removeAll(gridPaneBoard, clockCompassBox);

                        // Add the updated nodes
                        root.getChildren().addAll(gridPaneBoard, clockCompassBox);

                        // Reset the view updated flag
                        model.setViewUpdated(false);
                    }
//                    // Redraw the view based on the updated model
//                    view.update();
//                    GridPane gridPaneBoard = view.getGameBoard();
//                    VBox clockCompassBox = view.getClockAndCompass();
//                    // Set the VBox to the top right corner
//                    AnchorPane.setTopAnchor(clockCompassBox, 0.0);
//                    AnchorPane.setRightAnchor(clockCompassBox, 0.0);
//                    
//                    // Clear the root children and add the updated nodes
//                    root.getChildren().removeAll(gridPaneBoard, clockCompassBox);
//                    root.getChildren().addAll(gridPaneBoard, clockCompassBox);
            	});
            }
        };
    }
    
	// Start the game loop
	public void startGame() {
		gameLoop.start();
    }
	// Stop the game loop
    public void stopGame() {
        gameLoop.stop();
    }
}
