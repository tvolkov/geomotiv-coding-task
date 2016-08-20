package rubiconproject;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import rubiconproject.keywordservice.InputDataKeywordsProvider;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;
import rubiconproject.processor.FileListProvider;
import rubiconproject.processor.InputDataProcessor;
import rubiconproject.reader.InputFileReader;
import rubiconproject.reader.InputFileReaderProvider;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputDataProcessorTest {
    @InjectMocks
    private InputDataProcessor inputDataProcessor;

    @Mock
    private InputFileReaderProvider mockedInputFileReaderProvider;

    @Mock
    private FileListProvider mockedFileListProvider;

    @Mock
    private InputDataKeywordsProvider mockInputDataKeywordsProvider;

    private static final String COLLECTION1 = "collection1.csv";
    private static final String COLLECTION2 = "collection2.json";

    private static final String[] ALLOWED_EXTENSIONS = {".csv", ".json"};

    @Before
    public void setUp(){
        inputDataProcessor.setAllowedFileExtensions(ALLOWED_EXTENSIONS);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfThereAre0InputFiles(){
        //given
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Collections.emptyList());

        //when
        inputDataProcessor.processInputData();
    }

    @Test
    public void shouldReturnListOfProcessedEntries(){
        //given
        File mockFile1 = mock(File.class);
        when(mockFile1.getName()).thenReturn(COLLECTION1);
        File mockFile2 = mock(File.class);
        when(mockFile2.getName()).thenReturn(COLLECTION2);

        InputFileReader mockInputDataReader = mock(InputFileReader.class);

        Entry entry1 = new Entry("1", "1", "1", "1");
        Entry entry2 = new Entry("2", "2", "2", "2");
        Collection collection1 = new Collection(COLLECTION1, Lists.newArrayList(entry1, entry2));

        Entry entry3 = new Entry("3", "3", "3", "3");
        Entry entry4 = new Entry("4", "4", "4", "4");
        Collection collection2 = new Collection(COLLECTION2, Lists.newArrayList(entry3, entry4));

        when(mockInputDataReader.readFile(COLLECTION1)).thenReturn(collection1);
        when(mockInputDataReader.readFile(COLLECTION2)).thenReturn(collection2);
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Lists.newArrayList(mockFile1, mockFile2));
        when(mockedInputFileReaderProvider.getInputFileReader(mockFile1)).thenReturn(mockInputDataReader);
        when(mockedInputFileReaderProvider.getInputFileReader(mockFile2)).thenReturn(mockInputDataReader);

        //when
        List<Collection> result = inputDataProcessor.processInputData();

        //then
        assertEquals(2, result.size());
        List<Entry> resultEntries = result.get(0).getEntries();
        assertEquals(2, resultEntries.size());
        assertEquals(entry1, resultEntries.get(0));
        assertEquals(entry2, resultEntries.get(1));

        resultEntries = result.get(1).getEntries();
        assertEquals(2, resultEntries.size());
        assertEquals(entry3, resultEntries.get(0));
        assertEquals(entry4, resultEntries.get(1));
    }
}