package rubiconproject.reader;

import rubiconproject.model.Collection;

import java.util.Map;

/**
 * Loads the collection of Entry's from either csv of json file
 */
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
     *
     */
    private final Map<String, String> beanAliases;

    public CollectionLoader(InputFileReaderFactory inputFileReaderFactory, Map<String, String> beanAliases) {
        this.inputFileReaderFactory = inputFileReaderFactory;
        this.beanAliases = beanAliases;
    }

    public Collection loadCollection(String fileName){
        InputFileReader inputFileReader = inputFileReaderFactory.getReader(getBeanName(getBeanAlias(fileName)));
        return new Collection(fileName, inputFileReader.readFile());
    }

    private String getBeanName(String alias){
        return beanAliases.get(alias);
    }

    private String getBeanAlias(String fileName) {
        return fileName.substring(fileName.indexOf('.') + 1);
    }
}
