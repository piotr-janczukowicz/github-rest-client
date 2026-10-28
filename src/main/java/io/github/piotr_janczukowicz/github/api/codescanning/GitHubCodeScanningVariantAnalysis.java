package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A run of a CodeQL query against one or more repositories.
 *
 * @param id The ID of the variant analysis.
 * @param controllerRepo A GitHub repository.
 * @param actor A GitHub user.
 * @param queryLanguage The language targeted by the CodeQL query
 * @param queryPackUrl The download url for the query pack.
 * @param createdAt The date and time at which the variant analysis was created, in ISO 8601 format':' YYYY-MM-
 *     DDTHH:MM:SSZ.
 * @param updatedAt The date and time at which the variant analysis was last updated, in ISO 8601 format':' YYYY-
 *     MM-DDTHH:MM:SSZ.
 * @param completedAt The date and time at which the variant analysis was completed, in ISO 8601 format':' YYYY-
 *     MM-DDTHH:MM:SSZ.
 * @param status Status.
 * @param actionsWorkflowRunId The GitHub Actions workflow run used to execute this variant analysis.
 * @param failureReason The reason for a failure of the variant analysis.
 * @param scannedRepositories Scanned repositories.
 * @param skippedRepositories Information about repositories that were skipped from processing.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-variant-analysis")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeScanningVariantAnalysis(
        long id,
        GitHubSimpleRepository controllerRepo,
        GitHubSimpleUser actor,
        GitHubCodeScanningVariantAnalysisLanguage queryLanguage,
        String queryPackUrl,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable Instant completedAt,
        GitHubCodeScanningVariantAnalysisOverallStatus status,
        @Nullable Long actionsWorkflowRunId,
        @Nullable GitHubCodeScanningVariantAnalysisFailureReason failureReason,
        @Nullable List<GitHubCodeScanningVariantAnalysisScannedRepository> scannedRepositories,
        @Nullable GitHubCodeScanningVariantAnalysisSkippedRepositories skippedRepositories) {
    public GitHubCodeScanningVariantAnalysis {
        requireNonNull(controllerRepo, "controllerRepo");
        requireNonNull(actor, "actor");
        requireNonNull(queryLanguage, "queryLanguage");
        requireNonNull(queryPackUrl, "queryPackUrl");
        requireNonNull(status, "status");
    }
}
