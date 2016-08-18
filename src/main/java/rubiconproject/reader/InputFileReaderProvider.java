package rubiconproject.reader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class InputFileReaderProvider {
    private final BeanFactory beanFactory;

    @Autowired
    public InputFileReaderProvider(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public InputFileReader getInputFileReader(File inputFile){
        if (inputFile == null){
            throw new IllegalArgumentException("File is null!");
        }

        String path = inputFile.getAbsolutePath();
        if (path.endsWith(".csv")){
            return createCSVFileReader(path);
        } else if (path.endsWith(".json")){
            return createJsonFileReader(path);
        }

        throw new IllegalArgumentException("Only .csv and .json files are supported!");
    }
    private InputFileReader createCSVFileReader(String filename){
        return (InputFileReader) beanFactory.getBean("csvFileReader", beanFactory.getBean("csvReader", createReader(filename)));
    }

    private Reader createReader(String filename){
        //ideally this Reader should be a bean, for now I wil just create it directly because it's easy.
        // Although it's not that easy to test it, since it has to be tested with real file, not mocked
        try {
            return new InputStreamReader(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + filename + " does not exist");
        }
    }

    private InputFileReader createJsonFileReader(String filename){
        return (InputFileReader) beanFactory.getBean("jsonFileReader", filename, beanFactory.getBean("objectMapper"));
    }
}
