package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 24: Strings. Basic: source questions 1-12. */
public class StringsBasic {

  /*
   * Question 1: Valid Anagram
   * 
   * Question: Given two strings s and t, return true if t is an anagram of s and false otherwise.
   * 
   * Constraints: 1 <= s.length, t.length <= 50000; s and t contain lowercase English letters.
   * 
   * Time and space complexity: Time O(n); Space O(1). Count 26 lowercase letters.
   * 
   * Example 1:
   * Input: s = "anagram", t = "nagaram"
   * Output: true
   * Explanation: Both strings contain the same characters with the same counts.
   * 
   * Example 2:
   * Input: s = "rat", t = "car"
   * Output: false
   * Explanation: The character counts differ.
   * 
   * Example 3:
   * Input: s = "a", t = "ab"
   * Output: false
   * Explanation: Different lengths cannot be anagrams.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
      count[t.charAt(i) - 'a']--;
    }
    for (int value : count) {
      if (value != 0) return false;
    }
    return true;
  }

  /*
   * Question 2: Valid Palindrome
   * 
   * Question: Given a string s, return true if it is a palindrome after converting uppercase letters to lowercase and removing non-alphanumeric characters.
   * 
   * Constraints: 1 <= s.length <= 200000; s consists of printable ASCII characters.
   * 
   * Time and space complexity: Time O(n); Space O(1). Two pointers skip ignored characters in place.
   * 
   * Example 1:
   * Input: s = "A man, a plan, a canal: Panama"
   * Output: true
   * Explanation: Normalized string is amanaplanacanalpanama.
   * 
   * Example 2:
   * Input: s = "race a car"
   * Output: false
   * Explanation: Normalized string is not symmetric.
   * 
   * Example 3:
   * Input: s = " "
   * Output: true
   * Explanation: No alphanumeric characters remain.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;
    while (left < right) {
      while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
      while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
      if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
      left++;
      right--;
    }
    return true;
  }

  /*
   * Question 3: Longest Substring Without Repeating Characters
   * 
   * Question: Given a string s, return the length of the longest substring without repeating characters.
   * 
   * Constraints: 0 <= s.length <= 50000; s consists of English letters, digits, symbols, and spaces.
   * 
   * Time and space complexity: Time O(n); Space O(charset). Sliding window moves each boundary at most n times.
   * 
   * Example 1:
   * Input: s = "abcabcbb"
   * Output: 3
   * Explanation: The longest substrings include abc.
   * 
   * Example 2:
   * Input: s = "bbbbb"
   * Output: 1
   * Explanation: Only one repeated character can be used.
   * 
   * Example 3:
   * Input: s = "pwwkew"
   * Output: 3
   * Explanation: wke is valid; pwke is not contiguous.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> last = new HashMap<>();
    int left = 0;
    int best = 0;
    for (int right = 0; right < s.length(); right++) {
      char ch = s.charAt(right);
      if (last.containsKey(ch) && last.get(ch) >= left) left = last.get(ch) + 1;
      last.put(ch, right);
      best = Math.max(best, right - left + 1);
    }
    return best;
  }

  /*
   * Question 4: Longest Repeating Character Replacement
   * 
   * Question: Given a string s and integer k, return the length of the longest substring that can be made of one repeated character after at most k replacements.
   * 
   * Constraints: 1 <= s.length <= 100000; s contains uppercase English letters; 0 <= k <= s.length.
   * 
   * Time and space complexity: Time O(n); Space O(1). Sliding window tracks 26 uppercase counts.
   * 
   * Example 1:
   * Input: s = "ABAB", k = 2
   * Output: 4
   * Explanation: Replace two letters to make the whole string equal.
   * 
   * Example 2:
   * Input: s = "AABABBA", k = 1
   * Output: 4
   * Explanation: AABA or ABBA can be made uniform.
   * 
   * Example 3:
   * Input: s = "AAAA", k = 0
   * Output: 4
   * Explanation: No replacement is needed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int left = 0;
    int maxFreq = 0;
    int best = 0;
    for (int right = 0; right < s.length(); right++) {
      maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
      while (right - left + 1 - maxFreq > k) {
        count[s.charAt(left) - 'A']--;
        left++;
      }
      best = Math.max(best, right - left + 1);
    }
    return best;
  }

  /*
   * Question 5: Minimum Window Substring
   * 
   * Question: Given strings s and t, return the minimum window substring of s that contains every character of t including duplicates.
   * 
   * Constraints: 1 <= s.length, t.length <= 100000; s and t consist of uppercase and lowercase English letters.
   * 
   * Time and space complexity: Time O(n + m), where n and m are the two string lengths; Space O(charset).
   * 
   * Example 1:
   * Input: s = "ADOBECODEBANC", t = "ABC"
   * Output: "BANC"
   * Explanation: BANC is the smallest window containing A, B, and C.
   * 
   * Example 2:
   * Input: s = "a", t = "a"
   * Output: "a"
   * Explanation: The only character satisfies the requirement.
   * 
   * Example 3:
   * Input: s = "a", t = "aa"
   * Output: ""
   * Explanation: The source does not contain enough a characters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String minWindow(String s, String t) {
    int[] need = new int[128];
    int required = 0;
    for (int i = 0; i < t.length(); i++) {
      if (need[t.charAt(i)]++ == 0) required++;
    }

    int[] window = new int[128];
    int formed = 0, left = 0, bestLeft = 0, bestLength = Integer.MAX_VALUE;
    for (int right = 0; right < s.length(); right++) {
      char add = s.charAt(right);
      if (++window[add] == need[add] && need[add] > 0) formed++;
      while (formed == required) {
        if (right - left + 1 < bestLength) {
          bestLength = right - left + 1;
          bestLeft = left;
        }
        char remove = s.charAt(left++);
        if (window[remove]-- == need[remove] && need[remove] > 0) formed--;
      }
    }
    return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestLeft, bestLeft + bestLength);
  }

  /*
   * Question 6: Group Anagrams
   * 
   * Question: Given an array of strings, group the anagrams together in any order.
   * 
   * Constraints: 1 <= strs.length <= 10000; 0 <= strs[i].length <= 100; strs[i] contains lowercase English letters.
   * 
   * Time and space complexity: Time O(nL); Space O(nL). Use a 26-count signature as the map key.
   * 
   * Example 1:
   * Input: strs = ["eat","tea","tan","ate","nat","bat"]
   * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
   * Explanation: Words with the same sorted key are grouped.
   * 
   * Example 2:
   * Input: strs = [""]
   * Output: [[""]]
   * Explanation: The empty string forms one group.
   * 
   * Example 3:
   * Input: strs = ["a"]
   * Output: [["a"]]
   * Explanation: One word forms one group.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();
    for (String word : strs) {
      int[] count = new int[26];
      for (int i = 0; i < word.length(); i++) count[word.charAt(i) - 'a']++;
      String key = Arrays.toString(count);
      groups.computeIfAbsent(key, unused -> new ArrayList<>()).add(word);
    }
    return new ArrayList<>(groups.values());
  }

  /*
   * Question 7: Encode and Decode Strings
   * 
   * Question: Design an algorithm to encode a list of strings into one string and decode it back to the original list.
   * 
   * Constraints: 0 <= strs.length <= 200; 0 <= strs[i].length <= 200; strings may contain any valid characters.
   * 
   * Time and space complexity: Encode O(total characters); decode O(total characters); Space O(total characters). Length prefixes avoid escaping.
   * 
   * Example 1:
   * Input: strs = ["lint","code","love","you"]
   * Output: ["lint","code","love","you"] after decode
   * Explanation: Each length prefix makes decoding unambiguous.
   * 
   * Example 2:
   * Input: strs = [""]
   * Output: [""] after decode
   * Explanation: Length zero is encoded explicitly.
   * 
   * Example 3:
   * Input: strs = ["a#b","12"]
   * Output: ["a#b","12"] after decode
   * Explanation: Delimiter characters inside content are preserved.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String encode(List<String> strs) {
    StringBuilder encoded = new StringBuilder();
    for (String s : strs) {
      encoded.append(s.length()).append('#').append(s);
    }
    return encoded.toString();
  }

  public List<String> decode(String s) {
    List<String> answer = new ArrayList<>();
    int index = 0;
    while (index < s.length()) {
      int delimiter = index;
      while (s.charAt(delimiter) != '#') delimiter++;
      int length = Integer.parseInt(s.substring(index, delimiter));
      int start = delimiter + 1;
      answer.add(s.substring(start, start + length));
      index = start + length;
    }
    return answer;
  }

  /*
   * Question 8: Longest Palindromic Substring
   * 
   * Question: Given a string s, return the longest palindromic substring in s.
   * 
   * Constraints: 1 <= s.length <= 1000; s consists of digits and English letters.
   * 
   * Time and space complexity: Time O(n^2); Space O(1). Expand around each center.
   * 
   * Example 1:
   * Input: s = "babad"
   * Output: "bab" or "aba"
   * Explanation: Both length-three palindromes are valid.
   * 
   * Example 2:
   * Input: s = "cbbd"
   * Output: "bb"
   * Explanation: The longest palindrome has an even center.
   * 
   * Example 3:
   * Input: s = "a"
   * Output: "a"
   * Explanation: A single character is a palindrome.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String longestPalindrome(String s) {
    int bestStart = 0;
    int bestLength = 1;
    for (int center = 0; center < s.length(); center++) {
      int odd = expandQ8(s, center, center);
      int even = expandQ8(s, center, center + 1);
      int length = Math.max(odd, even);
      if (length > bestLength) {
        bestLength = length;
        bestStart = center - (length - 1) / 2;
      }
    }
    return s.substring(bestStart, bestStart + bestLength);
  }

  private int expandQ8(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
      left--;
      right++;
    }
    return right - left - 1;
  }

  /*
   * Question 9: Palindromic Substrings
   * 
   * Question: Given a string s, return the number of palindromic substrings in it.
   * 
   * Constraints: 1 <= s.length <= 1000; s consists of lowercase English letters.
   * 
   * Time and space complexity: Time O(n^2); Space O(1). Expand around 2n-1 centers.
   * 
   * Example 1:
   * Input: s = "abc"
   * Output: 3
   * Explanation: Only single-character palindromes exist.
   * 
   * Example 2:
   * Input: s = "aaa"
   * Output: 6
   * Explanation: Three singles, two aa substrings, and one aaa.
   * 
   * Example 3:
   * Input: s = "aba"
   * Output: 4
   * Explanation: a, b, a, and aba are palindromes.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int countSubstrings(String s) {
    int count = 0;
    for (int center = 0; center < s.length(); center++) {
      count += expandQ9(s, center, center);
      count += expandQ9(s, center, center + 1);
    }
    return count;
  }

  private int expandQ9(String s, int left, int right) {
    int count = 0;
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
      count++;
      left--;
      right++;
    }
    return count;
  }

  /*
   * Question 10: Valid Parenthesis String
   * 
   * Question: Given a string containing (, ), and *, return true if * can be treated as (, ), or empty to make the string valid.
   * 
   * Constraints: 1 <= s.length <= 100; s[i] is (, ), or *.
   * 
   * Time and space complexity: Time O(n); Space O(1). Greedy lower and upper open-count bounds.
   * 
   * Example 1:
   * Input: s = "()"
   * Output: true
   * Explanation: Already valid.
   * 
   * Example 2:
   * Input: s = "(*)"
   * Output: true
   * Explanation: * can be empty.
   * 
   * Example 3:
   * Input: s = "(*))"
   * Output: true
   * Explanation: * can be an opening parenthesis.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public boolean checkValidString(String s) {
    int low = 0;
    int high = 0;
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == '(') {
        low++;
        high++;
      } else if (ch == ')') {
        low = Math.max(0, low - 1);
        high--;
      } else {
        low = Math.max(0, low - 1);
        high++;
      }
      if (high < 0) return false;
    }
    return low == 0;
  }

  /*
   * Question 11: Find the Index of the First Occurrence in a String
   * 
   * Question: Given strings haystack and needle, return the index of the first occurrence of needle in haystack, or -1 if it is not present.
   * 
   * Constraints: 1 <= haystack.length, needle.length <= 10000; strings contain lowercase English letters.
   * 
   * Time and space complexity: Time O(n+m); Space O(m). KMP scans haystack once after prefix preprocessing.
   * 
   * Example 1:
   * Input: haystack = "sadbutsad", needle = "sad"
   * Output: 0
   * Explanation: The first occurrence starts at index 0.
   * 
   * Example 2:
   * Input: haystack = "leetcode", needle = "leeto"
   * Output: -1
   * Explanation: The pattern is absent.
   * 
   * Example 3:
   * Input: haystack = "aaaaa", needle = "bba"
   * Output: -1
   * Explanation: Repeated haystack characters still do not match.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int strStr(String haystack, String needle) {
    int[] lps = buildLpsQ11(needle);
    int j = 0;
    for (int i = 0; i < haystack.length(); i++) {
      while (j > 0 && haystack.charAt(i) != needle.charAt(j)) j = lps[j - 1];
      if (haystack.charAt(i) == needle.charAt(j)) j++;
      if (j == needle.length()) return i - needle.length() + 1;
    }
    return -1;
  }

  private int[] buildLpsQ11(String pattern) {
    int[] lps = new int[pattern.length()];
    for (int i = 1, len = 0; i < pattern.length(); i++) {
      while (len > 0 && pattern.charAt(i) != pattern.charAt(len)) len = lps[len - 1];
      if (pattern.charAt(i) == pattern.charAt(len)) lps[i] = ++len;
    }
    return lps;
  }

  /*
   * Question 12: Repeated Substring Pattern
   * 
   * Question: Given a string s, return true if it can be constructed by repeating one of its proper substrings multiple times.
   * 
   * Constraints: 1 <= s.length <= 10000; s consists of lowercase English letters.
   * 
   * Time and space complexity: Time O(n); Space O(n). The prefix table gives the longest border; a valid period is n - border when it divides n.
   * 
   * Example 1:
   * Input: s = "abab"
   * Output: true
   * Explanation: ab repeated twice forms the string.
   * 
   * Example 2:
   * Input: s = "aba"
   * Output: false
   * Explanation: No proper substring repeats to form it.
   * 
   * Example 3:
   * Input: s = "abcabcabcabc"
   * Output: true
   * Explanation: abc repeats four times.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public boolean repeatedSubstringPattern(String s) {
    int n = s.length();
    int[] lps = new int[n];
    for (int i = 1; i < n; i++) {
      int length = lps[i - 1];
      while (length > 0 && s.charAt(i) != s.charAt(length)) {
        length = lps[length - 1];
      }
      if (s.charAt(i) == s.charAt(length)) length++;
      lps[i] = length;
    }
    int border = lps[n - 1];
    int period = n - border;
    return border > 0 && n % period == 0;
  }
}
