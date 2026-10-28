package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeWebhook;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Issue Event.
 *
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param actor Actor
 * @param event Event
 * @param commitId Commit id
 * @param commitUrl Commit url
 * @param createdAt Created at
 * @param issue Issue
 * @param label Label
 * @param assignee Assignee
 * @param assigner Assigner
 * @param reviewRequester Review requester
 * @param requestedReviewer Requested reviewer
 * @param requestedTeam Requested team
 * @param dismissedReview Dismissed review
 * @param milestone Milestone
 * @param projectCard Project card
 * @param rename Rename
 * @param issueType Issue type
 * @param prevIssueType Prev issue type
 * @param subIssue Sub issue
 * @param parentIssue Parent issue
 * @param blockedBy Blocked by
 * @param blocking Blocking
 * @param intent Intent
 * @param authorAssociation Author association
 * @param lockReason Lock reason
 * @param performedViaGithubApp Performed via github app
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"components/schemas/issue-event", "components/schemas/issue-event-for-issue"})
@RecordBuilder
public record GitHubIssueEvent(
        long id,
        String nodeId,
        String url,
        @Nullable GitHubSimpleUser actor,
        String event,
        @Nullable String commitId,
        @Nullable String commitUrl,
        Instant createdAt,
        @Nullable GitHubIssue issue,
        @Nullable GitHubIssueEventLabel label,
        @Nullable GitHubSimpleUser assignee,
        @Nullable GitHubSimpleUser assigner,
        @Nullable GitHubSimpleUser reviewRequester,
        @Nullable GitHubSimpleUser requestedReviewer,
        @Nullable GitHubTeam requestedTeam,
        @Nullable GitHubIssueEventDismissedReview dismissedReview,
        @Nullable GitHubIssueEventMilestone milestone,
        @Nullable GitHubIssueEventProjectCard projectCard,
        @Nullable GitHubIssueEventRename rename,
        @Nullable GitHubIssueTypeWebhook issueType,
        @Nullable GitHubIssueTypeWebhook prevIssueType,
        @Nullable GitHubIssueReference subIssue,
        @Nullable GitHubIssueReference parentIssue,
        @Nullable GitHubIssueReference blockedBy,
        @Nullable GitHubIssueReference blocking,
        @Nullable GitHubIssueEventIntent intent,
        @Nullable GitHubAuthorAssociation authorAssociation,
        @Nullable String lockReason,
        @Nullable GitHubIntegration performedViaGithubApp) {

    public GitHubIssueEvent {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(event, "event");
        requireNonNull(createdAt, "createdAt");
    }
}
