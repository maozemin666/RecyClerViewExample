package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * 算法分析
 * 由算法示例可知，计数排序的时间复杂度为 。因为算法过程中需要申请一个额外空间和一个与待排序集合大小相同的已排序空间，
 * 所以空间复杂度为 。由此可知，计数排序只适用于元素值较为集中的情况，若集合中存在最大最小元素值相差甚远的情况，
 * 则计数排序开销较大、性能较差。通过额外空间的作用方式可知，额外空间存储元素信息是通过计算元素与最小元素值的差值作为下标来完成的，
 * 若待排序集合中存在元素值为浮点数形式或其他形式，则需要对元素值或元素差值做变换，以保证所有差值都为一个非负整数形式。
 *
 * @Date 2020/7/5 17:06
 * @Created by zemin
 */
public class CountSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        int[] a = getMinAndMaxValue(nums);
        return countSort(nums, a[0], a[1]);
    }

    private int[] countSort(int[] nums, int min, int max) {
        int[] temp = new int[max - min + 1];
        for (int i = 0; i < nums.length; i++) {
            temp[nums[i] - min]++;
        }

        int n = 0;
        for (int i = 0; i < temp.length; i++) {
            while (temp[i] > 0) {
                nums[n++] = i + min;  // 取出这个数
                temp[i]--;  // 计数-1
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        CountSort countSort = new CountSort();
        System.out.println(Arrays.toString(countSort.sort(arr)));
    }
}
