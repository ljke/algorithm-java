package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BucketSort {
    public static void bucketSort(int[] nums) {
        if (nums.length < 2)
            return;
        //计算最大值和最小值
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (int i : nums) {
            minValue = Math.min(minValue, i);
            maxValue = Math.max(maxValue, i);
        }
        //桶的大小
        int bucketSize = (maxValue - minValue) / nums.length + 1;
        //桶的个数，等于nums的长度
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        //使用数组保存多个桶，每个桶使用ArrayList表示
        final ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        //添加到桶中
        for (int x : nums) {
            int index = (x - minValue) / bucketSize; //这里以minValue为基准
            buckets[index].add(x);
        }
        //桶内排序
        for (ArrayList<Integer> list : buckets) {
            Collections.sort(list);
        }
        //依次从桶中取出
        int i = 0;
        for (ArrayList<Integer> list : buckets) {
            for (int x : list) {
                nums[i++] = x;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{5, 3, -4, 1, 2};
        bucketSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
