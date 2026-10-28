package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Custom property values to set on organizations of an enterprise.
 *
 * @param organizationLogins The organization names to set the values on
 * @param properties The custom property values to set
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/custom-properties-for-orgs-create-or-update-enterprise-values/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateOrUpdateEnterpriseValuesPayload(
        List<String> organizationLogins, List<GitHubCustomPropertyValue> properties) {

    public GitHubCreateOrUpdateEnterpriseValuesPayload {
        organizationLogins = List.copyOf(
                requireSizeAtMost(requireNonEmpty(organizationLogins, "organizationLogins"), 30, "organizationLogins"));
        requireNonNull(properties, "properties");
        properties = List.copyOf(properties);
    }
}
