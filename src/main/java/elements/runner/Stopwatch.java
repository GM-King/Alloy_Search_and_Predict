package elements.runner;

public class Stopwatch {

    public static final double ONE_BILLION = 1000L * 1000L * 1000L;

    private long startTime;
    private long finishTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        finishTime = System.nanoTime();
    }

    public double getSeconds() {
        long nanoseconds = finishTime - startTime;
        return nanoseconds / ONE_BILLION;
    }

    public double getCurrentSeconds() {
        long nanoseconds = finishTime = System.nanoTime() - startTime;
        return nanoseconds / ONE_BILLION;
    }

}
