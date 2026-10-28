package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The authorization for an OAuth app, GitHub App, or a Personal Access Token.
 *
 * @param id Id
 * @param url Url
 * @param scopes A list of scopes that this authorization is in
 * @param token Token
 * @param tokenLastEight Token last eight
 * @param hashedToken Hashed token
 * @param app App
 * @param note Note
 * @param noteUrl Note url
 * @param updatedAt Updated at
 * @param createdAt Created at
 * @param fingerprint Fingerprint
 * @param user User
 * @param installation Installation
 * @param expiresAt Expires at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/authorization")
public record GitHubAuthorization(
        long id,
        String url,
        @Nullable List<String> scopes,
        String token,
        @Nullable String tokenLastEight,
        @Nullable String hashedToken,
        GitHubAuthorizationApp app,
        @Nullable String note,
        @Nullable String noteUrl,
        Instant updatedAt,
        Instant createdAt,
        @Nullable String fingerprint,
        @Nullable GitHubSimpleUser user,
        @Nullable GitHubScopedInstallation installation,
        @Nullable Instant expiresAt) {

    public GitHubAuthorization {
        requireNonNull(url, "url");
        requireNonNull(token, "token");
        requireNonNull(app, "app");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(createdAt, "createdAt");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code token} redacted
     */
    @Override
    public String toString() {
        return "GitHubAuthorization[id=" + id + ", url=" + url + ", scopes=" + scopes
                + ", token=<redacted>, tokenLastEight=" + tokenLastEight + ", hashedToken=" + hashedToken + ", app="
                + app + ", note=" + note + ", noteUrl=" + noteUrl + ", updatedAt=" + updatedAt + ", createdAt="
                + createdAt + ", fingerprint=" + fingerprint + ", user=" + user + ", installation=" + installation
                + ", expiresAt=" + expiresAt + "]";
    }
}
