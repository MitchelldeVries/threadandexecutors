import java.util.ArrayList;
import java.util.List;

/**
 * Parallel Computing KMP substring search.
 *
 * @author Mitchell de Vries
 * @author Boyd Hogerheijde
 */
public class StringUtils {

    public static List<String> splitText(String text, int numberOfPartitions) {
        List<String> partitions = new ArrayList<>();

        int nCharsPerThread = text.length() / numberOfPartitions;

        int index = 0;
        while (index < text.length()) {
            String partition = text.substring(index, Math.min(index + nCharsPerThread, text.length()));

            if (partitions.size() == numberOfPartitions) {
                int lastIndex = partitions.size() - 1;
                partitions.set(lastIndex, partitions.get(lastIndex).concat(partition));
            } else {
                partitions.add(partition);
            }

            index += nCharsPerThread;
        }

        return partitions;
    }
}
