package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Copilot model policies of the enterprise team.
 *
 * @param models Model policies
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-enterprise-team-model-policies/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetEnterpriseTeamModelPoliciesResponse(
        List<GitHubGetEnterpriseTeamModelPoliciesResponseModel> models) {

    public GitHubGetEnterpriseTeamModelPoliciesResponse {
        requireNonNull(models, "models");
    }
}
