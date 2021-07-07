package elements.controllers;

import elements.calculations.Element;
import elements.calculations.ElementData;
import elements.calculations.OutputParameter;
import elements.input.Input;
import elements.input.InputFile;
import elements.input.WebInputRequest;
import elements.io.*;
import elements.runner.CalculationLauncher;
import elements.runner.IntervalFormatter;
import elements.runner.Stopwatch;
import elements.systems.SystemsFileProcessor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.*;

@Controller
public class AlloyController {

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET)
    public String alloyCalculator(Map<String, Object> model) {
        model.put("outputParameters", OutputParameter.values());
        model.put("availableElements", availableElementSymbolsAndWildcard());
        return "alloy-calculator";
    }

    @RequestMapping(
            value = "/results.csv",
            method = RequestMethod.GET,
            produces = "text/csv")
    @ResponseBody
    public String search(@RequestParam final OutputParameter optimisationParameter,
                         @RequestParam final Double targetValue,
                         @RequestParam final Double temperature,
                         @RequestParam final Integer numberOfElements,
                         @RequestParam final String element1,
                         @RequestParam final String element2,
                         @RequestParam final String element3,
                         @RequestParam final String element4,
                         @RequestParam final String element5,
                         @RequestParam final String element6,
                         @RequestParam final Double stepSize) throws Exception {

        StringBuilder builder = new StringBuilder();
        builder.append("optimisationParameter," + optimisationParameter + "\n");
        builder.append("targetValue," + targetValue + "\n");
        builder.append("temperature," + temperature + "\n");
        builder.append("numberOfElements," + numberOfElements + "\n");
        builder.append("element1," + element1 + "\n");
        builder.append("element2," + element2 + "\n");
        builder.append("element3," + element3 + "\n");
        builder.append("element4," + element4 + "\n");
        builder.append("element5," + element5 + "\n");
        builder.append("element6," + element6 + "\n");
        builder.append("stepSize," + stepSize + "\n");

        String elementList = element1 + "," + element2 + "," + element3 + "," + element4 + "," + element5 + "," + element6;
        return runCalculations(new WebInputRequest(
                optimisationParameter,
                targetValue,
                temperature,
                numberOfElements,
                elementList,
                stepSize
        ));
    }

    private List<String> availableElementSymbolsAndWildcard() {
        List<String> elements = new ArrayList<>();
        elements.add("*");
        for (Element element : ElementData.getAllAvailableElements()) {
            elements.add(element.getSymbol());
        }
        return elements;
    }

    private String runCalculations(Input input) throws Exception {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        ResultFolder resultFolder;
        if (input.useFileSystemTxt()) {
            resultFolder = SystemsFileProcessor.runInput(input);
        } else {
            resultFolder = CalculationLauncher.runInput(input);
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

        return FileUtils.fileRead(resultFolder.getCsvPath());
    }

}
