package rubiconproject;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionIfThereAre0ProcessedEntries(){
        //given
        File mockFile = mock(File.class);
        InputFileReader mockInputDataReader = mock(InputFileReader.class);
        when(mockInputDataReader.readFile()).thenReturn(Collections.EMPTY_LIST);
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Lists.newArrayList(mockFile));
        when(mockedInputFileReaderProvider.getInputFileReader(mockFile)).thenReturn(mockInputDataReader);

        //when
        inputDataProcessor.processInputData();
    }

    @Test
    public void shouldReturnListOfProcessedEntries(){
        //given
        File mockFile = mock(File.class);
        InputFileReader mockInputDataReader = mock(InputFileReader.class);
        Entry entry1 = new Entry("1", "1", "1", "1");
        Entry entry2 = new Entry("2", "2", "2", "2");
        when(mockInputDataReader.readFile()).thenReturn(Lists.newArrayList(entry1, entry2));
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Lists.newArrayList(mockFile));
        when(mockedInputFileReaderProvider.getInputFileReader(mockFile)).thenReturn(mockInputDataReader);

        //when
        List<Entry> result = inputDataProcessor.processInputData();

        //then
        assertEquals(2, result.size());
        assertEquals(entry1, result.get(0));
        assertEquals(entry2, result.get(1));
    }
}