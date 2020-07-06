package com.example.recyclerviewexample.java.sort;


public interface Sort {
    int[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};

    int[] arr2 = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, 22};

    int[] sort(int[] nums);

    default void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * a[0] 最小，a[1] 最大
     *
     * @param nums
     * @return
     */
    default int[] getMinAndMaxValue(int[] nums) {
        int[] a = new int[2];
        a[0] = a[1] = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < a[0]) a[0] = nums[i];
            if (nums[i] > a[1]) a[1] = nums[i];
        }
        return a;
    }
}
