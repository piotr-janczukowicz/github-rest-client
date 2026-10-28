package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * User marketplace purchase.
 *
 * @param billingCycle Billing cycle
 * @param nextBillingDate Next billing date
 * @param unitCount Unit count
 * @param onFreeTrial On free trial
 * @param freeTrialEndsOn Free trial ends on
 * @param updatedAt Updated at
 * @param account Account
 * @param plan Plan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/user-marketplace-purchase")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUserMarketplacePurchase(
        String billingCycle,
        @Nullable Instant nextBillingDate,
        @Nullable Integer unitCount,
        boolean onFreeTrial,
        @Nullable Instant freeTrialEndsOn,
        @Nullable Instant updatedAt,
        GitHubMarketplaceAccount account,
        GitHubMarketplaceListingPlan plan) {

    public GitHubUserMarketplacePurchase {
        requireNonNull(billingCycle, "billingCycle");
        requireNonNull(account, "account");
        requireNonNull(plan, "plan");
    }
}
