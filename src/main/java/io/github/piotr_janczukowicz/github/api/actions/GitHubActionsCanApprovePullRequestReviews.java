package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Whether GitHub Actions can approve pull requests.
 *
 * @param value JSON value
 */
@CatalogSchema("components/schemas/actions-can-approve-pull-request-reviews")
public record GitHubActionsCanApprovePullRequestReviews(
        @JsonValue boolean value) {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public GitHubActionsCanApprovePullRequestReviews {
        // Empty on purpose: the compact constructor only carries @JsonCreator, so Jackson reads the bare value.
    }
}
