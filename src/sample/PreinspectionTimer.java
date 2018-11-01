package sample;

import javafx.concurrent.Task;
import javafx.scene.control.Label;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreinspectionTimer extends  Thread{

        Label timerLabel;
        private boolean isRunning;

        public PreinspectionTimer(Label timerLabel) {
            this.timerLabel = timerLabel;
        }


        @Override
        public void run() {

            isRunning = true;
            Task task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
            int i = 15;
                    while (isRunning) {

                        if(i <= 0) {
                            updateMessage("DNS");
                            isRunning = false;
                            break;
                        }

                        updateMessage(String.valueOf(i));
                        System.out.println(String.valueOf(i));
                        Thread.sleep(1000);
                        i--;
                    }
                    return null;
                }
            };
            timerLabel.textProperty().bind(task.messageProperty());
            new Thread(task).start();
        }

        public void stopPreinspection(){
            isRunning = false;
        }
}
