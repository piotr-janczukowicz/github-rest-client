package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update commit comment payload.
 *
 * @param body The contents of the comment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-commit-comment/requestBody")
public record GitHubUpdateCommitCommentPayload(String body) {

    public GitHubUpdateCommitCommentPayload {
        requireNonNull(body, "body");
    }
}
