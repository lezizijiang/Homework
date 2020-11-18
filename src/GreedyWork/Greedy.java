package GreedyWork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Greedy {
    int n;
    boolean[] isExplored;
    int[][] other;
    List<Edge> edge;
    List<Integer> explored;
    List<Edge> E;
    LinkedList<Integer> Unexplored;
    HashMap<Integer, GreedyWork.UnionFind.UFNode> nodeAndPnt = new HashMap<>();
    public Greedy(int n){
        this.n = n;
        other = new int[n+1][n+1];
        edge = new LinkedList<>();
        isExplored = new boolean[n+1];
        explored = new LinkedList<>();
        E = new LinkedList<>();
        Unexplored = new LinkedList<>();
    }
    public void readFile(String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String str;
        String[] temp;
        int col, row, weight;
        str = file.readLine();
        while((str = file.readLine()) != null){
            temp = str.split(" ");
            row = Integer.parseInt(temp[0]);
            col = Integer.parseInt(temp[1]);
            weight = Integer.parseInt(temp[2]);
            other[col][row] = other[row][col] = weight;
            edge.add(new Edge(row, col, weight));
        }
        for(int i = 2; i < n+1; i++){
            Unexplored.add(i);
        }
    }
    public int Prim(){
        int sum = 0;
        Comparator<Edge> comparator = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        };
        PriorityQueue<Edge> pq = new PriorityQueue<>(comparator);
        explored.add(1);
        while(explored.size() != n) {
            for (int i : explored) {
                for (int j : Unexplored)
                    if(other[i][j] != 0)
                        pq.offer(new Edge(i, j, other[i][j]));
            }
            Edge u = pq.poll();
            E.add(u);
            if (!explored.contains(u.startNode)) {
                explored.add(u.startNode);
                Unexplored.remove(Integer.valueOf(u.startNode));
            }
            if (!explored.contains(u.endNode)) {
                explored.add(u.endNode);
                Unexplored.remove(Integer.valueOf(u.endNode));
            }
            pq = new PriorityQueue<>(comparator);
        }
        for(Edge e : E){
            sum += e.weight;
        }
        return sum;
    }

    public int kruskal(){
        Comparator<Edge> comparator = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        };
        edge.sort(comparator);
        int sum = 0;
        for(int i = 1; i < n+1; i++){
            nodeAndPnt.put(i, new UnionFind.UFNode());
        }
        for (Edge temp : edge) {
            if (UnionFind.find(nodeAndPnt.get(temp.startNode)) != UnionFind.find(nodeAndPnt.get(temp.endNode))) {
                sum += temp.weight;
                UnionFind.union(nodeAndPnt.get(temp.startNode), nodeAndPnt.get(temp.endNode));
            }
        }
        return sum;
    }
    public static void main(String[] args) throws IOException {
        Greedy greedy1 = new Greedy(500);
        greedy1.readFile("HomeWork4/h4biggraph.txt");
        long startTime = System.currentTimeMillis();
        System.out.println("Prim求得大图MST权重和" + greedy1.Prim());
        long endTime1 = System.currentTimeMillis();
        System.out.println("Prim大图用时"+ (endTime1 - startTime));
        System.out.println("Kruskal求得大图MST权重和" + greedy1.kruskal());
        long endTime2 = System.currentTimeMillis();
        System.out.println("kruskal大图用时"+ (endTime2 - endTime1));
        greedy1 = new Greedy(6);
        greedy1.readFile("HomeWork4/h4test.txt");
        startTime = System.currentTimeMillis();
        System.out.println("Prim求得小图MST权重和" + greedy1.Prim());
        endTime1 = System.currentTimeMillis();
        System.out.println("Prim小图用时"+ (endTime1 - startTime));
        System.out.println("Kruskal求得小图MST权重和" + greedy1.kruskal());
        endTime2 = System.currentTimeMillis();
        System.out.println("kruskal小图用时"+ (endTime2 - endTime1));
    }
}
class Edge{
    int startNode;
    int endNode;
    int weight;
    public Edge(int row, int col, int weight){
        startNode = row;
        endNode = col;
        this.weight = weight;
    }
    public int getEndNode() {
        return endNode;
    }

    public int getStartNode() {
        return startNode;
    }

    public int getWeight() {
        return weight;
    }
}

