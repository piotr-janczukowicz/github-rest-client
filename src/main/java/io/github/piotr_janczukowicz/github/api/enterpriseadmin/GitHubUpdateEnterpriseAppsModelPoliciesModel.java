package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Model policy to update.
 *
 * @param modelName Name of the model
 * @param policyState Policy state of the model
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-apps-model-policies/requestBody/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseAppsModelPoliciesModel(
        String modelName, GitHubUpdateEnterpriseAppsModelPoliciesPolicyState policyState) {

    public GitHubUpdateEnterpriseAppsModelPoliciesModel {
        requireNonNull(modelName, "modelName");
        modelName = requireMinLength(modelName, 1, "modelName");
        requireNonNull(policyState, "policyState");
    }
}
