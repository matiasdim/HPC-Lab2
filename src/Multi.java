import java.util.*;
import java.awt.Point;

public class Multi implements Runnable {
    private int i, j, northValue, nwValue, westValue;
    private String sString, tString;

    public Multi(int i, int j, int northValue, int nwValue, int westValue, String sString, String tString){
        this.i = i;
        this.j = j;
        this.northValue = northValue;
        this.nwValue = nwValue;
        this.westValue = westValue;
        this.sString = sString;
        this.tString = tString;
    }

    @Override
    public void run() {

    }
    /*
     * i and J are the position of the matrix to compute.
     * northValue, nwValue and westValue are N, NW and W values of i,j on the matrix.
     * ssString and tString has the char to evaluate W.
     */
    public Triplet compute(){
        int[] values = new int[3];
        values[0] = northValue + calculateNorthOrWest();
        values[1] = westValue + calculateNorthOrWest();
        values[2] = nwValue + calculateNorthWest(sString, tString);
        int max = getMax(values);
        Triplet triplet = new Triplet(i, j, max);
        return triplet;
    }

    private int calculateNorthOrWest(){
        return - 2;
    }

    private int calculateNorthWest(String s, String t){
        if (s.equals(t) || s.equals("?") || t.equals("?")){
            return  1;
        }else{
            return -1;
        }
    }

    private int getMax(int nums[]){
        int max = 0;
        for (int i: nums) {
            if (i > max){
                max = i;
            }
        }
        return max;
    }

}
