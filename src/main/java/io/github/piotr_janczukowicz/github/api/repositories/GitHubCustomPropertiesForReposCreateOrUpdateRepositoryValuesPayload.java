package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Create or update repository custom property values
 * ({@code repos/custom-properties-for-repos-create-or-update-repository-values}).
 *
 * @param properties Custom property values
 */
@RecordBuilder
@CatalogSchema("operations/repos/custom-properties-for-repos-create-or-update-repository-values/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubCustomPropertiesForReposCreateOrUpdateRepositoryValuesPayload(
        List<GitHubCustomPropertyValue> properties) {

    public GitHubCustomPropertiesForReposCreateOrUpdateRepositoryValuesPayload {
        properties = List.copyOf(requireNonNull(properties, "properties"));
    }
}
