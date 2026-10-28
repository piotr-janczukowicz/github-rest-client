package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Organization identifiers selected by an enterprise ruleset organization ID condition.
 *
 * @param organizationIds The organization IDs that the ruleset applies to. One of these IDs must match for the
 *     condition to pass.
 */
@CatalogSchema("components/schemas/enterprise-ruleset-conditions-organization-id-target/properties/organization_id")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId(
        @Nullable List<Long> organizationIds) {

    public GitHubEnterpriseRulesetConditionsOrganizationIdTargetOrganizationId {
        if (organizationIds != null) {
            organizationIds = List.copyOf(organizationIds);
        }
    }
}
