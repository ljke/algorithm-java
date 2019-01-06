package graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private int v; //顶点个数
    private LinkedList<Integer>[] adj;  // LinkedList array: 邻接表
    //private boolean found = false; //dfs中表示找到路径

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) {
        adj[s].add(t);
        adj[t].add(s);
    }

    public void printPath(int s, int t, int[] prev) {
        if (t != s) {
            printPath(s, prev[t], prev);
        }
        System.out.print(" -> " + t);
    }

    public void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        boolean[] visited = new boolean[v]; //已访问顶点
        for (int i = 0; i < v; i++) {
            visited[i] = false;
        }
        int[] prev = new int[v]; //前驱顶点
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        Queue<Integer> queue = new LinkedList<>(); //使用队列保存待检查顶点
        queue.add(s);
        visited[s] = true; //只要被添加到queue中，就算是被访问过了

        while (queue.size() != 0) {
            int w = queue.poll();
            //遍历w的所有邻接顶点
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                //只处理未访问过的顶点
                if (!visited[q]) {
                    prev[q] = w; //记录前驱顶点
                    if (q == t) {
                        printPath(s, q, prev);
                        System.out.println();
                        return;
                    } else {
                        queue.add(q);
                        visited[q] = true;
                    }
                }
            }
        }
        System.out.println("No found");
    }

    public void dfs(int s, int t) {
        boolean[] visited = new boolean[v]; //已访问顶点
        for (int i = 0; i < v; i++) {
            visited[i] = false;
        }
        int[] prev = new int[v]; //前驱顶点
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        if (recurDFS(s, t, visited, prev)) {
            printPath(s, t, prev);
            System.out.println();
        } else {
            System.out.println("No found");
        }

    }

    public boolean recurDFS(int w, int t, boolean[] visited, int[] prev) {
        //带有返回值的递归函数可以不使用found标志变量
        //if (found) return true;
        visited[w] = true; //遍历到当前顶点
        if (w == t) { //当前顶点符合，直接返回
            //found = true;
            return true;
        }
        boolean ret = false;
        for (int i = 0; i < adj[w].size(); i++) {
            if (ret) return true;
            int q = adj[w].get(i);
            if (!visited[q]) { //只检查未访问过的顶点
                prev[q] = w;
                ret = recurDFS(q, t, visited, prev);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);

        graph.bfs(0, 7);
        graph.dfs(0, 7);
    }
}
