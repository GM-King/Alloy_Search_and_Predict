package elements.runner;

import elements.concentrations.CalculationResultSummary;

import java.util.ArrayList;
import java.util.List;

public class BestCalculationResultSummaryListener implements CalculationResultSummaryListener {
    private Double bestTargetDistance;
    private List<CalculationResultSummary> bestSummaries;

    @Override
    public void handle(CalculationResultSummary summary) {
        double targetDistance = summary.getBestTargetDistance();
        if (bestTargetDistance == null || targetDistance < bestTargetDistance) {
            bestTargetDistance = targetDistance;

            bestSummaries = new ArrayList<>();
            bestSummaries.add(summary);
        } else if (targetDistance == bestTargetDistance) {
            bestSummaries.add(summary);
        }
    }

    public List<CalculationResultSummary> getBestSummaries() {
        return bestSummaries;
    }
}
