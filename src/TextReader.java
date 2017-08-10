import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Reads in a file from the specified path and
 * returns the lines as a String.
 *
 * @author Mitchell de Vries.
 * @author Boyd Hogerheijde.
 */
public class TextReader {

    /**
     * Returns all the lines from the specified
     * text file as a String.
     *
     * @param filePath the path to the text file
     * @return all the lines from the specified
     * text file as a String
     */
    public static String read(String filePath) {
        String lines = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            lines = Arrays.toString(bufferedReader.lines().toArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
