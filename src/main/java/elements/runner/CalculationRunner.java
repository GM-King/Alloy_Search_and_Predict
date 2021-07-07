package elements.runner;

import elements.concentrations.CalculationResult;
import elements.concentrations.CalculationResultSummary;
import elements.calculations.Element;
import elements.input.Input;
import elements.io.ResultCSV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class CalculationRunner {

    public static final int BATCH_SIZE = 1000;
    private final Input input;
    private final ResultCSV detailedResultCSV;
    private ArrayList<CalculationResultSummaryListener> listeners;

    public CalculationRunner(Input input,
                             ResultCSV detailedResultCSV) {
        this.input = input;
        this.detailedResultCSV = detailedResultCSV;
        listeners = new ArrayList<>();
    }

    public void run() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService<CalculationResultSummary> completionService = new ExecutorCompletionService(executorService);

        ProgressListener progressListener = ProgressListener.newProgressListener(input);
        BestCalculationResultListener bestCalculationResultListener = new BestCalculationResultListener(input);

        Iterator<List<Element>> elementSelections = input.getElementSelections().iterator();

        while (elementSelections.hasNext()) {
            int tasks = 0;
            while (elementSelections.hasNext() && tasks < BATCH_SIZE) {
                completionService.submit(
                        new CategoriserRunner(
                                elementSelections.next(),
                                input,
                                input.isOutputBestResultOnly() ? null : detailedResultCSV,
                                progressListener,
                                bestCalculationResultListener
                        )
                );
                tasks++;
            }

            for (int i = 0; i < tasks; i++) {
                CalculationResultSummary summary = completionService.take().get();
                for (CalculationResultSummaryListener listener : listeners) {
                    listener.handle(summary);
                }
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        progressListener.stop();

        if (input.isOutputBestResultOnly() && !input.hasMultipleSelections()) {
            for (CalculationResult calculationResult : bestCalculationResultListener.bestCalculations()) {
                detailedResultCSV.write(calculationResult);
            }
        }
    }

    public void addListener(CalculationResultSummaryListener listener) {
        listeners.add(listener);
    }
}
