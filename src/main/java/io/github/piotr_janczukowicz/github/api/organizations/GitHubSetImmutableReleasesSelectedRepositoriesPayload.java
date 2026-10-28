package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Set selected repositories for immutable releases enforcement.
 *
 * @param selectedRepositoryIds Selected repository ids
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-immutable-releases-settings-repositories/requestBody")
public record GitHubSetImmutableReleasesSelectedRepositoriesPayload(List<Long> selectedRepositoryIds) {

    public GitHubSetImmutableReleasesSelectedRepositoriesPayload {
        selectedRepositoryIds = List.copyOf(requireNonNull(selectedRepositoryIds, "selectedRepositoryIds"));
    }
}
