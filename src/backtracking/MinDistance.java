/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package backtracking;

/**
 * 回溯法求最短路径
 *
 * @author linjie
 * @version : MinDistance.java, v 0.1 2022年06月17日 9:18 上午 linjie Exp $
 */
public class MinDistance {
    int minDist = Integer.MAX_VALUE;
    public int solution(int[][] w) {
        minDistanceBackTracking(0, 0, 0, w, w[0].length);
        return minDist;
    }

    public void minDistanceBackTracking(int i, int j, int dist, int[][] w, int n) {
        if (i == n && j == n) {
            // 到达终点
            if (dist < minDist) {
                minDist = dist;
            }
            return;
        }
        if (i < n) {
            minDistanceBackTracking(i + 1, j, dist + w[i][j], w, n);
        }
        if (j < n) {
            minDistanceBackTracking(i, j + 1, dist + w[i][j], w, n);
        }
    }
}