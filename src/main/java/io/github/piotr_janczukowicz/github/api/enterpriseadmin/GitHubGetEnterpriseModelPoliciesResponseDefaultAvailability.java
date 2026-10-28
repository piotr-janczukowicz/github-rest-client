package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Default availability of the models.
 *
 * @param policyState Policy state of the default availability
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-enterprise-model-policies/200/properties/default_availability")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetEnterpriseModelPoliciesResponseDefaultAvailability(
        GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState policyState) {

    public GitHubGetEnterpriseModelPoliciesResponseDefaultAvailability {
        requireNonNull(policyState, "policyState");
    }
}
