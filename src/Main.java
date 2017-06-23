import java.util.concurrent.TimeUnit;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class Main {
    private static final String FILE = "Sample-text-file-20000kb.txt";
    private static final String PATTERN = "boom";
    private static long sequentialTime, kmpTime, parallelTime;
    private static int sequentialOccurrences, kmpOccurrences, parallelOccurrences;

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            executeKMP();
            executeSequential();
            executeParallel();
        }

        System.out.println("- KMP");
        System.out.println("Amount of occurrences: " + kmpOccurrences);
        System.out.println("Execution took: " + TimeUnit.NANOSECONDS.toSeconds(kmpTime) + " seconds.");
        System.out.println("Execution took: " + TimeUnit.NANOSECONDS.toMillis(kmpTime) + " miliseconds.");
        System.out.println("Execution took: " + kmpTime + " nanoseconds.");

        System.out.println("- Sequential KMP");
        System.out.println("Amount of occurrences: " + sequentialOccurrences);
        System.out.println("Execution took: " + TimeUnit.NANOSECONDS.toSeconds(sequentialTime) + " seconds.");
        System.out.println("Execution took: " + TimeUnit.NANOSECONDS.toMillis(sequentialTime) + " miliseconds.");
        System.out.println("Execution took: " + sequentialTime + " nanoseconds.");

        System.out.println("- Parallel KMP");
        System.out.println("Amount of occurrences: " + parallelOccurrences);
        System.out.println("Execution took: " + TimeUnit.NANOSECONDS.toSeconds(parallelTime) + " seconds.");
        System.out.println("Execution took: " + TimeUnit.NANOSECONDS.toMillis(parallelTime) + " miliseconds.");
        System.out.println("Execution took: " + parallelTime + " nanoseconds.");
    }

    private static void executeKMP() {
        KMP sequentialKMP = new KMP(FILE, PATTERN);

        Timer timer = new Timer();
        timer.start();
        sequentialKMP.search();
        timer.end();

        kmpTime += timer.getExecutionTimeInNanoSeconds();
        kmpOccurrences = sequentialKMP.getNumberOfOccurrence();
    }

    private static void executeSequential() {
        SeqTLKMP seqTLKMP = new SeqTLKMP(FILE, PATTERN.toCharArray());

        Timer timer = new Timer();
        timer.start();
        seqTLKMP.search(3);
        timer.end();

        sequentialTime += timer.getExecutionTimeInNanoSeconds();
        sequentialOccurrences = seqTLKMP.getAmountOfOccurrences();
    }

    private static void executeParallel() {
        ParallelKMP parallelKMP = new ParallelKMP(FILE, PATTERN.toCharArray());

        Timer timer = new Timer();
        timer.start();
        parallelKMP.splitTextAndSearch(3);
        timer.end();

        parallelTime += timer.getExecutionTimeInNanoSeconds();
        parallelOccurrences = parallelKMP.getAmountOfOccurrences();
    }


}
