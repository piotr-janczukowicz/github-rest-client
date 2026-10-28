package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Model policies to update for the enterprise apps.
 *
 * @param models Model policies to update
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-apps-model-policies/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseAppsModelPoliciesPayload(
        List<GitHubUpdateEnterpriseAppsModelPoliciesModel> models) {

    public GitHubUpdateEnterpriseAppsModelPoliciesPayload {
        models = List.copyOf(requireNonEmpty(requireNonNull(models, "models"), "models"));
    }
}
