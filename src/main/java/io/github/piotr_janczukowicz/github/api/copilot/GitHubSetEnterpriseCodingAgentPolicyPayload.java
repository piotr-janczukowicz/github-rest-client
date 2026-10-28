package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Coding agent policy to set for an enterprise.
 *
 * @param policyState The policy state for Copilot cloud agent in the enterprise.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/set-enterprise-coding-agent-policy/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetEnterpriseCodingAgentPolicyPayload(GitHubCodingAgentPolicyState policyState) {

    public GitHubSetEnterpriseCodingAgentPolicyPayload {
        requireNonNull(policyState, "policyState");
    }
}
