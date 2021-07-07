package elements.concentrations2;

import elements.calculations.Element;
import elements.calculations.ElementData;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SummingAllocationIteratorTest {

    @Test
    public void shouldAllocate() {
        Element element = ElementData.getElement("H");
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
                .withStartInclusive(1.0)
                .withEndExclusive(2.0)
                .withStepSize(1.0)
                .build();

        Map<Element, CloneableDoubleIteratorWithinBounds> elementIterators = Collections.<Element, CloneableDoubleIteratorWithinBounds>singletonMap(
                element,
                iterator);

        double sum = 1.0;
        SummingAllocationIterator summingAllocationIterator = SummingAllocationIterator.newInstance(elementIterators, sum);

        assertThat(summingAllocationIterator.hasNext(), is(true));
        assertThat(((Map<Element, Double>) summingAllocationIterator.next()).get(element), is(1.0));
        assertThat(summingAllocationIterator.hasNext(), is(false));
    }

    @Test
    public void shouldAllocateTwo() {
        Element element1 = ElementData.getElement("H");
        Element element2 = ElementData.getElement("C");
        Map<Element, CloneableDoubleIteratorWithinBounds> elementIterators = new HashMap<>();
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
                .withStartInclusive(1.0)
                .withEndExclusive(3.0)
                .withStepSize(1.0)
                .build();
        elementIterators.put(element1, iterator.clone());
        elementIterators.put(element2, iterator.clone());

        double sum = 3.0;
        Set<Map<Element, Double>> results = extractResultSet(elementIterators, sum);

        assertThat(results.size(), is(2));

        Map<Element, Double> allocation;

        allocation = new HashMap<>();
        allocation.put(element1, 1.0);
        allocation.put(element2, 2.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 2.0);
        allocation.put(element2, 1.0);
        assertThat(results.contains(allocation), is(true));
    }

    @Test
    public void shouldAllocateThree() {
        Element element1 = ElementData.getElement("H");
        Element element2 = ElementData.getElement("C");
        Element element3 = ElementData.getElement("K");
        Map<Element, CloneableDoubleIteratorWithinBounds> elementIterators = new HashMap<>();

        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
                .withStartInclusive(1.0)
                .withEndExclusive(4.0)
                .withStepSize(1.0)
                .build();


        elementIterators.put(element1, iterator.clone());
        elementIterators.put(element2, iterator.clone());
        elementIterators.put(element3, iterator.clone());

        double sum = 6.0;
        Set<Map<Element, Double>> results = extractResultSet(elementIterators, sum);

        assertThat(results.size(), is(7));

        Map<Element, Double> allocation;

        allocation = new HashMap<>();
        allocation.put(element1, 1.0);
        allocation.put(element2, 2.0);
        allocation.put(element3, 3.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 1.0);
        allocation.put(element2, 3.0);
        allocation.put(element3, 2.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 2.0);
        allocation.put(element2, 1.0);
        allocation.put(element3, 3.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 2.0);
        allocation.put(element2, 2.0);
        allocation.put(element3, 2.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 2.0);
        allocation.put(element2, 3.0);
        allocation.put(element3, 1.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 3.0);
        allocation.put(element2, 1.0);
        allocation.put(element3, 2.0);
        assertThat(results.contains(allocation), is(true));

        allocation = new HashMap<>();
        allocation.put(element1, 3.0);
        allocation.put(element2, 2.0);
        allocation.put(element3, 1.0);
        assertThat(results.contains(allocation), is(true));
    }

    private Set<Map<Element, Double>> extractResultSet(Map<Element, CloneableDoubleIteratorWithinBounds> elementIterators, double sum) {
        SummingAllocationIterator summingAllocationIterator = SummingAllocationIterator.newInstance(elementIterators, sum);

        Set<Map<Element, Double>> results = new HashSet<>();
        while (summingAllocationIterator.hasNext()) {
            results.add(summingAllocationIterator.next());
        }

        return results;
    }

}
