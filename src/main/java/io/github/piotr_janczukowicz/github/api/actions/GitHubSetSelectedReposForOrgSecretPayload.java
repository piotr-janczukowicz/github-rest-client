package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Set selected repositories for an organization Actions secret.
 *
 * @param selectedRepositoryIds The IDs of the repositories that can access the organization secret.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/set-selected-repos-for-org-secret/requestBody")
public record GitHubSetSelectedReposForOrgSecretPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetSelectedReposForOrgSecretPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
