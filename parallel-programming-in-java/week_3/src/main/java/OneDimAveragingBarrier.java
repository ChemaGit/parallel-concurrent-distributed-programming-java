import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static edu.rice.pcdp.PCDP.*;

public class OneDimAveragingBarrier {

    private int n;
    private double[] myNew;
    private double[] myVal;

    public OneDimAveragingBarrier(int n) {
        this.n = n;
        this.myNew = new double[n];
        this.myVal = createArray(n);
    }

    public void runSequential(int iterations) {
        for(int iter = 0; iter < iterations; iter++) {
            long startTime = System.nanoTime();
            double sum = 0;
            for(int j = 1; j <= n-2; j++) {
                myNew[j] = (myVal[j - 1] + myVal[j + 1]) / 2.0;
                sum += myNew[j];
            }
            double[] temp = myNew;
            myNew = myVal;
            myVal = temp;
            long timeInNanos = System.nanoTime() - startTime;
            printResults("runSequential", timeInNanos, sum);
        }
    }

    public void runForAll(final int iterations) {
        for(int iter = 0; iter < iterations; iter++) {
            long startTime = System.nanoTime();
            AtomicReference<Double> sum = new AtomicReference<>((double) 0);
            forall(1, n - 2, (j) -> {
                myNew[j] = (myVal[j - 1] + myVal[j + 1]) / 2.0;
                sum.updateAndGet(v -> new Double((double) (v + myNew[j])));
            });
            double[] temp = myNew;
            myNew = myVal;
            myVal = temp;
            long timeInNanos = System.nanoTime() - startTime;
            printResults("runForAll", timeInNanos, sum.get());
        }
    }

    public void runForAllGrouped(final int iterations, final int tasks) {
        for(int iter = 0; iter < iterations; iter++) {
            long startTime = System.nanoTime();
            AtomicReference<Double> sum = new AtomicReference<>((double) 0);
            forall(1, tasks, (i) -> {
                for(int j = i * ((n-2) / tasks) + 1; j <= (i + 1) * ((n-2) / tasks); j++) {
                    int index = j;
                    myNew[j] = (myVal[j - 1] + myVal[j + 1]) / 2.0;
                    sum.updateAndGet(v -> new Double((double) (v + myNew[index])));
                }
            });
            double[] temp = myNew;
            myNew = myVal;
            myVal = temp;
            long timeInNanos = System.nanoTime() - startTime;
            printResults("runForAllGrouped", timeInNanos, sum.get());
        }
    }

    public void runForallBarrier(final int iterations, final int tasks) {

    }

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    private double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = rand.nextInt(100);
        }
        return input;
    }

    private static void printResults(String name, long timeInNanos, double sum) {
        System.out.printf("   %s completed in %8.3f milliseconds, with sum = %8.5f \n", name, timeInNanos / 1e6, sum);
    }

    public static void main(String [] args) {
        OneDimAveragingBarrier one = new OneDimAveragingBarrier(2048);
        one.runSequential(4);
        System.out.println();
        one.runForAll(4);
        System.out.println();
        one.runForAllGrouped(4, 4);
    }
}
