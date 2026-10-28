package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubAppPermissions;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for scoping an authorization token.
 *
 * @param accessToken The OAuth access token used to authenticate to the GitHub API
 * @param target The name of the user or organization to scope the user access token to
 * @param targetId The ID of the user or organization to scope the user access token to
 * @param repositories The list of repository names to scope the user access token to
 * @param repositoryIds The list of repository IDs to scope the user access token to
 * @param permissions The permissions granted to the user access token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/scope-token/requestBody")
public record GitHubScopeTokenPayload(
        String accessToken,
        @Nullable String target,
        @Nullable Long targetId,
        @Nullable List<String> repositories,
        @Nullable List<Long> repositoryIds,
        @Nullable GitHubAppPermissions permissions) {

    public GitHubScopeTokenPayload {
        requireNonNull(accessToken, "accessToken");
        if (repositories != null) {
            repositories = List.copyOf(repositories);
        }
        if (repositoryIds != null) {
            repositoryIds = List.copyOf(repositoryIds);
        }
    }

    /**
     * Describes this record without its secrets.
     *
     * @return text with {@code accessToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubScopeTokenPayload[accessToken=<redacted>, target=" + target + ", targetId=" + targetId
                + ", repositories=" + repositories + ", repositoryIds=" + repositoryIds + ", permissions=" + permissions
                + "]";
    }
}
