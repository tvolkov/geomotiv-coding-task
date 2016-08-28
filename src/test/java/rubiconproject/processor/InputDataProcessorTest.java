package rubiconproject.processor;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.keywordservice.InputDataKeywordsProvider;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;
import rubiconproject.reader.CollectionLoader;
import rubiconproject.reader.InputFileReader;

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
    private FileListProvider mockedFileListProvider;

    @Mock
    private InputDataKeywordsProvider mockInputDataKeywordsProvider;

    @Mock
    private CollectionLoader mockCollectionLoader;

    private static final String COLLECTION1 = "collection1.csv";
    private static final String COLLECTION2 = "collection2.json";
    private static final String INPUT_PATH = "path";
    private static final File INPUT_DIR = new File(INPUT_PATH);

    @Before
    public void setUp(){
        when(mockInputDataKeywordsProvider.provideKeywords(anyListOf(Entry.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
    }

    @Test
    public void shouldReturnListOfProcessedEntries(){
        //given
        List<String> fileNames = Lists.newArrayList(COLLECTION1, COLLECTION2);

        InputFileReader mockInputDataReader = mock(InputFileReader.class);

        Entry entry1 = new Entry("1", "1", "1", "1");
        Entry entry2 = new Entry("2", "2", "2", "2");
        Collection collection1 = new Collection(COLLECTION1, Lists.newArrayList(entry1, entry2));
        List<Entry> list1 = Lists.newArrayList(entry1, entry2);

        Entry entry3 = new Entry("3", "3", "3", "3");
        Entry entry4 = new Entry("4", "4", "4", "4");
        Collection collection2 = new Collection(COLLECTION2, Lists.newArrayList(entry3, entry4));
        List<Entry> list2 = Lists.newArrayList(entry3, entry4);

        when(mockInputDataReader.readFile()).thenReturn(list1);
        when(mockInputDataReader.readFile()).thenReturn(list2);
        when(mockedFileListProvider.getInputFilesList(INPUT_DIR)).thenReturn(fileNames);
        when(mockCollectionLoader.loadCollection(COLLECTION1)).thenReturn(collection1);
        when(mockCollectionLoader.loadCollection(COLLECTION2)).thenReturn(collection2);

        //when
        List<Collection> result = inputDataProcessor.processInputData(INPUT_PATH);

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
        when(mockedFileListProvider.getInputFilesList(INPUT_DIR)).thenReturn(Collections.emptyList());

        //when
        List<Collection> result = inputDataProcessor.processInputData(INPUT_PATH);

        //then
        assertEquals(0, result.size());
    }
}