package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Review of a custom deployment protection rule with a required state.
 *
 * @param environmentName The name of the environment to approve or reject
 * @param state Whether to approve or reject deployment to the specified environments
 * @param comment Optional comment to include with the review
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/review-custom-gates-state-required")
public record GitHubReviewCustomGatesStateRequired(
        String environmentName,
        GitHubReviewCustomGatesState state,
        @Nullable String comment) {

    public GitHubReviewCustomGatesStateRequired {
        requireNonNull(environmentName, "environmentName");
        requireNonNull(state, "state");
    }
}
