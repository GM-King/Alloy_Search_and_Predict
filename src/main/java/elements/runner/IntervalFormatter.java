package elements.runner;

import java.math.BigInteger;

public class IntervalFormatter {

    public static String formatSeconds(BigInteger remainingSeconds) {
        BigInteger secondMultiplier = BigInteger.ONE;
        BigInteger minuteMultiplier = BigInteger.valueOf(60);
        BigInteger hourMultiplier = minuteMultiplier.multiply(BigInteger.valueOf(60));
        BigInteger dayMultiplier = hourMultiplier.multiply(BigInteger.valueOf(24));

        StringBuilder builder = new StringBuilder();
        BigInteger talliedSeconds = remainingSeconds;

        if (talliedSeconds.equals(BigInteger.ZERO)) {
            return "< 1 second";
        }

        talliedSeconds = addTimeDivision(dayMultiplier, "days", builder, talliedSeconds);
        talliedSeconds = addTimeDivision(hourMultiplier, "hours", builder, talliedSeconds);
        talliedSeconds = addTimeDivision(minuteMultiplier, "minutes", builder, talliedSeconds);
        addTimeDivision(secondMultiplier, "seconds", builder, talliedSeconds);

        return builder.toString();
    }

    private static BigInteger addTimeDivision(BigInteger multiplier,
                                              String label,
                                              StringBuilder builder,
                                              BigInteger talliedSeconds) {
        BigInteger units = talliedSeconds.divide(multiplier);
        if (units.compareTo(BigInteger.ZERO) > 0) {
            builder.append(" ");
            builder.append(units);
            builder.append(" ");
            builder.append(label);
        }
        return talliedSeconds.subtract(units.multiply(multiplier));
    }
}
