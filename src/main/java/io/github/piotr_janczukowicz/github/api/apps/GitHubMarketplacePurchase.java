package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Marketplace purchase.
 *
 * @param url Url
 * @param type Type
 * @param id Id
 * @param login Login
 * @param organizationBillingEmail Organization billing email
 * @param email Email
 * @param marketplacePendingChange Marketplace pending change
 * @param marketplacePurchase Marketplace purchase
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/marketplace-purchase")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMarketplacePurchase(
        String url,
        String type,
        long id,
        String login,
        @Nullable String organizationBillingEmail,
        @Nullable String email,
        @Nullable GitHubMarketplacePurchaseMarketplacePendingChange marketplacePendingChange,
        GitHubMarketplacePurchaseMarketplacePurchase marketplacePurchase) {

    public GitHubMarketplacePurchase {
        requireNonNull(url, "url");
        requireNonNull(type, "type");
        requireNonNull(login, "login");
        requireNonNull(marketplacePurchase, "marketplacePurchase");
    }
}
