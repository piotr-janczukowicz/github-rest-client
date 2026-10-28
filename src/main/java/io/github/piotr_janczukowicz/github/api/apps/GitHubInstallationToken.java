package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubAppPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Authentication token for a GitHub App installed on a user or org.
 *
 * @param token The token used for authentication
 * @param expiresAt The time this token expires
 * @param permissions The permissions granted to the user access token
 * @param repositorySelection Describe whether all repositories have been selected or there's a selection involved
 * @param repositories List of repositories that the token has access to
 * @param singleFile Single file
 * @param hasMultipleSingleFiles Has multiple single files
 * @param singleFilePaths Single file paths
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/installation-token")
public record GitHubInstallationToken(
        String token,

        @SuppressSchemaTypeMismatch("dump declares a plain string; the value is an ISO 8601 timestamp")
        Instant expiresAt,

        @Nullable GitHubAppPermissions permissions,
        @Nullable GitHubInstallationTokenRepositorySelection repositorySelection,
        @Nullable List<GitHubRepository> repositories,
        @Nullable String singleFile,
        @Nullable Boolean hasMultipleSingleFiles,
        @Nullable List<String> singleFilePaths) {

    public GitHubInstallationToken {
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
        return "GitHubInstallationToken[token=<redacted>, expiresAt=" + expiresAt + ", permissions=" + permissions
                + ", repositorySelection=" + repositorySelection + ", repositories=" + repositories + ", singleFile="
                + singleFile + ", hasMultipleSingleFiles=" + hasMultipleSingleFiles + ", singleFilePaths="
                + singleFilePaths + "]";
    }
}
