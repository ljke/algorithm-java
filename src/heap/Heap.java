/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package heap;

/**
 * @author linjie
 * @version : Heap.java, v 0.1 2022年07月06日 11:11 上午 linjie Exp $
 */
public class Heap {

    /**
     * 使用数组存储
     */
    private int[] a;

    /**
     * 最大数据个数
     */
    private int n;

    /**
     * 当前存储个数
     */
    private int count;

    /**
     * 大顶堆
     *
     * @param capacity
     */
    public Heap(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }

    /**
     * 插入一个元素
     * 时间复杂度O(logn)
     *
     * @param data
     */
    public void insert(int data) {
        if (count >= n) {
            return;
        }
        count++;
        a[count] = data;
        int i = count;
        // 从下往上堆化
        while (i / 2 > 0 && a[i] > a[i / 2]) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    /**
     * 删除堆顶元素
     * 时间复杂度O(logn)
     *
     * @return 返回删除的元素
     */
    public int removeMax() {
        if (count == 0) {
            return -1;
        }
        int max = a[1];
        a[1] = a[count];
        count--;
        // 从上往下堆化
        heapify(1, count);
        return max;
    }

    /**
     * 建堆，使用从上往下堆化的思想，从非叶子节点开始
     * 时间复杂度，每个元素的移动次数不一样，总时间复杂度O(n)
     */
    public void buildHeap() {
        for (int i = count / 2; i >= 1; i--) {
            heapify(i, count);
        }
    }

    /**
     * 堆排序
     * 先建堆，然后按删除堆顶元素的方式依次找到最大值
     * 时间复杂度O(nlogn)
     */
    public void sort() {
        buildHeap();
        int k = count;
        while (k > 1) {
            swap(1, k);
            k--;
            heapify(1, k);
        }
    }

    /**
     * 交换两个元素
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /**
     * 从上往下堆化流程
     *
     * @param i 起始位置
     * @param j 终止位置
     */
    private void heapify(int i, int j) {
        int maxPos = i * 2;
        while (true) {
            // 父节点和左右节点比较，取最大作为新的父节点
            if (i * 2 <= j && a[i] < a[i * 2]) {
                maxPos = i * 2;
            }
            if (i * 2 + 1 <= j && a[maxPos] < a[i * 2 + 1]) {
                maxPos = i * 2 + 1;
            }
            if (maxPos == i) {
                break;
            }
            swap(i, maxPos);
            i = maxPos;
        }
    }


}