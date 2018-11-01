package sample;

import java.util.Random;

/**
 * A class that represents a scramble for Rubik's Cube
 */
public class Scramble {

    private String scramble = "";

    /*
    A two-dimensional array that stores the notation of the algorithm. Its initialization is not accidental. Indexes of the first dimension of the array form 3 groups, representing 3 axes (three-dimensionality of the rubik's cube). The elements belonging to each axis result from division of the index of the first modulo dimension by 3.
    As a result of:
    The first axis -> array [0] and array [3]
    The second axis -> array [1] and array [4]
    The third axis -> array [2] and array [5]
    We will need this to properly generate a scramble.
     */
    public final String[][] notationForScramble = new String [][]{
            { "U", "U2", "U'"},
            { "F", "F2", "F'"},
            { "R", "R2", "R'"},
            { "D", "D2", "D'"},
            { "B", "B2", "B'"},
            { "L", "L2", "L'"} };

    public Scramble(int scrambleLength){
        makeScramble(scrambleLength);
    }

    public String getScramble(){
        return scramble;
    }

    public void setScramble(String scramble){
        this.scramble = scramble;
    }

    /*
    Method to generate new scramble.
     */
    public void makeScramble(int scrambleLength){
        Random random = new Random();
        int i = 0, j = 0;
        int lastIndex = 0, preLastIndex = 0;
        scramble = "";

        for (int x = 0; x < scrambleLength; x++){

            /*
            Forcing a new index to be drawn, which is not the same
            as the previous one or does not belong to the same axis as the penultimate one
             */
            while(i == lastIndex || i % 3 == preLastIndex % 3) {
                i = random.nextInt(5);
            }

            j = random.nextInt(2);
            preLastIndex = lastIndex;
            lastIndex = i;
            scramble += notationForScramble[i][j] + " "; //Convert the generated numbers into scramble notation
        }
    }


}
