package rubiconproject.reader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testBeans.groovy")
public class InputFileReaderProviderTest {
    private InputFileReaderProvider inputFileReaderProvider;

    private static final String FILE_NAME = "name.csv";

    @Autowired
    private BeanFactory beanFactory;

    @Before
    public void setUp(){
        inputFileReaderProvider = beanFactory.getBean("inputFileReaderProvider", InputFileReaderProvider.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfFileIsNull(){
        //when
        inputFileReaderProvider.getInputFileReader(null);
    }

    @Test
    public void shouldReturnCsvFileReaderIfFileHasCsvExtension(){
        //given
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn(FILE_NAME);

        //when
        InputFileReader inputFileReader = inputFileReaderProvider.getInputFileReader(mockFile);

        //then
        assertNotNull(inputFileReader);
        assertTrue(inputFileReader instanceof CSVFileReader);
    }

    @Test
    public void shouldReturnJsonFileReadaerIfFileHasJsonExtension(){
        //given
        File mockFile = mock(File.class);
        when(mockFile.getName()).thenReturn("name.json");

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
        when(mockFile.getName()).thenReturn("name.txt");

        //when
        inputFileReaderProvider.getInputFileReader(mockFile);
    }
}