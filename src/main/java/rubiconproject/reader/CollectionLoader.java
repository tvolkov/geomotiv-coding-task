package rubiconproject.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import rubiconproject.model.Collection;

import java.io.File;

/**
 * Loads the collection of Entry's from either csv of json file
 */
@Slf4j
public class CollectionLoader {
    private final InputFileReaderFactory inputFileReaderFactory;

    public CollectionLoader(InputFileReaderFactory inputFileReaderFactory) {
        this.inputFileReaderFactory = inputFileReaderFactory;
    }

    public Collection loadCollection(String filePath){
        InputFileReader inputFileReader = inputFileReaderFactory.getReader(getBeanAlias(filePath), filePath);
        return new Collection(extractFileName(filePath), inputFileReader.readFile());
    }

    private String extractFileName(String filePath) {
        int lastIndexOfFileSeparator = filePath.lastIndexOf(File.separator);
        if (lastIndexOfFileSeparator == -1){
            return filePath;
        }
        return filePath.substring(lastIndexOfFileSeparator + 1);
    }

    private String getBeanAlias(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }
}
