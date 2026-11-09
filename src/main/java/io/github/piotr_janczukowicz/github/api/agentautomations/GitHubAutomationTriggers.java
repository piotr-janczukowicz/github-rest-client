package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Triggers. Shared by the 5 schemas that have this shape: automation_triggers, item_triggers,
 * revision_snapshot_triggers, snapshot_triggers, triggers.
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
    "operations/agent-automations/create-repo-automation/201/properties/triggers",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers",
    "operations/agent-automations/get-repo-automation/200/properties/triggers",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers",
    "operations/agent-automations/list-automations/200/items/properties/triggers",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAutomationTriggers(
        @Nullable GitHubAutomationIntervalTrigger interval,
        @Nullable GitHubAutomationIssueTrigger issueOpened,
        @Nullable GitHubAutomationIssueLabeledTrigger issueLabeled,
        @Nullable GitHubAutomationWorkflowRunTrigger workflowRun,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestOpened,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestSynchronize,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestMerged,
        @Nullable GitHubAutomationIssueCommentTrigger issueComment,
        @Nullable GitHubAutomationPullRequestCommentTrigger pullRequestComment,
        @Nullable GitHubAutomationDiscussionTrigger discussionOpened,
        @Nullable GitHubAutomationDiscussionTrigger discussionUpdated,
        @Nullable GitHubAutomationDiscussionCommentTrigger discussionComment,
        @Nullable GitHubAutomationIssueAssignedTrigger issueAssigned,
        @Nullable GitHubAutomationPullRequestAssignedTrigger pullRequestAssigned,
        @Nullable GitHubAutomationIssueTrigger subIssueAdded,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestReadyForReview,
        @Nullable GitHubAutomationPullRequestLabeledTrigger pullRequestLabeled,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestMergeConflict,
        @Nullable GitHubAutomationPullRequestReviewSubmittedTrigger pullRequestReviewSubmitted,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestReviewRequested,
        @Nullable GitHubAutomationIssueTrigger issueClosed,
        @Nullable GitHubAutomationIssueTrigger issueReopened,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestClosed,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestReopened,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestConvertedToDraft,
        @Nullable GitHubAutomationPullRequestTrigger pullRequestReviewDismissed,

        @Nullable GitHubAutomationPullRequestTrigger pullRequestReviewThreadResolved,

        @Nullable GitHubAutomationPullRequestTrigger pullRequestReviewThreadUnresolved,

        @Nullable GitHubAutomationCompletedTrigger automationCompleted,
        @Nullable GitHubAutomationFailedTrigger automationFailed,
        @Nullable GitHubAutomationAlertTrigger codeScanningAlert,
        @Nullable GitHubAutomationAlertTrigger secretScanningAlert,
        @Nullable GitHubAutomationAlertTrigger dependabotAlert) {}
