package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
           
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddBook.fxml"));
            Pane root = loader.load();

       
            Scene scene = new Scene(root, 695, 590); 

            primaryStage.setTitle("Carnet d'adresses");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}