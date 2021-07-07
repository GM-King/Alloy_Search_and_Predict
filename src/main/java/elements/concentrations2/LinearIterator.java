package elements.concentrations2;

class LinearIterator implements CloneableDoubleIteratorWithinBounds {

    private final double startInclusive;
    private final double endExclusive;
    private final double stepSize;
    private double next;

    public LinearIterator(double startInclusive,
                          double endExclusive,
                          double stepSize) {
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.stepSize = stepSize;
        this.next = startInclusive;
    }

    @Override
    public CloneableDoubleIteratorWithinBounds clone() {
        LinearIterator linearIterator = new LinearIterator(
                startInclusive,
                endExclusive,
                stepSize);
        linearIterator.next = next;
        return linearIterator;
    }

    @Override
    public boolean isInRange(double concentration) {
        return RangeUtil.isInRange(
                concentration,
                startInclusive,
                endExclusive
        );
    }

    @Override
    public boolean hasNext() {
        return next < endExclusive;
    }

    @Override
    public Double next() {
        double result = next;
        next += stepSize;
        return result;
    }

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
