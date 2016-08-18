package rubiconproject.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rubiconproject.model.Collection;
import rubiconproject.model.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JsonFileReader implements InputFileReader {

    private final String pathToFile;
    private final ObjectMapper objectMapper;

    JsonFileReader(String pathToFile, ObjectMapper objectMapper) {
        this.pathToFile = pathToFile;
        this.objectMapper = objectMapper;
    }

    @Override
    public Collection readFile(String collectionId) {
        try {
//            return new Collection(collectionId, objectMapper.readValue(new File(pathToFile), new TypeReference<ArrayList<Entry>>() {}));
            List<Entry> someClassList = objectMapper.readValue(new File(pathToFile), objectMapper.getTypeFactory().constructCollectionType(List.class, Entry.class));
//            return new Collection(collectionId, objectMapper.readValue(new File(pathToFile), new TypeReference<ArrayList<Entry>>() {}));
            return new Collection(collectionId, someClassList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
