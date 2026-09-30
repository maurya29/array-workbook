package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 24: Strings. Moderate: source questions 13-20. */
public class StringsModerate {

  /*
   * Question 13: String Compression
   * 
   * Question: Given an array of characters, compress it in place by replacing each group with the character followed by its count when count is greater than one, and return the new length.
   * 
   * Constraints: 1 <= chars.length <= 2000; chars[i] is a lowercase/uppercase letter, digit, or symbol.
   * 
   * Time and space complexity: Time O(n); Space O(1). Two pointers read runs and write compressed output in place.
   * 
   * Example 1:
   * Input: chars = ["a","a","b","b","c","c","c"]
   * Output: 6, chars starts ["a","2","b","2","c","3"]
   * Explanation: Each repeated run writes its count.
   * 
   * Example 2:
   * Input: chars = ["a"]
   * Output: 1
   * Explanation: A count of one is omitted.
   * 
   * Example 3:
   * Input: chars = ["a" repeated 12 times]
   * Output: 3, chars starts ["a","1","2"]
   * Explanation: Multi-digit counts are written one digit at a time.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int compress(char[] chars) {
    int write = 0;
    int read = 0;
    while (read < chars.length) {
      char current = chars[read];
      int start = read;
      while (read < chars.length && chars[read] == current) read++;
      chars[write++] = current;
      int count = read - start;
      if (count > 1) {
        String digits = String.valueOf(count);
        for (int i = 0; i < digits.length(); i++) chars[write++] = digits.charAt(i);
      }
    }
    return write;
  }

  /*
   * Question 14: Reverse Words in a String
   * 
   * Question: Given a string s, reverse the order of its words, removing leading/trailing spaces and reducing multiple spaces to one.
   * 
   * Constraints: 1 <= s.length <= 10000; s contains English letters, digits, and spaces.
   * 
   * Time and space complexity: Time O(n); Space O(n). Parse words from the end without storing all tokens first.
   * 
   * Example 1:
   * Input: s = "the sky is blue"
   * Output: "blue is sky the"
   * Explanation: Word order is reversed.
   * 
   * Example 2:
   * Input: s = "  hello world  "
   * Output: "world hello"
   * Explanation: Outer spaces are removed.
   * 
   * Example 3:
   * Input: s = "a good   example"
   * Output: "example good a"
   * Explanation: Multiple spaces collapse to one.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String reverseWords(String s) {
    StringBuilder answer = new StringBuilder();
    int index = s.length() - 1;
    while (index >= 0) {
      while (index >= 0 && s.charAt(index) == ' ') index--;
      if (index < 0) break;
      int end = index;
      while (index >= 0 && s.charAt(index) != ' ') index--;
      if (answer.length() > 0) answer.append(' ');
      answer.append(s, index + 1, end + 1);
    }
    return answer.toString();
  }

  /*
   * Question 15: Zigzag Conversion
   * 
   * Question: Given a string s and number of rows, write the string in zigzag order and then read row by row.
   * 
   * Constraints: 1 <= s.length <= 1000; 1 <= numRows <= 1000; s contains English letters, comma, and period.
   * 
   * Time and space complexity: Time O(n); Space O(n). Append directly to row builders while simulating direction.
   * 
   * Example 1:
   * Input: s = "PAYPALISHIRING", numRows = 3
   * Output: "PAHNAPLSIIGYIR"
   * Explanation: Rows are read after zigzag placement.
   * 
   * Example 2:
   * Input: s = "PAYPALISHIRING", numRows = 4
   * Output: "PINALSIGYAHRPI"
   * Explanation: A different row count changes the cycle.
   * 
   * Example 3:
   * Input: s = "A", numRows = 1
   * Output: "A"
   * Explanation: One row leaves the string unchanged.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String convert(String s, int numRows) {
    if (numRows == 1 || numRows >= s.length()) return s;
    StringBuilder[] rows = new StringBuilder[numRows];
    for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();

    int row = 0;
    int direction = 1;
    for (int i = 0; i < s.length(); i++) {
      rows[row].append(s.charAt(i));
      if (row == 0) direction = 1;
      else if (row == numRows - 1) direction = -1;
      row += direction;
    }

    StringBuilder answer = new StringBuilder();
    for (StringBuilder current : rows) answer.append(current);
    return answer.toString();
  }

  /*
   * Question 16: Add Strings
   * 
   * Question: Given two non-negative integers as strings, return their sum as a string without converting the full inputs to integers.
   * 
   * Constraints: 1 <= num1.length, num2.length <= 10000; num1 and num2 contain digits only and have no leading zeros except 0.
   * 
   * Time and space complexity: Time O(n+m); Space O(n+m). Manual carry addition over characters.
   * 
   * Example 1:
   * Input: num1 = "11", num2 = "123"
   * Output: "134"
   * Explanation: 11 + 123 = 134.
   * 
   * Example 2:
   * Input: num1 = "456", num2 = "77"
   * Output: "533"
   * Explanation: Carry propagates across digits.
   * 
   * Example 3:
   * Input: num1 = "0", num2 = "0"
   * Output: "0"
   * Explanation: Zero plus zero remains zero.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String addStrings(String num1, String num2) {
    StringBuilder reversed = new StringBuilder();
    int i = num1.length() - 1;
    int j = num2.length() - 1;
    int carry = 0;
    while (i >= 0 || j >= 0 || carry != 0) {
      int sum = carry;
      if (i >= 0) sum += num1.charAt(i--) - '0';
      if (j >= 0) sum += num2.charAt(j--) - '0';
      reversed.append(sum % 10);
      carry = sum / 10;
    }
    return reversed.reverse().toString();
  }

  /*
   * Question 17: Decode String
   * 
   * Question: Given an encoded string with patterns k[encoded_string], return the decoded string.
   * 
   * Constraints: 1 <= s.length <= 30; s contains lowercase letters, digits, and brackets; decoded length is within limits.
   * 
   * Time and space complexity: Time O(n + D * L) upper bound; Space O(n + L), where L is decoded length and D is maximum nesting depth. Nested expansions can copy characters repeatedly.
   * 
   * Example 1:
   * Input: s = "3[a]2[bc]"
   * Output: "aaabcbc"
   * Explanation: Each bracketed block repeats independently.
   * 
   * Example 2:
   * Input: s = "3[a2[c]]"
   * Output: "accaccacc"
   * Explanation: The inner block is decoded before outer repetition.
   * 
   * Example 3:
   * Input: s = "2[abc]3[cd]ef"
   * Output: "abcabccdcdcdef"
   * Explanation: Plain suffix is appended after decoded blocks.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String decodeString(String s) {
    Deque<Integer> counts = new ArrayDeque<>();
    Deque<StringBuilder> stack = new ArrayDeque<>();
    StringBuilder current = new StringBuilder();
    int number = 0;
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (Character.isDigit(ch)) number = number * 10 + ch - '0';
      else if (ch == '[') {
        counts.push(number);
        stack.push(current);
        current = new StringBuilder();
        number = 0;
      } else if (ch == ']') {
        StringBuilder decoded = stack.pop();
        int repeat = counts.pop();
        for (int k = 0; k < repeat; k++) decoded.append(current);
        current = decoded;
      } else {
        current.append(ch);
      }
    }
    return current.toString();
  }

  /*
   * Question 18: Basic Calculator II
   * 
   * Question: Given a string expression containing non-negative integers and operators +, -, *, /, evaluate it with integer division truncating toward zero.
   * 
   * Constraints: 1 <= s.length <= 300000; s consists of digits, spaces, +, -, *, and /; expression is valid.
   * 
   * Time and space complexity: Time O(n); Space O(1). One pass keeps result, last term, current number, and operator.
   * 
   * Example 1:
   * Input: s = "3+2*2"
   * Output: 7
   * Explanation: Multiplication happens before addition.
   * 
   * Example 2:
   * Input: s = " 3/2 "
   * Output: 1
   * Explanation: Integer division truncates toward zero.
   * 
   * Example 3:
   * Input: s = " 3+5 / 2 "
   * Output: 5
   * Explanation: 5 / 2 becomes 2, then 3 + 2.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int calculate(String s) {
    int result = 0;
    int last = 0;
    int number = 0;
    char operator = '+';
    for (int i = 0; i <= s.length(); i++) {
      char ch = i < s.length() ? s.charAt(i) : '+';
      if (ch == ' ') continue;
      if (Character.isDigit(ch)) {
        number = number * 10 + ch - '0';
      } else {
        if (operator == '+') { result += last; last = number; }
        else if (operator == '-') { result += last; last = -number; }
        else if (operator == '*') last *= number;
        else last /= number;
        operator = ch;
        number = 0;
      }
    }
    return result + last;
  }

  /*
   * Question 19: Longest Common Prefix
   * 
   * Question: Given an array of strings, return the longest common prefix among all strings.
   * 
   * Constraints: 1 <= strs.length <= 200; 0 <= strs[i].length <= 200; strs[i] contains lowercase English letters.
   * 
   * Time and space complexity: Time O(S); Space O(1). Vertical scan stops at the first mismatch.
   * 
   * Example 1:
   * Input: strs = ["flower","flow","flight"]
   * Output: "fl"
   * Explanation: All strings share fl.
   * 
   * Example 2:
   * Input: strs = ["dog","racecar","car"]
   * Output: ""
   * Explanation: There is no common first character.
   * 
   * Example 3:
   * Input: strs = ["alone"]
   * Output: "alone"
   * Explanation: One string is its own prefix.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public String longestCommonPrefix(String[] strs) {
    for (int index = 0; index < strs[0].length(); index++) {
      char expected = strs[0].charAt(index);
      for (int i = 1; i < strs.length; i++) {
        if (index == strs[i].length() || strs[i].charAt(index) != expected) {
          return strs[0].substring(0, index);
        }
      }
    }
    return strs[0];
  }

  /*
   * Question 20: Compare Version Numbers
   * 
   * Question: Given two version strings, compare their revision numbers and return -1, 0, or 1.
   * 
   * Constraints: 1 <= version1.length, version2.length <= 500; versions contain digits and dots; revisions fit in 32-bit signed integer.
   * 
   * Time and space complexity: Time O(n+m); Space O(1). Two pointers parse segments directly.
   * 
   * Example 1:
   * Input: version1 = "1.01", version2 = "1.001"
   * Output: 0
   * Explanation: Leading zeros do not change segment value.
   * 
   * Example 2:
   * Input: version1 = "1.0", version2 = "1.0.0"
   * Output: 0
   * Explanation: Missing segments count as zero.
   * 
   * Example 3:
   * Input: version1 = "0.1", version2 = "1.1"
   * Output: -1
   * Explanation: The first segment 0 is smaller than 1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/strings.html
   */
  public int compareVersion(String version1, String version2) {
    int i = 0, j = 0;
    while (i < version1.length() || j < version2.length()) {
      int left = 0;
      while (i < version1.length() && version1.charAt(i) != '.') left = left * 10 + version1.charAt(i++) - '0';
      int right = 0;
      while (j < version2.length() && version2.charAt(j) != '.') right = right * 10 + version2.charAt(j++) - '0';
      if (left != right) return left < right ? -1 : 1;
      i++;
      j++;
    }
    return 0;
  }
}
