package rubiconproject.processor;

import lombok.extern.slf4j.Slf4j;
import rubiconproject.keywordservice.InputDataKeywordsProvider;
import rubiconproject.model.Collection;
import rubiconproject.reader.CollectionLoader;

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
    private final InputDataKeywordsProvider inputDataKeywordsProvider;
    private final CollectionLoader collectionLoader;

    public InputDataProcessor(FileListProvider fileListProvider, InputDataKeywordsProvider inputDataKeywordsProvider, CollectionLoader collectionLoader) {
        this.fileListProvider = fileListProvider;
        this.inputDataKeywordsProvider = inputDataKeywordsProvider;
        this.collectionLoader = collectionLoader;
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
        List<Collection> collections = new ArrayList<>();

        collections.addAll(inputFiles.stream()
                .map(file -> collectionLoader.loadCollection(file.getName()))
                .collect(Collectors.toList()));
        collections.forEach(collection -> inputDataKeywordsProvider.provideKeywords(collection.getEntries()));
        return collections;
    }
}
