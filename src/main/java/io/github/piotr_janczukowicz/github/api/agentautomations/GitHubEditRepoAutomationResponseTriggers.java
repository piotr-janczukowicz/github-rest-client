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
@CatalogSchema("operations/agent-automations/edit-repo-automation/200/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubEditRepoAutomationResponseTriggers(
        @Nullable GitHubEditRepoAutomationResponseTriggersInterval interval,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueOpened issueOpened,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueLabeled issueLabeled,
        @Nullable GitHubEditRepoAutomationResponseTriggersWorkflowRun workflowRun,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueComment issueComment,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubEditRepoAutomationResponseTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubEditRepoAutomationResponseTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubEditRepoAutomationResponseTriggersDiscussionComment discussionComment,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueAssigned issueAssigned,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubEditRepoAutomationResponseTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueClosed issueClosed,
        @Nullable GitHubEditRepoAutomationResponseTriggersIssueReopened issueReopened,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestClosed pullRequestClosed,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReopened pullRequestReopened,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestConvertedToDraft pullRequestConvertedToDraft,
        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReviewDismissed pullRequestReviewDismissed,

        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReviewThreadResolved pullRequestReviewThreadResolved,

        @Nullable GitHubEditRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved pullRequestReviewThreadUnresolved,

        @Nullable GitHubEditRepoAutomationResponseTriggersAutomationCompleted automationCompleted,
        @Nullable GitHubEditRepoAutomationResponseTriggersAutomationFailed automationFailed,
        @Nullable GitHubEditRepoAutomationResponseTriggersCodeScanningAlert codeScanningAlert,
        @Nullable GitHubEditRepoAutomationResponseTriggersSecretScanningAlert secretScanningAlert,
        @Nullable GitHubEditRepoAutomationResponseTriggersDependabotAlert dependabotAlert) {}
