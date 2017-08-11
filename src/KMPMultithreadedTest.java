import java.util.List;
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
        List<String> partitions = StringUtils.splitText(TEXT, NUM_THREADS);

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        for (int i = 0; i < NUM_THREADS; i++) {
            String text = partitions.get(i);

            KMPRunnable runnable = new KMPRunnable(PATTERN, text);
            executorService.execute(runnable);
        }
    }
}
