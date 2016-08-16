package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Represents an entry from csv or json file.
 * The fields are of a string type to avoid unnecessary conversions, because no operations are to be done on the Entries
 */
@RequiredArgsConstructor
@ToString
public class Entry {
    @Getter
    @JsonProperty("site_id")
    private final String Id;

    @Getter
    @JsonProperty("name")
    private final String name;

    @Getter
    @JsonProperty("mobile")
    private final String isMobile;

    @Getter
    @JsonProperty("score")
    private final String score;

    @Getter
    @Setter
    private String keywords = "";
}
