package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("vue.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        primaryStage.setTitle("Labyrinthe");
        primaryStage.setScene(new Scene(root, 722, 525));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
