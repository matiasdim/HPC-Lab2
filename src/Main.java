import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    //private static String S_FILE_NAME = "Example2S";//"HIV-1_db.fasta"; // Change to use another sequence input file
    //private static String T_FILE_NAME = "Example2T"; //"HIV-1_Polymerase.txt"; // Change to use another unknown input file
    private static String S_FILE_NAME = "HIV-1_db.fasta"; // Change to use another sequence input file
    private static String T_FILE_NAME = "HIV-1_Polymerase.txt"; // Change to use another unknown input file

    public static void main(String[] args) {
        String s = readFile(S_FILE_NAME);
        String t = readFile(T_FILE_NAME);
        Multi multi = new Multi(s, t);
        multi.calcMatrix();
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
