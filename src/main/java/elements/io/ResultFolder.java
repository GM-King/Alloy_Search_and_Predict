package elements.io;

import elements.input.Input;
import elements.input.InputFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultFolder {
    private final File resultFolder;

    public ResultFolder(Input input) {
        resultFolder = createResultFolder(input);
        copyInputFile(resultFolder);
    }

    public String getPath() {
        return resultFolder.getPath() + "/";
    }

    public String getCsvPath() {
        String[] csvFiles = FileUtils.getFilesFromExtension(
                resultFolder.getPath(),
                new String[] {"csv"}
        );
        return csvFiles[0];
    }

    private static File createResultFolder(Input input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String filename = "../java-calculations-results/" + simpleDateFormat.format(new Date()) + input.getElementSpecifier();
        File resultFolder = new File(filename);
        resultFolder.mkdirs();
        return resultFolder;
    }

    private static void copyInputFile(File resultFolder) {
        try {
            File copiedInputFile = new File(resultFolder, InputFile.INPUT_FILENAME);
            Files.copy(new File(InputFile.INPUT_FILENAME).toPath(), copiedInputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
