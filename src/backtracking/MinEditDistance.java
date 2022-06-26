/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package backtracking;

/**
 * 求莱文斯特距离
 * 可以增加/删除/替换字符
 *
 * @author linjie
 * @version : MinEditDistance.java, v 0.1 2022年06月25日 3:58 下午 linjie Exp $
 */
public class MinEditDistance {
    public int min_dist = Integer.MAX_VALUE;

    public int minEditDistance(char[] a, char[] b) {
        solution(0, 0, 0, a, b);
        return min_dist;
    }

    public void solution(int i, int j, int dist, char[] a, char[] b) {
        if (i == a.length || j == b.length) {
            if (i < a.length) {
                dist = dist + (a.length - i);
            }
            if (j < b.length) {
                dist = dist + (b.length - j);
            }
            if (dist < min_dist) {
                min_dist = dist;
            }
            return;
        }
        // 分情况讨论
        if (a[i] == b[j]) {
            // 相等情况后移
            solution(i + 1, j + 1, dist, a, b);
        } else {
            // 3种情况
            solution(i + 1, j, dist + 1, a, b);
            solution(i, j + 1, dist + 1, a, b);
            solution(i  + 1, j + 1, dist + 1, a, b);
        }
    }
}