package elements.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ResultColumnAligner {
    public static void alignColumns(String inputFile) {
        try {
            Map<Integer, Integer> maxWidths = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }

                    String[] fields = line.split(",");
                    for (int i = 0; i < fields.length; i++) {
                        if (!maxWidths.containsKey(i)) {
                            maxWidths.put(i, 0);
                        }

                        int previousMaxWidth = maxWidths.get(i);
                        int width = fields[i].trim().length();
                        if (width > previousMaxWidth) {
                            maxWidths.put(i, width);
                        }
                    }
                }
            }

            String alignedTempFileName = inputFile + ".aligned";
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(alignedTempFileName))) {
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }

                        StringBuilder alignedLineBuilder = new StringBuilder();
                        String[] fields = line.split(",");
                        for (int i = 0; i < fields.length; i++) {
                            String alignedField = StringUtils.leftPad(fields[i].trim(), maxWidths.get(i));
                            alignedLineBuilder.append(alignedField);
                            if (i < fields.length - 1) {
                                alignedLineBuilder.append(',');
                            }
                        }
                        alignedLineBuilder.append('\n');
                        writer.write(alignedLineBuilder.toString());
                    }
                }
            }

            FileUtils.fileDelete(inputFile);
            FileUtils.fileCopy(alignedTempFileName, inputFile);
            FileUtils.fileDelete(alignedTempFileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}