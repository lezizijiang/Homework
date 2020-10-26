package stableMatch;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        while(number != 1){
            int temp = number;
            if(number % 2 == 1) {
                number = number * 3 + 1;
                System.out.println(temp + "*3+1=" + number);
            }else {
                number = number / 2;
                System.out.println(temp + "/2=" + number);
            }
        }
        System.out.print("End");
    }
}
