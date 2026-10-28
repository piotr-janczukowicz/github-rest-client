package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Delete file payload.
 *
 * @param message The commit message
 * @param sha The blob SHA of the file being deleted
 * @param branch The branch name
 * @param committer Committer information
 * @param author Author information
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/delete-file/requestBody")
public record GitHubDeleteFilePayload(
        String message,
        String sha,
        @Nullable String branch,
        @Nullable GitHubDeleteFileAuthor committer,
        @Nullable GitHubDeleteFileAuthor author) {

    public GitHubDeleteFilePayload {
        requireNonNull(message, "message");
        requireNonNull(sha, "sha");
    }
}
