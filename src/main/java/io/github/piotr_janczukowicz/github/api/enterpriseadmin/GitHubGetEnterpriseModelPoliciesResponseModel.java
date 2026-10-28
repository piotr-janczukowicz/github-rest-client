package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Model policy of the enterprise.
 *
 * @param modelName Name of the model
 * @param displayName Display name of the model
 * @param policyState Policy state of the model
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-enterprise-model-policies/200/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetEnterpriseModelPoliciesResponseModel(
        String modelName, String displayName, GitHubGetEnterpriseModelPoliciesResponsePolicyState policyState) {

    public GitHubGetEnterpriseModelPoliciesResponseModel {
        requireNonNull(modelName, "modelName");
        requireNonNull(displayName, "displayName");
        requireNonNull(policyState, "policyState");
    }
}
