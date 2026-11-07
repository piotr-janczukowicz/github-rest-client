package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Model.
 *
 * @param modelName model name
 * @param displayName display name
 * @param policyState When editable, the Copilot Fusion layer's configured value, or {@code enabled} if the Copilot
 *     Fusion layer has not configured this model, because Copilot Fusion only restricts models it explicitly disables.
 *     When locked, the enterprise's explicit value or its applied default availability for an eligible model. Returns
 *     {@code enabled} when locked to an applied default availability that is delegated to organizations. Returns
 *     {@code disabled} when locked without an explicit enterprise value and the model is excluded from default
 *     availability.
 * @param enterprisePolicyState The separately read enterprise-layer policy for this model, used to determine whether
 *     the Copilot Fusion layer can edit its policy.
 * @param editable Whether the enterprise policy allows the Copilot Fusion layer's policy to be updated.
 * @param requiredForCopilotFusionMax Indicates that the model registry explicitly marks this model as required for
 *     Copilot Fusion Max. Only present with the value {@code true}; otherwise omitted. Omission does not guarantee that
 *     the model is optional or that Copilot Fusion Max is available. This read-only metadata does not change model
 *     policy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-enterprise-fusion-model-policies/200/properties/models/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetEnterpriseFusionModelPoliciesResponseModel(
        String modelName,
        String displayName,
        GitHubGetEnterpriseFusionModelPoliciesResponseModelPolicyState policyState,
        GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState enterprisePolicyState,
        boolean editable,
        @Nullable Boolean requiredForCopilotFusionMax) {
    public GitHubGetEnterpriseFusionModelPoliciesResponseModel {
        requireNonNull(modelName, "modelName");
        requireNonNull(displayName, "displayName");
        requireNonNull(policyState, "policyState");
        requireNonNull(enterprisePolicyState, "enterprisePolicyState");
    }
}
