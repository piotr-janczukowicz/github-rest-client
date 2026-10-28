package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Directory listing content result.
 *
 * @param entries directory entries
 */
@CatalogSchema("components/schemas/content-directory")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubContentDirectory(@JsonValue List<GitHubContentDirectoryEntry> entries)
        implements GitHubContentResult {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubContentDirectory {
        requireNonNull(entries, "entries");
    }
}
