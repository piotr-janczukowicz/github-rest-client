package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Code scanning variant analysis skipped repo group.
 *
 * @param repositoryCount The total number of repositories that were skipped for this reason.
 * @param repositories A list of repositories that were skipped.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-variant-analysis-skipped-repo-group")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningVariantAnalysisSkippedRepoGroup(
        int repositoryCount, List<GitHubCodeScanningVariantAnalysisRepository> repositories) {
    public GitHubCodeScanningVariantAnalysisSkippedRepoGroup {
        requireNonNull(repositories, "repositories");
    }
}
