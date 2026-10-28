package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestStack;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Pull request simple. */
@CatalogSchema("components/schemas/pull-request-simple")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestSimple(
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
        String state,
        boolean locked,
        String title,
        @Nullable OptionalNull<GitHubSimpleUser> user,
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
        @Nullable List<GitHubTeam> requestedTeams,
        GitHubPullRequestBranchRef head,
        GitHubPullRequestBranchRef base,
        @JsonProperty("_links") GitHubPullRequestSimpleLinks links,
        GitHubAuthorAssociation authorAssociation,
        @Nullable OptionalNull<GitHubAutoMerge> autoMerge,
        @Nullable GitHubPullRequestStack stack,
        @Nullable Boolean draft) {
    public GitHubPullRequestSimple {
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
        requireNonNull(labels, "labels");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(head, "head");
        requireNonNull(base, "base");
        requireNonNull(links, "links");
        requireNonNull(authorAssociation, "authorAssociation");
    }
}
