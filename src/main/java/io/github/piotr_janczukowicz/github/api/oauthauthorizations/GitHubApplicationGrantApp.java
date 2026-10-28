package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubApplicationGrantApp
 *
 * @param clientId client id
 * @param name name
 * @param url url
 * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow. Deprecation
 *     date {@code 2020-02-14}; removal date {@code 2020-11-13}.
 */
@Deprecated(since = "2020-02-14")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/application-grant/properties/app")
@Flavors(GitHubFlavor.GHES)
public record GitHubApplicationGrantApp(String clientId, String name, String url) {
    public GitHubApplicationGrantApp {
        requireNonNull(clientId, "clientId");
        requireNonNull(name, "name");
        requireNonNull(url, "url");
    }
}
