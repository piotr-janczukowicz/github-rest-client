package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create a self-hosted runner group for an enterprise.
 *
 * @param name Name of the runner group
 * @param visibility Visibility of a runner group
 * @param selectedOrganizationIds List of organization IDs that can access the runner group
 * @param runners List of runner IDs to add to the runner group
 * @param allowsPublicRepositories Whether the runner group can be used by public repositories
 * @param restrictedToWorkflows If true, the runner group will be restricted to running only the specified workflows
 * @param selectedWorkflows List of workflows the runner group should be allowed to run
 * @param networkConfigurationId The identifier of a hosted compute network configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-self-hosted-runner-group-for-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateSelfHostedRunnerGroupForEnterprisePayload(
        String name,
        @Nullable GitHubRunnerGroupVisibilityForEnterprise visibility,
        @Nullable List<Long> selectedOrganizationIds,
        @Nullable List<Long> runners,
        @Nullable Boolean allowsPublicRepositories,
        @Nullable Boolean restrictedToWorkflows,
        @Nullable List<String> selectedWorkflows,
        @Nullable String networkConfigurationId) {

    public GitHubCreateSelfHostedRunnerGroupForEnterprisePayload {
        requireNonNull(name, "name");
        if (selectedOrganizationIds != null) {
            selectedOrganizationIds = List.copyOf(selectedOrganizationIds);
        }
        if (runners != null) {
            runners = List.copyOf(runners);
        }
        if (selectedWorkflows != null) {
            selectedWorkflows = List.copyOf(selectedWorkflows);
        }
    }
}
