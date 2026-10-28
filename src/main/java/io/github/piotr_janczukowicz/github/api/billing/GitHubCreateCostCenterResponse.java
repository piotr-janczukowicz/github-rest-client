package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Cost center created by the enterprise billing API.
 *
 * @param id Unique identifier for the cost center.
 * @param name Name of the cost center.
 * @param azureSubscription Azure subscription ID associated with the cost center.
 * @param state State of the cost center.
 * @param resources Resources assigned to this cost center.
 * @param aiCreditPoolEnabled Whether the cost center draws from the AI credit pool.
 * @param aiCreditPoolState Read-only cap-budget projection for the cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-cost-center/200")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateCostCenterResponse(
        @Nullable String id,
        @Nullable String name,
        @Nullable String azureSubscription,
        @Nullable GitHubCreateCostCenterResponseState state,
        @Nullable List<GitHubCreateCostCenterResponseResource> resources,
        @Nullable Boolean aiCreditPoolEnabled,
        @Nullable GitHubCreateCostCenterResponseAiCreditPoolState aiCreditPoolState) {}
