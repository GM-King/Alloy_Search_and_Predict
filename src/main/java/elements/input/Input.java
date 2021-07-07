package elements.input;

import elements.calculations.Element;

import java.util.List;

public interface Input {
    boolean useFileSystemTxt();

    Iterable<List<Element>> getElementSelections();

    boolean hasMultipleSelections();

    String getTargetName();

    double getTargetValue();

    String getElementSpecifier();

    int getNumberOfElements();

    boolean isOutputBestResultOnly();

    boolean useExponentialIncrement();

    double getLinearStepSize();

    double getFirstIncrement();

    int getNumberOfConcentrations();
}
