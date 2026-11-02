package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update enterprise feature policies response.
 *
 * @param defaultAvailability default availability
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-feature-policies/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseFeaturePoliciesResponse(
        GitHubUpdateEnterpriseFeaturePoliciesResponseDefaultAvailability defaultAvailability) {
    public GitHubUpdateEnterpriseFeaturePoliciesResponse {
        requireNonNull(defaultAvailability, "defaultAvailability");
    }
}
