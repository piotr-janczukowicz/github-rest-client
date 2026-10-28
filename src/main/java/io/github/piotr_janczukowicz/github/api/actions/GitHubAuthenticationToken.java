package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Authentication token.
 *
 * @param token The token used for authentication
 * @param expiresAt The time this token expires
 * @param permissions The permissions granted to the token
 * @param repositories The repositories this token has access to
 * @param singleFile The single file the token has access to
 * @param repositorySelection Whether all repositories have been selected or there is a selection involved
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/authentication-token")
public record GitHubAuthenticationToken(
        String token,
        Instant expiresAt,

        @SuppressSchemaTypeMismatch("dump is a bare object with no properties") @Nullable Map<String, Object> permissions,

        @Nullable List<GitHubRepository> repositories,
        @Nullable String singleFile,
        @Nullable GitHubAuthenticationTokenRepositorySelection repositorySelection) {

    public GitHubAuthenticationToken {
        requireNonNull(token, "token");
        requireNonNull(expiresAt, "expiresAt");
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code token} redacted
     */
    @Override
    public String toString() {
        return "GitHubAuthenticationToken[token=<redacted>, expiresAt=" + expiresAt + ", permissions=" + permissions
                + ", repositories=" + repositories + ", singleFile=" + singleFile + ", repositorySelection="
                + repositorySelection + "]";
    }
}
