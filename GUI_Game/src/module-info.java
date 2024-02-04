module GUI_Game {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;

	opens com.guigame.application to javafx.graphics, javafx.fxml;
	opens com.guigame.application.view to javafx.fxml;
	opens com.guigame.application.controller to javafx.fxml;
}
