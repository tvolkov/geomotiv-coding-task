package rubiconproject;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.keywordservice.KeywordService;
import rubiconproject.model.Entry;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputDataKeywordsProviderTest {

    private InputDataKeywordsProvider inputDataKeywordsProvider;

    @Mock
    private KeywordService mockKeywordService;

    @Before
    public void setUp(){
        inputDataKeywordsProvider = new InputDataKeywordsProvider(mockKeywordService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInputListIsNull(){
        //when
        inputDataKeywordsProvider.provideKeywords(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInputListIsEmpty(){
        //when
        inputDataKeywordsProvider.provideKeywords(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldProviderEntriesWithKeywords(){
        //given
        Entry entry1 = new Entry("1", "1", "1", "1");
        Entry entry2 = new Entry("2", "2", "2", "2");
        Entry entry3 = new Entry("3", "3", "3", "3");
        List<Entry> inputEntries = Lists.newArrayList(entry1, entry2, entry3);

        when(mockKeywordService.resolveKeywords(entry1)).thenReturn("keyword1");
        when(mockKeywordService.resolveKeywords(entry2)).thenReturn("keyword2");
        when(mockKeywordService.resolveKeywords(entry3)).thenReturn("keyword3");

        //when
        List<Entry> entriesWithKeywords = inputDataKeywordsProvider.provideKeywords(inputEntries);

        //then
        assertEquals(3, entriesWithKeywords.size());
        assertEquals("keyword1", entriesWithKeywords.get(0).getKeywords());
        assertEquals("keyword2", entriesWithKeywords.get(1).getKeywords());
        assertEquals("keyword3", entriesWithKeywords.get(2).getKeywords());
    }
}