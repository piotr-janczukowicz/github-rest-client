package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Set selected repositories for an organization Dependabot secret.
 *
 * @param selectedRepositoryIds An array of repository ids that can access the organization secret. You can only provide
 *     a list of repository ids when the {@code visibility} is set to {@code selected}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/set-selected-repos-for-org-secret/requestBody")
public record GitHubSetSelectedReposForOrgSecretPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetSelectedReposForOrgSecretPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
