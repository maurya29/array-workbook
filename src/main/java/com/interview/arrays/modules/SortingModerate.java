package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 25: Sorting. Moderate: source questions 13-20. */
public class SortingModerate {

  /*
   * Question 13: Sort List
   * 
   * Question: Given the head of a linked list, sort the list in ascending order and return the sorted head.
   * 
   * Constraints: 0 <= number of nodes <= 50000; -100000 <= Node.val <= 100000.
   * 
   * Time and space complexity: Time O(n log n); Space O(1) extra links. Bottom-up merge sort relinks nodes iteratively.
   * 
   * Example 1:
   * Input: head = [4,2,1,3]
   * Output: [1,2,3,4]
   * Explanation: Merge sort orders the linked nodes.
   * 
   * Example 2:
   * Input: head = [-1,5,3,4,0]
   * Output: [-1,0,3,4,5]
   * Explanation: Negative and positive values are both sorted.
   * 
   * Example 3:
   * Input: head = []
   * Output: []
   * Explanation: An empty list stays empty.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public ListNode sortList(ListNode head) {
    int length = 0;
    for (ListNode node = head; node != null; node = node.next) length++;
    ListNode dummy = new ListNode(0, head);
    for (int size = 1; size < length; size *= 2) {
      ListNode current = dummy.next;
      ListNode tail = dummy;
      while (current != null) {
        ListNode left = current;
        ListNode right = splitQ13(left, size);
        current = splitQ13(right, size);
        tail = mergeQ13(left, right, tail);
      }
    }
    return dummy.next;
  }

  private ListNode splitQ13(ListNode head, int size) {
    for (int i = 1; head != null && i < size; i++) head = head.next;
    if (head == null) return null;
    ListNode second = head.next;
    head.next = null;
    return second;
  }

  private ListNode mergeQ13(ListNode a, ListNode b, ListNode tail) {
    while (a != null && b != null) {
      if (a.val <= b.val) {
        tail.next = a;
        a = a.next;
      } else {
        tail.next = b;
        b = b.next;
      }
      tail = tail.next;
    }
    tail.next = (a != null) ? a : b;
    while (tail.next != null) tail = tail.next;
    return tail;
  }

  /*
   * Question 14: Insertion Sort List
   * 
   * Question: Given the head of a linked list, sort it using insertion sort and return the sorted head.
   * 
   * Constraints: 0 <= number of nodes <= 5000; -5000 <= Node.val <= 5000.
   * 
   * Time and space complexity: Time O(n^2); Space O(1). Insert each node into a sorted linked prefix.
   * 
   * Example 1:
   * Input: head = [4,2,1,3]
   * Output: [1,2,3,4]
   * Explanation: Each node is inserted into the sorted prefix.
   * 
   * Example 2:
   * Input: head = [-1,5,3,4,0]
   * Output: [-1,0,3,4,5]
   * Explanation: Nodes are re-linked in ascending order.
   * 
   * Example 3:
   * Input: head = [1]
   * Output: [1]
   * Explanation: A single node is already sorted.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public ListNode insertionSortList(ListNode head) {
    ListNode dummy = new ListNode(0);
    ListNode current = head;
    while (current != null) {
      ListNode next = current.next;
      ListNode prev = dummy;
      while (prev.next != null && prev.next.val < current.val) prev = prev.next;
      current.next = prev.next;
      prev.next = current;
      current = next;
    }
    return dummy.next;
  }

  /*
   * Question 15: Relative Sort Array
   * 
   * Question: Given arr1 and arr2 where arr2 has distinct values appearing in arr1, sort arr1 so values in arr2 appear first in arr2 order and remaining values appear ascending.
   * 
   * Constraints: 1 <= arr1.length, arr2.length <= 1000; 0 <= arr1[i], arr2[i] <= 1000; arr2 values are distinct.
   * 
   * Time and space complexity: Time O(n + range); Space O(range). Count values in the bounded domain and emit in required order.
   * 
   * Example 1:
   * Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
   * Output: [2,2,2,1,4,3,3,9,6,7,19]
   * Explanation: Reference values come first in arr2 order.
   * 
   * Example 2:
   * Input: arr1 = [28,6,22,8,44,17], arr2 = [22,28,8,6]
   * Output: [22,28,8,6,17,44]
   * Explanation: Leftovers 17 and 44 are ascending.
   * 
   * Example 3:
   * Input: arr1 = [1,1,2], arr2 = [1]
   * Output: [1,1,2]
   * Explanation: Repeated reference values are emitted together.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int[] relativeSortArray(int[] arr1, int[] arr2) {
    int[] count = new int[1001];
    for (int value : arr1) count[value]++;
    int index = 0;
    for (int value : arr2) {
      while (count[value]-- > 0) arr1[index++] = value;
    }
    for (int value = 0; value < count.length; value++) {
      while (count[value]-- > 0) arr1[index++] = value;
    }
    return arr1;
  }

  /*
   * Question 16: Sort Characters By Frequency
   * 
   * Question: Given a string s, sort its characters in decreasing frequency and return the resulting string.
   * 
   * Constraints: 1 <= s.length <= 500000; s contains uppercase/lowercase English letters and digits.
   * 
   * Time and space complexity: Time O(n + u); Space O(n + u). Bucket characters by frequency.
   * 
   * Example 1:
   * Input: s = "tree"
   * Output: "eert" or "eetr"
   * Explanation: e appears twice and comes first.
   * 
   * Example 2:
   * Input: s = "cccaaa"
   * Output: "cccaaa" or "aaaccc"
   * Explanation: c and a tie, so either order is valid.
   * 
   * Example 3:
   * Input: s = "Aabb"
   * Output: "bbAa" or "bbaA"
   * Explanation: Uppercase and lowercase are different characters.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public String frequencySort(String s) {
    Map<Character, Integer> frequency = new HashMap<>();
    for (char ch : s.toCharArray()) frequency.put(ch, frequency.getOrDefault(ch, 0) + 1);
    List<Character>[] buckets = new ArrayList[s.length() + 1];
    for (char ch : frequency.keySet()) {
      int count = frequency.get(ch);
      if (buckets[count] == null) buckets[count] = new ArrayList<>();
      buckets[count].add(ch);
    }
    StringBuilder answer = new StringBuilder();
    for (int count = buckets.length - 1; count >= 0; count--) {
      if (buckets[count] == null) continue;
      for (char ch : buckets[count]) {
        for (int i = 0; i < count; i++) answer.append(ch);
      }
    }
    return answer.toString();
  }

  /*
   * Question 17: Maximum Gap
   * 
   * Question: Given an integer array nums, return the maximum difference between two successive elements in sorted order.
   * 
   * Constraints: 1 <= nums.length <= 100000; 0 <= nums[i] <= 1000000000; target linear time and space.
   * 
   * Time and space complexity: Time O(n); Space O(n). Use min/max buckets sized by pigeonhole lower bound.
   * 
   * Example 1:
   * Input: nums = [3,6,9,1]
   * Output: 3
   * Explanation: Sorted order is [1,3,6,9], max gap is 3.
   * 
   * Example 2:
   * Input: nums = [10]
   * Output: 0
   * Explanation: No adjacent pair exists.
   * 
   * Example 3:
   * Input: nums = [1,10000000]
   * Output: 9999999
   * Explanation: The only sorted gap is the answer.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int maximumGap(int[] nums) {
    if (nums.length < 2) return 0;
    int min = nums[0], max = nums[0];
    for (int value : nums) {
      min = Math.min(min, value);
      max = Math.max(max, value);
    }
    if (min == max) return 0;
    int bucketSize = Math.max(1, (max - min) / (nums.length - 1));
    int bucketCount = (max - min) / bucketSize + 1;
    int[] bucketMin = new int[bucketCount];
    int[] bucketMax = new int[bucketCount];
    Arrays.fill(bucketMin, Integer.MAX_VALUE);
    Arrays.fill(bucketMax, Integer.MIN_VALUE);
    for (int value : nums) {
      int bucket = (value - min) / bucketSize;
      bucketMin[bucket] = Math.min(bucketMin[bucket], value);
      bucketMax[bucket] = Math.max(bucketMax[bucket], value);
    }
    int best = 0;
    int previous = min;
    for (int i = 0; i < bucketCount; i++) {
      if (bucketMin[i] == Integer.MAX_VALUE) continue;
      best = Math.max(best, bucketMin[i] - previous);
      previous = bucketMax[i];
    }
    return best;
  }

  /*
   * Question 18: Count of Smaller Numbers After Self
   * 
   * Question: Given nums, return counts where counts[i] is the number of smaller elements to the right of nums[i].
   * 
   * Constraints: 1 <= nums.length <= 100000; -10000 <= nums[i] <= 10000.
   * 
   * Time and space complexity: Time O(n log n); Space O(n). Fenwick tree over coordinate-compressed values scans from right.
   * 
   * Example 1:
   * Input: nums = [5,2,6,1]
   * Output: [2,1,1,0]
   * Explanation: 5 has smaller values 2 and 1 after it.
   * 
   * Example 2:
   * Input: nums = [-1]
   * Output: [0]
   * Explanation: No values appear after the only element.
   * 
   * Example 3:
   * Input: nums = [-1,-1]
   * Output: [0,0]
   * Explanation: Equal values are not smaller.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public List<Integer> countSmaller(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    Map<Integer, Integer> rank = new HashMap<>();
    int id = 1;
    for (int value : sorted) if (!rank.containsKey(value)) rank.put(value, id++);
    int[] tree = new int[id + 1];
    Integer[] answer = new Integer[nums.length];
    for (int i = nums.length - 1; i >= 0; i--) {
      int r = rank.get(nums[i]);
      answer[i] = queryQ18(tree, r - 1);
      updateQ18(tree, r, 1);
    }
    return Arrays.asList(answer);
  }

  private void updateQ18(int[] tree, int index, int delta) {
    while (index < tree.length) {
      tree[index] += delta;
      index += index & -index;
    }
  }

  private int queryQ18(int[] tree, int index) {
    int sum = 0;
    while (index > 0) {
      sum += tree[index];
      index -= index & -index;
    }
    return sum;
  }

  /*
   * Question 19: Reverse Pairs
   * 
   * Question: Given nums, return the number of reverse pairs where i < j and nums[i] > 2 * nums[j].
   * 
   * Constraints: 1 <= nums.length <= 50000; -2^31 <= nums[i] <= 2^31 - 1.
   * 
   * Time and space complexity: Time O(n log n); Space O(n). Fenwick tree with compressed values counts prior values greater than 2*x.
   * 
   * Example 1:
   * Input: nums = [1,3,2,3,1]
   * Output: 2
   * Explanation: The reverse pairs are formed by values 3 with later 1s.
   * 
   * Example 2:
   * Input: nums = [2,4,3,5,1]
   * Output: 3
   * Explanation: Three pairs satisfy nums[i] > 2*nums[j].
   * 
   * Example 3:
   * Input: nums = [-5,-5]
   * Output: 1
   * Explanation: -5 > 2*(-5) is true for the first value before the second.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int reversePairs(int[] nums) {
    TreeSet<Long> values = new TreeSet<>();
    for (int value : nums) {
      values.add((long) value);
      values.add(2L * value);
    }
    Map<Long, Integer> rank = new HashMap<>();
    int id = 1;
    for (long value : values) rank.put(value, id++);
    int[] tree = new int[id + 1];
    int pairs = 0;
    for (int i = 0; i < nums.length; i++) {
      int lessOrEqualDouble = queryQ19(tree, rank.get(2L * nums[i]));
      pairs += i - lessOrEqualDouble;
      updateQ19(tree, rank.get((long) nums[i]), 1);
    }
    return pairs;
  }

  private void updateQ19(int[] tree, int index, int delta) {
    while (index < tree.length) {
      tree[index] += delta;
      index += index & -index;
    }
  }

  private int queryQ19(int[] tree, int index) {
    int sum = 0;
    while (index > 0) {
      sum += tree[index];
      index -= index & -index;
    }
    return sum;
  }

  /*
   * Question 20: Queue Reconstruction by Height
   * 
   * Question: Given people as [height, k], reconstruct the queue where k is the number of people in front with height at least height.
   * 
   * Constraints: 1 <= people.length <= 2000; 0 <= height <= 1000000; 0 <= k < people.length.
   * 
   * Time and space complexity: Time O(n^2 + n log n); Space O(n). Sort by height descending/k ascending and insert at k.
   * 
   * Example 1:
   * Input: people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
   * Output: [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
   * Explanation: Each person has exactly k taller-or-equal people before them.
   * 
   * Example 2:
   * Input: people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
   * Output: [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
   * Explanation: Taller-first insertion satisfies all counts.
   * 
   * Example 3:
   * Input: people = [[7,0]]
   * Output: [[7,0]]
   * Explanation: A single person is already valid.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int[][] reconstructQueue(int[][] people) {
    Arrays.sort(people, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
    List<int[]> queue = new ArrayList<>();
    for (int[] person : people) queue.add(person[1], person);
    return queue.toArray(new int[queue.size()][]);
  }
}
