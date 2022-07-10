/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package sort;

import java.util.Arrays;

/**
 * @author linjie
 * @version : Sort.java, v 0.1 2022年07月06日 12:53 上午 linjie Exp $
 */
public class Sort {

    /**
     * 快排写法
     *
     * @param nums
     */
    public void quickSort(int[] nums) {
        // 递归调用
        quickSortRecur(nums, 0, nums.length - 1);
    }

    private void quickSortRecur(int[] nums, int begin, int end) {
        // 递归结束条件
        if (begin >= end) {
            return;
        }
        // 先分区
        int pivot = partition(nums, begin, end);
        // 分别处理子问题，注意分区点不用计入
        quickSortRecur(nums, begin, pivot - 1);
        quickSortRecur(nums, pivot + 1, end);
    }

    private int partition(int[] nums, int begin, int end) {
        // 双指针分区
        int pivotNum = nums[end];
        int i = begin;
        for (int j = begin; j < end; j++) {
            if (nums[j] < pivotNum) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, end);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    /**
     * 归并排序写法
     *
     * @param nums
     */
    public void mergeSort(int[] nums) {
        // 递归调用
        mergeSortRecur(nums, 0, nums.length - 1);
    }

    private void mergeSortRecur(int[] nums, int begin, int end) {
        // 递归结束条件
        if (begin >= end) {
            return;
        }
        int mid = begin + (end - begin) / 2;
        // 分别处理子问题
        mergeSortRecur(nums, begin, mid);
        mergeSortRecur(nums, mid + 1, end);
        // 合并结果
        merge(nums, begin, mid, end);
    }

    private void merge(int[] nums, int begin, int mid, int end) {
        int[] tmp = new int[end - begin + 1];
        int k = 0;
        int i = begin, j = mid + 1;
        while(i <= mid && j <= end) {
            if (nums[i] <= nums[j]) {
                tmp[k] = nums[i];
                i++;
            } else {
                tmp[k] = nums[j];
                j++;
            }
            k++;
        }
        while(i <= mid) {
            tmp[k] = nums[i];
            i++;
            k++;
        }
        while(j <= end) {
            tmp[k] = nums[j];
            j++;
            k++;
        }
        for (int x = 0; x < tmp.length; x++) {
            nums[begin + x] = tmp[x];
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 5, 3, 8, 4};
        Sort sort = new Sort();
        sort.mergeSort(nums);
        System.out.println(Arrays.toString(nums));
        int[] nums1 = new int[]{1, 5, 3, 8, 4};
        sort.quickSort(nums1);
        System.out.println(Arrays.toString(nums1));
    }
}