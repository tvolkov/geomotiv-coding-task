package rubiconproject;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;
import rubiconproject.reader.InputFileReader;
import rubiconproject.reader.InputFileReaderProvider;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputDataProcessorTest {
    private InputDataProcessor inputDataProcessor;

    @Mock
    private InputFileReaderProvider mockedInputFileReaderProvider;

    @Mock
    private FileListProvider mockedFileListProvider;

    private static final String COLLECTION_ID = "collection1";

    @Before
    public void setUp(){
        inputDataProcessor = new InputDataProcessor(mockedFileListProvider, mockedInputFileReaderProvider);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfThereAre0InputFiles(){
        //given
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Collections.EMPTY_LIST);

        //when
        inputDataProcessor.processInputData();
    }

    @Test
    public void shouldReturnListOfProcessedEntries(){
        //given
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn(COLLECTION_ID);
        InputFileReader mockInputDataReader = mock(InputFileReader.class);
        Entry entry1 = new Entry("1", "1", "1", "1");
        Entry entry2 = new Entry("2", "2", "2", "2");
        Collection collection = new Collection(COLLECTION_ID, Lists.newArrayList(entry1, entry2));
        when(mockInputDataReader.readFile(COLLECTION_ID)).thenReturn(collection);
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Lists.newArrayList(mockFile));
        when(mockedInputFileReaderProvider.getInputFileReader(mockFile)).thenReturn(mockInputDataReader);

        //when
        List<Collection> result = inputDataProcessor.processInputData();

        //then
        assertEquals(1, result.size());
        List<Entry> resultEntries = result.get(0).getEntries();
        assertEquals(2, resultEntries.size());
        assertEquals(entry1, resultEntries.get(0));
        assertEquals(entry2, resultEntries.get(1));
    }
}