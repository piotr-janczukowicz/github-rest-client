package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Policy of one model for an organization.
 *
 * @param modelName The public identifier of the model.
 * @param displayName The display name of the model.
 * @param policyState The policy state of the model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-model-policies/200/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationModelPoliciesResponseModel(
        String modelName,
        String displayName,
        GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState policyState) {

    public GitHubUpdateOrganizationModelPoliciesResponseModel {
        requireNonNull(modelName, "modelName");
        requireNonNull(displayName, "displayName");
        requireNonNull(policyState, "policyState");
    }
}
