package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Represents a 'discussion_comment' secret scanning location type. This location type shows that a secret was detected
 * in a comment on a discussion.
 *
 * @param discussionCommentUrl The API URL to get the discussion comment where the secret was detected.
 */
@CatalogSchema("components/schemas/secret-scanning-location-discussion-comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationDiscussionComment(String discussionCommentUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationDiscussionComment {
        requireNonNull(discussionCommentUrl, "discussionCommentUrl");
    }
}
