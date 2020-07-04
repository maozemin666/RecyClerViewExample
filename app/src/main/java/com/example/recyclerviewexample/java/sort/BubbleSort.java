package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

public class BubbleSort implements Sort {

    @Override
    public int[] sort(int[] nums) {
        return bubbleSort(nums);
    }

    // 基于原理：
    // 每一趟完整的循环就对完成一个最大值或者最小值的放置
    // 那每一趟都可以删去枝叶，也就是最大值或者最小值的位置
    // i的大小也同样可以确定已经完成排序的数值的个数
    private int[] bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        System.out.println(Arrays.toString(bubbleSort.sort(arr)));
    }
}
