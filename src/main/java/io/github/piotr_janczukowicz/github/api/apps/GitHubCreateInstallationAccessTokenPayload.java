package io.github.piotr_janczukowicz.github.api.apps;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubAppPermissions;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for creating an installation access token.
 *
 * @param repositories List of repository names that the token should have access to
 * @param repositoryIds List of repository ids that the token should have access to
 * @param permissions The permissions granted to the user access token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/create-installation-access-token/requestBody")
public record GitHubCreateInstallationAccessTokenPayload(
        @Nullable List<String> repositories,
        @Nullable List<Long> repositoryIds,
        @Nullable GitHubAppPermissions permissions) {

    public GitHubCreateInstallationAccessTokenPayload {
        if (repositories != null) {
            repositories = List.copyOf(repositories);
        }
        if (repositoryIds != null) {
            repositoryIds = List.copyOf(repositoryIds);
        }
    }
}
