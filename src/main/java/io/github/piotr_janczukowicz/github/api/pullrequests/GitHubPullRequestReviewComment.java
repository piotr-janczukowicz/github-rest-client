package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.issues.GitHubTimelineComment;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Pull request review comment. */
@CatalogSchema("components/schemas/pull-request-review-comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
// The implemented sealed interface carries the deduction; this type itself decodes as a plain record.
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public record GitHubPullRequestReviewComment(
        String url,
        @Nullable Long pullRequestReviewId,
        long id,
        String nodeId,
        String diffHunk,
        String path,
        @Nullable Integer position,
        @Nullable Integer originalPosition,
        String commitId,
        String originalCommitId,
        @Nullable Long inReplyToId,
        @Nullable OptionalNull<GitHubSimpleUser> user,
        String body,
        Instant createdAt,
        Instant updatedAt,
        String htmlUrl,
        String pullRequestUrl,
        GitHubAuthorAssociation authorAssociation,
        @JsonProperty("_links") GitHubPullRequestReviewCommentLinks links,
        @Nullable Integer startLine,
        @Nullable Integer originalStartLine,
        @Nullable GitHubPullRequestReviewCommentSide startSide,
        @Nullable Integer line,
        @Nullable Integer originalLine,
        @Nullable GitHubPullRequestReviewCommentSide side,
        @Nullable GitHubPullRequestReviewCommentSubjectType subjectType,
        @Nullable GitHubReactionRollup reactions,
        @Nullable String bodyHtml,
        @Nullable String bodyText,
        @Nullable List<GitHubPullRequestReviewCommentCopilotCodeReviewSource> copilotCodeReviewSources,
        @Nullable Boolean isCopilotCodeReview)
        implements GitHubTimelineComment {

    public GitHubPullRequestReviewComment {
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(diffHunk, "diffHunk");
        requireNonNull(path, "path");
        requireNonNull(commitId, "commitId");
        requireNonNull(originalCommitId, "originalCommitId");
        requireNonNull(body, "body");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(pullRequestUrl, "pullRequestUrl");
        requireNonNull(authorAssociation, "authorAssociation");
        requireNonNull(links, "links");
        if (copilotCodeReviewSources != null) {
            requireSizeAtMost(copilotCodeReviewSources, 16, "copilotCodeReviewSources");
        }
    }
}
