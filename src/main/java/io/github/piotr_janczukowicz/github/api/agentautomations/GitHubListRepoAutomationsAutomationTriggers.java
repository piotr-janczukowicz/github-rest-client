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
@CatalogSchema(
        "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListRepoAutomationsAutomationTriggers(
        @Nullable GitHubListRepoAutomationsAutomationTriggersInterval interval,
        @Nullable GitHubListRepoAutomationsAutomationTriggersIssueOpened issueOpened,
        @Nullable GitHubListRepoAutomationsAutomationTriggersIssueLabeled issueLabeled,
        @Nullable GitHubListRepoAutomationsAutomationTriggersWorkflowRun workflowRun,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubListRepoAutomationsAutomationTriggersIssueComment issueComment,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubListRepoAutomationsAutomationTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubListRepoAutomationsAutomationTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubListRepoAutomationsAutomationTriggersDiscussionComment discussionComment,
        @Nullable GitHubListRepoAutomationsAutomationTriggersIssueAssigned issueAssigned,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubListRepoAutomationsAutomationTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubListRepoAutomationsAutomationTriggersAutomationCompleted automationCompleted) {}
