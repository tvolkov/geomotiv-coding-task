package rubiconproject.processor;

import lombok.extern.slf4j.Slf4j;
import rubiconproject.keywordservice.InputDataKeywordsProvider;
import rubiconproject.model.Collection;
import rubiconproject.reader.InputFileReaderProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * creates list of collection of entries. No exceptions are thrown in case of empty collections.
 */
@Slf4j
public class InputDataProcessor {
    private final FileListProvider fileListProvider;
    private final InputFileReaderProvider inputFileReaderProvider;
    private final InputDataKeywordsProvider inputDataKeywordsProvider;

    public InputDataProcessor(FileListProvider fileListProvider, InputFileReaderProvider inputFileReaderProvider, InputDataKeywordsProvider inputDataKeywordsProvider) {
        this.fileListProvider = fileListProvider;
        this.inputFileReaderProvider = inputFileReaderProvider;
        this.inputDataKeywordsProvider = inputDataKeywordsProvider;
    }

    public List<Collection> processInputData(){
        List<File> inputFiles = fileListProvider.getInputFilesList();
        if (inputFiles.size() == 0){
            log.info("no input files found. exiting");
            return Collections.emptyList();
        }

        return process(inputFiles);
    }

    private List<Collection> process(List<File> inputFiles){
        log.info("processing " + inputFiles.size() + " input files");
        List<Collection> entries = new ArrayList<>();
        entries.addAll(inputFiles.stream().map(file -> inputFileReaderProvider.getInputFileReader(file).readFile(file.getName())).collect(Collectors.toList()));
        entries.forEach(collection -> inputDataKeywordsProvider.provideKeywords(collection.getEntries()));
        return entries;
    }
}
