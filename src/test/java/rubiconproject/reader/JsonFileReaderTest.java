package rubiconproject.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonFileReaderTest {
    private JsonFileReader jsonFileReader;

    private static final String PATH = "path";
    private static final String COLLECTION_ID = "collection1";

    @Mock
    private ObjectMapper objectMapper;

    private File file;

    private List<Entry> mockedResultList;

    @Before
    public void setUp(){
        file = new File(PATH);
        mockedResultList = new ArrayList<>();
        mockedResultList.add(new Entry("1", "name","true", "12"));
        jsonFileReader = new JsonFileReader(PATH, objectMapper);
    }

    @Test
    public void shouldReturnListOfEntries() throws IOException {
        //given
        when(objectMapper.readValue(eq(file), any(TypeReference.class))).thenReturn(mockedResultList);

        //when
        Collection result = jsonFileReader.readFile(COLLECTION_ID);

        //then
        assertEquals(mockedResultList, result.getEntries());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionIfUnableToReadJsonFile() throws IOException {
        //given
        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenThrow(IOException.class);

        //when
        jsonFileReader.readFile(COLLECTION_ID);
    }

}