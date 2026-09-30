package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 5: Binary Search. Moderate: source questions 13-20. */
public class BinarySearchModerate {

  /*
   * Question 13: Koko Eating Bananas
   * 
   * Question: Koko has piles of bananas and h hours. Each hour she chooses one pile and eats up to k bananas from it. Return the minimum integer speed k such that she can eat all bananas within h hours.
   * 
   * Constraints: 1 <= piles.length <= 10000; piles.length <= h <= 1000000000; 1 <= piles[i] <= 1000000000.
   * 
   * Time and space complexity: Time O(n log maxPile); Space O(1). Each binary-search check scans all piles once.
   * 
   * Example 1:
   * Input: piles = [3,6,7,11], h = 8
   * Output: 4
   * Explanation: At speed 4, total hours are 1 + 2 + 2 + 3 = 8.
   * 
   * Example 2:
   * Input: piles = [30,11,23,4,20], h = 5
   * Output: 30
   * Explanation: Only speed 30 can finish one pile per hour.
   * 
   * Example 3:
   * Input: piles = [30,11,23,4,20], h = 6
   * Output: 23
   * Explanation: Speed 23 finishes within 6 hours; lower speeds do not.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int minEatingSpeed(int[] piles, int h) {
    int left = 1;
    int right = 0;
    for (int pile : piles) right = Math.max(right, pile);

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (canFinishQ13(piles, h, mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    return left;
  }

  private boolean canFinishQ13(int[] piles, int h, int speed) {
    long hours = 0;
    for (int pile : piles) {
      hours += (pile + speed - 1L) / speed;
      if (hours > h) return false;
    }
    return true;
  }

  /*
   * Question 14: Capacity To Ship Packages Within D Days
   * 
   * Question: Given package weights in order and an integer days, return the least ship capacity needed to ship all packages within days days. Packages must be shipped in the given order.
   * 
   * Constraints: 1 <= days <= weights.length <= 50000; 1 <= weights[i] <= 500.
   * 
   * Time and space complexity: Time O(n log sumWeights); Space O(1). Each feasibility check scans weights once.
   * 
   * Example 1:
   * Input: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
   * Output: 15
   * Explanation: Capacity 15 can ship the packages in 5 days.
   * 
   * Example 2:
   * Input: weights = [3,2,2,4,1,4], days = 3
   * Output: 6
   * Explanation: Capacity 6 is the least feasible capacity.
   * 
   * Example 3:
   * Input: weights = [1,2,3,1,1], days = 4
   * Output: 3
   * Explanation: Capacity 3 ships all packages within 4 days.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int shipWithinDays(int[] weights, int days) {
    int left = 0;
    int right = 0;

    for (int weight : weights) {
      left = Math.max(left, weight);
      right += weight;
    }

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (canShipQ14(weights, days, mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    return left;
  }

  private boolean canShipQ14(int[] weights, int days, int capacity) {
    int usedDays = 1;
    int load = 0;

    for (int weight : weights) {
      if (load + weight > capacity) {
        usedDays++;
        load = 0;
      }
      load += weight;
      if (usedDays > days) return false;
    }

    return true;
  }

  /*
   * Question 15: Split Array Largest Sum
   * 
   * Question: Given an integer array nums and an integer k, split nums into k non-empty contiguous subarrays. Return the minimized largest sum among these subarrays.
   * 
   * Constraints: 1 <= nums.length <= 1000; 0 <= nums[i] <= 1000000; 1 <= k <= min(50, nums.length).
   * 
   * Time and space complexity: Time O(n log sumNums); Space O(1). Binary search checks each candidate limit with one greedy scan.
   * 
   * Example 1:
   * Input: nums = [7,2,5,10,8], k = 2
   * Output: 18
   * Explanation: Split as [7,2,5] and [10,8]; largest sum is 18.
   * 
   * Example 2:
   * Input: nums = [1,2,3,4,5], k = 2
   * Output: 9
   * Explanation: Split as [1,2,3] and [4,5].
   * 
   * Example 3:
   * Input: nums = [1,4,4], k = 3
   * Output: 4
   * Explanation: Each element can be its own subarray.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int splitArray(int[] nums, int k) {
    int left = 0;
    int right = 0;

    for (int num : nums) {
      left = Math.max(left, num);
      right += num;
    }

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (canSplitQ15(nums, k, mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    return left;
  }

  private boolean canSplitQ15(int[] nums, int k, int limit) {
    int parts = 1;
    int currentSum = 0;

    for (int num : nums) {
      if (currentSum + num > limit) {
        parts++;
        currentSum = 0;
      }
      currentSum += num;
      if (parts > k) return false;
    }

    return true;
  }

  /*
   * Question 16: Minimized Maximum of Products Distributed to Any Store
   * 
   * Question: You have n stores and product quantities where quantities[i] is the count of one product type. A store can receive at most one product type, but any amount of that type. Return the minimum possible maximum number of products assigned to any store.
   * 
   * Constraints: 1 <= quantities.length <= n <= 100000; 1 <= quantities[i] <= 100000.
   * 
   * Time and space complexity: Time O(m log maxQuantity); Space O(1). Each feasibility check scans all product quantities once.
   * 
   * Example 1:
   * Input: n = 6, quantities = [11,6]
   * Output: 3
   * Explanation: 11 needs 4 stores and 6 needs 2 stores when maximum load is 3.
   * 
   * Example 2:
   * Input: n = 7, quantities = [15,10,10]
   * Output: 5
   * Explanation: Each type can be split so no store gets more than 5 products.
   * 
   * Example 3:
   * Input: n = 1, quantities = [100000]
   * Output: 100000
   * Explanation: The only store must receive all products.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int minimizedMaximum(int n, int[] quantities) {
    int left = 1;
    int right = 0;
    for (int quantity : quantities) right = Math.max(right, quantity);

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (canDistributeQ16(n, quantities, mid)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    return left;
  }

  private boolean canDistributeQ16(int n, int[] quantities, int limit) {
    long stores = 0;
    for (int quantity : quantities) {
      stores += (quantity + limit - 1L) / limit;
      if (stores > n) return false;
    }
    return true;
  }

  /*
   * Question 17: Magnetic Force Between Two Balls
   * 
   * Question: Given basket positions and an integer m, place m balls into baskets so that the minimum magnetic force between any two balls is maximized. Magnetic force is the absolute difference between positions. Return that maximum possible minimum distance.
   * 
   * Constraints: 2 <= position.length <= 100000; 1 <= position[i] <= 1000000000; all positions are distinct; 2 <= m <= position.length.
   * 
   * Time and space complexity: Time O(n log n + n log range); Space O(1) excluding sort. Binary search each feasible distance with a greedy scan.
   * 
   * Example 1:
   * Input: position = [1,2,3,4,7], m = 3
   * Output: 3
   * Explanation: Place balls at 1, 4, and 7 for minimum distance 3.
   * 
   * Example 2:
   * Input: position = [5,4,3,2,1,1000000000], m = 2
   * Output: 999999999
   * Explanation: Place balls at positions 1 and 1000000000.
   * 
   * Example 3:
   * Input: position = [1,10,20,30], m = 4
   * Output: 9
   * Explanation: All baskets are used, so the smallest adjacent gap is 9.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int maxDistance(int[] position, int m) {
    Arrays.sort(position);
    int left = 1;
    int right = position[position.length - 1] - position[0];
    int answer = 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (canPlaceQ17(position, m, mid)) {
        answer = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return answer;
  }

  private boolean canPlaceQ17(int[] position, int m, int distance) {
    int count = 1;
    int last = position[0];

    for (int i = 1; i < position.length; i++) {
      if (position[i] - last >= distance) {
        count++;
        last = position[i];
        if (count == m) return true;
      }
    }

    return false;
  }

  /*
   * Question 18: Aggressive Cows
   * 
   * Question: Given stall positions and k cows, place the cows in stalls so that the minimum distance between any two cows is as large as possible. Return that largest minimum distance.
   * 
   * Constraints: 2 <= stalls.length <= 100000; 2 <= k <= stalls.length; 0 <= stalls[i] <= 1000000000.
   * 
   * Time and space complexity: Time O(n log n + n log range); Space O(1) excluding sort. Binary search uses greedy feasibility.
   * 
   * Example 1:
   * Input: stalls = [1,2,4,8,9], k = 3
   * Output: 3
   * Explanation: Place cows at 1, 4, and 8 or 9 for minimum distance 3.
   * 
   * Example 2:
   * Input: stalls = [10,1,2,7,5], k = 3
   * Output: 4
   * Explanation: After sorting, place cows at 1, 5, and 10.
   * 
   * Example 3:
   * Input: stalls = [1,2,3], k = 2
   * Output: 2
   * Explanation: Place cows at 1 and 3.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public static int aggressiveCows(int[] stalls, int k) {
    Arrays.sort(stalls);
    int left = 1;
    int right = stalls[stalls.length - 1] - stalls[0];
    int answer = 0;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (canPlaceQ18(stalls, k, mid)) {
        answer = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return answer;
  }

  private static boolean canPlaceQ18(int[] stalls, int k, int distance) {
    int cows = 1;
    int last = stalls[0];

    for (int i = 1; i < stalls.length; i++) {
      if (stalls[i] - last >= distance) {
        cows++;
        last = stalls[i];
        if (cows == k) return true;
      }
    }

    return false;
  }

  /*
   * Question 19: Median of Two Sorted Arrays
   * 
   * Question: Given two sorted arrays nums1 and nums2, return the median of the two sorted arrays. The optimized solution must run in O(log(m + n)) time.
   * 
   * Constraints: 0 <= nums1.length, nums2.length <= 1000; 1 <= nums1.length + nums2.length <= 2000; -1000000 <= nums1[i], nums2[i] <= 1000000.
   * 
   * Time and space complexity: Time O(log min(m, n)); Space O(1). Binary search only partitions the smaller array.
   * 
   * Example 1:
   * Input: nums1 = [1,3], nums2 = [2]
   * Output: 2.00000
   * Explanation: The merged sorted array is [1,2,3].
   * 
   * Example 2:
   * Input: nums1 = [1,2], nums2 = [3,4]
   * Output: 2.50000
   * Explanation: Median is (2 + 3) / 2.
   * 
   * Example 3:
   * Input: nums1 = [], nums2 = [1]
   * Output: 1.00000
   * Explanation: The second array alone determines the median.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

    int m = nums1.length;
    int n = nums2.length;
    int leftSize = (m + n + 1) / 2;
    int left = 0;
    int right = m;

    while (left <= right) {
      int cut1 = left + (right - left) / 2;
      int cut2 = leftSize - cut1;

      int left1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[cut1 - 1];
      int right1 = cut1 == m ? Integer.MAX_VALUE : nums1[cut1];
      int left2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[cut2 - 1];
      int right2 = cut2 == n ? Integer.MAX_VALUE : nums2[cut2];

      if (left1 <= right2 && left2 <= right1) {
        if ((m + n) % 2 == 1) return Math.max(left1, left2);
        return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
      }

      if (left1 > right2) right = cut1 - 1;
      else left = cut1 + 1;
    }

    return 0.0;
  }

  /*
   * Question 20: Kth Missing Positive Number
   * 
   * Question: Given a strictly increasing positive integer array arr and an integer k, return the kth positive integer missing from arr.
   * 
   * Constraints: 1 <= arr.length <= 1000; 1 <= arr[i] <= 1000; arr is strictly increasing; 1 <= k <= 1000.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Binary search the first index with at least k missing numbers before it.
   * 
   * Example 1:
   * Input: arr = [2,3,4,7,11], k = 5
   * Output: 9
   * Explanation: The missing positives are 1, 5, 6, 8, 9.
   * 
   * Example 2:
   * Input: arr = [1,2,3,4], k = 2
   * Output: 6
   * Explanation: The missing positives are 5 and 6.
   * 
   * Example 3:
   * Input: arr = [5,6,7], k = 1
   * Output: 1
   * Explanation: 1 is missing before the first array value.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int findKthPositive(int[] arr, int k) {
    int left = 0;
    int right = arr.length;

    while (left < right) {
      int mid = left + (right - left) / 2;
      int missingBeforeMid = arr[mid] - mid - 1;

      if (missingBeforeMid < k) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left + k;
  }
}
