package rubiconproject.reader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputFileReaderProviderTest {
    private InputFileReaderProvider inputFileReaderProvider;

    @Mock
    private BeanFactory mockedBeanFactory;

    @Before
    public void setUp(){
        inputFileReaderProvider = new InputFileReaderProvider(mockedBeanFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfFileIsNull(){
        //when
        inputFileReaderProvider.getInputFileReader(null);
    }

    @Test
    public void shouldReturnCsvFileReaderIfFileHasCsvExtension(){
        //given
        CSVFileReader mockedCsvFileReader = mock(CSVFileReader.class);
        when(mockedBeanFactory.getBean("csvFileReader", CSVFileReader.class)).thenReturn(mockedCsvFileReader);
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("name.csv");

        //when
        InputFileReader inputFileReader = inputFileReaderProvider.getInputFileReader(mockFile);

        //then
        assertEquals(mockedCsvFileReader, inputFileReader);
    }

    @Test
    public void shouldReturnJsonFileReadaerIfFileHasJsonExtension(){
        //given
        JsonFileReader mockJsonFileReader = mock(JsonFileReader.class);
        when(mockedBeanFactory.getBean("jsonFileReader", JsonFileReader.class)).thenReturn(mockJsonFileReader);
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("name.json");

        //when
        InputFileReader inputFileReader = inputFileReaderProvider.getInputFileReader(mockFile);

        //then
        assertEquals(mockJsonFileReader, inputFileReader);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfFileHasOtherExtension(){
        //given
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("name.txt");

        //when
        inputFileReaderProvider.getInputFileReader(mockFile);
    }
}