package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create or update file contents payload.
 *
 * @param message The commit message
 * @param content The new file content, using Base64 encoding
 * @param sha The blob SHA of the file being replaced
 * @param branch The branch name
 * @param committer The person that committed the file
 * @param author The author of the file
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-or-update-file-contents/requestBody")
public record GitHubCreateOrUpdateFileContentsPayload(
        String message,
        String content,
        @Nullable String sha,
        @Nullable String branch,
        @Nullable GitHubFileContentsAuthor committer,
        @Nullable GitHubFileContentsAuthor author) {
    public GitHubCreateOrUpdateFileContentsPayload {
        requireNonNull(message, "message");
        requireNonNull(content, "content");
    }
}
