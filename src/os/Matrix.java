package os;

import java.io.*;
import java.sql.Time;

public class Matrix {
    public double[][] singleThread(double[][] a, double[][] b, int length){
        double[][] res = new double[length][length];
        for(int i = 0; i < length;i++){
            for(int j = 0; j < length; j++){
                for(int k = 0; k < length; k++){
                   res[i][j] = a[i][k]*b[k][j];
                }
            }
        }
        return res;
    }
    public double[][] getData(String fileName, int length) throws IOException {
        double[][] res = new double[length][length];
        int j = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String str;
        while((str = bufferedReader.readLine()) != null){
            String[] tempList = str.split(" ");
            for(int i = 0; i < length;i++){
                res[j][i] = Double.parseDouble(tempList[i]);
            }
            j++;
        }
        return res;
    }
    public static void main(String[] args){
        long startTime = System.currentTimeMillis();

    }
}
