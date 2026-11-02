package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Default availability.
 *
 * @param policyState The enterprise default policy applied to newly released Copilot features and clients.
 *     {@code delegate_to_organizations} means each organization sets its own default.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-enterprise-feature-policies/200/properties/default_availability")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetEnterpriseFeaturePoliciesResponseDefaultAvailability(
        GitHubGetEnterpriseFeaturePoliciesResponseDefaultAvailabilityPolicyState policyState) {
    public GitHubGetEnterpriseFeaturePoliciesResponseDefaultAvailability {
        requireNonNull(policyState, "policyState");
    }
}
