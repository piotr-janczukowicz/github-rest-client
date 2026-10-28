package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeWebhook;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment.Minimized;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment.PinnedComment;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Timeline Event.
 *
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param actor Actor
 * @param event Event
 * @param commitId Commit id
 * @param commitUrl Commit url
 * @param createdAt Created at
 * @param label Label
 * @param assignee Assignee
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
 * @param links Links
 * @param author Author
 * @param body Body
 * @param bodyHtml Body html
 * @param bodyText Body text
 * @param comments Comments
 * @param committer Committer
 * @param htmlUrl Html url
 * @param issueUrl Issue url
 * @param message Message
 * @param minimized Minimized
 * @param parents Parents
 * @param pin Pin
 * @param pullRequestUrl Pull request url
 * @param reactions Reactions
 * @param sha Sha
 * @param source Source
 * @param state State
 * @param stateReason State reason
 * @param submittedAt Submitted at
 * @param tree Tree
 * @param updatedAt Updated at
 * @param user User
 * @param verification Verification
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({"components/schemas/timeline-issue-events", "operations/issues/list-events-for-timeline/200/items"})
@RecordBuilder
public record GitHubTimelineEvent(
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String url,
        @Nullable GitHubSimpleUser actor,
        @Nullable String event,
        @Nullable String commitId,
        @Nullable String commitUrl,
        @Nullable Instant createdAt,
        @Nullable GitHubIssueEventLabel label,
        @Nullable GitHubSimpleUser assignee,
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
        @Nullable GitHubIntegration performedViaGithubApp,
        @JsonProperty("_links") @Nullable GitHubTimelineEventLinks links,
        @Nullable GitHubTimelineGitUser author,
        @Nullable String body,
        @Nullable String bodyHtml,
        @Nullable String bodyText,
        @Nullable List<GitHubTimelineComment> comments,
        @Nullable GitHubTimelineGitUser committer,
        @Nullable String htmlUrl,
        @Nullable String issueUrl,
        @Nullable String message,
        @Nullable Minimized minimized,
        @Nullable List<GitHubTimelineCommitParent> parents,
        @Nullable PinnedComment pin,
        @Nullable String pullRequestUrl,
        @Nullable GitHubReactionRollup reactions,
        @Nullable String sha,
        @Nullable GitHubTimelineEventSource source,
        @Nullable String state,
        @Nullable String stateReason,
        @Nullable Instant submittedAt,
        @Nullable GitHubTimelineCommitTree tree,
        @Nullable Instant updatedAt,
        @Nullable GitHubSimpleUser user,
        @Nullable GitHubTimelineVerification verification) {}
