package rubiconproject.reader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class InputFileReaderProvider {
    private BeanFactory beanFactory;

    @Autowired
    public InputFileReaderProvider(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public InputFileReader getInputFileReader(File inputFile){
        if (inputFile == null){
            throw new IllegalArgumentException("File is null!");
        }

        String name = inputFile.getName();
        if (name.endsWith(".csv")){
            return beanFactory.getBean("csvFileReader", CSVFileReader.class);
        } else if (name.endsWith(".json")){
            return beanFactory.getBean("jsonFileReader", JsonFileReader.class);
        }

        throw new IllegalArgumentException("Only .csv and .json files are supported!");
    }
}
