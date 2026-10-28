package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Detach configurations from repositories.
 *
 * @param selectedRepositoryIds An array of repository IDs to detach from configurations. Up to 250 IDs can be provided.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/code-security/detach-configuration/requestBody")
@RecordBuilder
public record GitHubDetachConfigurationPayload(List<Long> selectedRepositoryIds) {

    public GitHubDetachConfigurationPayload {
        selectedRepositoryIds = List.copyOf(requireSizeBetween(selectedRepositoryIds, 1, 250, "selectedRepositoryIds"));
    }
}
