package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 5: Binary Search. Basic: source questions 1-12. */
public class BinarySearchBasic {

  /*
   * Question 1: Binary Search
   * 
   * Question: Given an array nums sorted in strictly increasing order and an integer target, return the index of target if it is present. If target is not present, return -1. The required optimized solution must run in O(log n) time.
   * 
   * Constraints: 1 <= nums.length <= 10000; -10000 < nums[i], target < 10000; nums is sorted in strictly increasing order.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Each loop removes half of the remaining search window.
   * 
   * Example 1:
   * Input: nums = [-1,0,3,5,9,12], target = 9
   * Output: 4
   * Explanation: nums[4] is 9.
   * 
   * Example 2:
   * Input: nums = [-1,0,3,5,9,12], target = 2
   * Output: -1
   * Explanation: 2 is not present in nums.
   * 
   * Example 3:
   * Input: nums = [5], target = -5
   * Output: -1
   * Explanation: The only value does not match target.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] == target) return mid;
      if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return -1;
  }

  /*
   * Question 2: First Bad Version
   * 
   * Question: You are given versions 1 through n and an API isBadVersion(version). All versions after a bad version are also bad. Return the first bad version while calling the API as few times as possible.
   * 
   * Constraints: 1 <= bad <= n <= 2^31 - 1; isBadVersion(version) returns false before bad and true from bad onward.
   * 
   * Time and space complexity: Time O(log n) API calls; Space O(1). Binary search finds the first true predicate value.
   * 
   * Example 1:
   * Input: n = 5, bad = 4
   * Output: 4
   * Explanation: Versions 1, 2, and 3 are good; versions 4 and 5 are bad.
   * 
   * Example 2:
   * Input: n = 1, bad = 1
   * Output: 1
   * Explanation: The only version is the first bad version.
   * 
   * Example 3:
   * Input: n = 7, bad = 1
   * Output: 1
   * Explanation: Every version is bad, so the first version is the answer.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   * Implementation note: Pass the version API as a predicate, for example version -> version >= 4.
   */
  public int firstBadVersion(int n, java.util.function.IntPredicate isBadVersion) {
    int left = 1, right = n;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (isBadVersion.test(mid)) right = mid;
      else left = mid + 1;
    }
    return left;
  }

  /*
   * Question 3: Search Insert Position
   * 
   * Question: Given a sorted array of distinct integers nums and an integer target, return the index if target is found. Otherwise return the index where target should be inserted to keep nums sorted. The optimized solution must run in O(log n) time.
   * 
   * Constraints: 1 <= nums.length <= 10000; -10000 <= nums[i], target <= 10000; nums is sorted in ascending order with distinct values.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Lower-bound binary search halves the candidate index range.
   * 
   * Example 1:
   * Input: nums = [1,3,5,6], target = 5
   * Output: 2
   * Explanation: 5 already exists at index 2.
   * 
   * Example 2:
   * Input: nums = [1,3,5,6], target = 2
   * Output: 1
   * Explanation: 2 belongs between 1 and 3.
   * 
   * Example 3:
   * Input: nums = [1,3,5,6], target = 7
   * Output: 4
   * Explanation: 7 should be inserted after all existing values.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length;

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;
  }

  /*
   * Question 4: Find First and Last Position of Element in Sorted Array
   * 
   * Question: Given an array nums sorted in non-decreasing order and an integer target, return the first and last index of target. If target is not found, return [-1, -1]. The optimized solution must run in O(log n) time.
   * 
   * Constraints: 0 <= nums.length <= 100000; -1000000000 <= nums[i], target <= 1000000000; nums is sorted in non-decreasing order.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Two binary searches locate the lower and upper boundaries.
   * 
   * Example 1:
   * Input: nums = [5,7,7,8,8,10], target = 8
   * Output: [3,4]
   * Explanation: 8 starts at index 3 and ends at index 4.
   * 
   * Example 2:
   * Input: nums = [5,7,7,8,8,10], target = 6
   * Output: [-1,-1]
   * Explanation: 6 is not present.
   * 
   * Example 3:
   * Input: nums = [], target = 0
   * Output: [-1,-1]
   * Explanation: The array is empty.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int[] searchRange(int[] nums, int target) {
    int first = findFirstQ4(nums, target);
    int last = findLastQ4(nums, target);
    return new int[] {first, last};
  }

  private int findFirstQ4(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int answer = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] >= target) {
        if (nums[mid] == target) answer = mid;
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return answer;
  }

  private int findLastQ4(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int answer = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] <= target) {
        if (nums[mid] == target) answer = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return answer;
  }

  /*
   * Question 5: Sqrt(x)
   * 
   * Question: Given a non-negative integer x, return the floor of its square root. You must not use built-in exponent functions or square-root functions.
   * 
   * Constraints: 0 <= x <= 2^31 - 1; built-in exponent and square-root functions are not allowed.
   * 
   * Time and space complexity: Time O(log x); Space O(1). Binary search over candidate roots from 1 to x / 2.
   * 
   * Example 1:
   * Input: x = 4
   * Output: 2
   * Explanation: 2 * 2 equals 4.
   * 
   * Example 2:
   * Input: x = 8
   * Output: 2
   * Explanation: The real square root is about 2.828, so the floor is 2.
   * 
   * Example 3:
   * Input: x = 2147395599
   * Output: 46339
   * Explanation: 46339 is the largest integer whose square does not exceed x.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int mySqrt(int x) {
    if (x < 2) return x;

    int left = 1;
    int right = x / 2;
    int answer = 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      long square = (long) mid * mid;

      if (square <= x) {
        answer = mid;
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return answer;
  }

  /*
   * Question 6: Valid Perfect Square
   * 
   * Question: Given a positive integer num, return true if num is a perfect square. Otherwise return false. You must not use built-in square-root functions.
   * 
   * Constraints: 1 <= num <= 2^31 - 1; do not use built-in square-root functions.
   * 
   * Time and space complexity: Time O(log num); Space O(1). Binary search halves the candidate root range.
   * 
   * Example 1:
   * Input: num = 16
   * Output: true
   * Explanation: 4 * 4 equals 16.
   * 
   * Example 2:
   * Input: num = 14
   * Output: false
   * Explanation: No integer squared equals 14.
   * 
   * Example 3:
   * Input: num = 1
   * Output: true
   * Explanation: 1 * 1 equals 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public boolean isPerfectSquare(int num) {
    if (num == 1) return true;

    int left = 1;
    int right = num / 2;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      long square = (long) mid * mid;

      if (square == num) return true;
      if (square < num) left = mid + 1;
      else right = mid - 1;
    }

    return false;
  }

  /*
   * Question 7: Search in Rotated Sorted Array
   * 
   * Question: Given a sorted array of distinct integers that has been rotated at an unknown pivot and an integer target, return the index of target if it exists. Otherwise return -1. The optimized solution must run in O(log n) time.
   * 
   * Constraints: 1 <= nums.length <= 5000; -10000 <= nums[i], target <= 10000; nums contains distinct values and is rotated from ascending order.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Each step discards one sorted or impossible half.
   * 
   * Example 1:
   * Input: nums = [4,5,6,7,0,1,2], target = 0
   * Output: 4
   * Explanation: 0 is at index 4 after the rotation pivot.
   * 
   * Example 2:
   * Input: nums = [4,5,6,7,0,1,2], target = 3
   * Output: -1
   * Explanation: 3 is not in the array.
   * 
   * Example 3:
   * Input: nums = [1], target = 0
   * Output: -1
   * Explanation: The only element does not match target.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int searchRotated(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) return mid;

      if (nums[left] <= nums[mid]) {
        if (nums[left] <= target && target < nums[mid]) {
          right = mid - 1;
        } else {
          left = mid + 1;
        }
      } else {
        if (nums[mid] < target && target <= nums[right]) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
    }

    return -1;
  }

  /*
   * Question 8: Find Minimum in Rotated Sorted Array
   * 
   * Question: Given a sorted array of unique integers rotated between 1 and n times, return the minimum element. The optimized solution must run in O(log n) time.
   * 
   * Constraints: 1 <= nums.length <= 5000; -5000 <= nums[i] <= 5000; all integers are unique; nums is a rotated ascending array.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Binary search moves toward the rotation boundary.
   * 
   * Example 1:
   * Input: nums = [3,4,5,1,2]
   * Output: 1
   * Explanation: 1 is the rotation boundary and the minimum value.
   * 
   * Example 2:
   * Input: nums = [4,5,6,7,0,1,2]
   * Output: 0
   * Explanation: 0 is smaller than every other value.
   * 
   * Example 3:
   * Input: nums = [11,13,15,17]
   * Output: 11
   * Explanation: The array is effectively already sorted.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int findMin(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] > nums[right]) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return nums[left];
  }

  /*
   * Question 9: Find Peak Element
   * 
   * Question: A peak element is an element strictly greater than its neighbors. Given an integer array nums where adjacent values are not equal, return the index of any peak element. Treat nums[-1] and nums[n] as negative infinity. The optimized solution must run in O(log n) time.
   * 
   * Constraints: 1 <= nums.length <= 1000; -2^31 <= nums[i] <= 2^31 - 1; nums[i] != nums[i + 1].
   * 
   * Time and space complexity: Time O(log n); Space O(1). Each slope check discards one side that does not need to be searched.
   * 
   * Example 1:
   * Input: nums = [1,2,3,1]
   * Output: 2
   * Explanation: 3 is greater than both neighbors.
   * 
   * Example 2:
   * Input: nums = [1,2,1,3,5,6,4]
   * Output: 5
   * Explanation: Index 5 is valid because 6 is a peak; index 1 is also a valid peak.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: 0
   * Explanation: The single element is a peak.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int findPeakElement(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] < nums[mid + 1]) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;
  }

  /*
   * Question 10: Peak Index in a Mountain Array
   * 
   * Question: Given a mountain array arr, return the index of its peak. A mountain array strictly increases up to one peak and then strictly decreases.
   * 
   * Constraints: 3 <= arr.length <= 100000; 0 <= arr[i] <= 1000000; arr is guaranteed to be a valid mountain array.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Binary search follows the slope toward the peak.
   * 
   * Example 1:
   * Input: arr = [0,1,0]
   * Output: 1
   * Explanation: 1 is greater than both neighbors.
   * 
   * Example 2:
   * Input: arr = [0,2,1,0]
   * Output: 1
   * Explanation: 2 is the peak value.
   * 
   * Example 3:
   * Input: arr = [0,10,5,2]
   * Output: 1
   * Explanation: 10 is the only turning point.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int peakIndexInMountainArray(int[] arr) {
    int left = 0;
    int right = arr.length - 1;

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (arr[mid] < arr[mid + 1]) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;
  }

  /*
   * Question 11: Single Element in a Sorted Array
   * 
   * Question: Given a sorted array where every element appears exactly twice except one element that appears once, return the single element. The optimized solution must run in O(log n) time and O(1) space.
   * 
   * Constraints: 1 <= nums.length <= 100000; nums.length is odd; every value appears twice except one; nums is sorted.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Binary search uses pair parity to discard half the array.
   * 
   * Example 1:
   * Input: nums = [1,1,2,3,3,4,4,8,8]
   * Output: 2
   * Explanation: 2 is the only value that appears once.
   * 
   * Example 2:
   * Input: nums = [3,3,7,7,10,11,11]
   * Output: 10
   * Explanation: 10 is the unpaired element.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: 1
   * Explanation: The only element is single.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public int singleNonDuplicate(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
      int mid = left + (right - left) / 2;
      if (mid % 2 == 1) mid--;

      if (nums[mid] == nums[mid + 1]) {
        left = mid + 2;
      } else {
        right = mid;
      }
    }

    return nums[left];
  }

  /*
   * Question 12: Find Smallest Letter Greater Than Target
   * 
   * Question: Given a sorted array of lowercase letters and a target letter, return the smallest letter in the array that is strictly greater than target. The letters wrap around, so if no larger letter exists, return letters[0].
   * 
   * Constraints: 2 <= letters.length <= 10000; letters is sorted in non-decreasing order; letters contains lowercase English letters; target is lowercase.
   * 
   * Time and space complexity: Time O(log n); Space O(1). Upper-bound binary search finds the first greater letter.
   * 
   * Example 1:
   * Input: letters = ["c","f","j"], target = "a"
   * Output: "c"
   * Explanation: c is the smallest letter greater than a.
   * 
   * Example 2:
   * Input: letters = ["c","f","j"], target = "c"
   * Output: "f"
   * Explanation: The answer must be strictly greater than c.
   * 
   * Example 3:
   * Input: letters = ["x","x","y","y"], target = "z"
   * Output: "x"
   * Explanation: No letter is greater than z, so the answer wraps to x.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/binary-search.html
   */
  public char nextGreatestLetter(char[] letters, char target) {
    int left = 0;
    int right = letters.length;

    while (left < right) {
      int mid = left + (right - left) / 2;

      if (letters[mid] <= target) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return letters[left % letters.length];
  }
}
