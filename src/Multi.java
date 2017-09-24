public class Multi {
    String tArray[] = {"A", "T", "A", "G", "C", "T"}; //j
    String sArray[] = {"G", "A", "T", "A", "T", "G", "C", "A"}; //i
    int [][] matrix = new int[sArray.length+1][tArray.length+1];
    int generalMax = 0;
    // To storeinal value max and positions i,j
    int max = 0;
    int i = 0;
    int j = 0;

    public void Multi(){

    }

    public void calcMatrix(){
        initMatrix();
        int currentMax = 0;
        for(int i = 1; i < matrix.length; i++){
            for(int j = 1; j < matrix[0].length; j++){
                int[] values = new int[3];
                values[0] = calculateNorthOrWest(matrix[i][j-1]);
                values[1] = calculateNorthOrWest(matrix[i-1][j]);
                values[2] = calculateNorthWest(matrix[i-1][j-1], sArray[i-1], tArray[j-1]);
                currentMax = getMax(values);
                if (generalMax <= currentMax)
                    matrix[i][j] =  getMax(values);
            }
        }
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                System.out.println(i+","+j + ":"+ matrix[i][j] + " ");
            }
            System.out.println();
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

    private void initMatrix(){
        for(int i = 0; i < matrix[0].length; i++){
            matrix[0][i] = 0;
        }
        for(int i = 0; i < matrix.length; i++){
            matrix[i][0] = 0;
        }

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                //  System.out.println(i+","+j + ":"+ matrix[i][j] + " ");
            }
            //System.out.println();
        }

    }

    private int calculateNorthOrWest(int value){
        return value - 2;
    }

    private int calculateNorthWest(int value, String s, String t){
        if (s == t || s == "?" || t == "?"){
            return value + 1;
        }else{
            return value -1;
        }
    }
}
