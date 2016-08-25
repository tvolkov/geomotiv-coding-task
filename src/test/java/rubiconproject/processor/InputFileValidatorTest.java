package rubiconproject.processor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputFileValidatorTest {

    private static final String[] ALLOWED_EXTENSIONS = {".csv", ".json"};

    private InputFileValidator inputFileValidator;

    @Before
    public void setup(){
        inputFileValidator = new InputFileValidator();
        inputFileValidator.setAllowedFileExtensions(ALLOWED_EXTENSIONS);
    }

    @Test
    public void shouldReturnFalseIfFileIsDirectory(){
        //given
        File mockedFile = mock(File.class);
        when(mockedFile.isFile()).thenReturn(false);

        //when
        boolean result = inputFileValidator.isFileValid(mockedFile);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldReturnFalseIfFileExtensionIsNotAllowed(){
        //given
        File mockedFile = mock(File.class);
        when(mockedFile.isFile()).thenReturn(true);
        when(mockedFile.getName()).thenReturn("file.xml");

        //when
        boolean result = inputFileValidator.isFileValid(mockedFile);

        //then
        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueIfFileExtensionIsOkAndItIsfile(){
        //given
        File mockedFile = mock(File.class);
        when(mockedFile.isFile()).thenReturn(true);
        when(mockedFile.getName()).thenReturn("file.csv");

        //when
        boolean result = inputFileValidator.isFileValid(mockedFile);

        //then
        assertTrue(result);
    }

}