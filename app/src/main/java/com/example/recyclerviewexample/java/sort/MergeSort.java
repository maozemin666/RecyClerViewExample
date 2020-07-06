package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

/**
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(N)，归并排序需要一个与原数组相同长度的数组做辅助来排序
 * 稳定性：归并排序是稳定的排序算法，temp[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
 * 这行代码可以保证当左右两部分的值相等的时候，先复制左边的值，这样可以保证值相等的时候两个元素的相对位置不变。
 *
 * @Date 2020/7/5 15:38
 * @Created by zemin
 */
public class MergeSort implements Sort {
    @Override
    public int[] sort(int[] nums) {
        int[] arr = Arrays.copyOf(nums, nums.length);
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    private void mergeSort(int[] nums, int left, int right) {
        if (left == right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(nums, left, mid);
        mergeSort(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = 0;
        int leftStart = left;
        int rightStart = mid + 1;

        // 比较左右两部分的元素，哪个小，把那个元素填入temp中
        while (leftStart <= mid && rightStart <= right) {
            temp[i++] = nums[leftStart] < nums[rightStart] ? nums[leftStart++] : nums[rightStart++];
        }

        // 上面的循环退出后，把剩余的元素依次填入到temp中
        // 以下两个while只有一个会执行
        while (leftStart <= mid) temp[i++] = nums[leftStart++];
        while (rightStart <= right) temp[i++] = nums[rightStart++];

        for (int j = 0; j < temp.length; j++) {
            nums[left + j] = temp[j];
        }
    }

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        System.out.println(Arrays.toString(mergeSort.sort(arr)));
    }
}
