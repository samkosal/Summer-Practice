/*
 * ============================================================================
 * 9. PALINDROME NUMBER                                                  [EASY]
 * https://leetcode.com/problems/palindrome-number/
 * ============================================================================
 *
 * Given an integer x, return true if x is a palindrome, and false otherwise.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLES
 * ---------------------------------------------------------------------------
 *   Input:  x = 121
 *   Output: true
 *   Why:    121 reads as 121 from left to right and from right to left.
 *
 *   Input:  x = -121
 *   Output: false
 *   Why:    Left to right it reads -121. Right to left it becomes 121-.
 *
 *   Input:  x = 10
 *   Output: false
 *   Why:    Reads 01 from right to left.
 *
 * ---------------------------------------------------------------------------
 * CONSTRAINTS
 * ---------------------------------------------------------------------------
 *   -2^31 <= x <= 2^31 - 1     <-- that is exactly Java's int range.
 *                                  Not a hint. A trap. See below.
 *
 * ---------------------------------------------------------------------------
 * FOLLOW UP (LeetCode asks this directly, so both slots exist below)
 * ---------------------------------------------------------------------------
 *   Could you solve it without converting the integer to a string?
 *
 * ---------------------------------------------------------------------------
 * THE ACTUAL REQUIREMENT
 * ---------------------------------------------------------------------------
 *   The string version is four lines and teaches you nothing. It is your
 *   baseline, you write it first in an interview to buy thinking time, and
 *   then you throw it away.
 *
 *   The real question is approach 2: decide this using arithmetic only. No
 *   String, no char[], no StringBuilder, no Integer.toString, no split of any
 *   kind. A fixed number of int variables and the operators / and %.
 *
 *   Two things make that harder than it looks. Both matter:
 *
 *     1. "Palindrome" is a property of the WRITTEN DECIMAL FORM, not of the
 *        number. The minus sign is part of what you read. That is the only
 *        reason -121 is false.
 *
 *     2. The input can be 10 digits long. Reversing a 10-digit number can
 *        produce a value that does not fit in an int. Java will not throw
 *        when that happens. It will wrap around silently and keep going.
 *        The overflow demo at the bottom of main() shows you this happening.
 *
 *   There is a way to sidestep problem 2 entirely rather than defend against
 *   it. Finding it is the point of this problem.
 * ============================================================================
 */
public class PalindromeNumber {

    // ========================================================================
    // YOUR CODE
    // ========================================================================

    /**
     * APPROACH 1 - BRUTE FORCE (STRING CONVERSION).
     *
     * The thing the follow-up tells you not to do. Write it anyway. It is your
     * baseline, and the harness below runs both so you can see them agree.
     *
     * Convert to a String, then two pointers from the outside in. Note that
     * you do NOT need an early-exit guard for negatives or trailing zeros
     * here -- work out for yourself why the character comparison already
     * handles both. Adding the guard anyway is not wrong, just redundant.
     *
     * Time:  O(?)
     * Space: O(?)   <-- this is the one that disqualifies it. Say what it is
     *                   and, more importantly, say what n means for a number.
     */
    public boolean isPalindromeString(int x) {
        // TODO: implement
        // if ((x != 0 && x % 10 == 0) || (x < 0)) {
        //     return false;
        // }
        char[] charArray = Integer.toString(x).toCharArray();
        int left = 0;
        int right = charArray.length - 1;


        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * APPROACH 2 - NO STRING CONVERSION.
     *
     * Arithmetic only: / and % and a few int variables.
     *
     * Start with the cases you can reject before doing any work at all. You
     * already know two categories. One of them has exactly one exception --
     * get that guard right and it does real work for you later, because it
     * rules out an input that would otherwise ruin the clean version.
     *
     * Then, the actual question. The obvious move is "reverse the whole
     * number, compare to the original," and it has the overflow problem
     * described in the header. So:
     *
     *   - To compare a number against its own reverse, do you actually need
     *     the whole reverse? What is the least you could build and still be
     *     certain of the answer?
     *   - If you are peeling digits off the back of x and stacking them into
     *     a growing value, x shrinks while that value grows. What is true at
     *     the moment they cross? How would you detect that moment without
     *     knowing the digit count in advance?
     *   - Even and odd digit counts do not finish in the same state. Check
     *     121 and 1221 by hand and see how the two differ at the end.
     *
     * Time:  O(?)
     * Space: O(?)   <-- must be O(1). No allocation of any kind.
     */
    public boolean isPalindrome(int x) {
        // TODO: implement
        return false;
    }

    // ========================================================================
    // TEST HARNESS - you shouldn't need to touch anything below this line.
    // Run with:  java PalindromeNumber.java
    // ========================================================================

    public static void main(String[] args) {

        PalindromeNumber sol = new PalindromeNumber();

        // --- Straight off LeetCode -----------------------------------------
        check(sol, 121,          true,  "basic case");
        check(sol, -121,         false, "negative - the minus sign counts");
        check(sol, 10,           false, "trailing zero");

        // --- Edge cases worth thinking about --------------------------------
        check(sol, 0,            true,  "ZERO - ends in 0 but IS a palindrome");
        check(sol, 7,            true,  "single digit");
        check(sol, -7,           false, "single NEGATIVE digit");
        check(sol, 11,           true,  "two digits, even length");
        check(sol, 12,           false, "two digits, not a palindrome");
        check(sol, 100,          false, "two trailing zeros");
        check(sol, 1001,         true,  "even length, interior zeros");
        check(sol, 1221,         true,  "even length - stops differently than odd");
        check(sol, 1000021,      false, "interior zeros, NOT a palindrome");
        check(sol, -101,         false, "negative that would be a palindrome unsigned");

        // --- Where the int range starts to bite ------------------------------
        check(sol, 1000000001,   true,  "10 digits, palindrome");
        check(sol, 1234554321,   true,  "10 digits, palindrome");
        check(sol, 2147447412,   true,  "10 digits, palindrome, just under INT_MAX");
        check(sol, 1234567899,   false, "10 digits, reversal OVERFLOWS int");
        check(sol, 1534236469,   false, "10 digits, reversal OVERFLOWS int");
        check(sol, 2147483647,   false, "INT_MAX exactly");
        check(sol, -2147483648,  false, "INT_MIN - breaks anything using Math.abs");

        System.out.println();
        System.out.println("  string  : " + passedStr + " / " + total + " passed");
        System.out.println("  numeric : " + passedNum + " / " + total + " passed");

        if (passedStr == total && passedNum == total) {
            overflowDemo();
        } else {
            System.out.println();
            System.out.println("Look at the first FAIL above.");
            System.out.println("(The overflow demo unlocks once both approaches are green.)");
        }
    }

    // ------------------------------------------------------------------------

    private static int passedStr = 0;
    private static int passedNum = 0;
    private static int total     = 0;

    private static void check(PalindromeNumber sol, int x, boolean expected, String label) {
        total++;

        Result str = run(() -> sol.isPalindromeString(x), expected);
        Result num = run(() -> sol.isPalindrome(x),       expected);

        if (str.ok) passedStr++;
        if (num.ok) passedNum++;

        System.out.printf("str:%-4s  num:%-4s  %-46s x=%-12d want=%-6s %s%n",
                str.ok ? "PASS" : "FAIL",
                num.ok ? "PASS" : "FAIL",
                label,
                x,
                expected,
                notes(str, num));
    }

    private static String notes(Result str, Result num) {
        if (str.ok && num.ok) return "";
        StringBuilder sb = new StringBuilder();
        if (!str.ok) sb.append(" str_got=").append(str.detail);
        if (!num.ok) sb.append(" num_got=").append(num.detail);
        return sb.toString();
    }

    private static class Result {
        boolean ok;
        String detail;
        Result(boolean ok, String detail) { this.ok = ok; this.detail = detail; }
    }

    private static Result run(java.util.function.BooleanSupplier attempt, boolean expected) {
        try {
            boolean got = attempt.getAsBoolean();
            return new Result(got == expected, String.valueOf(got));
        } catch (Throwable t) {
            return new Result(false, t.getClass().getSimpleName());
        }
    }

    /**
     * Both approaches are correct at this point. This shows the trap you just
     * avoided -- what "reverse the whole number into an int" actually does
     * once the input gets long enough.
     */
    private static void overflowDemo() {
        System.out.println();
        System.out.println("--- reversing the WHOLE number into an int ---");
        System.out.println();
        System.out.printf("  %-14s %-16s %-16s %s%n", "x", "true reversal", "stored in an int", "");

        for (int x : new int[] {121, 1000000001, 1234567899, 1534236469, 1999999999, 2147483647}) {
            long rev = 0;
            for (long t = x; t != 0; t /= 10) rev = rev * 10 + t % 10;
            System.out.printf("  %-14d %-16d %-16d %s%n",
                    x, rev, (int) rev, rev > Integer.MAX_VALUE ? "<-- OVERFLOWED, silently" : "");
        }

        System.out.println();
        System.out.println("  Java did not throw. It wrapped the value around and carried on.");
        System.out.println();
        System.out.println("  Now the subtle part, and it is worth sitting with:");
        System.out.println("  a full-reversal solution that overflows like this STILL passes");
        System.out.println("  LeetCode. Every single time. Work out why before you read on --");
        System.out.println("  the question is: which inputs can actually overflow, and what");
        System.out.println("  answer does the wrapped comparison give for those inputs?");
        System.out.println();
        System.out.println("  Passing the judge is not the same as being correct. You would");
        System.out.println("  be relying on an accident. Try the same trick on LeetCode 7");
        System.out.println("  (Reverse Integer), where the overflow IS the whole problem, and");
        System.out.println("  the accident stops saving you.");
    }
}
