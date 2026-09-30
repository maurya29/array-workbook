# Java Array Pattern Workbook

160 questions from topics **1, 2, 3, 4, 5, 23, 24, and 25** of the [DSA Pattern Workbook](https://maurya29.github.io/DSA-Pattern-Workbook/dsa.html), retrieved September 30, 2026.

Each topic has exactly two module classes. **Basic contains source questions 1–12; Moderate contains source questions 13–20.** These labels follow the requested numbering, not the website's difficulty ratings. The original question order is preserved, including the string and linked-list exercises within the selected topics.

Every exercise has a comment containing its question, constraints, examples, time and space complexity, and source URL. Only the optimized implementation is included in the module classes. [Browse all 160 questions](CATALOG.md).

## Module classes

| Topic | Module | Basic: 1–12 | Moderate: 13–20 |
|---|---|---|---|
| 1 | Arrays & Hashing | [ArraysHashingBasic](src/main/java/com/interview/arrays/modules/ArraysHashingBasic.java) | [ArraysHashingModerate](src/main/java/com/interview/arrays/modules/ArraysHashingModerate.java) |
| 2 | Two Pointers | [TwoPointersBasic](src/main/java/com/interview/arrays/modules/TwoPointersBasic.java) | [TwoPointersModerate](src/main/java/com/interview/arrays/modules/TwoPointersModerate.java) |
| 3 | Sliding Window | [SlidingWindowBasic](src/main/java/com/interview/arrays/modules/SlidingWindowBasic.java) | [SlidingWindowModerate](src/main/java/com/interview/arrays/modules/SlidingWindowModerate.java) |
| 4 | Prefix Sum | [PrefixSumBasic](src/main/java/com/interview/arrays/modules/PrefixSumBasic.java) | [PrefixSumModerate](src/main/java/com/interview/arrays/modules/PrefixSumModerate.java) |
| 5 | Binary Search | [BinarySearchBasic](src/main/java/com/interview/arrays/modules/BinarySearchBasic.java) | [BinarySearchModerate](src/main/java/com/interview/arrays/modules/BinarySearchModerate.java) |
| 23 | Matrix | [MatrixBasic](src/main/java/com/interview/arrays/modules/MatrixBasic.java) | [MatrixModerate](src/main/java/com/interview/arrays/modules/MatrixModerate.java) |
| 24 | Strings | [StringsBasic](src/main/java/com/interview/arrays/modules/StringsBasic.java) | [StringsModerate](src/main/java/com/interview/arrays/modules/StringsModerate.java) |
| 25 | Sorting | [SortingBasic](src/main/java/com/interview/arrays/modules/SortingBasic.java) | [SortingModerate](src/main/java/com/interview/arrays/modules/SortingModerate.java) |

## Build and run

Install a JDK (8 or later) and Maven. Open this folder in a terminal:

```sh
mvn clean package
java -jar target/java-array-workbook-1.0.0.jar
```

Run tests independently with `mvn test`. Import `pom.xml` in IntelliJ IDEA, Eclipse, or VS Code. Source and compiled bytecode target Java 8. The application has no external runtime dependencies; JUnit and Gson are used only for tests. Maven needs network access for dependencies on its first build.

## Call a solution

```java
import com.interview.arrays.modules.ArraysHashingBasic;
import com.interview.arrays.modules.BinarySearchBasic;
import com.interview.arrays.modules.PrefixSumBasic;

int[] pair = new ArraysHashingBasic().twoSum(new int[]{2, 7, 11, 15}, 9);
int firstBad = new BinarySearchBasic().firstBadVersion(5, version -> version >= 4);
PrefixSumBasic.NumArray range = new PrefixSumBasic.NumArray(new int[]{-2, 0, 3, -5, 2, -1});
int sum = range.sumRange(0, 2);
```

Solutions are public instance methods except the source's static `aggressiveCows` method. To avoid duplicate signatures, rotated search is named `searchRotated`, staircase matrix search is `searchMatrixII`, and strict wiggle sorting is `wiggleSortStrict`. Private helpers carry a question-number suffix so similarly named helpers cannot interfere.

The stateful `NumArray`, `NumMatrix`, and `RandomizedSet` exercises are public static nested classes inside their owning module. `ListNode` is the shared model for linked-list questions. `Main` provides a demo covering all eight topics.

## Source adaptations and input conventions

Implementations follow the stated per-question constraints. In-place exercises modify their arrays, matrices, or linked lists; sorting-based methods may also reorder their inputs. Pass copies when the original order must be retained. Complexity comments describe the included approach; “optimized” does not claim that every approach is asymptotically optimal under every possible tradeoff.

The imported optimized solutions were adjusted where needed:

- Removed alternate recursive palindrome/reversal implementations from the Two Pointers answers.
- Replaced the quadratic odd-length subarray enumeration with O(n) contribution counting and corrected its example explanation.
- Added precise complexity notes where the source used placeholders, and corrected selected space/time bounds.
- Used a supplied `IntPredicate` for First Bad Version, making it runnable without a judge-provided API.
- Used long intermediate arithmetic for Continuous Subarray Sum and long map keys for Maximum Size Subarray Sum.
- Made `NumMatrix.sumRegion` return `long`, with long prefix sums, because the supplied constraints allow sums exceeding a signed int.
- Reset Sudoku masks on every call; replaced vague Sudoku examples with concrete boards.
- Selected the Wiggle Sort II median in place to match its constant auxiliary-space bound.

## Verification and provenance

The test suite contains **168 tests**: one source example for every question, plus eight regression tests covering overflow, repeated Sudoku calls, codec round trips, helper separation, contribution counting, boundary cases, and sorting. Unordered answers and in-place outputs are checked according to their problem contracts. Passing examples do not constitute exhaustive verification of all inputs.

`workbook-data/` stores the selected source metadata and optimized snippets with page URLs. `workbook-index.json` maps questions to classes and public methods. Original problem links are preserved in the source snapshots. The workbook content is attributed to the linked website; no additional license is asserted for upstream material.

The optional Python generation scripts are for reproducibility and are not needed to build or run Java:

```sh
python tools/build_workbook.py
python tools/build_example_tests.py
```

Generation overwrites the module classes and example fixtures. Make maintained adaptations in `tools/build_workbook.py` if regenerating.
