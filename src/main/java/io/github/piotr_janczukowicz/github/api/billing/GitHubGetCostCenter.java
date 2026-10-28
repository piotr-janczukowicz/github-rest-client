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
 * Cost center of an enterprise.
 *
 * @param id ID of the cost center.
 * @param name Name of the cost center.
 * @param azureSubscription Azure subscription ID associated with the cost center.
 * @param state State of the cost center.
 * @param resources Resources assigned to the cost center.
 * @param hasNextPage Indicates if there are more resources available for pagination.
 * @param aiCreditPoolEnabled Whether the cost center draws from the AI credit pool.
 * @param aiCreditPoolState Read-only cap-budget projection for the cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-cost-center")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCostCenter(
        String id,
        String name,
        @Nullable String azureSubscription,
        @Nullable GitHubGetCostCenterState state,
        List<GitHubGetCostCenterResource> resources,
        @Nullable Boolean hasNextPage,
        @Nullable Boolean aiCreditPoolEnabled,
        @Nullable GitHubGetCostCenterAiCreditPoolState aiCreditPoolState) {
    public GitHubGetCostCenter {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
        requireNonNull(resources, "resources");
    }
}
