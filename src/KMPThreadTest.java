import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Created by boydhogerheijde on 12/08/2017.
 */
public class KMPThreadTest {

    private static final String TEXT = TextReader.read("Sample-text-file-5000kb.txt");
    private static final String PATTERN = "nec ferm";
    private static final int NUM_THREADS = 2;

    public static void main(String[] args) {

        long[] timings = new long[1000];

        for (int i = 0; i < 1000; i++) {
            List<String> partitions = StringUtils.splitText(TEXT, NUM_THREADS);
            List<Thread> threads = new ArrayList<>();

            long startTime = System.nanoTime();

            for (int j = 0; j < NUM_THREADS; j++) {
                String text = partitions.get(j);

                threads.add(new Thread(new KMPRunnable(PATTERN, text)));
            }

            threads.forEach(Thread::start);

            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.nanoTime();
            long timeElapsed = (endTime - startTime) / 1_000_000;

            timings[i] = timeElapsed;
        }

        OptionalDouble optionalAverage = Arrays.stream(timings).average();
        double averageTime = optionalAverage.isPresent() ? optionalAverage.getAsDouble() : -1;
        System.out.println("Average time for " + NUM_THREADS + " threads: " + averageTime + "ms");
    }
}
