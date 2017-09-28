import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Multi {

    private String s; // To store the sequence read file
    private String t; // To store the unknown read file
    private String S_FILE_NAME = "HIV-1_db.fasta";//"HIV-1_db.fasta"; // Change to use another sequence input file
    private String T_FILE_NAME = "HIV-1_Polymerase.txt"; //"HIV-1_Polymerase.txt"; // Change to use another unknown input file

    int generalMax = 0;
    // To store final max value and positions i,j
    int max = 0;
    int maxI = 0;
    int maxJ = 0;

    public void Multi(String t, String s){
        this.t = t;
        this.s = s;
        String tArray[] = t.split("");
        String sArray[] = s.split("");
    }

    public void execute(){
        s = readFile(S_FILE_NAME);
        t = readFile(T_FILE_NAME);
        calcMatrix(s.split(""), t.split(""));
    }

    public void calcMatrix(String[] sArray, String[] tArray){
        int [][] matrix = initMatrix(sArray, tArray);
        int currentMax = 0;
        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                int[] values = new int[3];
                values[0] = matrix[i][j-1] + calculateNorthOrWest();
                values[1] = matrix[i-1][j] + calculateNorthOrWest();
                values[2] = matrix[i-1][j-1] + calculateNorthWest(sArray[i-1], tArray[j-1]);
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

    private int[][] initMatrix(String[] sArray, String[] tArray){
        int[][] matrix = new int[sArray.length+1][tArray.length+1];
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

    private String readFile(String filePath){
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
