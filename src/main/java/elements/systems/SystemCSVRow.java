package elements.systems;

import elements.concentrations.CalculationResult;
import elements.io.CSVRow;

import java.util.ArrayList;
import java.util.Collection;

class SystemCSVRow implements CSVRow {
    private final String systemDescription;
    private final CalculationResult calculationResult;

    SystemCSVRow(String systemDescription,
                 CalculationResult calculationResult) {
        this.systemDescription = systemDescription;
        this.calculationResult = calculationResult;
    }

    @Override
    public Collection<String> getHeadings() {
        ArrayList<String> headings = new ArrayList<>();
        headings.add("System");
        headings.addAll(calculationResult.getHeadingsWithoutElements());
        return headings;
    }

    @Override
    public Collection<Object> getValues() {
        ArrayList<Object> values = new ArrayList<>();
        values.add(systemDescription);
        values.addAll(calculationResult.getValuesWithoutElements());
        return values;
    }
}
