package Greedy;

class StartVertex{
    NearVertex next;
    int data;
    private StartVertex(int n){
        this.data = n;
    }
}
class NearVertex{
    NearVertex next;
    int data;
    int weight;
    private NearVertex(int n, int weight){
        this.data = n;
        this.weight = weight;
    }
}
