# Array - Optimized Questions and Solutions

## Topic 1: Arrays & Hashing

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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

### Basic (1-12)

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

### Moderate (13-20)

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
