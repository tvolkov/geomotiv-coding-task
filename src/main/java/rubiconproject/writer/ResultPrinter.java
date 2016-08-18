package rubiconproject.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import rubiconproject.model.Collection;

import java.io.IOException;
import java.util.List;

public class ResultPrinter {

    private final ObjectMapper objectMapper;

    public ResultPrinter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String printResult(List<Collection> result){
        StringBuilder stringBuilder = new StringBuilder();
        for (Collection collection : result){
            try {
                stringBuilder.append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(collection));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
