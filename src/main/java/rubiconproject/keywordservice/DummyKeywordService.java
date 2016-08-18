package rubiconproject.keywordservice;

import rubiconproject.model.Entry;

import java.util.Map;

public class DummyKeywordService implements KeywordService {

    private final Map<Integer, String> keywords;

    private static final int MASK = 7; //we will only distinguish three types of ids


    public DummyKeywordService(Map<Integer, String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String resolveKeywords(Object entityId) {
        Integer id = Integer.parseInt((String) entityId);
        return keywords.get(id & MASK);
    }
}
