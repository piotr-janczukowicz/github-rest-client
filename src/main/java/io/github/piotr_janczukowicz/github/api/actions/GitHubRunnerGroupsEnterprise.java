package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A self-hosted runner group of an enterprise.
 *
 * @param id The ID of the runner group
 * @param name The name of the runner group
 * @param visibility The visibility of the runner group
 * @param isDefault Whether the runner group is the default group
 * @param selectedOrganizationsUrl Link to the selected organizations resource for this runner group
 * @param runnersUrl Link to the runners of this runner group
 * @param hostedRunnersUrl Link to the hosted runners of this runner group
 * @param networkConfigurationId The identifier of a hosted compute network configuration
 * @param allowsPublicRepositories Whether public repositories are allowed to use the runner group
 * @param workflowRestrictionsReadOnly Whether the workflow restrictions cannot be modified
 * @param restrictedToWorkflows Whether the runner group is restricted to the selected workflows
 * @param selectedWorkflows The workflows the runner group is allowed to run
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/runner-groups-enterprise")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubRunnerGroupsEnterprise(
        double id,
        String name,
        String visibility,
        @JsonProperty("default") boolean isDefault,
        @Nullable String selectedOrganizationsUrl,
        String runnersUrl,
        @Nullable String hostedRunnersUrl,
        @Nullable String networkConfigurationId,
        boolean allowsPublicRepositories,
        @Nullable Boolean workflowRestrictionsReadOnly,
        @Nullable Boolean restrictedToWorkflows,
        @Nullable List<String> selectedWorkflows) {

    public GitHubRunnerGroupsEnterprise {
        requireNonNull(name, "name");
        requireNonNull(visibility, "visibility");
        requireNonNull(runnersUrl, "runnersUrl");
    }
}
