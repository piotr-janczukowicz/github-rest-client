package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning variant analysis repo task.
 *
 * @param repository A GitHub repository.
 * @param analysisStatus The new status of the CodeQL variant analysis repository task.
 * @param artifactSizeInBytes The size of the artifact.
 * @param resultCount The number of results in the case of a successful analysis.
 * @param failureMessage The reason of the failure of this repo task.
 * @param databaseCommitSha The SHA of the commit the CodeQL database was built against.
 * @param sourceLocationPrefix The source location prefix to use.
 * @param artifactUrl The URL of the artifact.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-variant-analysis-repo-task")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningVariantAnalysisRepoTask(
        GitHubSimpleRepository repository,
        GitHubCodeScanningVariantAnalysisStatus analysisStatus,
        @Nullable Integer artifactSizeInBytes,
        @Nullable Integer resultCount,
        @Nullable String failureMessage,
        @Nullable String databaseCommitSha,
        @Nullable String sourceLocationPrefix,
        @Nullable String artifactUrl) {
    public GitHubCodeScanningVariantAnalysisRepoTask {
        requireNonNull(repository, "repository");
        requireNonNull(analysisStatus, "analysisStatus");
    }
}
