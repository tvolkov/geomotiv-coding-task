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
        //todo make it a string
        this.inputDirectory = inputDirectory;
        this.inputFileValidator = inputFileValidator;
        log.debug("initialized with inputDirectory " + inputDirectory);
    }

    //todo return file names instead of files
    public List<File> getInputFilesList() {
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

        List<File> inputFiles = new ArrayList<>();
        for (File file : files)
            if (inputFileValidator.isFileValid(file)) {
                inputFiles.add(file);
            }

        return inputFiles;
    }
}
