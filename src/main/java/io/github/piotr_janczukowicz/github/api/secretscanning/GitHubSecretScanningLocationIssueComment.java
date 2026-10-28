package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Represents an 'issue_comment' secret scanning location type. This location type shows that a secret was detected in a
 * comment on an issue.
 *
 * @param issueCommentUrl The API URL to get the issue comment where the secret was detected.
 * @param htmlUrl The GitHub URL for the issue comment where the secret was detected.
 */
@CatalogSchema("components/schemas/secret-scanning-location-issue-comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationIssueComment(
        String issueCommentUrl, @Nullable String htmlUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationIssueComment {
        requireNonNull(issueCommentUrl, "issueCommentUrl");
    }
}
