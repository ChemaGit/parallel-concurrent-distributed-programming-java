import java.util.Random;

import static edu.rice.pcdp.PCDP.*;

public class MatrixMultiplyExample {

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    private static double[][] createMatrix(final int N) {
        final double[][] input = new double[N][N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                input[i][j] = rand.nextInt(100);
            }
        }

        return input;
    }

    /**
     * SeqMatrixMultiply
     * @param A
     * @param B
     * @param C
     * @param n
     */
    public static void seqMatrixMultiply(double[][]A, double[][]B, double[][]C, int n) {
        long startTime = System.nanoTime();
        forseq2d(0, n - 1, 0, n - 1, (i, j) -> {
            C[i][j] = 0;
            for(int k = 0;k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });

        long timeInNanos = System.nanoTime() - startTime;
        printResults("seqMatrixMultiply", timeInNanos, C[n - 1][n - 1]);
    }

    /**
     * ParMatrixMultiply
     * @param A
     * @param B
     * @param C
     * @param n
     */
    public static void parMatrixMultiply(double[][]A, double[][]B, double[][]C, int n) {
        long startTime = System.nanoTime();
        forall2d(0, n - 1, 0, n - 1, (i, j) -> {
            C[i][j] = 0;
            for(int k = 0;k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });

        long timeInNanos = System.nanoTime() - startTime;
        printResults("parMatrixMultiply", timeInNanos, C[n - 1][n - 1]);
    }

    /**
     * parMatrixMultiplyChunked
     * @param A
     * @param B
     * @param C
     * @param n
     */
    public static void parMatrixMultiplyChunked(double[][]A, double[][]B, double[][]C, int n) {
        long startTime = System.nanoTime();
        forall2dChunked(0, n - 1, 0, n - 1, (i, j) -> {
            C[i][j] = 0;
            for(int k = 0;k < n; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });

        long timeInNanos = System.nanoTime() - startTime;
        printResults("parMatrixMultiplyChunked", timeInNanos, C[n - 1][n - 1]);
    }

    private static void printResults(String name, long timeInNanos, double sum) {
        System.out.printf("   %s completed in %8.3f milliseconds, with sum = %8.5f \n", name, timeInNanos / 1e6, sum);
    }

    public static void main(String [] args) {
        double[][]A = createMatrix(512);
        double[][]B = createMatrix(512);
        double[][]C = createMatrix(512);
        seqMatrixMultiply(A,B,C,32);
        System.out.println();
        parMatrixMultiply(A,B,C,32);
        System.out.println();
        parMatrixMultiplyChunked(A,B,C,32);
    }
}
