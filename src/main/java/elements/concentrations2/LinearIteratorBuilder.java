package elements.concentrations2;

public class LinearIteratorBuilder {

    private double startInclusive;
    private double endExclusive;
    private double stepSize;

    public LinearIteratorBuilder withStartInclusive(double startInclusive) {
        this.startInclusive = startInclusive;
        return this;
    }

    public LinearIteratorBuilder withEndExclusive(double endExclusive) {
        this.endExclusive = endExclusive;
        return this;
    }

    public LinearIteratorBuilder withStepSize(double stepSize) {
        this.stepSize = stepSize;
        return this;
    }

    public CloneableDoubleIteratorWithinBounds build() {
        return new LinearIterator(
                startInclusive,
                endExclusive,
                stepSize);
    }

}
