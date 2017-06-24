/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class MessageBrokerApp {

    private static final char[] PATTERN = "ep".toCharArray();
    private static final char[] TEXT = TextReader.read("Sample-text-file-40000kb.txt");

    public static void main(String[] args) throws Exception {
        thread(new Producer(PATTERN), false);
        thread(new Consumer(TEXT, PATTERN), false);
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}
