package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request body for creating a cost center.
 *
 * @param name The name of the cost center (max length 255 characters).
 * @param aiCreditPoolEnabled Whether the cost center draws from the AI credit pool.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-cost-center/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateCostCenterPayload(
        String name, @Nullable Boolean aiCreditPoolEnabled) {
    public GitHubCreateCostCenterPayload {
        requireNonNull(name, "name");
    }
}
