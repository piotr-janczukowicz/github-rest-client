package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationCustomProperty;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organization custom property definitions to create or update for an enterprise.
 *
 * @param properties The custom property definitions to create or update
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/custom-properties-for-orgs-create-or-update-enterprise-definitions/requestBody")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubCreateOrUpdateEnterpriseDefinitionsPayload(List<GitHubOrganizationCustomProperty> properties) {

    public GitHubCreateOrUpdateEnterpriseDefinitionsPayload {
        properties = List.copyOf(requireSizeAtMost(requireNonEmpty(properties, "properties"), 100, "properties"));
    }
}
