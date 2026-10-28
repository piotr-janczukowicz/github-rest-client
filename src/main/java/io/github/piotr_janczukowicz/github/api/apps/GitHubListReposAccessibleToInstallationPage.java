package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Repositories accessible to the installation.
 *
 * @param totalCount Total count
 * @param repositories Repositories
 * @param repositorySelection Repository selection
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/list-repos-accessible-to-installation/200")
public record GitHubListReposAccessibleToInstallationPage(
        int totalCount,
        List<GitHubListReposAccessibleToInstallationRepository> repositories,
        @Nullable String repositorySelection) {

    public GitHubListReposAccessibleToInstallationPage {
        requireNonNull(repositories, "repositories");
    }
}
