package com.interview.arrays;

import com.interview.arrays.modules.*;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/** Boundary cases for adapted code and helpers that share signatures across exercises. */
public class WorkbookRegressionTest {
    @Test public void continuousSubarrayHandlesLargeRemainders() {
        assertTrue(new PrefixSumModerate().checkSubarraySum(
                new int[]{1000000000,1000000000,1000000000,1000000000}, 2000000000));
        assertFalse(new PrefixSumModerate().checkSubarraySum(new int[]{1000000000,1000000000,1000000000}, Integer.MAX_VALUE));
        assertTrue(new PrefixSumModerate().checkSubarraySum(new int[]{0,0}, Integer.MAX_VALUE));
    }

    @Test public void twoDimensionalRangeSupportsDocumentedMaximum() {
        int[][] matrix = new int[200][200];
        for (int[] row : matrix) Arrays.fill(row, 100000);
        assertEquals(4000000000L, new PrefixSumBasic.NumMatrix(matrix).sumRegion(0,0,199,199));
    }

    @Test public void sudokuModuleCanBeReused() {
        MatrixModerate module = new MatrixModerate();
        for (int run = 0; run < 2; run++) {
            char[][] board = WorkbookExamplesTest.sudoku();
            char[][] givens = WorkbookExamplesTest.sudoku();
            module.solveSudoku(board);
            assertTrue(module.isValidSudoku(board));
            for (int r=0; r<9; r++) for (int c=0; c<9; c++) {
                assertTrue(board[r][c] >= '1' && board[r][c] <= '9');
                if (givens[r][c] != '.') assertEquals(givens[r][c], board[r][c]);
            }
        }
    }

    @Test public void codecPreservesEmptyStringsDelimitersAndUnicode() {
        List<String> input = Arrays.asList("", "#", "12#abc", "\u03a9\ud83d\ude00", "\n");
        ArraysHashingModerate hashing = new ArraysHashingModerate();
        StringsBasic strings = new StringsBasic();
        assertEquals(input, hashing.decode(hashing.encode(input)));
        assertEquals(input, strings.decode(strings.encode(input)));
        assertEquals(Collections.emptyList(), hashing.decode(hashing.encode(Collections.emptyList())));
    }

    @Test public void palindromeHelpersKeepTheirDifferentMeanings() {
        StringsBasic module = new StringsBasic();
        assertEquals("abba", module.longestPalindrome("cabbad"));
        assertEquals(10, module.countSubstrings("aaaa"));
    }

    @Test public void oddLengthContributionMatchesEnumeration() {
        Random random = new Random(29);
        for (int n=1; n<=20; n++) {
            int[] values = new int[n];
            for (int i=0; i<n; i++) values[i] = random.nextInt(1000)+1;
            int expected = 0;
            for (int left=0; left<n; left++) {
                int sum=0;
                for (int right=left; right<n; right++) {
                    sum += values[right];
                    if (((right-left+1)&1)==1) expected += sum;
                }
            }
            assertEquals(expected, new PrefixSumBasic().sumOddLengthSubarrays(values));
        }
    }

    @Test public void emptySingletonAndMissingResults() {
        assertArrayEquals(new int[]{-1,-1}, new ArraysHashingBasic().twoSum(new int[]{1,2}, 10));
        assertEquals(0, new TwoPointersBasic().removeDuplicates(new int[0]));
        assertFalse(new TwoPointersBasic().hasCycle(null));
        assertNull(new TwoPointersModerate().detectCycle(null));
        assertEquals(0, new SlidingWindowModerate().numSubarrayProductLessThanK(new int[]{1,2,3}, 1));
        assertEquals(3, new SlidingWindowModerate().minOperations(new int[]{1,2,3}, 6));
        assertEquals(-1, new SlidingWindowModerate().minOperations(new int[]{1,2,3}, 7));
        assertEquals(1, new BinarySearchBasic().firstBadVersion(1, v -> true));
        assertEquals(Integer.MAX_VALUE, new BinarySearchBasic().firstBadVersion(Integer.MAX_VALUE, v -> v >= Integer.MAX_VALUE));
        assertEquals(1.0, new BinarySearchModerate().findMedianSortedArrays(new int[0], new int[]{1}), 0.0);
        assertEquals("", new StringsBasic().minWindow("a", "aa"));
        assertEquals(1, new MatrixModerate().shortestPathBinaryMatrix(new int[][]{{0}}));
    }

    @Test public void sortingWithDuplicatesAndExtremeReversePairs() {
        SortingBasic basic = new SortingBasic();
        assertEquals(2, basic.findKthLargest(new int[]{2,2,2,2}, 3));
        int[] wiggle = {1,1,2,2,3,3};
        basic.wiggleSortStrict(wiggle);
        for (int i=1; i<wiggle.length; i++) {
            assertTrue((i&1)==1 ? wiggle[i]>wiggle[i-1] : wiggle[i]<wiggle[i-1]);
        }
        assertEquals(1, new SortingModerate().reversePairs(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}));
        assertEquals(0, new SortingModerate().maximumGap(new int[]{7,7,7}));
    }
}
