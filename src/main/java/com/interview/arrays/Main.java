package com.interview.arrays;

import com.interview.arrays.modules.*;
import java.util.Arrays;

/** Run this demo, or call any of the documented module methods directly. */
public class Main {
    public static void main(String[] args) {
        System.out.println("Array workbook: 8 topics, 16 classes, 160 questions");
        System.out.println("Two Sum: " + Arrays.toString(new ArraysHashingBasic().twoSum(new int[]{2,7,11,15}, 9)));
        System.out.println("Trapped water: " + new TwoPointersModerate().trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        System.out.println("Window maximum: " + Arrays.toString(new SlidingWindowBasic().maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3)));
        System.out.println("Range sum: " + new PrefixSumBasic.NumArray(new int[]{-2,0,3,-5,2,-1}).sumRange(0, 2));
        System.out.println("First bad version: " + new BinarySearchBasic().firstBadVersion(5, version -> version >= 4));
        System.out.println("Spiral: " + new MatrixBasic().spiralOrder(new int[][]{{1,2,3},{4,5,6},{7,8,9}}));
        System.out.println("Decoded: " + new StringsModerate().decodeString("3[a]2[bc]"));
        System.out.println("Kth largest: " + new SortingBasic().findKthLargest(new int[]{3,2,1,5,6,4}, 2));
    }
}
