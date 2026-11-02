package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;

/**
 * The Agent Merge configuration for an enrolled pull request.
 *
 * @param allowedActions The set of actions which Copilot may perform on the pull request.
 * @param createdAt Timestamp when the pull request was enrolled for Agent Merge.
 * @param updatedAt Timestamp when the Agent Merge enrollment was last updated.
 * @param user The user who most recently enabled Agent Merge for the pull request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/agent-merge/put-agent-merge-enrollment/200",
    "operations/agent-merge/put-agent-merge-enrollment/201"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPutAgentMergeEnrollmentResponse(
        List<GitHubPutAgentMergeEnrollmentResponseAllowedAction> allowedActions,
        Instant createdAt,
        Instant updatedAt,
        GitHubPutAgentMergeEnrollmentResponseUser user) {
    public GitHubPutAgentMergeEnrollmentResponse {
        requireNonEmpty(allowedActions, "allowedActions");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(user, "user");
    }
}
