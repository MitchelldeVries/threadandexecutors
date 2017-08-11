import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Parallel Computing KMP substring search.
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class KMPMultithreadedTest {

    private static final String TEXT = TextReader.read("Sample-text-file-80000kb.txt");
    private static final String PATTERN = "nec ferm";
    private static final int NUM_THREADS = 2;

    public static void main(String[] args) {

        long[] timings = new long[1000];

        for (int j = 0; j < 1000; j++) {
            List<String> partitions = StringUtils.splitText(TEXT, NUM_THREADS);

            ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
            long startTime = System.nanoTime();
            for (int i = 0; i < NUM_THREADS; i++) {
                String text = partitions.get(i);

                KMPRunnable runnable = new KMPRunnable(PATTERN, text);
                executorService.execute(runnable);
            }

            executorService.shutdown();
            while (!executorService.isTerminated()) {
                // do nothing..
            }

            long endTime = System.nanoTime();
            long timeElapsed = (endTime - startTime) / 1_000_000;

            timings[j] = timeElapsed;
        }

        OptionalDouble optionalAverage = Arrays.stream(timings).average();
        double averageTime = optionalAverage.isPresent() ? optionalAverage.getAsDouble() : -1;
        System.out.println("Average time for " + NUM_THREADS + " threads: " + averageTime + "ms");
    }
}
