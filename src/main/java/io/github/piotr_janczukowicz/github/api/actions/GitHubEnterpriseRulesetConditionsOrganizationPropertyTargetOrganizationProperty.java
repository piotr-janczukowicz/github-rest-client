package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization properties selected by an enterprise ruleset organization property condition, with the properties to
 * include and exclude.
 *
 * @param include The organization properties and values to include. All of these properties must match for the
 *     condition to pass.
 * @param exclude The organization properties and values to exclude. The condition will not pass if any of these
 *     properties match.
 */
@CatalogSchema(
        "components/schemas/enterprise-ruleset-conditions-organization-property-target/properties/organization_property")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty(
        @Nullable List<GitHubEnterpriseRulesetConditionsOrganizationPropertySpec> include,
        @Nullable List<GitHubEnterpriseRulesetConditionsOrganizationPropertySpec> exclude) {

    public GitHubEnterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty {
        if (include != null) {
            include = List.copyOf(include);
        }
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
