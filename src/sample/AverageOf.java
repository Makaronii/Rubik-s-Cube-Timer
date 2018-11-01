package sample;

public class AverageOf {

    private Time currentTimes[], bestTimes[];
    private double currentAVG, bestAVG = 999999999;

    public AverageOf(int x){
        currentTimes = new Time[x];
        bestTimes = new Time[x];
    }

    public void setCurrentTime(int index, Time time){
        currentTimes[index] = time;
    }

    public void setAVG(double avgValue){
        this.currentAVG = avgValue;

        if(bestAVG > currentAVG)
            setBestAVG(this);
    }

    public int getAverageSize(){
        return currentTimes.length;
    }

    public Time[] getCurrentTimes(){
        return currentTimes;
    }

    public void setBestAVG(AverageOf values){
        this.bestTimes = values.getCurrentTimes();
        this.bestAVG = values.getCurrentAVG();
    }

    public double getCurrentAVG(){
        return currentAVG;
    }

    public String getBestAVG(){
        return Timer.getStringValueOfTime((long)bestAVG);
    }

    @Override
    public String toString(){
            return Timer.getStringValueOfTime((long)currentAVG);
    }
}
