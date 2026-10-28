package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization name patterns selected by an enterprise ruleset organization name condition, with the patterns to
 * include and exclude.
 *
 * @param include Array of organization names or patterns to include. One of these patterns must match for the condition
 *     to pass. Also accepts {@code ~ALL} to include all organizations and ~EMUS to target all enterprise managed user
 *     accounts.
 * @param exclude Array of organization names or patterns to exclude. The condition will not pass if any of these
 *     patterns match.
 */
@CatalogSchema("components/schemas/enterprise-ruleset-conditions-organization-name-target/properties/organization_name")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName(
        @Nullable List<String> include, @Nullable List<String> exclude) {

    public GitHubEnterpriseRulesetConditionsOrganizationNameTargetOrganizationName {
        if (include != null) {
            include = List.copyOf(include);
        }
        if (exclude != null) {
            exclude = List.copyOf(exclude);
        }
    }
}
