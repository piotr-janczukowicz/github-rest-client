package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Cost center in a list of cost centers.
 *
 * @param id ID of the cost center.
 * @param name Name of the cost center.
 * @param state State of the cost center.
 * @param azureSubscription Azure subscription ID associated with the cost center.
 * @param aiCreditPoolEnabled Whether the cost center draws from the AI credit pool.
 * @param aiCreditPoolState Read-only cap-budget projection for the cost center.
 * @param resources Resources assigned to the cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-all-cost-centers/properties/costCenters/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetAllCostCentersCostCenter(
        String id,
        String name,
        @Nullable GitHubGetAllCostCentersCostCenterState state,
        @Nullable String azureSubscription,
        @Nullable Boolean aiCreditPoolEnabled,
        @Nullable GitHubGetAllCostCentersCostCenterAiCreditPoolState aiCreditPoolState,
        List<GitHubGetAllCostCentersCostCenterResource> resources) {
    public GitHubGetAllCostCentersCostCenter {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(resources, "resources");
    }
}
