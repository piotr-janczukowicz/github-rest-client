package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Set self-hosted runner permissions for an organization.
 *
 * @param enabledRepositories The policy that controls whether self-hosted runners can be used in the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-self-hosted-runners-permissions-organization/requestBody")
public record GitHubSetSelfHostedRunnersPermissionsOrganizationPayload(
        GitHubSelfHostedRunnersEnabledRepositories enabledRepositories) {

    public GitHubSetSelfHostedRunnersPermissionsOrganizationPayload {
        requireNonNull(enabledRepositories, "enabledRepositories");
    }
}
