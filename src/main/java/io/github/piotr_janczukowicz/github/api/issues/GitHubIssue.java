package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueDependenciesSummary;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueFieldValue;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.search.GitHubSubIssuesSummary;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Issue.
 *
 * @param url Url
 * @param repositoryUrl Repository url
 * @param labelsUrl Labels url
 * @param commentsUrl Comments url
 * @param eventsUrl Events url
 * @param htmlUrl Html url
 * @param id Id
 * @param nodeId Node id
 * @param number Number
 * @param title Title
 * @param locked Locked
 * @param activeLockReason Active lock reason
 * @param assignees Assignees
 * @param user User
 * @param labels Labels
 * @param subIssuesSummary Sub issues summary
 * @param issueDependenciesSummary Issue dependencies summary
 * @param issueFieldValues Issue field values
 * @param state State
 * @param stateReason State reason
 * @param milestone Milestone
 * @param comments Comments
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param closedAt Closed at
 * @param pullRequest Pull request
 * @param body Body
 * @param authorAssociation Author association
 * @param draft Draft
 * @param closedBy Closed by
 * @param bodyHtml Body html
 * @param bodyText Body text
 * @param timelineUrl Timeline url
 * @param type Type
 * @param repository Repository
 * @param performedViaGithubApp Performed via github app
 * @param pinnedComment Pinned comment
 * @param reactions Reactions
 * @param parentIssueUrl Parent issue url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue")
public record GitHubIssue(
        String url,
        String repositoryUrl,
        String labelsUrl,
        String commentsUrl,
        String eventsUrl,
        String htmlUrl,
        long id,
        String nodeId,
        int number,
        String title,
        boolean locked,
        @Nullable String activeLockReason,
        @Nullable List<GitHubSimpleUser> assignees,
        @Nullable GitHubSimpleUser user,
        List<GitHubIssueLabel> labels,
        @Nullable GitHubSubIssuesSummary subIssuesSummary,
        @Nullable GitHubIssueDependenciesSummary issueDependenciesSummary,
        @Nullable List<GitHubIssueFieldValue> issueFieldValues,
        String state,
        @Nullable GitHubIssueStateReason stateReason,
        @Nullable GitHubMilestone milestone,
        int comments,
        Instant createdAt,
        Instant updatedAt,
        @Nullable Instant closedAt,
        @Nullable GitHubIssuePullRequest pullRequest,
        @Nullable String body,
        @Nullable GitHubAuthorAssociation authorAssociation,
        @Nullable Boolean draft,
        @Nullable GitHubSimpleUser closedBy,
        @Nullable String bodyHtml,
        @Nullable String bodyText,
        @Nullable String timelineUrl,
        @Nullable GitHubIssueType type,
        @Nullable GitHubRepository repository,
        @Nullable GitHubIntegration performedViaGithubApp,
        @Nullable GitHubIssueComment pinnedComment,
        @Nullable GitHubReactionRollup reactions,
        @Nullable String parentIssueUrl) {

    public GitHubIssue {
        requireNonNull(url, "url");
        requireNonNull(repositoryUrl, "repositoryUrl");
        requireNonNull(labelsUrl, "labelsUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(eventsUrl, "eventsUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(title, "title");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(labels, "labels");
    }
}
