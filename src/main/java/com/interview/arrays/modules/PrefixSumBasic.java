package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 4: Prefix Sum. Basic: source questions 1-12. */
public class PrefixSumBasic {

  /*
   * Question 1: Running Sum of 1d Array
   * 
   * Question: Given an integer array nums, return an array where answer[i] equals nums[0] + nums[1] + ... + nums[i].
   * 
   * Constraints: 1 <= nums.length <= 1000; -10^6 <= nums[i] <= 10^6.
   * 
   * Time and space complexity: Time O(n), Space O(1) extra if nums is reused as the output.
   * 
   * Example 1:
   * Input: nums = [1,2,3,4]
   * Output: [1,3,6,10]
   * Explanation: The prefixes are 1, 1+2, 1+2+3, and 1+2+3+4.
   * 
   * Example 2:
   * Input: nums = [1,1,1,1,1]
   * Output: [1,2,3,4,5]
   * Explanation: Each step adds one more 1 to the previous prefix.
   * 
   * Example 3:
   * Input: nums = [3,1,2,10,1]
   * Output: [3,4,6,16,17]
   * Explanation: The running total is written at every index.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int[] runningSum(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
      nums[i] += nums[i - 1];
    }
    return nums;
  }

  /*
   * Question 2: Find Pivot Index
   * 
   * Question: Given nums, return the leftmost index where the sum of all elements to the left equals the sum of all elements to the right. Return -1 if no such index exists.
   * 
   * Constraints: 1 <= nums.length <= 10^4; -1000 <= nums[i] <= 1000.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,7,3,6,5,6]
   * Output: 3
   * Explanation: Left sum 1+7+3 equals right sum 5+6.
   * 
   * Example 2:
   * Input: nums = [1,2,3]
   * Output: -1
   * Explanation: No index has equal left and right sums.
   * 
   * Example 3:
   * Input: nums = [2,1,-1]
   * Output: 0
   * Explanation: At index 0, the left sum is 0 and the right sum is 1 + -1 = 0.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int pivotIndex(int[] nums) {
    int total = 0;
    for (int num : nums) {
      total += num;
    }

    int left = 0;
    for (int i = 0; i < nums.length; i++) {
      int right = total - left - nums[i];
      if (left == right) return i;
      left += nums[i];
    }
    return -1;
  }

  /*
   * Question 3: Find the Highest Altitude
   * 
   * Question: A biker starts at altitude 0. Given gain[i] as the net altitude change between point i and i + 1, return the highest altitude reached.
   * 
   * Constraints: 1 <= gain.length <= 100; -100 <= gain[i] <= 100.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: gain = [-5,1,5,0,-7]
   * Output: 1
   * Explanation: Altitudes are 0, -5, -4, 1, 1, -6; the maximum is 1.
   * 
   * Example 2:
   * Input: gain = [-4,-3,-2,-1,4,3,2]
   * Output: 0
   * Explanation: Every altitude after the start is below 0, so the highest remains 0.
   * 
   * Example 3:
   * Input: gain = [2,2,-3,-1,2]
   * Output: 4
   * Explanation: Altitudes are 0, 2, 4, 1, 0, 2; the maximum is 4.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int largestAltitude(int[] gain) {
    int altitude = 0;
    int highest = 0;
    for (int change : gain) {
      altitude += change;
      highest = Math.max(highest, altitude);
    }
    return highest;
  }

  /*
   * Question 4: Minimum Value to Get Positive Step by Step Sum
   * 
   * Question: Given an integer array nums, choose the minimum positive startValue so that the running sum startValue + nums[0] + ... + nums[i] is always at least 1.
   * 
   * Constraints: 1 <= nums.length <= 100; -100 <= nums[i] <= 100.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [-3,2,-3,4,2]
   * Output: 5
   * Explanation: Prefix sums are -3, -1, -4, 0, 2; lowest is -4, so startValue is 5.
   * 
   * Example 2:
   * Input: nums = [1,2]
   * Output: 1
   * Explanation: The running sum never drops below 1 when startValue is 1.
   * 
   * Example 3:
   * Input: nums = [1,-2,-3]
   * Output: 5
   * Explanation: Prefix sums are 1, -1, -4; startValue 5 keeps the minimum running sum at 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int minStartValue(int[] nums) {
    int prefix = 0;
    int minPrefix = 0;
    for (int num : nums) {
      prefix += num;
      minPrefix = Math.min(minPrefix, prefix);
    }
    return 1 - minPrefix;
  }

  /*
   * Question 5: Range Sum Query - Immutable
   * 
   * Question: Design NumArray so that sumRange(left, right) returns the sum of nums[left] through nums[right] for many immutable range-sum queries.
   * 
   * Constraints: 1 <= nums.length <= 10^4; -10^5 <= nums[i] <= 10^5; 0 <= left <= right < nums.length; at most 10^4 calls to sumRange.
   * 
   * Time and space complexity: Constructor Time O(n), Space O(n); sumRange Time O(1), Space O(1).
   * 
   * Example 1:
   * Input: nums = [-2,0,3,-5,2,-1], sumRange(0,2)
   * Output: 1
   * Explanation: -2 + 0 + 3 = 1.
   * 
   * Example 2:
   * Input: nums = [-2,0,3,-5,2,-1], sumRange(2,5)
   * Output: -1
   * Explanation: 3 + -5 + 2 + -1 = -1.
   * 
   * Example 3:
   * Input: nums = [-2,0,3,-5,2,-1], sumRange(0,5)
   * Output: -3
   * Explanation: The full array sum is -3.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public static class NumArray {
    private final int[] prefix;
  
    public NumArray(int[] nums) {
      prefix = new int[nums.length + 1];
      for (int i = 0; i < nums.length; i++) {
        prefix[i + 1] = prefix[i] + nums[i];
      }
    }
  
    public int sumRange(int left, int right) {
      return prefix[right + 1] - prefix[left];
    }
  }

  /*
   * Question 6: Range Sum Query 2D - Immutable
   * 
   * Question: Design NumMatrix so that sumRegion(row1, col1, row2, col2) returns the sum of a rectangle in an immutable matrix.
   * 
   * Constraints: 1 <= m, n <= 200; -10^5 <= matrix[i][j] <= 10^5; at most 10^4 calls to sumRegion.
   * 
   * Time and space complexity: Constructor Time O(m*n), Space O(m*n); sumRegion Time O(1), Space O(1).
   * 
   * Example 1:
   * Input: matrix = [[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]], sumRegion(2,1,4,3)
   * Output: 8
   * Explanation: The selected rectangle sums to 8.
   * 
   * Example 2:
   * Input: same matrix, sumRegion(1,1,2,2)
   * Output: 11
   * Explanation: 6 + 3 + 2 + 0 = 11.
   * 
   * Example 3:
   * Input: same matrix, sumRegion(1,2,2,4)
   * Output: 12
   * Explanation: 3 + 2 + 1 + 0 + 1 + 5 = 12.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   * Implementation note: Use long prefix sums and return long: the documented bounds allow a rectangle sum of 4,000,000,000.
   */
  public static class NumMatrix {
    private final long[][] prefix;
  
    public NumMatrix(int[][] matrix) {
      int rows = matrix.length;
      int cols = matrix[0].length;
      prefix = new long[rows + 1][cols + 1];
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          prefix[row + 1][col + 1] = matrix[row][col]
              + prefix[row][col + 1]
              + prefix[row + 1][col]
              - prefix[row][col];
        }
      }
    }
  
    public long sumRegion(int row1, int col1, int row2, int col2) {
      return prefix[row2 + 1][col2 + 1]
          - prefix[row1][col2 + 1]
          - prefix[row2 + 1][col1]
          + prefix[row1][col1];
    }
  }

  /*
   * Question 7: Matrix Block Sum
   * 
   * Question: Given a matrix mat and integer k, return answer where answer[i][j] is the sum of all mat[r][c] with |r - i| <= k and |c - j| <= k inside matrix bounds.
   * 
   * Constraints: 1 <= m, n, k <= 100; 1 <= mat[i][j] <= 100.
   * 
   * Time and space complexity: Time O(m*n), Space O(m*n) for prefix and answer.
   * 
   * Example 1:
   * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1
   * Output: [[12,21,16],[27,45,33],[24,39,28]]
   * Explanation: Each answer cell sums its clipped 3x3 neighborhood.
   * 
   * Example 2:
   * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 2
   * Output: [[45,45,45],[45,45,45],[45,45,45]]
   * Explanation: Every block covers the entire matrix.
   * 
   * Example 3:
   * Input: mat = [[5]], k = 0
   * Output: [[5]]
   * Explanation: The only block is the single cell itself.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int[][] matrixBlockSum(int[][] mat, int k) {
    int rows = mat.length;
    int cols = mat[0].length;
    int[][] prefix = new int[rows + 1][cols + 1];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        prefix[row + 1][col + 1] = mat[row][col]
            + prefix[row][col + 1]
            + prefix[row + 1][col]
            - prefix[row][col];
      }
    }

    int[][] ans = new int[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int r1 = Math.max(0, row - k);
        int c1 = Math.max(0, col - k);
        int r2 = Math.min(rows - 1, row + k);
        int c2 = Math.min(cols - 1, col + k);
        ans[row][col] = sumQ7(prefix, r1, c1, r2, c2);
      }
    }
    return ans;
  }

  private int sumQ7(int[][] prefix, int r1, int c1, int r2, int c2) {
    return prefix[r2 + 1][c2 + 1]
        - prefix[r1][c2 + 1]
        - prefix[r2 + 1][c1]
        + prefix[r1][c1];
  }

  /*
   * Question 8: Left and Right Sum Differences
   * 
   * Question: Given nums, return an array answer where answer[i] is the absolute difference between the sum of elements left of i and the sum of elements right of i.
   * 
   * Constraints: 1 <= nums.length <= 1000; 1 <= nums[i] <= 10^5.
   * 
   * Time and space complexity: Time O(n), Space O(n) for the answer; O(1) extra state.
   * 
   * Example 1:
   * Input: nums = [10,4,8,3]
   * Output: [15,1,11,22]
   * Explanation: At index 1, left sum is 10 and right sum is 11, so the difference is 1.
   * 
   * Example 2:
   * Input: nums = [1]
   * Output: [0]
   * Explanation: Both left and right sums are empty and equal to 0.
   * 
   * Example 3:
   * Input: nums = [1,2,3,4]
   * Output: [9,6,1,6]
   * Explanation: Each position compares accumulated left sum with remaining right sum.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int[] leftRightDifference(int[] nums) {
    int total = 0;
    for (int num : nums) total += num;

    int[] ans = new int[nums.length];
    int left = 0;
    for (int i = 0; i < nums.length; i++) {
      int right = total - left - nums[i];
      ans[i] = Math.abs(left - right);
      left += nums[i];
    }
    return ans;
  }

  /*
   * Question 9: Find the Middle Index in Array
   * 
   * Question: Given nums, return the leftmost middleIndex where the sum strictly before the index equals the sum strictly after it. Return -1 if none exists.
   * 
   * Constraints: 1 <= nums.length <= 100; -1000 <= nums[i] <= 1000.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [2,3,-1,8,4]
   * Output: 3
   * Explanation: Left sum 2+3-1 equals right sum 4.
   * 
   * Example 2:
   * Input: nums = [1,-1,4]
   * Output: 2
   * Explanation: Left sum 0 equals empty right sum 0.
   * 
   * Example 3:
   * Input: nums = [2,5]
   * Output: -1
   * Explanation: No index balances the two sides.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int findMiddleIndex(int[] nums) {
    int total = 0;
    for (int num : nums) total += num;

    int left = 0;
    for (int i = 0; i < nums.length; i++) {
      int right = total - left - nums[i];
      if (left == right) return i;
      left += nums[i];
    }
    return -1;
  }

  /*
   * Question 10: Sum of All Odd Length Subarrays
   * 
   * Question: Given an array arr of positive integers, return the sum of all possible odd-length subarrays.
   * 
   * Constraints: 1 <= arr.length <= 100; 1 <= arr[i] <= 1000.
   * 
   * Time and space complexity: Time O(n); Space O(1). Count each element's contribution to odd-length subarrays.
   * 
   * Example 1:
   * Input: arr = [1,4,2,5,3]
   * Output: 58
   * Explanation: Length 1, 3, and 5 totals are 15 + 28 + 15 = 58.
   * 
   * Example 2:
   * Input: arr = [1,2]
   * Output: 3
   * Explanation: Only odd-length subarrays are [1] and [2].
   * 
   * Example 3:
   * Input: arr = [10,11,12]
   * Output: 66
   * Explanation: Single elements sum to 33 and the full length-3 subarray also sums to 33.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   * Implementation note: Replaces the source's quadratic prefix enumeration with linear contribution counting.
   */
  public int sumOddLengthSubarrays(int[] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
      int containing = (i + 1) * (arr.length - i);
      sum += ((containing + 1) / 2) * arr[i];
    }
    return sum;
  }

  /*
   * Question 11: Number of Ways to Split Array
   * 
   * Question: Given a 0-indexed integer array nums, count split positions i where the left part nums[0..i] and right part nums[i+1..n-1] are both non-empty and leftSum >= rightSum.
   * 
   * Constraints: 2 <= nums.length <= 10^5; -10^5 <= nums[i] <= 10^5.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [10,4,-8,7]
   * Output: 2
   * Explanation: Splits after indices 0 and 1 satisfy leftSum >= rightSum.
   * 
   * Example 2:
   * Input: nums = [2,3,1,0]
   * Output: 2
   * Explanation: Splits after indices 1 and 2 are valid.
   * 
   * Example 3:
   * Input: nums = [-1,-2,-3]
   * Output: 1
   * Explanation: Only the split after index 1 has left sum -3 >= right sum -3.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int waysToSplitArray(int[] nums) {
    long total = 0;
    for (int num : nums) total += num;

    int ways = 0;
    long left = 0;
    for (int i = 0; i < nums.length - 1; i++) {
      left += nums[i];
      long right = total - left;
      if (left >= right) ways++;
    }
    return ways;
  }

  /*
   * Question 12: Maximum Population Year
   * 
   * Question: Given birth and death years where a person is alive from birth through death - 1, return the earliest year with the maximum population.
   * 
   * Constraints: 1 <= logs.length <= 100; 1950 <= birth < death <= 2050.
   * 
   * Time and space complexity: Time O(n + Y), Space O(Y).
   * 
   * Example 1:
   * Input: logs = [[1993,1999],[2000,2010]]
   * Output: 1993
   * Explanation: Population is 1 in 1993 and 2000, so the earliest max year is 1993.
   * 
   * Example 2:
   * Input: logs = [[1950,1961],[1960,1971],[1970,1981]]
   * Output: 1960
   * Explanation: Population reaches 2 first in 1960.
   * 
   * Example 3:
   * Input: logs = [[2000,2001]]
   * Output: 2000
   * Explanation: The person is alive in 2000 and not in 2001.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int maximumPopulation(int[][] logs) {
    int[] diff = new int[102];
    for (int[] log : logs) {
      diff[log[0] - 1950]++;
      diff[log[1] - 1950]--;
    }

    int bestYear = 1950;
    int bestPopulation = 0;
    int population = 0;
    for (int i = 0; i <= 100; i++) {
      population += diff[i];
      if (population > bestPopulation) {
        bestPopulation = population;
        bestYear = 1950 + i;
      }
    }
    return bestYear;
  }
}
