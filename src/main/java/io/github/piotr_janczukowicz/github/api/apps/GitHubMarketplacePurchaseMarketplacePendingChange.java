package io.github.piotr_janczukowicz.github.api.apps;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pending change of a marketplace purchase.
 *
 * @param isInstalled Is installed
 * @param effectiveDate Effective date
 * @param unitCount Unit count
 * @param id Id
 * @param plan Plan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/marketplace-purchase/properties/marketplace_pending_change")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMarketplacePurchaseMarketplacePendingChange(
        @Nullable Boolean isInstalled,
        @Nullable String effectiveDate,
        @Nullable Integer unitCount,
        @Nullable Long id,
        @Nullable GitHubMarketplaceListingPlan plan) {}
