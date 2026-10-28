package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubAutoMerge;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestBranchRef;
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

/** Pull request. */
@CatalogSchema("components/schemas/pull-request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequest(
        String url,
        long id,
        String nodeId,
        String htmlUrl,
        String diffUrl,
        String patchUrl,
        String issueUrl,
        String commitsUrl,
        String reviewCommentsUrl,
        String reviewCommentUrl,
        String commentsUrl,
        String statusesUrl,
        int number,
        GitHubPullRequestUpdateState state,
        boolean locked,
        String title,
        GitHubSimpleUser user,
        @Nullable String body,
        List<GitHubPullRequestLabel> labels,
        @Nullable OptionalNull<GitHubMilestone> milestone,
        @Nullable String activeLockReason,
        Instant createdAt,
        Instant updatedAt,
        @Nullable OptionalNull<Instant> closedAt,
        @Nullable OptionalNull<Instant> mergedAt,
        @Nullable List<GitHubSimpleUser> assignees,
        @Nullable List<GitHubSimpleUser> requestedReviewers,
        @Nullable List<GitHubTeamSimple> requestedTeams,
        GitHubPullRequestBranchRef head,
        GitHubPullRequestBranchRef base,
        @JsonProperty("_links") GitHubPullRequestSimpleLinks links,
        GitHubAuthorAssociation authorAssociation,
        @Nullable OptionalNull<GitHubAutoMerge> autoMerge,
        @Nullable GitHubPullRequestStack stack,
        @Nullable Boolean draft,
        boolean merged,
        @Nullable OptionalNull<Boolean> mergeable,
        @Nullable Boolean rebaseable,
        String mergeableState,
        @Nullable OptionalNull<GitHubSimpleUser> mergedBy,
        int comments,
        int reviewComments,
        boolean maintainerCanModify,
        int commits,
        int additions,
        int deletions,
        int changedFiles) {

    public GitHubPullRequest {
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(diffUrl, "diffUrl");
        requireNonNull(patchUrl, "patchUrl");
        requireNonNull(issueUrl, "issueUrl");
        requireNonNull(commitsUrl, "commitsUrl");
        requireNonNull(reviewCommentsUrl, "reviewCommentsUrl");
        requireNonNull(reviewCommentUrl, "reviewCommentUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(statusesUrl, "statusesUrl");
        requireNonNull(state, "state");
        requireNonNull(title, "title");
        requireNonNull(user, "user");
        requireNonNull(labels, "labels");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(head, "head");
        requireNonNull(base, "base");
        requireNonNull(links, "links");
        requireNonNull(authorAssociation, "authorAssociation");
        requireNonNull(mergeableState, "mergeableState");
    }
}
