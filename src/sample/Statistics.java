package sample;

import javafx.collections.ObservableList;


/**
 * This class is used to calculate statistics for the obtained times
 */
public class Statistics {

    Controller c;
    private ObservableList<Time> timesList;
    public int lastIndex;
    private Time bestTime, worstTime, currentTime;
    private AverageOf AVG5 = new AverageOf(5), AVG12 = new AverageOf(12);
    public static final double DNF_CODE = -111;

    public Statistics(Controller c)
    {
        // get reference from Controller
        this.c = c;
        timesList = c.timesList;
    }


    public void refreshStatistics(){
        lastIndex = timesList.size()-1;

        // Statistics are Empty
        if(timesList.size() <= 0)
            return;

        // First time is now best and worst single time
        if(timesList.size() == 1){
            bestTime = timesList.get(0);
            worstTime = timesList.get(0);
        }

        // Best Single Time Check
        if (timesList.get(lastIndex).getTime() < bestTime.getTime()){
            bestTime = timesList.get(lastIndex);
        }

        // Worst Single Time Check
        if (timesList.get(lastIndex).getTime() > worstTime.getTime()){
            worstTime = timesList.get(lastIndex);
        }

        /*if(timesList.get(bestTime.getIndex()) == null){
            System.out.println(bestTime.getIndex());
        }*/

        // Calculation for average of last 5 times
        if(timesList.size() >= 5){
            updateAVG(AVG5);
        }

        if(timesList.size() >= 12) {
            updateAVG(AVG12);

        }

        c.resultsTextArea.setText(getStringValueOfStatistics());
    }

    /**
     *This method updates the average of [@avgOf] times
     * @param current
     */
    public void updateAVG(AverageOf current){
        int avgSize = current.getAverageSize();

        // Get last [avgSize] Times objects
        for(int i = 0; i < avgSize; i++) {
            current.setCurrentTime(i, timesList.get(lastIndex - i));
        }

        // Set current AVG of [avgSize] and checks if it's the best
        current.setAVG(calculationAVGOf(current.getCurrentTimes()));
    }

    /*
    This method returns the average of the given array times without extremes
     */
    public double calculationAVGOf(Time times[]) {

        int dnfOrDnsCount = 0;
        double avg = 0;
        long best = times[0].getTime();
        long worst = times[0].getTime();
        int bestIndex = 0, worstIndex = 0;

        for (int i = 0; i < times.length; i++) {

            if(dnfOrDnsCount >= 2){ //
                return DNF_CODE;
            }

            if(times[i].isDNS() || times[i].isDNF()){
                worstTime = times[i];
                dnfOrDnsCount++;
                continue;
            }

            if (times[i].getTime() > worst) {
                worstIndex = i;
                worst = times[i].getTime();
                continue;
            }
            if (times[i].getTime() < best) {
                bestIndex = i;
                best = times[i].getTime();
            }
        }

        for (int i = 0; i < times.length; i++) {
            if (i == bestIndex || i == worstIndex) {
                continue;
            }
            avg += times[i].getTime();
        }

        return avg/(times.length-2);
    }

    public String getStringValueOfStatistics(){
        lastIndex = timesList.size()-1;
        String stats = "";
        if(lastIndex >= 0){
            stats = "Current Time: " + timesList.get(lastIndex).toString();
            stats += "\nBest Time: " + bestTime.toString();
            stats += "\nWorst Time: " + worstTime.toString();

        }
        if(lastIndex >= 4){
            stats += "\nCurrent AVG5: " + AVG5;
            stats += "\nBest AVG5: " + AVG5.getBestAVG();
        }
        if(lastIndex >= 11){
            stats += "\nCurrent AVG12: " + AVG12;
            stats += "\nBest AVG12: " + AVG12.getBestAVG();
        }

        return stats;
    }

    public void updateAfterTimeDelete(int index){
        Time deletedTime = timesList.get(index);
        timesList.remove(index);
        lastIndex = timesList.size()-1;

        if(deletedTime == bestTime || deletedTime == worstTime )
            setSingleTimes();
        if(timesList.size() >= 5)
            countAllAvgOf(AVG5);

        c.resultsTextArea.setText(getStringValueOfStatistics());
    }

    public void countAllAvgOf(AverageOf average){
        for(int i = 0; i < timesList.size(); i++){

            if(i >= average.getAverageSize()-1){

                for(int j = 0; j < average.getAverageSize(); j++) {
                    average.setCurrentTime(j, timesList.get(i - j));
                }
                    // Set current AVG of [avgSize] and checks if it's the best
                    average.setAVG(calculationAVGOf(average.getCurrentTimes()));
                    System.out.println(average);
                    if(i == average.getAverageSize()-1)
                        average.setBestAVG(average);
                }
        }
    }

    public void setSingleTimes(){
        Time best, worst;

        if(timesList.size() == 0){
            return;
        }

        if(timesList.size() == 1){
           bestTime = timesList.get(0);
           worstTime = bestTime;
           return;
        }

        best = timesList.get(0);
        worst = timesList.get(0);

        for (Time time:timesList) {
            if(time.getTime() > worst.getTime()){
                worst = time;
                continue;
            }

            if(time.getTime() < best.getTime())
                best = time;
        }

        bestTime = best;
        worstTime = worst;
    }

}
