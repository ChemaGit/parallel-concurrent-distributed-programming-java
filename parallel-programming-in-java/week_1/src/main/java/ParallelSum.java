import edu.rice.pcdp.PCDP;

import java.util.Random;

import static edu.rice.pcdp.PCDP.finish;
import static edu.rice.pcdp.PCDP.async;

public class ParallelSum {

    private static double sum1;
    private static double sum2;

    /**
     * <p>seqArraySum</p>
     *
     * @param X an array of double
     * @return a double
     */
    public static double seqArraySum(double[] X) {
        long startTime = System.nanoTime();
        sum1 = 0;
        sum2 = 0;
        // Compute sum of lower half of array
        for(int i = 0; i < X.length / 2; i++) {
            sum1 += 1 / X[i];
        }

        // Compute sum of upper half of array
        for(int i = X.length / 2; i < X.length;i++) {
            sum2 += 1 / X[i];
        }
        // Combine sum1 and sum2
        double sum = sum1 + sum2;
        long timeInNanos = System.nanoTime() - startTime;
        printResults("seqArraySum", timeInNanos, sum);
        return sum;
    }

    /**
     * <p>parArraySum</p>
     *
     * @param X an array of double
     * @return a double
     */
    public static double parArraySum(double[] X) /*throws SuspendableException*/{
        // Start of Task T0 (main program)
        long startTime = System.nanoTime();
        sum1 = 0;
        sum2 = 0;
        finish(() -> {
        async(()-> {
                    // Compute sum of lower half of array
                    for (int i = 0; i < X.length / 2; i++) {
                        sum1 += 1 / X[i];
                    }
                });

        // Compute sum of upper half of array
        for(int i = X.length / 2; i < X.length;i++) {
            sum2 += 1 / X[i];
        }
        });
        // Combine sum1 and sum2
        double sum = sum1 + sum2;
        long timeInNanos = System.nanoTime() - startTime;
        printResults("parArraySum", timeInNanos, sum);
        // Task T0 waits for Task T1 (join)
        return sum;
    }

    private static void printResults(String name, long timeInNanos, double sum) {
        System.out.printf("   %s completed in %8.3f milliseconds, with sum = %8.5f \n", name, timeInNanos / 1e6, sum);
    }

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    private static double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = rand.nextInt(100);
            // Don't allow zero values in the input array to prevent divide-by-zero
            if (input[i] == 0.0) {
                i--;
            }
        }

        return input;
    }

    public static void main(String [] args) {
        double[] input = createArray(100000000);
        for(int i = 1; i < 5; i++) {
            System.out.println("Run: " + i);
            seqArraySum(input);
            parArraySum(input);
            System.out.println("");
        }
    }
}
