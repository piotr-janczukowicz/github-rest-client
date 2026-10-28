package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Copilot model policies of the enterprise.
 *
 * @param defaultAvailability Default availability of the models
 * @param models Model policies
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-model-policies/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseModelPoliciesResponse(
        @Nullable GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailability defaultAvailability,
        List<GitHubUpdateEnterpriseModelPoliciesResponseModel> models) {

    public GitHubUpdateEnterpriseModelPoliciesResponse {
        requireNonNull(models, "models");
    }
}
