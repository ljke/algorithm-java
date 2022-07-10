/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package dp;

/**
 * @author linjie
 * @version : MinEditDistance.java, v 0.1 2022年06月25日 5:31 下午 linjie Exp $
 */
public class MinEditDistance {
    /**
     * 求莱文斯特距离
     * 可以增加/删除/替换字符
     *
     * @param a
     * @param b
     * @return
     */
    public int minEditDistance(char[] a, char[] b) {
        int m = a.length;
        int n = b.length;
        if (m * n == 0) {
            return m + n;
        }
        int[][] dp = new int[m][n];
        // 初始化边界值
        // 第0行 a[0] 和 b[0...j] 的编辑距离
        for (int j = 0; j < n; j++) {
            if (a[0] == b[j]) {
                dp[0][j] = j;
            } else if (j != 0) {
                dp[0][j] = dp[0][j - 1] + 1;
            } else {
                dp[0][j] = 1;
            }
        }
        // 第0列 a[0...i] 和 b[0] 的编辑距离
        for (int i = 0; i < m; i++) {
            if (b[0] == a[i]) {
                dp[i][0] = i;
            } else if (i != 0) {
                dp[i][0] = dp[i - 1][0] + 1;
            } else {
                dp[i][0] = 1;
            }
        }
        // 更新dp
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (a[i] == b[j]) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j] + 1), dp[i][j - 1] + 1);
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1), dp[i][j - 1] + 1);
                }
            }
        }
        // 得到最优解
        return dp[m - 1][n - 1];
    }
}