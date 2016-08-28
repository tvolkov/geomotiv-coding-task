package rubiconproject.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileListProviderTest {

    @InjectMocks
    private FileListProvider fileListProvider;

    @Mock
    private File mockedInputDirectory;

    @Mock
    InputFileValidator inputFileValidator;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfDirectoryDoesNotExist(){
        //given
        when(mockedInputDirectory.exists()).thenReturn(false);

        //when
        fileListProvider.getInputFilesList(mockedInputDirectory);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInputFileIsNotDirectory(){
        //given
        when(mockedInputDirectory.exists()).thenReturn(true);
        when(mockedInputDirectory.isDirectory()).thenReturn(false);

        //when
        fileListProvider.getInputFilesList(mockedInputDirectory);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfDirectoryHasNoFiles(){
        //given
        when(mockedInputDirectory.exists()).thenReturn(true);
        when(mockedInputDirectory.isDirectory()).thenReturn(true);
        when(mockedInputDirectory.listFiles()).thenReturn(null);

        //when
        fileListProvider.getInputFilesList(mockedInputDirectory);
    }

    @Test
    public void shouldReturnListOfFilesInDirectory(){
        //given
        when(mockedInputDirectory.exists()).thenReturn(true);
        when(mockedInputDirectory.isDirectory()).thenReturn(true);

        File mockedFile1 = mock(File.class);
        when(mockedFile1.isFile()).thenReturn(true);
        when(mockedFile1.getPath()).thenReturn("path1");
        File mockedFile2 = mock(File.class);
        when(mockedFile2.isFile()).thenReturn(true);
        when(mockedFile2.getPath()).thenReturn("path2");
        File[] mockedFiles = {mockedFile1, mockedFile2};
        when(mockedInputDirectory.listFiles()).thenReturn(mockedFiles);
        when(inputFileValidator.isFileValid(mockedFile1)).thenReturn(true);
        when(inputFileValidator.isFileValid(mockedFile2)).thenReturn(true);

        //when
        List<String> result = fileListProvider.getInputFilesList(mockedInputDirectory);

        //then
        assertEquals(mockedFiles.length, result.size());
        assertEquals(Arrays.asList("path1", "path2"), result);
    }

    @Test
    public void shouldNotIncludeFileInTheResultListIfItIsNotValid(){
        //given
        when(mockedInputDirectory.exists()).thenReturn(true);
        when(mockedInputDirectory.isDirectory()).thenReturn(true);

        File mockedFile1 = mock(File.class);
        when(mockedFile1.isFile()).thenReturn(true);
        when(mockedFile1.getPath()).thenReturn("path1");
        File mockedFile2 = mock(File.class);
        when(mockedFile2.isFile()).thenReturn(true);
        File[] mockedFiles = {mockedFile1, mockedFile2};
        when(mockedInputDirectory.listFiles()).thenReturn(mockedFiles);
        when(inputFileValidator.isFileValid(mockedFile1)).thenReturn(true);
        when(inputFileValidator.isFileValid(mockedFile2)).thenReturn(false);

        //when
        List<String> result = fileListProvider.getInputFilesList(mockedInputDirectory);

        //then
        assertEquals(1, result.size());
        assertEquals("path1", result.get(0));
    }
}