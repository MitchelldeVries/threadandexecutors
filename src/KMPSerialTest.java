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
        KMP kmp = new KMP(PATTERN);

        // Time measurement of method execution.
        long startTime = System.nanoTime();
        kmp.search(TEXT);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Milliseconds: " + (timeElapsed / 1_000_000));
    }
}
