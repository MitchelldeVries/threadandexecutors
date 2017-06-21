import java.util.concurrent.TimeUnit;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class Main {

    public static void main(String[] args) {

        Runnable task = () -> {
            try {
                String threadName = Thread.currentThread().getName();
                System.out.println("Foo " + threadName);
                Thread.sleep(1000);
                System.out.println("Bar " + threadName);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }
}
