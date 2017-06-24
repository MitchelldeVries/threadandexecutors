import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

/**
 * @author Mitchell de Vries.
 * @author Boyd Hogerheijde.
 *
 * This class reads a file from the specified path and returns the lines as a char array.
 *
 */
public class TextReader {

    public static char[] read(String filePath) {
        String lines = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            File file = new File(filePath);
            lines = Arrays.toString(bufferedReader.lines().toArray());
            System.out.println("File is: " + file.length() / (1024) + "KB");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines.toCharArray();
    }
}
