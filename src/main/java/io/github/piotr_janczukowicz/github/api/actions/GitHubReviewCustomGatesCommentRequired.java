package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Review of a custom deployment protection rule that requires a comment.
 *
 * @param environmentName The name of the environment to approve or reject
 * @param comment Comment associated with the pending deployment protection rule, required when state is rejected
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/review-custom-gates-comment-required")
public record GitHubReviewCustomGatesCommentRequired(String environmentName, String comment) {

    public GitHubReviewCustomGatesCommentRequired {
        requireNonNull(environmentName, "environmentName");
        requireNonNull(comment, "comment");
    }
}
