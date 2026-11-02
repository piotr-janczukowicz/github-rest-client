package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * The Agent Merge enrollment configuration.
 *
 * @param allowedActions The set of actions that Copilot may perform on the pull request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-merge/put-agent-merge-enrollment/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPutAgentMergeEnrollmentPayload(List<GitHubPutAgentMergeEnrollmentAllowedAction> allowedActions) {
    public GitHubPutAgentMergeEnrollmentPayload {
        allowedActions =
                List.copyOf(requireUniqueItems(requireNonEmpty(allowedActions, "allowedActions"), "allowedActions"));
    }
}
