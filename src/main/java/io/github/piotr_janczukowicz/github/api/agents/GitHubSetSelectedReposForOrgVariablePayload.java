package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Set selected repositories for an organization variable.
 *
 * @param selectedRepositoryIds The IDs of the repositories that can access the organization variable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agents/set-selected-repos-for-org-variable/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetSelectedReposForOrgVariablePayload(List<Long> selectedRepositoryIds) {

    public GitHubSetSelectedReposForOrgVariablePayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
