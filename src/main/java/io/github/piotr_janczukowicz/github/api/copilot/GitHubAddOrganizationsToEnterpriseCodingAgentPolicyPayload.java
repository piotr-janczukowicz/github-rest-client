package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organizations to add to the enterprise coding agent policy.
 *
 * @param organizations List of organization logins within the enterprise to enable Copilot cloud agent for.
 * @param customProperties List of custom property filters to match organizations. Organizations matching any of the
 *     specified property name/value pairs will be included.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/copilot/add-organizations-to-enterprise-coding-agent-policy/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddOrganizationsToEnterpriseCodingAgentPolicyPayload(
        @Nullable List<String> organizations, @Nullable List<GitHubCodingAgentCustomProperty> customProperties) {

    public GitHubAddOrganizationsToEnterpriseCodingAgentPolicyPayload {
        if (organizations != null) {
            organizations = List.copyOf(organizations);
        }
        if (customProperties != null) {
            customProperties = List.copyOf(customProperties);
        }
    }
}
