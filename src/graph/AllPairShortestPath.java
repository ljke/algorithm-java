package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 3种有向图的最短路径算法
 */
public class AllPairShortestPath {
    //不要设置为Integer.MAX_VALUE，除非算法中已经排除了不可达结点，否则会溢出
//    private static final int INF = 99999;
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Floyd算法，时间复杂度O(V^3)
     */
    public void floydWarshall(int[][] graph, int src, int dst, int v) {
        int i, j, k;

        //初始化一个前驱数组
        int[][] prev = new int[v][v];
        for (i = 0; i < v; i++) {
            for (j = 0; j < v; j++) {
                prev[i][j] = i;
            }
        }
        for (i = 0; i < v; i++) {
            prev[i][i] = -1;
        }

        int[][] dist = new int[v][v];
        //初始化一个距离数组
        for (i = 0; i < v; i++) {
            for (j = 0; j < v; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        //插入k结点，更新距离矩阵
        for (k = 0; k < v; k++) {
            for (i = 0; i < v; i++) {
                for (j = 0; j < v; j++) {
                    //排除不可达结点
                    if(dist[i][k] == INF || dist[k][j] == INF){
                        continue;
                    }
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        prev[i][j] = prev[k][j]; //更新前驱数组
                    }
                }
            }
//            printSolution(dist, v);
            printSolution(prev, v);
        }
        if (dist[src][dst] == INF) {
            System.out.println("No found");
        } else {
            System.out.println("Shortest distance:" + dist[src][dst]);
            printPath(src, dst, prev);
        }
    }

    /**
     * Dijkstra算法，时间复杂度O(V^2)
     */
    public void dijkstra(int[][] graph, int src, int dst, int v) {
        //注意对数组进行初始化
        boolean[] visited = new boolean[v];
        visited[src] = true;
        int[] prev = new int[v];
        Arrays.fill(prev, -1);
        for (int n : graph[src]) {
            if (n < INF) {
                prev[src] = src;
            }
        }
        int[] costs = Arrays.copyOf(graph[src], v); //保存当前计算的最短路径

        int cost;
        int new_cost;
        int node = findLowestCost(visited, costs, v);
        while (node != dst && node != -1) { //当node == dst时，已经找到了到达dst的最短路径，可以结束
            cost = costs[node];
            int[] neighbor = graph[node];
            for (int i = 0; i < v; i++) {
                if (neighbor[i] == INF) { //排除不可达结点
                    continue;
                }
                new_cost = cost + neighbor[i];
                if (new_cost < costs[i]) {
                    costs[i] = new_cost;
                    prev[i] = node;
                }
            }
            visited[node] = true;
            node = findLowestCost(visited, costs, v);
        }

        if (costs[dst] == INF) {
            System.out.println("No found");
        } else {
            System.out.println("Shortest distance:" + costs[dst]);
            printPath(src, dst, prev);
        }
    }

    /**
     * Bellman-Ford算法，可以计算负权边，时间复杂度为O(V * E)
     */
    public void bellmanFord(int[][] graph, int src, int dst, int vertex) {
        int[] dist = new int[vertex];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        int[] prev = new int[vertex];
        Arrays.fill(prev, -1);
        //算法主要考虑的是边，所以将邻接矩阵转换为三元组
        List<int[]> edges = adjacencyToEdge(graph, vertex);

        int u, v, w;
        boolean updated; //改进：如果循环没有更新，提前跳出循环
        for (int i = 0; i < vertex - 1; i++) {
            updated = false;
            for (int[] edge : edges) {
                u = edge[0];
                v = edge[1];
                w = edge[2];
                if (dist[u] != INF && (dist[u] + w) < dist[v]) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                    updated = true;
                }
            }
            if(!updated){
                break;
            }
        }

        //检测是否存在负环路
        for (int[] edge:edges) {
            u = edge[0];
            v = edge[1];
            w = edge[2];
            if (dist[u] != INF && (dist[u] + w) < dist[v]) {
                dist[v] = dist[u] + w;
                System.out.println("Negative loop existed");
                return;
            }
        }

        if (dist[dst] == INF) {
            System.out.println("No found");
        } else {
            System.out.println("Shortest distance:" + dist[dst]);
            printPath(src, dst, prev);
        }
    }

    /**
     * Dijkstra 算法中用于查找最小结点
     *
     * @param processed 已处理顶点数组，true表示已处理
     * @param costs     从原点到每个顶点的cost
     * @return 未处理的顶点中cost最小的顶点
     */
    private int findLowestCost(boolean[] processed, int[] costs, int v) {
        //使用Java 8 stream API简化编程
        //注意通常来说数组stream是不能够操作下标的，所以这里需要一些技巧
        return IntStream.range(0, v).filter(i -> !processed[i]).boxed()
                .min(Comparator.comparingInt(x -> costs[x])).orElse(-1);
    }

    /**
     * Floyd算法中用于打印距离矩阵
     *
     * @param dist
     * @param v
     */
    private void printSolution(int[][] dist, int v) {
        System.out.println("Distance:");
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF\t");
                } else {
                    System.out.print(dist[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * 在bellman算法中用于将邻接表转化为三元组(u, v, w)
     * @param graph
     * @param v
     */
    private List<int[]> adjacencyToEdge(int[][] graph, int v){
        List<int[]> edge = new ArrayList<>();
        for(int i = 0; i < v; i++){
            for(int j = 0; j < v; j++){
                if(graph[i][j] != 0 && graph[i][j] != INF){
                    int[] tuple = {i, j, graph[i][j]};
                    edge.add(tuple);
                }
            }
        }
        return edge;
    }


    /**
     * 使用prev数组打印路径
     * 分为两种情况，floyd算法的prev是二维数组，另外两个是一维数组
     * @param dst
     * @param prev
     */
    private void printPath(int src, int dst, int[] prev) {
        System.out.print("path:" + src);
        recurPrint(src, dst, prev);
        System.out.println();
    }

    private void printPath(int src, int dst, int[][] prev){
        System.out.print("path:" + src);
        recurPrint(src, dst, prev);
        System.out.println();
    }

    private void recurPrint(int src, int dst, int[] prev) {
        if (dst != -1 && dst != src) {
            recurPrint(src, prev[dst], prev);
        } else {
            return;
        }
        System.out.print("->" + dst);
    }

    private void recurPrint(int src, int dst, int[][] prev) {
        if (dst != -1 && dst != src) {
            recurPrint(src, prev[src][dst], prev);
        } else {
            return;
        }
        System.out.print("->" + dst);
    }

    public static void main(String[] args) {
        //使用邻接矩阵存储图
        int[][] graph = {{0, 5, INF, 1}, {INF, 0, 2, INF}, {INF, INF, 0, INF}, {INF, 2, INF, 0}};
        int v = graph.length;
        AllPairShortestPath a = new AllPairShortestPath();
        a.floydWarshall(graph, 0, 2, v);
        a.dijkstra(graph, 0, 2, v);
        a.bellmanFord(graph, 0, 2, v);
    }

}
