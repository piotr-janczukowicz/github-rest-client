package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Default availability of eligible models for an organization.
 *
 * @param policyState The default policy state.
 * @param editable Whether the default availability can be edited.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-organization-model-policies/200/properties/default_availability")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetOrganizationModelPoliciesResponseDefaultAvailability(
        GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState policyState, boolean editable) {

    public GitHubGetOrganizationModelPoliciesResponseDefaultAvailability {
        requireNonNull(policyState, "policyState");
    }
}
