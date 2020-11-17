package Vertex;

import java.io.*;
import java.util.*;

/**
 * @author lezizijiang
 */
public class Graph {
    /**
     * @param beginMap 用来存储节点和由其射入的节点
     * @param reverseMap 用来记录反向后的节点和由其摄入的节点
     * @param curLabel 用于拓扑排序时候记录节点序号
     * */
    int maxVertex = 0, curLabel, numScc;
    int[] marked, f, scc;
    PriorityQueue<Integer> priorityQueue;
    final int MARKED = 1, UNMARKED = 0;
    HashMap<Integer, List<Integer>> beginMap, reverseMap;

    public void readFile(String filename) throws IOException {
        // 利用BuffedReader读取文件
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String str = file.readLine();
        int number, target;
        String[] temp1;
        beginMap = new HashMap<>();
        reverseMap = new HashMap<>();
        // 将文件中的数据写入字典中
        while (str != null) {
            temp1 = str.split(" ");
            number = Integer.parseInt(temp1[0]);
            target = Integer.parseInt(temp1[1]);
            maxVertex = Math.max(maxVertex, Math.max(number, target));
            addElement(number, target, beginMap);
            addElement(target, number, reverseMap);
            str = file.readLine();
        }
        for (int i = 1; i < maxVertex + 1; i++) {
            if (!beginMap.containsKey(i)) {
                beginMap.put(i, new LinkedList<>());
            }
            if (!reverseMap.containsKey(i)) {
                reverseMap.put(i, new LinkedList<>());
            }
        }
    }

    public void addElement(int target, int number,  HashMap<Integer, List<Integer>> map) {
        List<Integer> temp2;
        if (!map.containsKey(target)) {
            temp2 = new LinkedList<>();
            temp2.add(number);
            map.put(target, temp2);
        } else {
            List<Integer> temp3 = new LinkedList<>(map.get(target));
            temp3.add(number);
            map.put(target, temp3);
        }
        if (!map.containsKey(number)) {
        map.put(number, new LinkedList<>());
        };
    }

    public int[] kosaraju() {
        marked = new int[maxVertex + 1];
        f = new int[maxVertex + 1];
        int[] res = new int[5];
        scc = new int[maxVertex + 1];
        // 对图的逆序进行拓扑排序
        topoSort(reverseMap);
        //设置优先队列
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };
        priorityQueue = new PriorityQueue<>(comparator);
        // 将图中的节点全部设置为未访问状态
        for (int number : beginMap.keySet()) {
            marked[number] = UNMARKED;
        }
        // 计算单个节点的连通分量编号，编号相同的在同一个连通分量里面
        numScc = 0;
        for (int i = 1; i < maxVertex + 1; i++) {
            int vertex = f[i];
            if (marked[vertex] == UNMARKED) {
                numScc++;
                dfs_scc(vertex);
            }
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i < maxVertex + 1; i++) {
            if (map.containsKey(scc[i])) {
                map.put(scc[i], map.get(scc[i]) + 1);
            } else {
                map.put(scc[i], 1);
            }
        }
        for (int key : map.keySet()) {
            priorityQueue.add(map.get(key));
        }
        for (int i = 0; i < 5; i++) {
            if (!priorityQueue.isEmpty()) {
                res[i] = priorityQueue.poll();
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    public void dfs_scc(int vertex) {
        marked[vertex] = MARKED;
        scc[vertex] = numScc;
        for (int v : beginMap.get(vertex)) {
            if (marked[v] == UNMARKED) {
                dfs_scc(v);
            }
        }
    }

    public void topoSort(HashMap<Integer, List<Integer>> map) {
        // for each方法
        for (int number : map.keySet()) {
            marked[number] = UNMARKED;
        }
        curLabel = maxVertex;
        for (int vertex : map.keySet()) {
            if (marked[vertex] == UNMARKED) {
                dfs_topo(map, vertex);
            }
        }
        // Iterator 方法
    }

    public void dfs_topo(HashMap<Integer, List<Integer>> map, int s) {
        marked[s] = MARKED;
        for (int v : map.get(s)) {
            if (marked[v] == UNMARKED) {
                dfs_topo(map, v);
            }
        }
        f[curLabel] = s;
        curLabel--;
    }

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.readFile("homework3/h3biggraph.txt");
        System.out.println("大图结果为"+ Arrays.toString(graph.kosaraju()));
        graph = new Graph();
        graph.readFile("homework3/h3test1.txt");
        System.out.println("第一个图结果为"+ Arrays.toString(graph.kosaraju()));
        graph = new Graph();
        graph.readFile("homework3/h3test2.txt");
        System.out.println("第二个图结果为"+ Arrays.toString(graph.kosaraju()));
        graph = new Graph();
        graph.readFile("homework3/h3test3.txt");
        System.out.println("第三个图结果为"+ Arrays.toString(graph.kosaraju()));
        graph = new Graph();
        graph.readFile("homework3/h3test4.txt");
        System.out.println("第四个图结果为"+ Arrays.toString(graph.kosaraju()));
        graph = new Graph();
        graph.readFile("homework3/h3test5.txt");
        System.out.println("第五个图结果为"+ Arrays.toString(graph.kosaraju()));
    }

}
