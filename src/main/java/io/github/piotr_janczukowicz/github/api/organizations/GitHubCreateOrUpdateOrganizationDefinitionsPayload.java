package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Create or update organization custom property definitions request body.
 *
 * @param properties Custom properties to create or update
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/custom-properties-for-repos-create-or-update-organization-definitions/requestBody")
public record GitHubCreateOrUpdateOrganizationDefinitionsPayload(List<GitHubCustomProperty> properties) {

    private static final String PARAM_PROPERTIES = "properties";

    public GitHubCreateOrUpdateOrganizationDefinitionsPayload {
        properties = List.copyOf(requireSizeBetween(properties, 1, 100, PARAM_PROPERTIES));
    }
}
