package elements.runner;

import elements.calculations.RegularCalculation;
import elements.concentrations.*;
import elements.calculations.Element;
import elements.concentrations2.*;
import elements.input.Input;
import elements.io.ResultCSV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CategoriserRunner implements Callable<CalculationResultSummary> {
    private final List<Element> elements;
    private final Input input;
    private final ResultCSV detailedResultCSV;
    private final ProgressListener progressListener;
    private final BestCalculationResultListener bestCalculationResultListener;

    public CategoriserRunner(List<Element> elements,
                             Input input,
                             ResultCSV detailedResultCSV,
                             ProgressListener progressListener,
                             BestCalculationResultListener bestCalculationResultListener) {
        this.elements = elements;
        this.input = input;
        this.detailedResultCSV = detailedResultCSV;
        this.progressListener = progressListener;
        this.bestCalculationResultListener = bestCalculationResultListener;
    }

    @Override
    public CalculationResultSummary call() {
        CalculationResultSummary calculationResultSummary = new CalculationResultSummary(
                input,
                elements
        );

        CalculationResultFactory calculationResultFactory = new CalculationResultFactory(
                input.getTargetName()
        );

        List<CalculationResultListener> listeners = configureListeners(calculationResultSummary);

        SummingAllocationIterator summingAllocationIterator = buildSummingAllocationIterator();

        Element[] elementArray = elements.toArray(new Element[elements.size()]);
        while (summingAllocationIterator.hasNext()) {
            Map<Element, Double> allocation = summingAllocationIterator.next();
            List<Double> concentrations = new ArrayList<>();
            for (Element element : elements) {
                concentrations.add(allocation.get(element));
            }

            CalculationResult calculationResult = new RegularCalculation(calculationResultFactory)
                    .calculate(
                            elementArray,
                            concentrations.toArray(new Double[concentrations.size()])
                    );

            for (CalculationResultListener listener : listeners) {
                listener.handle(calculationResult);
            }
        }

        return calculationResultSummary;
   }

    private SummingAllocationIterator buildSummingAllocationIterator() {
        CloneableDoubleIteratorWithinBounds iterator;
        if (input.useExponentialIncrement()) {
            iterator = new ExponentialIteratorBetweenZeroAndOneBuilder()
                    .withStartInclusive(input.getFirstIncrement())
                    .withNumberOfSteps(input.getNumberOfConcentrations())
                    .build();
        } else {
            iterator = new LinearIteratorBuilder()
                    .withStartInclusive(input.getLinearStepSize())
                    .withEndExclusive(1.0)
                    .withStepSize(input.getLinearStepSize())
                    .build();
        }

        Map<Element, CloneableDoubleIteratorWithinBounds> allocators = new HashMap<>();
        for (Element element : elements) {
            allocators.put(element, iterator.clone());
        }

        return SummingAllocationIterator.newInstance(
                allocators,
                1.0);
    }

    private List<CalculationResultListener> configureListeners(final CalculationResultSummary calculationResultSummary) {
        List<CalculationResultListener> listeners = new ArrayList<>();

        listeners.add(progressListener);

        if (input.isOutputBestResultOnly()) {
            listeners.add(bestCalculationResultListener);
        }

        if (detailedResultCSV != null) {
            listeners.add(new CalculationResultListener() {
                @Override
                public void handle(CalculationResult result) {
                    detailedResultCSV.write(result);
                }
            });
        }

        listeners.add(new CalculationResultListener() {
            @Override
            public void handle(CalculationResult result) {
                calculationResultSummary.handleResult(result);
            }
        });
        return listeners;
    }

}
