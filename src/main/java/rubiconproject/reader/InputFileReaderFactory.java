package rubiconproject.reader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputFileReaderFactory {

    private BeanFactory beanFactory;

    @Autowired
    public InputFileReaderFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    InputFileReader getReader(String readerName, String pathToFile){
        return (InputFileReader) beanFactory.getBean(readerName, pathToFile);
    }
}
