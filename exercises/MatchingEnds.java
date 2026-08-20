public class MatchingEnds {

    // Two words "match" when they share both their first and last character.
    static boolean sameEnds(String a, String b) {
        return a.charAt(0) == b.charAt(0)
            && a.charAt(a.length() - 1) == b.charAt(b.length() - 1);
    }

    // Returns one flag per consecutive pair.
    // For an array of length n, the result has length n - 1.
    static boolean[] matchFlags(String[] words) {
        boolean[] flags = new boolean[Math.max(0, words.length - 1)];
        for (int i = 0; i < flags.length; i++) {
            flags[i] = sameEnds(words[i], words[i + 1]);
        }
        return flags;
    }

    // Challenge signature: same as matchFlags, written without helpers.
    static boolean[] solution(String[] words) {
        boolean[] result = new boolean[words.length - 1];
        for (int i = 0; i < result.length; i++) {
            String a = words[i], b = words[i + 1];
            result[i] = a.charAt(0) == b.charAt(0)
                     && a.charAt(a.length() - 1) == b.charAt(b.length() - 1);
        }
        return result;
    }

    public static void main(String[] args) {
        // quick sanity checks
        System.out.println(sameEnds("abcd", "abdd")); // true  (a...d / a...d)
        System.out.println(sameEnds("abdd", "da"));   // false (different first)
        System.out.println(sameEnds("da", "dd"));     // false (different last)
        System.out.println(sameEnds("a", "a"));       // true  (first == last == 'a')

        // challenge examples
        System.out.println(java.util.Arrays.toString(
                solution(new String[]{"abcd", "abdd", "da", "dd"}))); // [true, false, false]
        System.out.println(java.util.Arrays.toString(
                solution(new String[]{"a", "a"})));                   // [true]
    }
}
