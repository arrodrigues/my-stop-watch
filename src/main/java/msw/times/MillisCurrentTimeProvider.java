package msw.times;

public class MillisCurrentTimeProvider implements CurrentTimeProvider {
    public long currentTime() {
        return System.currentTimeMillis();
    }
}
