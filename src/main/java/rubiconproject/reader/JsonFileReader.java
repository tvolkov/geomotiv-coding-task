package rubiconproject.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import rubiconproject.model.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonFileReader implements InputFileReader {

    /**
     * having this pathToFile here is probably not a good idea, because it makes this object stateful.
     * However I will leave it as is, since doing refactoring might take plenty of time because, among other things,
     * of usage the third party libraries for parsing csv and json
     */
    private final String pathToFile;
    private ObjectMapper objectMapper;

    public JsonFileReader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public List<Entry> readFile() {
        log.info("thread id: " + Thread.currentThread().getId());
        try {
            return objectMapper.readValue(new File(pathToFile), new TypeReference<ArrayList<Entry>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
