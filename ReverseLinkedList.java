import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/*
 * ============================================================================
 * 206. REVERSE LINKED LIST                                             [EASY]
 * https://leetcode.com/problems/reverse-linked-list/
 * ============================================================================
 *
 * Given the `head` of a singly linked list, reverse the list, and return
 * the reversed list.
 *
 * ---------------------------------------------------------------------------
 * EXAMPLES
 * ---------------------------------------------------------------------------
 *   Input:  head = [1,2,3,4,5]
 *   Output: [5,4,3,2,1]
 *
 *   Input:  head = [1,2]
 *   Output: [2,1]
 *
 *   Input:  head = []
 *   Output: []          <-- an empty list is valid input. head will be null.
 *
 * ---------------------------------------------------------------------------
 * CONSTRAINTS
 * ---------------------------------------------------------------------------
 *   The number of nodes in the list is in the range [0, 5000]
 *   -5000 <= Node.val <= 5000
 *
 * ---------------------------------------------------------------------------
 * FOLLOW UP (LeetCode asks this directly, so both slots exist below)
 * ---------------------------------------------------------------------------
 *   A linked list can be reversed either iteratively or recursively.
 *   Could you implement both?
 *
 * ---------------------------------------------------------------------------
 * WHAT MAKES THIS DIFFERENT FROM THE ARRAY PROBLEMS
 * ---------------------------------------------------------------------------
 *   An array gives you random access: numbers[left], numbers[right], jump
 *   anywhere for free. A singly linked list gives you exactly two things:
 *   a reference to a node, and that node's `next`. No indexing. No .length.
 *   No way to walk backwards.
 *
 *   So the danger is specific: the instant you overwrite some node's `next`
 *   pointer, you have destroyed your only route to the rest of the list.
 *   Everything past it becomes unreachable.
 *
 *   The whole problem is ordering your operations so that never happens.
 * ============================================================================
 */
public class ReverseLinkedList {

    /**
     * Definition for singly-linked list. This is exactly what LeetCode gives
     * you in the editor. Do not modify it.
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // ========================================================================
    // YOUR CODE
    // ========================================================================

    /**
     * APPROACH 1 - ITERATIVE.
     *
     * Walk the list once, flipping each `next` pointer as you go.
     * Reuse the existing nodes. Do not allocate new ones.
     *
     * Time:  O(?)
     * Space: O(?)
     */
    public ListNode reverseListIterative(ListNode head) {
        // TODO: implement
        if (head == null) {
            return null;
        }
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        
        while (tail != head) {
            ListNode temp = head;
            head = head.next;
            temp.next = tail.next;
            tail.next = temp;
        }

        return head;
        
    }

    /**
     * APPROACH 2 - RECURSIVE.
     *
     * Same result, no loop. Ask yourself: if the rest of the list after `head`
     * were already reversed for you, what single pointer change would finish
     * the job? That is the recursive step. Then find the case that stops it.
     *
     * Time:  O(?)
     * Space: O(?)  <-- careful. This is NOT the same as the iterative version,
     *                  and the reason is not visible anywhere in your source.
     */
    public ListNode reverseListRecursive(ListNode head) {
        // TODO: implement
        return null;
    }

    // ========================================================================
    // TEST HARNESS - you shouldn't need to touch anything below this line.
    // Run with:  java ReverseLinkedList.java
    // ========================================================================

    public static void main(String[] args) {

        ReverseLinkedList sol = new ReverseLinkedList();

        // --- Straight off LeetCode -----------------------------------------
        check(sol, new int[] {1, 2, 3, 4, 5}, "basic case");
        check(sol, new int[] {1, 2},          "two nodes");
        check(sol, new int[] {},              "EMPTY list - head is null");

        // --- Edge cases worth thinking about --------------------------------
        check(sol, new int[] {1},                "single node");
        check(sol, new int[] {3, 3, 3},          "all identical - looks unchanged");
        check(sol, new int[] {1, 1, 2, 2},       "duplicate values");
        check(sol, new int[] {-5, 0, 5},         "negatives and zero");
        check(sol, new int[] {5000, -5000},      "constraint boundaries");
        check(sol, new int[] {1, 2, 3, 4, 5, 6, 7, 8}, "longer list");

        System.out.println();
        System.out.println("  iterative : " + passedIter + " / " + total + " passed");
        System.out.println("  recursive : " + passedRec  + " / " + total + " passed");

        if (passedIter == total && passedRec == total) {
            stackDepthDemo(sol);
        } else {
            System.out.println();
            System.out.println("Look at the first FAIL above.");
            System.out.println("(The stack-depth demo unlocks once both approaches are green.)");
        }
    }

    // ------------------------------------------------------------------------

    private static int passedIter = 0;
    private static int passedRec  = 0;
    private static int total      = 0;

    private static void check(ReverseLinkedList sol, int[] values, String label) {
        total++;

        int[] expected = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            expected[i] = values[values.length - 1 - i];
        }

        Result iter = run(sol::reverseListIterative, values, expected);
        Result rec  = run(sol::reverseListRecursive, values, expected);

        if (iter.ok) passedIter++;
        if (rec.ok)  passedRec++;

        System.out.printf("iter:%-4s  rec:%-4s  %-32s in=%-22s want=%-22s %s%n",
                iter.ok ? "PASS" : "FAIL",
                rec.ok  ? "PASS" : "FAIL",
                label,
                Arrays.toString(values),
                Arrays.toString(expected),
                notes(iter, rec));
    }

    private static String notes(Result iter, Result rec) {
        if (iter.ok && rec.ok) return "";
        StringBuilder sb = new StringBuilder();
        if (!iter.ok) sb.append("  iter_got=").append(iter.detail);
        if (!rec.ok)  sb.append("  rec_got=").append(rec.detail);
        return sb.toString();
    }

    // ------------------------------------------------------------------------

    private static class Result {
        boolean ok;
        String detail;
        Result(boolean ok, String detail) { this.ok = ok; this.detail = detail; }
    }

    /**
     * Runs one attempt and checks three separate things:
     *   1. it terminates and produces the right values in the right order
     *   2. it did not create a cycle (a very common reversal bug)
     *   3. it reused the original nodes instead of allocating new ones
     */
    private static Result run(java.util.function.Function<ListNode, ListNode> attempt,
                              int[] originalValues, int[] expected) {

        // Build the input here, and remember exactly which node objects it
        // contains, so we can tell afterwards whether they were reused.
        ListNode head = build(originalValues);
        Set<ListNode> originalNodes = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
        for (ListNode n = head; n != null; n = n.next) originalNodes.add(n);

        ListNode out;
        try {
            out = attempt.apply(head);
        } catch (StackOverflowError e) {
            return new Result(false, "StackOverflowError");
        } catch (Throwable t) {
            return new Result(false, t.getClass().getSimpleName());
        }

        // Walk the result with a hard cap, so a cycle can't hang the harness.
        int cap = originalValues.length + 10;
        List<Integer> got = new ArrayList<>();
        int newNodes = 0;
        Set<ListNode> seen = java.util.Collections.newSetFromMap(new IdentityHashMap<>());
        for (ListNode n = out; n != null; n = n.next) {
            if (!seen.add(n))     return new Result(false, "CYCLE detected");
            if (got.size() > cap) return new Result(false, "runs too long / probable cycle");
            if (!originalNodes.contains(n)) newNodes++;
            got.add(n.val);
        }

        int[] gotArr = got.stream().mapToInt(Integer::intValue).toArray();
        if (!Arrays.equals(gotArr, expected)) {
            return new Result(false, Arrays.toString(gotArr));
        }
        if (newNodes > 0) {
            return new Result(false, "values correct, but allocated " + newNodes
                    + " NEW node(s) instead of relinking - not in place");
        }
        return new Result(true, Arrays.toString(gotArr));
    }

    /** Builds a list from an array. An empty array produces null. */
    private static ListNode build(int[] values) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        for (int v : values) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    /**
     * Both approaches are correct at this point. This shows what the recursive
     * version's space complexity actually costs you at runtime.
     *
     * LeetCode caps this problem at 5000 nodes. That cap is not arbitrary --
     * it sits just below where the default JVM call stack gives out. Go past
     * it and the difference between O(1) and O(n) space stops being notation.
     */
    private static void stackDepthDemo(ReverseLinkedList sol) {
        System.out.println();
        System.out.println("--- stack depth: same algorithm, two implementations ---");

        for (int n : new int[] {5_000, 100_000}) {
            int[] values = new int[n];
            for (int i = 0; i < n; i++) values[i] = i;

            System.out.printf("  n = %-8d iterative: %-26s recursive: %s%n",
                    n, attempt(() -> sol.reverseListIterative(build(values))),
                       attempt(() -> sol.reverseListRecursive(build(values))));
        }
        System.out.println();
        System.out.println("  Both are O(n) time. Only one of them is O(1) space.");
    }

    private static String attempt(java.util.function.Supplier<ListNode> s) {
        try {
            ListNode r = s.get();
            int count = 0;
            for (ListNode n = r; n != null && count < 200_000; n = n.next) count++;
            return "ok (" + count + " nodes)";
        } catch (StackOverflowError e) {
            return "*** StackOverflowError ***";
        } catch (Throwable t) {
            return "*** " + t.getClass().getSimpleName() + " ***";
        }
    }
}
