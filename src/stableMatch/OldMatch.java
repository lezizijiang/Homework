package stableMatch;

import java.io.*;
import java.util.*;

public class OldMatch {
    public int[][] read(String filename) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "r");
        List<String> tmpList = new ArrayList<>();
        long position = 0, len = file.length();
        while(position < len){
            String str = file.readLine();
            position = file.getFilePointer();
            tmpList.add(str);
        }
        int[][] result = new int[tmpList.size()][];
        String[] temp;
        for(int i = 0; i < tmpList.size(); i++){
            temp = tmpList.get(i).split(" ");
            result[i] = new int[temp.length];
            for(int j = 0; j < temp.length; j++){
                result[i][j] = Integer.parseInt(temp[j]);
            }
        }
        return result;
    }
    public int manNotMatch(int[] record){
        for (int i = 0; i < record.length; i++) {
            if (record[i] == -1) {
                return i;
            }
        }
        return -1;
    }
    public boolean loveMoreThanCurrent(int[] loveList, int lover, int prevLover){
        for (int value : loveList) {
            if (value == lover) {
                return true;
            } else if (value == prevLover) {
                return false;
            }
        }
        return false;
    }
    public int[] find(int[][] woman, int[][] man){
        int peopleNumber = woman.length;
        int[] recordForMan = new int[peopleNumber];
        int[] recordForWoman = new int[peopleNumber];
        Arrays.fill(recordForMan, -1);
        Arrays.fill(recordForWoman, -1);
        int manIndex, womenIndex;
        while((manIndex = manNotMatch(recordForMan))>= 0) {
            womenIndex = 0;
            while(womenIndex < peopleNumber){
                int targetWoman = man[manIndex][womenIndex] - 1;
                if (recordForWoman[targetWoman] == -1) {
                    recordForWoman[targetWoman] = manIndex + 1;
                    recordForMan[manIndex] = targetWoman + 1;
                    break;
                } else if(loveMoreThanCurrent(woman[targetWoman], manIndex + 1, recordForWoman[targetWoman])){
                    recordForMan[recordForWoman[targetWoman] - 1] = -1;
                    recordForWoman[targetWoman] = manIndex + 1;
                    recordForMan[manIndex] = targetWoman + 1;
                    break;
                }else{
                    womenIndex++;
                }
            }
        }
        return recordForWoman;
    }
    public static void main(String[] args) throws IOException{
        long startTime = System.currentTimeMillis();
        OldMatch match = new OldMatch();
        int[][] man5 = match.read("Homework1\\man5");
        int[][] woman5 = match.read("Homework1\\woman5");
        int[][] man1000 = match.read("Homework1\\man1000");
        int[][] woman1000 = match.read("Homework1\\woman1000");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        int[] result = match.find(woman1000, man1000);
        for(int i : result){
            System.out.print(i+" ");
        }
    }
}
