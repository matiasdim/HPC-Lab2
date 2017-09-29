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
    private static int NUM_OF_THREADS = 10; //Number of threads to fill out the matrix
    private Thread[] threads = new Thread[NUM_OF_THREADS]; //Array containing the threads
    private String [] tList;
    private String [] sList;
    private int [][] matrix;
    // To store final max value and positions i,j
    int maxVal = 0;
    int maxI = 0;
    int maxJ = 0;
    // We are storing a list of coordinates of the matrix ready to be computed
    private java.util.List<Point> readyPoints;


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
        Multi multi = new Multi();
        for(int i = 0; i < NUM_OF_THREADS; i++){
            threads[i] = new Thread(multi);
        }
        readyPoints.add(new Point(1,1))// Matrix 1,1 is ready to compute at the beginning
        this.calcMatrix();
    }
    public void calcMatrix(){
        if (readyPoints.size() > 0 ){

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
        for(int i = 0; i < matrix[0].length; i++){
            matrix[0][i] = 0;
        }
        for(int i = 0; i < matrix.length; i++){
            matrix[i][0] = 0;
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
