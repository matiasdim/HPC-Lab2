import com.sun.org.apache.xpath.internal.operations.Mult;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class Main {

    private static String S_FILE_NAME = "Example2S";//"HIV-1_db.fasta"; // Change to use another sequence input file
    private static String T_FILE_NAME = "Example2T"; //"HIV-1_Polymerase.txt"; // Change to use another unknown input file
    private String s; // To store the sequence read file
    private String t; // To store the unknown read file
    private static int NUM_OF_THREADS = 10000; //Number of threads to fill out the matrix
    private List<Thread> threads; //List containing the threads
    private String [] tList;
    private String [] sList;
    private int [][] matrix;
    // To store final max value and positions i,j
    int maxVal = 0;
    int maxI = 0;
    int maxJ = 0;
    // We are storing a list of coordinates of the matrix ready to be computed
    private List<Point> readyPoints;


    public static void main(String[] args) {
        Main main = new Main();
        main.execute();
    }

    private void execute(){
        s = readFile(S_FILE_NAME);
        t = readFile(T_FILE_NAME);
        tList = t.split("");
        sList = s.split("");
        matrix = initMatrix();

        readyPoints.add(new Point(1,1));// Matrix 1,1 is ready to compute at the beginning
        this.calcMatrix();
    }
    public void calcMatrix(){
        while(matrix[sList.length+1][tList.length+1] < 0){
            if (readyPoints.size() > 0 ){
                // Getting values to init Multi-runnable class
                Point point = readyPoints.get(0);
                readyPoints.remove(0);
                int northValue = matrix[point.x][point.y - 1];
                int nwValue = matrix[point.x - 1][point.y - 1];
                int westValue = matrix[point.x - 1][point.y];
                String sString = sList[point.x];
                String tString = sList[point.y];
                //init Multi
                Multi multi = new Multi(point.x, point.y, northValue, nwValue, westValue, sString, tString);
                // Init java thread Thread with multi
                Thread thread = new Thread(multi);
            }
        }

        /*
        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                Triplet triplet = Multi.compute(i, j, matrix[i][j-1], matrix[i-1][j-1], matrix[i-1][j], sList[i-1], tList[j-1]);
                matrix[i][j] = (int)triplet.value;
            }
        }

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                if (matrix[i][j] >= maxVal) {
                    maxVal = matrix[i][j];
                    maxI = i;
                    maxJ = j;
                }
            }
        }
        System.out.println();
        System.out.println("Max: " + maxVal + " at " + maxI + "," + maxJ);
        */
    }

    private int[][] initMatrix(){
        int[][] matrix = new int[sList.length+1][tList.length+1];

        for(int i = 0; i <= sList.length; i++){
            for(int j = 0; j <= tList.length; j++){
                if (i == 0 || j == 0) {
                    matrix[i][j] = 0;
                }else{
                    matrix[i][j] = -1;
                }
            }
        }

        return matrix;
    }



    private static String readFile(String filePath){
        String finalString = "";
        try {
            String line = "";

            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(filePath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                finalString = finalString + line;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file ");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return finalString;
    }

}
