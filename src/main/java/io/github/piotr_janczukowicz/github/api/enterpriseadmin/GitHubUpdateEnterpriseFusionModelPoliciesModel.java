package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Model.
 *
 * @param modelName model name
 * @param policyState policy state
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/enterprise-admin/update-enterprise-fusion-model-policies/requestBody/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseFusionModelPoliciesModel(
        String modelName, GitHubUpdateEnterpriseFusionModelPoliciesModelPolicyState policyState) {
    public GitHubUpdateEnterpriseFusionModelPoliciesModel {
        requireNonNull(modelName, "modelName");
        requireMinLength(modelName, 1, "modelName");
        requireNonNull(policyState, "policyState");
    }
}
