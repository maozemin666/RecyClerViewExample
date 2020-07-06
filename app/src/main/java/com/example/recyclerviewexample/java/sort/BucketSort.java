package com.example.recyclerviewexample.java.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Date 2020/7/5 18:05
 * @Created by zemin
 */
public class BucketSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        int[] a = getMinAndMaxValue(nums);
        return bucketSort(nums, a[0], a[1]);
    }

    private int[] bucketSort(int[] nums, int min, int max) {
        int bucket = (max - min) / nums.length + 1;
        List<List<Integer>> bucketList = new ArrayList<>(bucket);
        for (int i = 0; i < bucket; i++) {
            bucketList.add(new ArrayList<>());
        }

        for (int i = 0; i < nums.length; i++) {
            int n = (nums[i] - min) / nums.length;
            bucketList.get(n).add(nums[i]);
        }

        for (int i = 0; i < bucketList.size(); i++) {
            Collections.sort(bucketList.get(i));
        }

        int n = 0;
        for (int i = 0; i < bucketList.size(); i++) {
            for (int i1 = 0; i1 < bucketList.get(i).size(); i1++) {
                nums[n++] = bucketList.get(i).get(i1);
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        BucketSort bucketSort = new BucketSort();
        System.out.println(Arrays.toString(bucketSort.sort(arr)));
    }
}
