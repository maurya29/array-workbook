package com.interview.arrays;

import com.google.gson.*;
import com.interview.arrays.model.ListNode;
import com.interview.arrays.modules.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import static org.junit.Assert.*;

/** Executes a source example for every question, respecting mutation and unordered outputs. */
@RunWith(Parameterized.class)
public class WorkbookExamplesTest {
    private static final Gson GSON = new Gson();
    private final JsonObject test;

    public WorkbookExamplesTest(String name, JsonObject test) { this.test = test; }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> examples() throws IOException {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(
                WorkbookExamplesTest.class.getResourceAsStream("/examples.json")), "UTF-8")) {
            List<Object[]> cases = new ArrayList<>();
            for (JsonElement element : JsonParser.parseReader(reader).getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                cases.add(new Object[]{object.get("id").getAsString() + " " + object.get("title").getAsString(), object});
            }
            assertEquals(160, cases.size());
            return cases;
        }
    }

    @Test public void sourceExample() throws Exception {
        String id = test.get("id").getAsString();
        if (test.has("special")) { special(id); return; }
        Object module = Class.forName("com.interview.arrays.modules." + test.get("className").getAsString())
                .getDeclaredConstructor().newInstance();
        JsonArray input = test.getAsJsonArray("args");
        Method method = null;
        for (Method candidate : module.getClass().getDeclaredMethods()) {
            if (candidate.getName().equals(test.get("method").getAsString()) && candidate.getParameterCount() == input.size()) {
                method = candidate; break;
            }
        }
        assertNotNull("Exercise method must exist", method);
        Object[] args = new Object[input.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = method.getParameterTypes()[i] == ListNode.class
                    ? list(GSON.fromJson(input.get(i), int[].class))
                    : GSON.fromJson(input.get(i), method.getGenericParameterTypes()[i]);
        }
        Object result = method.invoke(module, args);
        String mode = test.get("mode").getAsString();
        JsonElement expected = test.get("expected");
        if (mode.equals("roundTrip")) {
            result = module.getClass().getMethod("decode", String.class).invoke(module, result);
        } else if (mode.equals("mutated")) {
            result = args[0];
        } else if (mode.equals("prefix")) {
            assertEquals(test.get("length").getAsInt(), ((Number) result).intValue());
            JsonArray actual = GSON.toJsonTree(args[0]).getAsJsonArray();
            JsonArray prefix = test.getAsJsonArray("prefix");
            for (int i = 0; i < prefix.size(); i++) assertEquals(prefix.get(i), actual.get(i));
            return;
        } else if (mode.endsWith("Wiggle") || mode.equals("wiggle")) {
            int[] actual = (int[]) args[0];
            int[] sorted = actual.clone(); Arrays.sort(sorted);
            int[] original = GSON.fromJson(input.get(0), int[].class); Arrays.sort(original);
            assertArrayEquals(original, sorted);
            boolean strict = mode.equals("strictWiggle");
            for (int i = 1; i < actual.length; i++) {
                if ((i & 1) == 1) assertTrue(strict ? actual[i] > actual[i-1] : actual[i] >= actual[i-1]);
                else assertTrue(strict ? actual[i] < actual[i-1] : actual[i] <= actual[i-1]);
            }
            return;
        } else if (mode.equals("palindrome")) {
            String actual = (String) result;
            assertEquals(expected.getAsInt(), actual.length());
            assertTrue(((String) args[0]).contains(actual));
            assertEquals(actual, new StringBuilder(actual).reverse().toString());
            return;
        } else if (mode.equals("frequency")) {
            String actual = (String) result;
            char[] a = actual.toCharArray(), b = ((String) args[0]).toCharArray();
            Arrays.sort(a); Arrays.sort(b); assertArrayEquals(b, a);
            int previous = Integer.MAX_VALUE;
            Set<Character> seen = new HashSet<>();
            for (int i = 0; i < actual.length();) {
                char c = actual.charAt(i); assertTrue(seen.add(c));
                int j = i; while (j < actual.length() && actual.charAt(j) == c) j++;
                assertTrue(j-i <= previous); previous = j-i; i = j;
            }
            return;
        }
        if (result instanceof ListNode) result = values((ListNode) result);
        JsonElement actual = GSON.toJsonTree(result);
        if (mode.equals("groups") || mode.equals("unordered")) {
            assertEquals(canonical(expected, mode.equals("groups")), canonical(actual, mode.equals("groups")));
        } else if (result instanceof Double) {
            assertEquals(expected.getAsDouble(), ((Double) result).doubleValue(), 1e-6);
        } else assertEquals(expected, actual);
    }

    private static List<String> canonical(JsonElement element, boolean sortInner) {
        List<String> out = new ArrayList<>();
        for (JsonElement item : element.getAsJsonArray()) {
            if (sortInner) {
                List<String> group = new ArrayList<>();
                for (JsonElement word : item.getAsJsonArray()) group.add(word.getAsString());
                Collections.sort(group); out.add(group.toString());
            } else out.add(item.toString());
        }
        Collections.sort(out); return out;
    }

    static ListNode list(int... values) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int value : values) { tail.next = new ListNode(value); tail = tail.next; }
        return dummy.next;
    }

    static List<Integer> values(ListNode node) {
        List<Integer> result = new ArrayList<>();
        while (node != null) { assertTrue("Unexpected cycle", result.size() < 10000); result.add(node.val); node = node.next; }
        return result;
    }

    static char[][] sudoku() {
        String[] rows = {"53..7....", "6..195...", ".98....6.", "8...6...3", "4..8.3..1", "7...2...6", ".6....28.", "...419..5", "....8..79"};
        char[][] board = new char[9][];
        for (int i=0; i<9; i++) board[i] = rows[i].toCharArray();
        return board;
    }

    private void special(String id) {
        switch (id) {
            case "arrays-hashing-20": {
                ArraysHashingModerate.RandomizedSet set = new ArraysHashingModerate.RandomizedSet();
                assertTrue(set.insert(1)); assertFalse(set.remove(2)); assertTrue(set.insert(2));
                int value = set.getRandom(); assertTrue(value == 1 || value == 2);
                assertTrue(set.remove(1)); assertFalse(set.insert(2)); assertEquals(2, set.getRandom()); return;
            }
            case "two-pointers-9":
            case "two-pointers-17": {
                ListNode head = list(3,2,0,-4); head.next.next.next.next = head.next;
                if (id.endsWith("-9")) assertTrue(new TwoPointersBasic().hasCycle(head));
                else assertSame(head.next, new TwoPointersModerate().detectCycle(head));
                return;
            }
            case "prefix-sum-5":
                assertEquals(1, new PrefixSumBasic.NumArray(new int[]{-2,0,3,-5,2,-1}).sumRange(0,2)); return;
            case "prefix-sum-6":
                assertEquals(8, new PrefixSumBasic.NumMatrix(new int[][]{{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}}).sumRegion(2,1,4,3)); return;
            case "binary-search-2":
                assertEquals(4, new BinarySearchBasic().firstBadVersion(5, version -> version >= 4)); return;
            case "matrix-18":
                assertTrue(new MatrixModerate().isValidSudoku(sudoku())); return;
            case "matrix-19": {
                char[][] board = sudoku(); new MatrixModerate().solveSudoku(board);
                assertTrue(new MatrixModerate().isValidSudoku(board));
                for (char[] row : board) for (char c : row) assertTrue(c >= '1' && c <= '9');
                return;
            }
            default: fail("Missing example test: " + id);
        }
    }
}
