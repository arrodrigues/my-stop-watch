package msw.times;

public class NanosCurrentTimeProvider implements CurrentTimeProvider {
    public long currentTime() {
        return System.nanoTime();
    }
}
