import java.util.Arrays;
import java.util.OptionalDouble;

/**
 * Parallel Computing KMP substring search.
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class KMPSerialTest {

    private static final String TEXT = TextReader.read("Sample-text-file-80000kb.txt");
    private static final String PATTERN = "nec ferm";

    public static void main(String[] args) {
        long[] timings = new long[1000];

        for (int i = 0; i < 1000; i++) {
            KMP kmp = new KMP(PATTERN);

            // Time measurement of method execution.
            long startTime = System.nanoTime();
            kmp.search(TEXT);
            long endTime = System.nanoTime();
            long timeElapsed = (endTime - startTime) / 1_000_000;

            timings[i] = timeElapsed;
        }

        OptionalDouble optionalAverage = Arrays.stream(timings).average();
        double averageTime = optionalAverage.isPresent() ? optionalAverage.getAsDouble() : -1;
        System.out.println("Average time for serial execution: " + averageTime + "ms");
    }
}
