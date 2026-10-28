package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Parameters for targeting an organization property.
 *
 * @param name The name of the organization property to target
 * @param propertyValues The values to match for the organization property
 */
@CatalogSchema("components/schemas/enterprise-ruleset-conditions-organization-property-spec")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEnterpriseRulesetConditionsOrganizationPropertySpec(String name, List<String> propertyValues) {

    public GitHubEnterpriseRulesetConditionsOrganizationPropertySpec {
        requireNonNull(name, "name");
        propertyValues = List.copyOf(requireNonNull(propertyValues, "propertyValues"));
    }
}
