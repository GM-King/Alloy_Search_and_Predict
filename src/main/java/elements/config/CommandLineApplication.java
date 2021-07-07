package elements.config;

import elements.input.InputFile;
import elements.io.PreviewPrinter;
import elements.io.ResultColumnAligner;
import elements.io.ResultFolder;
import elements.runner.CalculationLauncher;
import elements.runner.IntervalFormatter;
import elements.runner.Stopwatch;
import elements.systems.SystemsFileProcessor;

import java.math.BigInteger;

public class CommandLineApplication {

    public static void main(String[] args) throws Exception {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        InputFile inputFile = new InputFile(InputFile.INPUT_FILENAME);
        ResultFolder resultFolder;
        if (inputFile.useFileSystemTxt()) {
            resultFolder = SystemsFileProcessor.runInput(inputFile);
        } else {
            resultFolder = CalculationLauncher.runInput(inputFile);
        }

        stopwatch.stop();

        ResultColumnAligner.alignColumns(
                resultFolder.getCsvPath()
        );

        PreviewPrinter.showFilePreview(
                resultFolder.getCsvPath()
        );

        BigInteger timeIntervalSeconds = BigInteger.valueOf((long) stopwatch.getSeconds());
        String formattedTimeInterval = IntervalFormatter.formatSeconds(timeIntervalSeconds);
        System.out.println("Total time taken: " + formattedTimeInterval);
    }

}
