package com.example.recyclerviewexample.java.sort;


public interface Sort {
    int[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};

    int[] sort(int[] nums);

    default void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
