import java.util.*;

public class Multi {

    int generalMax = 0;
    private String s; // To store the sequence read file
    private String t; // To store the unknown read file
    List<String> tList;
    List<String> sList;
    // To store final max value and positions i,j
    int max = 0;
    int maxI = 0;
    int maxJ = 0;

    //String sequence s - String unknown t
    public Multi(String t, String s){
        this.t = t;
        this.s = s;
        tList = Arrays.asList(t.split(""));
        sList = Arrays.asList(s.split(""));
    }

    public void calcMatrix(){
        int [][] matrix = initMatrix(sList.size(), tList.size());
        int currentMax = 0;

        // start counting execution time.
        long startTime = System.currentTimeMillis();

        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                int[] values = new int[3];
                values[0] = matrix[i][j-1] + calculateNorthOrWest();
                values[1] = matrix[i-1][j] + calculateNorthOrWest();
                values[2] = matrix[i-1][j-1] + calculateNorthWest(sList.get(i-1), tList.get(j-1));
                currentMax = getMax(values);
                if (generalMax <= currentMax){
                    generalMax = currentMax;
                    maxJ = j;
                    maxI = i;
                }
                matrix[i][j] = currentMax;
            }
        }
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                System.out.println(i+","+j + ":"+ matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Max: " + generalMax + " at " + maxI + "," + maxJ);
        // Stop counting execution time.
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time: " + totalTime);
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

    private int[][] initMatrix(int sListSize, int tListSize){
        int[][] matrix = new int[sListSize+1][tListSize+1];
        for(int i = 0; i < matrix[0].length; i++){
            matrix[0][i] = 0;
        }
        for(int i = 0; i < matrix.length; i++){
            matrix[i][0] = 0;
        }
        return matrix;
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
}
