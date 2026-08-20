public class Zigzag {

    // A triple (a, b, c) is a zigzag if the middle value is a peak
    // (a < b > c) or a valley (a > b < c).
    static boolean isZigzag(int a, int b, int c) {
        return (a < b && b > c) || (a > b && b < c);
    }

    // Count how many consecutive triples in the array are zigzags.
    static int countZigzags(int[] nums) {
        int count = 0;
        for (int i = 0; i + 2 < nums.length; i++) {
            if (isZigzag(nums[i], nums[i + 1], nums[i + 2])) {
                count++;
            }
        }
        return count;
    }

    // Returns one flag per consecutive triple: 1 if it's a zigzag, 0 otherwise.
    // For an array of length n, the result has length n - 2.
    static int[] zigzagFlags(int[] nums) {
        int[] flags = new int[Math.max(0, nums.length - 2)];
        for (int i = 0; i + 2 < nums.length; i++) {
            flags[i] = isZigzag(nums[i], nums[i + 1], nums[i + 2]) ? 1 : 0;
        }
        return flags;
    }

    // Challenge signature: same as zigzagFlags, written without helpers.
    static int[] solution(int[] numbers) {
        int[] result = new int[numbers.length - 2];
        for (int i = 0; i < result.length; i++) {
            int a = numbers[i], b = numbers[i + 1], c = numbers[i + 2];
            result[i] = (a < b && b > c) || (a > b && b < c) ? 1 : 0;
        }
        return result;
    }

    public static void main(String[] args) {
        // quick sanity checks
        System.out.println(isZigzag(1, 3, 2)); // true  (peak)
        System.out.println(isZigzag(3, 1, 2)); // true  (valley)
        System.out.println(isZigzag(1, 2, 3)); // false (increasing)
        System.out.println(isZigzag(3, 2, 1)); // false (decreasing)
        System.out.println(isZigzag(2, 2, 3)); // false (tie)

        int[] nums = {1, 3, 2, 4, 1, 5, 5, 6};
        System.out.println("Zigzag triples in array: " + countZigzags(nums));

        int[] example = {1, 2, 1, 3, 4};
        System.out.println("zigzagFlags: " + java.util.Arrays.toString(zigzagFlags(example)));

        // challenge examples
        System.out.println(java.util.Arrays.toString(solution(new int[]{1, 2, 1, 3, 4}))); // [1, 1, 0]
        System.out.println(java.util.Arrays.toString(solution(new int[]{1, 2, 3, 4})));    // [0, 0]
    }
}
