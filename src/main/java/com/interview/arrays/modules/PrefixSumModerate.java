package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 4: Prefix Sum. Moderate: source questions 13-20. */
public class PrefixSumModerate {

  /*
   * Question 13: Subarray Sum Equals K
   * 
   * Question: Given an integer array nums and an integer k, return the total number of continuous subarrays whose sum equals k.
   * 
   * Constraints: 1 <= nums.length <= 2 * 10^4; -1000 <= nums[i] <= 1000; -10^7 <= k <= 10^7.
   * 
   * Time and space complexity: Time O(n), Space O(n) for prefix frequency map.
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
   * Input: nums = [1,-1,0], k = 0
   * Output: 3
   * Explanation: Repeated prefix sums count [1,-1], [0], and [1,-1,0].
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    freq.put(0, 1);

    int prefix = 0;
    int count = 0;
    for (int num : nums) {
      prefix += num;
      count += freq.getOrDefault(prefix - k, 0);
      freq.put(prefix, freq.getOrDefault(prefix, 0) + 1);
    }
    return count;
  }

  /*
   * Question 14: Continuous Subarray Sum
   * 
   * Question: Given nums and k, return true if nums has a continuous subarray of length at least 2 whose sum is a multiple of k.
   * 
   * Constraints: 1 <= nums.length <= 10^5; 0 <= nums[i] <= 10^9; 1 <= k <= 2^31 - 1.
   * 
   * Time and space complexity: Time O(n), Space O(min(n, k)) for first remainder index map.
   * 
   * Example 1:
   * Input: nums = [23,2,4,6,7], k = 6
   * Output: true
   * Explanation: The subarray [2,4] has sum 6, a multiple of 6.
   * 
   * Example 2:
   * Input: nums = [23,2,6,4,7], k = 6
   * Output: true
   * Explanation: The subarray [23,2,6,4,7] has sum 42, a multiple of 6.
   * 
   * Example 3:
   * Input: nums = [23,2,6,4,7], k = 13
   * Output: false
   * Explanation: No length-at-least-2 subarray has sum divisible by 13.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   * Implementation note: Use long for the intermediate sum to avoid overflow within the stated constraints.
   */
  public boolean checkSubarraySum(int[] nums, int k) {
    Map<Integer, Integer> firstIndex = new HashMap<>();
    firstIndex.put(0, -1);

    int prefix = 0;
    for (int i = 0; i < nums.length; i++) {
      prefix = (int) (((long) prefix + nums[i]) % k);
      Integer first = firstIndex.get(prefix);
      if (first != null) {
        if (i - first >= 2) return true;
      } else {
        firstIndex.put(prefix, i);
      }
    }
    return false;
  }

  /*
   * Question 15: Subarray Sums Divisible by K
   * 
   * Question: Given nums and k, return the number of non-empty subarrays whose sum is divisible by k.
   * 
   * Constraints: 1 <= nums.length <= 3 * 10^4; -10^4 <= nums[i] <= 10^4; 2 <= k <= 10^4.
   * 
   * Time and space complexity: Time O(n), Space O(k) for remainder frequencies.
   * 
   * Example 1:
   * Input: nums = [4,5,0,-2,-3,1], k = 5
   * Output: 7
   * Explanation: Seven subarrays have sums divisible by 5.
   * 
   * Example 2:
   * Input: nums = [5], k = 9
   * Output: 0
   * Explanation: The only subarray sum is 5, not divisible by 9.
   * 
   * Example 3:
   * Input: nums = [-1,2,9], k = 2
   * Output: 2
   * Explanation: Normalized remainders handle the negative prefix correctly.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int subarraysDivByK(int[] nums, int k) {
    int[] freq = new int[k];
    freq[0] = 1;

    int prefix = 0;
    int count = 0;
    for (int num : nums) {
      prefix = ((prefix + num) % k + k) % k;
      count += freq[prefix];
      freq[prefix]++;
    }
    return count;
  }

  /*
   * Question 16: Contiguous Array
   * 
   * Question: Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
   * 
   * Constraints: 1 <= nums.length <= 10^5; nums[i] is 0 or 1.
   * 
   * Time and space complexity: Time O(n), Space O(n) for first balance index map.
   * 
   * Example 1:
   * Input: nums = [0,1]
   * Output: 2
   * Explanation: The full array has one 0 and one 1.
   * 
   * Example 2:
   * Input: nums = [0,1,0]
   * Output: 2
   * Explanation: Either [0,1] or [1,0] is balanced.
   * 
   * Example 3:
   * Input: nums = [0,0,1,0,0,0,1,1]
   * Output: 6
   * Explanation: A longest balanced subarray has equal transformed sum from repeated balance.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int findMaxLength(int[] nums) {
    Map<Integer, Integer> firstIndex = new HashMap<>();
    firstIndex.put(0, -1);

    int balance = 0;
    int best = 0;
    for (int i = 0; i < nums.length; i++) {
      balance += nums[i] == 0 ? -1 : 1;
      if (firstIndex.containsKey(balance)) {
        best = Math.max(best, i - firstIndex.get(balance));
      } else {
        firstIndex.put(balance, i);
      }
    }
    return best;
  }

  /*
   * Question 17: Maximum Size Subarray Sum Equals k
   * 
   * Question: Given nums and k, return the maximum length of a subarray that sums to k. Return 0 if no such subarray exists.
   * 
   * Constraints: 1 <= nums.length <= 2 * 10^5; -10^4 <= nums[i] <= 10^4; -10^9 <= k <= 10^9.
   * 
   * Time and space complexity: Time O(n), Space O(n) for first prefix index map.
   * 
   * Example 1:
   * Input: nums = [1,-1,5,-2,3], k = 3
   * Output: 4
   * Explanation: The subarray [1,-1,5,-2] sums to 3 and has length 4.
   * 
   * Example 2:
   * Input: nums = [-2,-1,2,1], k = 1
   * Output: 2
   * Explanation: The subarray [-1,2] sums to 1.
   * 
   * Example 3:
   * Input: nums = [1,2,3], k = 7
   * Output: 0
   * Explanation: No subarray has sum 7.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   * Implementation note: Use long keys so prefix minus k cannot overflow.
   */
  public int maxSubArrayLen(int[] nums, int k) {
    Map<Long, Integer> firstIndex = new HashMap<>();
    firstIndex.put(0L, -1);

    long prefix = 0;
    int best = 0;
    for (int i = 0; i < nums.length; i++) {
      prefix += nums[i];
      if (firstIndex.containsKey(prefix - k)) {
        best = Math.max(best, i - firstIndex.get(prefix - k));
      }
      firstIndex.putIfAbsent(prefix, i);
    }
    return best;
  }

  /*
   * Question 18: Corporate Flight Bookings
   * 
   * Question: Given bookings where bookings[i] = [first, last, seats], return seats booked for each flight 1 through n after applying every inclusive range booking.
   * 
   * Constraints: 1 <= n <= 2 * 10^4; 1 <= bookings.length <= 2 * 10^4; 1 <= first <= last <= n; 1 <= seats <= 10^4.
   * 
   * Time and space complexity: Time O(b + n), Space O(n) for the difference/result array.
   * 
   * Example 1:
   * Input: bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
   * Output: [10,55,45,25,25]
   * Explanation: Range additions overlap on flights 2 and 3.
   * 
   * Example 2:
   * Input: bookings = [[1,2,10],[2,2,15]], n = 2
   * Output: [10,25]
   * Explanation: Flight 2 receives both bookings.
   * 
   * Example 3:
   * Input: bookings = [[1,1,5]], n = 1
   * Output: [5]
   * Explanation: A single inclusive range updates the only flight.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public int[] corpFlightBookings(int[][] bookings, int n) {
    int[] diff = new int[n + 1];
    for (int[] booking : bookings) {
      int start = booking[0] - 1;
      int end = booking[1];
      int seats = booking[2];
      diff[start] += seats;
      diff[end] -= seats;
    }

    int running = 0;
    int[] ans = new int[n];
    for (int i = 0; i < n; i++) {
      running += diff[i];
      ans[i] = running;
    }
    return ans;
  }

  /*
   * Question 19: Car Pooling
   * 
   * Question: Given trips [numPassengers, from, to] and vehicle capacity, return whether all trips can be completed. Passengers are in the car for locations from through to - 1.
   * 
   * Constraints: 1 <= trips.length <= 1000; 1 <= numPassengers <= 100; 0 <= from < to <= 1000; 1 <= capacity <= 10^5.
   * 
   * Time and space complexity: Time O(t + L), Space O(L), with L <= 1001.
   * 
   * Example 1:
   * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
   * Output: false
   * Explanation: At locations 3 and 4, passenger count becomes 5.
   * 
   * Example 2:
   * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
   * Output: true
   * Explanation: Maximum passenger count is exactly 5.
   * 
   * Example 3:
   * Input: trips = [[2,1,5],[3,5,7]], capacity = 3
   * Output: true
   * Explanation: The first group leaves at 5 before the second group boards from 5.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public boolean carPooling(int[][] trips, int capacity) {
    int[] diff = new int[1002];
    for (int[] trip : trips) {
      diff[trip[1]] += trip[0];
      diff[trip[2]] -= trip[0];
    }

    int passengers = 0;
    for (int location = 0; location <= 1000; location++) {
      passengers += diff[location];
      if (passengers > capacity) return false;
    }
    return true;
  }

  /*
   * Question 20: Shifting Letters II
   * 
   * Question: Given a lowercase string s and shifts [start, end, direction], shift each character in every inclusive range backward for 0 or forward for 1. Return the final string.
   * 
   * Constraints: 1 <= s.length, shifts.length <= 5 * 10^4; shifts[i].length == 3; direction is 0 or 1.
   * 
   * Time and space complexity: Time O(n + q), Space O(n) for difference array and output.
   * 
   * Example 1:
   * Input: s = "abc", shifts = [[0,1,0],[1,2,1],[0,2,1]]
   * Output: "ace"
   * Explanation: Net shifts are [0,1,2], producing a, c, e.
   * 
   * Example 2:
   * Input: s = "dztz", shifts = [[0,0,0],[1,1,1]]
   * Output: "catz"
   * Explanation: d shifts backward to c and z shifts forward to a.
   * 
   * Example 3:
   * Input: s = "a", shifts = [[0,0,0],[0,0,1]]
   * Output: "a"
   * Explanation: The two opposite shifts cancel.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/prefix-sum.html
   */
  public String shiftingLetters(String s, int[][] shifts) {
    int n = s.length();
    int[] diff = new int[n + 1];
    for (int[] shift : shifts) {
      int delta = shift[2] == 1 ? 1 : -1;
      diff[shift[0]] += delta;
      diff[shift[1] + 1] -= delta;
    }

    char[] ans = s.toCharArray();
    int running = 0;
    for (int i = 0; i < n; i++) {
      running += diff[i];
      int offset = ((ans[i] - 'a' + running) % 26 + 26) % 26;
      ans[i] = (char) ('a' + offset);
    }
    return new String(ans);
  }
}
