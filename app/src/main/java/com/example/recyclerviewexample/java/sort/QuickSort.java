package com.example.recyclerviewexample.java.sort;

import java.util.Arrays;

public class QuickSort implements Sort {

    @Override
    public int[] sort(int[] nums) {

        return quickSort(nums, 0, nums.length - 1);
    }

    /**
     快速排序其实是冒泡排序的升级版，同样的基于两两交换，但是引入了分治的思想。
     通过使用中线，对需要排序的区间进行了重新的一个划分，而这内部剩下的性能还有一方面就是在于这个中线，
     因为数值的比较不再是全局，而是局部，从效率计算上来讲一般情况可降到O(nlogn)，当然极端情况就可能退化回我们的冒泡排序。

     时间复杂度：O(nlogn)
     空间复杂度：快速排序使用递归，递归使用栈，因此它的空间复杂度为O(logn)
     稳定性：快速排序无法保证相等的元素的相对位置不变，因此它是不稳定的排序算法
     */
    public int[] quickSort(int[] nums, int start, int end) {
        if (start < end) {
            int left = start;
            int right = end;
            int privot = nums[left];
            while (left < right) {
                while (left < right && nums[right] >= privot) right--;
                nums[left] = nums[right];
                while (left < right && nums[left] < privot) left++;
                nums[right] = nums[left];
            }
            nums[left] = privot;
            quickSort(nums, start, left - 1);
            quickSort(nums, left + 1, end);
        }
        return nums;
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        System.out.println(Arrays.toString(quickSort.sort(arr)));
    }

}
