package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestStack;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestUpdateState;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubAutoMerge;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestLabel;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestSimpleLinks;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The pull request of a pull request event: the minimal object on GitHub.com and GitHub Enterprise Cloud, the full pull
 * request on GitHub Enterprise Server. A property only the full pull request has is nullable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/pull-request-event/properties/pull_request",
    "components/schemas/pull-request-review-event/properties/pull_request",
    "components/schemas/pull-request-review-comment-event/properties/pull_request"
})
@RecordBuilder
public record GitHubPullRequestEventPullRequest(
        String url,
        long id,
        @Nullable String nodeId,
        @Nullable String htmlUrl,
        @Nullable String diffUrl,
        @Nullable String patchUrl,
        @Nullable String issueUrl,
        @Nullable String commitsUrl,
        @Nullable String reviewCommentsUrl,
        @Nullable String reviewCommentUrl,
        @Nullable String commentsUrl,
        @Nullable String statusesUrl,
        int number,
        @Nullable GitHubPullRequestUpdateState state,
        @Nullable Boolean locked,
        @Nullable String title,
        @Nullable GitHubSimpleUser user,
        @Nullable String body,
        @Nullable List<GitHubPullRequestLabel> labels,
        @Nullable OptionalNull<GitHubMilestone> milestone,
        @Nullable String activeLockReason,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable OptionalNull<Instant> closedAt,
        @Nullable OptionalNull<Instant> mergedAt,
        @Nullable List<GitHubSimpleUser> assignees,
        @Nullable List<GitHubSimpleUser> requestedReviewers,
        @Nullable List<GitHubTeamSimple> requestedTeams,
        GitHubPullRequestEventPullRequestRef head,
        GitHubPullRequestEventPullRequestRef base,
        @Nullable @JsonProperty("_links") GitHubPullRequestSimpleLinks links,
        @Nullable GitHubAuthorAssociation authorAssociation,
        @Nullable OptionalNull<GitHubAutoMerge> autoMerge,
        @Nullable GitHubPullRequestStack stack,
        @Nullable Boolean draft,
        @Nullable Boolean merged,
        @Nullable OptionalNull<Boolean> mergeable,
        @Nullable Boolean rebaseable,
        @Nullable String mergeableState,
        @Nullable OptionalNull<GitHubSimpleUser> mergedBy,
        @Nullable Integer comments,
        @Nullable Integer reviewComments,
        @Nullable Boolean maintainerCanModify,
        @Nullable Integer commits,
        @Nullable Integer additions,
        @Nullable Integer deletions,
        @Nullable Integer changedFiles) {
    public GitHubPullRequestEventPullRequest {
        requireNonNull(url, "url");
        requireNonNull(head, "head");
        requireNonNull(base, "base");
    }
}
