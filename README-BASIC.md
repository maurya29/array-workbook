# Array - Basic Questions and Solutions

## Topic 1: Arrays & Hashing

#### 1. Two Sum - [LeetCode 1](https://leetcode.com/problems/two-sum/)

Given an integer array nums and an integer target, return the indexes of two distinct elements whose values add up to target. If no pair exists, return {-1, -1}.

```java
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
```

#### 2. Contains Duplicate - [LeetCode 217](https://leetcode.com/problems/contains-duplicate/)

Given an integer array nums, return true if any value appears at least twice. Return false if every element is distinct.

```java
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
```

#### 3. Valid Anagram - [LeetCode 242](https://leetcode.com/problems/valid-anagram/)

Given two strings s and t, return true if t is an anagram of s. Return false otherwise. Both strings contain lowercase English letters.

```java
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
```

#### 4. Group Anagrams - [LeetCode 49](https://leetcode.com/problems/group-anagrams/)

Given an array of strings strs, group the anagrams together. You may return the groups in any order.

```java
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
```

#### 5. Top K Frequent Elements - [LeetCode 347](https://leetcode.com/problems/top-k-frequent-elements/)

Given an integer array nums and an integer k, return the k most frequent elements. The answer may be returned in any order.

```java
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
```

#### 6. Product of Array Except Self - [LeetCode 238](https://leetcode.com/problems/product-of-array-except-self/)

Given an integer array nums, return an array answer where answer[i] equals the product of all elements of nums except nums[i]. Solve it in O(n) time without using division.

```java
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
```

#### 7. Subarray Sum Equals K - [LeetCode 560](https://leetcode.com/problems/subarray-sum-equals-k/)

Given an integer array nums and an integer k, return the total number of contiguous non-empty subarrays whose sum equals k.

```java
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
```

#### 8. Longest Consecutive Sequence - [LeetCode 128](https://leetcode.com/problems/longest-consecutive-sequence/)

Given an unsorted integer array nums, return the length of the longest consecutive elements sequence. The optimized solution must run in O(n) time.

```java
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
```

#### 9. Majority Element - [LeetCode 169](https://leetcode.com/problems/majority-element/)

Given an integer array nums, return the majority element. The majority element appears more than floor(n / 2) times, and it is guaranteed to exist.

```java
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
```

#### 10. Find All Numbers Disappeared in an Array - [LeetCode 448](https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/)

Given an integer array nums of length n where every nums[i] is in the range [1, n], return all numbers in the range [1, n] that do not appear in nums.

```java
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
```

#### 11. Intersection of Two Arrays - [LeetCode 349](https://leetcode.com/problems/intersection-of-two-arrays/)

Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique, and the result may be returned in any order.

```java
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
```

#### 12. First Unique Character in a String - [LeetCode 387](https://leetcode.com/problems/first-unique-character-in-a-string/)

Given a string s, find the first non-repeating character and return its index. If it does not exist, return -1.

```java
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
```

## Topic 2: Two Pointers

#### 1. Two Sum II - Input Array Is Sorted - [LeetCode 167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)

Return 1-indexed positions of two numbers in a sorted array that sum to target.

```java
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
```

#### 2. Valid Palindrome - [LeetCode 125](https://leetcode.com/problems/valid-palindrome/)

Check whether a string is a palindrome after ignoring non-alphanumeric characters and case.

```java
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
```

#### 3. Remove Duplicates from Sorted Array - [LeetCode 26](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

Remove duplicates in-place and return the count of unique values.

```java
public int removeDuplicates(int[] nums) {
  if (nums.length == 0) return 0;
  int write = 1;
  for (int read = 1; read < nums.length; read++) {
    if (nums[read] != nums[write - 1]) nums[write++] = nums[read];
  }
  return write;
}
```

#### 4. Remove Element - [LeetCode 27](https://leetcode.com/problems/remove-element/)

Remove all occurrences of val in-place and return the new length.

```java
public int removeElement(int[] nums, int val) {
  int write = 0;
  for (int read = 0; read < nums.length; read++) {
    if (nums[read] != val) nums[write++] = nums[read];
  }
  return write;
}
```

#### 5. Move Zeroes - [LeetCode 283](https://leetcode.com/problems/move-zeroes/)

Move all zeroes to the end while preserving non-zero order.

```java
public void moveZeroes(int[] nums) {
  int write = 0;
  for (int read = 0; read < nums.length; read++) {
    if (nums[read] != 0) nums[write++] = nums[read];
  }
  while (write < nums.length) nums[write++] = 0;
}
```

#### 6. Squares of a Sorted Array - [LeetCode 977](https://leetcode.com/problems/squares-of-a-sorted-array/)

Return squares of a sorted array in nondecreasing order.

```java
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
```

#### 7. Reverse String - [LeetCode 344](https://leetcode.com/problems/reverse-string/)

Reverse a character array in-place.

```java
public void reverseString(char[] s) {
  int left = 0, right = s.length - 1;
  while (left < right) {
    char temp = s[left];
    s[left++] = s[right];
    s[right--] = temp;
  }
}
```

#### 8. Merge Sorted Array - [LeetCode 88](https://leetcode.com/problems/merge-sorted-array/)

Merge nums2 into nums1 in nondecreasing order.

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
  int i = m - 1, j = n - 1, write = m + n - 1;
  while (j >= 0) {
    if (i >= 0 && nums1[i] > nums2[j]) nums1[write--] = nums1[i--];
    else nums1[write--] = nums2[j--];
  }
}
```

#### 9. Linked List Cycle - [LeetCode 141](https://leetcode.com/problems/linked-list-cycle/)

Return true if a linked list contains a cycle.

```java
public boolean hasCycle(ListNode head) {
  ListNode slow = head, fast = head;
  while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) return true;
  }
  return false;
}
```

#### 10. Middle of the Linked List - [LeetCode 876](https://leetcode.com/problems/middle-of-the-linked-list/)

Return the middle node of a linked list; for even length, return the second middle.

```java
public ListNode middleNode(ListNode head) {
  ListNode slow = head, fast = head;
  while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
  }
  return slow;
}
```

#### 11. Container With Most Water - [LeetCode 11](https://leetcode.com/problems/container-with-most-water/)

Find the maximum water area formed by two vertical lines.

```java
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
```

#### 12. 3Sum - [LeetCode 15](https://leetcode.com/problems/3sum/)

Return all unique triplets that sum to zero.

```java
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
```

## Topic 3: Sliding Window

#### 1. Maximum Average Subarray I - [LeetCode 643](https://leetcode.com/problems/maximum-average-subarray-i/)

Given an integer array nums and an integer k, return the maximum average value among all contiguous subarrays of length k.

```java
public double findMaxAverage(int[] nums, int k) {
  int windowSum = 0;
  for (int i = 0; i < k; i++) {
    windowSum += nums[i];
  }

  int bestSum = windowSum;
  for (int right = k; right < nums.length; right++) {
    windowSum += nums[right] - nums[right - k];
    bestSum = Math.max(bestSum, windowSum);
  }

  return (double) bestSum / k;
}
```

#### 2. Contains Duplicate II - [LeetCode 219](https://leetcode.com/problems/contains-duplicate-ii/)

Given nums and k, return true if there are two distinct indices i and j such that nums[i] == nums[j] and abs(i - j) <= k.

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
  Set<Integer> window = new HashSet<>();

  for (int i = 0; i < nums.length; i++) {
    if (i > k) {
      window.remove(nums[i - k - 1]);
    }
    if (!window.add(nums[i])) {
      return true;
    }
  }

  return false;
}
```

#### 3. Longest Substring Without Repeating Characters - [LeetCode 3](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

Given a string s, return the length of the longest substring without repeating characters.

```java
public int lengthOfLongestSubstring(String s) {
  Map<Character, Integer> lastSeen = new HashMap<>();
  int left = 0;
  int best = 0;

  for (int right = 0; right < s.length(); right++) {
    char current = s.charAt(right);
    if (lastSeen.containsKey(current)) {
      left = Math.max(left, lastSeen.get(current) + 1);
    }
    lastSeen.put(current, right);
    best = Math.max(best, right - left + 1);
  }

  return best;
}
```

#### 4. Minimum Size Subarray Sum - [LeetCode 209](https://leetcode.com/problems/minimum-size-subarray-sum/)

Given an array of positive integers nums and a positive integer target, return the minimum length of a contiguous subarray whose sum is at least target. Return 0 if no such subarray exists.

```java
public int minSubArrayLen(int target, int[] nums) {
  int left = 0;
  int sum = 0;
  int best = Integer.MAX_VALUE;

  for (int right = 0; right < nums.length; right++) {
    sum += nums[right];
    while (sum >= target) {
      best = Math.min(best, right - left + 1);
      sum -= nums[left++];
    }
  }

  return best == Integer.MAX_VALUE ? 0 : best;
}
```

#### 5. Permutation in String - [LeetCode 567](https://leetcode.com/problems/permutation-in-string/)

Given strings s1 and s2, return true if s2 contains a permutation of s1 as a contiguous substring.

```java
public boolean checkInclusion(String s1, String s2) {
  if (s1.length() > s2.length()) {
    return false;
  }

  int[] need = new int[26];
  int[] window = new int[26];
  for (char c : s1.toCharArray()) {
    need[c - 'a']++;
  }

  for (int right = 0; right < s2.length(); right++) {
    window[s2.charAt(right) - 'a']++;
    if (right >= s1.length()) {
      window[s2.charAt(right - s1.length()) - 'a']--;
    }
    if (Arrays.equals(need, window)) {
      return true;
    }
  }

  return false;
}
```

#### 6. Find All Anagrams in a String - [LeetCode 438](https://leetcode.com/problems/find-all-anagrams-in-a-string/)

Given strings s and p, return all start indices of substrings in s that are anagrams of p. Return the answer in any order.

```java
public List<Integer> findAnagrams(String s, String p) {
  List<Integer> answer = new ArrayList<>();
  if (p.length() > s.length()) {
    return answer;
  }

  int[] need = new int[26];
  int[] window = new int[26];
  for (char c : p.toCharArray()) {
    need[c - 'a']++;
  }

  for (int right = 0; right < s.length(); right++) {
    window[s.charAt(right) - 'a']++;
    if (right >= p.length()) {
      window[s.charAt(right - p.length()) - 'a']--;
    }
    if (Arrays.equals(need, window)) {
      answer.add(right - p.length() + 1);
    }
  }

  return answer;
}
```

#### 7. Fruit Into Baskets - [LeetCode 904](https://leetcode.com/problems/fruit-into-baskets/)

Given an integer array fruits where fruits[i] is the type of fruit at tree i, return the length of the longest contiguous subarray containing at most two distinct fruit types.

```java
public int totalFruit(int[] fruits) {
  Map<Integer, Integer> count = new HashMap<>();
  int left = 0;
  int best = 0;

  for (int right = 0; right < fruits.length; right++) {
    count.put(fruits[right], count.getOrDefault(fruits[right], 0) + 1);

    while (count.size() > 2) {
      int fruit = fruits[left++];
      count.put(fruit, count.get(fruit) - 1);
      if (count.get(fruit) == 0) {
        count.remove(fruit);
      }
    }

    best = Math.max(best, right - left + 1);
  }

  return best;
}
```

#### 8. Max Consecutive Ones III - [LeetCode 1004](https://leetcode.com/problems/max-consecutive-ones-iii/)

Given a binary array nums and an integer k, return the maximum number of consecutive 1s after flipping at most k zeroes.

```java
public int longestOnes(int[] nums, int k) {
  int left = 0;
  int zeroes = 0;
  int best = 0;

  for (int right = 0; right < nums.length; right++) {
    if (nums[right] == 0) {
      zeroes++;
    }
    while (zeroes > k) {
      if (nums[left++] == 0) {
        zeroes--;
      }
    }
    best = Math.max(best, right - left + 1);
  }

  return best;
}
```

#### 9. Longest Repeating Character Replacement - [LeetCode 424](https://leetcode.com/problems/longest-repeating-character-replacement/)

Given a string s and integer k, return the length of the longest substring that can be turned into all the same character by replacing at most k characters.

```java
public int characterReplacement(String s, int k) {
  int[] count = new int[26];
  int left = 0;
  int maxFrequency = 0;
  int best = 0;

  for (int right = 0; right < s.length(); right++) {
    int index = s.charAt(right) - 'A';
    count[index]++;
    maxFrequency = Math.max(maxFrequency, count[index]);

    while (right - left + 1 - maxFrequency > k) {
      count[s.charAt(left) - 'A']--;
      left++;
    }

    best = Math.max(best, right - left + 1);
  }

  return best;
}
```

#### 10. Grumpy Bookstore Owner - [LeetCode 1052](https://leetcode.com/problems/grumpy-bookstore-owner/)

Given customers, grumpy, and minutes, return the maximum number of satisfied customers if the owner can suppress grumpiness for exactly one consecutive window of length minutes.

```java
public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
  int baseSatisfied = 0;
  int windowGain = 0;

  for (int i = 0; i < customers.length; i++) {
    if (grumpy[i] == 0) {
      baseSatisfied += customers[i];
    } else if (i < minutes) {
      windowGain += customers[i];
    }
  }

  int bestGain = windowGain;
  for (int right = minutes; right < customers.length; right++) {
    if (grumpy[right] == 1) {
      windowGain += customers[right];
    }
    if (grumpy[right - minutes] == 1) {
      windowGain -= customers[right - minutes];
    }
    bestGain = Math.max(bestGain, windowGain);
  }

  return baseSatisfied + bestGain;
}
```

#### 11. Sliding Window Maximum - [LeetCode 239](https://leetcode.com/problems/sliding-window-maximum/)

Given nums and k, return an array containing the maximum value in every contiguous window of size k.

```java
public int[] maxSlidingWindow(int[] nums, int k) {
  Deque<Integer> deque = new ArrayDeque<>();
  int[] answer = new int[nums.length - k + 1];

  for (int right = 0; right < nums.length; right++) {
    while (!deque.isEmpty() && deque.peekFirst() <= right - k) {
      deque.pollFirst();
    }
    while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
      deque.pollLast();
    }
    deque.offerLast(right);

    if (right >= k - 1) {
      answer[right - k + 1] = nums[deque.peekFirst()];
    }
  }

  return answer;
}
```

#### 12. Minimum Window Substring - [LeetCode 76](https://leetcode.com/problems/minimum-window-substring/)

Given strings s and t, return the smallest substring of s that contains every character of t including duplicates. Return an empty string if no such window exists.

```java
public String minWindow(String s, String t) {
  int[] need = new int[128];
  for (char c : t.toCharArray()) {
    need[c]++;
  }

  int missing = t.length();
  int left = 0;
  int start = 0;
  int best = Integer.MAX_VALUE;

  for (int right = 0; right < s.length(); right++) {
    if (need[s.charAt(right)]-- > 0) {
      missing--;
    }

    while (missing == 0) {
      if (right - left + 1 < best) {
        best = right - left + 1;
        start = left;
      }
      if (++need[s.charAt(left++)] > 0) {
        missing++;
      }
    }
  }

  return best == Integer.MAX_VALUE ? "" : s.substring(start, start + best);
}
```

## Topic 4: Prefix Sum

#### 1. Running Sum of 1d Array - [LeetCode 1480](https://leetcode.com/problems/running-sum-of-1d-array/)

Given an integer array nums, return an array where answer[i] equals nums[0] + nums[1] + ... + nums[i].

```java
public int[] runningSum(int[] nums) {
  for (int i = 1; i < nums.length; i++) {
    nums[i] += nums[i - 1];
  }
  return nums;
}
```

#### 2. Find Pivot Index - [LeetCode 724](https://leetcode.com/problems/find-pivot-index/)

Given nums, return the leftmost index where the sum of all elements to the left equals the sum of all elements to the right. Return -1 if no such index exists.

```java
public int pivotIndex(int[] nums) {
  int total = 0;
  for (int num : nums) {
    total += num;
  }

  int left = 0;
  for (int i = 0; i < nums.length; i++) {
    int right = total - left - nums[i];
    if (left == right) return i;
    left += nums[i];
  }
  return -1;
}
```

#### 3. Find the Highest Altitude - [LeetCode 1732](https://leetcode.com/problems/find-the-highest-altitude/)

A biker starts at altitude 0. Given gain[i] as the net altitude change between point i and i + 1, return the highest altitude reached.

```java
public int largestAltitude(int[] gain) {
  int altitude = 0;
  int highest = 0;
  for (int change : gain) {
    altitude += change;
    highest = Math.max(highest, altitude);
  }
  return highest;
}
```

#### 4. Minimum Value to Get Positive Step by Step Sum - [LeetCode 1413](https://leetcode.com/problems/minimum-value-to-get-positive-step-by-step-sum/)

Given an integer array nums, choose the minimum positive startValue so that the running sum startValue + nums[0] + ... + nums[i] is always at least 1.

```java
public int minStartValue(int[] nums) {
  int prefix = 0;
  int minPrefix = 0;
  for (int num : nums) {
    prefix += num;
    minPrefix = Math.min(minPrefix, prefix);
  }
  return 1 - minPrefix;
}
```

#### 5. Range Sum Query - Immutable - [LeetCode 303](https://leetcode.com/problems/range-sum-query-immutable/)

Design NumArray so that sumRange(left, right) returns the sum of nums[left] through nums[right] for many immutable range-sum queries.

```java
public static class NumArray {
  private final int[] prefix;

  public NumArray(int[] nums) {
    prefix = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
      prefix[i + 1] = prefix[i] + nums[i];
    }
  }

  public int sumRange(int left, int right) {
    return prefix[right + 1] - prefix[left];
  }
}
```

#### 6. Range Sum Query 2D - Immutable - [LeetCode 304](https://leetcode.com/problems/range-sum-query-2d-immutable/)

Design NumMatrix so that sumRegion(row1, col1, row2, col2) returns the sum of a rectangle in an immutable matrix.

```java
public static class NumMatrix {
  private final long[][] prefix;

  public NumMatrix(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    prefix = new long[rows + 1][cols + 1];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        prefix[row + 1][col + 1] = matrix[row][col]
            + prefix[row][col + 1]
            + prefix[row + 1][col]
            - prefix[row][col];
      }
    }
  }

  public long sumRegion(int row1, int col1, int row2, int col2) {
    return prefix[row2 + 1][col2 + 1]
        - prefix[row1][col2 + 1]
        - prefix[row2 + 1][col1]
        + prefix[row1][col1];
  }
}
```

#### 7. Matrix Block Sum - [LeetCode 1314](https://leetcode.com/problems/matrix-block-sum/)

Given a matrix mat and integer k, return answer where answer[i][j] is the sum of all mat[r][c] with |r - i| <= k and |c - j| <= k inside matrix bounds.

```java
public int[][] matrixBlockSum(int[][] mat, int k) {
  int rows = mat.length;
  int cols = mat[0].length;
  int[][] prefix = new int[rows + 1][cols + 1];

  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      prefix[row + 1][col + 1] = mat[row][col]
          + prefix[row][col + 1]
          + prefix[row + 1][col]
          - prefix[row][col];
    }
  }

  int[][] ans = new int[rows][cols];
  for (int row = 0; row < rows; row++) {
    for (int col = 0; col < cols; col++) {
      int r1 = Math.max(0, row - k);
      int c1 = Math.max(0, col - k);
      int r2 = Math.min(rows - 1, row + k);
      int c2 = Math.min(cols - 1, col + k);
      ans[row][col] = sumQ7(prefix, r1, c1, r2, c2);
    }
  }
  return ans;
}

private int sumQ7(int[][] prefix, int r1, int c1, int r2, int c2) {
  return prefix[r2 + 1][c2 + 1]
      - prefix[r1][c2 + 1]
      - prefix[r2 + 1][c1]
      + prefix[r1][c1];
}
```

#### 8. Left and Right Sum Differences - [LeetCode 2574](https://leetcode.com/problems/left-and-right-sum-differences/)

Given nums, return an array answer where answer[i] is the absolute difference between the sum of elements left of i and the sum of elements right of i.

```java
public int[] leftRightDifference(int[] nums) {
  int total = 0;
  for (int num : nums) total += num;

  int[] ans = new int[nums.length];
  int left = 0;
  for (int i = 0; i < nums.length; i++) {
    int right = total - left - nums[i];
    ans[i] = Math.abs(left - right);
    left += nums[i];
  }
  return ans;
}
```

#### 9. Find the Middle Index in Array - [LeetCode 1991](https://leetcode.com/problems/find-the-middle-index-in-array/)

Given nums, return the leftmost middleIndex where the sum strictly before the index equals the sum strictly after it. Return -1 if none exists.

```java
public int findMiddleIndex(int[] nums) {
  int total = 0;
  for (int num : nums) total += num;

  int left = 0;
  for (int i = 0; i < nums.length; i++) {
    int right = total - left - nums[i];
    if (left == right) return i;
    left += nums[i];
  }
  return -1;
}
```

#### 10. Sum of All Odd Length Subarrays - [LeetCode 1588](https://leetcode.com/problems/sum-of-all-odd-length-subarrays/)

Given an array arr of positive integers, return the sum of all possible odd-length subarrays.

```java
public int sumOddLengthSubarrays(int[] arr) {
  int sum = 0;
  for (int i = 0; i < arr.length; i++) {
    int containing = (i + 1) * (arr.length - i);
    sum += ((containing + 1) / 2) * arr[i];
  }
  return sum;
}
```

#### 11. Number of Ways to Split Array - [LeetCode 2270](https://leetcode.com/problems/number-of-ways-to-split-array/)

Given a 0-indexed integer array nums, count split positions i where the left part nums[0..i] and right part nums[i+1..n-1] are both non-empty and leftSum >= rightSum.

```java
public int waysToSplitArray(int[] nums) {
  long total = 0;
  for (int num : nums) total += num;

  int ways = 0;
  long left = 0;
  for (int i = 0; i < nums.length - 1; i++) {
    left += nums[i];
    long right = total - left;
    if (left >= right) ways++;
  }
  return ways;
}
```

#### 12. Maximum Population Year - [LeetCode 1854](https://leetcode.com/problems/maximum-population-year/)

Given birth and death years where a person is alive from birth through death - 1, return the earliest year with the maximum population.

```java
public int maximumPopulation(int[][] logs) {
  int[] diff = new int[102];
  for (int[] log : logs) {
    diff[log[0] - 1950]++;
    diff[log[1] - 1950]--;
  }

  int bestYear = 1950;
  int bestPopulation = 0;
  int population = 0;
  for (int i = 0; i <= 100; i++) {
    population += diff[i];
    if (population > bestPopulation) {
      bestPopulation = population;
      bestYear = 1950 + i;
    }
  }
  return bestYear;
}
```

## Topic 5: Binary Search

#### 1. Binary Search - [LeetCode 704](https://leetcode.com/problems/binary-search/)

Given an array nums sorted in strictly increasing order and an integer target, return the index of target if it is present. If target is not present, return -1. The required optimized solution must run in O(log n) time.

```java
public int search(int[] nums, int target) {
  int left = 0;
  int right = nums.length - 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] == target) return mid;
    if (nums[mid] < target) {
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  return -1;
}
```

#### 2. First Bad Version - [LeetCode 278](https://leetcode.com/problems/first-bad-version/)

You are given versions 1 through n and an API isBadVersion(version). All versions after a bad version are also bad. Return the first bad version while calling the API as few times as possible.

```java
public int firstBadVersion(int n, java.util.function.IntPredicate isBadVersion) {
  int left = 1, right = n;
  while (left < right) {
    int mid = left + (right - left) / 2;
    if (isBadVersion.test(mid)) right = mid;
    else left = mid + 1;
  }
  return left;
}
```

#### 3. Search Insert Position - [LeetCode 35](https://leetcode.com/problems/search-insert-position/)

Given a sorted array of distinct integers nums and an integer target, return the index if target is found. Otherwise return the index where target should be inserted to keep nums sorted. The optimized solution must run in O(log n) time.

```java
public int searchInsert(int[] nums, int target) {
  int left = 0;
  int right = nums.length;

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] < target) {
      left = mid + 1;
    } else {
      right = mid;
    }
  }

  return left;
}
```

#### 4. Find First and Last Position of Element in Sorted Array - [LeetCode 34](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

Given an array nums sorted in non-decreasing order and an integer target, return the first and last index of target. If target is not found, return [-1, -1]. The optimized solution must run in O(log n) time.

```java
public int[] searchRange(int[] nums, int target) {
  int first = findFirstQ4(nums, target);
  int last = findLastQ4(nums, target);
  return new int[] {first, last};
}

private int findFirstQ4(int[] nums, int target) {
  int left = 0;
  int right = nums.length - 1;
  int answer = -1;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] >= target) {
      if (nums[mid] == target) answer = mid;
      right = mid - 1;
    } else {
      left = mid + 1;
    }
  }

  return answer;
}

private int findLastQ4(int[] nums, int target) {
  int left = 0;
  int right = nums.length - 1;
  int answer = -1;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] <= target) {
      if (nums[mid] == target) answer = mid;
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  return answer;
}
```

#### 5. Sqrt(x) - [LeetCode 69](https://leetcode.com/problems/sqrtx/)

Given a non-negative integer x, return the floor of its square root. You must not use built-in exponent functions or square-root functions.

```java
public int mySqrt(int x) {
  if (x < 2) return x;

  int left = 1;
  int right = x / 2;
  int answer = 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;
    long square = (long) mid * mid;

    if (square <= x) {
      answer = mid;
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  return answer;
}
```

#### 6. Valid Perfect Square - [LeetCode 367](https://leetcode.com/problems/valid-perfect-square/)

Given a positive integer num, return true if num is a perfect square. Otherwise return false. You must not use built-in square-root functions.

```java
public boolean isPerfectSquare(int num) {
  if (num == 1) return true;

  int left = 1;
  int right = num / 2;

  while (left <= right) {
    int mid = left + (right - left) / 2;
    long square = (long) mid * mid;

    if (square == num) return true;
    if (square < num) left = mid + 1;
    else right = mid - 1;
  }

  return false;
}
```

#### 7. Search in Rotated Sorted Array - [LeetCode 33](https://leetcode.com/problems/search-in-rotated-sorted-array/)

Given a sorted array of distinct integers that has been rotated at an unknown pivot and an integer target, return the index of target if it exists. Otherwise return -1. The optimized solution must run in O(log n) time.

```java
public int searchRotated(int[] nums, int target) {
  int left = 0;
  int right = nums.length - 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return mid;

    if (nums[left] <= nums[mid]) {
      if (nums[left] <= target && target < nums[mid]) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    } else {
      if (nums[mid] < target && target <= nums[right]) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
  }

  return -1;
}
```

#### 8. Find Minimum in Rotated Sorted Array - [LeetCode 153](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)

Given a sorted array of unique integers rotated between 1 and n times, return the minimum element. The optimized solution must run in O(log n) time.

```java
public int findMin(int[] nums) {
  int left = 0;
  int right = nums.length - 1;

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] > nums[right]) {
      left = mid + 1;
    } else {
      right = mid;
    }
  }

  return nums[left];
}
```

#### 9. Find Peak Element - [LeetCode 162](https://leetcode.com/problems/find-peak-element/)

A peak element is an element strictly greater than its neighbors. Given an integer array nums where adjacent values are not equal, return the index of any peak element. Treat nums[-1] and nums[n] as negative infinity. The optimized solution must run in O(log n) time.

```java
public int findPeakElement(int[] nums) {
  int left = 0;
  int right = nums.length - 1;

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (nums[mid] < nums[mid + 1]) {
      left = mid + 1;
    } else {
      right = mid;
    }
  }

  return left;
}
```

#### 10. Peak Index in a Mountain Array - [LeetCode 852](https://leetcode.com/problems/peak-index-in-a-mountain-array/)

Given a mountain array arr, return the index of its peak. A mountain array strictly increases up to one peak and then strictly decreases.

```java
public int peakIndexInMountainArray(int[] arr) {
  int left = 0;
  int right = arr.length - 1;

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (arr[mid] < arr[mid + 1]) {
      left = mid + 1;
    } else {
      right = mid;
    }
  }

  return left;
}
```

#### 11. Single Element in a Sorted Array - [LeetCode 540](https://leetcode.com/problems/single-element-in-a-sorted-array/)

Given a sorted array where every element appears exactly twice except one element that appears once, return the single element. The optimized solution must run in O(log n) time and O(1) space.

```java
public int singleNonDuplicate(int[] nums) {
  int left = 0;
  int right = nums.length - 1;

  while (left < right) {
    int mid = left + (right - left) / 2;
    if (mid % 2 == 1) mid--;

    if (nums[mid] == nums[mid + 1]) {
      left = mid + 2;
    } else {
      right = mid;
    }
  }

  return nums[left];
}
```

#### 12. Find Smallest Letter Greater Than Target - [LeetCode 744](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)

Given a sorted array of lowercase letters and a target letter, return the smallest letter in the array that is strictly greater than target. The letters wrap around, so if no larger letter exists, return letters[0].

```java
public char nextGreatestLetter(char[] letters, char target) {
  int left = 0;
  int right = letters.length;

  while (left < right) {
    int mid = left + (right - left) / 2;

    if (letters[mid] <= target) {
      left = mid + 1;
    } else {
      right = mid;
    }
  }

  return letters[left % letters.length];
}
```

## Topic 23: Matrix

#### 1. Set Matrix Zeroes - [LeetCode 73](https://leetcode.com/problems/set-matrix-zeroes/)

Given an m x n integer matrix, if an element is 0, set its entire row and column to 0 in place.

```java
public void setZeroes(int[][] matrix) {
  int m = matrix.length;
  int n = matrix[0].length;
  boolean firstRowZero = false;
  boolean firstColZero = false;

  for (int c = 0; c < n; c++) if (matrix[0][c] == 0) firstRowZero = true;
  for (int r = 0; r < m; r++) if (matrix[r][0] == 0) firstColZero = true;

  for (int r = 1; r < m; r++) {
    for (int c = 1; c < n; c++) {
      if (matrix[r][c] == 0) {
        matrix[r][0] = 0;
        matrix[0][c] = 0;
      }
    }
  }

  for (int r = 1; r < m; r++) {
    for (int c = 1; c < n; c++) {
      if (matrix[r][0] == 0 || matrix[0][c] == 0) matrix[r][c] = 0;
    }
  }
  if (firstRowZero) for (int c = 0; c < n; c++) matrix[0][c] = 0;
  if (firstColZero) for (int r = 0; r < m; r++) matrix[r][0] = 0;
}
```

#### 2. Spiral Matrix - [LeetCode 54](https://leetcode.com/problems/spiral-matrix/)

Given an m x n matrix, return all elements of the matrix in spiral order.

```java
public List<Integer> spiralOrder(int[][] matrix) {
  List<Integer> answer = new ArrayList<>();
  int top = 0, bottom = matrix.length - 1;
  int left = 0, right = matrix[0].length - 1;

  while (top <= bottom && left <= right) {
    for (int c = left; c <= right; c++) answer.add(matrix[top][c]);
    top++;
    for (int r = top; r <= bottom; r++) answer.add(matrix[r][right]);
    right--;
    if (top <= bottom) {
      for (int c = right; c >= left; c--) answer.add(matrix[bottom][c]);
      bottom--;
    }
    if (left <= right) {
      for (int r = bottom; r >= top; r--) answer.add(matrix[r][left]);
      left++;
    }
  }
  return answer;
}
```

#### 3. Spiral Matrix II - [LeetCode 59](https://leetcode.com/problems/spiral-matrix-ii/)

Given an integer n, generate an n x n matrix filled with numbers from 1 to n^2 in spiral order.

```java
public int[][] generateMatrix(int n) {
  int[][] matrix = new int[n][n];
  int top = 0, bottom = n - 1;
  int left = 0, right = n - 1;
  int value = 1;

  while (top <= bottom && left <= right) {
    for (int c = left; c <= right; c++) matrix[top][c] = value++;
    top++;
    for (int r = top; r <= bottom; r++) matrix[r][right] = value++;
    right--;
    for (int c = right; c >= left && top <= bottom; c--) matrix[bottom][c] = value++;
    bottom--;
    for (int r = bottom; r >= top && left <= right; r--) matrix[r][left] = value++;
    left++;
  }
  return matrix;
}
```

#### 4. Rotate Image - [LeetCode 48](https://leetcode.com/problems/rotate-image/)

Given an n x n matrix, rotate it 90 degrees clockwise in place.

```java
public void rotate(int[][] matrix) {
  int n = matrix.length;
  for (int r = 0; r < n; r++) {
    for (int c = r + 1; c < n; c++) {
      int temp = matrix[r][c];
      matrix[r][c] = matrix[c][r];
      matrix[c][r] = temp;
    }
  }

  for (int r = 0; r < n; r++) {
    int left = 0, right = n - 1;
    while (left < right) {
      int temp = matrix[r][left];
      matrix[r][left++] = matrix[r][right];
      matrix[r][right--] = temp;
    }
  }
}
```

#### 5. Search a 2D Matrix - [LeetCode 74](https://leetcode.com/problems/search-a-2d-matrix/)

Given a matrix where each row is sorted and the first integer of each row is greater than the last integer of the previous row, return whether target exists.

```java
public boolean searchMatrix(int[][] matrix, int target) {
  int m = matrix.length;
  int n = matrix[0].length;
  int left = 0;
  int right = m * n - 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;
    int value = matrix[mid / n][mid % n];
    if (value == target) return true;
    if (value < target) left = mid + 1;
    else right = mid - 1;
  }
  return false;
}
```

#### 6. Search a 2D Matrix II - [LeetCode 240](https://leetcode.com/problems/search-a-2d-matrix-ii/)

Given an m x n matrix sorted ascending left-to-right in each row and top-to-bottom in each column, return whether target exists.

```java
public boolean searchMatrixII(int[][] matrix, int target) {
  int r = 0;
  int c = matrix[0].length - 1;
  while (r < matrix.length && c >= 0) {
    int value = matrix[r][c];
    if (value == target) return true;
    if (value > target) c--;
    else r++;
  }
  return false;
}
```

#### 7. Game of Life - [LeetCode 289](https://leetcode.com/problems/game-of-life/)

Given a board of live and dead cells, update it to the next Game of Life state in place using the standard four rules.

```java
public void gameOfLife(int[][] board) {
  int m = board.length, n = board[0].length;
  for (int r = 0; r < m; r++) {
    for (int c = 0; c < n; c++) {
      int live = liveNeighborsQ7(board, r, c);
      if (board[r][c] == 1 && (live < 2 || live > 3)) board[r][c] = -1;
      if (board[r][c] == 0 && live == 3) board[r][c] = 2;
    }
  }
  for (int r = 0; r < m; r++) {
    for (int c = 0; c < n; c++) board[r][c] = board[r][c] > 0 ? 1 : 0;
  }
}

private int liveNeighborsQ7(int[][] board, int r, int c) {
  int count = 0;
  for (int dr = -1; dr <= 1; dr++) for (int dc = -1; dc <= 1; dc++) {
    if (dr == 0 && dc == 0) continue;
    int nr = r + dr, nc = c + dc;
    if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && Math.abs(board[nr][nc]) == 1) count++;
  }
  return count;
}
```

#### 8. Word Search - [LeetCode 79](https://leetcode.com/problems/word-search/)

Given an m x n board of characters and a word, return true if the word exists by moving horizontally or vertically without reusing a cell.

```java
public boolean exist(char[][] board, String word) {
  if (word.length() > board.length * board[0].length) return false;
  for (int r = 0; r < board.length; r++) {
    for (int c = 0; c < board[0].length; c++) {
      if (dfsQ8(board, word, r, c, 0)) return true;
    }
  }
  return false;
}

private boolean dfsQ8(char[][] board, String word, int r, int c, int index) {
  if (index == word.length()) return true;
  if (r < 0 || r == board.length || c < 0 || c == board[0].length || board[r][c] != word.charAt(index)) return false;
  char saved = board[r][c];
  board[r][c] = '#';
  boolean found = dfsQ8(board, word, r + 1, c, index + 1) || dfsQ8(board, word, r - 1, c, index + 1) || dfsQ8(board, word, r, c + 1, index + 1) || dfsQ8(board, word, r, c - 1, index + 1);
  board[r][c] = saved;
  return found;
}
```

#### 9. Number of Islands - [LeetCode 200](https://leetcode.com/problems/number-of-islands/)

Given a grid of 1s and 0s, return the number of islands where land is connected horizontally or vertically.

```java
public int numIslands(char[][] grid) {
  int count = 0;
  for (int r = 0; r < grid.length; r++) {
    for (int c = 0; c < grid[0].length; c++) {
      if (grid[r][c] == '1') {
        count++;
        sinkQ9(grid, r, c);
      }
    }
  }
  return count;
}

private void sinkQ9(char[][] grid, int sr, int sc) {
  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  Queue<int[]> queue = new ArrayDeque<>();
  queue.offer(new int[]{sr, sc});
  grid[sr][sc] = '0';
  while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int d = 0; d < 4; d++) {
      int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
      if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == '1') {
        grid[nr][nc] = '0';
        queue.offer(new int[]{nr, nc});
      }
    }
  }
}
```

#### 10. Max Area of Island - [LeetCode 695](https://leetcode.com/problems/max-area-of-island/)

Given a binary grid, return the maximum area of an island connected horizontally or vertically.

```java
public int maxAreaOfIsland(int[][] grid) {
  int best = 0;
  for (int r = 0; r < grid.length; r++) {
    for (int c = 0; c < grid[0].length; c++) {
      if (grid[r][c] == 1) best = Math.max(best, sinkQ10(grid, r, c));
    }
  }
  return best;
}

private int sinkQ10(int[][] grid, int sr, int sc) {
  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  Queue<int[]> queue = new ArrayDeque<>();
  queue.offer(new int[]{sr, sc});
  grid[sr][sc] = 0;
  int count = 0;
  while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    count++;
    for (int d = 0; d < 4; d++) {
      int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
      if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length && grid[nr][nc] == 1) {
        grid[nr][nc] = 0;
        queue.offer(new int[]{nr, nc});
      }
    }
  }
  return count;
}
```

#### 11. Surrounded Regions - [LeetCode 130](https://leetcode.com/problems/surrounded-regions/)

Given a board of X and O, capture all regions of O that are fully surrounded by X.

```java
public void solve(char[][] board) {
  int m = board.length, n = board[0].length;
  for (int r = 0; r < m; r++) {
    markQ11(board, r, 0);
    markQ11(board, r, n - 1);
  }
  for (int c = 0; c < n; c++) {
    markQ11(board, 0, c);
    markQ11(board, m - 1, c);
  }
  for (int r = 0; r < m; r++) {
    for (int c = 0; c < n; c++) {
      if (board[r][c] == 'O') board[r][c] = 'X';
      if (board[r][c] == '#') board[r][c] = 'O';
    }
  }
}

private void markQ11(char[][] board, int sr, int sc) {
  if (board[sr][sc] != 'O') return;
  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  Queue<int[]> queue = new ArrayDeque<>();
  queue.offer(new int[]{sr, sc});
  board[sr][sc] = '#';
  while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int d = 0; d < 4; d++) {
      int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
      if (nr >= 0 && nr < board.length && nc >= 0 && nc < board[0].length && board[nr][nc] == 'O') {
        board[nr][nc] = '#';
        queue.offer(new int[]{nr, nc});
      }
    }
  }
}
```

#### 12. Pacific Atlantic Water Flow - [LeetCode 417](https://leetcode.com/problems/pacific-atlantic-water-flow/)

Given a matrix of heights, return coordinates from which water can flow to both the Pacific and Atlantic oceans.

```java
public List<List<Integer>> pacificAtlantic(int[][] heights) {
  int m = heights.length, n = heights[0].length;
  boolean[][] pacific = new boolean[m][n];
  boolean[][] atlantic = new boolean[m][n];
  Queue<int[]> pQueue = new ArrayDeque<>();
  Queue<int[]> aQueue = new ArrayDeque<>();

  for (int r = 0; r < m; r++) {
    addQ12(pQueue, pacific, r, 0);
    addQ12(aQueue, atlantic, r, n - 1);
  }
  for (int c = 0; c < n; c++) {
    addQ12(pQueue, pacific, 0, c);
    addQ12(aQueue, atlantic, m - 1, c);
  }
  bfsQ12(heights, pQueue, pacific);
  bfsQ12(heights, aQueue, atlantic);

  List<List<Integer>> answer = new ArrayList<>();
  for (int r = 0; r < m; r++) for (int c = 0; c < n; c++) {
    if (pacific[r][c] && atlantic[r][c]) answer.add(Arrays.asList(r, c));
  }
  return answer;
}

private void addQ12(Queue<int[]> queue, boolean[][] seen, int r, int c) {
  if (!seen[r][c]) {
    seen[r][c] = true;
    queue.offer(new int[]{r, c});
  }
}

private void bfsQ12(int[][] h, Queue<int[]> queue, boolean[][] seen) {
  int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
  while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int d = 0; d < 4; d++) {
      int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
      if (nr >= 0 && nr < h.length && nc >= 0 && nc < h[0].length && !seen[nr][nc] && h[nr][nc] >= h[cell[0]][cell[1]]) {
        seen[nr][nc] = true;
        queue.offer(new int[]{nr, nc});
      }
    }
  }
}
```

## Topic 24: Strings

#### 1. Valid Anagram - [LeetCode 242](https://leetcode.com/problems/valid-anagram/)

Given two strings s and t, return true if t is an anagram of s and false otherwise.

```java
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
```

#### 2. Valid Palindrome - [LeetCode 125](https://leetcode.com/problems/valid-palindrome/)

Given a string s, return true if it is a palindrome after converting uppercase letters to lowercase and removing non-alphanumeric characters.

```java
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
```

#### 3. Longest Substring Without Repeating Characters - [LeetCode 3](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

Given a string s, return the length of the longest substring without repeating characters.

```java
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
```

#### 4. Longest Repeating Character Replacement - [LeetCode 424](https://leetcode.com/problems/longest-repeating-character-replacement/)

Given a string s and integer k, return the length of the longest substring that can be made of one repeated character after at most k replacements.

```java
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
```

#### 5. Minimum Window Substring - [LeetCode 76](https://leetcode.com/problems/minimum-window-substring/)

Given strings s and t, return the minimum window substring of s that contains every character of t including duplicates.

```java
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
```

#### 6. Group Anagrams - [LeetCode 49](https://leetcode.com/problems/group-anagrams/)

Given an array of strings, group the anagrams together in any order.

```java
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
```

#### 7. Encode and Decode Strings - [LeetCode 271](https://leetcode.com/problems/encode-and-decode-strings/)

Design an algorithm to encode a list of strings into one string and decode it back to the original list.

```java
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
```

#### 8. Longest Palindromic Substring - [LeetCode 5](https://leetcode.com/problems/longest-palindromic-substring/)

Given a string s, return the longest palindromic substring in s.

```java
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
```

#### 9. Palindromic Substrings - [LeetCode 647](https://leetcode.com/problems/palindromic-substrings/)

Given a string s, return the number of palindromic substrings in it.

```java
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
```

#### 10. Valid Parenthesis String - [LeetCode 678](https://leetcode.com/problems/valid-parenthesis-string/)

Given a string containing (, ), and *, return true if * can be treated as (, ), or empty to make the string valid.

```java
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
```

#### 11. Find the Index of the First Occurrence in a String - [LeetCode 28](https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/)

Given strings haystack and needle, return the index of the first occurrence of needle in haystack, or -1 if it is not present.

```java
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
```

#### 12. Repeated Substring Pattern - [LeetCode 459](https://leetcode.com/problems/repeated-substring-pattern/)

Given a string s, return true if it can be constructed by repeating one of its proper substrings multiple times.

```java
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
```

## Topic 25: Sorting

#### 1. Sort Colors - [LeetCode 75](https://leetcode.com/problems/sort-colors/)

Given an array nums containing only 0, 1, and 2, sort it in-place so equal colors are grouped in the order 0, 1, 2.

```java
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
```

#### 2. Merge Sorted Array - [LeetCode 88](https://leetcode.com/problems/merge-sorted-array/)

Given sorted arrays nums1 and nums2 where nums1 has enough trailing space, merge nums2 into nums1 as one sorted array.

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
  int i = m - 1;
  int j = n - 1;
  int write = m + n - 1;
  while (j >= 0) {
    if (i >= 0 && nums1[i] > nums2[j]) nums1[write--] = nums1[i--];
    else nums1[write--] = nums2[j--];
  }
}
```

#### 3. Kth Largest Element in an Array - [LeetCode 215](https://leetcode.com/problems/kth-largest-element-in-an-array/)

Given an integer array nums and an integer k, return the kth largest element in the array.

```java
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
```

#### 4. Top K Frequent Elements - [LeetCode 347](https://leetcode.com/problems/top-k-frequent-elements/)

Given an integer array nums and integer k, return any order of the k most frequent elements.

```java
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
```

#### 5. Largest Number - [LeetCode 179](https://leetcode.com/problems/largest-number/)

Given a list of non-negative integers, arrange them so they form the largest possible number as a string.

```java
public String largestNumber(int[] nums) {
  String[] values = new String[nums.length];
  for (int i = 0; i < nums.length; i++) values[i] = String.valueOf(nums[i]);
  Arrays.sort(values, (a, b) -> (b + a).compareTo(a + b));
  if (values[0].equals("0")) return "0";
  StringBuilder answer = new StringBuilder();
  for (String value : values) answer.append(value);
  return answer.toString();
}
```

#### 6. Meeting Rooms - [LeetCode 252](https://leetcode.com/problems/meeting-rooms/)

Given meeting time intervals, return true if one person can attend all meetings.

```java
public boolean canAttendMeetings(int[][] intervals) {
  Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
  for (int i = 1; i < intervals.length; i++) {
    if (intervals[i][0] < intervals[i - 1][1]) return false;
  }
  return true;
}
```

#### 7. Meeting Rooms II - [LeetCode 253](https://leetcode.com/problems/meeting-rooms-ii/)

Given meeting time intervals, return the minimum number of conference rooms required.

```java
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
```

#### 8. Merge Intervals - [LeetCode 56](https://leetcode.com/problems/merge-intervals/)

Given intervals, merge all overlapping intervals and return the non-overlapping result.

```java
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
```

#### 9. Non-overlapping Intervals - [LeetCode 435](https://leetcode.com/problems/non-overlapping-intervals/)

Given intervals, return the minimum number of intervals to remove so the rest are non-overlapping.

```java
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
```

#### 10. H-Index - [LeetCode 274](https://leetcode.com/problems/h-index/)

Given citations where citations[i] is citations for a paper, return the researcher h-index.

```java
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
```

#### 11. Wiggle Sort - [LeetCode 280](https://leetcode.com/problems/wiggle-sort/)

Given an unsorted array nums, reorder it in-place so nums[0] <= nums[1] >= nums[2] <= nums[3] and so on.

```java
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
```

#### 12. Wiggle Sort II - [LeetCode 324](https://leetcode.com/problems/wiggle-sort-ii/)

Given nums, reorder it in-place so nums[0] < nums[1] > nums[2] < nums[3] and so on.

```java
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
```
