package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TimesFile {

    File timesFile;
    Scanner scanner;
    PrintWriter writer;

    public TimesFile() {
        timesFile = new File("Times.txt");
        if (!timesFile.exists()) {
            createFile(timesFile);
        }

        getTimes();
    }

    public void getTimes() {

        long time;
        Scramble scramble = new Scramble(30);
        boolean isPlusTwo, isDNF, isDNS;

        try {
            scanner = new Scanner(timesFile);

            while (scanner.hasNext()) {
                scanner.nextLine();
                time = scanner.nextLong();
                scanner.nextLine();
                scramble.setScramble(scanner.nextLine());
                isPlusTwo = scanner.nextBoolean();
                scanner.nextLine();
                isDNF = scanner.nextBoolean();
                scanner.nextLine();
                isDNS = scanner.nextBoolean();

                Time t = new Time(time, scramble);
                if (isPlusTwo)
                    t.setPlusTwo();
                if (isDNF)
                    t.setDNF();
                if (isDNS)
                    t.setDNS();

                System.out.println(t.toString());
                Controller.timesList.add(t);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTimes() {
        try {
            writer = new PrintWriter(timesFile);
            writer.println("");

            for (Time time:Controller.timesList) {
                writer.println(time.getTime());
                writer.println(time.getScramble());
                writer.println(time.isPlusTwo());
                writer.println(time.isDNF());
                writer.println(time.isDNF());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.close();
    }

}

