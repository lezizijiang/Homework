package os;

import java.io.*;
import java.util.concurrent.CountDownLatch;

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
    }
    public void setN(int n) {
        this.n = n;
        count1 = new CountDownLatch(n);
    }

    public void singleThread(){
        for(int i = 0; i < length;i++){
            for(int j = 0; j < length; j++){
                for(int k = 0; k < length; k++){
                   result[i][j] = a[i][k]*b[k][j];
                }
            }
        }
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
        int id;
        MyThread(int id){
            this.id = id;
        }
        @Override
        public void run() {
            int len = Matrix.this.length;
            int width = len / Matrix.this.n;
            int start = id * width, end = start + width;
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
    public void multiThread(){
        for(int i = 0 ; i  < n; i++){
            MyThread thread = new MyThread(i);
            thread.start();
        }
    }

    public void show(int n) throws InterruptedException {
        long startTime, endTime;
        if( n <= 1){
            startTime = System.currentTimeMillis();
            singleThread();
            endTime = System.currentTimeMillis();
            System.out.println(length+"矩阵单线程用时"+(endTime - startTime));
        }else{
            startTime = System.currentTimeMillis();
            setN(n);
            multiThread();
            count1.await();
            endTime = System.currentTimeMillis();
            System.out.println(length+"矩阵"+n+"线程用时"+(endTime - startTime));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        long startTime, endTime;
        Matrix matrix64 = new Matrix("mx/M64A.txt","mx/M64B.txt", 64);
        Matrix matrix128 = new Matrix("mx/M128A.txt","mx/M128B.txt", 128);
        Matrix matrix512 = new Matrix("mx/M512A.txt","mx/M512B.txt", 512);
        Matrix matrix1024 = new Matrix("mx/M1024A.txt","mx/M1024B.txt", 1024);
//        matrix64.show(1);
//        matrix128.show(1);
//        matrix512.show(1);
//        matrix1024.show(1);
//        matrix64.show(4);
//        matrix128.show(4);
//        matrix512.show(4);
//        matrix1024.show(4);
//        matrix64.show(16);
//        matrix128.show(16);
//        matrix512.show(16);
//        matrix1024.show(16);
    }
}
