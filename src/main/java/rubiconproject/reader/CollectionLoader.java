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

    /**
     * Currently it's not possible to define bean aliases in spring groovy config (there's nothing similar to xml config's <alias>)
     * Hence this workaround: store bean aliases in a map.
     * On the other hand bean aliases can be registered before loading context using e.g.
     * applicationContext.registerAlias("csvFileReader", "csv");
     * But it would require refreshing application context which will, inter alia, lead to unnecessary initialization of
     * prototype beans which will result in exception since some of the prototype beans are defined with the dummy constructor args,
     * which are not supposed to be passed in their constructors
     * So, despite the fact that having bean names in code is bad idea, I think this is most optimal trade-off in this situation
     */
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
