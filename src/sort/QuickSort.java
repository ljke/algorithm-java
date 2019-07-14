package sort;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class QuickSort {

    /**
     * 交换数组中的两个元素
     *
     * @param items 数组
     * @param i     交换下标
     * @param j     交换下标
     */
    private void swap(@NotNull int[] items, int i, int j) {
        int tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

    /**
     * 双端扫描交换 Double-End Scan and Swap
     *
     * @param items 待排序数组
     */
    public void deScanSwapSort(int[] items) {
        deScanSwapSort(items, 0, items.length - 1);
    }

    public void deScanSwapSort(int[] items, int start, int end) {
        //回归条件
        if (start >= end) {
            return;
        }
        int pivot = items[start];
        int i = start + 1, j = end;
        //i 与 j 必须交错，如果两者相遇之后就停止比较，那相遇点所在的元素就没有和中心点进行比较。
        while (i <= j) {
            while (i <= j && items[i] < pivot) i++;
            while (i <= j && items[j] > pivot) j--;
            if (i <= j) {
                swap(items, i, j);
            }
        }
        /*
        这里只能用j，不能用i
        因为上面的逻辑（重点在于等号）中实际上有意识的让j前撮了一步，j能够到达前区的领域内
         */
        swap(items, start, j);
        deScanSwapSort(items, start, j - 1);
        deScanSwapSort(items, j + 1, end);
    }

    /**
     * 赋值填充方式
     *
     * @param items 待排序数组
     */
    public void fillSort(int[] items) {
        fillSort(items, 0, items.length - 1);
    }

    private void fillSort(int[] items, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = items[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (i < j && items[j] > pivot) j--; //从右往左找到第一个不符合要求的j
            items[i] = items[j]; //交换到前区
            while (i < j && items[i] < pivot) i++; //从左往后找到第一个不符合要求的i
            items[j] = items[i]; //交换到后区
        }
        // 相遇后i == j, 这里能保证正确是因为最后一次赋值的优先权问题，一定是另一边跟进
        items[i] = pivot;
        fillSort(items, start, i - 1);
        fillSort(items, i + 1, end);
    }

    /**
     * 单向扫描划分方式
     *
     * @param items 待排序数组
     */
    public void forwardScanSort(int[] items) {
        forwardScanSort(items, 0, items.length - 1);
    }

    private void forwardScanSort(int[] items, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = items[start];
        int i = start, j = start + 1;
        while (j <= end) {
            if (items[j] < pivot) {
                i++;
                swap(items, i, j);
            }
            j++;
        }
        //如果pivot是start，就要保证与前区元素交换
        //如果pivot是end，就要保证与后区元素交换
        swap(items, start, i);
        forwardScanSort(items, start, i - 1);
        forwardScanSort(items, i + 1, end);
    }

    /**
     * 三分单向扫描
     *
     * @param items 待排序数组
     */
    public void div3ScanSort(int[] items) {
        div3ScanSort(items, 0, items.length - 1);
    }

    private void div3ScanSort(int[] items, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = items[start];
        int i = start, j = end, k = start + 1;
        while (k <= j) {
            if (items[k] < pivot) {
                swap(items, i, k);
                i++;
                k++;
            } else if (items[k] > pivot) {
                swap(items, j, k);
                j--;
            } else {
                k++;
            }
        }
        div3ScanSort(items, start, i - 1);
        div3ScanSort(items, j + 1, end);
    }

    /**
     * 双端扫描三分排序
     *
     * @param items 待排序数组
     */
    public void div3DeScanSort(int[] items) {
        div3DeScanSort(items, 0, items.length - 1);
    }

    private void div3DeScanSort(int[] items, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = items[start];
        int i = start, j = end, k = start + 1;
        OUT_LOOP:
        while (k <= j) {
            if (items[k] < pivot) {
                swap(items, i, k);
                i++;
                k++;
            } else if (items[k] == pivot) {
                k++;
            } else {
                while (items[j] > pivot) {
                    j--;
                    if (k > j) {
                        break OUT_LOOP;
                    }
                }
                if (items[j] < pivot) {
                    swap(items, j, k);
                    swap(items, i, k);
                    i++;
                } else {
                    swap(items, j, k);
                }
                k++;
                j--;
            }
        }
        div3DeScanSort(items, start, i - 1);
        div3DeScanSort(items, j + 1, end);
    }

    /**
     * 双轴快排
     *
     * @param items 待排序数组
     */
    public void dualPivotQuickSort(int[] items) {
        dualPivotQuickSort(items, 0, items.length - 1);
    }

    private void dualPivotQuickSort(int[] items, int start, int end) {
        if (start >= end) {
            return;
        }
        if (items[start] > items[end]) {
            swap(items, start, end);
        }
        int pivot1 = items[start], pivot2 = items[end];
        int i = start, j = end, k = start + 1;
        OUT_LOOP:
        while (k < j) {
            if (items[k] < pivot1) {
                swap(items, ++i, k++);
            } else if (items[k] <= pivot2) {
                k++;
            } else {
                while (items[--j] > pivot2) {
                    if (j <= k) {
                        break OUT_LOOP;
                    }
                }
                if (items[j] < pivot2) {
                    swap(items, j, k);
                    swap(items, ++i, k);
                } else {
                    swap(items, j, k);
                }
                k++;
            }
        }
        swap(items, start, i);
        swap(items, end, j);

        dualPivotQuickSort(items, start, i - 1);
        dualPivotQuickSort(items, i + 1, j - 1);
        dualPivotQuickSort(items, j + 1, end);
    }

    public static void main(String[] args) {
        int[] test = {3, -2, 1, 0, 5, -4};
        QuickSort qs = new QuickSort();
//        qs.deScanSwapSort(test);
        qs.fillSort(test);
//        qs.forwardScanSort(test);
//        qs.div3ScanSort(test);
//        qs.div3DeScanSort(test);
//        qs.dualPivotQuickSort(test);
        System.out.println(Arrays.toString(test));
    }
}
