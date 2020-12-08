package os;

import java.io.*;
import java.util.concurrent.CountDownLatch;
/**
 * @author rrass
 */
public class Matrix {
    double[][] a, b;
    int length,n;
    double[][] result;
    CountDownLatch count1;
    Matrix(String fileName1, String fileName2, int length) throws IOException{
        a = getData(fileName1, length);
        b = getData(fileName2, length);
        this.length = length;
        result = new double[length][length];
        count1 = new CountDownLatch(n);
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[][] singleThread(){
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
    class MyThread extends Thread{
        int start;
        MyThread(int m){
            start = m;
        }
        @Override
        public void run() {
            int len = Matrix.this.length;
            int width = len / Matrix.this.n;
            int id = this.start, start = id * width, end = start + width;
            for(int i = start; i< end;i++){
                for(int j = 0; j < len; j++){
                    for(int k = 0; k < len; k++){
                        Matrix.this.result[i][j] = Matrix.this.a[i][k] * Matrix.this.b[k][j];
                    }
                }
            }
            Matrix.this.count1.countDown();
        }
    }
    public double[][] multiThread(){
        for(int i = 0 ; i  < n; i++){
            MyThread thread = new MyThread(i);
            thread.start();
        }
        return result;
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Matrix matrix64, matrix128, matrix512, matrix1024;
//        matrix64 = new Matrix("mx/M64A.txt", "mx/M64B.txt", 64);
//        matrix128 = new Matrix("mx/M128A.txt", "mx/M128B.txt", 128);
//        matrix512 = new Matrix("mx/M512A.txt", "mx/M512B.txt", 512);
        matrix1024 = new Matrix("mx/M1024A.txt", "mx/M1024B.txt", 1024);
//        long startTime = System.currentTimeMillis();
//        matrix64.singleThread();
//        long endTime = System.currentTimeMillis();
//        System.out.println("64矩阵单线程用时" + (endTime - startTime));
//        startTime = System.currentTimeMillis();
//        matrix128.singleThread();
//        endTime = System.currentTimeMillis();
//        System.out.println("128矩阵单线程用时" + (endTime - startTime));
//        startTime = System.currentTimeMillis();
//        matrix512.singleThread();
//        endTime = System.currentTimeMillis();
//        System.out.println("512矩阵单线程用时" + (endTime - startTime));
        long startTime = System.currentTimeMillis();
        matrix1024.singleThread();
        long endTime = System.currentTimeMillis();
        System.out.println("1024矩阵单线程用时" + (endTime - startTime));
    }
}
