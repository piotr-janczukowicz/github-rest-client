package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise ruleset conditions for organization IDs.
 *
 * @param organizationId Organization ID condition
 */
@CatalogSchema("components/schemas/enterprise-ruleset-conditions-organization-id-target")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEnterpriseRulesetConditionsOrganizationIdTarget(
        GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId organizationId) {
    public GitHubEnterpriseRulesetConditionsOrganizationIdTarget {
        requireNonNull(organizationId, "organizationId");
    }
}
