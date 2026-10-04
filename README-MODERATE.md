# Array - Moderate Questions and Solutions

## Topic 1: Arrays & Hashing

#### 13. Encode and Decode Strings - [LeetCode 271](https://leetcode.com/problems/encode-and-decode-strings/)

Design an algorithm to encode a list of strings into one string and decode it back to the original list. Strings may contain any valid characters.

```java
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
```

#### 14. Longest Substring Without Repeating Characters - [LeetCode 3](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

Given a string s, return the length of the longest substring without repeating characters.

```java
public int lengthOfLongestSubstring(String s) {
  Map<Character, Integer> last = new HashMap<>(); int left = 0, best = 0;
  for (int right = 0; right < s.length(); right++) {
    char c = s.charAt(right);
    if (last.containsKey(c)) left = Math.max(left, last.get(c) + 1);
    last.put(c, right); best = Math.max(best, right - left + 1);
  }
  return best;
}
```

#### 15. Minimum Window Substring - [LeetCode 76](https://leetcode.com/problems/minimum-window-substring/)

Given strings s and t, return the minimum window substring of s that contains every character in t including duplicates, or an empty string if none exists.

```java
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
```

#### 16. Find All Anagrams in a String - [LeetCode 438](https://leetcode.com/problems/find-all-anagrams-in-a-string/)

Given strings s and p, return all start indices of p's anagrams in s.

```java
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
```

#### 17. Contiguous Array - [LeetCode 525](https://leetcode.com/problems/contiguous-array/)

Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.

```java
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
```

#### 18. Longest Harmonious Subsequence - [LeetCode 594](https://leetcode.com/problems/longest-harmonious-subsequence/)

Given an integer array nums, return the length of the longest harmonious subsequence where max and min differ by exactly 1.

```java
public int findLHS(int[] nums) {
  Map<Integer, Integer> freq = new HashMap<>();
  for (int n : nums) freq.put(n, freq.getOrDefault(n, 0) + 1);
  int best = 0;
  for (int n : freq.keySet()) if (freq.containsKey(n + 1)) best = Math.max(best, freq.get(n) + freq.get(n + 1));
  return best;
}
```

#### 19. Subarrays Divisible by K - [LeetCode 974](https://leetcode.com/problems/subarray-sums-divisible-by-k/)

Given an integer array nums and an integer k, return the number of non-empty contiguous subarrays whose sum is divisible by k.

```java
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
```

#### 20. Insert Delete GetRandom O(1) - [LeetCode 380](https://leetcode.com/problems/insert-delete-getrandom-o1/)

Design a randomized set supporting insert, remove, and getRandom in average O(1) time.

```java
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
```

## Topic 2: Two Pointers

#### 13. 3Sum Closest - [LeetCode 16](https://leetcode.com/problems/3sum-closest/)

Find the triplet sum closest to target.

```java
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
```

#### 14. 4Sum - [LeetCode 18](https://leetcode.com/problems/4sum/)

Return all unique quadruplets that sum to target.

```java
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
```

#### 15. Sort Colors - [LeetCode 75](https://leetcode.com/problems/sort-colors/)

Sort an array containing only 0, 1, and 2 in-place.

```java
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
```

#### 16. Trapping Rain Water - [LeetCode 42](https://leetcode.com/problems/trapping-rain-water/)

Compute how much rain water can be trapped between bars.

```java
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
```

#### 17. Linked List Cycle II - [LeetCode 142](https://leetcode.com/problems/linked-list-cycle-ii/)

Return the node where a linked list cycle begins.

```java
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
```

#### 18. Remove Nth Node From End of List - [LeetCode 19](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

Remove the nth node from the end of a linked list.

```java
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
```

#### 19. Partition List - [LeetCode 86](https://leetcode.com/problems/partition-list/)

Partition a linked list so nodes less than x come before nodes greater than or equal to x.

```java
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
```

#### 20. Boats to Save People - [LeetCode 881](https://leetcode.com/problems/boats-to-save-people/)

Return the minimum boats needed when each boat carries at most two people under a weight limit.

```java
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
```

## Topic 3: Sliding Window

#### 13. Subarray Product Less Than K - [LeetCode 713](https://leetcode.com/problems/subarray-product-less-than-k/)

Given an array of positive integers nums and integer k, return the number of contiguous subarrays where the product of all elements is strictly less than k.

```java
public int numSubarrayProductLessThanK(int[] nums, int k) {
  if (k <= 1) return 0;
  int left = 0;
  int count = 0;
  long product = 1;
  for (int right = 0; right < nums.length; right++) {
    product *= nums[right];
    while (product >= k) {
      product /= nums[left++];
    }
    count += right - left + 1;
  }
  return count;
}
```

#### 14. Longest Subarray of 1s After Deleting One Element - [LeetCode 1493](https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/)

Given a binary array nums, delete exactly one element and return the longest non-empty subarray containing only 1s.

```java
public int longestSubarray(int[] nums) {
  int left = 0;
  int zeros = 0;
  int best = 0;
  for (int right = 0; right < nums.length; right++) {
    if (nums[right] == 0) zeros++;
    while (zeros > 1) {
      if (nums[left++] == 0) zeros--;
    }
    best = Math.max(best, right - left);
  }
  return best;
}
```

#### 15. Number of Substrings Containing All Three Characters - [LeetCode 1358](https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/)

Given a string s containing only a, b, and c, return the number of substrings containing at least one occurrence of all three characters.

```java
public int numberOfSubstrings(String s) {
  int[] count = new int[3];
  int left = 0;
  int answer = 0;
  for (int right = 0; right < s.length(); right++) {
    count[s.charAt(right) - 'a']++;
    while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
      answer += s.length() - right;
      count[s.charAt(left++) - 'a']--;
    }
  }
  return answer;
}
```

#### 16. Count Number of Nice Subarrays - [LeetCode 1248](https://leetcode.com/problems/count-number-of-nice-subarrays/)

Given nums and k, return the number of continuous subarrays containing exactly k odd numbers.

```java
public int numberOfSubarrays(int[] nums, int k) {
  return atMostQ16(nums, k) - atMostQ16(nums, k - 1);
}

private int atMostQ16(int[] nums, int k) {
  int left = 0;
  int answer = 0;
  for (int right = 0; right < nums.length; right++) {
    if (nums[right] % 2 == 1) k--;
    while (k < 0) {
      if (nums[left++] % 2 == 1) k++;
    }
    answer += right - left + 1;
  }
  return answer;
}
```

#### 17. Max Consecutive Ones - [LeetCode 485](https://leetcode.com/problems/max-consecutive-ones/)

Given a binary array nums, return the maximum number of consecutive 1s in the array.

```java
public int findMaxConsecutiveOnes(int[] nums) {
  int current = 0;
  int best = 0;
  for (int num : nums) {
    current = num == 1 ? current + 1 : 0;
    best = Math.max(best, current);
  }
  return best;
}
```

#### 18. Maximum Points You Can Obtain from Cards - [LeetCode 1423](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/)

Given cardPoints and k, take exactly k cards from either end of the row and return the maximum score.

```java
public int maxScore(int[] cardPoints, int k) {
  int total = 0;
  for (int point : cardPoints) total += point;

  int keep = cardPoints.length - k;
  if (keep == 0) return total;

  int window = 0;
  for (int i = 0; i < keep; i++) window += cardPoints[i];
  int minWindow = window;
  for (int right = keep; right < cardPoints.length; right++) {
    window += cardPoints[right] - cardPoints[right - keep];
    minWindow = Math.min(minWindow, window);
  }
  return total - minWindow;
}
```

#### 19. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit - [LeetCode 1438](https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/)

Given nums and limit, return the length of the longest continuous subarray where the absolute difference between any two elements is at most limit.

```java
public int longestSubarray(int[] nums, int limit) {
  Deque<Integer> maxDeque = new ArrayDeque<>();
  Deque<Integer> minDeque = new ArrayDeque<>();
  int left = 0;
  int best = 0;

  for (int right = 0; right < nums.length; right++) {
    while (!maxDeque.isEmpty() && nums[maxDeque.peekLast()] < nums[right]) maxDeque.pollLast();
    while (!minDeque.isEmpty() && nums[minDeque.peekLast()] > nums[right]) minDeque.pollLast();
    maxDeque.offerLast(right);
    minDeque.offerLast(right);

    while (nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()] > limit) {
      if (maxDeque.peekFirst() == left) maxDeque.pollFirst();
      if (minDeque.peekFirst() == left) minDeque.pollFirst();
      left++;
    }
    best = Math.max(best, right - left + 1);
  }
  return best;
}
```

#### 20. Minimum Operations to Reduce X to Zero - [LeetCode 1658](https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/)

Given nums and x, remove elements only from the left or right end so the removed sum is exactly x. Return the minimum number of removals, or -1 if impossible.

```java
public int minOperations(int[] nums, int x) {
  int total = 0;
  for (int num : nums) total += num;
  int target = total - x;
  if (target < 0) return -1;

  int left = 0;
  int sum = 0;
  int longest = -1;
  for (int right = 0; right < nums.length; right++) {
    sum += nums[right];
    while (sum > target && left <= right) {
      sum -= nums[left++];
    }
    if (sum == target) longest = Math.max(longest, right - left + 1);
  }
  return longest == -1 ? -1 : nums.length - longest;
}
```

## Topic 4: Prefix Sum

#### 13. Subarray Sum Equals K - [LeetCode 560](https://leetcode.com/problems/subarray-sum-equals-k/)

Given an integer array nums and an integer k, return the total number of continuous subarrays whose sum equals k.

```java
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
```

#### 14. Continuous Subarray Sum - [LeetCode 523](https://leetcode.com/problems/continuous-subarray-sum/)

Given nums and k, return true if nums has a continuous subarray of length at least 2 whose sum is a multiple of k.

```java
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
```

#### 15. Subarray Sums Divisible by K - [LeetCode 974](https://leetcode.com/problems/subarray-sums-divisible-by-k/)

Given nums and k, return the number of non-empty subarrays whose sum is divisible by k.

```java
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
```

#### 16. Contiguous Array - [LeetCode 525](https://leetcode.com/problems/contiguous-array/)

Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.

```java
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
```

#### 17. Maximum Size Subarray Sum Equals k - [LeetCode 325](https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/)

Given nums and k, return the maximum length of a subarray that sums to k. Return 0 if no such subarray exists.

```java
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
```

#### 18. Corporate Flight Bookings - [LeetCode 1109](https://leetcode.com/problems/corporate-flight-bookings/)

Given bookings where bookings[i] = [first, last, seats], return seats booked for each flight 1 through n after applying every inclusive range booking.

```java
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
```

#### 19. Car Pooling - [LeetCode 1094](https://leetcode.com/problems/car-pooling/)

Given trips [numPassengers, from, to] and vehicle capacity, return whether all trips can be completed. Passengers are in the car for locations from through to - 1.

```java
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
```

#### 20. Shifting Letters II - [LeetCode 2381](https://leetcode.com/problems/shifting-letters-ii/)

Given a lowercase string s and shifts [start, end, direction], shift each character in every inclusive range backward for 0 or forward for 1. Return the final string.

```java
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
```

## Topic 5: Binary Search

#### 13. Koko Eating Bananas - [LeetCode 875](https://leetcode.com/problems/koko-eating-bananas/)

Koko has piles of bananas and h hours. Each hour she chooses one pile and eats up to k bananas from it. Return the minimum integer speed k such that she can eat all bananas within h hours.

```java
public int minEatingSpeed(int[] piles, int h) {
  int left = 1;
  int right = 0;
  for (int pile : piles) right = Math.max(right, pile);

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (canFinishQ13(piles, h, mid)) {
      right = mid;
    } else {
      left = mid + 1;
    }
  }

  return left;
}

private boolean canFinishQ13(int[] piles, int h, int speed) {
  long hours = 0;
  for (int pile : piles) {
    hours += (pile + speed - 1L) / speed;
    if (hours > h) return false;
  }
  return true;
}
```

#### 14. Capacity To Ship Packages Within D Days - [LeetCode 1011](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)

Given package weights in order and an integer days, return the least ship capacity needed to ship all packages within days days. Packages must be shipped in the given order.

```java
public int shipWithinDays(int[] weights, int days) {
  int left = 0;
  int right = 0;

  for (int weight : weights) {
    left = Math.max(left, weight);
    right += weight;
  }

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (canShipQ14(weights, days, mid)) {
      right = mid;
    } else {
      left = mid + 1;
    }
  }

  return left;
}

private boolean canShipQ14(int[] weights, int days, int capacity) {
  int usedDays = 1;
  int load = 0;

  for (int weight : weights) {
    if (load + weight > capacity) {
      usedDays++;
      load = 0;
    }
    load += weight;
    if (usedDays > days) return false;
  }

  return true;
}
```

#### 15. Split Array Largest Sum - [LeetCode 410](https://leetcode.com/problems/split-array-largest-sum/)

Given an integer array nums and an integer k, split nums into k non-empty contiguous subarrays. Return the minimized largest sum among these subarrays.

```java
public int splitArray(int[] nums, int k) {
  int left = 0;
  int right = 0;

  for (int num : nums) {
    left = Math.max(left, num);
    right += num;
  }

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (canSplitQ15(nums, k, mid)) {
      right = mid;
    } else {
      left = mid + 1;
    }
  }

  return left;
}

private boolean canSplitQ15(int[] nums, int k, int limit) {
  int parts = 1;
  int currentSum = 0;

  for (int num : nums) {
    if (currentSum + num > limit) {
      parts++;
      currentSum = 0;
    }
    currentSum += num;
    if (parts > k) return false;
  }

  return true;
}
```

#### 16. Minimized Maximum of Products Distributed to Any Store - [LeetCode 2064](https://leetcode.com/problems/minimized-maximum-of-products-distributed-to-any-store/)

You have n stores and product quantities where quantities[i] is the count of one product type. A store can receive at most one product type, but any amount of that type. Return the minimum possible maximum number of products assigned to any store.

```java
public int minimizedMaximum(int n, int[] quantities) {
  int left = 1;
  int right = 0;
  for (int quantity : quantities) right = Math.max(right, quantity);

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (canDistributeQ16(n, quantities, mid)) {
      right = mid;
    } else {
      left = mid + 1;
    }
  }

  return left;
}

private boolean canDistributeQ16(int n, int[] quantities, int limit) {
  long stores = 0;
  for (int quantity : quantities) {
    stores += (quantity + limit - 1L) / limit;
    if (stores > n) return false;
  }
  return true;
}
```

#### 17. Magnetic Force Between Two Balls - [LeetCode 1552](https://leetcode.com/problems/magnetic-force-between-two-balls/)

Given basket positions and an integer m, place m balls into baskets so that the minimum magnetic force between any two balls is maximized. Magnetic force is the absolute difference between positions. Return that maximum possible minimum distance.

```java
public int maxDistance(int[] position, int m) {
  Arrays.sort(position);
  int left = 1;
  int right = position[position.length - 1] - position[0];
  int answer = 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (canPlaceQ17(position, m, mid)) {
      answer = mid;
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  return answer;
}

private boolean canPlaceQ17(int[] position, int m, int distance) {
  int count = 1;
  int last = position[0];

  for (int i = 1; i < position.length; i++) {
    if (position[i] - last >= distance) {
      count++;
      last = position[i];
      if (count == m) return true;
    }
  }

  return false;
}
```

#### 18. Aggressive Cows - [GeeksforGeeks](https://www.geeksforgeeks.org/problems/aggressive-cows/1)

Given stall positions and k cows, place the cows in stalls so that the minimum distance between any two cows is as large as possible. Return that largest minimum distance.

```java
public static int aggressiveCows(int[] stalls, int k) {
  Arrays.sort(stalls);
  int left = 1;
  int right = stalls[stalls.length - 1] - stalls[0];
  int answer = 0;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (canPlaceQ18(stalls, k, mid)) {
      answer = mid;
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  return answer;
}

private static boolean canPlaceQ18(int[] stalls, int k, int distance) {
  int cows = 1;
  int last = stalls[0];

  for (int i = 1; i < stalls.length; i++) {
    if (stalls[i] - last >= distance) {
      cows++;
      last = stalls[i];
      if (cows == k) return true;
    }
  }

  return false;
}
```

#### 19. Median of Two Sorted Arrays - [LeetCode 4](https://leetcode.com/problems/median-of-two-sorted-arrays/)

Given two sorted arrays nums1 and nums2, return the median of the two sorted arrays. The optimized solution must run in O(log(m + n)) time.

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
  if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

  int m = nums1.length;
  int n = nums2.length;
  int leftSize = (m + n + 1) / 2;
  int left = 0;
  int right = m;

  while (left <= right) {
    int cut1 = left + (right - left) / 2;
    int cut2 = leftSize - cut1;

    int left1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[cut1 - 1];
    int right1 = cut1 == m ? Integer.MAX_VALUE : nums1[cut1];
    int left2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[cut2 - 1];
    int right2 = cut2 == n ? Integer.MAX_VALUE : nums2[cut2];

    if (left1 <= right2 && left2 <= right1) {
      if ((m + n) % 2 == 1) return Math.max(left1, left2);
      return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
    }

    if (left1 > right2) right = cut1 - 1;
    else left = cut1 + 1;
  }

  return 0.0;
}
```

#### 20. Kth Missing Positive Number - [LeetCode 1539](https://leetcode.com/problems/kth-missing-positive-number/)

Given a strictly increasing positive integer array arr and an integer k, return the kth positive integer missing from arr.

```java
public int findKthPositive(int[] arr, int k) {
  int left = 0;
  int right = arr.length;

  while (left < right) {
    int mid = left + (right - left) / 2;
    int missingBeforeMid = arr[mid] - mid - 1;

    if (missingBeforeMid < k) {
      left = mid + 1;
    } else {
      right = mid;
    }
  }

  return left + k;
}
```

## Topic 23: Matrix

#### 13. Flood Fill - [LeetCode 733](https://leetcode.com/problems/flood-fill/)

Given an image, a starting cell, and a new color, recolor the connected component with the same original color.

```java
public int[][] floodFill(int[][] image, int sr, int sc, int color) {
  int original = image[sr][sc];
  if (original == color) return image;
  Queue<int[]> queue = new ArrayDeque<>();
  queue.offer(new int[]{sr, sc});
  image[sr][sc] = color;
  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int d = 0; d < 4; d++) {
      int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
      if (nr >= 0 && nr < image.length && nc >= 0 && nc < image[0].length && image[nr][nc] == original) {
        image[nr][nc] = color;
        queue.offer(new int[]{nr, nc});
      }
    }
  }
  return image;
}
```

#### 14. 01 Matrix - [LeetCode 542](https://leetcode.com/problems/01-matrix/)

Given a binary matrix, return a matrix where each cell contains the distance to the nearest 0.

```java
public int[][] updateMatrix(int[][] mat) {
  int m = mat.length, n = mat[0].length;
  Queue<int[]> queue = new ArrayDeque<>();
  for (int r = 0; r < m; r++) {
    for (int c = 0; c < n; c++) {
      if (mat[r][c] == 0) queue.offer(new int[]{r, c});
      else mat[r][c] = -1;
    }
  }

  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int d = 0; d < 4; d++) {
      int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
      if (nr >= 0 && nr < m && nc >= 0 && nc < n && mat[nr][nc] == -1) {
        mat[nr][nc] = mat[cell[0]][cell[1]] + 1;
        queue.offer(new int[]{nr, nc});
      }
    }
  }
  return mat;
}
```

#### 15. Rotting Oranges - [LeetCode 994](https://leetcode.com/problems/rotting-oranges/)

Given a grid of empty cells, fresh oranges, and rotten oranges, return minutes until all oranges rot or -1 if impossible.

```java
public int orangesRotting(int[][] grid) {
  Queue<int[]> queue = new ArrayDeque<>();
  int fresh = 0;
  for (int r = 0; r < grid.length; r++) for (int c = 0; c < grid[0].length; c++) {
    if (grid[r][c] == 2) queue.offer(new int[]{r, c});
    if (grid[r][c] == 1) fresh++;
  }

  int minutes = 0;
  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  while (!queue.isEmpty() && fresh > 0) {
    for (int size = queue.size(); size > 0; size--) {
      int[] cell = queue.poll();
      for (int d = 0; d < 4; d++) {
        int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
        if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == 1) {
          grid[nr][nc] = 2;
          fresh--;
          queue.offer(new int[]{nr, nc});
        }
      }
    }
    minutes++;
  }
  return fresh == 0 ? minutes : -1;
}
```

#### 16. Shortest Path in Binary Matrix - [LeetCode 1091](https://leetcode.com/problems/shortest-path-in-binary-matrix/)

Given an n x n binary grid, return the length of the shortest clear path from top-left to bottom-right moving in 8 directions.

```java
public int shortestPathBinaryMatrix(int[][] grid) {
  int n = grid.length;
  if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) return -1;
  Queue<int[]> queue = new ArrayDeque<>();
  queue.offer(new int[]{0, 0});
  grid[0][0] = 1;
  int length = 1;
  int[] dir = {-1, 0, 1};

  while (!queue.isEmpty()) {
    for (int size = queue.size(); size > 0; size--) {
      int[] cell = queue.poll();
      if (cell[0] == n - 1 && cell[1] == n - 1) return length;
      for (int dr : dir) for (int dc : dir) {
        int nr = cell[0] + dr, nc = cell[1] + dc;
        if ((dr != 0 || dc != 0) && nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
          grid[nr][nc] = 1;
          queue.offer(new int[]{nr, nc});
        }
      }
    }
    length++;
  }
  return -1;
}
```

#### 17. Toeplitz Matrix - [LeetCode 766](https://leetcode.com/problems/toeplitz-matrix/)

Given a matrix, return true if every diagonal from top-left to bottom-right has the same value.

```java
public boolean isToeplitzMatrix(int[][] matrix) {
  for (int r = 1; r < matrix.length; r++) {
    for (int c = 1; c < matrix[0].length; c++) {
      if (matrix[r][c] != matrix[r - 1][c - 1]) return false;
    }
  }
  return true;
}
```

#### 18. Valid Sudoku - [LeetCode 36](https://leetcode.com/problems/valid-sudoku/)

Given a partially filled 9 x 9 Sudoku board, return true if it is valid under row, column, and 3 x 3 box constraints.

```java
public boolean isValidSudoku(char[][] board) {
  int[] rows = new int[9];
  int[] cols = new int[9];
  int[] boxes = new int[9];
  for (int r = 0; r < 9; r++) {
    for (int c = 0; c < 9; c++) {
      if (board[r][c] == '.') continue;
      int bit = 1 << (board[r][c] - '1');
      int box = (r / 3) * 3 + c / 3;
      if ((rows[r] & bit) != 0 || (cols[c] & bit) != 0 || (boxes[box] & bit) != 0) return false;
      rows[r] |= bit;
      cols[c] |= bit;
      boxes[box] |= bit;
    }
  }
  return true;
}
```

#### 19. Sudoku Solver - [LeetCode 37](https://leetcode.com/problems/sudoku-solver/)

Given a 9 x 9 Sudoku board, fill it so every row, column, and 3 x 3 box contains digits 1 through 9 exactly once.

```java
private int[] rows = new int[9];
private int[] cols = new int[9];
private int[] boxes = new int[9];

public void solveSudoku(char[][] board) {
  Arrays.fill(rows, 0);
  Arrays.fill(cols, 0);
  Arrays.fill(boxes, 0);
  for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) {
    if (board[r][c] != '.') placeQ19(r, c, board[r][c] - '1');
  }
  solveQ19(board, 0);
}

private boolean solveQ19(char[][] board, int index) {
  if (index == 81) return true;
  int r = index / 9, c = index % 9;
  if (board[r][c] != '.') return solveQ19(board, index + 1);
  int box = (r / 3) * 3 + c / 3;
  for (int digit = 0; digit < 9; digit++) {
    int bit = 1 << digit;
    if ((rows[r] & bit) != 0 || (cols[c] & bit) != 0 || (boxes[box] & bit) != 0) continue;
    board[r][c] = (char) ('1' + digit);
    placeQ19(r, c, digit);
    if (solveQ19(board, index + 1)) return true;
    removeQ19(r, c, digit);
    board[r][c] = '.';
  }
  return false;
}

private void placeQ19(int r, int c, int digit) {
  int bit = 1 << digit, box = (r / 3) * 3 + c / 3;
  rows[r] |= bit; cols[c] |= bit; boxes[box] |= bit;
}

private void removeQ19(int r, int c, int digit) {
  int bit = ~(1 << digit), box = (r / 3) * 3 + c / 3;
  rows[r] &= bit; cols[c] &= bit; boxes[box] &= bit;
}
```

#### 20. Diagonal Traverse - [LeetCode 498](https://leetcode.com/problems/diagonal-traverse/)

Given an m x n matrix, return all elements in diagonal order alternating up-right and down-left.

```java
public int[] findDiagonalOrder(int[][] mat) {
  int m = mat.length, n = mat[0].length;
  int[] answer = new int[m * n];
  int r = 0, c = 0;
  for (int i = 0; i < answer.length; i++) {
    answer[i] = mat[r][c];
    if ((r + c) % 2 == 0) {
      if (c == n - 1) r++;
      else if (r == 0) c++;
      else { r--; c++; }
    } else {
      if (r == m - 1) c++;
      else if (c == 0) r++;
      else { r++; c--; }
    }
  }
  return answer;
}
```

## Topic 24: Strings

#### 13. String Compression - [LeetCode 443](https://leetcode.com/problems/string-compression/)

Given an array of characters, compress it in place by replacing each group with the character followed by its count when count is greater than one, and return the new length.

```java
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
```

#### 14. Reverse Words in a String - [LeetCode 151](https://leetcode.com/problems/reverse-words-in-a-string/)

Given a string s, reverse the order of its words, removing leading/trailing spaces and reducing multiple spaces to one.

```java
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
```

#### 15. Zigzag Conversion - [LeetCode 6](https://leetcode.com/problems/zigzag-conversion/)

Given a string s and number of rows, write the string in zigzag order and then read row by row.

```java
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
```

#### 16. Add Strings - [LeetCode 415](https://leetcode.com/problems/add-strings/)

Given two non-negative integers as strings, return their sum as a string without converting the full inputs to integers.

```java
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
```

#### 17. Decode String - [LeetCode 394](https://leetcode.com/problems/decode-string/)

Given an encoded string with patterns k[encoded_string], return the decoded string.

```java
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
```

#### 18. Basic Calculator II - [LeetCode 227](https://leetcode.com/problems/basic-calculator-ii/)

Given a string expression containing non-negative integers and operators +, -, *, /, evaluate it with integer division truncating toward zero.

```java
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
```

#### 19. Longest Common Prefix - [LeetCode 14](https://leetcode.com/problems/longest-common-prefix/)

Given an array of strings, return the longest common prefix among all strings.

```java
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
```

#### 20. Compare Version Numbers - [LeetCode 165](https://leetcode.com/problems/compare-version-numbers/)

Given two version strings, compare their revision numbers and return -1, 0, or 1.

```java
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
```

## Topic 25: Sorting

#### 13. Sort List - [LeetCode 148](https://leetcode.com/problems/sort-list/)

Given the head of a linked list, sort the list in ascending order and return the sorted head.

```java
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
```

#### 14. Insertion Sort List - [LeetCode 147](https://leetcode.com/problems/insertion-sort-list/)

Given the head of a linked list, sort it using insertion sort and return the sorted head.

```java
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
```

#### 15. Relative Sort Array - [LeetCode 1122](https://leetcode.com/problems/relative-sort-array/)

Given arr1 and arr2 where arr2 has distinct values appearing in arr1, sort arr1 so values in arr2 appear first in arr2 order and remaining values appear ascending.

```java
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
```

#### 16. Sort Characters By Frequency - [LeetCode 451](https://leetcode.com/problems/sort-characters-by-frequency/)

Given a string s, sort its characters in decreasing frequency and return the resulting string.

```java
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
```

#### 17. Maximum Gap - [LeetCode 164](https://leetcode.com/problems/maximum-gap/)

Given an integer array nums, return the maximum difference between two successive elements in sorted order.

```java
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
```

#### 18. Count of Smaller Numbers After Self - [LeetCode 315](https://leetcode.com/problems/count-of-smaller-numbers-after-self/)

Given nums, return counts where counts[i] is the number of smaller elements to the right of nums[i].

```java
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
```

#### 19. Reverse Pairs - [LeetCode 493](https://leetcode.com/problems/reverse-pairs/)

Given nums, return the number of reverse pairs where i < j and nums[i] > 2 * nums[j].

```java
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
```

#### 20. Queue Reconstruction by Height - [LeetCode 406](https://leetcode.com/problems/queue-reconstruction-by-height/)

Given people as [height, k], reconstruct the queue where k is the number of people in front with height at least height.

```java
public int[][] reconstructQueue(int[][] people) {
  Arrays.sort(people, (a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]));
  List<int[]> queue = new ArrayList<>();
  for (int[] person : people) queue.add(person[1], person);
  return queue.toArray(new int[queue.size()][]);
}
```
