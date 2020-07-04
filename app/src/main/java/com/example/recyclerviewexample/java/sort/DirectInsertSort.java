package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * 插入排序，顾名思义，就是把数字放到合适的位置。原理上讲就是将一个无须数组拆分成了两个部分，
 * 一块有序，一块无序。不断的删去无须队列中的数值，放到有序队列中，最后也就成为了有序队列。
 *
 * 时间复杂度为O(n) 空间复杂度为O(1)
 *
 * 相同元素的相对位置不变，如果两个元素相同，插入元素放在相同元素后面。是一种稳定排序
 */
public class DirectInsertSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        return insertSort(nums);
    }

    public int[] insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i;
            int temp = nums[i];

            // 如果在当前已完成排序数组中，下标数值大于当前数值
            // 就把这个数值往后进行移动
            while (j > 0 && temp < nums[j - 1]) {
                nums[j] = nums[j - 1];
                j--;
            }

            if (i != j) nums[j] = temp;
        }
        return nums;
    }

    public static void main(String[] args) {
        DirectInsertSort insertSort = new DirectInsertSort();
        System.out.println(Arrays.toString(insertSort.sort(arr)));
    }
}
