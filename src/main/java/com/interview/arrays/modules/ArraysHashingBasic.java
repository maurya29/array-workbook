package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 1: Arrays & Hashing. Basic: source questions 1-12. */
public class ArraysHashingBasic {

  /*
   * Question 1: Two Sum
   * 
   * Question: Given an integer array nums and an integer target, return the indexes of two distinct elements whose values add up to target. If no pair exists, return {-1, -1}.
   * 
   * Constraints: 2 <= nums.length <= 10^4; -10^9 <= nums[i] <= 10^9; -10^9 <= target <= 10^9; indexes must be distinct.
   * 
   * Time and space complexity: Time O(n), Space O(n).
   * 
   * Example 1:
   * Input: nums = [2,7,11,15], target = 9
   * Output: [0,1]
   * Explanation: nums[0] + nums[1] = 2 + 7 = 9.
   * 
   * Example 2:
   * Input: nums = [3,2,4], target = 6
   * Output: [1,2]
   * Explanation: nums[1] + nums[2] = 2 + 4 = 6.
   * 
   * Example 3:
   * Input: nums = [3,3], target = 6
   * Output: [0,1]
   * Explanation: The same value can be used twice only when it appears at two different indexes.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> indexByValue = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
      int complement = target - nums[i];
      if (indexByValue.containsKey(complement)) {
        return new int[] {indexByValue.get(complement), i};
      }
      indexByValue.put(nums[i], i);
    }

    return new int[] {-1, -1};
  }

  /*
   * Question 2: Contains Duplicate
   * 
   * Question: Given an integer array nums, return true if any value appears at least twice. Return false if every element is distinct.
   * 
   * Constraints: 1 <= nums.length <= 10^5; -10^9 <= nums[i] <= 10^9.
   * 
   * Time and space complexity: Time O(n), Space O(n).
   * 
   * Example 1:
   * Input: nums = [1,2,3,1]
   * Output: true
   * Explanation: The value 1 appears at indexes 0 and 3.
   * 
   * Example 2:
   * Input: nums = [1,2,3,4]
   * Output: false
   * Explanation: Every value appears exactly once.
   * 
   * Example 3:
   * Input: nums = [1,1,1,3,3,4,3,2,4,2]
   * Output: true
   * Explanation: Multiple values appear more than once.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
      if (seen.contains(num)) {
        return true;
      }
      seen.add(num);
    }

    return false;
  }

  /*
   * Question 3: Valid Anagram
   * 
   * Question: Given two strings s and t, return true if t is an anagram of s. Return false otherwise. Both strings contain lowercase English letters.
   * 
   * Constraints: 1 <= s.length, t.length <= 5 * 10^4; s and t consist of lowercase English letters.
   * 
   * Time and space complexity: Time O(n), Space O(1) because the frequency array has fixed size 26.
   * 
   * Example 1:
   * Input: s = "anagram", t = "nagaram"
   * Output: true
   * Explanation: Both strings contain the same letters with the same frequencies.
   * 
   * Example 2:
   * Input: s = "rat", t = "car"
   * Output: false
   * Explanation: The character counts are different.
   * 
   * Example 3:
   * Input: s = "aacc", t = "ccac"
   * Output: false
   * Explanation: s has two a characters, but t has only one.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }

    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
      count[t.charAt(i) - 'a']--;
    }

    for (int value : count) {
      if (value != 0) {
        return false;
      }
    }
    return true;
  }

  /*
   * Question 4: Group Anagrams
   * 
   * Question: Given an array of strings strs, group the anagrams together. You may return the groups in any order.
   * 
   * Constraints: 1 <= strs.length <= 10^4; 0 <= strs[i].length <= 100; strs[i] consists of lowercase English letters.
   * 
   * Time and space complexity: Time O(n * k), Space O(n * k), because each word builds a 26-count key and is stored in a hash map group.
   * 
   * Example 1:
   * Input: strs = ["eat","tea","tan","ate","nat","bat"]
   * Output: [["bat"],["tan","nat"],["eat","tea","ate"]]
   * Explanation: eat, tea, and ate share one signature; tan and nat share another; bat is alone.
   * 
   * Example 2:
   * Input: strs = [""]
   * Output: [[""]]
   * Explanation: The empty string forms one valid group.
   * 
   * Example 3:
   * Input: strs = ["a"]
   * Output: [["a"]]
   * Explanation: A single word is grouped by itself.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();

    for (String word : strs) {
      String key = buildKeyQ4(word);
      groups.computeIfAbsent(key, unused -> new ArrayList<>()).add(word);
    }

    return new ArrayList<>(groups.values());
  }

  private String buildKeyQ4(String word) {
    int[] count = new int[26];
    for (char c : word.toCharArray()) {
      count[c - 'a']++;
    }
    return Arrays.toString(count);
  }

  /*
   * Question 5: Top K Frequent Elements
   * 
   * Question: Given an integer array nums and an integer k, return the k most frequent elements. The answer may be returned in any order.
   * 
   * Constraints: 1 <= nums.length <= 10^5; -10^4 <= nums[i] <= 10^4; k is in [1, number of unique elements]; answer is guaranteed unique as a set.
   * 
   * Time and space complexity: Time O(n), Space O(n), using frequency buckets from 1 to n.
   * 
   * Example 1:
   * Input: nums = [1,1,1,2,2,3], k = 2
   * Output: [1,2]
   * Explanation: 1 appears 3 times and 2 appears 2 times.
   * 
   * Example 2:
   * Input: nums = [1], k = 1
   * Output: [1]
   * Explanation: The only element is also the most frequent.
   * 
   * Example 3:
   * Input: nums = [4,4,4,6,6,7], k = 1
   * Output: [4]
   * Explanation: 4 has the highest frequency.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) {
      freq.put(num, freq.getOrDefault(num, 0) + 1);
    }

    List<Integer>[] buckets = new ArrayList[nums.length + 1];
    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
      int count = entry.getValue();
      if (buckets[count] == null) {
        buckets[count] = new ArrayList<>();
      }
      buckets[count].add(entry.getKey());
    }

    int[] answer = new int[k];
    int index = 0;
    for (int count = buckets.length - 1; count >= 0 && index < k; count--) {
      if (buckets[count] == null) {
        continue;
      }
      for (int value : buckets[count]) {
        answer[index++] = value;
        if (index == k) {
          return answer;
        }
      }
    }
    return answer;
  }

  /*
   * Question 6: Product of Array Except Self
   * 
   * Question: Given an integer array nums, return an array answer where answer[i] equals the product of all elements of nums except nums[i]. Solve it in O(n) time without using division.
   * 
   * Constraints: 2 <= nums.length <= 10^5; -30 <= nums[i] <= 30; products fit in 32-bit integer; division is not allowed.
   * 
   * Time and space complexity: Time O(n), Space O(1) excluding the output array.
   * 
   * Example 1:
   * Input: nums = [1,2,3,4]
   * Output: [24,12,8,6]
   * Explanation: For index 0, product is 2 * 3 * 4 = 24; for index 1, product is 1 * 3 * 4 = 12.
   * 
   * Example 2:
   * Input: nums = [-1,1,0,-3,3]
   * Output: [0,0,9,0,0]
   * Explanation: Only the index containing 0 gets the product of all non-zero values.
   * 
   * Example 3:
   * Input: nums = [2,3]
   * Output: [3,2]
   * Explanation: Each answer is the other element.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] answer = new int[n];

    int leftProduct = 1;
    for (int i = 0; i < n; i++) {
      answer[i] = leftProduct;
      leftProduct *= nums[i];
    }

    int rightProduct = 1;
    for (int i = n - 1; i >= 0; i--) {
      answer[i] *= rightProduct;
      rightProduct *= nums[i];
    }

    return answer;
  }

  /*
   * Question 7: Subarray Sum Equals K
   * 
   * Question: Given an integer array nums and an integer k, return the total number of contiguous non-empty subarrays whose sum equals k.
   * 
   * Constraints: 1 <= nums.length <= 2 * 10^4; -1000 <= nums[i] <= 1000; -10^7 <= k <= 10^7.
   * 
   * Time and space complexity: Time O(n), Space O(n), using a hash map of prefix-sum frequencies.
   * 
   * Example 1:
   * Input: nums = [1,1,1], k = 2
   * Output: 2
   * Explanation: The valid subarrays are nums[0..1] and nums[1..2].
   * 
   * Example 2:
   * Input: nums = [1,2,3], k = 3
   * Output: 2
   * Explanation: The valid subarrays are [1,2] and [3].
   * 
   * Example 3:
   * Input: nums = [-1,-1,1], k = 0
   * Output: 1
   * Explanation: The valid subarray is nums[1..2] = [-1,1].
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixFrequency = new HashMap<>();
    prefixFrequency.put(0, 1);

    int prefix = 0;
    int count = 0;
    for (int num : nums) {
      prefix += num;
      count += prefixFrequency.getOrDefault(prefix - k, 0);
      prefixFrequency.put(prefix, prefixFrequency.getOrDefault(prefix, 0) + 1);
    }

    return count;
  }

  /*
   * Question 8: Longest Consecutive Sequence
   * 
   * Question: Given an unsorted integer array nums, return the length of the longest consecutive elements sequence. The optimized solution must run in O(n) time.
   * 
   * Constraints: 0 <= nums.length <= 10^5; -10^9 <= nums[i] <= 10^9; optimized solution should be O(n).
   * 
   * Time and space complexity: Time O(n), Space O(n), because each number is expanded at most once from a sequence head.
   * 
   * Example 1:
   * Input: nums = [100,4,200,1,3,2]
   * Output: 4
   * Explanation: The longest consecutive sequence is [1,2,3,4].
   * 
   * Example 2:
   * Input: nums = [0,3,7,2,5,8,4,6,0,1]
   * Output: 9
   * Explanation: The longest sequence is [0,1,2,3,4,5,6,7,8].
   * 
   * Example 3:
   * Input: nums = []
   * Output: 0
   * Explanation: No elements means no consecutive sequence.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int longestConsecutive(int[] nums) {
    Set<Integer> values = new HashSet<>();
    for (int num : nums) {
      values.add(num);
    }

    int best = 0;
    for (int num : values) {
      if (values.contains(num - 1)) {
        continue;
      }

      int current = num;
      int length = 0;
      while (values.contains(current)) {
        length++;
        current++;
      }

      best = Math.max(best, length);
    }

    return best;
  }

  /*
   * Question 9: Majority Element
   * 
   * Question: Given an integer array nums, return the majority element. The majority element appears more than floor(n / 2) times, and it is guaranteed to exist.
   * 
   * Constraints: 1 <= nums.length <= 5 * 10^4; -2^31 <= nums[i] <= 2^31 - 1; majority element always exists.
   * 
   * Time and space complexity: Time O(n), Space O(1), using Boyer-Moore voting.
   * 
   * Example 1:
   * Input: nums = [3,2,3]
   * Output: 3
   * Explanation: 3 appears 2 times, which is more than floor(3 / 2).
   * 
   * Example 2:
   * Input: nums = [2,2,1,1,1,2,2]
   * Output: 2
   * Explanation: 2 appears 4 times, which is more than floor(7 / 2).
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: 1
   * Explanation: The only element is the majority element.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int majorityElement(int[] nums) {
    int candidate = 0;
    int votes = 0;

    for (int num : nums) {
      if (votes == 0) {
        candidate = num;
      }
      votes += num == candidate ? 1 : -1;
    }

    return candidate;
  }

  /*
   * Question 10: Find All Numbers Disappeared in an Array
   * 
   * Question: Given an integer array nums of length n where every nums[i] is in the range [1, n], return all numbers in the range [1, n] that do not appear in nums.
   * 
   * Constraints: n == nums.length; 1 <= n <= 10^5; 1 <= nums[i] <= n.
   * 
   * Time and space complexity: Time O(n), Space O(1) excluding the output list, using in-place marking.
   * 
   * Example 1:
   * Input: nums = [4,3,2,7,8,2,3,1]
   * Output: [5,6]
   * Explanation: The numbers 5 and 6 from range [1,8] do not appear.
   * 
   * Example 2:
   * Input: nums = [1,1]
   * Output: [2]
   * Explanation: The value 2 is missing from range [1,2].
   * 
   * Example 3:
   * Input: nums = [1,2,3,4]
   * Output: []
   * Explanation: Every number from 1 to 4 appears.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public List<Integer> findDisappearedNumbers(int[] nums) {
    for (int num : nums) {
      int index = Math.abs(num) - 1;
      if (nums[index] > 0) {
        nums[index] = -nums[index];
      }
    }

    List<Integer> missing = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > 0) {
        missing.add(i + 1);
      }
    }
    return missing;
  }

  /*
   * Question 11: Intersection of Two Arrays
   * 
   * Question: Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique, and the result may be returned in any order.
   * 
   * Constraints: 1 <= nums1.length, nums2.length <= 1000; 0 <= nums1[i], nums2[i] <= 1000; each output value must be unique.
   * 
   * Time and space complexity: Time O(m + n), Space O(m + r), where r is the number of unique intersection values.
   * 
   * Example 1:
   * Input: nums1 = [1,2,2,1], nums2 = [2,2]
   * Output: [2]
   * Explanation: 2 appears in both arrays, but output values must be unique.
   * 
   * Example 2:
   * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
   * Output: [9,4]
   * Explanation: Both 9 and 4 appear in both arrays; order does not matter.
   * 
   * Example 3:
   * Input: nums1 = [1,3,5], nums2 = [2,4,6]
   * Output: []
   * Explanation: There are no common values.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int[] intersection(int[] nums1, int[] nums2) {
    Set<Integer> values = new HashSet<>();
    for (int num : nums1) {
      values.add(num);
    }

    Set<Integer> result = new HashSet<>();
    for (int num : nums2) {
      if (values.contains(num)) {
        result.add(num);
      }
    }

    int[] answer = new int[result.size()];
    int index = 0;
    for (int num : result) {
      answer[index++] = num;
    }
    return answer;
  }

  /*
   * Question 12: First Unique Character in a String
   * 
   * Question: Given a string s, find the first non-repeating character and return its index. If it does not exist, return -1.
   * 
   * Constraints: 1 <= s.length <= 10^5; s consists of lowercase English letters.
   * 
   * Time and space complexity: Time O(n), Space O(1), because the count array has fixed size 26.
   * 
   * Example 1:
   * Input: s = "leetcode"
   * Output: 0
   * Explanation: l is the first character that appears once.
   * 
   * Example 2:
   * Input: s = "loveleetcode"
   * Output: 2
   * Explanation: v at index 2 is the first non-repeating character.
   * 
   * Example 3:
   * Input: s = "aabb"
   * Output: -1
   * Explanation: Every character appears more than once.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/arrays-hashing.html
   */
  public int firstUniqChar(String s) {
    int[] count = new int[26];

    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
    }

    for (int i = 0; i < s.length(); i++) {
      if (count[s.charAt(i) - 'a'] == 1) {
        return i;
      }
    }

    return -1;
  }
}
