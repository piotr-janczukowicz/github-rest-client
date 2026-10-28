package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Set selected repositories for an organization variable.
 *
 * @param selectedRepositoryIds The IDs of the repositories that can access the organization variable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-selected-repos-for-org-variable/requestBody")
public record GitHubSetSelectedReposForOrgVariablePayload(List<Long> selectedRepositoryIds) {

    public GitHubSetSelectedReposForOrgVariablePayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
