package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The default policy applied to newly released Copilot features and clients.
 *
 * @param policyState The effective default availability. The enterprise default is used when the enterprise has set
 *     one. Otherwise, the organization's own default is used.
 * @param editable Whether the organization can update its default availability.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-feature-policies/200/properties/default_availability")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailability(
        GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailabilityPolicyState policyState, boolean editable) {
    public GitHubUpdateOrganizationFeaturePoliciesResponseDefaultAvailability {
        requireNonNull(policyState, "policyState");
    }
}
