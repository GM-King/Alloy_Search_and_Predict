package elements.io;

import elements.calculations.Element;
import elements.calculations.ElementData;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ElementSelectionIterator implements Iterator<List<Element>> {

    private final Iterator<ICombinatoricsVector<Element>> iterator;
    private final List<Element> preselectedElements;

    public ElementSelectionIterator(List<Element> preselectedElements,
                                    int wildcardElements) {
        this.preselectedElements = preselectedElements;

        this.iterator = createIterator(preselectedElements, wildcardElements);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public List<Element> next() {
        ArrayList<Element> selection = new ArrayList<>(iterator.next().getVector());
        selection.addAll(preselectedElements);
        Collections.sort(selection);
        return selection;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private Iterator<ICombinatoricsVector<Element>> createIterator(List<Element> preselectedElements,
                                                                   int wildcardElements) {
        ArrayList<Element> selectableElements = new ArrayList<>(ElementData.getAllAvailableElements());
        selectableElements.removeAll(preselectedElements);

        Generator<Element> elementSelectionGenerator = Factory.createSimpleCombinationGenerator(
                Factory.createVector(selectableElements),
                wildcardElements
        );

        return elementSelectionGenerator.iterator();
    }
}
