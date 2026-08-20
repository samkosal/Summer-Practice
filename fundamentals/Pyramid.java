public class Pyramid {

    // Print an ASCII pyramid with n levels.
    // Row i has (2*i - 1) asterisks, centered using the width of the base row.
    static void printPyramid(int n) {
        int width = 2 * n - 1; // width of the base row (the widest one)

        for (int i = 1; i <= n; i++) {
            int stars = 2 * i - 1;
            int padding = (width - stars) / 2;

            StringBuilder row = new StringBuilder();
            for (int p = 0; p < padding; p++) {
                row.append(' ');
            }
            for (int s = 0; s < stars; s++) {
                row.append('*');
            }
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        printPyramid(10);
    }
}
