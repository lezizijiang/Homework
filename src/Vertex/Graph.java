package Vertex;

import java.io.*;
import java.util.*;

/**
 * @author lezizijiang
 */
public class Graph{
    int maxVertex = 0;
    int curLabel, numScc;
    int[] marked;
    PriorityQueue<Integer> priorityQueue;
    int[] f;
    final int MARKED = 1;
    final int UNMARKED = 0;
    HashMap<Integer, List<Integer>> map;
    HashMap<Integer, List<Integer>> reverseMap;
    public void readFile(String filename) throws IOException {
        // 利用BuffedReader读取文件
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String str = file.readLine();
        int number, target;
        String[] temp1;
        List<Integer> temp2;
        map = new HashMap<>();
        reverseMap = new HashMap<>();
        // 将文件中的数据写入列表中
        while(str != null) {
            temp1 = str.split(" ");
            number = Integer.parseInt(temp1[0]);
            target = Integer.parseInt(temp1[1]);
            this.maxVertex = Math.max(maxVertex, Math.max(number, target));
            if(!map.containsKey(number)){
                temp2 = new LinkedList<>();
                temp2.add(target);
                map.put(number, temp2);
            }else{
                map.get(number).add(target);
            }
            if(!reverseMap.containsKey(target)){
                temp2 = new LinkedList<>();
                temp2.add(number);
                map.put(target, temp2);
            }else{
                map.get(target).add(number);
            }
            str = file.readLine();
        }
    }

    public int[] kosaraju(){
        marked = new int[maxVertex + 1];
        f = new int[maxVertex + 1];
        int[] res = new int[5];
        for(int number : reverseMap.keySet()){
            marked[number] = UNMARKED;
            topoSort(reverseMap);
        }
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        };
        priorityQueue = new PriorityQueue<>(comparator);
        for(int number : map.keySet()){
            marked[number] = UNMARKED;
        }
        numScc = 0;
        for (int i = 1; i < maxVertex + 1; i++) {
            int vertex = f[i];
            if (marked[vertex] == UNMARKED) {
                numScc++;
                dfs_scc(map, vertex);
            }
        }
        for(int i = 0; i < 5; i++){
            if(!priorityQueue.isEmpty()){
                res[i] = priorityQueue.poll();
            }else{
                res[i] = 0;
            }
        }
        return res;
    }
    public void dfs_scc(HashMap<Integer, List<Integer>>map,int vertex){
        marked[vertex] = MARKED;
        priorityQueue.add(numScc);
        for(int v : map.get(vertex)){
            if(marked[v] == MARKED)
                dfs_scc(map, v);
        }
    }
    public void topoSort(HashMap<Integer, List<Integer>> map){
        // for each方法
        curLabel = maxVertex;
        for (int vertex:map.keySet()) {
            dfs_topo(map, vertex);
        }
        // Iterator 方法
    }
    public void dfs_topo(HashMap<Integer, List<Integer>> map, int s){
        marked[s] = MARKED;
        for(int v : map.get(s)){
            if(marked[v] == UNMARKED){
                dfs_topo(map, v);
            }
        }
        f[curLabel] = s;
        curLabel--;
    }
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.readFile("homework3/h3test1.txt");
        System.out.println(Arrays.toString(graph.kosaraju()));
    }

}
