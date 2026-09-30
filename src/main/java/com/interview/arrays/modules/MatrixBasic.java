package com.interview.arrays.modules;

import java.util.*;
import com.interview.arrays.model.ListNode;

/** Topic 23: Matrix. Basic: source questions 1-12. */
public class MatrixBasic {

  /*
   * Question 1: Set Matrix Zeroes
   * 
   * Question: Given an m x n integer matrix, if an element is 0, set its entire row and column to 0 in place.
   * 
   * Constraints: 1 <= m, n <= 200; -2^31 <= matrix[i][j] <= 2^31 - 1.
   * 
   * Time and space complexity: Time O(mn); Space O(1). First row and first column store marker flags.
   * 
   * Example 1:
   * Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
   * Output: [[1,0,1],[0,0,0],[1,0,1]]
   * Explanation: The zero at row 1, column 1 clears that row and column.
   * 
   * Example 2:
   * Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
   * Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
   * Explanation: Zeros in the first row must still clear their columns.
   * 
   * Example 3:
   * Input: matrix = [[1]]
   * Output: [[1]]
   * Explanation: There is no zero to propagate.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 2: Spiral Matrix
   * 
   * Question: Given an m x n matrix, return all elements of the matrix in spiral order.
   * 
   * Constraints: 1 <= m, n <= 10; -100 <= matrix[i][j] <= 100.
   * 
   * Time and space complexity: Time O(mn); Space O(1) excluding output. Shrinking boundaries avoid visited state.
   * 
   * Example 1:
   * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
   * Output: [1,2,3,6,9,8,7,4,5]
   * Explanation: The outer layer is read before the center.
   * 
   * Example 2:
   * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
   * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
   * Explanation: The final inner row is handled once.
   * 
   * Example 3:
   * Input: matrix = [[1],[2],[3]]
   * Output: [1,2,3]
   * Explanation: One column is already spiral order.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 3: Spiral Matrix II
   * 
   * Question: Given an integer n, generate an n x n matrix filled with numbers from 1 to n^2 in spiral order.
   * 
   * Constraints: 1 <= n <= 20.
   * 
   * Time and space complexity: Time O(n^2); Space O(n^2). Boundary-layer filling writes each cell once.
   * 
   * Example 1:
   * Input: n = 3
   * Output: [[1,2,3],[8,9,4],[7,6,5]]
   * Explanation: Values are written around the boundary then inward.
   * 
   * Example 2:
   * Input: n = 1
   * Output: [[1]]
   * Explanation: Only one cell is filled.
   * 
   * Example 3:
   * Input: n = 2
   * Output: [[1,2],[4,3]]
   * Explanation: The single layer contains four values.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 4: Rotate Image
   * 
   * Question: Given an n x n matrix, rotate it 90 degrees clockwise in place.
   * 
   * Constraints: 1 <= n <= 20; -1000 <= matrix[i][j] <= 1000.
   * 
   * Time and space complexity: Time O(n^2); Space O(1). Transpose then reverse each row.
   * 
   * Example 1:
   * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
   * Output: [[7,4,1],[8,5,2],[9,6,3]]
   * Explanation: Rows become columns after clockwise rotation.
   * 
   * Example 2:
   * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
   * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
   * Explanation: All four layers rotate in place.
   * 
   * Example 3:
   * Input: matrix = [[1]]
   * Output: [[1]]
   * Explanation: One cell is unchanged.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 5: Search a 2D Matrix
   * 
   * Question: Given a matrix where each row is sorted and the first integer of each row is greater than the last integer of the previous row, return whether target exists.
   * 
   * Constraints: 1 <= m, n <= 100; -10000 <= matrix[i][j], target <= 10000.
   * 
   * Time and space complexity: Time O(log(mn)); Space O(1). Binary search a flattened virtual array.
   * 
   * Example 1:
   * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
   * Output: true
   * Explanation: 3 is present in the first row.
   * 
   * Example 2:
   * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
   * Output: false
   * Explanation: 13 is between values but absent.
   * 
   * Example 3:
   * Input: matrix = [[1]], target = 1
   * Output: true
   * Explanation: Single-cell matrix contains the target.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 6: Search a 2D Matrix II
   * 
   * Question: Given an m x n matrix sorted ascending left-to-right in each row and top-to-bottom in each column, return whether target exists.
   * 
   * Constraints: 1 <= m, n <= 300; -1000000000 <= matrix[i][j], target <= 1000000000.
   * 
   * Time and space complexity: Time O(m+n); Space O(1). Staircase search eliminates one row or column each step.
   * 
   * Example 1:
   * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
   * Output: true
   * Explanation: The target is found while walking the staircase.
   * 
   * Example 2:
   * Input: matrix = [[1,4],[2,5]], target = 3
   * Output: false
   * Explanation: The search eliminates all candidates.
   * 
   * Example 3:
   * Input: matrix = [[-1,3]], target = 3
   * Output: true
   * Explanation: Single row still supports staircase movement.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 7: Game of Life
   * 
   * Question: Given a board of live and dead cells, update it to the next Game of Life state in place using the standard four rules.
   * 
   * Constraints: 1 <= m, n <= 25; board[i][j] is 0 or 1.
   * 
   * Time and space complexity: Time O(mn); Space O(1). Transitional values encode old and new states in place.
   * 
   * Example 1:
   * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
   * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
   * Explanation: Cells update simultaneously from the original board.
   * 
   * Example 2:
   * Input: board = [[1,1],[1,0]]
   * Output: [[1,1],[1,1]]
   * Explanation: The dead bottom-right cell becomes live.
   * 
   * Example 3:
   * Input: board = [[0]]
   * Output: [[0]]
   * Explanation: A single dead cell stays dead.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 8: Word Search
   * 
   * Question: Given an m x n board of characters and a word, return true if the word exists by moving horizontally or vertically without reusing a cell.
   * 
   * Constraints: 1 <= m, n <= 6; 1 <= word.length <= 15; board and word contain English letters.
   * 
   * Time and space complexity: Time O(mn * 4^L); Space O(L). In-place marking avoids a visited matrix.
   * 
   * Example 1:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
   * Output: true
   * Explanation: A valid path spells the word.
   * 
   * Example 2:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
   * Output: true
   * Explanation: The word appears through adjacent cells.
   * 
   * Example 3:
   * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
   * Output: false
   * Explanation: The path would need to reuse B.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 9: Number of Islands
   * 
   * Question: Given a grid of 1s and 0s, return the number of islands where land is connected horizontally or vertically.
   * 
   * Constraints: 1 <= m, n <= 300; grid[i][j] is 0 or 1.
   * 
   * Time and space complexity: Time O(mn); Space O(mn) worst-case queue. Mutate visited land to water.
   * 
   * Example 1:
   * Input: grid = [["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]
   * Output: 1
   * Explanation: All land cells connect into one island.
   * 
   * Example 2:
   * Input: grid = [["1","1","0","0","0"],["1","1","0","0","0"],["0","0","1","0","0"],["0","0","0","1","1"]]
   * Output: 3
   * Explanation: There are three disconnected land components.
   * 
   * Example 3:
   * Input: grid = [["0"]]
   * Output: 0
   * Explanation: No land means no island.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 10: Max Area of Island
   * 
   * Question: Given a binary grid, return the maximum area of an island connected horizontally or vertically.
   * 
   * Constraints: 1 <= m, n <= 50; grid[i][j] is 0 or 1.
   * 
   * Time and space complexity: Time O(mn); Space O(mn) worst-case queue. Mutate visited land to water.
   * 
   * Example 1:
   * Input: grid = [[0,0,1,0],[1,1,1,0],[0,0,0,0]]
   * Output: 4
   * Explanation: The connected component has four land cells.
   * 
   * Example 2:
   * Input: grid = [[0,0,0,0]]
   * Output: 0
   * Explanation: There is no island.
   * 
   * Example 3:
   * Input: grid = [[1,1],[1,1]]
   * Output: 4
   * Explanation: The whole grid is one island.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 11: Surrounded Regions
   * 
   * Question: Given a board of X and O, capture all regions of O that are fully surrounded by X.
   * 
   * Constraints: 1 <= m, n <= 200; board[i][j] is X or O.
   * 
   * Time and space complexity: Time O(mn); Space O(mn) queue. Flood fill safe boundary-connected cells first.
   * 
   * Example 1:
   * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
   * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
   * Explanation: Only the boundary-connected O remains.
   * 
   * Example 2:
   * Input: board = [["X"]]
   * Output: [["X"]]
   * Explanation: Single cell remains unchanged.
   * 
   * Example 3:
   * Input: board = [["O","O"],["O","O"]]
   * Output: [["O","O"],["O","O"]]
   * Explanation: Every O touches the boundary.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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

  /*
   * Question 12: Pacific Atlantic Water Flow
   * 
   * Question: Given a matrix of heights, return coordinates from which water can flow to both the Pacific and Atlantic oceans.
   * 
   * Constraints: 1 <= m, n <= 200; 0 <= heights[i][j] <= 100000.
   * 
   * Time and space complexity: Time O(mn); Space O(mn). Reverse BFS/DFS from ocean borders.
   * 
   * Example 1:
   * Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
   * Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
   * Explanation: These cells can reach both oceans.
   * 
   * Example 2:
   * Input: heights = [[1]]
   * Output: [[0,0]]
   * Explanation: The only cell touches both oceans.
   * 
   * Example 3:
   * Input: heights = [[1,1],[1,1]]
   * Output: [[0,0],[0,1],[1,0],[1,1]]
   * Explanation: Flat terrain can flow everywhere.
   * 
   * Source: https://maurya29.github.io/DSA-Pattern-Workbook/pages/matrix.html
   */
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
}
