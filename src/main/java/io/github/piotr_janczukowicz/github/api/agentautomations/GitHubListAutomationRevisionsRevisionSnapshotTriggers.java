package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Trigger configuration for the automation.
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
@CatalogSchema(
        "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationRevisionsRevisionSnapshotTriggers(
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersInterval interval,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueOpened issueOpened,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueLabeled issueLabeled,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersWorkflowRun workflowRun,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueComment issueComment,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersDiscussionComment discussionComment,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueAssigned issueAssigned,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersSubIssueAdded subIssueAdded,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReadyForReview pullRequestReadyForReview,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestLabeled pullRequestLabeled,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestMergeConflict pullRequestMergeConflict,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewRequested pullRequestReviewRequested,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueClosed issueClosed,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersIssueReopened issueReopened,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestClosed pullRequestClosed,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReopened pullRequestReopened,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestConvertedToDraft pullRequestConvertedToDraft,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewDismissed pullRequestReviewDismissed,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadResolved
                pullRequestReviewThreadResolved,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersPullRequestReviewThreadUnresolved
                pullRequestReviewThreadUnresolved,

        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationCompleted automationCompleted,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersAutomationFailed automationFailed,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersCodeScanningAlert codeScanningAlert,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersSecretScanningAlert secretScanningAlert,
        @Nullable GitHubListAutomationRevisionsRevisionSnapshotTriggersDependabotAlert dependabotAlert) {}
