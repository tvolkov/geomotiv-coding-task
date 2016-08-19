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

    public FileListProvider(File inputDirectory) {
        this.inputDirectory = inputDirectory;
        log.debug("initialized with inputDirectory " + inputDirectory);
    }

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
            if (file.isFile()) {
                inputFiles.add(file);
            }

        return inputFiles;
    }
}
