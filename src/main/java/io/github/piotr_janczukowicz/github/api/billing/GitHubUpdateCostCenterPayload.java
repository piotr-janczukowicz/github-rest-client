package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request body for updating a cost center. At least one of name or aiCreditPoolEnabled is required.
 *
 * @param name The new name for the cost center.
 * @param aiCreditPoolEnabled Whether the cost center draws from the AI credit pool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/update-cost-center/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateCostCenterPayload(
        @Nullable String name, @Nullable Boolean aiCreditPoolEnabled) {
    public GitHubUpdateCostCenterPayload {
        requireAtLeastOne(name, aiCreditPoolEnabled, "name or aiCreditPoolEnabled");
    }
}
