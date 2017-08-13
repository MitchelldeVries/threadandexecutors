import java.io.Serializable;

/**
 * Parallel Computing KMP substring search.
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class KMP implements Serializable {

    private String pattern;
    private int[][] dfa;

    public KMP(String pattern) {
        this.pattern = pattern;

        int M = pattern.length();
        int R = 256;

        dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;

        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
                dfa[pattern.charAt(j)][j] = j + 1;
                X = dfa[pattern.charAt(j)][X];
            }
        }
    }

    public int search(String text) {
        int i, j;
        int N = text.length();
        int M = pattern.length();
        int occurrences = 0;

        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[text.charAt(i)][j];

            if (j == M) {
                j = 0;
                occurrences++;
            }
        }

        return occurrences;
    }
}
