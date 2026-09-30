package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 3: Sliding Window. Basic: source questions 1-12. */
public class SlidingWindowBasic {

  /*
   * Question 1: Maximum Average Subarray I
   * 
   * Question: Given an integer array nums and an integer k, return the maximum average value among all contiguous subarrays of length k.
   * 
   * Constraints: 1 <= k <= nums.length <= 10^5; -10^4 <= nums[i] <= 10^4.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,12,-5,-6,50,3], k = 4
   * Output: 12.75000
   * Explanation: The best length-4 window is [12,-5,-6,50] with sum 51.
   * 
   * Example 2:
   * Input: nums = [5], k = 1
   * Output: 5.00000
   * Explanation: The only element is the only window.
   * 
   * Example 3:
   * Input: nums = [-1,-2,-3], k = 2
   * Output: -1.50000
   * Explanation: The maximum sum window is [-1,-2].
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public double findMaxAverage(int[] nums, int k) {
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
      windowSum += nums[i];
    }

    int bestSum = windowSum;
    for (int right = k; right < nums.length; right++) {
      windowSum += nums[right] - nums[right - k];
      bestSum = Math.max(bestSum, windowSum);
    }

    return (double) bestSum / k;
  }

  /*
   * Question 2: Contains Duplicate II
   * 
   * Question: Given nums and k, return true if there are two distinct indices i and j such that nums[i] == nums[j] and abs(i - j) <= k.
   * 
   * Constraints: 1 <= nums.length <= 10^5; -10^9 <= nums[i] <= 10^9; 0 <= k <= 10^5.
   * 
   * Time and space complexity: Time O(n), Space O(min(n, k)).
   * 
   * Example 1:
   * Input: nums = [1,2,3,1], k = 3
   * Output: true
   * Explanation: The two 1 values are distance 3 apart.
   * 
   * Example 2:
   * Input: nums = [1,0,1,1], k = 1
   * Output: true
   * Explanation: The last two 1 values are distance 1 apart.
   * 
   * Example 3:
   * Input: nums = [1,2,3,1,2,3], k = 2
   * Output: false
   * Explanation: Every duplicate pair is farther than 2.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> window = new HashSet<>();

    for (int i = 0; i < nums.length; i++) {
      if (i > k) {
        window.remove(nums[i - k - 1]);
      }
      if (!window.add(nums[i])) {
        return true;
      }
    }

    return false;
  }

  /*
   * Question 3: Longest Substring Without Repeating Characters
   * 
   * Question: Given a string s, return the length of the longest substring without repeating characters.
   * 
   * Constraints: 0 <= s.length <= 5 * 10^4; s may contain English letters, digits, symbols, and spaces.
   * 
   * Time and space complexity: Time O(n), Space O(min(n, charset)).
   * 
   * Example 1:
   * Input: s = "abcabcbb"
   * Output: 3
   * Explanation: The longest substring without repeats is "abc".
   * 
   * Example 2:
   * Input: s = "bbbbb"
   * Output: 1
   * Explanation: The longest substring is "b".
   * 
   * Example 3:
   * Input: s = "pwwkew"
   * Output: 3
   * Explanation: The answer is "wke"; subsequences do not count.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> lastSeen = new HashMap<>();
    int left = 0;
    int best = 0;

    for (int right = 0; right < s.length(); right++) {
      char current = s.charAt(right);
      if (lastSeen.containsKey(current)) {
        left = Math.max(left, lastSeen.get(current) + 1);
      }
      lastSeen.put(current, right);
      best = Math.max(best, right - left + 1);
    }

    return best;
  }

  /*
   * Question 4: Minimum Size Subarray Sum
   * 
   * Question: Given an array of positive integers nums and a positive integer target, return the minimum length of a contiguous subarray whose sum is at least target. Return 0 if no such subarray exists.
   * 
   * Constraints: 1 <= target <= 10^9; 1 <= nums.length <= 10^5; 1 <= nums[i] <= 10^4.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: target = 7, nums = [2,3,1,2,4,3]
   * Output: 2
   * Explanation: The shortest valid subarray is [4,3].
   * 
   * Example 2:
   * Input: target = 4, nums = [1,4,4]
   * Output: 1
   * Explanation: A single 4 is enough.
   * 
   * Example 3:
   * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
   * Output: 0
   * Explanation: No subarray reaches 11.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int minSubArrayLen(int target, int[] nums) {
    int left = 0;
    int sum = 0;
    int best = Integer.MAX_VALUE;

    for (int right = 0; right < nums.length; right++) {
      sum += nums[right];
      while (sum >= target) {
        best = Math.min(best, right - left + 1);
        sum -= nums[left++];
      }
    }

    return best == Integer.MAX_VALUE ? 0 : best;
  }

  /*
   * Question 5: Permutation in String
   * 
   * Question: Given strings s1 and s2, return true if s2 contains a permutation of s1 as a contiguous substring.
   * 
   * Constraints: 1 <= s1.length, s2.length <= 10^4; s1 and s2 consist of lowercase English letters.
   * 
   * Time and space complexity: Time O(n), Space O(1), using fixed 26-character count arrays.
   * 
   * Example 1:
   * Input: s1 = "ab", s2 = "eidbaooo"
   * Output: true
   * Explanation: The substring "ba" is a permutation of "ab".
   * 
   * Example 2:
   * Input: s1 = "ab", s2 = "eidboaoo"
   * Output: false
   * Explanation: No substring has both a and b together.
   * 
   * Example 3:
   * Input: s1 = "adc", s2 = "dcda"
   * Output: true
   * Explanation: The substring "dca" is a permutation of "adc".
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) {
      return false;
    }

    int[] need = new int[26];
    int[] window = new int[26];
    for (char c : s1.toCharArray()) {
      need[c - 'a']++;
    }

    for (int right = 0; right < s2.length(); right++) {
      window[s2.charAt(right) - 'a']++;
      if (right >= s1.length()) {
        window[s2.charAt(right - s1.length()) - 'a']--;
      }
      if (Arrays.equals(need, window)) {
        return true;
      }
    }

    return false;
  }

  /*
   * Question 6: Find All Anagrams in a String
   * 
   * Question: Given strings s and p, return all start indices of substrings in s that are anagrams of p. Return the answer in any order.
   * 
   * Constraints: 1 <= s.length, p.length <= 3 * 10^4; s and p consist of lowercase English letters.
   * 
   * Time and space complexity: Time O(n), Space O(1), using fixed 26-character count arrays.
   * 
   * Example 1:
   * Input: s = "cbaebabacd", p = "abc"
   * Output: [0,6]
   * Explanation: The substrings "cba" and "bac" are anagrams of "abc".
   * 
   * Example 2:
   * Input: s = "abab", p = "ab"
   * Output: [0,1,2]
   * Explanation: The substrings "ab", "ba", and "ab" all qualify.
   * 
   * Example 3:
   * Input: s = "baa", p = "aa"
   * Output: [1]
   * Explanation: The substring "aa" starts at index 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public List<Integer> findAnagrams(String s, String p) {
    List<Integer> answer = new ArrayList<>();
    if (p.length() > s.length()) {
      return answer;
    }

    int[] need = new int[26];
    int[] window = new int[26];
    for (char c : p.toCharArray()) {
      need[c - 'a']++;
    }

    for (int right = 0; right < s.length(); right++) {
      window[s.charAt(right) - 'a']++;
      if (right >= p.length()) {
        window[s.charAt(right - p.length()) - 'a']--;
      }
      if (Arrays.equals(need, window)) {
        answer.add(right - p.length() + 1);
      }
    }

    return answer;
  }

  /*
   * Question 7: Fruit Into Baskets
   * 
   * Question: Given an integer array fruits where fruits[i] is the type of fruit at tree i, return the length of the longest contiguous subarray containing at most two distinct fruit types.
   * 
   * Constraints: 1 <= fruits.length <= 10^5; 0 <= fruits[i] < fruits.length.
   * 
   * Time and space complexity: Time O(n), Space O(1) because the map stores at most three types temporarily.
   * 
   * Example 1:
   * Input: fruits = [1,2,1]
   * Output: 3
   * Explanation: The entire array has only two fruit types.
   * 
   * Example 2:
   * Input: fruits = [0,1,2,2]
   * Output: 3
   * Explanation: The best window is [1,2,2].
   * 
   * Example 3:
   * Input: fruits = [1,2,3,2,2]
   * Output: 4
   * Explanation: The best window is [2,3,2,2].
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int totalFruit(int[] fruits) {
    Map<Integer, Integer> count = new HashMap<>();
    int left = 0;
    int best = 0;

    for (int right = 0; right < fruits.length; right++) {
      count.put(fruits[right], count.getOrDefault(fruits[right], 0) + 1);

      while (count.size() > 2) {
        int fruit = fruits[left++];
        count.put(fruit, count.get(fruit) - 1);
        if (count.get(fruit) == 0) {
          count.remove(fruit);
        }
      }

      best = Math.max(best, right - left + 1);
    }

    return best;
  }

  /*
   * Question 8: Max Consecutive Ones III
   * 
   * Question: Given a binary array nums and an integer k, return the maximum number of consecutive 1s after flipping at most k zeroes.
   * 
   * Constraints: 1 <= nums.length <= 10^5; nums[i] is 0 or 1; 0 <= k <= nums.length.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
   * Output: 6
   * Explanation: Flip two zeroes to get six consecutive ones.
   * 
   * Example 2:
   * Input: nums = [0,0,1,1,0,0,1,1,1,0], k = 3
   * Output: 6
   * Explanation: The best valid window has length 6.
   * 
   * Example 3:
   * Input: nums = [1,1,1], k = 0
   * Output: 3
   * Explanation: No flips are needed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int longestOnes(int[] nums, int k) {
    int left = 0;
    int zeroes = 0;
    int best = 0;

    for (int right = 0; right < nums.length; right++) {
      if (nums[right] == 0) {
        zeroes++;
      }
      while (zeroes > k) {
        if (nums[left++] == 0) {
          zeroes--;
        }
      }
      best = Math.max(best, right - left + 1);
    }

    return best;
  }

  /*
   * Question 9: Longest Repeating Character Replacement
   * 
   * Question: Given a string s and integer k, return the length of the longest substring that can be turned into all the same character by replacing at most k characters.
   * 
   * Constraints: 1 <= s.length <= 10^5; s consists of uppercase English letters; 0 <= k <= s.length.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: s = "ABAB", k = 2
   * Output: 4
   * Explanation: Replace two characters to make the whole string equal.
   * 
   * Example 2:
   * Input: s = "AABABBA", k = 1
   * Output: 4
   * Explanation: A window like "AABA" can become all A.
   * 
   * Example 3:
   * Input: s = "AAAA", k = 0
   * Output: 4
   * Explanation: Already all the same character.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int left = 0;
    int maxFrequency = 0;
    int best = 0;

    for (int right = 0; right < s.length(); right++) {
      int index = s.charAt(right) - 'A';
      count[index]++;
      maxFrequency = Math.max(maxFrequency, count[index]);

      while (right - left + 1 - maxFrequency > k) {
        count[s.charAt(left) - 'A']--;
        left++;
      }

      best = Math.max(best, right - left + 1);
    }

    return best;
  }

  /*
   * Question 10: Grumpy Bookstore Owner
   * 
   * Question: Given customers, grumpy, and minutes, return the maximum number of satisfied customers if the owner can suppress grumpiness for exactly one consecutive window of length minutes.
   * 
   * Constraints: 1 <= minutes <= customers.length <= 2 * 10^4; 0 <= customers[i] <= 1000; grumpy[i] is 0 or 1.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], minutes = 3
   * Output: 16
   * Explanation: Base satisfied plus best extra gain window gives 16.
   * 
   * Example 2:
   * Input: customers = [1], grumpy = [0], minutes = 1
   * Output: 1
   * Explanation: The only customer is already satisfied.
   * 
   * Example 3:
   * Input: customers = [4,10,10], grumpy = [1,1,0], minutes = 2
   * Output: 24
   * Explanation: Suppressing first two minutes satisfies all customers.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
    int baseSatisfied = 0;
    int windowGain = 0;

    for (int i = 0; i < customers.length; i++) {
      if (grumpy[i] == 0) {
        baseSatisfied += customers[i];
      } else if (i < minutes) {
        windowGain += customers[i];
      }
    }

    int bestGain = windowGain;
    for (int right = minutes; right < customers.length; right++) {
      if (grumpy[right] == 1) {
        windowGain += customers[right];
      }
      if (grumpy[right - minutes] == 1) {
        windowGain -= customers[right - minutes];
      }
      bestGain = Math.max(bestGain, windowGain);
    }

    return baseSatisfied + bestGain;
  }

  /*
   * Question 11: Sliding Window Maximum
   * 
   * Question: Given nums and k, return an array containing the maximum value in every contiguous window of size k.
   * 
   * Constraints: 1 <= nums.length <= 10^5; -10^4 <= nums[i] <= 10^4; 1 <= k <= nums.length.
   * 
   * Time and space complexity: Time O(n), Space O(k) for the deque.
   * 
   * Example 1:
   * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
   * Output: [3,3,5,5,6,7]
   * Explanation: Maximum for each length-3 window.
   * 
   * Example 2:
   * Input: nums = [1], k = 1
   * Output: [1]
   * Explanation: Only one window exists.
   * 
   * Example 3:
   * Input: nums = [9,8,7], k = 2
   * Output: [9,8]
   * Explanation: Decreasing windows keep the leftmost value as max.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public int[] maxSlidingWindow(int[] nums, int k) {
    Deque<Integer> deque = new ArrayDeque<>();
    int[] answer = new int[nums.length - k + 1];

    for (int right = 0; right < nums.length; right++) {
      while (!deque.isEmpty() && deque.peekFirst() <= right - k) {
        deque.pollFirst();
      }
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
        deque.pollLast();
      }
      deque.offerLast(right);

      if (right >= k - 1) {
        answer[right - k + 1] = nums[deque.peekFirst()];
      }
    }

    return answer;
  }

  /*
   * Question 12: Minimum Window Substring
   * 
   * Question: Given strings s and t, return the smallest substring of s that contains every character of t including duplicates. Return an empty string if no such window exists.
   * 
   * Constraints: 1 <= s.length, t.length <= 10^5; s and t consist of uppercase and lowercase English letters.
   * 
   * Time and space complexity: Time O(n + m), Space O(charset), where n = s.length and m = t.length.
   * 
   * Example 1:
   * Input: s = "ADOBECODEBANC", t = "ABC"
   * Output: "BANC"
   * Explanation: BANC is the shortest substring containing A, B, and C.
   * 
   * Example 2:
   * Input: s = "a", t = "a"
   * Output: "a"
   * Explanation: The full string is the minimum window.
   * 
   * Example 3:
   * Input: s = "a", t = "aa"
   * Output: ""
   * Explanation: s does not contain two a characters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sliding-window.html
   */
  public String minWindow(String s, String t) {
    int[] need = new int[128];
    for (char c : t.toCharArray()) {
      need[c]++;
    }

    int missing = t.length();
    int left = 0;
    int start = 0;
    int best = Integer.MAX_VALUE;

    for (int right = 0; right < s.length(); right++) {
      if (need[s.charAt(right)]-- > 0) {
        missing--;
      }

      while (missing == 0) {
        if (right - left + 1 < best) {
          best = right - left + 1;
          start = left;
        }
        if (++need[s.charAt(left++)] > 0) {
          missing++;
        }
      }
    }

    return best == Integer.MAX_VALUE ? "" : s.substring(start, start + best);
  }
}
