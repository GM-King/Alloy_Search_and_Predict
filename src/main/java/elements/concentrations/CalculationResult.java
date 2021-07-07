package elements.concentrations;

import elements.calculations.Element;
import elements.io.CSVRow;
import elements.structure.ResultingStructure;

import java.text.DecimalFormat;
import java.util.*;

public class CalculationResult implements CSVRow {
    private final Element[] elements;
    private final Double[] concentrations;
    private final ResultingStructure structure;
    private final String targetName;
    private final Map<String, Object> values;
    private final DecimalFormat concentrationFormat;

    public CalculationResult(Element[] elements,
                             Double[] concentrations,
                             ResultingStructure structure,
                             String targetName) {
        this.elements = elements;
        this.concentrations = concentrations;
        this.structure = structure;
        this.targetName = targetName;
        this.values = new TreeMap<>();
        concentrationFormat = new DecimalFormat("#.###############");
    }

    public void setValue(String name,
                         Object value) {
        values.put(
                name,
                value
        );
    }

    public Object getValue(String name) {
        return values.get(name);
    }

    @Override
    public Collection<String> getHeadings() {
        List<String> headings = new ArrayList<>();
        for (Element element : elements) {
            headings.add(element.symbol);
        }
        headings.addAll(getHeadingsWithoutElements());
        return headings;
    }

    public Collection<String> getHeadingsWithoutElements() {
        List<String> nonTargetNames = new ArrayList<>(values.keySet());
        nonTargetNames.remove(targetName);

        Collection<String> result = new ArrayList<>();
        result.add(targetName);
        result.addAll(nonTargetNames);
        result.add("Structure");
        return result;
    }

    @Override
    public Collection<Object> getValues() {
        Collection<Object> result = new ArrayList<>();
        for (Double concentration : concentrations) {
            result.add(concentrationFormat.format(concentration));
        }
        result.addAll(getValuesWithoutElements());
        return result;
    }

    public Collection<Object> getValuesWithoutElements() {
        Map<String, Object> nonTargetValues = new TreeMap<>(values);
        nonTargetValues.remove(targetName);

        Collection<Object> result = new ArrayList<>();
        result.add(values.get(targetName));
        result.addAll(nonTargetValues.values());
        result.add(structure);
        return result;
    }

    public ResultingStructure getStructure() {
        return structure;
    }
}
