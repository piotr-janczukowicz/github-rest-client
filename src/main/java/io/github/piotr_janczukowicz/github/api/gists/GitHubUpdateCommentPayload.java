package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update gist comment request body.
 *
 * @param body The comment text.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/gists/update-comment/requestBody")
@RecordBuilder
public record GitHubUpdateCommentPayload(String body) {

    public GitHubUpdateCommentPayload {
        body = requireMaxLength(requireNonNull(body, "body"), 65_535, "body");
    }
}
