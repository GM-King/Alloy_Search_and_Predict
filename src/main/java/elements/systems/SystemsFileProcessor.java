package elements.systems;

import elements.concentrations.CalculationResult;
import elements.concentrations.CalculationResultFactory;
import elements.calculations.RegularCalculation;
import elements.input.Input;
import elements.input.InputFile;
import elements.io.ResultCSV;
import elements.io.ResultFolder;

import java.io.BufferedReader;
import java.io.FileReader;

public class SystemsFileProcessor {

    private static final String FILENAME_SYSTEMS_TXT = "systems.txt";
    private static final String FILENAME_SYSTEMS_CSV = "systems.csv";

    public static ResultFolder runInput(Input input) throws Exception {
        String targetName = input.getTargetName();
        RegularCalculation regularCalculation = new RegularCalculation(
                new CalculationResultFactory(targetName)
        );

        ResultFolder resultFolder = new ResultFolder(input);
        try (BufferedReader systemsFileReader = new BufferedReader(new FileReader(FILENAME_SYSTEMS_TXT))) {
            try (ResultCSV systemsCSV = openSystemsCSV(resultFolder)) {
                while (true) {
                    String rawLine = systemsFileReader.readLine();
                    if (rawLine == null) {
                        break;
                    }

                    String systemDescription = rawLine.trim();
                    if (systemDescription.isEmpty()) {
                        continue;
                    }

                    systemsCSV.write(createSystemCSVRow(
                            regularCalculation,
                            systemDescription
                    ));
                }
            }
        }

        return resultFolder;
    }

    private static ResultCSV openSystemsCSV(ResultFolder resultFolder) {
        return new ResultCSV(resultFolder.getPath() + FILENAME_SYSTEMS_CSV);
    }

    private static SystemCSVRow createSystemCSVRow(RegularCalculation regularCalculation,
                                                   String systemDescription) {
        CalculationResult resultRow = regularCalculation.calculate(
                SystemCalculator.getElements(systemDescription),
                SystemCalculator.getConcentrations(systemDescription)
        );

        return new SystemCSVRow(
                systemDescription,
                resultRow
        );
    }

}
