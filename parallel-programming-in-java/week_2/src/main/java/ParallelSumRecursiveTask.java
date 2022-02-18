import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static edu.rice.pcdp.PCDP.async;
import static edu.rice.pcdp.PCDP.finish;

public class ParallelSumRecursiveTask {

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

    /**
     *
     * @param X an array of double
     * @return sum of 1/x[i] for 0 <= i < X.length
     */
    public static double parArraySumFuture(double[] X) {
        long startTime = System.nanoTime();
        SumArray t = new SumArray(X, 0, X.length);
        double sum = ForkJoinPool.commonPool().invoke(t);
        long timeInNanos = System.nanoTime() - startTime;
        printResults("parArraySumFuture", timeInNanos, sum);
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

    private static class SumArray extends RecursiveTask<Double> {
        // static int SEQUENTIAL_THRESHOLD = 5;
        static int SEQUENTIAL_THRESHOLD = 1000; // better performance
        int lo;
        int hi;
        double[] arr;
        public SumArray(double[] arr,int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }
        protected Double compute() {
            if(hi - lo <= SEQUENTIAL_THRESHOLD) {
                double sum = 0;
                for(int i = lo; i < hi; ++i)
                    sum += 1 / arr[i];
                return sum;
            } else {
                SumArray left = new SumArray(arr, lo, (hi + lo) / 2);
                SumArray right = new SumArray(arr, (hi + lo) / 2, hi);
                left.fork(); // future async
                double rightSum = right.compute();
                double leftSum = left.join(); // future get
                return leftSum + rightSum;
            }
        }
    }

    public static void main(String [] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4"); // eight cores
        double[] input = createArray(100000000);
        for(int i = 1; i < 5; i++) {
            System.out.println("Run: " + i);
            seqArraySum(input);
            parArraySum(input);
            parArraySumFuture(input);
            System.out.println("");
        }
    }
}
