package rubiconproject.processor;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.endsWithAny;

public class InputFileValidator {

    private String[] allowedFileExtensions;

    public boolean isFileValid(File inputFile) {
        if (!inputFile.isFile()){
            return false;
        }

        String fileName = inputFile.getName();
        //todo can be just: get file extension and check if array contains it
        return endsWithAny(fileName, allowedFileExtensions) || endsWithAny(fileName, allowedFileExtensions);

    }

    @Value("${allowed.file.extensions}")
    public void setAllowedFileExtensions(String[] allowedFileExtensions) {
        this.allowedFileExtensions = allowedFileExtensions;
    }
}
