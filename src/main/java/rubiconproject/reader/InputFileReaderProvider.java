package rubiconproject.reader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;

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

        String filename = inputFile.getName();
        if (filename.endsWith(".csv")){
            return createCSVFileReader(filename);
        } else if (filename.endsWith(".json")){
            return createJsonFileReader(filename);
        }

        throw new IllegalArgumentException("Only .csv and .json files are supported!");
    }
    private InputFileReader createCSVFileReader(String filename){
        return (InputFileReader) beanFactory.getBean("csvFileReader", beanFactory.getBean("csvReader", createReader(filename)));
    }

    private Reader createReader(String filename){
        // the inputStreamReader could be define as a bean, but it's just easier to invoke it's constructor here
        return new InputStreamReader(getClass().getResourceAsStream("/" + filename));
    }

    private InputFileReader createJsonFileReader(String filename){
        return (InputFileReader) beanFactory.getBean("jsonFileReader", filename, beanFactory.getBean("objectMapper"));
    }
}
