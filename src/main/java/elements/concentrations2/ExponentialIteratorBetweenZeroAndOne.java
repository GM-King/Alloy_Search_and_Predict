package elements.concentrations2;

public class ExponentialIteratorBetweenZeroAndOne implements CloneableDoubleIteratorWithinBounds {

    public static final double LIMIT_EXCLUSIVE = 1.0;

    private final double startInclusive;
    private final int numberOfSteps;
    private final double constantToRaiseToPower;

    private double nextPower;

    public ExponentialIteratorBetweenZeroAndOne(double startInclusive,
                                                int numberOfSteps) {
        this.startInclusive = startInclusive;
        this.numberOfSteps = numberOfSteps;
        this.constantToRaiseToPower = Math.pow(startInclusive, -1.0 / numberOfSteps);
        this.nextPower = -numberOfSteps;
    }

    @Override
    public boolean hasNext() {
        return nextPower < 0;
    }

    @Override
    public Double next() {
        double result = Math.pow(constantToRaiseToPower, nextPower);
        nextPower++;
        return result;
    }

    @Override
    public CloneableDoubleIteratorWithinBounds clone() {
        ExponentialIteratorBetweenZeroAndOne clonedIterator = new ExponentialIteratorBetweenZeroAndOne(
                startInclusive,
                numberOfSteps
        );
        clonedIterator.nextPower = this.nextPower;
        return clonedIterator;
    }

    @Override
    public boolean isInRange(double concentration) {
        return RangeUtil.isInRange(
                concentration,
                startInclusive,
                LIMIT_EXCLUSIVE
        );
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
