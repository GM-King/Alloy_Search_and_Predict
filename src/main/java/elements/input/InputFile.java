package elements.input;

import elements.calculations.Element;
import elements.io.ElementSelectionIterator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class InputFile implements Input {
    public static final String INPUT_FILENAME = "input.txt";
    private static final String SEPARATOR = ":";

    private boolean phiDeltaCutoff;

    private final boolean useFileSystemTxt;

    private final boolean outputBestResultOnly;

    private String targetName;
    private double targetValue;

    private List<Element> chosenElements;
    private int wildcardElements;
    private String elementSpecifier;
    private boolean useExponentialIncrement;
    private double firstIncrement;
    private final int numberOfConcentrations;
    private double linearStepSize;

    public InputFile(String filename) {
        Map<String, String> configurationData = extractConfigurationData(filename);

        useFileSystemTxt = Boolean.parseBoolean(configurationData.get("use_file_systems_txt"));
        outputBestResultOnly = Boolean.parseBoolean(configurationData.get("only_output_best_result"));
        targetName = configurationData.get("target_name").trim();
        targetValue = Double.parseDouble(configurationData.get("target_value").trim());
        phiDeltaCutoff = Boolean.parseBoolean(configurationData.get("cutoff_PhiDelta"));
        useExponentialIncrement = Boolean.parseBoolean(configurationData.get("use_exponential_increment"));
        linearStepSize = ConcentrationIncrementHelper.parseConcentrationIncrementSize(
                Double.parseDouble(configurationData.get("linear_step_size")));
        firstIncrement = Double.parseDouble(configurationData.get("first_increment"));
        numberOfConcentrations = Integer.parseInt(configurationData.get("number_of_concentrations"));

        ParsedElements parsedElements = new ParsedElements(configurationData.get("elements"));
        elementSpecifier = parsedElements.getElementSpecifier();
        chosenElements = parsedElements.getChosenElements();
        wildcardElements = parsedElements.getWildcardElements();
    }

    @Override
    public boolean useFileSystemTxt() {
        return useFileSystemTxt;
    }

    public boolean isPhiDeltaCutoff() {
        return phiDeltaCutoff;
    }

    @Override
    public Iterable<List<Element>> getElementSelections() {
        return new Iterable<List<Element>>() {
            @Override
            public Iterator<List<Element>> iterator() {
                return new ElementSelectionIterator(
                        chosenElements,
                        wildcardElements
                );
            }
        };
    }

    @Override
    public boolean hasMultipleSelections() {
        return wildcardElements > 0;
    }

    @Override
    public String getTargetName() {
        return targetName;
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
        return chosenElements.size() + wildcardElements;
    }

    @Override
    public boolean isOutputBestResultOnly() {
        return outputBestResultOnly;
    }

    @Override
    public boolean useExponentialIncrement() {
        return useExponentialIncrement;
    }

    @Override
    public double getLinearStepSize() { return linearStepSize; }

    @Override
    public double getFirstIncrement() {
        return firstIncrement;
    }

    @Override
    public int getNumberOfConcentrations() {
        return numberOfConcentrations;
    }

    private Map<String, String> extractConfigurationData(String filename) {
        Map<String, String> configurationData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains(SEPARATOR)) {
                    continue;
                }

                String[] splitString = line.split(SEPARATOR);
                configurationData.put(splitString[0].trim(), splitString[1].trim());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return configurationData;
    }

}
