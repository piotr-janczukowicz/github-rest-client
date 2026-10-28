package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repositories allowed to use repository-level self-hosted runners in an organization.
 *
 * @param selectedRepositoryIds IDs of repositories that can use repository-level self-hosted runners
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-selected-repositories-self-hosted-runners-organization/requestBody")
public record GitHubSetSelectedRepositoriesSelfHostedRunnersOrganizationPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetSelectedRepositoriesSelfHostedRunnersOrganizationPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
