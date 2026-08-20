import java.util.Scanner;

/*
 * ============================================================================
 * STAR PYRAMID GENERATOR
 * ============================================================================
 *
 * Reads a height from the user and prints a centered pyramid of '*' of that
 * height.
 *
 *   height = 5
 *
 *       *          row 1:  4 spaces, 1 star
 *      ***         row 2:  3 spaces, 3 stars
 *     *****        row 3:  2 spaces, 5 stars
 *    *******       row 4:  1 space,  7 stars
 *   *********      row 5:  0 spaces, 9 stars
 *
 * THE PATTERN
 * ---------------------------------------------------------------------------
 *   For a pyramid of height n, row i (counting from 1):
 *       spaces = n - i        shrinks by 1 each row
 *       stars  = 2*i - 1      grows by 2 each row, so it is always odd
 *
 *   Odd star counts are what make the rows center on a single apex. The last
 *   row is 2*n - 1 wide, which is the width of the whole pyramid.
 *
 * Run with:  java StarPyramid.java
 * ============================================================================
 */
public class StarPyramid {

    /** Largest height accepted, so a fat-fingered 1000000 can't flood the console. */
    private static final int MAX_HEIGHT = 100;

    /**
     * Builds the pyramid as a single string rather than printing as it goes.
     *
     * Returning the string instead of println-ing inside the loop keeps the
     * shape logic separate from the I/O -- it can be tested, reused, or written
     * somewhere other than the console without touching this method.
     *
     * Time:  O(n^2)  -- there are n^2 characters to produce, so this is optimal
     * Space: O(n^2)  -- the finished pyramid itself
     */
    public static String pyramid(int height) {
        StringBuilder sb = new StringBuilder();

        for (int row = 1; row <= height; row++) {
            for (int s = 0; s < height - row; s++) {
                sb.append(' ');
            }
            for (int star = 0; star < 2 * row - 1; star++) {
                sb.append('*');
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    // ========================================================================
    // INPUT HANDLING
    // ========================================================================

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Star pyramid generator");
        System.out.println("Enter a height between 1 and " + MAX_HEIGHT + " (or 'q' to quit).");

        while (true) {
            System.out.print("> ");

            // hasNextLine() is false once stdin is closed (Ctrl+Z on Windows,
            // or a piped file running out). Without this check the loop would
            // spin forever on EOF.
            if (!in.hasNextLine()) {
                System.out.println();
                break;
            }

            String line = in.nextLine().trim();

            if (line.equalsIgnoreCase("q") || line.equalsIgnoreCase("quit")) {
                break;
            }
            if (line.isEmpty()) {
                continue;
            }

            int height;
            try {
                height = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                // Deliberately not Scanner.nextInt(): on bad input nextInt throws
                // and leaves the garbage token sitting in the buffer, so the next
                // read grabs the same garbage again. Reading whole lines and
                // parsing them ourselves means bad input is consumed either way.
                System.out.println("  '" + line + "' is not a whole number. Try again.");
                continue;
            }

            if (height < 1) {
                System.out.println("  Height must be at least 1.");
                continue;
            }
            if (height > MAX_HEIGHT) {
                System.out.println("  Height must be at most " + MAX_HEIGHT + ".");
                continue;
            }

            System.out.println();
            System.out.print(pyramid(height));
            System.out.println();
        }

        System.out.println("Bye.");
        in.close();
    }
}
