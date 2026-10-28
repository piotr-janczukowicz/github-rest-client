package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Policy state of one model for an organization.
 *
 * @param modelName The public identifier of a model.
 * @param policyState The explicit policy state to set for the model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-model-policies/requestBody/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationModelPoliciesModel(
        String modelName, GitHubUpdateOrganizationModelPoliciesModelPolicyState policyState) {

    public GitHubUpdateOrganizationModelPoliciesModel {
        modelName = requireMinLength(requireNonNull(modelName, "modelName"), 1, "modelName");
        requireNonNull(policyState, "policyState");
    }
}
