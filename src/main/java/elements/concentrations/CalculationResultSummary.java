package elements.concentrations;

import elements.calculations.Element;
import elements.input.Input;
import elements.io.CSVRow;
import elements.structure.ResultingStructure;
import elements.structure.ResultingStructureTotals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CalculationResultSummary implements CSVRow {
    private final Input input;
    private final List<Element> elementSelection;

    private ResultingStructureTotals resultingStructureTotals = new ResultingStructureTotals();
    private Double bestTargetValue;
    private Double bestTargetDistance;

    public CalculationResultSummary(Input input,
                                    List<Element> elementSelection) {
        this.input = input;
        this.elementSelection = elementSelection;
    }

    public void handleResult(CalculationResult calculationResult) {
        tallyStructures(calculationResult);
        handleTarget(calculationResult);
    }

    private void tallyStructures(CalculationResult calculationResult) {
        ResultingStructure structure = calculationResult.getStructure();
        resultingStructureTotals.handleResult(structure);
    }

    private void handleTarget(CalculationResult calculationResult) {
        Double currentValue = (Double) calculationResult.getValue(input.getTargetName());
        double targetValue = input.getTargetValue();

        double currentTargetDistance = Math.abs(currentValue - targetValue);
        if (bestTargetDistance == null || bestTargetDistance > currentTargetDistance) {
            bestTargetValue = currentValue;
            bestTargetDistance = currentTargetDistance;
        }
    }

    @Override
    public Collection<String> getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("Best " + input.getTargetName());
        headings.addAll(getElementHeadings());
        for (ResultingStructure structure : ResultingStructure.values()) {
            headings.add(structure.toString());
        }
        return headings;
    }

    @Override
    public Collection<Object> getValues() {
        List<Object> headings = new ArrayList<>();
        headings.add(bestTargetValue);
        headings.addAll(getElementValues());
        for (ResultingStructure structure : ResultingStructure.values()) {
            headings.add(resultingStructureTotals.get(structure));
        }
        return headings;
    }

    public boolean noValuesHaveBeenSet() {
        return bestTargetValue == null;
    }

    private List<String> getElementHeadings() {
        List<String> headings = new ArrayList<>();
        for (int i = 0; i < elementSelection.size(); i++) {
            headings.add("Element " + (i + 1));
        }
        return headings;
    }

    private List<String> getElementValues() {
        List<String> values = new ArrayList<>();
        for (Element element : elementSelection) {
            values.add(element.symbol);
        }
        return values;
    }

    public Double getBestTargetDistance() {
        return bestTargetDistance;
    }

}
