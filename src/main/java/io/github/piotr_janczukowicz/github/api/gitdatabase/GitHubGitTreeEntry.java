package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Entry of a Git tree.
 *
 * @param path Path
 * @param mode Mode
 * @param type Type
 * @param sha Sha
 * @param size Size
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/git-tree/properties/tree/items")
public record GitHubGitTreeEntry(
        String path,
        String mode,
        String type,
        String sha,
        @Nullable Integer size,
        @Nullable String url) {

    public GitHubGitTreeEntry {
        requireNonNull(path, "path");
        requireNonNull(mode, "mode");
        requireNonNull(type, "type");
        requireNonNull(sha, "sha");
    }
}
