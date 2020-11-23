package os;

import java.io.*;
import java.lang.reflect.GenericDeclaration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Matrix {
    double[][] a, b;
    int length,n;
    double[][] result;
    CountDownLatch count1;
    Matrix(String fileName1, String fileName2, int length, int n) throws IOException{
        a = getData(fileName1, length);
        b = getData(fileName2, length);
        this.length = length;
        this.n = n;
        result = new double[length][length];
        count1 = new CountDownLatch(n);
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
        @Override
        public void run() {
            int len = Matrix.this.length;
            int width = len / Matrix.this.n;
            String[] name = Thread.currentThread().getName().split("-");
            int id = Integer.parseInt(name[1]), start = id * width, end = start + width;
            int count = 0;
            System.out.println(id);
            for(int i = start; i< end;i++){
                for(int j = start; j < end; j++){
                    for(int k = 0; k < len; k++){
                        Matrix.this.result[i][j] = Matrix.this.a[i][k] * Matrix.this.b[k][j];
                        count ++;
                    }
                }
            }
            Matrix.this.count1.countDown();
            System.out.println(count);
        }
    }
    public double[][] multiThread(int n){
        for(int i = 0 ; i  < n; i++){
            MyThread thread = new MyThread();
            thread.start();
        }
        return result;
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Matrix matrix = new Matrix("mx/M512A.txt", "mx/M512B.txt", 512, 4);
        long startTime = System.currentTimeMillis();
        matrix.singleThread();
        long endTime1 = System.currentTimeMillis();
        System.out.println(endTime1 - startTime);
        matrix.multiThread(4);
        matrix.count1.await();
        long endTime2 = System.currentTimeMillis();
        System.out.println(endTime2 - endTime1);
    }
}
