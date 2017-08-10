import java.util.concurrent.TimeUnit;

/**
 * Reads in two strings, the pattern and the input text, and
 * searches for the pattern in the input text using a version
 * of the KMP algorithm.
 * The version takes time as space proportional to N + M R
 * in the worst case, where N is the length of the text string,
 * M is the length of the pattern, and R is the alphabet size.
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class KMP {

    private static final int R = 256;       // the radix, the alphabet size.
    private int[][] dfa;       // the KMP automoton
    private String pat;        // the pattern string

    /**
     * Pre-processes the pattern string.
     *
     * @param pat the pattern string
     */
    public KMP(String pat) {
        this.pat = pat;

        // build DFA from pattern
        int m = pat.length();
        dfa = new int[R][m];
        dfa[pat.charAt(0)][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases. 
            dfa[pat.charAt(j)][j] = j + 1;   // Set match case. 
            x = dfa[pat.charAt(j)][x];     // Update restart state. 
        }
    }

    /**
     * Returns the execution time it took to find all
     * the number of occurrences of the pattern string
     * in the text string.
     *
     * @param txt the text string
     * @return the execution time it took to find all
     * the number of occurrences.
     */
    public long search(String txt) {

        // simulate operation of DFA on text
        int m = pat.length();
        int n = txt.length();
        int i, j;

        long start = System.nanoTime();
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
            if (j == m) j = 0; // found, look for new match.
        }
        long end = System.nanoTime();

        return TimeUnit.NANOSECONDS.toMillis(end - start);
    }
}
