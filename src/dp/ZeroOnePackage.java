/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package dp;

/**
 * 动态规划01背包问题
 *
 * @author linjie
 * @version : ZeroOnePackage.java, v 0.1 2022年06月15日 11:15 上午 linjie Exp $
 */
public class ZeroOnePackage {
    /**
     * 初始版本
     * 能装下n个商品的最大重量
     *
     * @param n 商品数量
     * @param w 背包容量
     * @param weight 每个商品重量
     * @return
     */
    public int solution1(int n, int w, int[] weight) {
        boolean[][] status = new boolean[n][w + 1];
        // 第一个商品需要特殊处理
        status[0][0] = true;
        status[0][weight[0]] = true;
        for (int i = 1; i < n; i++) {
            // 不选择当前商品
            for (int j = 0; j <= w; j++) {
                if (status[i - 1][j]) {
                    status[i][j] = status[i - 1][j];
                }
            }
            // 选择当前商品
            for (int j = 0; j <= (w - weight[i]); j++) {
                if (status[i - 1][j]) {
                    status[i][j + weight[i]] = true;
                }
            }
        }
        // 查找最大值
        for (int i = w; i >= 0; i--) {
            if (status[n - 1][i]) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 优化空间，使用一维数组保存
     *
     * @param n
     * @param w
     * @param weight
     * @return
     */
    public int solution2(int n, int w, int[] weight) {
        boolean[] status = new boolean[w + 1];
        // 第一个商品需要特殊处理
        status[0] = true;
        status[weight[0]] = true;
        for (int i = 1; i < n; i++) {
            // 选择当前商品
            for (int j = (w - weight[i]); j >= 0; j--) {
                if (status[j]) {
                    status[j + weight[i]] = true;
                }
            }
        }
        // 查找最大值
        for (int i = w; i >= 0; i--) {
            if (status[i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 新增商品价值
     * 能装下n个商品的最大商品价值
     *
     * @param n
     * @param w
     * @param weight
     * @param value 每个商品价值
     * @return
     */
    public int solution3(int n, int w, int[] weight, int[] value) {
        int[][] status = new int[n][w + 1];
        status[0][0] = 0;
        status[0][weight[0]] = value[0];
        for (int i = 1; i < n; i++) {
            // 不选择当前商品
            for (int j = 0; j <= w; j++) {
                status[i][j] = status[i - 1][j];
            }
            // 选择当前商品
            for (int j = 0; j <= (w - weight[i]); j++) {
                // 需要判断当前位置是有效的
                if ((status[i][j] > 0) && (status[i][j] + value[i]) > status[i][j + weight[i]]) {
                    status[i][j + weight[i]] = status[i][j] + value[i];
                }
            }
        }
        // 查找最大值
        int maxW = status[n - 1][0];
        for (int j = 0; j <= w; j++) {
            maxW = Math.max(maxW, status[n - 1][j]);
        }
        return maxW;
    }

    /**
     * 优化版本
     * 使用一维数组
     *
     * @param n
     * @param w
     * @param weight
     * @param value
     * @return
     */
    public int solution4(int n, int w, int[] weight, int[] value) {
        int[] status = new int[w + 1];
        status[0] = 0;
        status[weight[0]] = value[0];
        for (int i = 1; i < n; i++) {
            // 选择当前商品
            for (int j = (w - weight[i]); j >= 0; j--) {
                // 需要判断当前位置是有效的
                if ((status[j] > 0) && (status[j] + value[i]) > status[j + weight[i]]) {
                    status[j + weight[i]] = status[j] + value[i];
                }
            }
        }
        // 查找最大值
        int maxW = status[0];
        for (int j = 0; j <= w; j++) {
            maxW = Math.max(maxW, status[j]);
        }
        return maxW;
    }


    public static void main(String[] args) {
        ZeroOnePackage zeroOnePackage = new ZeroOnePackage();
        int[] weight = new int[]{3, 4, 8};
        int[] value = new int[]{5, 6, 7};
        System.out.println(zeroOnePackage.solution1(3, 10, weight));
        System.out.println(zeroOnePackage.solution2(3, 10, weight));
        System.out.println(zeroOnePackage.solution3(3, 10, weight, value));
        System.out.println(zeroOnePackage.solution4(3, 10, weight, value));
    }
}