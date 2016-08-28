package rubiconproject.processor;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

public class InputFileValidator {

    private List<String> allowedFileExtensions;

    public boolean isFileValid(File inputFile) {
        if (!inputFile.isFile()){
            return false;
        }

        String fileName = inputFile.getName();
        return allowedFileExtensions.contains(FilenameUtils.getExtension(fileName));

    }

    @Value("#{'${allowed.file.extensions}'.split(',')}")
    public void setAllowedFileExtensions(List<String> allowedFileExtensions) {
        this.allowedFileExtensions = allowedFileExtensions;
    }
}
