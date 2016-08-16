package rubiconproject;

import rubiconproject.model.Entry;
import rubiconproject.reader.InputFileReaderProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InputDataProcessor {
    private FileListProvider fileListProvider;
    private InputFileReaderProvider inputFileReaderProvider;

    private List<Entry> entries = new ArrayList<>();

    public InputDataProcessor(FileListProvider fileListProvider, InputFileReaderProvider inputFileReaderProvider) {
        this.fileListProvider = fileListProvider;
        this.inputFileReaderProvider = inputFileReaderProvider;
    }

    public List<Entry> processInputData(){
        List<File> inputFiles = fileListProvider.getInputFilesList();
        if (inputFiles.size() == 0){
            throw new IllegalStateException("There are no iles in the input directory");
        }
        for (File file : inputFiles){
            //Assuming it's ok for input file to be empty, we'd rather check if we have some data after processing all the files
            entries.addAll(inputFileReaderProvider.getInputFileReader(file).readFile());
        }

        if (entries.size() == 0){
            throw new RuntimeException("There're no entries in the input files");
        }

        return entries;
    }
}
