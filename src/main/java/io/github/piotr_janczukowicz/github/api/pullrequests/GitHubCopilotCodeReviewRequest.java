package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A request for Copilot to review a pull request.
 *
 * @param nodeId The global node ID of the review request.
 * @param state The current state of the review request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/copilot-code-review-request")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCopilotCodeReviewRequest(String nodeId, GitHubCopilotCodeReviewRequestState state) {
    public GitHubCopilotCodeReviewRequest {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(state, "state");
    }
}
