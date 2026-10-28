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
@CatalogSchema("operations/agent-automations/get-repo-automation/200/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetRepoAutomationResponseTriggers(
        @Nullable GitHubGetRepoAutomationResponseTriggersInterval interval,
        @Nullable GitHubGetRepoAutomationResponseTriggersIssueOpened issueOpened,
        @Nullable GitHubGetRepoAutomationResponseTriggersIssueLabeled issueLabeled,
        @Nullable GitHubGetRepoAutomationResponseTriggersWorkflowRun workflowRun,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubGetRepoAutomationResponseTriggersIssueComment issueComment,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubGetRepoAutomationResponseTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubGetRepoAutomationResponseTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubGetRepoAutomationResponseTriggersDiscussionComment discussionComment,
        @Nullable GitHubGetRepoAutomationResponseTriggersIssueAssigned issueAssigned,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubGetRepoAutomationResponseTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubGetRepoAutomationResponseTriggersAutomationCompleted automationCompleted) {}
