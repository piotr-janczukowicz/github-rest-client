package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code POST /repos/{owner}/{repo}/code-scanning/codeql/variant-analyses}.
 *
 * <p>The request body must include exactly one of {@code repositories}, {@code repositoryLists} or
 * {@code repositoryOwners}.
 *
 * @param language The language targeted by the CodeQL query
 * @param queryPack A Base64-encoded tarball containing a CodeQL query and all its dependencies
 * @param repositories List of repository names (in the form `owner/repo-name`) to run the query against.
 * @param repositoryLists List of repository lists to run the query against.
 * @param repositoryOwners List of organization or user names whose repositories the query should be run against.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/create-variant-analysis/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateVariantAnalysisPayload(
        GitHubCodeScanningVariantAnalysisLanguage language,
        String queryPack,
        @Nullable List<String> repositories,
        @Nullable List<String> repositoryLists,
        @Nullable List<String> repositoryOwners) {
    private static final int REQUIRED_BRANCH_COUNT = 1;

    public GitHubCreateVariantAnalysisPayload {
        requireNonNull(language, "language");
        requireNonNull(queryPack, "queryPack");
        final int branches =
                (repositories == null ? 0 : 1) + (repositoryLists == null ? 0 : 1) + (repositoryOwners == null ? 0 : 1);
        if (branches != REQUIRED_BRANCH_COUNT) {
            throw new IllegalArgumentException(
                    "exactly one of repositories, repositoryLists or repositoryOwners is required");
        }
        if (repositories != null) {
            repositories = List.copyOf(repositories);
        }
        if (repositoryLists != null) {
            repositoryLists = List.copyOf(requireSizeAtMost(repositoryLists, 1, "repositoryLists"));
        }
        if (repositoryOwners != null) {
            repositoryOwners = List.copyOf(requireSizeAtMost(repositoryOwners, 1, "repositoryOwners"));
        }
    }
}
