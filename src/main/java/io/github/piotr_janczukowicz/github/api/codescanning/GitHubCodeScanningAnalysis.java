package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.COMMIT_SHA;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning analysis.
 *
 * @param ref The Git reference, formatted as `refs/pull/&lt;number&gt;/merge`, `refs/pull/&lt;number&gt;/head`,
 *     `refs/heads/&lt;branch name&gt;` or simply `&lt;branch name&gt;`.
 * @param commitSha The SHA of the commit to which the analysis you are uploading relates.
 * @param analysisKey Identifies the configuration under which the analysis was executed.
 * @param environment Identifies the variable values associated with the environment in which this analysis was
 *     performed.
 * @param category Identifies the configuration under which the analysis was executed.
 * @param error Error.
 * @param createdAt The time that the analysis was created in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 * @param resultsCount The total number of results in the analysis.
 * @param rulesCount The total number of rules used in the analysis.
 * @param id Unique identifier for this analysis.
 * @param url The REST API URL of the analysis resource.
 * @param sarifId An identifier for the upload.
 * @param tool Tool.
 * @param deletable Deletable.
 * @param warning Warning generated when processing the analysis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-analysis")
public record GitHubCodeScanningAnalysis(
        String ref,
        String commitSha,
        String analysisKey,
        String environment,
        @Nullable String category,
        String error,
        Instant createdAt,
        int resultsCount,
        int rulesCount,
        long id,
        String url,
        String sarifId,
        GitHubCodeScanningAnalysisTool tool,
        boolean deletable,
        String warning) {
    private static final String PARAM_COMMIT_SHA = "commitSha";

    public GitHubCodeScanningAnalysis {
        requireNonNull(ref, "ref");
        requireNonNull(commitSha, PARAM_COMMIT_SHA);
        commitSha = requireMinLength(commitSha, 40, PARAM_COMMIT_SHA);
        commitSha = requireMaxLength(commitSha, 64, PARAM_COMMIT_SHA);
        commitSha = requireMatches(commitSha, COMMIT_SHA, PARAM_COMMIT_SHA);
        requireNonNull(analysisKey, "analysisKey");
        requireNonNull(environment, "environment");
        requireNonNull(error, "error");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(url, "url");
        requireNonNull(sarifId, "sarifId");
        requireNonNull(tool, "tool");
        requireNonNull(warning, "warning");
    }
}
