package rubiconproject.keywordservice;

import rubiconproject.model.Entry;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * gets list of entries and inserts keywords to each element
 */
public class InputDataKeywordsProvider {
    private final KeywordService keywordService;

    public InputDataKeywordsProvider(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public List<Entry> provideKeywords(List<Entry> inputEntries) {
        if (isEmpty(inputEntries)){
            throw new IllegalArgumentException("input entries list is empty!");
        }

        for (Entry entry : inputEntries) {
            entry.setKeywords(keywordService.resolveKeywords(entry.getId()));
        }
        return inputEntries;
    }
}
