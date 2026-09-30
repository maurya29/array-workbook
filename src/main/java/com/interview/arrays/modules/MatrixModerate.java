package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 23: Matrix. Moderate: source questions 13-20. */
public class MatrixModerate {

  /*
   * Question 13: Flood Fill
   * 
   * Question: Given an image, a starting cell, and a new color, recolor the connected component with the same original color.
   * 
   * Constraints: 1 <= m, n <= 50; 0 <= image[i][j], color < 2^16.
   * 
   * Time and space complexity: Time O(mn); Space O(mn) queue. Recolor cells directly to mark visited.
   * 
   * Example 1:
   * Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
   * Output: [[2,2,2],[2,2,0],[2,0,1]]
   * Explanation: Only the connected 1-component is recolored.
   * 
   * Example 2:
   * Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
   * Output: [[0,0,0],[0,0,0]]
   * Explanation: No work is needed when the color is unchanged.
   * 
   * Example 3:
   * Input: image = [[1]], sr = 0, sc = 0, color = 2
   * Output: [[2]]
   * Explanation: The single component is one cell.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 14: 01 Matrix
   * 
   * Question: Given a binary matrix, return a matrix where each cell contains the distance to the nearest 0.
   * 
   * Constraints: 1 <= m, n <= 10000 total cells; mat[i][j] is 0 or 1; at least one zero exists.
   * 
   * Time and space complexity: Time O(mn); Space O(mn). Multi-source BFS visits each cell once.
   * 
   * Example 1:
   * Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
   * Output: [[0,0,0],[0,1,0],[0,0,0]]
   * Explanation: The center one is adjacent to zeros.
   * 
   * Example 2:
   * Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
   * Output: [[0,0,0],[0,1,0],[1,2,1]]
   * Explanation: Distances expand from all zeros.
   * 
   * Example 3:
   * Input: mat = [[0]]
   * Output: [[0]]
   * Explanation: Zero distance for the only cell.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 15: Rotting Oranges
   * 
   * Question: Given a grid of empty cells, fresh oranges, and rotten oranges, return minutes until all oranges rot or -1 if impossible.
   * 
   * Constraints: 1 <= m, n <= 10; grid[i][j] is 0, 1, or 2.
   * 
   * Time and space complexity: Time O(mn); Space O(mn). Multi-source BFS processes each orange once.
   * 
   * Example 1:
   * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
   * Output: 4
   * Explanation: Fresh oranges rot level by level.
   * 
   * Example 2:
   * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
   * Output: -1
   * Explanation: One fresh orange is isolated.
   * 
   * Example 3:
   * Input: grid = [[0,2]]
   * Output: 0
   * Explanation: No fresh oranges exist.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 16: Shortest Path in Binary Matrix
   * 
   * Question: Given an n x n binary grid, return the length of the shortest clear path from top-left to bottom-right moving in 8 directions.
   * 
   * Constraints: 1 <= n <= 100; grid[i][j] is 0 or 1.
   * 
   * Time and space complexity: Time O(n^2); Space O(n^2). BFS visits each open cell at most once.
   * 
   * Example 1:
   * Input: grid = [[0,1],[1,0]]
   * Output: 2
   * Explanation: Move diagonally from start to end.
   * 
   * Example 2:
   * Input: grid = [[0,0,0],[1,1,0],[1,1,0]]
   * Output: 4
   * Explanation: BFS finds the shortest clear path.
   * 
   * Example 3:
   * Input: grid = [[1,0],[0,0]]
   * Output: -1
   * Explanation: The starting cell is blocked.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 17: Toeplitz Matrix
   * 
   * Question: Given a matrix, return true if every diagonal from top-left to bottom-right has the same value.
   * 
   * Constraints: 1 <= m, n <= 20; 0 <= matrix[i][j] <= 99.
   * 
   * Time and space complexity: Time O(mn); Space O(1). Compare each cell to its top-left neighbor.
   * 
   * Example 1:
   * Input: matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
   * Output: true
   * Explanation: Every diagonal repeats the same value.
   * 
   * Example 2:
   * Input: matrix = [[1,2],[2,2]]
   * Output: false
   * Explanation: The main diagonal contains 1 and 2.
   * 
   * Example 3:
   * Input: matrix = [[7]]
   * Output: true
   * Explanation: A single cell is Toeplitz.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
  public boolean isToeplitzMatrix(int[][] matrix) {
    for (int r = 1; r < matrix.length; r++) {
      for (int c = 1; c < matrix[0].length; c++) {
        if (matrix[r][c] != matrix[r - 1][c - 1]) return false;
      }
    }
    return true;
  }

  /*
   * Question 18: Valid Sudoku
   * 
   * Question: Given a partially filled 9 x 9 Sudoku board, return true if it is valid under row, column, and 3 x 3 box constraints.
   * 
   * Constraints: board.length == 9; board[i].length == 9; board[i][j] is digit 1-9 or dot.
   * 
   * Time and space complexity: Time O(81); Space O(1). Bit masks track used digits per row, column, and box.
   * 
   * Example 1:
   * Input: board = [["5", "3", ".", ".", "7", ".", ".", ".", "."], ["6", ".", ".", "1", "9", "5", ".", ".", "."], [".", "9", "8", ".", ".", ".", ".", "6", "."], ["8", ".", ".", ".", "6", ".", ".", ".", "3"], ["4", ".", ".", "8", ".", "3", ".", ".", "1"], ["7", ".", ".", ".", "2", ".", ".", ".", "6"], [".", "6", ".", ".", ".", ".", "2", "8", "."], [".", ".", ".", "4", "1", "9", ".", ".", "5"], [".", ".", ".", ".", "8", ".", ".", "7", "9"]]
   * Output: true
   * Explanation: No row, column, or box contains duplicate digits.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 19: Sudoku Solver
   * 
   * Question: Given a 9 x 9 Sudoku board, fill it so every row, column, and 3 x 3 box contains digits 1 through 9 exactly once.
   * 
   * Constraints: board.length == 9; board[i].length == 9; board has exactly one valid solution.
   * 
   * Time and space complexity: Time O(9^E); Space O(E). Bit masks make constraint checks O(1).
   * 
   * Example 1:
   * Input: board = [["5", "3", ".", ".", "7", ".", ".", ".", "."], ["6", ".", ".", "1", "9", "5", ".", ".", "."], [".", "9", "8", ".", ".", ".", ".", "6", "."], ["8", ".", ".", ".", "6", ".", ".", ".", "3"], ["4", ".", ".", "8", ".", "3", ".", ".", "1"], ["7", ".", ".", ".", "2", ".", ".", ".", "6"], [".", "6", ".", ".", ".", ".", "2", "8", "."], [".", ".", ".", "4", "1", "9", ".", ".", "5"], [".", ".", ".", ".", "8", ".", ".", "7", "9"]]
   * Output: [["5", "3", "4", "6", "7", "8", "9", "1", "2"], ["6", "7", "2", "1", "9", "5", "3", "4", "8"], ["1", "9", "8", "3", "4", "2", "5", "6", "7"], ["8", "5", "9", "7", "6", "1", "4", "2", "3"], ["4", "2", "6", "8", "5", "3", "7", "9", "1"], ["7", "1", "3", "9", "2", "4", "8", "5", "6"], ["9", "6", "1", "5", "3", "7", "2", "8", "4"], ["2", "8", "7", "4", "1", "9", "6", "3", "5"], ["3", "4", "5", "2", "8", "6", "1", "7", "9"]]
   * Explanation: Fill every dot while preserving all given digits.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   * Implementation note: Reset masks for every call so the module instance can solve multiple boards.
   */
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

  /*
   * Question 20: Diagonal Traverse
   * 
   * Question: Given an m x n matrix, return all elements in diagonal order alternating up-right and down-left.
   * 
   * Constraints: 1 <= m, n <= 10000 total cells; -100000 <= mat[i][j] <= 100000.
   * 
   * Time and space complexity: Time O(mn); Space O(1) excluding output. Simulate direction and bounce at boundaries.
   * 
   * Example 1:
   * Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
   * Output: [1,2,4,7,5,3,6,8,9]
   * Explanation: Direction alternates between diagonals.
   * 
   * Example 2:
   * Input: mat = [[1,2],[3,4]]
   * Output: [1,2,3,4]
   * Explanation: The middle diagonal is reversed.
   * 
   * Example 3:
   * Input: mat = [[1,2,3]]
   * Output: [1,2,3]
   * Explanation: A single row has no vertical movement.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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
}
