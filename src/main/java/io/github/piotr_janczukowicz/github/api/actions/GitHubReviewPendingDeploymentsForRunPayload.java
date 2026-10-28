package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Approve or reject pending deployments that are waiting for review.
 *
 * @param environmentIds The list of environment IDs to approve or reject
 * @param state Whether to approve or reject deployment to the specified environments
 * @param comment A comment to accompany the deployment review
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/review-pending-deployments-for-run/requestBody")
public record GitHubReviewPendingDeploymentsForRunPayload(
        List<Long> environmentIds, GitHubReviewCustomGatesState state, String comment) {

    public GitHubReviewPendingDeploymentsForRunPayload {
        environmentIds = List.copyOf(requireNonNull(environmentIds, "environmentIds"));
        requireNonNull(state, "state");
        requireNonNull(comment, "comment");
    }
}
