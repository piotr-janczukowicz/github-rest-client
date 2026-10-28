package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.CODE_SCANNING_REF;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.COMMIT_SHA;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code POST /repos/{owner}/{repo}/code-scanning/sarifs}.
 *
 * @param commitSha The SHA of the commit to which the analysis you are uploading relates.
 * @param ref The full Git reference, formatted as `refs/heads/&lt;branch name&gt;`, `refs/tags/&lt;tag&gt;`,
 *     `refs/pull/&lt;number&gt;/merge`, or `refs/pull/&lt;number&gt;/head`.
 * @param sarif A Base64 string representing the SARIF file to upload.
 * @param checkoutUri The base directory used in the analysis, as it appears in the SARIF file.
 * @param startedAt The time that the analysis run began.
 * @param toolName The name of the tool used to generate the code scanning analysis.
 * @param validate Whether the SARIF file will be validated according to the code scanning specifications.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/upload-sarif/requestBody")
public record GitHubUploadSarifPayload(
        String commitSha,
        String ref,
        String sarif,
        @Nullable URI checkoutUri,
        @Nullable Instant startedAt,
        @Nullable String toolName,
        @Nullable Boolean validate) {
    private static final String PARAM_COMMIT_SHA = "commitSha";

    public GitHubUploadSarifPayload {
        requireNonNull(commitSha, PARAM_COMMIT_SHA);
        commitSha = requireMinLength(commitSha, 40, PARAM_COMMIT_SHA);
        commitSha = requireMaxLength(commitSha, 64, PARAM_COMMIT_SHA);
        commitSha = requireMatches(commitSha, COMMIT_SHA, PARAM_COMMIT_SHA);
        requireNonNull(ref, "ref");
        ref = requireMatches(ref, CODE_SCANNING_REF, "ref");
        requireNonNull(sarif, "sarif");
    }
}
