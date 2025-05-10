module tp4 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	//requires javafx.media;
	//requires javafx.swing;
	//requires javafx.web;
	
	opens application to javafx.graphics, javafx.fxml;
}
