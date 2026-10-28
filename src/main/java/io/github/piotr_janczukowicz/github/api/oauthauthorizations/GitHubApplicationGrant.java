package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The authorization associated with an OAuth Access.
 *
 * @param id Id
 * @param url Url
 * @param app App
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param scopes A list of scopes that the user has granted
 * @param user User
 * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow. Deprecation
 *     date {@code 2020-02-14}; removal date {@code 2020-11-13}.
 */
@Deprecated(since = "2020-02-14")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/application-grant")
@Flavors(GitHubFlavor.GHES)
public record GitHubApplicationGrant(
        long id,
        String url,
        GitHubApplicationGrantApp app,
        Instant createdAt,
        Instant updatedAt,
        List<String> scopes,
        @Nullable GitHubSimpleUser user) {

    public GitHubApplicationGrant {
        requireNonNull(url, "url");
        requireNonNull(app, "app");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(scopes, "scopes");
    }
}
