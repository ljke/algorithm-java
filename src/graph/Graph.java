package graph;

import java.util.*;

/**
 * 图算法，BFS和DFS
 */
public class Graph {
    private int v;
    private List<List<Integer>> adjacency; //邻接表存储

    public Graph(int v) {
        this.v = v;
        adjacency = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            List<Integer> arr = new ArrayList<>();
            adjacency.add(arr);
        }
    }

    public void addEdge(int src, int dst) {
        //无向图添加双向
        adjacency.get(src).add(dst);
        adjacency.get(dst).add(src);
    }

    //通过prev列表生成，注意是反向的
    public String generatePath(int src, int dst, int[] prev) {
        StringBuilder str = new StringBuilder();
        while (prev[dst] != -1 && dst != src) {
            str.append(dst).append("<-");
            dst = prev[dst];
        }
        return str.append(src).toString();
    }

    //使用递归就能够正向打印
    public void printPath(int src, int dst, int[] prev){
        if(prev[dst] != -1 && dst != src){
            printPath(src, prev[dst], prev);
        }else{
            return;
        }
        System.out.print("->" + dst);
    }


    /**
     * 广度优先搜索算法(BFS)，查找最短段数的路径
     * BFS需要队列
     * @param src
     * @param dst
     */
    public void bfs(int src, int dst) {
        if (src == dst) {
            return;
        }
//        List<Boolean> visited = new ArrayList<>(Collections.nCopies(v, false));
//        List<Integer> prev = new ArrayList<>(Collections.nCopies(v, -1));
        //使用数组存储即可
        boolean[] visited = new boolean[v];  //已访问顶点
        int[] prev = new int[v]; //前驱顶点
        Arrays.fill(visited, false);
        Arrays.fill(prev, -1);

        Queue<Integer> queue = new LinkedList<>(); //使用队列保存待检查顶点

        visited[src] = true; //只要被添加到queue中，就算是被访问过了
        queue.offer(src);
        int v;
        List<Integer> neighbour;
        while (!queue.isEmpty()) {
            v = queue.poll();
            //遍历w的所有邻接顶点
            neighbour = adjacency.get(v);
            for (int n : neighbour) {
                //只处理未访问过的顶点
                if (!visited[n]) {
                    prev[n] = v; //记录前驱顶点
                    if (n == dst) {
                        System.out.println(generatePath(src, dst, prev));
                        System.out.print(src);
                        printPath(src, dst, prev);
                        System.out.println();
                        return;
                    } else {
                        queue.offer(n);
                        visited[n] = true;
                    }
                }
            }
        }
        System.out.println("No found");
    }

    /**
     * 深度优先搜索算法(DFS),找出一条可以到达的路径，不一定是最短的
     * DFS借助于回溯思想，使用递归栈实现
     * @param src
     * @param dst
     */
    public void dfs(int src, int dst){
        if (src == dst) {
            return;
        }
        boolean[] visited = new boolean[v];
        int[] prev = new int[v];
        Arrays.fill(visited, false);
        Arrays.fill(prev, -1);

        boolean res = recurDFS(src, dst, visited, prev);
        if (res) {
            System.out.println(generatePath(src, dst, prev));
        }else{
            System.out.println("No found");
        }
    }

    public boolean recurDFS(int src, int dst, boolean[] visited, int[] prev){
        visited[src] = true; //遍历到当前顶点
        if(src == dst){ //当前顶点符合，直接返回
            return true;
        }
        boolean ret = false;
        for (int neighbour :
                adjacency.get(src)) {
            if(ret){
                return true;
            }
            if (!visited[neighbour]) { //只检查未访问过的顶点
                prev[neighbour] = src;
                ret = recurDFS(neighbour, dst, visited, prev);
            }
        }
        return ret;
    }


    public static void main(String[] args) {
        Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 5);
        g.addEdge(1, 6);
        g.addEdge(2, 3);
        g.addEdge(5, 4);
        g.addEdge(6, 7);
        g.addEdge(3, 4);
        g.addEdge(4, 7);
        g.bfs(0, 7);
        g.dfs(0, 7);
    }
}
