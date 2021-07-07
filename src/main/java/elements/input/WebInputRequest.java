package elements.input;

import elements.calculations.Element;
import elements.calculations.OutputParameter;
import elements.io.ElementSelectionIterator;

import java.util.Iterator;
import java.util.List;

import static elements.input.ConcentrationIncrementHelper.parseConcentrationIncrementSize;

public class WebInputRequest implements Input {
    private final OutputParameter optimisationParameter;
    private final Double targetValue;
    private final Double temperature;
    private final Integer numberOfElements;
    private final Double stepSize;

    private String elementSpecifier;
    private List<Element> preselectedElements;
    private int numberOfWildcardElements;

    public WebInputRequest(OutputParameter optimisationParameter,
                           Double targetValue,
                           Double temperature,
                           Integer numberOfElements,
                           String elementList,
                           Double stepSize) {
        this.optimisationParameter = optimisationParameter;
        this.targetValue = targetValue;
        this.temperature = temperature;
        this.numberOfElements = numberOfElements;
        this.stepSize = parseConcentrationIncrementSize(stepSize);

        ParsedElements parsedElements = new ParsedElements(elementList, numberOfElements);
        elementSpecifier = parsedElements.getElementSpecifier();
        preselectedElements = parsedElements.getChosenElements();
        numberOfWildcardElements = parsedElements.getWildcardElements();
    }

    @Override
    public boolean useFileSystemTxt() {
        return false;
    }

    @Override
    public Iterable<List<Element>> getElementSelections() {
        return new Iterable<List<Element>>() {
            @Override
            public Iterator<List<Element>> iterator() {
                return new ElementSelectionIterator(
                        preselectedElements,
                        numberOfWildcardElements
                );
            }
        };
    }

    @Override
    public boolean hasMultipleSelections() {
        return numberOfWildcardElements > 0;
    }

    @Override
    public String getTargetName() {
        return optimisationParameter.getColumnName();
    }

    @Override
    public double getTargetValue() {
        return targetValue;
    }

    @Override
    public String getElementSpecifier() {
        return elementSpecifier;
    }

    @Override
    public int getNumberOfElements() {
        return numberOfElements;
    }

    @Override
    public boolean isOutputBestResultOnly() {
        return false;
    }

    @Override
    public boolean useExponentialIncrement() {
        return false;
    }

    @Override
    public double getLinearStepSize() {
        return stepSize;
    }

    @Override
    public double getFirstIncrement() {
        return 0;
    }

    @Override
    public int getNumberOfConcentrations() {
        return 0;
    }
}
