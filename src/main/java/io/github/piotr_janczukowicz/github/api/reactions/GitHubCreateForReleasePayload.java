package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create a reaction to a release.
 *
 * @param content The reaction type to add to the release.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/reactions/create-for-release/requestBody")
public record GitHubCreateForReleasePayload(GitHubReleaseReactionContent content) {

    public GitHubCreateForReleasePayload {
        requireNonNull(content, "content");
    }
}
