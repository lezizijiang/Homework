package Greedy;

import java.util.*;

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
    public void Prime(){

    }
    public int getPi(int v){
        int minNum = Integer.MAX_VALUE;
        for(int i : explored.keySet()){
            if(other[i][v] != 0){

            }
        }
        return minNum;
    }
}

