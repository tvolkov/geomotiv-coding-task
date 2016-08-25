package rubiconproject;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private InputDataProcessor inputDataProcessor;

    @Mock
    private InputFileReaderProvider mockedInputFileReaderProvider;

    @Mock
    private FileListProvider mockedFileListProvider;

    @Mock
    private InputDataKeywordsProvider mockInputDataKeywordsProvider;

    private static final String COLLECTION1 = "collection1.csv";
    private static final String COLLECTION2 = "collection2.json";

    @Before
    public void setUp(){
        when(mockInputDataKeywordsProvider.provideKeywords(anyListOf(Entry.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        inputDataProcessor = new InputDataProcessor(mockedFileListProvider, mockedInputFileReaderProvider, mockInputDataKeywordsProvider);
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

    @Test
    public void shouldReturnEmptyListIfNoInputFilesFound(){
        //given
        when(mockedFileListProvider.getInputFilesList()).thenReturn(Collections.EMPTY_LIST);

        //when
        List<Collection> result = inputDataProcessor.processInputData();

        //then
        assertEquals(0, result.size());
    }
}