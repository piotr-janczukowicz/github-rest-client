package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Default availability.
 *
 * @param policyState The organization default policy applied to newly released Copilot features and clients.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-feature-policies/requestBody/properties/default_availability")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationFeaturePoliciesDefaultAvailability(
        GitHubUpdateOrganizationFeaturePoliciesDefaultAvailabilityPolicyState policyState) {
    public GitHubUpdateOrganizationFeaturePoliciesDefaultAvailability {
        requireNonNull(policyState, "policyState");
    }
}
