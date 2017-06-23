import java.util.concurrent.TimeUnit;

/**
 * Created by mitchelldevries.
 * <p>
 * ${PROJECT}
 */
public class Timer {

    private long start, end;

    public void start() {
        start = System.nanoTime();
    }

    public void end() {
        end = System.nanoTime();
    }

    public long getExecutionTimeInNanoSeconds() {
        return TimeUnit.NANOSECONDS.toNanos(end - start);
    }

    public long getExecutionTimeInMicroSeconds() {
        return TimeUnit.NANOSECONDS.toMicros(end - start);
    }

    public long getExecutionTimeInMiliSeconds() {
        return TimeUnit.NANOSECONDS.toMillis(end - start);
    }

    public long getExecutionTimeInSeconds() {
        return TimeUnit.NANOSECONDS.toSeconds(end - start);
    }

}
