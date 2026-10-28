package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Review of custom deployment protection rules. Either {@code state} or {@code comment} is required.
 *
 * @param environmentName The name of the environment to approve or reject
 * @param state Whether to approve or reject deployment to the specified environments
 * @param comment Comment associated with the pending deployment protection rule, required when state is rejected
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/review-custom-gates-for-run/requestBody")
public record GitHubReviewCustomGatesForRunPayload(
        String environmentName,
        @Nullable GitHubReviewCustomGatesState state,
        @Nullable String comment) {

    public GitHubReviewCustomGatesForRunPayload {
        requireNonNull(environmentName, "environmentName");
        if (state == null && comment == null) {
            throw new IllegalArgumentException("state or comment is required");
        }
    }
}
