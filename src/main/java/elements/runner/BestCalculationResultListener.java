package elements.runner;

import elements.concentrations.CalculationResult;
import elements.concentrations.CalculationResultListener;
import elements.input.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BestCalculationResultListener implements CalculationResultListener {

    private final String targetName;
    private final double targetValue;
    private AtomicReference<BestResult> bestResult = new AtomicReference<>();

    public BestCalculationResultListener(Input input) {
        this.targetName = input.getTargetName();
        this.targetValue = input.getTargetValue();
    }

    @Override
    public void handle(CalculationResult calculationResult) {
        double distance = Math.abs((Double) calculationResult.getValue(targetName) - targetValue);

        BestResult currentBestResult;
        BestResult newBestResult;
        do {
            currentBestResult = bestResult.get();
            if (currentBestResult == null || distance < currentBestResult.targetDistance) {
                newBestResult = BestResult.newInstance(distance, calculationResult);
            } else if (distance == currentBestResult.targetDistance) {
                newBestResult = currentBestResult.addCalculationResult(calculationResult);
            } else {
                break;
            }
        } while (!bestResult.compareAndSet(currentBestResult, newBestResult));
    }

    public List<CalculationResult> bestCalculations() {
        BestResult bestResult = this.bestResult.get();
        return bestResult == null ? null : bestResult.calculationResults;
    }

    private static class BestResult {
        private final double targetDistance;
        private final List<CalculationResult> calculationResults;

        private BestResult(double targetDistance,
                           List<CalculationResult> calculationResults) {
            this.targetDistance = targetDistance;
            this.calculationResults = Collections.unmodifiableList(calculationResults);
        }

        private static BestResult newInstance(double targetDistance,
                                              CalculationResult calculationResults) {
            return new BestResult(
                    targetDistance,
                    Collections.singletonList(calculationResults)
            );
        }

        private BestResult addCalculationResult(CalculationResult calculationResult) {
            List<CalculationResult> newResultList = new ArrayList<>(calculationResults);
            newResultList.add(calculationResult);

            return new BestResult(
                    targetDistance,
                    newResultList
            );
        }
    }
}
