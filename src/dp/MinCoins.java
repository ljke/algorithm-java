/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package dp;

/**
 * 最少硬币问题，类似于爬楼梯问题
 *
 * @author linjie
 * @version : MinCoins.java, v 0.1 2022年06月19日 5:28 下午 linjie Exp $
 */
public class MinCoins {
    /**
     * 最少需要多少个1/3/5硬币才能凑齐x元
     * 状态转移方程：f(x) = 1 + min(f(x-1), f(x-3), f(x-5))
     *
     * @param money
     * @return
     */
    public int minCoins1(int money) {
        if (money == 1 || money == 3 || money == 5) {
            return 1;
        }
        boolean [][] state = new boolean[money][money + 1];
        if (money >= 1) {
            state[0][1] = true;
        }
        if (money >= 3) {
            state[0][3] = true;
        }
        if (money >= 5) {
            state[0][5] = true;
        }
        for (int i = 1; i < money; i++) {
            for (int j = 1; j <= money; j++) {
                // 在上一次的基础上更新
                if (state[i - 1][j]) {
                    if (j + 1 <= money) {
                        state[i][j + 1] = true;
                    }
                    if (j + 3 <= money) {
                        state[i][j + 3] = true;
                    }
                    if (j + 5 <= money) {
                        state[i][j + 5] = true;
                    }
                    if (state[i][money]) {
                        return i + 1;
                    }
                }
            }
        }
        return money;
    }

    public int minCoin2(int money) {
        // 记录最少到达需要多少个硬币
        int[] mem = new int[money + 1];
        mem[0] = 0;
        mem[1] = 1;
        mem[3] = 1;
        mem[5] = 1;
        return recur(money, mem);
    }

    public int recur(int money, int[] mem) {
        // 保证不会出现money <= 0的情况
        if (mem[money] != 0) {
            return mem[money];
        }
        int from1 = Integer.MAX_VALUE;
        if (money > 1) {
            from1 = recur(money - 1, mem);
        }
        int from3 = Integer.MAX_VALUE;
        if (money > 3) {
            from3 = recur(money - 3, mem);
        }
        int from5 = Integer.MAX_VALUE;
        if (money > 5) {
            from5 = recur(money - 5, mem);
        }
        // 状态转移方程
        mem[money] = Math.min(from1, Math.min(from3, from5)) + 1;
        return mem[money];
    }

    public static void main(String[] args) {
        MinCoins minCoins = new MinCoins();
        System.out.println(minCoins.minCoins1(13));
        System.out.println(minCoins.minCoin2(13));
    }
}