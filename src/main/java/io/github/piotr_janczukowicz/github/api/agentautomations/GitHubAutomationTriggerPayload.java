package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Trigger payload. Shared by the 1 schemas that have this shape: triggers.
 *
 * @param interval interval
 * @param issueOpened issue opened
 * @param issueLabeled issue labeled
 * @param workflowRun workflow run
 * @param pullRequestOpened pull request opened
 * @param pullRequestSynchronize pull request synchronize
 * @param pullRequestMerged pull request merged
 * @param issueComment issue comment
 * @param pullRequestComment pull request comment
 * @param discussionOpened discussion opened
 * @param discussionUpdated discussion updated
 * @param discussionComment discussion comment
 * @param issueAssigned issue assigned
 * @param pullRequestAssigned pull request assigned
 * @param subIssueAdded sub issue added
 * @param pullRequestReadyForReview pull request ready for review
 * @param pullRequestLabeled pull request labeled
 * @param pullRequestMergeConflict pull request merge conflict
 * @param pullRequestReviewSubmitted pull request review submitted
 * @param pullRequestReviewRequested pull request review requested
 * @param issueClosed issue closed
 * @param issueReopened issue reopened
 * @param pullRequestClosed pull request closed
 * @param pullRequestReopened pull request reopened
 * @param pullRequestConvertedToDraft pull request converted to draft
 * @param pullRequestReviewDismissed pull request review dismissed
 * @param pullRequestReviewThreadResolved pull request review thread resolved
 * @param pullRequestReviewThreadUnresolved pull request review thread unresolved
 * @param automationCompleted automation completed
 * @param automationFailed automation failed
 * @param codeScanningAlert Runs on the default branch when a security alert of this kind is created in the repository.
 *     These are system events without an issue, pull request, or human actor, and cannot be run manually.
 * @param secretScanningAlert Runs on the default branch when a security alert of this kind is created in the
 *     repository. These are system events without an issue, pull request, or human actor, and cannot be run manually.
 * @param dependabotAlert Runs on the default branch when a security alert of this kind is created in the repository.
 *     These are system events without an issue, pull request, or human actor, and cannot be run manually.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationTriggerPayload(
        @Nullable GitHubAutomationIntervalTriggerPayload interval,
        @Nullable GitHubAutomationIssueTriggerPayload issueOpened,
        @Nullable GitHubAutomationIssueLabeledTriggerPayload issueLabeled,
        @Nullable GitHubAutomationWorkflowRunTriggerPayload workflowRun,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestOpened,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestSynchronize,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestMerged,
        @Nullable GitHubAutomationIssueCommentTriggerPayload issueComment,
        @Nullable GitHubAutomationPullRequestCommentTriggerPayload pullRequestComment,
        @Nullable GitHubAutomationDiscussionTriggerPayload discussionOpened,
        @Nullable GitHubAutomationDiscussionTriggerPayload discussionUpdated,
        @Nullable GitHubAutomationDiscussionCommentTriggerPayload discussionComment,
        @Nullable GitHubAutomationIssueAssignedTriggerPayload issueAssigned,
        @Nullable GitHubAutomationPullRequestAssignedTriggerPayload pullRequestAssigned,
        @Nullable GitHubAutomationIssueTriggerPayload subIssueAdded,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestReadyForReview,
        @Nullable GitHubAutomationPullRequestLabeledTriggerPayload pullRequestLabeled,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestMergeConflict,
        @Nullable GitHubAutomationPullRequestReviewSubmittedTriggerPayload pullRequestReviewSubmitted,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestReviewRequested,
        @Nullable GitHubAutomationIssueTriggerPayload issueClosed,
        @Nullable GitHubAutomationIssueTriggerPayload issueReopened,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestClosed,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestReopened,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestConvertedToDraft,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestReviewDismissed,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestReviewThreadResolved,
        @Nullable GitHubAutomationPullRequestTriggerPayload pullRequestReviewThreadUnresolved,
        @Nullable GitHubAutomationCompletedTriggerPayload automationCompleted,
        @Nullable GitHubAutomationFailedTriggerPayload automationFailed,
        @Nullable GitHubAutomationAlertTriggerPayload codeScanningAlert,
        @Nullable GitHubAutomationAlertTriggerPayload secretScanningAlert,
        @Nullable GitHubAutomationAlertTriggerPayload dependabotAlert) {}
