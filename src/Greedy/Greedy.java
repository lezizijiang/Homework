package Greedy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class Greedy {
    int n;
    List<List<Integer>> list;
    int[][] other;
    HashSet<Integer> node;
    HashMap<Integer, int[]> explored;
    public Greedy(int n){
        list = new ArrayList<>(n);
        this.n = n;
        other = new int[n][n];
        node = new HashSet<>();
        explored = new HashMap<>();
    }
    public void readFile(String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String str = file.readLine();
        String[] temp = str.split(" ");
        temp.
    }
    public int Prime(){
        int start = 1, sum = 0;
        HashSet<Integer> result = new HashSet<>();
        explored.put(start, new int[]{0, 0});
        int[] T = new int[n];
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int num1 = 0, num2 = 0;
                if(explored.containsKey(o1)){
                    num1 = explored.get(o1)[1];
                }else{
                    num1 = getPi(o1);
                }
                if(explored.containsKey(o2)){
                    num2 = explored.get(o2)[1];
                }else{
                    num2 = getPi(o2);
                }
                return num1 - num2;
            }
        };
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(comparator);
        for(int i = 0; i < n; i++){
            priorityQueue.add(i+1);
        }
        while(!priorityQueue.isEmpty()){
            int u = priorityQueue.poll();
            sum += explored.get(u)[1];
            result.add(u);
            for(int i : list.get(u)){
                if(!result.contains(i)) {
                    if(other[u][i] < explored.get(i)[1]){
                        priorityQueue.remove(i);
                        explored.get(i)[1] = other[u][i];
                        explored.get(i)[0] = u;
                    }
                }
            }
        }
        return sum;
    }
    public int getPi(int v){
        int minNum = Integer.MAX_VALUE;
        int[] temp = new int[2];
        temp[1] = minNum;
        for(int i : explored.keySet()){
            if(other[i][v-1] != 0 && explored.get(i)[1] != Integer.MAX_VALUE){
                minNum = Math.min(minNum, explored.get(i)[1] + other[i][v]);
            }
            if(minNum < temp[1]){
                temp[0] = i + 1;
                temp[1] = minNum;
            }
        }
        this.explored.put(v, temp);
        return minNum;
    }
}

