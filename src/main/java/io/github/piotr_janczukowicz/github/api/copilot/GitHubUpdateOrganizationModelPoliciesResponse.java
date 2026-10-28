package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Model policies of an organization after an update.
 *
 * @param defaultAvailability The organization default availability for eligible models.
 * @param models Model policies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-model-policies/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationModelPoliciesResponse(
        GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailability defaultAvailability,
        List<GitHubUpdateOrganizationModelPoliciesResponseModel> models) {

    public GitHubUpdateOrganizationModelPoliciesResponse {
        requireNonNull(defaultAvailability, "defaultAvailability");
        requireNonNull(models, "models");
    }
}
