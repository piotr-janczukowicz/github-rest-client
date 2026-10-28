package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubUpdateAuthorizationPayload
 *
 * @param scopes A list of scopes that this authorization is in.
 * @param addScopes A list of scopes to add to this authorization.
 * @param removeScopes A list of scopes to remove from this authorization.
 * @param note A note to remind you what the OAuth token is for.
 * @param noteUrl A URL to remind you what app the OAuth token is for.
 * @param fingerprint A unique string to distinguish an authorization from others created for the same client ID and
 *     user.
 * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow. Deprecation
 *     date {@code 2020-02-14}; removal date {@code 2020-11-13}.
 */
@Deprecated(since = "2020-02-14")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/oauth-authorizations/update-authorization/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdateAuthorizationPayload(
        @Nullable List<String> scopes,
        @Nullable List<String> addScopes,
        @Nullable List<String> removeScopes,
        @Nullable String note,
        @Nullable String noteUrl,
        @Nullable String fingerprint) {
    public GitHubUpdateAuthorizationPayload {
        if (scopes != null) {
            scopes = List.copyOf(scopes);
        }
        if (addScopes != null) {
            addScopes = List.copyOf(addScopes);
        }
        if (removeScopes != null) {
            removeScopes = List.copyOf(removeScopes);
        }
    }
}
