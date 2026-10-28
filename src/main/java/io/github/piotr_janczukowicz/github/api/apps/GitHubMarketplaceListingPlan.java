package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Marketplace listing plan.
 *
 * @param url Url
 * @param accountsUrl Accounts url
 * @param id Id
 * @param number Number
 * @param name Name
 * @param description Description
 * @param monthlyPriceInCents Monthly price in cents
 * @param yearlyPriceInCents Yearly price in cents
 * @param priceModel Price model
 * @param hasFreeTrial Has free trial
 * @param unitName Unit name
 * @param state State
 * @param bullets Bullets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/marketplace-listing-plan")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMarketplaceListingPlan(
        String url,
        String accountsUrl,
        long id,
        int number,
        String name,
        String description,
        int monthlyPriceInCents,
        int yearlyPriceInCents,
        GitHubMarketplaceListingPlanPriceModel priceModel,
        boolean hasFreeTrial,
        @Nullable String unitName,
        String state,
        List<String> bullets) {

    public GitHubMarketplaceListingPlan {
        requireNonNull(url, "url");
        requireNonNull(accountsUrl, "accountsUrl");
        requireNonNull(name, "name");
        requireNonNull(description, "description");
        requireNonNull(priceModel, "priceModel");
        requireNonNull(state, "state");
        requireNonNull(bullets, "bullets");
    }
}
