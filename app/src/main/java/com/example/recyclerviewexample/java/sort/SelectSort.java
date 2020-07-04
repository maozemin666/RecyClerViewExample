package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * 空间效率为O(1)
 * 元素移动次数很少，当表有序时移动次数为0，但比较的次数与表的次序无关，所以时间复杂度始终为O(n2)
 *
 * @Date 2020/6/27 21:10
 * @Created by zemin
 */
public class SelectSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        return selectSort(nums);
    }

    /**
     * 基于原理：和冒泡排序完全一致。
     */
    private int[] selectSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            swap(nums, i, min);
        }
        return nums;
    }

    public static void main(String[] args) {
        SelectSort selectSort = new SelectSort();
        System.out.println(Arrays.toString(selectSort.sort(arr)));
    }
}
