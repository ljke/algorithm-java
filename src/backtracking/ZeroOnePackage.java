/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package backtracking;

/**
 * @author linjie
 * @version : ZeroOnePackage.java, v 0.1 2022年06月13日 5:43 下午 linjie Exp $
 */
public class ZeroOnePackage {

    public int maxW = Integer.MIN_VALUE; // 存储背包中物品总重量的最大值

    /**
     * 0-1背包问题的回溯法求解
     *
     * @return
     */
    public int solution(int i, int cw, int[] items, int n, int w) {
        f(0, 0, items, 10, 100);
        return maxW;
    }

    /**
     * @param i 考察到哪个物品
     * @param cw 当前已经装进去的物品的重量和
     * @param items 每个物品的重量
     * @param n 物品个数
     * @param w 背包重量
     */
    public void f(int i, int cw, int[] items, int n, int w) {
        if (cw == w || i == n) { // cw==w 表示装满了 ;i==n 表示已经考察完所有的物品
            if (cw > maxW) {
                maxW = cw;
            }
            return;
        }
        f(i + 1, cw, items, n, w); // 此情况表示不加当前物品
        if (cw + items[i] <= w) {// 已经超过可以背包承受的重量的时候，就不要再装了
            f(i + 1,cw + items[i], items, n, w); // 此情况表示添加当前物品
        }
    }
}