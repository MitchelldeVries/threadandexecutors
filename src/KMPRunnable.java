/**
 * Parallel Computing KMP substring search.
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class KMPRunnable implements Runnable {

    private KMP kmp;
    private String text;

    public KMPRunnable(String pattern, String text) {
        this.kmp = new KMP(pattern);
        this.text = text;
    }

    @Override
    public void run() {
        // Time measurement of method execution.
        long startTime = System.nanoTime();
        kmp.search(text);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Milliseconds: " + timeElapsed / 1_000_000);
    }
}
