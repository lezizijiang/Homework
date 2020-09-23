package stableMatch;

import java.io.*;
import java.util.*;

public class Match {

    public int[][] read(String filename) throws IOException {
        // 利用BuffedReader读取文件
        BufferedReader file = new BufferedReader(new FileReader(filename));
        List<String> tmpList = new ArrayList<>();
        String str = file.readLine();
        // 将文件中的数据写入列表中
        while(str != null){
            tmpList.add(str);
            str = file.readLine();
        }
        // 利用getInt方法将分割出来的字符串转化成数字
        return getInts(tmpList);
    }

    public static int[][] getInts(List<String> tmpList) {
        // 将字符串转化成整型
        int[][] result = new int[tmpList.size()][];
        String[] temp;
        for(int i = 0; i < tmpList.size(); i++){
            temp = tmpList.get(i).trim().split(" ");
            result[i] = new int[temp.length];
            for(int j = 0; j < temp.length; j++){
                result[i][j] = Integer.parseInt(temp[j]);
            }
        }
        return result;
    }

    public boolean loveMoreThanCurrent(int[] loveList, int lover, int curLover){
        // 比较女生爱谁爱的更深，如果爱现在的男友更深，则返回false，不然就是返回true
        for (int value : loveList) {
            if (value == lover) {
                return true;
            } else if (value == curLover) {
                return false;
            }
        }
        return false;
    }

    public int[] find(int[][] woman, int[][] man){
        // 开始进行算法
        int peopleNumber = woman.length, womanIndex = 0, manIndex;
        // 设置一个用于记录匹配结果的数组，大小和女性人数目相同，有了男友该处的大小被修改成男友id
        int[] recordForWoman = new int[peopleNumber];
        // 数组初始化
        Arrays.fill(recordForWoman, 0);
        // 将单身狗们压入栈，当栈空了的时候，单身狗们应该都有匹配对象了
        Stack<Integer> singleMan = new Stack<>();
        // 反向压入栈，正向弹出
        for(int i = peopleNumber - 1; i >= 0; i--) {
            singleMan.push(i);
        }
        while(!singleMan.empty()){
            // 获取当前男性ID
            manIndex = singleMan.peek();
            // 获得他现在最心仪的人
            int targetWoman = man[manIndex][womanIndex] - 1;
            // 如果喜欢的人没有匹配，那他成功表白
            if(recordForWoman[targetWoman] == 0){
                recordForWoman[targetWoman] = manIndex + 1;
                singleMan.pop();
                womanIndex = 0;
            }else if(loveMoreThanCurrent(woman[targetWoman], manIndex + 1, recordForWoman[targetWoman])){
                // 如果喜欢的女生有男朋友，但是他干得过现男友，那么他就成了现男友，原来那个人就失败了
                int loser = recordForWoman[targetWoman];
                recordForWoman[targetWoman] = manIndex + 1;
                singleMan.pop();
                // 把失败的人重新压入栈
                singleMan.push(loser - 1);
                // 由于他失败了，但是这个女生之前的人之前已经无法获得了，那么从当前女生的后面那个位置开始
                for(int i = 0; i < man[loser - 1].length;i++){
                    if(man[loser - 1][i] == targetWoman + 1){
                        womanIndex = i + 1;
                        break;
                    }
                }
            }else{
                // 当前的女生获得不了，只能找下一个
                womanIndex++;
            }
        }
        return recordForWoman;
    }

    public static void output(int[] result, BufferedWriter bufferedWriter5) throws IOException {
        // 利用BufferedWrite将匹配结果写入文件
        StringBuffer strings;
        for(int i = 0; i < result.length; i++){
            strings = new StringBuffer();
            strings.append(i + 1);
            strings.append('-');
            strings.append(result[i]);
            bufferedWriter5.write(strings.toString());
            bufferedWriter5.newLine();
            bufferedWriter5.flush();
        }
        bufferedWriter5.close();
    }

    public static void main(String[] args) throws IOException{
        // 计时、匹配、写入文件
        long startTime = System.currentTimeMillis();
        Match match = new Match();
        int[][] man5 = match.read("Homework1\\man5");
        int[][] woman5 = match.read("Homework1\\woman5");
        int[][] man1000 = match.read("Homework1\\man1000");
        int[][] woman1000 = match.read("Homework1\\woman1000");
        int[] result5 = match.find(woman5, man5);
        int[] result1000 = match.find(woman1000, man1000);
        BufferedWriter bufferedWriter5 = new BufferedWriter(new FileWriter("matchResult5"));
        BufferedWriter bufferedWriter1000 = new BufferedWriter(new FileWriter("matchResult1000"));
        output(result5, bufferedWriter5);
        output(result1000, bufferedWriter1000);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
