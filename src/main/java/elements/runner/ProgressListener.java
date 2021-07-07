package elements.runner;

import elements.concentrations.CalculationResult;
import elements.concentrations.CalculationResultListener;
import elements.calculations.Element;
import elements.input.Input;
import elements.input.InputFile;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProgressListener implements CalculationResultListener {
    private BigInteger totalCalculationCount;
    private BigInteger onePercentCalculationCount;
    private Stopwatch stopwatch;
    private AtomicLong resultCount;
    private Thread displayThread;

    public static ProgressListener newProgressListener(Input input) {
        System.out.println("Started running calculations");

        if (input.useExponentialIncrement()) {
            return new DoNothingProgressListener();
        }

        ProgressListener progressListener = new ProgressListener(input);
        progressListener.displayThread.start();
        return progressListener;
    }

    protected ProgressListener() {
    }

    public void stop() {
        displayThread.interrupt();
    }

    @Override
    public void handle(CalculationResult result) {
        resultCount.incrementAndGet();
    }

    private ProgressListener(Input input) {
        totalCalculationCount = BigInteger.valueOf(getCalculationsPerElementSelection(input))
                .multiply(BigInteger.valueOf(getNumberOfElementSelections(input)));
        onePercentCalculationCount = totalCalculationCount.divide(BigInteger.valueOf(100));
        stopwatch = new Stopwatch();
        stopwatch.start();
        resultCount = new AtomicLong(0);

        displayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("Finished running calculations");
                        System.out.println();
                        return;
                    }

                    BigInteger currentResultCount = BigInteger.valueOf(resultCount.get());
                    BigInteger currentSeconds = BigInteger.valueOf((long) stopwatch.getCurrentSeconds());
                    BigInteger remainingSeconds = currentSeconds
                            .multiply(totalCalculationCount.subtract(currentResultCount))
                            .divide(currentResultCount);
                    BigInteger percentageCompleted = currentResultCount.divide(onePercentCalculationCount);

                    String formattedTimeInterval = IntervalFormatter.formatSeconds(remainingSeconds);
                    String remainingTimeLabel;
                    if (formattedTimeInterval.isEmpty()) {
                        remainingTimeLabel = "";
                    } else {
                        remainingTimeLabel = ", " + formattedTimeInterval + " remaining";
                    }
                    System.out.println(percentageCompleted + "% completed" + remainingTimeLabel);
                }
            }
        });
        displayThread.setDaemon(true);
    }

    private long getCalculationsPerElementSelection(Input input) {
        int concentrationsPerElement = (int) (1.0 / input.getLinearStepSize());
        int numberOfElements = input.getNumberOfElements();
        BigInteger nFactorial = factorial(concentrationsPerElement - 1);
        BigInteger kFactorial = factorial(concentrationsPerElement - numberOfElements);
        BigInteger diffFactorial = factorial(numberOfElements - 1);
        return nFactorial.divide(diffFactorial.multiply(kFactorial)).longValue();
    }

    private long getNumberOfElementSelections(Input input) {
        long result = 0;
        Iterator<List<Element>> iterator = input.getElementSelections().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            result++;
        }
        return result;
    }

    private BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
