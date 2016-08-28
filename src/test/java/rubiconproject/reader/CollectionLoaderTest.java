package rubiconproject.reader;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CollectionLoaderTest {

    @InjectMocks
    private CollectionLoader collectionLoader;

    @Mock
    private InputFileReaderFactory mockInputFileReaderFactory;

    private static final String PATH = "file.csv";

    @Test
    public void shouldReturnCollection() {
        //given
        InputFileReader mockReader = mock(InputFileReader.class);
        when(mockReader.readFile()).thenReturn(Lists.newArrayList(new Entry("1", "2", "3", "4")));
        when(mockInputFileReaderFactory.getReader("csv", PATH)).thenReturn(mockReader);

        //when
        Collection result = collectionLoader.loadCollection(PATH);

        //then
        assertEquals(PATH, result.getCollectionId());
        Entry entry = result.getEntries().get(0);
        assertEquals("1", entry.getId());
        assertEquals("2", entry.getName());
        assertEquals("3", entry.getIsMobile());
        assertEquals("4", entry.getScore());
    }

}