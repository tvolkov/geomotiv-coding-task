package rubiconproject;

import rubiconproject.model.Collection;
import rubiconproject.model.Entry;
import rubiconproject.reader.InputFileReaderProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * creates list of collection of entires. No exceptions are thrown in case of empty collections.
 */
public class InputDataProcessor {
    private FileListProvider fileListProvider;
    private InputFileReaderProvider inputFileReaderProvider;

    private List<Collection> entries = new ArrayList<>();

    public InputDataProcessor(FileListProvider fileListProvider, InputFileReaderProvider inputFileReaderProvider) {
        this.fileListProvider = fileListProvider;
        this.inputFileReaderProvider = inputFileReaderProvider;
    }

    public  List<Collection> processInputData(){
        List<File> inputFiles = fileListProvider.getInputFilesList();
        if (inputFiles.size() == 0){
            throw new IllegalStateException("There are no iles in the input directory");
        }
        for (File file : inputFiles){
            entries.add(inputFileReaderProvider.getInputFileReader(file).readFile(file.getName()));
        }

        return entries;
    }
}
