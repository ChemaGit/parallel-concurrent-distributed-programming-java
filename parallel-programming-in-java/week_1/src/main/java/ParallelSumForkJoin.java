import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSumForkJoin {

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
     * @param X an array of double
     * @return sum of 1/X[i] for 0 <= i < X.length
     */
    public static double parArraySum(double[] X) {
        long startTime = System.nanoTime();
        SumArray t = new SumArray(X, 0, X.length);
        ForkJoinPool.commonPool().invoke(t);
        double sum = t.ans;
        long timeInNanos = System.nanoTime() - startTime;
        printResults("parArraySum", timeInNanos, sum);
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

    private static class SumArray extends RecursiveAction {
        // static int SEQUENTIAL_THRESHOLD = 5;
        static int SEQUENTIAL_THRESHOLD = 10000; // better performance
        int lo;
        int hi;
        double[] arr;
        double ans = 0;
        public SumArray(double[] arr,int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }
        protected void compute() {
            if(hi - lo <= SEQUENTIAL_THRESHOLD) {
                for(int i = lo; i < hi; ++i)
                    ans += 1 / arr[i];
            } else {
                SumArray left = new SumArray(arr, lo, (hi + lo) / 2);
                SumArray right = new SumArray(arr, (hi + lo) / 2, hi);
                left.fork(); // async
                right.compute();
                left.join();
                ans = left.ans + right.ans;
            }
        }
    }

    public static void main(String[] args) {
        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2"); // two cores
         //System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4"); // four cores
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8"); // eight cores
        double[] input = createArray(100000000);
        for(int i = 1; i < 5; i++) {
            System.out.println("Run: " + i);
            seqArraySum(input);
            parArraySum(input);
            System.out.println("");
        }
    }
}
