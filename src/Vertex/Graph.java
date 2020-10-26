package Vertex;

import javax.print.DocFlavor;
import java.io.*;
import java.util.*;

/**
 * @author lezizijiang
 */
public class Graph{
    int maxVertex = 0;
    HashMap<Integer, List<Integer>> map;
    HashMap<Integer, List<Integer>> reverseMap;
    public Graph(){
        this.map = new HashMap<>();
        this.reverseMap = new HashMap<>();
    }
    public void readFile(String filename) throws IOException {
        // 利用BuffedReader读取文件
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String str = file.readLine();
        int number, target;
        String[] temp1;
        List<Integer> temp2;
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

    }
    public void dfs_scc(int s){

    }
    public void dfs_topo(int s)
    public void topoSort(){
        int[] marked = new int[maxVertex];

    }
}
