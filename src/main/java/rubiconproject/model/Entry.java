package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an entry from csv or json file.
 * The fields are of a string type to avoid unnecessary conversions, because no operations are to be done on the Entries
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Entry {
    @Getter
    @JsonProperty("site_id")
    private String Id;

    @Getter
    @JsonProperty("name")
    private String name;

    @Getter
    @JsonProperty("mobile")
    private String isMobile;

    @Getter
    @JsonProperty("score")
    private String score;
}
