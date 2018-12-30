package sample;

import static java.sql.Types.NULL;

/**
* This class represends time object
*/
public class Time {

    private long time;
    private String stringValueOfTime;
    private String scramble;
    private boolean isPlusTwo = false, isDNF = false, isDNS = false;

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }

    // Constructor for DNS time
    public Time(Scramble scramble){
        this.scramble = scramble.getScramble();
        setDNS();
    }

    public Time (long time, Scramble scramble){
        this.time = time;
        this.stringValueOfTime = Timer.getStringValueOfTime(time);
        this.scramble = scramble.getScramble();
    }


    public String toString() {
        return stringValueOfTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
        this.stringValueOfTime = Timer.getStringValueOfTime(time);
    }

    public void setPlusTwo(){
        time += 2000;
        stringValueOfTime = Timer.getStringValueOfTime(time);
        isPlusTwo = true;
    }

    public void setDNF(){
        time = NULL;
        stringValueOfTime = "DNF";
        isDNF = true;
        isPlusTwo = true;
    }

    public void setDNS(){
        time = NULL;
        stringValueOfTime = "DNS";
        isDNS = true;
    }

    public boolean isPlusTwo(){
        return isPlusTwo;
    }

    public boolean isDNF(){
        return isDNF;
    }

    public boolean isDNS(){
        return isDNS;
    }
}
