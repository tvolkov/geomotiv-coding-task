package rubiconproject.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultPrinterTest {
    private ResultPrinter resultPrinter;

    private ObjectMapper objectMapper;

    private List<Entry> entries = new ArrayList<Entry>(){{
        add(new Entry("1", "name", "mobile", "score"));
    }};

    private List<Collection> inputData = new ArrayList<Collection>(){{
        add(new Collection("collection1", entries));
    }};
    private static final String EXPECTED_RESULT = "{\"collectionId\":\"collection1\",\"sites\":[{\"name\":\"name\",\"score\":\"score\",\"keywords\":\"\",\"id\":\"1\",\"site_id\":\"1\",\"mobile\":\"mobile\"}]}";

    private static final String LS = System.lineSeparator();
    private static final String EXPECTED_PRETTY_RESULT = "{" + LS +
            "  \"collectionId\" : \"collection1\"," + LS +
            "  \"sites\" : [ {" + LS +
            "    \"name\" : \"name\"," + LS +
            "    \"score\" : \"score\"," + LS +
            "    \"keywords\" : \"\"," + LS +
            "    \"id\" : \"1\"," + LS +
            "    \"site_id\" : \"1\"," + LS +
            "    \"mobile\" : \"mobile\"" + LS +
            "  } ]" + LS +
            "}";

    @Test
    public void shouldPrintCorrectResult(){
        //given
        objectMapper = new ObjectMapper();
        resultPrinter = new ResultPrinter(objectMapper);
        resultPrinter.setPrettyPrintResult(false);

        //when
        String result = resultPrinter.printResult(inputData);

        //then
        assertEquals(EXPECTED_RESULT, result);
    }

    @Test
    public void shouldPrettyPrintCorrectCresult(){
        //given
        objectMapper = new ObjectMapper();
        resultPrinter = new ResultPrinter(objectMapper);
        resultPrinter.setPrettyPrintResult(true);

        //when
        String result = resultPrinter.printResult(inputData);

        //then
        assertEquals(EXPECTED_PRETTY_RESULT, result);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenExceptionOccurs() throws IOException {
        //given
        objectMapper = mock(ObjectMapper.class);
        ObjectWriter mockWriter = mock(ObjectWriter.class);
        when(mockWriter.writeValueAsString(anyString())).thenThrow(RuntimeException.class);
        when(objectMapper.writer()).thenReturn(mockWriter);
        resultPrinter = new ResultPrinter(objectMapper);

        //when
        resultPrinter.printResult(inputData);
    }
}