import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * ============================================================================
 * 3. LONGEST SUBSTRING WITHOUT REPEATING CHARACTERS                   [MEDIUM]
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * ============================================================================
 *
 * Given a string s, find the length of the longest substring without
 * duplicate characters.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLES
 * ---------------------------------------------------------------------------
 *   Input:  s = "abcabcbb"
 *   Output: 3
 *   Why:    "abc", length 3. "bca" and "cab" are equally correct answers --
 *           you return the LENGTH, not the substring, so ties don't matter.
 *
 *   Input:  s = "bbbbb"
 *   Output: 1
 *   Why:    "b".
 *
 *   Input:  s = "pwwkew"
 *   Output: 3
 *   Why:    "wke". Note "pwke" is a SUBSEQUENCE, not a substring, and does
 *           not count.
 *
 * ---------------------------------------------------------------------------
 * CONSTRAINTS
 * ---------------------------------------------------------------------------
 *   0 <= s.length <= 10^5      <-- zero is legal. s can be "".
 *   s consists of English letters, digits, symbols and spaces.
 *                              <-- NOT just lowercase a-z. Read that again.
 *
 * ---------------------------------------------------------------------------
 * THE ACTUAL REQUIREMENT
 * ---------------------------------------------------------------------------
 *   Two words in the problem statement are doing all the work:
 *
 *     SUBSTRING   - contiguous. A block of adjacent characters, no gaps.
 *                   "pwke" is not a substring of "pwwkew" and never will be.
 *                   If you find yourself skipping characters, you have
 *                   quietly switched to a different, harder problem.
 *
 *     DUPLICATE   - within the window only. A character that repeats later
 *                   in the string is fine, as long as it doesn't repeat
 *                   inside the stretch you're currently measuring.
 *
 *   And one line in the constraints is a trap:
 *
 *     "letters, digits, symbols and spaces" means the alphabet is not 26.
 *     Any solution built on `new int[26]` and `c - 'a'` is wrong here, and
 *     it will not be wrong in a way the first three examples reveal.
 *
 *   The interesting question is not "can you find the answer" -- brute force
 *   finds it. It is: can you find it while looking at each character a
 *   BOUNDED number of times, rather than re-examining stretches you have
 *   already cleared?
 * ============================================================================
 */
public class LongestSubstring {

    // ========================================================================
    // YOUR CODE
    // ========================================================================

    /**
     * APPROACH 1 - BRUTE FORCE.
     *
     * The direct reading of the problem: consider the candidate stretches,
     * check each one for duplicates, keep the longest that is clean.
     *
     * Do not optimize this. Its job is to be obviously correct so that the
     * harness can hold your real solution to account.
     *
     * Time:  O(?)
     * Space: O(?)
     */
    public int lengthOfLongestSubstringBruteForce(String s) {
        // TODO: implement
        int highest = 0;

        for (int i = 0; i < s.length(); i++) {
            Set<Character> count = new HashSet<>();
            
            for (int j = i; j < s.length(); j++) {
                if (count.contains(s.charAt(j))) {
                    break;
                }
                if (j - i + 1 > highest) {
                    highest = j - i + 1;
                }

                count.add(s.charAt(j));
            }
        }
        return highest;
    }

    /**
     * APPROACH 2 - THE ONE THAT ANSWERS THE QUESTION.
     *
     * One pass. Each character enters your consideration a bounded number of
     * times and then never again.
     *
     * Do not read these until you have tried it yourself:
     *
     *   - Brute force throws away everything it learned each time it restarts
     *     from a new starting position. When you have verified that some
     *     stretch is duplicate-free, what does that already tell you about
     *     the stretches sitting inside it?
     *   - Suppose you are tracking a region of the string with a start and an
     *     end, and you advance the end by one character. Exactly two things
     *     can happen. What are they, and what is the cheapest possible
     *     response to each?
     *   - When the new character IS already inside your region, you must move
     *     the start. How far? Moving it one step at a time works. Is there
     *     information you could have stored earlier that would let you jump
     *     it straight to the right place instead?
     *   - Careful with that jump. The position you stored might be BEHIND
     *     where your start already is. What happens if you move a boundary
     *     backwards, and what single operation prevents it?
     *
     * Time:  O(?)
     * Space: O(?)   <-- bounded by the alphabet, not by s.length. Say why.
     */
    public int lengthOfLongestSubstring(String s) {
        // TODO: implement
        int highest = 0;
        int left = 0;

        Set<Character> window = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            
            while ((left != right - 1) && window.contains(s.charAt(right))) {
                window.remove(s.charAt(left));
                left++;
            }
                
            
            window.add(s.charAt(right));
            highest = Math.max(highest, window.size());
        }
        return highest;
    }

    // ========================================================================
    // TEST HARNESS - you shouldn't need to touch anything below this line.
    // Run with:  java medium/LongestSubstring.java
    // ========================================================================

    public static void main(String[] args) {

        LongestSubstring sol = new LongestSubstring();

        // --- Straight off LeetCode -----------------------------------------
        check(sol, "abcabcbb", 3, "basic case");
        check(sol, "bbbbb",    1, "all identical");
        check(sol, "pwwkew",   3, "answer is not at the start");

        // --- Edge cases worth thinking about --------------------------------
        check(sol, "",         0, "EMPTY string - legal input, length 0");
        check(sol, "a",        1, "single character");
        check(sol, " ",        1, "a single SPACE is a character");
        check(sol, "au",       2, "two distinct");
        check(sol, "aa",       1, "two identical");
        check(sol, "abcdefg",  7, "no duplicates at all - answer is the whole string");

        // --- The ones that catch a nearly-correct sliding window --------------
        check(sol, "abba",     2, "start must NOT be allowed to move backwards");
        check(sol, "dvdf",     3, "answer straddles a repeat - 'vdf'");
        check(sol, "tmmzuxt",  5, "'mzuxt' - the stale index trap");
        check(sol, "abcb",     3, "repeat of a char still inside the window");
        check(sol, "aab",      2, "duplicate immediately at the front");
        check(sol, "cdd",      2, "duplicate immediately at the back");

        // --- Alphabet is bigger than 26 --------------------------------------
        check(sol, "1234512345",   5, "digits, not letters");
        check(sol, "!@#$%^&*()",  10, "symbols only");
        check(sol, "Aa",           2, "case matters - 'A' and 'a' are different");
        check(sol, "a b c a",      3, "the repeat that limits the window is the SPACE");
        check(sol, "ab cd",        5, "a space sits INSIDE the winning window");

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

    private static void check(LongestSubstring sol, String s, int expected, String label) {
        total++;

        Result brute = run(() -> sol.lengthOfLongestSubstringBruteForce(s), expected);
        Result opt   = run(() -> sol.lengthOfLongestSubstring(s),           expected);

        if (brute.ok) passedBrute++;
        if (opt.ok)   passedOpt++;

        System.out.printf("brute:%-4s  opt:%-4s  %-48s s=%-16s want=%-4d %s%n",
                brute.ok ? "PASS" : "FAIL",
                opt.ok   ? "PASS" : "FAIL",
                label,
                quote(s),
                expected,
                notes(brute, opt));
    }

    private static String notes(Result brute, Result opt) {
        if (brute.ok && opt.ok) return "";
        StringBuilder sb = new StringBuilder();
        if (!brute.ok) sb.append(" brute_got=").append(brute.detail);
        if (!opt.ok)   sb.append(" opt_got=").append(opt.detail);
        return sb.toString();
    }

    /** Shows "" and " " as something you can actually see in the output. */
    private static String quote(String s) {
        return "\"" + s + "\"";
    }

    private static class Result {
        boolean ok;
        String detail;
        Result(boolean ok, String detail) { this.ok = ok; this.detail = detail; }
    }

    private static Result run(java.util.function.IntSupplier attempt, int expected) {
        try {
            int got = attempt.getAsInt();
            return new Result(got == expected, String.valueOf(got));
        } catch (Throwable t) {
            return new Result(false, t.getClass().getSimpleName());
        }
    }

    /**
     * Both approaches are correct at this point. This shows what correct costs.
     *
     * Deliberately exceeds the LeetCode length constraint in order to make the
     * gap visible. Watch the numbers rather than trusting the notation.
     *
     * There is a genuinely interesting observation buried in this output, and
     * it is worth an interview conversation: the alphabet here is bounded --
     * printable ASCII, fewer than 100 distinct characters. So no duplicate-free
     * window can ever be longer than that. Think about what that does to the
     * brute force's inner loop, and whether "O(n^2)" is really telling you the
     * truth about the running time you are about to see.
     */
    private static void perfComparison(LongestSubstring sol) {
        int n = 200_000;
        Random rng = new Random(42);
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char) (33 + rng.nextInt(94)));   // printable ASCII
        }
        String s = sb.toString();

        System.out.println();
        System.out.println("--- perf: n = " + n + " random printable ASCII ---");

        long t0 = System.nanoTime();
        int b = sol.lengthOfLongestSubstringBruteForce(s);
        long t1 = System.nanoTime();
        int o = sol.lengthOfLongestSubstring(s);
        long t2 = System.nanoTime();

        System.out.printf("  brute force : %8.2f ms   answer = %d%n", (t1 - t0) / 1e6, b);
        System.out.printf("  optimal     : %8.2f ms   answer = %d%n", (t2 - t1) / 1e6, o);
        System.out.printf("  speedup     : %8.1fx%n", (double) (t1 - t0) / Math.max(t2 - t1, 1));

        if (b != o) {
            System.out.println("  *** the two approaches DISAGREE - one of them is wrong ***");
        }
    }
}
