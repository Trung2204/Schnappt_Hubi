package com.guigame.application.controller;

import com.guigame.application.model.MainGameModel;
import com.guigame.application.model.MainGameModel.GameState;
import com.guigame.application.view.MainGameView;
import com.guigame.helper.type.ActionType;
import com.guigame.helper.type.DirectionType;
import com.guigame.player.Player;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainGameController {
	private MainGameModel model;
	private MainGameView view;
	private AnimationTimer gameLoop;
	
	@FXML
	private AnchorPane MainGameAnchorPane;
	@FXML
	private ImageView characterImage;
	@FXML
	private Button pressToStart,pressToStop,upButton,downButton,leftButton,rightButton,endTurnButton;
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
	public void stopGameLoop(ActionEvent event) {
		System.out.println("Game stops");
    	stopGame();
    }
	
	private ActionType actionChosen = null;
	private DirectionType directionChosen = null;
	// Handle player actions performance
	@FXML
    public void handleMove(ActionEvent event) {
		ActionTypes.setText("Move");
		actionChosen = ActionType.MOVE;
		System.out.println("Player selected Move");
		enableDirectionButtons();
	}
	@FXML
	public void handleViewCurtain(ActionEvent event) {
		ActionTypes.setText("View Curtain");
		actionChosen = ActionType.VIEW_CURTAIN;
		System.out.println("Player selected View Curtain");
		enableDirectionButtons();
	}
	@FXML
    public void handleOpenMagicDoor(ActionEvent event) {
		ActionTypes.setText("Open Magic Door");
		actionChosen = ActionType.OPEN_MAGIC_DOOR;
		System.out.println("Player selected Open Magic Door");
		enableDirectionButtons();
	}
	@FXML
    public void handleViewToken(ActionEvent event) {
		ActionTypes.setText("View Token");
		actionChosen = ActionType.VIEW_TOKEN;
		System.out.println("Player selected View Token");
		directionChosen = DirectionType.NONE;
	}
	@FXML
	public void handleUp(ActionEvent event) {
		directionChosen = DirectionType.UP;
		System.out.println("Player selected Up");
	}
	@FXML
	public void handleDown(ActionEvent event) {
		directionChosen = DirectionType.DOWN;
		System.out.println("Player selected Down");
	}
	@FXML
	public void handleLeft(ActionEvent event) {
		directionChosen = DirectionType.LEFT;
		System.out.println("Player selected Left");
	}
	@FXML
	public void handleRight(ActionEvent event) {
		directionChosen = DirectionType.RIGHT;
		System.out.println("Player selected Right");
	}
	@FXML
	public void handleEndTurn(ActionEvent event) {
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
    private Label playerIndexLabel,numberOfActionsLabel;
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
                        	Image newImage = new Image(getClass().getResource("/resources/rabbit_1.png").toExternalForm());
                        	characterImage.setImage(newImage);
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
                        	System.out.println("Player "+(model.getCurrentPlayerIndex()+1)+" is performing action");
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
                        	}
                        	// If an action and direction are chosen, perform the action
                            if (actionChosen != null && directionChosen != null && remainingActions > 0) {
                                model.performPlayerAction(actionChosen, directionChosen);
                                // Decrement the number of remaining actions if an action is performed successfully
                                if (model.isActionPerformed()) {
                                	currentPlayer.decrementNumberOfActions();
                                }
                                // Reset the action and direction
                                actionChosen = null;
                                directionChosen = null;
                                ActionTypes.setText("Action Types");
                                disableDirectionButtons();
                                model.setViewUpdated(true);
                            }
                            break;
                        case NEXT_PLAYER:
                            model.nextPlayer();
                            break;
                        case CHECK_GAME_OVER:
                            model.checkGameOver();
                            break;
                        case WIN:
                        	stopGame();
                        case LOSE:
                        	stopGame();
                    }
                    // Check if the view needs to be updated
                    if (model.isViewUpdated()) {
                        // Redraw the view based on the updated model
                        view.update();
                        GridPane gridPaneBoard = view.getGameBoard();
                        VBox clockCompassBox = view.getClockAndCompass();
                        // Set the VBox to the top right corner
                        AnchorPane.setTopAnchor(clockCompassBox, 500.0);
                        AnchorPane.setRightAnchor(clockCompassBox, 0.0);
                        
                        // Remove the old nodes
                        MainGameAnchorPane.getChildren().removeAll(gridPaneBoard, clockCompassBox);

                        // Add the updated nodes
                        MainGameAnchorPane.getChildren().addAll(gridPaneBoard, clockCompassBox);

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
        gameLoop.stop();
    }
}
