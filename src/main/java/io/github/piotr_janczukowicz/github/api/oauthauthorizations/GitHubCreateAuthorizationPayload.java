package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * GitHubCreateAuthorizationPayload
 *
 * @param scopes A list of scopes that this authorization is in.
 * @param note A note to remind you what the OAuth token is for.
 * @param noteUrl A URL to remind you what app the OAuth token is for.
 * @param clientId The OAuth app client key for which to create the token.
 * @param clientSecret The OAuth app client secret for which to create the token.
 * @param fingerprint A unique string to distinguish an authorization from others created for the same client ID and
 *     user.
 * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow. Deprecation
 *     date {@code 2020-02-14}; removal date {@code 2020-11-13}.
 */
@Deprecated(since = "2020-02-14")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/oauth-authorizations/create-authorization/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubCreateAuthorizationPayload(
        @Nullable List<String> scopes,
        @Nullable String note,
        @Nullable String noteUrl,
        @Nullable String clientId,
        @Nullable String clientSecret,
        @Nullable String fingerprint) {
    public GitHubCreateAuthorizationPayload {
        if (scopes != null) {
            scopes = List.copyOf(scopes);
        }
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code clientSecret} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreateAuthorizationPayload[" + "scopes=" + scopes + ", note=" + note + ", noteUrl=" + noteUrl
                + ", clientId=" + clientId + ", clientSecret=<redacted>" + ", fingerprint=" + fingerprint + "]";
    }
}
