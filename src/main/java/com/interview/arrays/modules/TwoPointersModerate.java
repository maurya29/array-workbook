package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 2: Two Pointers. Moderate: source questions 13-20. */
public class TwoPointersModerate {

  /*
   * Question 13: 3Sum Closest
   * 
   * Question: Find the triplet sum closest to target.
   * 
   * Constraints: 3 <= nums.length <= 500; -1000 <= nums[i] <= 1000; -10^4 <= target <= 10^4.
   * 
   * Time and space complexity: Time O(n^2); auxiliary space O(log n) for sorting.
   * 
   * Example 1:
   * Input: nums = [-1,2,1,-4], target = 1
   * Output: 2
   * Explanation: The closest triplet sum is -1 + 2 + 1 = 2.
   * 
   * Example 2:
   * Input: nums = [0,0,0], target = 1
   * Output: 0
   * Explanation: Only possible triplet sum is 0.
   * 
   * Example 3:
   * Input: nums = [1,1,1,0], target = -100
   * Output: 2
   * Explanation: Closest sum is 0 + 1 + 1 = 2.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int best = nums[0] + nums[1] + nums[2];
    for (int i = 0; i < nums.length - 2; i++) {
      int left = i + 1, right = nums.length - 1;
      while (left < right) {
        int sum = nums[i] + nums[left] + nums[right];
        if (Math.abs(target - sum) < Math.abs(target - best)) best = sum;
        if (sum < target) left++;
        else if (sum > target) right--;
        else return target;
      }
    }
    return best;
  }

  /*
   * Question 14: 4Sum
   * 
   * Question: Return all unique quadruplets that sum to target.
   * 
   * Constraints: 1 <= nums.length <= 200; -10^9 <= nums[i], target <= 10^9.
   * 
   * Time and space complexity: Time O(n^3); auxiliary space O(log n) for sorting, plus output storage.
   * 
   * Example 1:
   * Input: nums = [1,0,-1,0,-2,2], target = 0
   * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
   * Explanation: Unique quadruplets sum to target.
   * 
   * Example 2:
   * Input: nums = [2,2,2,2,2], target = 8
   * Output: [[2,2,2,2]]
   * Explanation: Duplicate quadruplets collapse into one.
   * 
   * Example 3:
   * Input: nums = [1,2,3], target = 6
   * Output: []
   * Explanation: Fewer than four elements cannot form a quadruplet.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public List<List<Integer>> fourSum(int[] nums, int target) {
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<>();
    int n = nums.length;
    for (int i = 0; i < n - 3; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) continue;
      for (int j = i + 1; j < n - 2; j++) {
        if (j > i + 1 && nums[j] == nums[j - 1]) continue;
        int left = j + 1, right = n - 1;
        while (left < right) {
          long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
          if (sum == target) {
            ans.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
            left++;
            right--;
            while (left < right && nums[left] == nums[left - 1]) left++;
            while (left < right && nums[right] == nums[right + 1]) right--;
          } else if (sum < target) left++;
          else right--;
        }
      }
    }
    return ans;
  }

  /*
   * Question 15: Sort Colors
   * 
   * Question: Sort an array containing only 0, 1, and 2 in-place.
   * 
   * Constraints: 1 <= nums.length <= 300; nums[i] is 0, 1, or 2.
   * 
   * Time and space complexity: Time O(n); Space O(1).
   * 
   * Example 1:
   * Input: nums = [2,0,2,1,1,0]
   * Output: [0,0,1,1,2,2]
   * Explanation: Sort 0s, 1s, and 2s in-place.
   * 
   * Example 2:
   * Input: nums = [2,0,1]
   * Output: [0,1,2]
   * Explanation: All colors ordered.
   * 
   * Example 3:
   * Input: nums = [0]
   * Output: [0]
   * Explanation: Single value remains.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public void sortColors(int[] nums) {
    int low = 0, mid = 0, high = nums.length - 1;
    while (mid <= high) {
      if (nums[mid] == 0) swapQ15(nums, low++, mid++);
      else if (nums[mid] == 1) mid++;
      else swapQ15(nums, mid, high--);
    }
  }

  private void swapQ15(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /*
   * Question 16: Trapping Rain Water
   * 
   * Question: Compute how much rain water can be trapped between bars.
   * 
   * Constraints: 1 <= height.length <= 2 * 10^4; 0 <= height[i] <= 10^5.
   * 
   * Time and space complexity: Time O(n); Space O(1).
   * 
   * Example 1:
   * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
   * Output: 6
   * Explanation: Water is trapped between higher boundaries.
   * 
   * Example 2:
   * Input: height = [4,2,0,3,2,5]
   * Output: 9
   * Explanation: Multiple basins trap 9 units.
   * 
   * Example 3:
   * Input: height = [1,2,3]
   * Output: 0
   * Explanation: Monotonic bars trap no water.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int trap(int[] height) {
    int left = 0, right = height.length - 1;
    int leftMax = 0, rightMax = 0, water = 0;
    while (left < right) {
      if (height[left] < height[right]) {
        leftMax = Math.max(leftMax, height[left]);
        water += leftMax - height[left++];
      } else {
        rightMax = Math.max(rightMax, height[right]);
        water += rightMax - height[right--];
      }
    }
    return water;
  }

  /*
   * Question 17: Linked List Cycle II
   * 
   * Question: Return the node where a linked list cycle begins.
   * 
   * Constraints: 0 <= number of nodes <= 10^4; pos may be -1 for no cycle.
   * 
   * Time and space complexity: Time O(n); Space O(1).
   * 
   * Example 1:
   * Input: head = [3,2,0,-4], pos = 1
   * Output: node with value 2
   * Explanation: Cycle starts at index 1.
   * 
   * Example 2:
   * Input: head = [1,2], pos = 0
   * Output: node with value 1
   * Explanation: Cycle starts at head.
   * 
   * Example 3:
   * Input: head = [1], pos = -1
   * Output: null
   * Explanation: No cycle exists.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        ListNode entry = head;
        while (entry != slow) {
          entry = entry.next;
          slow = slow.next;
        }
        return entry;
      }
    }
    return null;
  }

  /*
   * Question 18: Remove Nth Node From End of List
   * 
   * Question: Remove the nth node from the end of a linked list.
   * 
   * Constraints: 1 <= list length <= 30; 1 <= n <= list length.
   * 
   * Time and space complexity: Time O(n); Space O(1).
   * 
   * Example 1:
   * Input: head = [1,2,3,4,5], n = 2
   * Output: [1,2,3,5]
   * Explanation: Remove 4.
   * 
   * Example 2:
   * Input: head = [1], n = 1
   * Output: []
   * Explanation: Remove the only node.
   * 
   * Example 3:
   * Input: head = [1,2], n = 1
   * Output: [1]
   * Explanation: Remove tail.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode slow = dummy, fast = dummy;
    for (int i = 0; i < n; i++) fast = fast.next;
    while (fast.next != null) {
      slow = slow.next;
      fast = fast.next;
    }
    slow.next = slow.next.next;
    return dummy.next;
  }

  /*
   * Question 19: Partition List
   * 
   * Question: Partition a linked list so nodes less than x come before nodes greater than or equal to x.
   * 
   * Constraints: 0 <= list length <= 200; -100 <= Node.val <= 100; -200 <= x <= 200.
   * 
   * Time and space complexity: Time O(n); Space O(1).
   * 
   * Example 1:
   * Input: head = [1,4,3,2,5,2], x = 3
   * Output: [1,2,2,4,3,5]
   * Explanation: Nodes less than 3 come first preserving order.
   * 
   * Example 2:
   * Input: head = [2,1], x = 2
   * Output: [1,2]
   * Explanation: 1 moves before 2.
   * 
   * Example 3:
   * Input: head = [], x = 1
   * Output: []
   * Explanation: Empty list remains empty.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public ListNode partition(ListNode head, int x) {
    ListNode smallDummy = new ListNode(0), largeDummy = new ListNode(0);
    ListNode small = smallDummy, large = largeDummy;
    while (head != null) {
      if (head.val < x) {
        small.next = head;
        small = small.next;
      } else {
        large.next = head;
        large = large.next;
      }
      head = head.next;
    }
    large.next = null;
    small.next = largeDummy.next;
    return smallDummy.next;
  }

  /*
   * Question 20: Boats to Save People
   * 
   * Question: Return the minimum boats needed when each boat carries at most two people under a weight limit.
   * 
   * Constraints: 1 <= people.length <= 5 * 10^4; 1 <= people[i] <= limit <= 3 * 10^4.
   * 
   * Time and space complexity: Time O(n log n); auxiliary space O(log n) for sorting.
   * 
   * Example 1:
   * Input: people = [1,2], limit = 3
   * Output: 1
   * Explanation: Both fit in one boat.
   * 
   * Example 2:
   * Input: people = [3,2,2,1], limit = 3
   * Output: 3
   * Explanation: Pair 1+2, remaining 2 and 3 alone.
   * 
   * Example 3:
   * Input: people = [3,5,3,4], limit = 5
   * Output: 4
   * Explanation: No useful pair with 5 or 4.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/two-pointers.html
   */
  public int numRescueBoats(int[] people, int limit) {
    Arrays.sort(people);
    int left = 0, right = people.length - 1, boats = 0;
    while (left <= right) {
      if (people[left] + people[right] <= limit) left++;
      right--;
      boats++;
    }
    return boats;
  }
}
