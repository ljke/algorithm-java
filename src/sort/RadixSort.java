package sort;

import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort {
    public static void radixSort(int[] nums) {
        //获得最大值和最小值
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (int i : nums) {
            minValue = Math.min(minValue, i);
            maxValue = Math.max(maxValue, i);
        }
        //获得总位数D
        int D = Integer.toString(maxValue - minValue).length();
        //每一位使用桶排序，桶排序是稳定的
        //建立10个桶，表示每一位数字的10个取值，相当于计数排序
        ArrayList<Integer>[] buckets = new ArrayList[10];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        //桶排序
        for (int i = 0; i < D; i++) {
            //进入桶
            for (int x : nums) {
                int index = getDigit(x - minValue, i); //这里以minValue为基准
                buckets[index].add(x);
            }
            //从桶中取出
            int index = 0;
            for (ArrayList<Integer> bucket : buckets) {
                for (int x : bucket) {
                    nums[index++] = x;
                }
                bucket.clear(); //清空桶，用于后续再次排序
            }
        }
    }

    /**
     * 获得第i位数
     *
     * @param n
     * @param i
     * @return
     */
    private static int getDigit(int n, int i) {
        for (int j = 0; j < i; j++) {
            n /= 10;
        }
        return n % 10;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 3, -4, 1, 2};
        radixSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
