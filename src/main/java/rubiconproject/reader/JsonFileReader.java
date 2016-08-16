package rubiconproject.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader implements InputFileReader {

    private String pathTofile;
    private ObjectMapper objectMapper;

    public JsonFileReader(String pathTofile, ObjectMapper objectMapper) {
        this.pathTofile = pathTofile;
        this.objectMapper = objectMapper;
    }

    @Override
    public Collection readFile(String colletcionId) {
        try {
            return new Collection(colletcionId, objectMapper.readValue(new File(pathTofile), new TypeReference<ArrayList<Entry>>() {}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
