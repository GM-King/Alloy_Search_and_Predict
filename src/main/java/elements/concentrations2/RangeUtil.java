package elements.concentrations2;

public final class RangeUtil {

    private static final double FUDGE_FACTOR = 1E-9;

    private RangeUtil() {
    }

    public static boolean isInRange(double value,
                                    double startInclusive,
                                    double endExclusive) {
        return value + FUDGE_FACTOR >= startInclusive
            && value + FUDGE_FACTOR < endExclusive;
    }
}
