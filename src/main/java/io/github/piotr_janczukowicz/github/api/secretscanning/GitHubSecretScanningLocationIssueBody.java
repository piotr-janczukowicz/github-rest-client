package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Represents an 'issue_body' secret scanning location type. This location type shows that a secret was detected in the
 * body of an issue.
 *
 * @param issueBodyUrl The API URL to get the issue where the secret was detected.
 * @param htmlUrl The GitHub URL for the issue where the secret was detected.
 */
@CatalogSchema("components/schemas/secret-scanning-location-issue-body")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationIssueBody(
        String issueBodyUrl, @Nullable String htmlUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationIssueBody {
        requireNonNull(issueBodyUrl, "issueBodyUrl");
    }
}
