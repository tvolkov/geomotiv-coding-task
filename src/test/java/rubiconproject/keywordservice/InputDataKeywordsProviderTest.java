package rubiconproject.keywordservice;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.model.Entry;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputDataKeywordsProviderTest {

    @InjectMocks
    private InputDataKeywordsProvider inputDataKeywordsProvider;

    @Mock
    private KeywordService mockKeywordService;

    private static final String TEST_KEYWORD = "test";

    @Test
    public void shouldResolveKeywordsForGivenEntries(){
        //given
        Entry entry = new Entry("1", "1", "1", "1");
        List<Entry> entryList = Lists.newArrayList(entry);
        when(mockKeywordService.resolveKeywords(entry.getId())).thenReturn(TEST_KEYWORD);

        //when
        List<Entry> result = inputDataKeywordsProvider.provideKeywords(entryList);

        //then
        assertEquals(entryList.size(), result.size());
        assertEquals(TEST_KEYWORD, result.get(0).getKeywords());
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenEntryListIsEmpty(){
        //when
        inputDataKeywordsProvider.provideKeywords(Collections.emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenEntryListIsNull(){
        //when
        inputDataKeywordsProvider.provideKeywords(null);
    }

}