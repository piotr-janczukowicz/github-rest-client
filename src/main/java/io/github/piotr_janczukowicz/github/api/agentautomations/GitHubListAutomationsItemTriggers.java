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
@CatalogSchema("operations/agent-automations/list-automations/200/items/properties/triggers")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationsItemTriggers(
        @Nullable GitHubListAutomationsItemTriggersInterval interval,
        @Nullable GitHubListAutomationsItemTriggersIssueOpened issueOpened,
        @Nullable GitHubListAutomationsItemTriggersIssueLabeled issueLabeled,
        @Nullable GitHubListAutomationsItemTriggersWorkflowRun workflowRun,
        @Nullable GitHubListAutomationsItemTriggersPullRequestOpened pullRequestOpened,
        @Nullable GitHubListAutomationsItemTriggersPullRequestSynchronize pullRequestSynchronize,
        @Nullable GitHubListAutomationsItemTriggersPullRequestMerged pullRequestMerged,
        @Nullable GitHubListAutomationsItemTriggersIssueComment issueComment,
        @Nullable GitHubListAutomationsItemTriggersPullRequestComment pullRequestComment,
        @Nullable GitHubListAutomationsItemTriggersDiscussionOpened discussionOpened,
        @Nullable GitHubListAutomationsItemTriggersDiscussionUpdated discussionUpdated,
        @Nullable GitHubListAutomationsItemTriggersDiscussionComment discussionComment,
        @Nullable GitHubListAutomationsItemTriggersIssueAssigned issueAssigned,
        @Nullable GitHubListAutomationsItemTriggersPullRequestAssigned pullRequestAssigned,
        @Nullable GitHubListAutomationsItemTriggersSubIssueAdded subIssueAdded,
        @Nullable GitHubListAutomationsItemTriggersPullRequestReadyForReview pullRequestReadyForReview,
        @Nullable GitHubListAutomationsItemTriggersPullRequestLabeled pullRequestLabeled,
        @Nullable GitHubListAutomationsItemTriggersPullRequestMergeConflict pullRequestMergeConflict,
        @Nullable GitHubListAutomationsItemTriggersPullRequestReviewSubmitted pullRequestReviewSubmitted,
        @Nullable GitHubListAutomationsItemTriggersPullRequestReviewRequested pullRequestReviewRequested,
        @Nullable GitHubListAutomationsItemTriggersAutomationCompleted automationCompleted) {}
