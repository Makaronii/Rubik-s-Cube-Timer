package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Main Controller
 * */
public class Controller implements Initializable {

    public static ObservableList<Time> timesList = FXCollections.observableArrayList();
    Statistics stats = new Statistics(this);
    Timer timer;
    PreinspectionTimer preinspectionTimer;
    public Boolean preinspectionIsRunning = false, isRunning = false;
    public long time;
    Scramble scramble = new Scramble(30);
    static TimesFile file;

    @FXML
    Button plusTwoButton, dnfButton, nextScrambleButton, copyScrambleButton, clearButton;
    @FXML
    CheckBox preinspectionCheckBox;
    @FXML
    Label timerLabel;
    @FXML
    TextArea scrambleTextArea;
    @FXML
    ListView timesListView;
    @FXML
    TextArea resultsTextArea = new TextArea();

    public void initialize(URL location, ResourceBundle resources){

        file = new TimesFile();
        stats.initStatistics();
        timesListView.setItems(timesList);

        preinspectionCheckBox.setSelected(false);

        //Delete selected Time
        timesListView.setOnKeyPressed(e -> {
            if (KeyCode.DELETE == e.getCode() ){
                timesList.remove(timesListView.getSelectionModel().getSelectedIndex());
                stats.initStatistics();
                timesListView.setItems(timesList);
            }

        });

        // Generate first scramble
        scrambleTextArea.setText(scramble.getScramble());

        // Open Time Editor Window (TimePopup) for selected time(item) in timesList(timesListView)
        timesListView.setCellFactory( lv -> {
            ListCell<Time> cell = new ListCell<Time>() {
                @Override
                protected void updateItem(Time item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            };

            // Checking for double click on non empty item and create new TimePopup window
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! cell.isEmpty()) ) {
                    int index = cell.getIndex();
                    new TimePopup(index, stats);
                }
            });
            return cell ;
        });
    }

    /**
    * Detecting if the Spacebar was pressed -> Starting the timer or pre-inspection
    * */
    public void detectSpaceKeyEvent(KeyEvent event){
        if (KeyCode.SPACE == event.getCode()) {
            if(preinspectionCheckBox.isSelected() && !isRunning){
               preinspectionCheck();
            }
            else
                timerCheck();
            }
    }

    /**
     * This method checks and supports the pre-inspection
      */
    public void preinspectionCheck(){
        if (preinspectionIsRunning == false){
            preinspectionTimer = new PreinspectionTimer(timerLabel);
            preinspectionTimer.start();
            preinspectionIsRunning = true;
        }
        else {
            if(timerLabel.getText() == "DNS"){
                updateTimesList(new Time(scramble));
            }
            else {
                preinspectionTimer.stopPreinspection();
                timerCheck();
            }
            preinspectionIsRunning = false;
        }
    }

    // Checking status of timer
    private void timerCheck (){
        if(!isRunning){
            startTimer();
        }
        else{
            stopTimer();
        }
    }

    private void stopTimer(){
        timer.stopTimer();
        isRunning = false;
        updateTimesList(new Time(timer.getTime(), scramble));
        timerLabel.textProperty().unbind(); //Unbind from Timer Thread
        // Reset buttons
        dnfButton.setDisable(false);
        plusTwoButton.setDisable(false);
    }

    /**
    Starting new Thread from custom Timer with timerLabel reference
     */
    private void startTimer(){
            timer = new Timer(timerLabel);
            timer.start();
            isRunning = true;
    }

    public void updateScramble(){
        scramble.makeScramble(30);
        scrambleTextArea.setText(scramble.getScramble());
    }

    public void setDNF(){
        Time time = timesList.get(getLastIndexOfTimesList());
        time.setDNF();

        timesList.set(getLastIndexOfTimesList(), time);
        timerLabel.setText(time.toString());

        dnfButton.setDisable(true);
    }

    public int getLastIndexOfTimesList(){
        return timesList.size()-1;
    }

    public void setPlusTwo(){
        Time time = timesList.get(getLastIndexOfTimesList());
        time.setPlusTwo();

        timesList.set(getLastIndexOfTimesList(), time);
        timerLabel.setText(time.toString());

        plusTwoButton.setDisable(true);
    }

    public void updateTimesList(Time time){
        timesList.add(time);
        timesListView.setItems(timesList);
        updateScramble();
        stats.refreshStatistics(); //update statistics and resultsTextArea ( Controller reference in Statistics constructor )
    }

    /**
     * This method clear all times with confirm popup window
     */
    public void clearTimes(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setHeaderText("Are you sure to delete all times?");
        alert.setContentText("There will be no return");
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.getButtonData() == ButtonBar.ButtonData.YES) {
                for(int i = timesList.size()-1; i >= 0; i--)
                    timesList.remove(i);
                timesListView.setItems(timesList);
                resultsTextArea.setText("");
            }
        });
    }

}
