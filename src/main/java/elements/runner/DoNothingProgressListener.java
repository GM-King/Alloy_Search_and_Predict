package elements.runner;

import elements.concentrations.CalculationResult;

public class DoNothingProgressListener extends ProgressListener {

    public DoNothingProgressListener() {
    }

    @Override
    public void handle(CalculationResult result) {
    }

    @Override
    public void stop() {
    }
}
