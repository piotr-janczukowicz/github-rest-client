package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Billing cost center membership.
 *
 * @param resource resource
 * @param memberships The list of cost center memberships for the resource.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-cost-center-membership")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingCostCenterMembership(
        GitHubBillingCostCenterMembershipResource resource,
        List<GitHubBillingCostCenterMembershipMembership> memberships) {
    public GitHubBillingCostCenterMembership {
        requireNonNull(resource, "resource");
        requireNonNull(memberships, "memberships");
    }
}
