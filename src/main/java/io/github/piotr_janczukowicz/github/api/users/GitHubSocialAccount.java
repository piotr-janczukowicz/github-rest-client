package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Social account. Social media account.
 *
 * @param provider Provider
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/social-account")
@RecordBuilder
public record GitHubSocialAccount(String provider, String url) {

    public GitHubSocialAccount {
        requireNonNull(provider, "provider");
        requireNonNull(url, "url");
    }
}
