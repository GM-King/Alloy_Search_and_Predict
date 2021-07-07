package elements.concentrations2;

public class ExponentialIteratorBetweenZeroAndOneBuilder {
    private double startInclusive;
    private int numberOfSteps;

    public ExponentialIteratorBetweenZeroAndOneBuilder withStartInclusive(double startInclusive) {
        this.startInclusive = startInclusive;
        return this;
    }

    public ExponentialIteratorBetweenZeroAndOneBuilder withNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
        return this;
    }

    public CloneableDoubleIteratorWithinBounds build() {
		return new ExponentialIteratorBetweenZeroAndOne(
                startInclusive,
                numberOfSteps
        );
    }
}
