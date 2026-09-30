package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 3: Sliding Window. Moderate: source questions 13-20. */
public class SlidingWindowModerate {

  /*
   * Question 13: Subarray Product Less Than K
   * 
   * Question: Given an array of positive integers nums and integer k, return the number of contiguous subarrays where the product of all elements is strictly less than k.
   * 
   * Constraints: 1 <= nums.length <= 3 * 10^4; 1 <= nums[i] <= 1000; 0 <= k <= 10^6.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [10,5,2,6], k = 100
   * Output: 8
   * Explanation: Eight subarrays have product less than 100.
   * 
   * Example 2:
   * Input: nums = [1,2,3], k = 0
   * Output: 0
   * Explanation: No positive product is less than 0.
   * 
   * Example 3:
   * Input: nums = [1,1,1], k = 2
   * Output: 6
   * Explanation: Every subarray product is 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int numSubarrayProductLessThanK(int[] nums, int k) {
    if (k <= 1) return 0;
    int left = 0;
    int count = 0;
    long product = 1;
    for (int right = 0; right < nums.length; right++) {
      product *= nums[right];
      while (product >= k) {
        product /= nums[left++];
      }
      count += right - left + 1;
    }
    return count;
  }

  /*
   * Question 14: Longest Subarray of 1s After Deleting One Element
   * 
   * Question: Given a binary array nums, delete exactly one element and return the longest non-empty subarray containing only 1s.
   * 
   * Constraints: 1 <= nums.length <= 10^5; nums[i] is 0 or 1.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,1,0,1]
   * Output: 3
   * Explanation: Delete the zero to join three ones.
   * 
   * Example 2:
   * Input: nums = [0,1,1,1,0,1,1,0,1]
   * Output: 5
   * Explanation: The best window has one zero and length 6, then delete it.
   * 
   * Example 3:
   * Input: nums = [1,1,1]
   * Output: 2
   * Explanation: One element must be deleted.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int longestSubarray(int[] nums) {
    int left = 0;
    int zeros = 0;
    int best = 0;
    for (int right = 0; right < nums.length; right++) {
      if (nums[right] == 0) zeros++;
      while (zeros > 1) {
        if (nums[left++] == 0) zeros--;
      }
      best = Math.max(best, right - left);
    }
    return best;
  }

  /*
   * Question 15: Number of Substrings Containing All Three Characters
   * 
   * Question: Given a string s containing only a, b, and c, return the number of substrings containing at least one occurrence of all three characters.
   * 
   * Constraints: 3 <= s.length <= 5 * 10^4; s consists only of a, b, and c.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: s = "abcabc"
   * Output: 10
   * Explanation: Every substring containing a, b, and c is counted.
   * 
   * Example 2:
   * Input: s = "aaacb"
   * Output: 3
   * Explanation: The valid substrings are aaacb, aacb, and acb.
   * 
   * Example 3:
   * Input: s = "abc"
   * Output: 1
   * Explanation: Only the full string works.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int numberOfSubstrings(String s) {
    int[] count = new int[3];
    int left = 0;
    int answer = 0;
    for (int right = 0; right < s.length(); right++) {
      count[s.charAt(right) - 'a']++;
      while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
        answer += s.length() - right;
        count[s.charAt(left++) - 'a']--;
      }
    }
    return answer;
  }

  /*
   * Question 16: Count Number of Nice Subarrays
   * 
   * Question: Given nums and k, return the number of continuous subarrays containing exactly k odd numbers.
   * 
   * Constraints: 1 <= nums.length <= 5 * 10^4; 1 <= nums[i] <= 10^5; 1 <= k <= nums.length.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,1,2,1,1], k = 3
   * Output: 2
   * Explanation: Two subarrays contain exactly three odd numbers.
   * 
   * Example 2:
   * Input: nums = [2,4,6], k = 1
   * Output: 0
   * Explanation: No odd numbers exist.
   * 
   * Example 3:
   * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
   * Output: 16
   * Explanation: Even runs around the two odds create sixteen subarrays.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int numberOfSubarrays(int[] nums, int k) {
    return atMostQ16(nums, k) - atMostQ16(nums, k - 1);
  }

  private int atMostQ16(int[] nums, int k) {
    int left = 0;
    int answer = 0;
    for (int right = 0; right < nums.length; right++) {
      if (nums[right] % 2 == 1) k--;
      while (k < 0) {
        if (nums[left++] % 2 == 1) k++;
      }
      answer += right - left + 1;
    }
    return answer;
  }

  /*
   * Question 17: Max Consecutive Ones
   * 
   * Question: Given a binary array nums, return the maximum number of consecutive 1s in the array.
   * 
   * Constraints: 1 <= nums.length <= 10^5; nums[i] is 0 or 1.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,1,0,1,1,1]
   * Output: 3
   * Explanation: The longest run of ones is at the end.
   * 
   * Example 2:
   * Input: nums = [1,0,1,1,0,1]
   * Output: 2
   * Explanation: The longest run length is 2.
   * 
   * Example 3:
   * Input: nums = [0,0]
   * Output: 0
   * Explanation: There are no ones.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int findMaxConsecutiveOnes(int[] nums) {
    int current = 0;
    int best = 0;
    for (int num : nums) {
      current = num == 1 ? current + 1 : 0;
      best = Math.max(best, current);
    }
    return best;
  }

  /*
   * Question 18: Maximum Points You Can Obtain from Cards
   * 
   * Question: Given cardPoints and k, take exactly k cards from either end of the row and return the maximum score.
   * 
   * Constraints: 1 <= cardPoints.length <= 10^5; 1 <= cardPoints[i] <= 10^4; 1 <= k <= cardPoints.length.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
   * Output: 12
   * Explanation: Leave [1,2,3,4], take [5,6,1].
   * 
   * Example 2:
   * Input: cardPoints = [2,2,2], k = 2
   * Output: 4
   * Explanation: Any two cards score 4.
   * 
   * Example 3:
   * Input: cardPoints = [9,7,7,9,7,7,9], k = 7
   * Output: 55
   * Explanation: Take all cards.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int maxScore(int[] cardPoints, int k) {
    int total = 0;
    for (int point : cardPoints) total += point;

    int keep = cardPoints.length - k;
    if (keep == 0) return total;

    int window = 0;
    for (int i = 0; i < keep; i++) window += cardPoints[i];
    int minWindow = window;
    for (int right = keep; right < cardPoints.length; right++) {
      window += cardPoints[right] - cardPoints[right - keep];
      minWindow = Math.min(minWindow, window);
    }
    return total - minWindow;
  }

  /*
   * Question 19: Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
   * 
   * Question: Given nums and limit, return the length of the longest continuous subarray where the absolute difference between any two elements is at most limit.
   * 
   * Constraints: 1 <= nums.length <= 10^5; 1 <= nums[i] <= 10^9; 0 <= limit <= 10^9.
   * 
   * Time and space complexity: Time O(n), Space O(n) for deques.
   * 
   * Example 1:
   * Input: nums = [8,2,4,7], limit = 4
   * Output: 2
   * Explanation: [2,4] is valid, length 2.
   * 
   * Example 2:
   * Input: nums = [10,1,2,4,7,2], limit = 5
   * Output: 4
   * Explanation: [2,4,7,2] is valid.
   * 
   * Example 3:
   * Input: nums = [4,2,2,2,4,4,2,2], limit = 0
   * Output: 3
   * Explanation: The longest equal-value run has length 3.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int longestSubarray(int[] nums, int limit) {
    Deque<Integer> maxDeque = new ArrayDeque<>();
    Deque<Integer> minDeque = new ArrayDeque<>();
    int left = 0;
    int best = 0;

    for (int right = 0; right < nums.length; right++) {
      while (!maxDeque.isEmpty() && nums[maxDeque.peekLast()] < nums[right]) maxDeque.pollLast();
      while (!minDeque.isEmpty() && nums[minDeque.peekLast()] > nums[right]) minDeque.pollLast();
      maxDeque.offerLast(right);
      minDeque.offerLast(right);

      while (nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()] > limit) {
        if (maxDeque.peekFirst() == left) maxDeque.pollFirst();
        if (minDeque.peekFirst() == left) minDeque.pollFirst();
        left++;
      }
      best = Math.max(best, right - left + 1);
    }
    return best;
  }

  /*
   * Question 20: Minimum Operations to Reduce X to Zero
   * 
   * Question: Given nums and x, remove elements only from the left or right end so the removed sum is exactly x. Return the minimum number of removals, or -1 if impossible.
   * 
   * Constraints: 1 <= nums.length <= 10^5; 1 <= nums[i] <= 10^4; 1 <= x <= 10^9.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,1,4,2,3], x = 5
   * Output: 2
   * Explanation: Keep [1,1,4] with sum 6, so remove 2 and 3.
   * 
   * Example 2:
   * Input: nums = [5,6,7,8,9], x = 4
   * Output: -1
   * Explanation: No removed-end sum can equal 4.
   * 
   * Example 3:
   * Input: nums = [3,2,20,1,1,3], x = 10
   * Output: 5
   * Explanation: Keep [20], so remove five elements.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int minOperations(int[] nums, int x) {
    int total = 0;
    for (int num : nums) total += num;
    int target = total - x;
    if (target < 0) return -1;

    int left = 0;
    int sum = 0;
    int longest = -1;
    for (int right = 0; right < nums.length; right++) {
      sum += nums[right];
      while (sum > target && left <= right) {
        sum -= nums[left++];
      }
      if (sum == target) longest = Math.max(longest, right - left + 1);
    }
    return longest == -1 ? -1 : nums.length - longest;
  }
}
