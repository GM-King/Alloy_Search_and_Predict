package elements.io;

import java.io.BufferedReader;
import java.io.FileReader;

public class PreviewPrinter {
    public static final int PREVIEW_LINES = 3;

    public static void showFilePreview(String outputFilename) {
        System.out.println("===== Preview of output file =====");

        boolean truncated;
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFilename))) {
            truncated = false;
            int i = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                System.out.println(line);

                if (i == PREVIEW_LINES) {
                    truncated = true;
                    System.out.println("...");
                    break;
                }

                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.print("===== ");
        if (truncated) {
            System.out.print("Output truncated, full output");
        } else {
            System.out.print("Output");
        }
        System.out.println(" saved in " + outputFilename + " =====");
        System.out.println();
    }
}
