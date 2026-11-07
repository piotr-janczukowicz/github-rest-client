package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Resource.
 *
 * @param type The type of resource.
 * @param identifier The identifier for the resource.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-cost-center-membership/properties/resource")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingCostCenterMembershipResource(
        GitHubBillingCostCenterMembershipResourceType type,
        GitHubBillingCostCenterMembershipResourceIdentifier identifier) {
    public GitHubBillingCostCenterMembershipResource {
        requireNonNull(type, "type");
        requireNonNull(identifier, "identifier");
    }
}
