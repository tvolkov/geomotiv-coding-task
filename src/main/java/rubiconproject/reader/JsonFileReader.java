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
