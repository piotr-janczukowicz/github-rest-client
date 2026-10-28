package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create a self-hosted runner group for an organization.
 *
 * @param name Name of the runner group
 * @param visibility Visibility of a runner group
 * @param selectedRepositoryIds List of repository IDs that can access the runner group
 * @param runners List of runner IDs to add to the runner group
 * @param allowsPublicRepositories Whether the runner group can be used by public repositories
 * @param restrictedToWorkflows If true, the runner group will be restricted to running only the specified workflows
 * @param selectedWorkflows List of workflows the runner group should be allowed to run
 * @param networkConfigurationId The identifier of a hosted compute network configuration
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/create-self-hosted-runner-group-for-org/requestBody")
public record GitHubCreateSelfHostedRunnerGroupForOrgPayload(
        String name,
        @Nullable GitHubRunnerGroupVisibility visibility,
        @Nullable List<Long> selectedRepositoryIds,
        @Nullable List<Long> runners,
        @Nullable Boolean allowsPublicRepositories,
        @Nullable Boolean restrictedToWorkflows,
        @Nullable List<String> selectedWorkflows,
        @Nullable String networkConfigurationId) {

    public GitHubCreateSelfHostedRunnerGroupForOrgPayload {
        requireNonNull(name, "name");
        if (selectedRepositoryIds != null) {
            selectedRepositoryIds = List.copyOf(selectedRepositoryIds);
        }
        if (runners != null) {
            runners = List.copyOf(runners);
        }
        if (selectedWorkflows != null) {
            selectedWorkflows = List.copyOf(selectedWorkflows);
        }
    }
}
