package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * @Description TODO
 * @Date 2020/6/27 19:48
 * @Created by zemin
 */
public class XiErSort implements Sort {

    @Override
    public int[] sort(int[] nums) {
        return xiErSort(nums);
    }

    private int[] xiErSort(int[] nums) {
        for (int gap = nums.length >> 1; gap > 0; gap /= 2) {
            for (int i = gap; i < nums.length; i++) {

            }
        }
        return nums;
    }

    public static void main(String[] args) {
        XiErSort xiErSort = new XiErSort();
        System.out.println(Arrays.toString(xiErSort.sort(arr)));
    }
}
