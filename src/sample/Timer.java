package sample;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class representing the new thread of the timer
 */

public class Timer  extends Thread {

    private long time, startTime;
    private boolean isRunning = true;
    Label timerLabel;

    public Timer(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public long getTime() {
        return time;
    }

    public void stopTimer(){
        isRunning = false;
    }

    @Override
    public void run() {

        time = 0;
        isRunning = true;
        startTime = getCurrentTime();

        Task task = new Task<Void>() {
            @Override
            public Void call() throws InterruptedException {

                while (isRunning) {
                        updateTime();

                        updateMessage(getStringValueOfTime(time));
                        System.out.println(getStringValueOfTime(time));
                        Thread.sleep(25);

                }
                return null;
            }
        };
        timerLabel.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }



    /*
    * Time formatting for the user
    * */

    public static String getStringValueOfTime (long time){

        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("mm:ss.SSS");
        String timeFormatted = formatter.format(date);

        return String.valueOf(timeFormatted);
    }

    public long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public void updateTime(){
        time = getCurrentTime() - startTime;
    }

}

