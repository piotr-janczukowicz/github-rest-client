package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Update enterprise fusion model policies payload.
 *
 * @param models models
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-fusion-model-policies/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseFusionModelPoliciesPayload(
        List<GitHubUpdateEnterpriseFusionModelPoliciesModel> models) {
    public GitHubUpdateEnterpriseFusionModelPoliciesPayload {
        models = List.copyOf(requireNonEmpty(models, "models"));
    }
}
