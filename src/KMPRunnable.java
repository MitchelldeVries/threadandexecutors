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
        kmp.search(text);
    }
}
