package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Model policy changes for an organization.
 *
 * @param defaultAvailability The organization default availability for eligible models without an explicit policy.
 * @param models The model policies to update. Each model name must be unique.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/update-organization-model-policies/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateOrganizationModelPoliciesPayload(
        @Nullable GitHubUpdateOrganizationModelPoliciesDefaultAvailability defaultAvailability,
        @Nullable List<GitHubUpdateOrganizationModelPoliciesModel> models) {

    public GitHubUpdateOrganizationModelPoliciesPayload {
        if (models != null) {
            models = List.copyOf(requireNonEmpty(models, "models"));
        }
        if (defaultAvailability == null && models == null) {
            throw new IllegalArgumentException("at least one of defaultAvailability or models is required");
        }
    }
}
