package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repositories of an installation accessible to the authenticated user.
 *
 * @param totalCount Total count
 * @param repositorySelection Repository selection
 * @param repositories Repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/list-installation-repos-for-authenticated-user/200")
public record GitHubListInstallationReposForAuthenticatedUserPage(
        int totalCount,
        @Nullable String repositorySelection,
        List<GitHubListInstallationReposForAuthenticatedUserRepository> repositories) {

    public GitHubListInstallationReposForAuthenticatedUserPage {
        requireNonNull(repositories, "repositories");
    }
}
