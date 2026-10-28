package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;

/** Repository language byte counts keyed by language name. */
@CatalogSchema("components/schemas/language")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubRepositoryLanguages(Map<String, Integer> languages) {

    public GitHubRepositoryLanguages {
        requireNonNull(languages, "languages");
    }

    /**
     * Deserializes a flat language map from JSON.
     *
     * @param languages language byte counts
     * @return languages wrapper
     */
    @JsonCreator
    public static GitHubRepositoryLanguages fromJson(final Map<String, Integer> languages) {
        return new GitHubRepositoryLanguages(languages);
    }

    /**
     * Serializes as a flat language map for JSON.
     *
     * @return language byte counts
     */
    @JsonValue
    public Map<String, Integer> toJson() {
        return languages;
    }
}
