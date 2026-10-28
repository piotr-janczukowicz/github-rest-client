package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/** Pull request review comment. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/pull-request-review-comment-event/properties/comment")
@RecordBuilder
public record GitHubPullRequestReviewCommentEventComment(
        long id,
        String nodeId,
        String url,
        @Nullable Long pullRequestReviewId,
        String diffHunk,
        String path,
        @Nullable Integer position,
        @Nullable Integer originalPosition,
        @Nullable String subjectType,
        String commitId,
        @Nullable GitHubPullRequestReviewCommentEventCommentUser user,
        String body,
        Instant createdAt,
        Instant updatedAt,
        String htmlUrl,
        String pullRequestUrl,
        @JsonProperty("_links") GitHubPullRequestReviewCommentEventCommentLinks links,
        String originalCommitId,
        GitHubPullRequestReviewCommentEventCommentReactions reactions,
        @Nullable Long inReplyToId,
        @Nullable String authorAssociation,
        @Nullable Integer startLine,
        @Nullable Integer originalStartLine,
        @Nullable String startSide,
        @Nullable Integer line,
        @Nullable Integer originalLine,
        @Nullable String side) {
    public GitHubPullRequestReviewCommentEventComment {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(diffHunk, "diffHunk");
        requireNonNull(path, "path");
        requireNonNull(commitId, "commitId");
        requireNonNull(body, "body");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(pullRequestUrl, "pullRequestUrl");
        requireNonNull(links, "links");
        requireNonNull(originalCommitId, "originalCommitId");
        requireNonNull(reactions, "reactions");
    }
}
