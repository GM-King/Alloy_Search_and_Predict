package elements.concentrations2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LinearIteratorTest {

    @Test
    public void shouldCorrectlyCalculateRange() {
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
            .withStartInclusive(1.0)
            .withEndExclusive(3.0)
            .withStepSize(1.0)
            .build();

        assertThat(iterator.isInRange(0.0), is(false));
        assertThat(iterator.isInRange(1.0), is(true));
        assertThat(iterator.isInRange(2.0), is(true));
        assertThat(iterator.isInRange(3.0), is(false));
    }

    @Test
    public void shouldNotChangeRangeAfterIterating() {
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
            .withStartInclusive(1.0)
            .withEndExclusive(2.0)
            .withStepSize(1.0)
            .build();

        assertThat(iterator.isInRange(1.0), is(true));

        iterator.next();

        assertThat(iterator.isInRange(1.0), is(true));
    }

    @Test
    public void shouldIterateLinearly() {
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
            .withStartInclusive(1.0)
            .withEndExclusive(3.0)
            .withStepSize(1.0)
            .build();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1.0));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2.0));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldIterateWhenStepSizeIsLessThanOne() {
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
            .withStartInclusive(0.0)
            .withEndExclusive(0.2)
            .withStepSize(0.1)
            .build();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.0));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.1));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldNotExceedSkippedLimit() {
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
            .withStartInclusive(0.0)
            .withEndExclusive(0.5)
            .withStepSize(1.0)
            .build();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.0));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void shouldClone() {
        CloneableDoubleIteratorWithinBounds iterator = new LinearIteratorBuilder()
            .withStartInclusive(0.0)
            .withEndExclusive(2.0)
            .withStepSize(1.0)
            .build();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(0.0));

        CloneableDoubleIteratorWithinBounds clonedIterator = iterator.clone();
        assertThat(clonedIterator.hasNext(), is(true));
        assertThat(clonedIterator.next(), is(1.0));
        assertThat(clonedIterator.hasNext(), is(false));

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1.0));
        assertThat(iterator.hasNext(), is(false));
    }

}
