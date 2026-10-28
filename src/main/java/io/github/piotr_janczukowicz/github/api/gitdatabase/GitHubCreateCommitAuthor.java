package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Information about the author of the commit.
 *
 * @param name The name of the author (or committer) of the commit
 * @param email The email of the author (or committer) of the commit
 * @param date Indicates when this commit was authored (or committed). This is a timestamp in ISO 8601 format:
 *     {@code YYYY-MM-DDTHH:MM:SSZ}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-commit/requestBody/properties/author")
public record GitHubCreateCommitAuthor(
        String name, String email, @Nullable Instant date) {

    public GitHubCreateCommitAuthor {
        requireNonNull(name, "name");
        requireNonNull(email, "email");
    }
}
