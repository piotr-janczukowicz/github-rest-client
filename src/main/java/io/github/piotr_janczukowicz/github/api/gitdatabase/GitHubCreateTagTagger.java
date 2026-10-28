package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An object with information about the individual creating the tag.
 *
 * @param name The name of the author of the tag
 * @param email The email of the author of the tag
 * @param date When this object was tagged. This is a timestamp in ISO 8601 format: {@code YYYY-MM-DDTHH:MM:SSZ}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-tag/requestBody/properties/tagger")
public record GitHubCreateTagTagger(
        String name, String email, @Nullable Instant date) {

    public GitHubCreateTagTagger {
        requireNonNull(name, "name");
        requireNonNull(email, "email");
    }
}
