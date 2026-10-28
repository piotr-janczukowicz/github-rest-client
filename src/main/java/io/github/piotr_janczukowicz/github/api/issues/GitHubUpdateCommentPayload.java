package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update issue comment request body.
 *
 * @param body The contents of the comment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/update-comment/requestBody")
@RecordBuilder
public record GitHubUpdateCommentPayload(String body) {

    public GitHubUpdateCommentPayload {
        requireNonNull(body, "body");
    }
}
