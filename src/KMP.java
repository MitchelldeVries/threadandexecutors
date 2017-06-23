/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class KMP {

    private int numberOfOccurrence = 0;
    private char[] text;
    private char[] pattern;
    private int[] prefix;

    public KMP(String file, String pattern) {
        this.text = TextReader.read(file);
        this.pattern = pattern.toCharArray();
        this.prefix = calculatePrefix();
    }

    private int[] calculatePrefix() {
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
    }

    public void search() {
        int i = 0;
        int j = 0;
        int textLength = text.length;
        int patternLength = pattern.length;

        while (i < textLength) {
            if (text[i] == pattern[j]) {
                if (j == patternLength - 1) {
                    i++;
                    numberOfOccurrence++;
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
    }

    public int getNumberOfOccurrence() {
        return numberOfOccurrence;
    }
}