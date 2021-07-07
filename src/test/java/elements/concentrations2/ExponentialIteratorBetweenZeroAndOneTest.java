package elements.concentrations2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExponentialIteratorBetweenZeroAndOneTest {

    @Test
    public void shouldCorrectlyCalculateRange() {
        CloneableDoubleIteratorWithinBounds iterator = new ExponentialIteratorBetweenZeroAndOneBuilder()
                .withStartInclusive(0.25)
                .withNumberOfSteps(2)
                .build();

        assertThat(iterator.isInRange(0.20), is(false));
        assertThat(iterator.isInRange(0.2499999999999999), is(true));
        assertThat(iterator.isInRange(0.25), is(true));
        assertThat(iterator.isInRange(0.90), is(true));
        assertThat(iterator.isInRange(0.9999999999999999), is(false));
        assertThat(iterator.isInRange(1.00), is(false));
    }

    @Test
    public void shouldIterateExponentially() {
        CloneableDoubleIteratorWithinBounds iterator = new ExponentialIteratorBetweenZeroAndOneBuilder()
                .withStartInclusive(0.25)
                .withNumberOfSteps(2)
                .build();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.25));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.5));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldCloneIteratorAtCurrentPosition() {
        CloneableDoubleIteratorWithinBounds iterator = new ExponentialIteratorBetweenZeroAndOneBuilder()
                .withStartInclusive(0.25)
                .withNumberOfSteps(2)
                .build();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.25));

        CloneableDoubleIteratorWithinBounds clonedIterator = iterator.clone();
        assertThat(clonedIterator.hasNext(), is(true));
        assertThat(clonedIterator.next(), is(0.5));
        assertThat(clonedIterator.hasNext(), is(false));

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.5));
        assertThat(iterator.hasNext(), is(false));
    }

}
