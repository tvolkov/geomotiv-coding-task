package rubiconproject.reader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testBeans.groovy")
public class InputFileReaderProviderTest {
    private InputFileReaderProvider inputFileReaderProvider;

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
    public void shouldReturnCsvFileReaderIfFileHasCsvExtension() throws URISyntaxException {
        //given
        URL url = this.getClass().getResource("/input1.csv");
        File file = new File(url.toURI());

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