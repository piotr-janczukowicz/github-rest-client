package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubAppPermissions;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Scoped installation.
 *
 * @param permissions The permissions granted to the fine-grained access token
 * @param repositorySelection Describe whether all repositories have been selected or there's a selection involved
 * @param singleFileName Single file name
 * @param hasMultipleSingleFiles Has multiple single files
 * @param singleFilePaths Single file paths
 * @param repositoriesUrl Repositories url
 * @param account Account
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/scoped-installation")
public record GitHubScopedInstallation(
        GitHubAppPermissions permissions,
        GitHubScopedInstallationRepositorySelection repositorySelection,
        @Nullable String singleFileName,
        @Nullable Boolean hasMultipleSingleFiles,
        @Nullable List<String> singleFilePaths,
        String repositoriesUrl,
        GitHubSimpleUser account) {

    public GitHubScopedInstallation {
        requireNonNull(permissions, "permissions");
        requireNonNull(repositorySelection, "repositorySelection");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(account, "account");
    }
}
