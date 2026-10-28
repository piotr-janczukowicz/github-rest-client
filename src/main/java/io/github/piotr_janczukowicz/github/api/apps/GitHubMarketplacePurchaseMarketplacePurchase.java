package io.github.piotr_janczukowicz.github.api.apps;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Marketplace purchase details.
 *
 * @param billingCycle Billing cycle
 * @param nextBillingDate Next billing date
 * @param isInstalled Is installed
 * @param unitCount Unit count
 * @param onFreeTrial On free trial
 * @param freeTrialEndsOn Free trial ends on
 * @param updatedAt Updated at
 * @param plan Plan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/marketplace-purchase/properties/marketplace_purchase")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMarketplacePurchaseMarketplacePurchase(
        @Nullable String billingCycle,
        @Nullable String nextBillingDate,
        @Nullable Boolean isInstalled,
        @Nullable Integer unitCount,
        @Nullable Boolean onFreeTrial,
        @Nullable String freeTrialEndsOn,
        @Nullable String updatedAt,
        @Nullable GitHubMarketplaceListingPlan plan) {}
