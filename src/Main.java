import java.util.concurrent.*;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<Integer> task = () -> {
            TimeUnit.SECONDS.sleep(1);
            return 123;
        };

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);

        System.out.println("future done? " + future.isDone());

        int result = future.get();

        System.out.println("future done? " + future.isDone());
        System.out.println("result:" + result);

        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }

            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }
}
