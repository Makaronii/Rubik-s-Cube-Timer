package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TimePopup{


    private static String scramble;
    private static Time time;
    public static Stage stage;
    private static int index;
    public static Statistics stats;


    public static int getIndex(){
        return index;
    }

    public static Time getTime() {
        return time;
    }


    public static String getScramble() {
        return scramble;
    }


    public TimePopup(int index, Statistics stats){
        this.index = index;
        this.time = Controller.timesList.get(index);
        this.scramble = time.getScramble();
        this.stats = stats;

        stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("fxmlFiles/timepopup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 600,300);
        stage.setTitle("Okno czasu");

        stage.setScene(scene);
        stage.show();
    }


}








