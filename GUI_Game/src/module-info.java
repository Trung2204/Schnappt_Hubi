module GUI_Game {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens com.guigame.application to javafx.graphics, javafx.fxml;
}
