/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package dp;

/**
 * 动态规划求最短路径
 *
 * @author linjie
 * @version : MinDistance.java, v 0.1 2022年06月17日 9:17 上午 linjie Exp $
 */
public class MinDistance {

    /**
     * n * n矩阵求(0, 0) -> (n-1, n-1)最短路径
     * 状态转移表法
     *
     * @param matrix
     * @param n
     * @return
     */
    public int minDistanceDp1(int[][] matrix, int n) {
        int[][] status = new int[n][n];
        // 第一行赋初值
        int sum = 0;
        for (int j = 0; j < n; j++) {
            sum += matrix[0][j];
            status[0][j] = sum;
        }
        // 第一列赋初值
        sum = 0;
        for (int i = 0; i < n; i++) {
            sum += matrix[i][0];
            status[i][0] = sum;
        }
        // 填状态转移表
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                status[i][j] = matrix[i][j] + Math.min(status[i][j-1], status[i-1][j]);
            }
        }
        return status[n-1][n-1];
    }

    /**
     * 递归版状态转移方程法
     *
     * @param matrix
     * @param n
     * @return
     */
    public int minDistanceDp2(int[][] matrix, int n) {
        int[][] mem = new int[n][n];
        return minDist(n - 1, n - 1, mem, matrix);
    }

    public int minDist(int i, int j, int[][] mem, int[][] matrix) {
        // 只有(0, 0)需要特殊处理，第一行/第一列可以合并入后面的情况
        if (i == 0 && j == 0) {
            return matrix[i][j];
        } else if (mem[i][j] != 0) {
            return mem[i][j];
        }
        int minLeft = Integer.MAX_VALUE;
        if ((j - 1) >= 0) {
            minLeft = minDist(i, j - 1, mem, matrix);;
        }
        int minUp = Integer.MAX_VALUE;
        if ((i - 1) >= 0) {
            minUp = minDist(i - 1, j, mem, matrix);
        }
        mem[i][j] = matrix[i][j] + Math.min(minLeft, minUp);
        return mem[i][j];
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
        MinDistance minDistance = new MinDistance();
        System.out.println(minDistance.minDistanceDp1(matrix, 4));
        System.out.println(minDistance.minDistanceDp2(matrix, 4));
    }

    /**
     * 变形：杨辉三角问题
     * 二叉树上节点值任意，求最短路径
     *
     * @param matrix
     * @return
     */
    int[][] matrix = {{5},{7,8},{2,3,4},{4,9,6,1},{2,7,9,4,5}};
    public int yanghuiTriangle(int[][] matrix) {
        int[][] state = new int[matrix.length][matrix.length];
        state[0][0] = matrix[0][0];
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == 0) {
                    state[i][j] = state[i - 1][j] + matrix[i][j];
                }
                else if (j == matrix[i].length - 1) {
                    state[i][j] = state[i - 1][j - 1] + matrix[i][j];
                }
                else {
                    int top1 = state[i - 1][j - 1];
                    int top2 = state[i - 1][j];
                    state[i][j] = Math.min(top1, top2) + matrix[i][j];
                }
            }
        }
        int minDis = Integer.MAX_VALUE;
        for (int i = 0; i < matrix[matrix.length - 1].length; i++) {
            int distance = state[matrix.length - 1][i];
            if (distance < minDis) {
                minDis = distance;
            }
        }
        return minDis;
    }
}