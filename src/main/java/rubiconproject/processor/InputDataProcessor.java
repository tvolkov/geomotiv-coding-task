package rubiconproject.processor;

import org.springframework.beans.factory.annotation.Value;
import rubiconproject.keywordservice.InputDataKeywordsProvider;
import rubiconproject.model.Collection;
import rubiconproject.reader.InputFileReaderProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * creates list of collection of entries. No exceptions are thrown in case of empty collections.
 */
public class InputDataProcessor {
    private final FileListProvider fileListProvider;
    private final InputFileReaderProvider inputFileReaderProvider;
    private final InputDataKeywordsProvider inputDataKeywordsProvider;

    private String[] allowedFileExtensions;

    private final List<Collection> entries = new ArrayList<>();

    public InputDataProcessor(FileListProvider fileListProvider, InputFileReaderProvider inputFileReaderProvider, InputDataKeywordsProvider inputDataKeywordsProvider) {
        this.fileListProvider = fileListProvider;
        this.inputFileReaderProvider = inputFileReaderProvider;
        this.inputDataKeywordsProvider = inputDataKeywordsProvider;
    }

    public List<Collection> processInputData(){
        List<File> inputFiles = fileListProvider.getInputFilesList();
        validateInputFiles(inputFiles);
        entries.addAll(inputFiles.stream().map(file -> inputFileReaderProvider.getInputFileReader(file).readFile(file.getName())).collect(Collectors.toList()));
        entries.forEach(collection -> inputDataKeywordsProvider.provideKeywords(collection.getEntries()));

        return entries;
    }

    private void validateInputFiles(List<File> inputFiles) {
        //as per requirements, there should only be 2 input files
        if (inputFiles.size() != 2){
            throw new IllegalStateException("There are no files in the input directory");
        }
        if (!(endsWithAny(inputFiles.get(0).getName(), allowedFileExtensions) || endsWithAny(inputFiles.get(1).getName(), allowedFileExtensions))){
            throw new IllegalArgumentException("Incorrect file extensions");
        }
    }

    @Value("${allowed.file.extensions}")
    public void setAllowedFileExtensions(String[] allowedFileExtensions) {
        this.allowedFileExtensions = allowedFileExtensions;
    }
}
