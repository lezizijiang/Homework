package Vertex;

import java.util.LinkedList;

public class GraphNode {
    int val;
    GraphNode next;
    LinkedList<GraphNode> list;
    public GraphNode(){
        this.val = 0;
        this.list = new LinkedList<>();
        this.next = null;
    }
    public GraphNode(int val){
        this.val = val;
        this.next = null;
        this.list = new LinkedList<>();
    }
}
