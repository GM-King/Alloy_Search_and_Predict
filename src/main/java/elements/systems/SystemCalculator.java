package elements.systems;

import elements.calculations.Element;
import elements.calculations.ElementData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SystemCalculator {

    public static Element[] getElements(String systemDescription) {
        List<Element> elementList = convertToElements(getElementNames(systemDescription));
        Element[] elementArray = new Element[elementList.size()];
        return elementList.toArray(elementArray);
    }

    public static Double[] getConcentrations(String systemDescription) {
        List<Double> concentrationList = spreadConcentrations(getElementConcentrations(systemDescription));
        Double[] concentrationArray = new Double[concentrationList.size()];
        return concentrationList.toArray(concentrationArray);
    }

    private static List<Double> spreadConcentrations(List<Double> originalConcentrations) {
        double sum = 0.0;
        for (Double concentration : originalConcentrations) {
            sum += concentration;
        }

        List<Double> elementConcentrations = new ArrayList<>();
        for (Double concentration : originalConcentrations) {
            elementConcentrations.add(concentration / sum);
        }

        return elementConcentrations;
    }

    private static List<Element> convertToElements(List<String> elementNames) {
        List<Element> elements = new ArrayList<>();
        for (String elementName : elementNames) {
            elements.add(ElementData.getElement(elementName));
        }
        return elements;
    }

    private static List<String> getElementNames(String systemDescription) {
        List<String> elementConcentrationStrings = getElementConcentrationStrings(systemDescription);

        List<String> elements = new ArrayList<>();
        for (String elementConcentrationString : elementConcentrationStrings) {
            String elementName = elementConcentrationString
                    .replaceAll("[0-9.]", "");
            elements.add(elementName);
        }
        
        return elements;
    }

    private static List<Double> getElementConcentrations(String systemDescription) {
        List<String> elementStrings = getElementConcentrationStrings(systemDescription);

        List<Double> elements = new ArrayList<>();
        for (String elementConcentrationString : elementStrings) {
            String concentrationString = elementConcentrationString
                    .replaceAll("[A-Za-z]", "");

            double e;
            if (concentrationString.isEmpty()) {
                e = 1.0;
            } else {
                e = Double.parseDouble(concentrationString);
            }
            elements.add(e);
        }

        return elements;
    }

    private static List<String> getElementConcentrationStrings(String systemDescription) {
        return Arrays.asList(
                systemDescription
                        .replaceAll("([A-Z])", " $1")
                        .replaceFirst("^ ", "")
                        .split(" ")
        );
    }
}
