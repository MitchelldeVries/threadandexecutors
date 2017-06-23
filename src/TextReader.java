import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class TextReader {

    public static char[] read(String filePath) {
        String lines = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            File file = new File(filePath);
//            System.out.println("File is: " + file.length() / (1024) + "KB\n");

            lines = Arrays.toString(bufferedReader.lines().toArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines.toCharArray();
    }
}
