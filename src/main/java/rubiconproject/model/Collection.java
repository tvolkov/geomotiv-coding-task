package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Collection of entries
 */
@AllArgsConstructor
@ToString
public class Collection {
    @Getter
    @JsonProperty("collectionId")
    private String collectionId;
    @Getter
    @JsonProperty("sites")
    private List<Entry> entries;
}
