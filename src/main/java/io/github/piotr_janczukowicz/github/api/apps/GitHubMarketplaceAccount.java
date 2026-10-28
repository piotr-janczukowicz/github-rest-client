package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Marketplace account.
 *
 * @param url Url
 * @param id Id
 * @param type Type
 * @param nodeId Node id
 * @param login Login
 * @param email Email
 * @param organizationBillingEmail Organization billing email
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/marketplace-account")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubMarketplaceAccount(
        String url,
        long id,
        String type,
        @Nullable String nodeId,
        String login,
        @Nullable String email,
        @Nullable String organizationBillingEmail) {

    public GitHubMarketplaceAccount {
        requireNonNull(url, "url");
        requireNonNull(type, "type");
        requireNonNull(login, "login");
    }
}
