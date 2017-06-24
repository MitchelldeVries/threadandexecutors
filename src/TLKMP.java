import java.util.concurrent.*;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class TLKMP {
    private int numberOfOccurrences = 0;
    private ExecutorService executor;
    private char[] pattern;
    private char[] text;

    public TLKMP(String file, char[] pattern) {
        this.pattern = pattern;
        read(file);
        calculatePrefix(pattern);
    }

    private void read(String file) {
        text = TextReader.read(file);
    }

    private Callable<int[]> calculatePrefix(char[] pattern) {
        return () -> {
            this.pattern = pattern;
            int i = 1;
            int j = 0;
            int patternLength = pattern.length;
            int[] prefix = new int[patternLength];

            prefix[0] = 0;

            while (i < patternLength) {
                if (pattern[i] == pattern[j]) {
                    j++;
                    prefix[i] = j;
                    i++;
                } else if (j > 0) {
                    j = prefix[j - 1];
                } else {
                    prefix[i] = 0;
                    i++;
                }
            }
            return prefix;
        };
    }

    public void search(int numberOfThreads) {
        try {
            executor = Executors.newFixedThreadPool(numberOfThreads);
            int[] prefix = executor.submit(calculatePrefix(pattern)).get();
            executor.execute(stringMatchTask(text, prefix));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        try {
            System.out.println("Attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("Cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("Shutdown finished");
        }
    }

    public int getAmountOfOccurrences() {
        return numberOfOccurrences;
    }

    private Runnable stringMatchTask(char[] text, int[] prefix) {
        return () -> {
            int i = 0;
            int j = 0;
            int textLength = text.length;
            int patternLength = pattern.length;

            while (i < textLength) {
                if (text[i] == pattern[j]) {
                    if (j == patternLength - 1) {
                        i++;
                        numberOfOccurrences++;
                    } else {
                        i++;
                        j++;
                    }
                } else if (j > 0) {
                    j = prefix[j - 1];
                } else {
                    i++;
                }
            }
        };
    }
}
