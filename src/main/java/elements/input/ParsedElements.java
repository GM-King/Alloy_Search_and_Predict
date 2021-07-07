package elements.input;

import elements.calculations.Element;
import elements.calculations.ElementData;

import java.util.ArrayList;
import java.util.List;

public class ParsedElements {
    private String elementSpecifier;
    private List<Element> chosenElements;
    private int wildcardElements;

    public ParsedElements(String elementList) {
        this(elementList, Integer.MAX_VALUE);
    }

    public ParsedElements(String elementList, int numberOfElements) {
        elementSpecifier = "";
        chosenElements = new ArrayList<>();
        wildcardElements = 0;

        int elementCount = 0;
        for (String elementSymbol : elementList.split(",")) {
            elementCount++;
            if (elementCount > numberOfElements) {
                break;
            }

            String trimmedElementSymbol = elementSymbol.trim();
            if ("*".equals(trimmedElementSymbol)) {
                wildcardElements++;
                elementSpecifier += "-wildcard";
            } else {
                Element element = ElementData.getElement(trimmedElementSymbol);
                chosenElements.add(element);
                elementSpecifier += "-" + element.symbol;
            }
        }
    }

    public String getElementSpecifier() {
        return elementSpecifier;
    }

    public List<Element> getChosenElements() {
        return chosenElements;
    }

    public int getWildcardElements() {
        return wildcardElements;
    }
}
