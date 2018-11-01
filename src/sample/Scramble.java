package sample;

import java.util.Random;

public class Scramble {

    private String scramble = "";
    public String[][] notationForScramble = new String [][]{
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

    public void makeScramble(int scrambleLength){
        Random random = new Random();
        int i = 0, j = 0;
        int lastIndex = 0, preLastIndex=0;
        scramble = "";

        for (int x = 0; x < scrambleLength; x++){
            while(i % 3 == lastIndex % 3) {
                i = random.nextInt(5);
            }

            j = random.nextInt(2);
            lastIndex = i;
            scramble += notationForScramble[i][j] + " ";
        }
    }


}
