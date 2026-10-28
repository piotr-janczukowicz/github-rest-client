package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Model policy of the enterprise team.
 *
 * @param modelName Name of the model
 * @param displayName Display name of the model
 * @param policyState Policy state of the model
 * @param enterprisePolicyState Enterprise policy state of the model
 * @param editable Whether the model policy is editable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-team-model-policies/200/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseTeamModelPoliciesResponseModel(
        String modelName,
        String displayName,
        GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState policyState,
        GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState enterprisePolicyState,
        boolean editable) {

    public GitHubUpdateEnterpriseTeamModelPoliciesResponseModel {
        requireNonNull(modelName, "modelName");
        requireNonNull(displayName, "displayName");
        requireNonNull(policyState, "policyState");
        requireNonNull(enterprisePolicyState, "enterprisePolicyState");
    }
}
