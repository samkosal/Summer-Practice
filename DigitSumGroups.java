public class DigitSumGroups {

    // Sum of the digits in number[from, to).
    static int digitSum(String number, int from, int to) {
        int sum = 0;
        for (int i = from; i < to; i++) {
            sum += number.charAt(i) - '0';
        }
        return sum;
    }

    // One pass of steps 2-3: split into groups of k, replace each group by its
    // digit sum, concatenate in the same order. The last group may be shorter.
    static String round(String number, int k) {
        StringBuilder next = new StringBuilder();
        for (int i = 0; i < number.length(); i += k) {
            next.append(digitSum(number, i, Math.min(i + k, number.length())));
        }
        return next.toString();
    }

    // Challenge signature: repeat rounds until the number fits in k digits.
    static String solution(String number, int k) {
        while (number.length() > k) {
            StringBuilder next = new StringBuilder();
            for (int i = 0; i < number.length(); i += k) {
                int sum = 0;
                for (int j = i; j < Math.min(i + k, number.length()); j++) {
                    sum += number.charAt(j) - '0';
                }
                next.append(sum);
            }
            number = next.toString();
        }
        return number;
    }

    public static void main(String[] args) {
        // quick sanity checks: one round at a time
        System.out.println(round("11111222322", 3)); // 3474  (111|112|223|22 -> 3,4,7,4)
        System.out.println(round("3474", 3));        // 144   (347|4 -> 14,4)

        // challenge examples
        System.out.println(solution("11111222322", 3)); // 144
        System.out.println(solution("11111222223", 3)); // 135
        System.out.println(solution("00000000", 3));    // 000  (leading zeros kept)
        System.out.println(solution("123", 4));         // 123  (already short enough)
        System.out.println(solution("9999", 2));        // 99
    }
}
