package rubiconproject.processor;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns list of file names in the given directory
 */
@Slf4j
public class FileListProvider {
    private final InputFileValidator inputFileValidator;

    public FileListProvider(InputFileValidator inputFileValidator) {
        this.inputFileValidator = inputFileValidator;
    }

    public List<String> getInputFilesList(File inputDir) {
        log.info("inputDirectory " + inputDir.getPath());

        validateInputFiles(inputDir);

        List<String> inputFiles = new ArrayList<>();
        for (File file : inputDir.listFiles())
            if (inputFileValidator.isFileValid(file)) {
                inputFiles.add(file.getPath());
            }

        return inputFiles;
    }

    private void validateInputFiles(File inputDirectory) {
        if (!inputDirectory.exists()) {
            throw new IllegalArgumentException("Directory " + inputDirectory + " doesn't exist");
        }

        if (!inputDirectory.isDirectory()) {
            throw new IllegalArgumentException(inputDirectory + " is not a directory");
        }

        if (inputDirectory.listFiles() == null){
            throw new IllegalStateException("Can't read files from directory");
        }
    }
}
