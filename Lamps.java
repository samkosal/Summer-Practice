import java.util.Arrays;

public class Lamps {

    // A lamp at c with radius r lights every integer point in [c - r, c + r].
    // Sweep line: the coverage count only changes where a lamp starts or stops,
    // so sort those 2n boundaries and total the stretches where exactly one is on.
    static int solution(int[][] lamps) {
        int n = lamps.length;
        long[] open = new long[n];   // first lit point
        long[] close = new long[n];  // one past the last lit point
        for (int i = 0; i < n; i++) {
            open[i] = (long) lamps[i][0] - lamps[i][1];
            close[i] = (long) lamps[i][0] + lamps[i][1] + 1;
        }
        Arrays.sort(open);
        Arrays.sort(close);

        long lit = 0;
        int oi = 0, ci = 0, active = 0;
        long prev = open[0];
        while (ci < n) {
            // next boundary, whichever kind comes first
            long pos = (oi < n && open[oi] <= close[ci]) ? open[oi] : close[ci];
            if (active == 1) {
                lit += pos - prev;   // [prev, pos) sat under exactly one lamp
            }
            // apply every event landing on this exact position before moving on
            while (oi < n && open[oi] == pos) {
                active++;
                oi++;
            }
            while (ci < n && close[ci] == pos) {
                active--;
                ci++;
            }
            prev = pos;
        }
        return (int) lit;
    }

    // O(range * n) reference implementation, for checking the sweep on small inputs.
    static int bruteForce(int[][] lamps) {
        int lo = Integer.MAX_VALUE, hi = Integer.MIN_VALUE;
        for (int[] lamp : lamps) {
            lo = Math.min(lo, lamp[0] - lamp[1]);
            hi = Math.max(hi, lamp[0] + lamp[1]);
        }
        int count = 0;
        for (int p = lo; p <= hi; p++) {
            int hits = 0;
            for (int[] lamp : lamps) {
                if (Math.abs(p - lamp[0]) <= lamp[1]) {
                    hits++;
                }
            }
            if (hits == 1) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        // the worked example: ranges [-5,1], [-1,5], [1,3] leave -5..-2 and 4..5 alone
        System.out.println(solution(new int[][]{{-2, 3}, {2, 3}, {2, 1}})); // 6
        System.out.println(solution(new int[][]{{2, 1}, {6, 1}}));  // 6  two disjoint 3-point ranges
        System.out.println(solution(new int[][]{{0, 1}}));          // 3  [-1, 1]
        System.out.println(solution(new int[][]{{0, 1}, {1, 1}}));  // 2  only -1 and 2 are alone
        System.out.println(solution(new int[][]{{0, 2}, {0, 2}}));  // 0  identical lamps, nothing is alone
        System.out.println(solution(new int[][]{{0, 5}, {0, 1}}));  // 8  inner lamp carves a hole
        System.out.println(solution(new int[][]{{-1000000000, 100000}, {1000000000, 100000}})); // 400002

        // cross-check the sweep against brute force on random small inputs
        java.util.Random rnd = new java.util.Random(7);
        for (int t = 0; t < 5000; t++) {
            int[][] lamps = new int[1 + rnd.nextInt(6)][2];
            for (int[] lamp : lamps) {
                lamp[0] = rnd.nextInt(21) - 10;
                lamp[1] = 1 + rnd.nextInt(4);
            }
            if (solution(lamps) != bruteForce(lamps)) {
                System.out.println("MISMATCH: " + Arrays.deepToString(lamps));
                return;
            }
        }
        System.out.println("random cross-check passed");

        // timing at the constraint ceiling: 10^5 lamps
        int[][] big = new int[100000][2];
        for (int[] lamp : big) {
            lamp[0] = rnd.nextInt(2000000001) - 1000000000;
            lamp[1] = 1 + rnd.nextInt(100000);
        }
        long start = System.nanoTime();
        int answer = solution(big);
        System.out.println("10^5 lamps -> " + answer + " in "
                + (System.nanoTime() - start) / 1000000 + " ms");
    }
}
