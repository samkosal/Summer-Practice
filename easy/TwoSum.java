import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * ============================================================================
 * 1. TWO SUM                                                          [EASY]
 * https://leetcode.com/problems/two-sum/
 * ============================================================================
 *
 * Given an array of integers `nums` and an integer `target`, return the
 * indices of the two numbers such that they add up to `target`.
 *
 * You may assume that each input would have exactly one solution, and you
 * may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLES
 * ---------------------------------------------------------------------------
 *   Input:  nums = [2,7,11,15], target = 9
 *   Output: [0,1]
 *   Why:    nums[0] + nums[1] == 9
 *
 *   Input:  nums = [3,2,4], target = 6
 *   Output: [1,2]
 *
 *   Input:  nums = [3,3], target = 6
 *   Output: [0,1]
 *
 * ---------------------------------------------------------------------------
 * CONSTRAINTS
 * ---------------------------------------------------------------------------
 *   2 <= nums.length <= 10^4
 *   -10^9 <= nums[i] <= 10^9
 *   -10^9 <= target <= 10^9
 *   Only one valid answer exists.
 *
 * ---------------------------------------------------------------------------
 * FOLLOW-UP (don't read until you've solved it once)
 * ---------------------------------------------------------------------------
 *   Can you come up with an algorithm that is less than O(n^2) time?
 * ============================================================================
 */
public class TwoSum {

    /**
     * APPROACH 1 - BRUTE FORCE. Done. Leave this alone.
     *
     * Time:  O(?)
     * Space: O(?)
     */
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[] {};
    }

    /**
     * APPROACH 2 - HASHMAP. YOUR CODE GOES HERE.
     *
     * You already described this out loud: one pass, check-then-insert,
     * key = value from the array, map value = the index it was found at.
     *
     * Time:  O(?)
     * Space: O(?)
     */
    public int[] twoSum(int[] nums, int target) {
        // TODO: implement
        Map<Integer, Integer> store = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int answer = target - current;

            if (store.containsKey(answer)) {
                int value = store.get(answer);
                return new int[]{i, value};
            }
            else {
                store.put(current, i);
            }
        }
        return new int[] {};
    }

    // ========================================================================
    // TEST HARNESS - you shouldn't need to touch anything below this line.
    // Run with:  java TwoSum.java
    // ========================================================================

    public static void main(String[] args) {
        TwoSum sol = new TwoSum();

        // --- The three cases straight off LeetCode -------------------------
        check(sol, new int[] {2, 7, 11, 15}, 9,  new int[] {0, 1}, "basic case");
        check(sol, new int[] {3, 2, 4},      6,  new int[] {1, 2}, "answer is not the first pair");
        check(sol, new int[] {3, 3},         6,  new int[] {0, 1}, "duplicate values");

        // --- Edge cases worth thinking about --------------------------------
        check(sol, new int[] {-1, -2, -3, -4, -5}, -8, new int[] {2, 4}, "all negatives");
        check(sol, new int[] {0, 4, 3, 0},           0, new int[] {0, 3}, "zeros / target is 0");
        check(sol, new int[] {-3, 4, 3, 90},         0, new int[] {0, 2}, "negative + positive = 0");
        check(sol, new int[] {5, 75, 25},          100, new int[] {1, 2}, "answer at the very end");
        check(sol, new int[] {1, 6, 3, 3},            6, new int[] {2, 3}, "duplicates, pair is at the end");
        check(sol, new int[] {1000000000, 1000000000}, 2000000000, new int[] {0, 1}, "large values (overflow watch)");

        System.out.println();
        System.out.println("  brute force : " + passedBrute + " / " + total + " passed");
        System.out.println("  hashmap     : " + passedMap   + " / " + total + " passed");
        if (passedBrute != total || passedMap != total) {
            System.out.println();
            System.out.println("Look at the first FAIL above.");
        }
    }

    private static int passedBrute = 0;
    private static int passedMap   = 0;
    private static int total       = 0;

    private static void check(TwoSum sol, int[] nums, int target, int[] expected, String label) {
        total++;

        // Fresh copy for each call, so an in-place mutation can't leak between them.
        int[] brute = sol.twoSumBruteForce(Arrays.copyOf(nums, nums.length), target);
        int[] map   = sol.twoSum(Arrays.copyOf(nums, nums.length), target);

        boolean bOk = matches(brute, expected);
        boolean mOk = matches(map, expected);
        if (bOk) passedBrute++;
        if (mOk) passedMap++;

        System.out.printf("brute:%-4s  map:%-4s  %-32s nums=%-28s target=%-12d map_got=%s%n",
                bOk ? "PASS" : "FAIL",
                mOk ? "PASS" : "FAIL",
                label,
                Arrays.toString(nums),
                target,
                (map == null ? "null" : Arrays.toString(map))
                        + (mOk ? "" : "  want=" + Arrays.toString(expected)));
    }

    /** Index order doesn't matter, so compare as an unordered pair. */
    private static boolean matches(int[] actual, int[] expected) {
        if (actual == null || actual.length != 2) return false;
        return Math.min(actual[0], actual[1]) == Math.min(expected[0], expected[1])
            && Math.max(actual[0], actual[1]) == Math.max(expected[0], expected[1]);
    }
}
