package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Model policies and default availability to update.
 *
 * @param defaultAvailability Default availability of the models
 * @param models Model policies to update
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-enterprise-model-policies/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateEnterpriseModelPoliciesPayload(
        @Nullable GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailability defaultAvailability,
        @Nullable List<GitHubUpdateEnterpriseModelPoliciesPayloadModel> models) {

    public GitHubUpdateEnterpriseModelPoliciesPayload {
        requireAtLeastOne(defaultAvailability, models, "defaultAvailability");
        if (models != null) {
            models = List.copyOf(models);
        }
    }
}
