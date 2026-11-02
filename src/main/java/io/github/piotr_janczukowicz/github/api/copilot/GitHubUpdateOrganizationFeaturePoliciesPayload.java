package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Update organization feature policies payload.
 *
 * @param defaultAvailability default availability
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-feature-policies/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationFeaturePoliciesPayload(
        GitHubUpdateOrganizationFeaturePoliciesDefaultAvailability defaultAvailability) {
    public GitHubUpdateOrganizationFeaturePoliciesPayload {
        requireNonNull(defaultAvailability, "defaultAvailability");
    }
}
