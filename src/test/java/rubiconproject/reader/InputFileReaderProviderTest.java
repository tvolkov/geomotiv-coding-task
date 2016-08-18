package rubiconproject.reader;

import au.com.bytecode.opencsv.CSVReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * I could have used spring test here, i.e. create a test context, but it would be tricky to create 'mock' fileInputStreams, etc.
 * So I decided to stick to mockito
 */
@RunWith(MockitoJUnitRunner.class)
public class InputFileReaderProviderTest {

    private InputFileReaderProvider inputFileReaderProvider;

    @Mock
    private BeanFactory beanFactory;

    @Before
    public void setUp(){
        inputFileReaderProvider = new InputFileReaderProvider(beanFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfFileIsNull(){
        //when
        inputFileReaderProvider.getInputFileReader(null);
    }

    @Test
    public void shouldReturnCsvFileReaderIfFileHasCsvExtension() throws URISyntaxException {
        //given
        File file = mock(File.class);
        when(file.getAbsolutePath()).thenReturn("file.csv");
        InputStream mockInputStream = mock(InputStream.class);
        when(beanFactory.getBean("inputStream", file.getAbsolutePath())).thenReturn(mockInputStream);
        InputStreamReader mockInputStreamReader = mock(InputStreamReader.class);
        when(beanFactory.getBean("inputStreamReader", mockInputStream)).thenReturn(mockInputStreamReader);
        CSVReader mockCsvReader = mock(CSVReader.class);
        when(beanFactory.getBean("csvReader", mockInputStreamReader)).thenReturn(mockCsvReader);
        CSVFileReader mockCsvFileReader = mock(CSVFileReader.class);
        when(beanFactory.getBean("csvFileReader", mockCsvReader)).thenReturn(mockCsvFileReader);

        //when
        InputFileReader inputFileReader = inputFileReaderProvider.getInputFileReader(file);

        //then
        assertNotNull(inputFileReader);
        assertTrue(inputFileReader instanceof CSVFileReader);
    }

    @Test
    public void shouldReturnJsonFileReadaerIfFileHasJsonExtension(){
        //given
        File mockFile = mock(File.class);
        when(mockFile.getAbsolutePath()).thenReturn("input2.json");
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        when(beanFactory.getBean("objectMapper")).thenReturn(mockObjectMapper);
        InputFileReader mockInputFileReader = mock(JsonFileReader.class);
        when(beanFactory.getBean("jsonFileReader", mockFile.getAbsolutePath(), mockObjectMapper)).thenReturn(mockInputFileReader);

        //when
        InputFileReader inputFileReader = inputFileReaderProvider.getInputFileReader(mockFile);

        //then
        assertNotNull(inputFileReader);
        assertTrue(inputFileReader instanceof JsonFileReader);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfFileHasOtherExtension(){
        //given
        File mockFile = mock(File.class);
        when(mockFile.getAbsolutePath()).thenReturn("name.txt");

        //when
        inputFileReaderProvider.getInputFileReader(mockFile);
    }
}