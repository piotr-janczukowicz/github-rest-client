package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The OAuth app or GitHub App that the authorization belongs to.
 *
 * @param clientId Client id
 * @param name Name
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/authorization/properties/app")
public record GitHubAuthorizationApp(String clientId, String name, String url) {

    public GitHubAuthorizationApp {
        requireNonNull(clientId, "clientId");
        requireNonNull(name, "name");
        requireNonNull(url, "url");
    }
}
