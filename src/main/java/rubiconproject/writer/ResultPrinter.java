package rubiconproject.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import rubiconproject.model.Collection;

import java.io.IOException;
import java.util.List;

public class ResultPrinter {

    private final ObjectMapper objectMapper;

    public ResultPrinter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private boolean prettyPrintResult;

    public String printResult(List<Collection> result){
        StringBuilder stringBuilder = new StringBuilder();
        for (Collection collection : result){
            try {
                stringBuilder.append(getWriter().writeValueAsString(collection));
            } catch (IOException e) {
                throw new RuntimeException("Error occurred during printing result: " + e.getMessage());
            }
        }
        return stringBuilder.toString();
    }

    private ObjectWriter getWriter() {
        if (prettyPrintResult){
            return objectMapper.writerWithDefaultPrettyPrinter();
        }
        return objectMapper.writer();
    }

    @Value("${pretty.print.results}")
    public void setPrettyPrintResult(boolean prettyPrintResult) {
        this.prettyPrintResult = prettyPrintResult;
    }
}
