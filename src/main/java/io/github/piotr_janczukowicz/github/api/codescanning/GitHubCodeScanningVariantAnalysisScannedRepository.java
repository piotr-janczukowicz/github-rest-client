package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning variant analysis scanned repository.
 *
 * @param repository Repository Identifier
 * @param analysisStatus The new status of the CodeQL variant analysis repository task.
 * @param resultCount The number of results in the case of a successful analysis.
 * @param artifactSizeInBytes The size of the artifact.
 * @param failureMessage The reason of the failure of this repo task.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-variant-analysis/properties/scanned_repositories/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningVariantAnalysisScannedRepository(
        GitHubCodeScanningVariantAnalysisRepository repository,
        GitHubCodeScanningVariantAnalysisStatus analysisStatus,
        @Nullable Integer resultCount,
        @Nullable Integer artifactSizeInBytes,
        @Nullable String failureMessage) {
    public GitHubCodeScanningVariantAnalysisScannedRepository {
        requireNonNull(repository, "repository");
        requireNonNull(analysisStatus, "analysisStatus");
    }
}
