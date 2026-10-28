package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Represents a 'pull_request_review_comment' secret scanning location type. This location type shows that a secret was
 * detected in a review comment on a pull request.
 *
 * @param pullRequestReviewCommentUrl The API URL to get the pull request review comment where the secret was detected.
 * @param htmlUrl The GitHub URL for the pull request review comment where the secret was detected.
 */
@CatalogSchema("components/schemas/secret-scanning-location-pull-request-review-comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationPullRequestReviewComment(
        String pullRequestReviewCommentUrl, @Nullable String htmlUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationPullRequestReviewComment {
        requireNonNull(pullRequestReviewCommentUrl, "pullRequestReviewCommentUrl");
    }
}
