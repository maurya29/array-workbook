package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 2: Two Pointers. Basic: source questions 1-12. */
public class TwoPointersBasic {

  /*
   * Question 1: Two Sum II - Input Array Is Sorted
   * 
   * Question: Return 1-indexed positions of two numbers in a sorted array that sum to target.
   * 
   * Constraints: 2 <= numbers.length <= 3 * 10^4; numbers is sorted nondecreasing; exactly one solution exists.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: numbers = [2,7,11,15], target = 9
   * Output: [1,2]
   * Explanation: 2 + 7 = 9; return 1-indexed positions.
   * 
   * Example 2:
   * Input: numbers = [2,3,4], target = 6
   * Output: [1,3]
   * Explanation: 2 + 4 = 6.
   * 
   * Example 3:
   * Input: numbers = [-1,0], target = -1
   * Output: [1,2]
   * Explanation: -1 + 0 = -1.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int[] twoSum(int[] numbers, int target) {
    int left = 0, right = numbers.length - 1;
    while (left < right) {
      int sum = numbers[left] + numbers[right];
      if (sum == target) return new int[] {left + 1, right + 1};
      if (sum < target) left++;
      else right--;
    }
    return new int[] {-1, -1};
  }

  /*
   * Question 2: Valid Palindrome
   * 
   * Question: Check whether a string is a palindrome after ignoring non-alphanumeric characters and case.
   * 
   * Constraints: 1 <= s.length <= 2 * 10^5; s consists of printable ASCII characters.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: s = "A man, a plan, a canal: Panama"
   * Output: true
   * Explanation: After cleanup it reads the same forward and backward.
   * 
   * Example 2:
   * Input: s = "race a car"
   * Output: false
   * Explanation: The cleaned string is not a palindrome.
   * 
   * Example 3:
   * Input: s = " "
   * Output: true
   * Explanation: No alphanumeric characters means an empty palindrome.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
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
   * Question 3: Remove Duplicates from Sorted Array
   * 
   * Question: Remove duplicates in-place and return the count of unique values.
   * 
   * Constraints: 1 <= nums.length <= 3 * 10^4; nums is sorted nondecreasing.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [1,1,2]
   * Output: 2, nums = [1,2,_]
   * Explanation: First two values are unique.
   * 
   * Example 2:
   * Input: nums = [0,0,1,1,1,2,2,3,3,4]
   * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
   * Explanation: There are five unique values.
   * 
   * Example 3:
   * Input: nums = [1]
   * Output: 1, nums = [1]
   * Explanation: Single value stays.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    int write = 1;
    for (int read = 1; read < nums.length; read++) {
      if (nums[read] != nums[write - 1]) nums[write++] = nums[read];
    }
    return write;
  }

  /*
   * Question 4: Remove Element
   * 
   * Question: Remove all occurrences of val in-place and return the new length.
   * 
   * Constraints: 0 <= nums.length <= 100; order of remaining elements may change or stay.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [3,2,2,3], val = 3
   * Output: 2, nums = [2,2,_,_]
   * Explanation: All 3s are removed.
   * 
   * Example 2:
   * Input: nums = [0,1,2,2,3,0,4,2], val = 2
   * Output: 5
   * Explanation: Values not equal to 2 remain.
   * 
   * Example 3:
   * Input: nums = [1], val = 1
   * Output: 0
   * Explanation: The only value is removed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int removeElement(int[] nums, int val) {
    int write = 0;
    for (int read = 0; read < nums.length; read++) {
      if (nums[read] != val) nums[write++] = nums[read];
    }
    return write;
  }

  /*
   * Question 5: Move Zeroes
   * 
   * Question: Move all zeroes to the end while preserving non-zero order.
   * 
   * Constraints: 1 <= nums.length <= 10^4; modify nums in-place.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: nums = [0,1,0,3,12]
   * Output: [1,3,12,0,0]
   * Explanation: Non-zero order is preserved.
   * 
   * Example 2:
   * Input: nums = [0]
   * Output: [0]
   * Explanation: Single zero stays.
   * 
   * Example 3:
   * Input: nums = [1,2,3]
   * Output: [1,2,3]
   * Explanation: No zeroes to move.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public void moveZeroes(int[] nums) {
    int write = 0;
    for (int read = 0; read < nums.length; read++) {
      if (nums[read] != 0) nums[write++] = nums[read];
    }
    while (write < nums.length) nums[write++] = 0;
  }

  /*
   * Question 6: Squares of a Sorted Array
   * 
   * Question: Return squares of a sorted array in nondecreasing order.
   * 
   * Constraints: 1 <= nums.length <= 10^4; nums sorted nondecreasing.
   * 
   * Time and space complexity: Time O(n), Space O(n).
   * 
   * Example 1:
   * Input: nums = [-4,-1,0,3,10]
   * Output: [0,1,9,16,100]
   * Explanation: Squares sorted.
   * 
   * Example 2:
   * Input: nums = [-7,-3,2,3,11]
   * Output: [4,9,9,49,121]
   * Explanation: Largest squares come from ends.
   * 
   * Example 3:
   * Input: nums = [0]
   * Output: [0]
   * Explanation: Single value.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int[] sortedSquares(int[] nums) {
    int n = nums.length, left = 0, right = n - 1, write = n - 1;
    int[] ans = new int[n];
    while (left <= right) {
      int a = nums[left] * nums[left];
      int b = nums[right] * nums[right];
      if (a > b) {
        ans[write--] = a;
        left++;
      } else {
        ans[write--] = b;
        right--;
      }
    }
    return ans;
  }

  /*
   * Question 7: Reverse String
   * 
   * Question: Reverse a character array in-place.
   * 
   * Constraints: 1 <= s.length <= 10^5; modify in-place with O(1) extra memory.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: s = ["h","e","l","l","o"]
   * Output: ["o","l","l","e","h"]
   * Explanation: Reverse in-place.
   * 
   * Example 2:
   * Input: s = ["H","a","n","n","a","h"]
   * Output: ["h","a","n","n","a","H"]
   * Explanation: All chars swapped symmetrically.
   * 
   * Example 3:
   * Input: s = ["a"]
   * Output: ["a"]
   * Explanation: Single char unchanged.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public void reverseString(char[] s) {
    int left = 0, right = s.length - 1;
    while (left < right) {
      char temp = s[left];
      s[left++] = s[right];
      s[right--] = temp;
    }
  }

  /*
   * Question 8: Merge Sorted Array
   * 
   * Question: Merge nums2 into nums1 in nondecreasing order.
   * 
   * Constraints: nums1.length == m + n; nums1 and nums2 sorted nondecreasing.
   * 
   * Time and space complexity: Time O(m+n), Space O(1).
   * 
   * Example 1:
   * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
   * Output: [1,2,2,3,5,6]
   * Explanation: Merged sorted order.
   * 
   * Example 2:
   * Input: nums1 = [1], m = 1, nums2 = [], n = 0
   * Output: [1]
   * Explanation: nums2 empty.
   * 
   * Example 3:
   * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
   * Output: [1]
   * Explanation: nums1 has only buffer.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1, j = n - 1, write = m + n - 1;
    while (j >= 0) {
      if (i >= 0 && nums1[i] > nums2[j]) nums1[write--] = nums1[i--];
      else nums1[write--] = nums2[j--];
    }
  }

  /*
   * Question 9: Linked List Cycle
   * 
   * Question: Return true if a linked list contains a cycle.
   * 
   * Constraints: 0 <= number of nodes <= 10^4.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: head = [3,2,0,-4], pos = 1
   * Output: true
   * Explanation: Tail connects to index 1.
   * 
   * Example 2:
   * Input: head = [1,2], pos = 0
   * Output: true
   * Explanation: Tail connects to head.
   * 
   * Example 3:
   * Input: head = [1], pos = -1
   * Output: false
   * Explanation: No cycle.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) return true;
    }
    return false;
  }

  /*
   * Question 10: Middle of the Linked List
   * 
   * Question: Return the middle node of a linked list; for even length, return the second middle.
   * 
   * Constraints: 1 <= number of nodes <= 100.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: head = [1,2,3,4,5]
   * Output: [3,4,5]
   * Explanation: Middle is node 3.
   * 
   * Example 2:
   * Input: head = [1,2,3,4,5,6]
   * Output: [4,5,6]
   * Explanation: Second middle is returned.
   * 
   * Example 3:
   * Input: head = [1]
   * Output: [1]
   * Explanation: Only node is middle.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  /*
   * Question 11: Container With Most Water
   * 
   * Question: Find the maximum water area formed by two vertical lines.
   * 
   * Constraints: 2 <= height.length <= 10^5; 0 <= height[i] <= 10^4.
   * 
   * Time and space complexity: Time O(n), Space O(1).
   * 
   * Example 1:
   * Input: height = [1,8,6,2,5,4,8,3,7]
   * Output: 49
   * Explanation: Lines at 1 and 8 form area 49.
   * 
   * Example 2:
   * Input: height = [1,1]
   * Output: 1
   * Explanation: Only one container.
   * 
   * Example 3:
   * Input: height = [4,3,2,1,4]
   * Output: 16
   * Explanation: Ends form best area.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int maxArea(int[] height) {
    int left = 0, right = height.length - 1, best = 0;
    while (left < right) {
      int area = Math.min(height[left], height[right]) * (right - left);
      best = Math.max(best, area);
      if (height[left] < height[right]) left++;
      else right--;
    }
    return best;
  }

  /*
   * Question 12: 3Sum
   * 
   * Question: Return all unique triplets that sum to zero.
   * 
   * Constraints: 3 <= nums.length <= 3000; -10^5 <= nums[i] <= 10^5.
   * 
   * Time and space complexity: Time O(n^2), Space O(output).
   * 
   * Example 1:
   * Input: nums = [-1,0,1,2,-1,-4]
   * Output: [[-1,-1,2],[-1,0,1]]
   * Explanation: Unique triplets sum to zero.
   * 
   * Example 2:
   * Input: nums = [0,1,1]
   * Output: []
   * Explanation: No triplet sums to zero.
   * 
   * Example 3:
   * Input: nums = [0,0,0]
   * Output: [[0,0,0]]
   * Explanation: One unique triplet.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) continue;
      int left = i + 1, right = nums.length - 1;
      while (left < right) {
        int sum = nums[i] + nums[left] + nums[right];
        if (sum == 0) {
          ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
          left++;
          right--;
          while (left < right && nums[left] == nums[left - 1]) left++;
          while (left < right && nums[right] == nums[right + 1]) right--;
        } else if (sum < 0) left++;
        else right--;
      }
    }
    return ans;
  }
}
