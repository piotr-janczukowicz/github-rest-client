package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update a self-hosted runner group of an enterprise.
 *
 * @param name Name of the runner group
 * @param visibility Visibility of a runner group
 * @param allowsPublicRepositories Whether the runner group can be used by public repositories
 * @param restrictedToWorkflows If true, the runner group will be restricted to running only the specified workflows
 * @param selectedWorkflows List of workflows the runner group should be allowed to run
 * @param networkConfigurationId The identifier of a hosted compute network configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-self-hosted-runner-group-for-enterprise/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload(
        @Nullable String name,
        @Nullable GitHubRunnerGroupVisibilityForEnterprise visibility,
        @Nullable Boolean allowsPublicRepositories,
        @Nullable Boolean restrictedToWorkflows,
        @Nullable List<String> selectedWorkflows,
        @Nullable String networkConfigurationId) {

    public GitHubUpdateSelfHostedRunnerGroupForEnterprisePayload {
        if (selectedWorkflows != null) {
            selectedWorkflows = List.copyOf(selectedWorkflows);
        }
    }
}
