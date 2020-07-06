package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * @Date 2020/7/6 22:48
 * @Created by zemin
 */
public class RadixSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        int[] arr = Arrays.copyOf(nums, nums.length);
        radixSort(arr, 1000);
        return arr;
    }

    private void radixSort(int[] nums, int d) {
        int len = nums.length;
        int n = 1;

        int[][] bucket = new int[10][len];
        int[] count = new int[10];

        while (n < d) {
            for (int num : nums) {
                int digit = (num / n) % 10;
                bucket[digit][count[digit]] = num;
                count[digit]++;
            }

            int k = 0;
            for (int i = 0; i < count.length; i++) {
                if (count[i] != 0) {
                    for (int j = 0; j < count[i]; j++) {
                        nums[k++] = bucket[i][j];
                    }
                }
                count[i] = 0;
            }
            n *= 10;
        }
    }

    public static void main(String[] args) {
        RadixSort radixSort = new RadixSort();
        System.out.println(Arrays.toString(radixSort.sort(arr2)));
    }
}
