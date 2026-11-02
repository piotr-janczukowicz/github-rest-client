package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Get organization feature policies response.
 *
 * @param defaultAvailability The default policy applied to newly released Copilot features and clients.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-organization-feature-policies/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetOrganizationFeaturePoliciesResponse(
        GitHubGetOrganizationFeaturePoliciesResponseDefaultAvailability defaultAvailability) {
    public GitHubGetOrganizationFeaturePoliciesResponse {
        requireNonNull(defaultAvailability, "defaultAvailability");
    }
}
