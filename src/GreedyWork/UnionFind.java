package GreedyWork;

import java.util.HashSet;
import java.util.Set;

/**
 * 并查集定义
 */
public class UnionFind {

    public static class UFNode {
        UFNode parent;
    }

    public static UFNode find(UFNode x) {
        UFNode p = x;
        //用于先把不同深度的节点存起来，最后统一指向根结点，加快查询效率
        Set<UFNode> path = new HashSet<>();
        while (p.parent != null) {
            path.add(p);
            p = p.parent;
        }
        //将所有边指向同一个集合标识（根节点）
        for (UFNode ppp : path) {
            ppp.parent = p;
        }
        //返回集合标识（根节点）
        return p;
    }

    //合并
    public static void union(UFNode x, UFNode y) {
        //将两个不同的集合指向同一个集合标识（根节点）
        find(y).parent = find(x);
    }
}
