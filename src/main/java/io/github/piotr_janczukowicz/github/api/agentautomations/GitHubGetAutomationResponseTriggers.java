package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Public trigger configuration keyed by stable public trigger identifier.
 *
 * @param interval Interval
 * @param issueOpened Issue opened
 * @param issueLabeled Issue labeled
 * @param workflowRun Workflow run
 * @param pullRequestOpened Pull request opened
 * @param pullRequestSynchronize Pull request synchronize
 * @param pullRequestMerged Pull request merged
 * @param issueComment Issue comment
 * @param pullRequestComment Pull request comment
 * @param discussionOpened Discussion opened
 * @param discussionUpdated Discussion updated
 * @param discussionComment Discussion comment
 * @param issueAssigned Issue assigned
 * @param pullRequestAssigned Pull request assigned
 * @param subIssueAdded Sub issue added
 * @param pullRequestReadyForReview Pull request ready for review
 * @param pullRequestLabeled Pull request labeled
 * @param pullRequestMergeConflict Pull request merge conflict
 * @param pullRequestReviewSubmitted Pull request review submitted
 * @param pullRequestReviewRequested Pull request review requested
 * @param automationCompleted Automation completed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/get-automation/200/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAutomationResponseTriggers(
        @Nullable GitHubGetAutomationResponseTriggersInterval interval,
        @Nullable GitHubGetAutomationResponseTriggersIssueOpened issueOpened,
        @Nullable GitHubGetAutomationResponseTriggersIssueLabeled issueLabeled,
        @Nullable GitHubGetAutomationResponseTriggersWorkflowRun workflowRun,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubGetAutomationResponseTriggersIssueComment issueComment,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubGetAutomationResponseTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubGetAutomationResponseTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubGetAutomationResponseTriggersDiscussionComment discussionComment,
        @Nullable GitHubGetAutomationResponseTriggersIssueAssigned issueAssigned,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubGetAutomationResponseTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubGetAutomationResponseTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubGetAutomationResponseTriggersAutomationCompleted automationCompleted) {}
