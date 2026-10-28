package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Custom property values of an organization in an enterprise.
 *
 * @param organizationId The unique identifier of the organization
 * @param organizationLogin The organization name
 * @param properties The custom property values of the organization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/custom-properties-for-orgs-get-enterprise-property-values")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubEnterpriseOrgPropertyValues(
        long organizationId, String organizationLogin, List<GitHubCustomPropertyValue> properties) {

    public GitHubEnterpriseOrgPropertyValues {
        requireNonNull(organizationLogin, "organizationLogin");
        requireNonNull(properties, "properties");
    }
}
