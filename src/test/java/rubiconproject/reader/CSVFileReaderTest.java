package rubiconproject.reader;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CSVFileReaderTest {
    @InjectMocks
    private CSVFileReader csvFileReader;

    @Mock
    private CSVReader mockedCsvReader;

    private static final String ID = "12000";
    private static final String NAME = "example.com/csv1";
    private static final String MOBILE = "true";
    private static final String SCORE = "454";
    private static final String[] CSV_HEADER = {"id", "name", "is mobile", "score"};
    private static final String[] MOCKED_CSV_LINE = {ID, NAME, MOBILE, SCORE};

    @Test
    public void shouldReturnListOfParsedEntries() throws IOException {
        //given
        when(mockedCsvReader.readNext()).thenReturn(CSV_HEADER).thenReturn(MOCKED_CSV_LINE).thenReturn(null);

        //when
        List<Entry> result = csvFileReader.readFile();

        //then
        assertEquals(1, result.size());
        Entry entry = result.get(0);
        assertEquals(ID, entry.getId());
        assertEquals(NAME, entry.getName());
        assertEquals(MOBILE, entry.getIsMobile());
        assertEquals(SCORE, entry.getScore());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionIfUnableToReadNextLine() throws IOException {
        //given
        when(mockedCsvReader.readNext()).thenThrow(IOException.class);

        //when
        csvFileReader.readFile();
    }
}