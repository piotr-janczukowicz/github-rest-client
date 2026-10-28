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
@CatalogSchema("operations/agent-automations/create-repo-automation/requestBody/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateRepoAutomationTriggers(
        @Nullable GitHubCreateRepoAutomationTriggersInterval interval,
        @Nullable GitHubCreateRepoAutomationTriggersIssueOpened issueOpened,
        @Nullable GitHubCreateRepoAutomationTriggersIssueLabeled issueLabeled,
        @Nullable GitHubCreateRepoAutomationTriggersWorkflowRun workflowRun,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubCreateRepoAutomationTriggersIssueComment issueComment,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubCreateRepoAutomationTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubCreateRepoAutomationTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubCreateRepoAutomationTriggersDiscussionComment discussionComment,
        @Nullable GitHubCreateRepoAutomationTriggersIssueAssigned issueAssigned,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubCreateRepoAutomationTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubCreateRepoAutomationTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubCreateRepoAutomationTriggersAutomationCompleted automationCompleted) {}
