package rubiconproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Represents an entry from csv or json file.
 * The fields are of a string type to avoid unnecessary conversions, because no operations are to be done on the Entries
 */
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Entry {
    @Getter
    @JsonProperty("site_id")
    @NonNull
    private String Id;

    @Getter
    @JsonProperty("name")
    @NonNull
    private String name;

    @Getter
    @JsonProperty("mobile")
    @NonNull
    private String isMobile;

    @Getter
    @JsonProperty("score")
    @NonNull
    private String score;

    @Getter
    @Setter
    private String keywords = "";
}
