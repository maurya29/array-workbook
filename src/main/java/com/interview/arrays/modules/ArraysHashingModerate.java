package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 1: Arrays & Hashing. Moderate: source questions 13-20. */
public class ArraysHashingModerate {

  /*
   * Question 13: Encode and Decode Strings
   * 
   * Question: Design an algorithm to encode a list of strings into one string and decode it back to the original list. Strings may contain any valid characters.
   * 
   * Constraints: Design problem; encode and decode must be inverse operations for arbitrary strings.
   * 
   * Time and space complexity: Time O(totalChars), Space O(totalChars), using length-prefix encoding.
   * 
   * Example 1:
   * Input: strs = ["lint","code","love","you"]
   * Output: ["lint","code","love","you"]
   * Explanation: Decoding restores the original list.
   * 
   * Example 2:
   * Input: strs = [""]
   * Output: [""]
   * Explanation: An empty string is encoded as length 0.
   * 
   * Example 3:
   * Input: strs = ["a#b","12"]
   * Output: ["a#b","12"]
   * Explanation: The delimiter is safe because length controls parsing.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public String encode(List<String> strs) {
    StringBuilder out = new StringBuilder();
    for (String s : strs) out.append(s.length()).append('#').append(s);
    return out.toString();
  }
  public List<String> decode(String s) {
    List<String> ans = new ArrayList<>(); int i = 0;
    while (i < s.length()) {
      int j = i; while (s.charAt(j) != '#') j++;
      int len = Integer.parseInt(s.substring(i, j));
      ans.add(s.substring(j + 1, j + 1 + len));
      i = j + 1 + len;
    }
    return ans;
  }

  /*
   * Question 14: Longest Substring Without Repeating Characters
   * 
   * Question: Given a string s, return the length of the longest substring without repeating characters.
   * 
   * Constraints: 0 <= s.length <= 5 * 10^4; s may contain letters, digits, symbols, and spaces.
   * 
   * Time and space complexity: Time O(n), Space O(min(n, charset)).
   * 
   * Example 1:
   * Input: s = "abcabcbb"
   * Output: 3
   * Explanation: The answer is "abc".
   * 
   * Example 2:
   * Input: s = "bbbbb"
   * Output: 1
   * Explanation: The answer is "b".
   * 
   * Example 3:
   * Input: s = "pwwkew"
   * Output: 3
   * Explanation: The answer is "wke".
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> last = new HashMap<>(); int left = 0, best = 0;
    for (int right = 0; right < s.length(); right++) {
      char c = s.charAt(right);
      if (last.containsKey(c)) left = Math.max(left, last.get(c) + 1);
      last.put(c, right); best = Math.max(best, right - left + 1);
    }
    return best;
  }

  /*
   * Question 15: Minimum Window Substring
   * 
   * Question: Given strings s and t, return the minimum window substring of s that contains every character in t including duplicates, or an empty string if none exists.
   * 
   * Constraints: 1 <= s.length, t.length <= 10^5; s and t contain uppercase and lowercase English letters.
   * 
   * Time and space complexity: Time O(n + m), Space O(charset).
   * 
   * Example 1:
   * Input: s = "ADOBECODEBANC", t = "ABC"
   * Output: "BANC"
   * Explanation: BANC is the shortest valid window.
   * 
   * Example 2:
   * Input: s = "a", t = "a"
   * Output: "a"
   * Explanation: The full string is valid.
   * 
   * Example 3:
   * Input: s = "a", t = "aa"
   * Output: ""
   * Explanation: Two a characters are required.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public String minWindow(String s, String t) {
    int[] need = new int[128]; for (char c : t.toCharArray()) need[c]++;
    int missing = t.length(), left = 0, start = 0, best = Integer.MAX_VALUE;
    for (int right = 0; right < s.length(); right++) {
      if (need[s.charAt(right)]-- > 0) missing--;
      while (missing == 0) {
        if (right - left + 1 < best) { best = right - left + 1; start = left; }
        if (++need[s.charAt(left++)] > 0) missing++;
      }
    }
    return best == Integer.MAX_VALUE ? "" : s.substring(start, start + best);
  }

  /*
   * Question 16: Find All Anagrams in a String
   * 
   * Question: Given strings s and p, return all start indices of p's anagrams in s.
   * 
   * Constraints: 1 <= s.length, p.length <= 3 * 10^4; s and p consist of lowercase English letters.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: s = "cbaebabacd", p = "abc"
   * Output: [0,6]
   * Explanation: cba and bac are anagrams.
   * 
   * Example 2:
   * Input: s = "abab", p = "ab"
   * Output: [0,1,2]
   * Explanation: ab, ba, and ab are valid.
   * 
   * Example 3:
   * Input: s = "baa", p = "aa"
   * Output: [1]
   * Explanation: aa starts at index 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public List<Integer> findAnagrams(String s, String p) {
    List<Integer> ans = new ArrayList<>();
    if (p.length() > s.length()) return ans;
    int[] need = new int[26], window = new int[26];
    for (char c : p.toCharArray()) need[c - 'a']++;
    for (int right = 0; right < s.length(); right++) {
      window[s.charAt(right) - 'a']++;
      if (right >= p.length()) window[s.charAt(right - p.length()) - 'a']--;
      if (Arrays.equals(need, window)) ans.add(right - p.length() + 1);
    }
    return ans;
  }

  /*
   * Question 17: Contiguous Array
   * 
   * Question: Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
   * 
   * Constraints: 1 <= nums.length <= 10^5; nums[i] is 0 or 1.
   * 
   * Time and space complexity: Time O(n), Space O(n).
   * 
   * Example 1:
   * Input: nums = [0,1]
   * Output: 2
   * Explanation: The whole array is balanced.
   * 
   * Example 2:
   * Input: nums = [0,1,0]
   * Output: 2
   * Explanation: [0,1] or [1,0] is balanced.
   * 
   * Example 3:
   * Input: nums = [0,0,1,0,0,0,1,1]
   * Output: 6
   * Explanation: A longest balanced subarray has length 6.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int findMaxLength(int[] nums) {
    Map<Integer, Integer> first = new HashMap<>();
    first.put(0, -1);
    int balance = 0, best = 0;
    for (int i = 0; i < nums.length; i++) {
      balance += nums[i] == 1 ? 1 : -1;
      if (first.containsKey(balance)) best = Math.max(best, i - first.get(balance));
      else first.put(balance, i);
    }
    return best;
  }

  /*
   * Question 18: Longest Harmonious Subsequence
   * 
   * Question: Given an integer array nums, return the length of the longest harmonious subsequence where max and min differ by exactly 1.
   * 
   * Constraints: 1 <= nums.length <= 2 * 10^4; -10^9 <= nums[i] <= 10^9.
   * 
   * Time and space complexity: Time O(n), Space O(n).
   * 
   * Example 1:
   * Input: nums = [1,3,2,2,5,2,3,7]
   * Output: 5
   * Explanation: [3,2,2,2,3] is harmonious.
   * 
   * Example 2:
   * Input: nums = [1,2,3,4]
   * Output: 2
   * Explanation: Any adjacent pair gives length 2.
   * 
   * Example 3:
   * Input: nums = [1,1,1,1]
   * Output: 0
   * Explanation: Difference is never exactly 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int findLHS(int[] nums) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.put(n, freq.getOrDefault(n, 0) + 1);
    int best = 0;
    for (int n : freq.keySet()) if (freq.containsKey(n + 1)) best = Math.max(best, freq.get(n) + freq.get(n + 1));
    return best;
  }

  /*
   * Question 19: Subarrays Divisible by K
   * 
   * Question: Given an integer array nums and an integer k, return the number of non-empty contiguous subarrays whose sum is divisible by k.
   * 
   * Constraints: 1 <= nums.length <= 3 * 10^4; -10^4 <= nums[i] <= 10^4; 2 <= k <= 10^4.
   * 
   * Time and space complexity: Time O(n), Space O(k).
   * 
   * Example 1:
   * Input: nums = [4,5,0,-2,-3,1], k = 5
   * Output: 7
   * Explanation: There are 7 valid subarrays.
   * 
   * Example 2:
   * Input: nums = [5], k = 9
   * Output: 0
   * Explanation: 5 is not divisible by 9.
   * 
   * Example 3:
   * Input: nums = [-1,2,9], k = 2
   * Output: 2
   * Explanation: Normalized remainders handle negative prefixes.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int subarraysDivByK(int[] nums, int k) {
    int[] freq = new int[k];
    freq[0] = 1;
    int prefix = 0, count = 0;
    for (int num : nums) {
      prefix = ((prefix + num) % k + k) % k;
      count += freq[prefix];
      freq[prefix]++;
    }
    return count;
  }

  /*
   * Question 20: Insert Delete GetRandom O(1)
   * 
   * Question: Design a randomized set supporting insert, remove, and getRandom in average O(1) time.
   * 
   * Constraints: -2^31 <= val <= 2^31 - 1; at most 2 * 10^5 calls; getRandom called only when non-empty.
   * 
   * Time and space complexity: Average O(1) for insert, remove, getRandom; Space O(n).
   * 
   * Example 1:
   * Input: insert(1), remove(2), insert(2), getRandom(), remove(1), insert(2)
   * Output: true, false, true, 1 or 2, true, false
   * Explanation: Operations maintain the set while getRandom returns an existing value.
   * 
   * Example 2:
   * Input: insert(1), insert(1)
   * Output: true, false
   * Explanation: Duplicate insert returns false.
   * 
   * Example 3:
   * Input: insert(5), remove(5)
   * Output: true, true
   * Explanation: A single existing value can be removed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public static class RandomizedSet {
    private final Map<Integer, Integer> index = new HashMap<>();
    private final List<Integer> values = new ArrayList<>();
    private final Random random = new Random();
    public boolean insert(int val) {
      if (index.containsKey(val)) return false;
      index.put(val, values.size()); values.add(val); return true;
    }
    public boolean remove(int val) {
      Integer i = index.get(val); if (i == null) return false;
      int last = values.get(values.size() - 1);
      values.set(i, last); index.put(last, i);
      values.remove(values.size() - 1); index.remove(val); return true;
    }
    public int getRandom() { return values.get(random.nextInt(values.size())); }
  }
}
