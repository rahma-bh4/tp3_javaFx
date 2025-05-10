
module tp4 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	//requires javafx.media;
	//requires javafx.swing;
	//requires javafx.web;
	
	// The key fix - open application package to both javafx.graphics, javafx.fxml AND javafx.base
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
