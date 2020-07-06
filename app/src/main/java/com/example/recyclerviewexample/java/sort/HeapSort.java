package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * 对于第 i个元素，它的左节点的下标是 2i+1 ，右节点的下标是 2i+2。
 * 它的父节点的下标是 i/2 向上取整，然后减 1。
 *
 * @Date 2020/6/27 21:26
 * @Created by zemin
 */
public class HeapSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        int[] arr = Arrays.copyOf(nums, nums.length);
        return heapSort(arr);
    }

    private int[] heapSort(int[] nums) {
        // 构建大顶堆，这里其实就是把待排序序列，变成一个大顶堆结构的数组
        buildMaxHeap(nums);

        int len = nums.length;

        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            len--;
            adjustHeap(nums, 0, len);
        }

        return nums;
    }

    private void buildMaxHeap(int[] nums) {
        // 从最后一个非叶节点开始向前遍历，调整节点性质，使之成为大顶堆
        for (int i = (nums.length - 1) / 2; i >= 0; i--) {
            adjustHeap(nums, i, nums.length);
        }
    }

    private void adjustHeap(int[] nums, int i, int len) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < len && nums[left] > nums[largest]) {
            largest = left;
        }

        if (right < len && nums[right] > nums[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(nums, i, largest);
            // 因为互换之后，子节点的值变了，如果该子节点也有自己的子节点，仍需要再次调整。
            adjustHeap(nums, largest, len);
        }
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        System.out.println(Arrays.toString(heapSort.sort(arr)));
    }
}
