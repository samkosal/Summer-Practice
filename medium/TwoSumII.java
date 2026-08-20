import java.util.Arrays;

/*
 * ============================================================================
 * 167. TWO SUM II - INPUT ARRAY IS SORTED                            [MEDIUM]
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 * ============================================================================
 *
 * Given a 1-indexed array of integers `numbers` that is already sorted in
 * NON-DECREASING order, find two numbers such that they add up to a specific
 * `target` number.
 *
 * Let these two numbers be numbers[index1] and numbers[index2] where
 * 1 <= index1 < index2 <= numbers.length.
 *
 * Return the indices of the two numbers, index1 and index2, ADDED BY ONE,
 * as an integer array [index1, index2] of length 2.
 *
 * The tests are generated such that there is exactly one solution.
 * You may NOT use the same element twice.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLES
 * ---------------------------------------------------------------------------
 *   Input:  numbers = [2,7,11,15], target = 9
 *   Output: [1,2]
 *   Why:    2 + 7 == 9. Return the 1-BASED positions, so [1,2] not [0,1].
 *
 *   Input:  numbers = [2,3,4], target = 6
 *   Output: [1,3]
 *
 *   Input:  numbers = [-1,0], target = -1
 *   Output: [1,2]
 *
 * ---------------------------------------------------------------------------
 * CONSTRAINTS
 * ---------------------------------------------------------------------------
 *   2 <= numbers.length <= 3 * 10^4
 *   -1000 <= numbers[i] <= 1000
 *   numbers is sorted in NON-DECREASING order  <-- this is the whole problem
 *   -1000 <= target <= 1000
 *   The tests are generated such that there is exactly one solution.
 *
 * ---------------------------------------------------------------------------
 * THE ACTUAL REQUIREMENT
 * ---------------------------------------------------------------------------
 *   Your solution must use only CONSTANT extra space.
 *
 *   That is not a bonus round. It is part of the problem. A HashMap solution
 *   is O(n) space and does not satisfy it -- it would be accepted by the
 *   judge, but it fails the question being asked.
 *
 *   Two things differ from Two Sum I. Both matter:
 *     1. The array is sorted.
 *     2. The output is 1-indexed, and index1 must come BEFORE index2.
 *
 *   The test harness below enforces the ORDER of your returned pair, unlike
 *   the Two Sum I harness which accepted either order.
 * ============================================================================
 */
public class TwoSumII {

    /**
     * APPROACH 1 - BRUTE FORCE.
     *
     * Write this first even though you already know it. It is your baseline,
     * and the perf comparison at the bottom of main() needs it to make a
     * point that is worth seeing with your own eyes.
     *
     * Ignore the sortedness entirely here. Just check every pair.
     *
     * Time:  O(?)
     * Space: O(?)
     */
    public int[] twoSumBruteForce(int[] numbers, int target) {
        // TODO: implement

        for (int i = 0; i < numbers.length; i++ ) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[] {i + 1, j + 1};

                }
            }
        }

        return new int[] {};

    }

    /**
     * APPROACH 2 - THE ONE THAT ACTUALLY ANSWERS THE QUESTION.
     *
     * Constant extra space. No HashMap, no copy of the input, no second array.
     * A fixed number of int variables, and that is all.
     *
     * Time:  O(?)
     * Space: O(?)  <-- must be O(1)
     */
    public int[] twoSum(int[] numbers, int target) {
        // TODO: implement
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int total = numbers[left] + numbers[right];
            if (total < target) {
                left++;
            }
            else if (total > target) {
                right--;
            }
            else {
                return new int[]{left + 1, right + 1};
            }
        }
        return new int[] {};
    }

    // ========================================================================
    // TEST HARNESS - you shouldn't need to touch anything below this line.
    // Run with:  java TwoSumII.java
    // ========================================================================

    public static void main(String[] args) {

        TwoSumII sol = new TwoSumII();

        // --- Straight off LeetCode -----------------------------------------
        check(sol, new int[] {2, 7, 11, 15}, 9,  new int[] {1, 2}, "basic case");
        check(sol, new int[] {2, 3, 4},      6,  new int[] {1, 3}, "skips the middle element");
        check(sol, new int[] {-1, 0},       -1,  new int[] {1, 2}, "smallest possible input");

        // --- Edge cases worth thinking about --------------------------------
        check(sol, new int[] {1, 2},                  3, new int[] {1, 2}, "length 2, trivial");
        check(sol, new int[] {-5, -4, -3, -2, -1},   -8, new int[] {1, 3}, "all negatives");
        check(sol, new int[] {0, 0, 3, 4},            0, new int[] {1, 2}, "zeros, target 0");
        check(sol, new int[] {-3, -1, 1, 2, 5},       2, new int[] {1, 5}, "answer is both endpoints");
        check(sol, new int[] {1, 2, 5, 6, 20},       11, new int[] {3, 4}, "answer is adjacent, in the middle");
        check(sol, new int[] {1, 3, 3, 4},            6, new int[] {2, 3}, "duplicate values are the answer");
        check(sol, new int[] {1, 2, 3, 9, 10},       19, new int[] {4, 5}, "answer is the last two");

        System.out.println();
        System.out.println("  brute force : " + passedBrute + " / " + total + " passed");
        System.out.println("  optimal     : " + passedOpt   + " / " + total + " passed");

        if (passedBrute == total && passedOpt == total) {
            perfComparison(sol);
        } else {
            System.out.println();
            System.out.println("Look at the first FAIL above.");
            System.out.println("(The perf comparison unlocks once both approaches are green.)");
        }
    }

    // ------------------------------------------------------------------------

    private static int passedBrute = 0;
    private static int passedOpt   = 0;
    private static int total       = 0;

    private static void check(TwoSumII sol, int[] numbers, int target, int[] expected, String label) {
        total++;

        int[] brute = sol.twoSumBruteForce(Arrays.copyOf(numbers, numbers.length), target);
        int[] opt   = sol.twoSum(Arrays.copyOf(numbers, numbers.length), target);

        boolean bOk = matches(brute, expected);
        boolean oOk = matches(opt, expected);
        if (bOk) passedBrute++;
        if (oOk) passedOpt++;

        System.out.printf("brute:%-4s  opt:%-4s  %-36s numbers=%-26s target=%-6d opt_got=%s%n",
                bOk ? "PASS" : "FAIL",
                oOk ? "PASS" : "FAIL",
                label,
                Arrays.toString(numbers),
                target,
                (opt == null ? "null" : Arrays.toString(opt))
                        + (oOk ? "" : "  want=" + Arrays.toString(expected)));
    }

    /**
     * Unlike the Two Sum I harness, ORDER MATTERS here. The problem states
     * index1 < index2, so [2,1] is not an acceptable answer for [1,2].
     */
    private static boolean matches(int[] actual, int[] expected) {
        return actual != null && Arrays.equals(actual, expected);
    }

    /**
     * Both approaches are correct at this point. This shows what correct costs.
     *
     * Deliberately exceeds the LeetCode value constraint (-1000..1000) in order
     * to build a large sorted array whose answer sits at the very end, which is
     * the worst case for a nested loop and an ordinary case for the optimal.
     */
    private static void perfComparison(TwoSumII sol) {
        int n = 30_000;
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i + 1;              // [1, 2, 3, ..., 30000]
        }
        int target = (n - 1) + n;            // answer is the final pair
        int[] expected = {n - 1, n};

        System.out.println();
        System.out.println("--- perf: n = " + n + ", answer at the very last pair ---");

        long t0 = System.nanoTime();
        int[] b = sol.twoSumBruteForce(Arrays.copyOf(numbers, n), target);
        long t1 = System.nanoTime();
        int[] o = sol.twoSum(Arrays.copyOf(numbers, n), target);
        long t2 = System.nanoTime();

        System.out.printf("  brute force : %8.2f ms   %s%n",
                (t1 - t0) / 1e6, matches(b, expected) ? "correct" : "WRONG " + Arrays.toString(b));
        System.out.printf("  optimal     : %8.2f ms   %s%n",
                (t2 - t1) / 1e6, matches(o, expected) ? "correct" : "WRONG " + Arrays.toString(o));
        System.out.printf("  speedup     : %8.1fx%n", (double) (t1 - t0) / Math.max(t2 - t1, 1));
    }
}
