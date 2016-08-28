package rubiconproject.processor;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns list of files in the given directory
 */
@Slf4j
public class FileListProvider {
    private final File inputDirectory;
    private final InputFileValidator inputFileValidator;

    public FileListProvider(File inputDirectory, InputFileValidator inputFileValidator) {
        this.inputDirectory = inputDirectory;
        this.inputFileValidator = inputFileValidator;
        log.debug("initialized with inputDirectory " + inputDirectory);
    }

    public List<String> getInputFilesList() {
        if (!inputDirectory.exists()) {
            throw new IllegalArgumentException("Directory " + inputDirectory + " doesn't exist");
        }

        if (!inputDirectory.isDirectory()) {
            throw new IllegalArgumentException(inputDirectory + " is not a directory");
        }

        File[] files = inputDirectory.listFiles();
        if (files == null){
            throw new IllegalStateException("Can't read files from directory");
        }

        List<String> inputFiles = new ArrayList<>();
        for (File file : files)
            if (inputFileValidator.isFileValid(file)) {
                inputFiles.add(file.getPath());
            }

        return inputFiles;
    }
}
