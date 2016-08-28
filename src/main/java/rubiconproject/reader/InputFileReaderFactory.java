package rubiconproject.reader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * I have intentionally chosen this way to retrieve different implementations of InputFileReader.
 * Of course this could be done without depending on BeanFactory object, just by using lookup method.
 * But in this case I'd have to write a logic to resolve bean name.
 * So I think this is a good trade-off between simplicity and efficiency
 */
public class InputFileReaderFactory {

    private final BeanFactory beanFactory;

    @Autowired
    public InputFileReaderFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    InputFileReader getReader(String readerName, String pathToFile){
        return (InputFileReader) beanFactory.getBean(readerName, pathToFile);
    }
}
