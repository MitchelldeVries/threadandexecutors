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

    /**
     * Searches for the pattern string in the text string; and prints
     * the number of occurrences of the pattern string in the text string
     * and prints out the execution time.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        KMP kmp = new KMP(PAT);

        kmp.search(TXT);

        // print results
        System.out.println("Text size: " + TXT.length() / (1024 * 1024) + " MB");
        System.out.println("Pattern: " + PAT);
        System.out.println("Occurrences: " + kmp.getOccurrences());
        System.out.println("Took: " + kmp.getTime() + " ms.\n");

    }

}
