package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Membership.
 *
 * @param costCenter cost center
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-cost-center-membership/properties/memberships/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingCostCenterMembershipMembership(
        @Nullable GitHubBillingCostCenterMembershipMembershipCostCenter costCenter) {}
