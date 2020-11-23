package GreedyWork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Greedy {
    int n;
    ArrayList<Integer>[] graph;
    ArrayList<Integer>[] weights;
    List<Edge> edge;
    HashSet<Integer> explored;
    List<Edge> E;
    HashMap<Integer, GreedyWork.UnionFind.UFNode> nodeAndPnt = new HashMap<>();

    public Greedy(int n) {
        this.n = n;
        edge = new LinkedList<>();
        explored = new HashSet<>();
        E = new LinkedList<>();
        graph = new ArrayList[n + 1];
        weights = new ArrayList[n + 1];
    }

    public void readFile(String filename) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(filename));
        String str;
        String[] temp;
        int col, row, weight;
        str = file.readLine();
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
            weights[i] = new ArrayList<>();
        }
        while ((str = file.readLine()) != null) {
            temp = str.split(" ");
            row = Integer.parseInt(temp[0]);
            col = Integer.parseInt(temp[1]);
            weight = Integer.parseInt(temp[2]);
            edge.add(new Edge(row, col, weight));
            weights[row].add(weight);
            weights[col].add(weight);
            graph[row].add(col);
            graph[col].add(row);
        }
    }

    public int Prim() {
        int sum = 0;
        Comparator<Edge> comparator = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        };
        PriorityQueue<Edge> pq = new PriorityQueue<>(comparator);
        explored.add(1);
        Edge edge;
        for (int i = 0; i < graph[1].size(); i++) {
            edge = new Edge(1, graph[1].get(i), weights[1].get(i));
            pq.add(edge);
        }
        while (explored.size() != n) {
            Edge u = pq.poll();
            while (explored.contains(u.endNode)) {
                u = pq.poll();
            }
            int newNode = u.endNode;
            sum += u.weight;
            explored.add(u.endNode);
            for (int i = 0; i < graph[newNode].size(); i++) {
                u = new Edge(newNode, graph[newNode].get(i), weights[newNode].get(i));
                pq.add(u);
            }
        }
        for (Edge e : E) {
            sum += e.weight;
        }
        return sum;
    }

    public int kruskal() {
        Comparator<Edge> comparator = new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        };
        edge.sort(comparator);
        int sum = 0;
        for (int i = 1; i < n + 1; i++) {
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
        System.out.println("Prim大图用时" + (endTime1 - startTime));
        System.out.println("Kruskal求得大图MST权重和" + greedy1.kruskal());
        long endTime2 = System.currentTimeMillis();
        System.out.println("kruskal大图用时" + (endTime2 - endTime1));
        greedy1 = new Greedy(6);
        greedy1.readFile("HomeWork4/h4test.txt");
        startTime = System.currentTimeMillis();
        System.out.println("Prim求得小图MST权重和" + greedy1.Prim());
        endTime1 = System.currentTimeMillis();
        System.out.println("Prim小图用时" + (endTime1 - startTime));
        System.out.println("Kruskal求得小图MST权重和" + greedy1.kruskal());
        endTime2 = System.currentTimeMillis();
        System.out.println("kruskal小图用时" + (endTime2 - endTime1));
    }
}

class Edge {
    int startNode;
    int endNode;
    int weight;

    public Edge(int row, int col, int weight) {
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

