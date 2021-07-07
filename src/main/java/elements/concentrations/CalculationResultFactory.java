package elements.concentrations;

import elements.calculations.Element;
import elements.structure.ResultingStructure;

public class CalculationResultFactory {
    private final String targetName;

    public CalculationResultFactory(String targetName) {
        this.targetName = targetName;
    }

    public CalculationResult newInstance(Element[] elements,
                                         Double[] concentrations,
                                         ResultingStructure structure) {
        return new CalculationResult(
                elements,
                concentrations,
                structure,
                targetName
        );
    }
}
