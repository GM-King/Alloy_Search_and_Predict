package elements.io;

import com.google.code.externalsorting.ExternalSort;

import java.io.*;
import java.util.*;

public class ResultSorter {

    public static void sort(String inputFilename,
                            String outputFilename,
                            final String targetName,
                            final Double targetValue) {
        if (!new File(inputFilename).exists()) {
            throw new IllegalArgumentException("File " + inputFilename + " does not exist");
        }

        final int targetIndex = findTargetIndex(inputFilename, targetName);
        ExternalSort.defaultcomparator = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                double lhsDistance = convertToDistanceMakingAnyStringVeryNegative(extractTargetField(lhs));
                double rhsDistance = convertToDistanceMakingAnyStringVeryNegative(extractTargetField(rhs));
                return Double.compare(lhsDistance, rhsDistance);
            }

            private double convertToDistanceMakingAnyStringVeryNegative(String field) {
                try {
                    return Math.abs(Double.parseDouble(field)- targetValue);
                } catch (NumberFormatException e) {
                    return -Double.MAX_VALUE;
                }
            }

            private String extractTargetField(String row) {
                String targetFieldToEndOfLine = row.substring(targetIndex);
                String targetField = targetFieldToEndOfLine.substring(0, targetFieldToEndOfLine.indexOf(","));
                return targetField.trim();
            }
        };

        try {
            ExternalSort.sort(
                    new File(inputFilename),
                    new File(outputFilename)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int findTargetIndex(String inputFilename,
                                       String targetName) {
        try (BufferedReader inputFileReader = new BufferedReader(new FileReader(inputFilename))) {
            String line = inputFileReader.readLine();
            String[] headings = line.split(",");
            for (int i = 0; i < headings.length; i++) {
                String heading = headings[i].trim();
                if (heading.endsWith(targetName)) {
                    return i;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("target name '" + targetName + "' not found");
    }

}
