package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 25: Sorting. Basic: source questions 1-12. */
public class SortingBasic {

  /*
   * Question 1: Sort Colors
   * 
   * Question: Given an array nums containing only 0, 1, and 2, sort it in-place so equal colors are grouped in the order 0, 1, 2.
   * 
   * Constraints: 1 <= nums.length <= 300; nums[i] is 0, 1, or 2; mutate nums in-place.
   * 
   * Time and space complexity: Time O(n); Space O(1). Dutch national flag partition scans each index at most once.
   * 
   * Example 1:
   * Input: nums = [2,0,2,1,1,0]
   * Output: [0,0,1,1,2,2]
   * Explanation: All 0s move left and all 2s move right.
   * 
   * Example 2:
   * Input: nums = [2,0,1]
   * Output: [0,1,2]
   * Explanation: One pass partitions three values.
   * 
   * Example 3:
   * Input: nums = [1,1,1]
   * Output: [1,1,1]
   * Explanation: Middle values stay in the unknown scan zone until consumed.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public void sortColors(int[] nums) {
    int low = 0;
    int mid = 0;
    int high = nums.length - 1;
    while (mid <= high) {
      if (nums[mid] == 0) swapQ1(nums, low++, mid++);
      else if (nums[mid] == 2) swapQ1(nums, mid, high--);
      else mid++;
    }
  }

  private void swapQ1(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /*
   * Question 2: Merge Sorted Array
   * 
   * Question: Given sorted arrays nums1 and nums2 where nums1 has enough trailing space, merge nums2 into nums1 as one sorted array.
   * 
   * Constraints: nums1.length == m + n; nums2.length == n; 0 <= m,n <= 200; mutate nums1.
   * 
   * Time and space complexity: Time O(m+n); Space O(1). Merge from the back using three pointers.
   * 
   * Example 1:
   * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
   * Output: [1,2,2,3,5,6]
   * Explanation: The arrays merge into one sorted sequence.
   * 
   * Example 2:
   * Input: nums1 = [1], m = 1, nums2 = [], n = 0
   * Output: [1]
   * Explanation: No second array values need to be copied.
   * 
   * Example 3:
   * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
   * Output: [1]
   * Explanation: The destination initially contains only buffer space.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1;
    int j = n - 1;
    int write = m + n - 1;
    while (j >= 0) {
      if (i >= 0 && nums1[i] > nums2[j]) nums1[write--] = nums1[i--];
      else nums1[write--] = nums2[j--];
    }
  }

  /*
   * Question 3: Kth Largest Element in an Array
   * 
   * Question: Given an integer array nums and an integer k, return the kth largest element in the array.
   * 
   * Constraints: 1 <= k <= nums.length <= 100000; -10000 <= nums[i] <= 10000.
   * 
   * Time and space complexity: Average Time O(n), worst O(n^2); Space O(1). Iterative quickselect partitions in-place.
   * 
   * Example 1:
   * Input: nums = [3,2,1,5,6,4], k = 2
   * Output: 5
   * Explanation: The sorted order is [1,2,3,4,5,6].
   * 
   * Example 2:
   * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
   * Output: 4
   * Explanation: Duplicates count as separate elements.
   * 
   * Example 3:
   * Input: nums = [1], k = 1
   * Output: 1
   * Explanation: The only element is the first largest.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int findKthLargest(int[] nums, int k) {
    int target = nums.length - k;
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int pivot = partitionQ3(nums, left, right);
      if (pivot == target) return nums[pivot];
      if (pivot < target) left = pivot + 1;
      else right = pivot - 1;
    }
    return -1;
  }

  private int partitionQ3(int[] nums, int left, int right) {
    int pivotValue = nums[right];
    int store = left;
    for (int i = left; i < right; i++) {
      if (nums[i] <= pivotValue) swapQ3(nums, store++, i);
    }
    swapQ3(nums, store, right);
    return store;
  }

  private void swapQ3(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /*
   * Question 4: Top K Frequent Elements
   * 
   * Question: Given an integer array nums and integer k, return any order of the k most frequent elements.
   * 
   * Constraints: 1 <= nums.length <= 100000; k is in range [1, number of unique values].
   * 
   * Time and space complexity: Time O(n + u); Space O(n + u). Frequency buckets avoid sorting unique values.
   * 
   * Example 1:
   * Input: nums = [1,1,1,2,2,3], k = 2
   * Output: [1,2]
   * Explanation: 1 appears three times and 2 appears twice.
   * 
   * Example 2:
   * Input: nums = [1], k = 1
   * Output: [1]
   * Explanation: Only one unique value exists.
   * 
   * Example 3:
   * Input: nums = [4,4,6,6,7], k = 2
   * Output: any two of [4,6]
   * Explanation: 4 and 6 tie for highest frequency.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequency = new HashMap<>();
    for (int value : nums) frequency.put(value, frequency.getOrDefault(value, 0) + 1);
    List<Integer>[] buckets = new ArrayList[nums.length + 1];
    for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
      int count = entry.getValue();
      if (buckets[count] == null) buckets[count] = new ArrayList<>();
      buckets[count].add(entry.getKey());
    }
    int[] answer = new int[k];
    int index = 0;
    for (int count = buckets.length - 1; count >= 0 && index < k; count--) {
      if (buckets[count] == null) continue;
      for (int value : buckets[count]) {
        answer[index++] = value;
        if (index == k) break;
      }
    }
    return answer;
  }

  /*
   * Question 5: Largest Number
   * 
   * Question: Given a list of non-negative integers, arrange them so they form the largest possible number as a string.
   * 
   * Constraints: 1 <= nums.length <= 100; 0 <= nums[i] <= 1000000000.
   * 
   * Time and space complexity: Time O(n log n * d); Space O(n*d). Sort string forms by a+b versus b+a.
   * 
   * Example 1:
   * Input: nums = [10,2]
   * Output: "210"
   * Explanation: 2 before 10 makes 210 larger than 102.
   * 
   * Example 2:
   * Input: nums = [3,30,34,5,9]
   * Output: "9534330"
   * Explanation: Pairwise concatenation decides the custom order.
   * 
   * Example 3:
   * Input: nums = [0,0]
   * Output: "0"
   * Explanation: Leading zeros collapse to one zero.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public String largestNumber(int[] nums) {
    String[] values = new String[nums.length];
    for (int i = 0; i < nums.length; i++) values[i] = String.valueOf(nums[i]);
    Arrays.sort(values, (a, b) -> (b + a).compareTo(a + b));
    if (values[0].equals("0")) return "0";
    StringBuilder answer = new StringBuilder();
    for (String value : values) answer.append(value);
    return answer.toString();
  }

  /*
   * Question 6: Meeting Rooms
   * 
   * Question: Given meeting time intervals, return true if one person can attend all meetings.
   * 
   * Constraints: 0 <= intervals.length <= 10000; intervals[i] = [start,end] and start < end.
   * 
   * Time and space complexity: Time O(n log n); Space O(n) worst-case temporary storage for Java object-array sorting.
   * 
   * Example 1:
   * Input: intervals = [[0,30],[5,10],[15,20]]
   * Output: false
   * Explanation: The meeting starting at 5 overlaps [0,30].
   * 
   * Example 2:
   * Input: intervals = [[7,10],[2,4]]
   * Output: true
   * Explanation: After sorting, 4 <= 7 so there is no overlap.
   * 
   * Example 3:
   * Input: intervals = [[1,2],[2,3]]
   * Output: true
   * Explanation: Touching endpoints do not overlap.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    for (int i = 1; i < intervals.length; i++) {
      if (intervals[i][0] < intervals[i - 1][1]) return false;
    }
    return true;
  }

  /*
   * Question 7: Meeting Rooms II
   * 
   * Question: Given meeting time intervals, return the minimum number of conference rooms required.
   * 
   * Constraints: 0 <= intervals.length <= 10000; intervals[i] = [start,end] and start < end.
   * 
   * Time and space complexity: Time O(n log n); Space O(n). Sort starts and ends, then sweep with two pointers.
   * 
   * Example 1:
   * Input: intervals = [[0,30],[5,10],[15,20]]
   * Output: 2
   * Explanation: At most two meetings overlap.
   * 
   * Example 2:
   * Input: intervals = [[7,10],[2,4]]
   * Output: 1
   * Explanation: Meetings do not overlap.
   * 
   * Example 3:
   * Input: intervals = [[1,5],[2,6],[3,7]]
   * Output: 3
   * Explanation: All three overlap around time 3.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int minMeetingRooms(int[][] intervals) {
    int n = intervals.length;
    int[] starts = new int[n];
    int[] ends = new int[n];
    for (int i = 0; i < n; i++) {
      starts[i] = intervals[i][0];
      ends[i] = intervals[i][1];
    }
    Arrays.sort(starts);
    Arrays.sort(ends);
    int rooms = 0;
    int best = 0;
    int end = 0;
    for (int start = 0; start < n; start++) {
      while (end < n && ends[end] <= starts[start]) {
        rooms--;
        end++;
      }
      rooms++;
      best = Math.max(best, rooms);
    }
    return best;
  }

  /*
   * Question 8: Merge Intervals
   * 
   * Question: Given intervals, merge all overlapping intervals and return the non-overlapping result.
   * 
   * Constraints: 1 <= intervals.length <= 10000; intervals[i].length == 2; 0 <= start <= end <= 10000.
   * 
   * Time and space complexity: Time O(n log n); Space O(n). Sort by start and scan once.
   * 
   * Example 1:
   * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
   * Output: [[1,6],[8,10],[15,18]]
   * Explanation: [1,3] overlaps [2,6].
   * 
   * Example 2:
   * Input: intervals = [[1,4],[4,5]]
   * Output: [[1,5]]
   * Explanation: Touching intervals merge.
   * 
   * Example 3:
   * Input: intervals = [[1,4],[0,0]]
   * Output: [[0,0],[1,4]]
   * Explanation: Sorting places disjoint intervals in output order.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    List<int[]> merged = new ArrayList<>();
    for (int[] interval : intervals) {
      if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
        merged.add(new int[]{interval[0], interval[1]});
      } else {
        int[] last = merged.get(merged.size() - 1);
        last[1] = Math.max(last[1], interval[1]);
      }
    }
    return merged.toArray(new int[merged.size()][]);
  }

  /*
   * Question 9: Non-overlapping Intervals
   * 
   * Question: Given intervals, return the minimum number of intervals to remove so the rest are non-overlapping.
   * 
   * Constraints: 1 <= intervals.length <= 100000; intervals[i].length == 2; start < end.
   * 
   * Time and space complexity: Time O(n log n); Space O(n) worst-case temporary storage for Java object-array sorting.
   * 
   * Example 1:
   * Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
   * Output: 1
   * Explanation: Remove [1,3].
   * 
   * Example 2:
   * Input: intervals = [[1,2],[1,2],[1,2]]
   * Output: 2
   * Explanation: Only one duplicate interval can remain.
   * 
   * Example 3:
   * Input: intervals = [[1,2],[2,3]]
   * Output: 0
   * Explanation: Touching endpoints do not overlap.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
    int removals = 0;
    int lastEnd = Integer.MIN_VALUE;
    for (int[] interval : intervals) {
      if (interval[0] >= lastEnd) lastEnd = interval[1];
      else removals++;
    }
    return removals;
  }

  /*
   * Question 10: H-Index
   * 
   * Question: Given citations where citations[i] is citations for a paper, return the researcher h-index.
   * 
   * Constraints: 1 <= citations.length <= 5000; 0 <= citations[i] <= 1000.
   * 
   * Time and space complexity: Time O(n); Space O(n). Bucket citation counts capped at n.
   * 
   * Example 1:
   * Input: citations = [3,0,6,1,5]
   * Output: 3
   * Explanation: Three papers have at least three citations.
   * 
   * Example 2:
   * Input: citations = [1,3,1]
   * Output: 1
   * Explanation: Only one paper has at least one citation after thresholding.
   * 
   * Example 3:
   * Input: citations = [0,0]
   * Output: 0
   * Explanation: No paper has at least one citation.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public int hIndex(int[] citations) {
    int n = citations.length;
    int[] buckets = new int[n + 1];
    for (int citation : citations) buckets[Math.min(citation, n)]++;
    int papers = 0;
    for (int h = n; h >= 0; h--) {
      papers += buckets[h];
      if (papers >= h) return h;
    }
    return 0;
  }

  /*
   * Question 11: Wiggle Sort
   * 
   * Question: Given an unsorted array nums, reorder it in-place so nums[0] <= nums[1] >= nums[2] <= nums[3] and so on.
   * 
   * Constraints: 1 <= nums.length <= 50000; values fit in 32-bit signed integer; any valid wiggle order is accepted.
   * 
   * Time and space complexity: Time O(n); Space O(1). One pass fixes each local violation by swapping adjacent values.
   * 
   * Example 1:
   * Input: nums = [3,5,2,1,6,4]
   * Output: a valid wiggle order such as [3,5,1,6,2,4]
   * Explanation: Every odd index is at least its neighbors.
   * 
   * Example 2:
   * Input: nums = [1,2,3,4]
   * Output: [1,3,2,4] or another valid order
   * Explanation: Only adjacent wiggle inequalities must hold.
   * 
   * Example 3:
   * Input: nums = [2,2,2]
   * Output: [2,2,2]
   * Explanation: Equal values satisfy non-strict inequalities.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   */
  public void wiggleSort(int[] nums) {
    for (int i = 1; i < nums.length; i++) {
      boolean shouldRise = i % 2 == 1;
      if (shouldRise && nums[i] < nums[i - 1]) swapQ11(nums, i, i - 1);
      if (!shouldRise && nums[i] > nums[i - 1]) swapQ11(nums, i, i - 1);
    }
  }

  private void swapQ11(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

  /*
   * Question 12: Wiggle Sort II
   * 
   * Question: Given nums, reorder it in-place so nums[0] < nums[1] > nums[2] < nums[3] and so on.
   * 
   * Constraints: 1 <= nums.length <= 50000; a valid answer is guaranteed for the input.
   * 
   * Time and space complexity: Average Time O(n), worst O(n^2); Space O(1). Quickselect median, then virtual-index 3-way partition.
   * 
   * Example 1:
   * Input: nums = [1,5,1,1,6,4]
   * Output: a valid result such as [1,6,1,5,1,4]
   * Explanation: Odd indices are local peaks.
   * 
   * Example 2:
   * Input: nums = [1,3,2,2,3,1]
   * Output: a valid result such as [2,3,1,3,1,2]
   * Explanation: Duplicates are separated around peaks.
   * 
   * Example 3:
   * Input: nums = [1,1,2,2,3,3]
   * Output: any strict wiggle order
   * Explanation: The median split prevents equal neighbors where possible.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/sorting.html
   * Implementation note: Select the median in place to preserve the stated constant auxiliary space.
   */
  public void wiggleSortStrict(int[] nums) {
    int median = selectQ12(nums, nums.length / 2);
    int left = 0;
    int index = 0;
    int right = nums.length - 1;
    while (index <= right) {
      int mapped = mapQ12(index, nums.length);
      if (nums[mapped] > median) {
        swapQ12(nums, mapQ12(left++, nums.length), mapped);
        index++;
      } else if (nums[mapped] < median) {
        swapQ12(nums, mapped, mapQ12(right--, nums.length));
      } else {
        index++;
      }
    }
  }

  private int selectQ12(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
      int pivot = partitionQ12(nums, left, right);
      if (pivot == target) return nums[pivot];
      if (pivot < target) left = pivot + 1;
      else right = pivot - 1;
    }
    return nums[target];
  }

  private int partitionQ12(int[] nums, int left, int right) {
    int pivot = nums[right];
    int store = left;
    for (int i = left; i < right; i++) if (nums[i] <= pivot) swapQ12(nums, store++, i);
    swapQ12(nums, store, right);
    return store;
  }

  private int mapQ12(int index, int n) {
    return (1 + 2 * index) % (n | 1);
  }

  private void swapQ12(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}
