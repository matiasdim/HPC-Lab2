import com.sun.org.apache.xpath.internal.operations.Mult;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    private static String S_FILE_NAME = "Example1S";//"HIV-1_db.fasta"; // Change to use another sequence input file
    private static String T_FILE_NAME = "Example1T"; //"HIV-1_Polymerase.txt"; // Change to use another unknown input file
    int generalMax = 0;
    private String s; // To store the sequence read file
    private String t; // To store the unknown read file
    private String [] tList;
    private String [] sList;
    private int [][] matrix;
    // To store final max value and positions i,j
    int currentMax = 0;
    int maxI = 0;
    int maxJ = 0;
    // We are storing a list of coordinates of the matrix ready to be computed
    private java.util.List<Point> readyPoints;
    // we need to remove from "readyPoints" a point while it is being calculated to avoid another thread to
    // get it, but we need to keep it to create the new points available after computing a particular point so
    // this is the reason to have the following array.
    private List<Point> beingComputedPoints;

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
        this.calcMatrix();
    }
    public void calcMatrix(){

        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                Triplet triplet = Multi.compute(i, j, matrix[i][j-1], matrix[i-1][j-1], matrix[i-1][j], sList[i-1], tList[j-1]);

                matrix[i][j] = (int)triplet.value;
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
