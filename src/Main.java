import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Main class finds the number of occurrences of a pattern string
 * in a text string.
 * <p>
 * This implementation uses the Knuth-Morris-Pratt substring search
 * algorithm.
 * <p>
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class Main {
    private static final String TXT = TextReader.read("Sample-text-file-40000kb.txt");
    private static final String PAT = "ep";
    private static final int N_THREADS = 2;
    private static int time = 0;

    /**
     * Searches for the pattern string in the text string; and prints
     * the number of occurrences of the pattern string in the text string
     * and prints out the execution time.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            execute();
        }

        // print results
        System.out.println("Text size: " + TXT.length() / (1024 * 1024) + " MB");
        System.out.println("Pattern: " + PAT);
        System.out.println("Took: " + time + " ms.\n");

    }

    private static List<String> splitText(String txt) {
        List<String> partitions = new ArrayList<>();

        int nCharsPerThread = txt.length() / N_THREADS;

        int index = 0;
        while (index < txt.length()) {
            String partition = txt.substring(index, Math.min(index + nCharsPerThread, txt.length()));

            if (partitions.size() == N_THREADS) {
                int lastIndex = partitions.size() - 1;
                partitions.set(lastIndex, partitions.get(lastIndex).concat(partition));
            } else {
                partitions.add(partition);
            }

            index += nCharsPerThread;
        }
        return partitions;
    }

    private static void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);

        for (String txt : splitText(TXT)) {
            executor.execute(new SubstringSearchThread(txt, PAT));
        }

        executor.shutdown();
    }

    private static class SubstringSearchThread implements Runnable {

        private String txt;
        private String pat;

        public SubstringSearchThread(String txt, String pat) {
            this.txt = txt;
            this.pat = pat;
        }

        @Override
        public void run() {
            KMP kmp = new KMP(pat);
            kmp.search(txt);
            time += kmp.getTime();
        }
    }
}
