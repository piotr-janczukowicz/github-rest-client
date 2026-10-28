package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Information about repositories that were skipped from processing.
 *
 * @param accessMismatchRepos Access mismatch repos.
 * @param notFoundRepos Not found repos.
 * @param noCodeqlDbRepos No codeql db repos.
 * @param overLimitRepos Over limit repos.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-variant-analysis/properties/skipped_repositories")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningVariantAnalysisSkippedRepositories(
        GitHubCodeScanningVariantAnalysisSkippedRepoGroup accessMismatchRepos,
        GitHubCodeScanningVariantAnalysisSkippedRepositoriesNotFoundRepos notFoundRepos,
        GitHubCodeScanningVariantAnalysisSkippedRepoGroup noCodeqlDbRepos,
        GitHubCodeScanningVariantAnalysisSkippedRepoGroup overLimitRepos) {
    public GitHubCodeScanningVariantAnalysisSkippedRepositories {
        requireNonNull(accessMismatchRepos, "accessMismatchRepos");
        requireNonNull(notFoundRepos, "notFoundRepos");
        requireNonNull(noCodeqlDbRepos, "noCodeqlDbRepos");
        requireNonNull(overLimitRepos, "overLimitRepos");
    }
}
