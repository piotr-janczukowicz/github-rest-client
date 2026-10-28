package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Request to create or update custom property values of an organization.
 *
 * @param properties A list of custom property names and associated values to apply to the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/custom-properties-for-orgs-create-or-update-organization-values/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload(List<GitHubCustomPropertyValue> properties) {

    public GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload {
        requireNonNull(properties, "properties");
        properties = List.copyOf(properties);
    }
}
