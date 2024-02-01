module GUI_Game {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens com.guigame.application to javafx.graphics, javafx.fxml;
}
