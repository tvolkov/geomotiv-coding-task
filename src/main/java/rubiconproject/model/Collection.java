package rubiconproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class Collection {
    @Getter
    private String collectionId;
    @Getter
    private List<Entry> entries;
}
