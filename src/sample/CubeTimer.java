package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CubeTimer extends Application {

    static Stage primaryStage;
    static Parent root;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public Parent getRoot(){
        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxmlFiles/sample.fxml"));
        System.out.println(getClass().getResource("fxmlFiles/sample.fxml").toString());

        primaryStage.setTitle("Rubik's Cube Timer");
        primaryStage.setScene(new Scene(root, 900, 450));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
