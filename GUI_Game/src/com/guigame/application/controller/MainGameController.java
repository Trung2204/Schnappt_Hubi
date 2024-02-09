package com.guigame.application.controller;

import java.io.IOException;

import com.guigame.application.model.MainGameModel;
import com.guigame.application.model.MainGameModel.GameState;
import com.guigame.application.view.MainGameView;
import com.guigame.application.model.helper.type.ActionType;
import com.guigame.application.model.helper.type.DirectionType;
import com.guigame.application.model.player.Player;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainGameController {
	private MainGameModel model;
	private MainGameView view;
	private AnimationTimer gameLoop;
	private boolean isGameStop;
	
	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	
	@FXML
	private AnchorPane MainGameAnchorPane;
	@FXML
	private Button pressToStart,pressToPause,goBackToSetting,upButton,downButton,leftButton,rightButton,endTurnButton;
	@FXML
	private MenuButton ActionTypes;
	@FXML
	private MenuItem actionOpenMagicDoor,actionViewToken;
	@FXML
    public void startGameLoop(ActionEvent event) {
    	Platform.runLater(() -> {
    		System.out.println("Game starts");
    		ActionTypes.setDisable(false);
    		endTurnButton.setDisable(false);
    		startGame();
    	});
    }
	@FXML
	public void pauseGameLoop(ActionEvent event) {
		System.out.println("Game pauses");
    	stopGame();
    }
	@FXML
	public void switchToPlayScene(ActionEvent event) throws IOException {
		stopGame();
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/guigame/application/view/Play.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	private ActionType actionChosen = null;
	private DirectionType directionChosen = null;
	// Handle player actions performance
	@FXML
    public void handleMove(ActionEvent event) {
		ActionTypes.setText("Move");
		actionChosen = ActionType.MOVE;
		enableDirectionButtons();
	}
	@FXML
	public void handleViewCurtain(ActionEvent event) {
		ActionTypes.setText("View Curtain");
		actionChosen = ActionType.VIEW_CURTAIN;
		enableDirectionButtons();
	}
	@FXML
    public void handleOpenMagicDoor(ActionEvent event) {
		ActionTypes.setText("Open Magic Door");
		actionChosen = ActionType.OPEN_MAGIC_DOOR;
		enableDirectionButtons();
	}
	@FXML
    public void handleViewToken(ActionEvent event) {
		ActionTypes.setText("View Token");
		actionChosen = ActionType.VIEW_TOKEN;
		directionChosen = DirectionType.NONE;
	}
	@FXML
	public void handleUp(ActionEvent event) {
		directionChosen = DirectionType.UP;
	}
	@FXML
	public void handleDown(ActionEvent event) {
		directionChosen = DirectionType.DOWN;
	}
	@FXML
	public void handleLeft(ActionEvent event) {
		directionChosen = DirectionType.LEFT;
	}
	@FXML
	public void handleRight(ActionEvent event) {
		directionChosen = DirectionType.RIGHT;
	}
	@FXML
	public void handleEndTurn(ActionEvent event) {
		if (isGameStop) startGame();
	    model.setCurrentState(GameState.NEXT_PLAYER);
	}
	private void enableDirectionButtons() {
		upButton.setDisable(false);
		downButton.setDisable(false);
		leftButton.setDisable(false);
		rightButton.setDisable(false);
	}
	private void disableDirectionButtons() {
		upButton.setDisable(true);
		downButton.setDisable(true);
		leftButton.setDisable(true);
		rightButton.setDisable(true);
	}
	
	// No-argument constructor
	public MainGameController() {}

	// Setter methods
    public void setModel(MainGameModel model) { this.model = model; }
    public void setView(MainGameView view) { this.view = view; }
    @FXML
    private Label playerIndexLabel,numberOfActionsLabel,performedActionLabel;
    private int remainingActions = 0;
    public void initializeGameLoop() {
    	model.findAdjacentPairs();
		// Initialize game loop
		this.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	Platform.runLater(() -> {
            		// Update the model based on the current game state
                    switch (model.getCurrentState()) {
                        case SPIN_COMPASS:
                        	String currentPlayerIndex = null;
                        	switch(model.getCurrentPlayerIndex()) {
                        	case 0:
                        		currentPlayerIndex = "ONE";
                        		break;
                        	case 1:
                        		currentPlayerIndex = "TWO";
                        		break;
                        	case 2:
                        		currentPlayerIndex = "THREE";
                        		break;
                        	case 3:
                        		currentPlayerIndex = "FOUR";
                        		break;
                        	}
                        	playerIndexLabel.setText("Player "+currentPlayerIndex);
                            model.spinCompass();
                            model.setViewUpdated(true); // Set viewUpdated to true as the game state has changed
                            break;
                        case UPDATE_CLOCK:
                            model.updateClock();
                            model.setViewUpdated(true);
                            break;
                        case UPDATE_PLAYER_ACTIONS:
                            model.updatePlayerActions();
                            model.setViewUpdated(true);
                            break;
                        case PERFORM_PLAYER_ACTION:
                        	// Get the number of actions of the current player
                        	Player currentPlayer = model.getListOfPlayers()[model.getCurrentPlayerIndex()];
                        	remainingActions = currentPlayer.getNumberOfActions();
                        	numberOfActionsLabel.setText("Number of remaining actions: "+remainingActions);
                            // Show action options based on the current phase of the game
                        	// Phase 1
                        	if (!model.isGhostActivated()) {
                        		// If Magic Door has been found, the action Open Magic Door is enable
                        		if (model.isMagicDoorFound()) {
                        			actionOpenMagicDoor.setVisible(true);
                        		}
                        	}
                        	// Phase 2
                        	else {
                        		// If Magic Door is opened, the action View Token is enable
                        		actionViewToken.setVisible(true);
								actionOpenMagicDoor.setVisible(false);
                        	}
                        	
                    		// If an action and direction are chosen, perform the action
                            if (actionChosen != null && directionChosen != null) {
                            	if (remainingActions > 0) {
                                    model.performPlayerAction(actionChosen, directionChosen);
                                    // Decrement the number of remaining actions if an action is performed successfully
                                    if (model.isActionPerformed()) {
                                    	currentPlayer.decrementNumberOfActions();
                                    	performedActionLabel.setVisible(false);
                                    } else { // If action is not performed
                                        if (model.isMoveOutOfBound()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("Cannot move out of the house!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isMoveThroughCurtain()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("This curtain needs to be opened first!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isRabbitsMoveThroughMouseHole()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("Rabbits cannot go through a mouse hole!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isMiceMoveThroughWindow()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("Mice cannot jump through a window!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isViewCurtainOutOfBound() ||  model.isOpenMagicDoorOutOfBound()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("There is nothing in this direction!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isViewOpenedCurtain()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("This curtain was opened!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isOpenNotMagicDoor()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("This is not the magic door!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isNoPlayerOtherSide()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("There must be at least one player on each side!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isViewRevealedToken()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("This token was revealed!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isRabbitsViewCheese()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("Rabbits can only view carrot tokens!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                        if (model.isMiceViewCarrot()) {
                                        	performedActionLabel.setVisible(true);
                                        	performedActionLabel.setText("Mice can only view cheese tokens!");
                                            performedActionLabel.setTextFill(Color.DARKRED);
                                        }
                                    }
                                    // Reset the action and direction
                                    actionChosen = null;
                                    directionChosen = null;
                                    ActionTypes.setText("Action Types");
                                    disableDirectionButtons();
                                    model.setViewUpdated(true);
                            	}else {
                            		stopGame();
                            		// Reset the action and direction
                                    actionChosen = null;
                                    directionChosen = null;
                                    ActionTypes.setText("Action Types");
                                    disableDirectionButtons();
                                    // Show an alert to the user in a new Thread
                                    new Thread(() -> {
                                        Platform.runLater(() -> {
                                            Alert alert = new Alert(AlertType.WARNING);
                                            alert.setTitle("Invalid action");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Current player has no actions left, please press End Turn to switch to the next player.");
                                            alert.showAndWait();
                                        });
                                    }).start();
                            	}
                            }
                            break;
                        case NEXT_PLAYER:
                            model.nextPlayer();
                            break;
                        case CHECK_GAME_OVER:
                            model.checkGameOver();
                            break;
                         // Handle game over...
                        case WIN:
                        	stopGame();
                        	// Show an alert to the user
                	        Alert alertWin = new Alert(AlertType.WARNING);
                	        alertWin.setTitle("CONGRATULATIONS!");
                	        alertWin.setHeaderText(null);
                	        alertWin.setContentText("Hura! You found the Hubi before midnight.");

                	        alertWin.showAndWait();
                        	System.out.println("Game over! Congratulations, you win!");
                        	break;
                        case LOSE:
                        	stopGame();
                        	// Show an alert to the user
                	        Alert alertLose = new Alert(AlertType.WARNING);
                	        alertLose.setTitle("TIME'S UP!");
                	        alertLose.setHeaderText(null);
                	        alertLose.setContentText("Oops! You lose :( Better luck next time.");

                	        alertLose.showAndWait();
                        	System.out.println("\nTIME'S UP");
                        	System.out.println("You lose! Better luck next time");
                        	break;
                    }
                    // Check if the view needs to be updated
                    if (model.isViewUpdated()) {
                        // Redraw the view based on the updated model
                        view.update();
                        GridPane gridPaneBoard = view.getGameBoard();
                        Label clock = view.getClock();
                        Label compass = view.getCompass();
                        
                        AnchorPane.setTopAnchor(clock, 20.0);
                        AnchorPane.setRightAnchor(clock, 47.0);
                        AnchorPane.setBottomAnchor(compass, 100.0);
                        AnchorPane.setRightAnchor(compass, 40.0);
                        
                        // Remove the old nodes
                        MainGameAnchorPane.getChildren().removeAll(gridPaneBoard, clock, compass);

                        // Add the updated nodes
                        MainGameAnchorPane.getChildren().addAll(gridPaneBoard, clock, compass);

                        // Reset the view updated flag
                        model.setViewUpdated(false);
                    }
            	});
            }
        };
    }
    
    // Start the game loop
    private void startGame() {
		gameLoop.start();
    }
	// Stop the game loop
    private void stopGame() {
    	isGameStop = true;
        gameLoop.stop();
    }
}
