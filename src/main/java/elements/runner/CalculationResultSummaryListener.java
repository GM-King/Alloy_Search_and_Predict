package elements.runner;

import elements.concentrations.CalculationResultSummary;

public interface CalculationResultSummaryListener {
    void handle(CalculationResultSummary summary);
}
