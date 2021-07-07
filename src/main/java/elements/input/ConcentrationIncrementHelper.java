package elements.input;

public class ConcentrationIncrementHelper {
    private static final double TOTAL = 1.0;

    public static double parseConcentrationIncrementSize(double incrementSize) {
        int scaledTotal = getScaledTotal(incrementSize);
        int decimalPlaces = 15;
        long scalingFactor = (long) Math.pow(10, decimalPlaces);
        if (Math.round(scaledTotal * incrementSize * scalingFactor) != scalingFactor) {
            throw new IllegalArgumentException("bad concentration_increment_size (" + incrementSize + "); it must evenly divide 1.00, e.g. 0.01 or 0.005");
        }
        return incrementSize;
    }

    private static int getScaledTotal(double incrementSize) {
        return (int) Math.round(TOTAL / incrementSize);
    }

}
