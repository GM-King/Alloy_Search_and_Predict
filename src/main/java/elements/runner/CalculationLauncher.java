package elements.runner;

import elements.concentrations.CalculationResultSummary;
import elements.input.Input;
import elements.input.InputFile;
import elements.io.*;

import java.io.File;

public class CalculationLauncher {
    private static final String FILENAME_DETAILED_SORTED = "detailed.csv";
    private static final String FILENAME_SUMMARY_SORTED = "summary.csv";
    private static final String SUFFIX_UNSORTED = ".incomplete";
    public static final String FILENAME_SUMMARY_UNSORTED = FILENAME_SUMMARY_SORTED + SUFFIX_UNSORTED;
    public static final String FILENAME_DETAILED_UNSORTED = FILENAME_DETAILED_SORTED + SUFFIX_UNSORTED;

    public static ResultFolder runInput(Input input) throws Exception {
        BestCalculationResultSummaryListener bestCalculationResultSummaryListener = new BestCalculationResultSummaryListener();

        ResultFolder resultFolder = new ResultFolder(input);
        try (ResultCSV detailedResultCSV = openDetailedCSV(input, resultFolder)) {
            try (ResultCSV summaryResultCSV = openSummaryCSV(input, resultFolder)) {
                CalculationRunner calculationRunner = new CalculationRunner(
                        input,
                        detailedResultCSV
                );

                if (summaryResultCSV != null) {
                    if (input.isOutputBestResultOnly()) {
                        calculationRunner.addListener(bestCalculationResultSummaryListener);
                    } else {
                        calculationRunner.addListener(new CalculationResultSummaryListener() {
                            @Override
                            public void handle(CalculationResultSummary summary) {
                                if (summary.noValuesHaveBeenSet()) {
                                    return;
                                }
                                summaryResultCSV.write(summary);
                            }
                        });
                    }
                }

                calculationRunner.run();

                if (summaryResultCSV != null && input.isOutputBestResultOnly()) {
                    for (CalculationResultSummary calculationResultSummary : bestCalculationResultSummaryListener.getBestSummaries()) {
                        summaryResultCSV.write(calculationResultSummary);
                    }
                }
            }
        }

        String outputFilename;
        String inputFilename;
        if (!input.hasMultipleSelections()) {
            inputFilename = FILENAME_DETAILED_UNSORTED;
            outputFilename = FILENAME_DETAILED_SORTED;
        } else {
            inputFilename = FILENAME_SUMMARY_UNSORTED;
            outputFilename = FILENAME_SUMMARY_SORTED;
        }

        System.out.println("Sorting output...");
        System.out.println();
        sortAndReplace(
                resultFolder.getPath(),
                inputFilename,
                outputFilename,
                input.getTargetName(),
                input.getTargetValue()
        );

        return resultFolder;
    }

    private static ResultCSV openSummaryCSV(Input input,
                                            ResultFolder resultFolder) {
        if (!input.hasMultipleSelections()) {
            return null;
        }
        return new ResultCSV(resultFolder.getPath() + FILENAME_SUMMARY_UNSORTED);
    }

    private static ResultCSV openDetailedCSV(Input input,
                                             ResultFolder resultFolder) {
        if (input.hasMultipleSelections()) {
            return null;
        }
        return new ResultCSV(resultFolder.getPath() + FILENAME_DETAILED_UNSORTED);
    }

    private static void sortAndReplace(String path,
                                       String tempFilename,
                                       String sortedFilename,
                                       String targetName,
                                       double targetValue) {
        ResultSorter.sort(
                path + tempFilename,
                path + sortedFilename,
                targetName,
                targetValue
        );

        new File(path + tempFilename)
                .delete();
    }
}