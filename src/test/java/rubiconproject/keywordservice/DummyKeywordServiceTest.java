package rubiconproject.keywordservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DummyKeywordServiceTest {
    private DummyKeywordService dummyKeywordService;
    private Map<Integer, String> keywords = new HashMap<Integer, String>(){{
        put(0, "0");
        put(1, "1");
        put(2, "2");
    }};

    @Before
    public void setUp(){
        dummyKeywordService = new DummyKeywordService(keywords);
    }

    @Test
    public void shouldAssignKeywordBasedOnProvidedEntityId(){
        //given
        String id1 = "12000", id2 = "12001", id3 = "12002";

        //when
        String result1= dummyKeywordService.resolveKeywords(id1);
        String result2= dummyKeywordService.resolveKeywords(id2);
        String result3= dummyKeywordService.resolveKeywords(id3);

        //then
        assertEquals("0", result1);
        assertEquals("1", result2);
        assertEquals("2", result3);
    }
}