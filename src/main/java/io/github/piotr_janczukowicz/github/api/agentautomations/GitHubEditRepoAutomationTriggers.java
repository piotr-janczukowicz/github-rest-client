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
@CatalogSchema("operations/agent-automations/edit-repo-automation/requestBody/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationTriggers(
        @Nullable GitHubEditRepoAutomationTriggersInterval interval,
        @Nullable GitHubEditRepoAutomationTriggersIssueOpened issueOpened,
        @Nullable GitHubEditRepoAutomationTriggersIssueLabeled issueLabeled,
        @Nullable GitHubEditRepoAutomationTriggersWorkflowRun workflowRun,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubEditRepoAutomationTriggersIssueComment issueComment,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubEditRepoAutomationTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubEditRepoAutomationTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubEditRepoAutomationTriggersDiscussionComment discussionComment,
        @Nullable GitHubEditRepoAutomationTriggersIssueAssigned issueAssigned,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubEditRepoAutomationTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubEditRepoAutomationTriggersIssueClosed issueClosed,
        @Nullable GitHubEditRepoAutomationTriggersIssueReopened issueReopened,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestClosed pullRequestClosed,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReopened pullRequestReopened,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestConvertedToDraft pullRequestConvertedToDraft,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReviewDismissed pullRequestReviewDismissed,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReviewThreadResolved pullRequestReviewThreadResolved,
        @Nullable GitHubEditRepoAutomationTriggersPullRequestReviewThreadUnresolved pullRequestReviewThreadUnresolved,
        @Nullable GitHubEditRepoAutomationTriggersAutomationCompleted automationCompleted,
        @Nullable GitHubEditRepoAutomationTriggersAutomationFailed automationFailed,
        @Nullable GitHubEditRepoAutomationTriggersCodeScanningAlert codeScanningAlert,
        @Nullable GitHubEditRepoAutomationTriggersSecretScanningAlert secretScanningAlert,
        @Nullable GitHubEditRepoAutomationTriggersDependabotAlert dependabotAlert) {}
