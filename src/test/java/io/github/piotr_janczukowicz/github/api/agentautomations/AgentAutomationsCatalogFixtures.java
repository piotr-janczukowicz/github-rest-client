package io.github.piotr_janczukowicz.github.api.agentautomations;

import java.time.Instant;
import java.util.List;

final class AgentAutomationsCatalogFixtures {

    private AgentAutomationsCatalogFixtures() {}

    static GitHubListAutomationsItem listAutomationsItem() {
        return new GitHubListAutomationsItem(
                "automation-1",
                listAutomationsItemRepository(),
                "Nightly triage",
                true,
                listAutomationsItemOwnerType(),
                listAutomationsItemWriteScope(),
                true,
                "gpt-5",
                "medium",
                listAutomationsItemTriggers(),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                listAutomationsItemCreatedBy());
    }

    static GitHubListAutomationsItemRepository listAutomationsItemRepository() {
        return new GitHubListAutomationsItemRepository(1L, "octocat", "Nightly triage");
    }

    static GitHubListAutomationsItemOwnerType listAutomationsItemOwnerType() {
        return GitHubListAutomationsItemOwnerType.USER;
    }

    static GitHubListAutomationsItemWriteScope listAutomationsItemWriteScope() {
        return GitHubListAutomationsItemWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggers listAutomationsItemTriggers() {
        return new GitHubListAutomationsItemTriggers(
                listAutomationsItemTriggersInterval(),
                listAutomationsItemTriggersIssueOpened(),
                listAutomationsItemTriggersIssueLabeled(),
                listAutomationsItemTriggersWorkflowRun(),
                listAutomationsItemTriggersPullRequestOpened(),
                listAutomationsItemTriggersPullRequestSynchronize(),
                listAutomationsItemTriggersPullRequestMerged(),
                listAutomationsItemTriggersIssueComment(),
                listAutomationsItemTriggersPullRequestComment(),
                listAutomationsItemTriggersDiscussionOpened(),
                listAutomationsItemTriggersDiscussionUpdated(),
                listAutomationsItemTriggersDiscussionComment(),
                listAutomationsItemTriggersIssueAssigned(),
                listAutomationsItemTriggersPullRequestAssigned(),
                listAutomationsItemTriggersSubIssueAdded(),
                listAutomationsItemTriggersPullRequestReadyForReview(),
                listAutomationsItemTriggersPullRequestLabeled(),
                listAutomationsItemTriggersPullRequestMergeConflict(),
                listAutomationsItemTriggersPullRequestReviewSubmitted(),
                listAutomationsItemTriggersPullRequestReviewRequested(),
                listAutomationsItemTriggersAutomationCompleted());
    }

    static GitHubListAutomationsItemTriggersInterval listAutomationsItemTriggersInterval() {
        return new GitHubListAutomationsItemTriggersInterval(
                List.of(listAutomationsItemTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                listAutomationsItemTriggersIntervalWriteScope());
    }

    static GitHubListAutomationsItemTriggersIntervalCadence listAutomationsItemTriggersIntervalCadence() {
        return GitHubListAutomationsItemTriggersIntervalCadence.HOURLY;
    }

    static GitHubListAutomationsItemTriggersIntervalWriteScope listAutomationsItemTriggersIntervalWriteScope() {
        return GitHubListAutomationsItemTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersIssueOpened listAutomationsItemTriggersIssueOpened() {
        return new GitHubListAutomationsItemTriggersIssueOpened(
                "is:open label:bug", listAutomationsItemTriggersIssueOpenedWriteScope());
    }

    static GitHubListAutomationsItemTriggersIssueOpenedWriteScope listAutomationsItemTriggersIssueOpenedWriteScope() {
        return GitHubListAutomationsItemTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersIssueLabeled listAutomationsItemTriggersIssueLabeled() {
        return new GitHubListAutomationsItemTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), listAutomationsItemTriggersIssueLabeledWriteScope());
    }

    static GitHubListAutomationsItemTriggersIssueLabeledWriteScope listAutomationsItemTriggersIssueLabeledWriteScope() {
        return GitHubListAutomationsItemTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersWorkflowRun listAutomationsItemTriggersWorkflowRun() {
        return new GitHubListAutomationsItemTriggersWorkflowRun(
                List.of("CI"),
                List.of(listAutomationsItemTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                listAutomationsItemTriggersWorkflowRunWriteScope());
    }

    static GitHubListAutomationsItemTriggersWorkflowRunConclusion listAutomationsItemTriggersWorkflowRunConclusion() {
        return GitHubListAutomationsItemTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubListAutomationsItemTriggersWorkflowRunWriteScope listAutomationsItemTriggersWorkflowRunWriteScope() {
        return GitHubListAutomationsItemTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestOpened listAutomationsItemTriggersPullRequestOpened() {
        return new GitHubListAutomationsItemTriggersPullRequestOpened(
                "is:open label:bug", List.of("src/**"), listAutomationsItemTriggersPullRequestOpenedWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestOpenedWriteScope
            listAutomationsItemTriggersPullRequestOpenedWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestSynchronize listAutomationsItemTriggersPullRequestSynchronize() {
        return new GitHubListAutomationsItemTriggersPullRequestSynchronize(
                "is:open label:bug", List.of("src/**"), listAutomationsItemTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestSynchronizeWriteScope
            listAutomationsItemTriggersPullRequestSynchronizeWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestMerged listAutomationsItemTriggersPullRequestMerged() {
        return new GitHubListAutomationsItemTriggersPullRequestMerged(
                "is:open label:bug", List.of("src/**"), listAutomationsItemTriggersPullRequestMergedWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestMergedWriteScope
            listAutomationsItemTriggersPullRequestMergedWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersIssueComment listAutomationsItemTriggersIssueComment() {
        return new GitHubListAutomationsItemTriggersIssueComment(
                "/triage", listAutomationsItemTriggersIssueCommentWriteScope());
    }

    static GitHubListAutomationsItemTriggersIssueCommentWriteScope listAutomationsItemTriggersIssueCommentWriteScope() {
        return GitHubListAutomationsItemTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestComment listAutomationsItemTriggersPullRequestComment() {
        return new GitHubListAutomationsItemTriggersPullRequestComment(
                List.of(listAutomationsItemTriggersPullRequestCommentCommentType()),
                "/triage",
                listAutomationsItemTriggersPullRequestCommentWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestCommentCommentType
            listAutomationsItemTriggersPullRequestCommentCommentType() {
        return GitHubListAutomationsItemTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubListAutomationsItemTriggersPullRequestCommentWriteScope
            listAutomationsItemTriggersPullRequestCommentWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersDiscussionOpened listAutomationsItemTriggersDiscussionOpened() {
        return new GitHubListAutomationsItemTriggersDiscussionOpened(
                List.of("General"), "/triage", listAutomationsItemTriggersDiscussionOpenedWriteScope());
    }

    static GitHubListAutomationsItemTriggersDiscussionOpenedWriteScope
            listAutomationsItemTriggersDiscussionOpenedWriteScope() {
        return GitHubListAutomationsItemTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersDiscussionUpdated listAutomationsItemTriggersDiscussionUpdated() {
        return new GitHubListAutomationsItemTriggersDiscussionUpdated(
                List.of("General"), "/triage", listAutomationsItemTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubListAutomationsItemTriggersDiscussionUpdatedWriteScope
            listAutomationsItemTriggersDiscussionUpdatedWriteScope() {
        return GitHubListAutomationsItemTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersDiscussionComment listAutomationsItemTriggersDiscussionComment() {
        return new GitHubListAutomationsItemTriggersDiscussionComment(
                List.of(listAutomationsItemTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                listAutomationsItemTriggersDiscussionCommentWriteScope());
    }

    static GitHubListAutomationsItemTriggersDiscussionCommentCommentType
            listAutomationsItemTriggersDiscussionCommentCommentType() {
        return GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubListAutomationsItemTriggersDiscussionCommentWriteScope
            listAutomationsItemTriggersDiscussionCommentWriteScope() {
        return GitHubListAutomationsItemTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersIssueAssigned listAutomationsItemTriggersIssueAssigned() {
        return new GitHubListAutomationsItemTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), listAutomationsItemTriggersIssueAssignedWriteScope());
    }

    static GitHubListAutomationsItemTriggersIssueAssignedWriteScope
            listAutomationsItemTriggersIssueAssignedWriteScope() {
        return GitHubListAutomationsItemTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestAssigned listAutomationsItemTriggersPullRequestAssigned() {
        return new GitHubListAutomationsItemTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                listAutomationsItemTriggersPullRequestAssignedWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestAssignedWriteScope
            listAutomationsItemTriggersPullRequestAssignedWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersSubIssueAdded listAutomationsItemTriggersSubIssueAdded() {
        return new GitHubListAutomationsItemTriggersSubIssueAdded(
                "is:open label:bug", listAutomationsItemTriggersSubIssueAddedWriteScope());
    }

    static GitHubListAutomationsItemTriggersSubIssueAddedWriteScope
            listAutomationsItemTriggersSubIssueAddedWriteScope() {
        return GitHubListAutomationsItemTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestReadyForReview
            listAutomationsItemTriggersPullRequestReadyForReview() {
        return new GitHubListAutomationsItemTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                listAutomationsItemTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestReadyForReviewWriteScope
            listAutomationsItemTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestLabeled listAutomationsItemTriggersPullRequestLabeled() {
        return new GitHubListAutomationsItemTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                listAutomationsItemTriggersPullRequestLabeledWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestLabeledWriteScope
            listAutomationsItemTriggersPullRequestLabeledWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestMergeConflict
            listAutomationsItemTriggersPullRequestMergeConflict() {
        return new GitHubListAutomationsItemTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                listAutomationsItemTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestMergeConflictWriteScope
            listAutomationsItemTriggersPullRequestMergeConflictWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewSubmitted
            listAutomationsItemTriggersPullRequestReviewSubmitted() {
        return new GitHubListAutomationsItemTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(listAutomationsItemTriggersPullRequestReviewSubmittedState()),
                listAutomationsItemTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState
            listAutomationsItemTriggersPullRequestReviewSubmittedState() {
        return GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewSubmittedWriteScope
            listAutomationsItemTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewRequested
            listAutomationsItemTriggersPullRequestReviewRequested() {
        return new GitHubListAutomationsItemTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                listAutomationsItemTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewRequestedWriteScope
            listAutomationsItemTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubListAutomationsItemTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemTriggersAutomationCompleted listAutomationsItemTriggersAutomationCompleted() {
        return new GitHubListAutomationsItemTriggersAutomationCompleted(
                List.of("automation-2"), listAutomationsItemTriggersAutomationCompletedWriteScope());
    }

    static GitHubListAutomationsItemTriggersAutomationCompletedWriteScope
            listAutomationsItemTriggersAutomationCompletedWriteScope() {
        return GitHubListAutomationsItemTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubListAutomationsItemCreatedBy listAutomationsItemCreatedBy() {
        return new GitHubListAutomationsItemCreatedBy(1L);
    }

    static GitHubListAvailableAutomationToolsItem listAvailableAutomationToolsItem() {
        return new GitHubListAvailableAutomationToolsItem(
                "automation-1", "Issue tools", List.of(listAvailableAutomationToolsItemTool()));
    }

    static GitHubListAvailableAutomationToolsItemTool listAvailableAutomationToolsItemTool() {
        return new GitHubListAvailableAutomationToolsItemTool(
                "automation-1",
                "Issue tools",
                "Reads issues and pull requests.",
                listAvailableAutomationToolsItemToolScope());
    }

    static GitHubListAvailableAutomationToolsItemToolScope listAvailableAutomationToolsItemToolScope() {
        return GitHubListAvailableAutomationToolsItemToolScope.READ;
    }

    static GitHubListAvailableAutomationTriggersItem listAvailableAutomationTriggersItem() {
        return new GitHubListAvailableAutomationTriggersItem(
                "automation-1",
                "Issue tools",
                "Reads issues and pull requests.",
                List.of(listAvailableAutomationTriggersItemField()),
                true);
    }

    static GitHubListAvailableAutomationTriggersItemField listAvailableAutomationTriggersItemField() {
        return new GitHubListAvailableAutomationTriggersItemField(
                "automation-1",
                "Branch",
                true,
                "Reads issues and pull requests.",
                listAvailableAutomationTriggersItemFieldSchemaString());
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaString
            listAvailableAutomationTriggersItemFieldSchemaString() {
        return new GitHubListAvailableAutomationTriggersItemFieldSchemaString(
                listAvailableAutomationTriggersItemFieldSchemaStringType(), List.of("low", "high"), 1);
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaStringType
            listAvailableAutomationTriggersItemFieldSchemaStringType() {
        return GitHubListAvailableAutomationTriggersItemFieldSchemaStringType.STRING;
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaArray
            listAvailableAutomationTriggersItemFieldSchemaArray() {
        return new GitHubListAvailableAutomationTriggersItemFieldSchemaArray(
                listAvailableAutomationTriggersItemFieldSchemaArrayType(),
                listAvailableAutomationTriggersItemFieldSchemaArrayItems(),
                1,
                1,
                true);
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaArrayType
            listAvailableAutomationTriggersItemFieldSchemaArrayType() {
        return GitHubListAvailableAutomationTriggersItemFieldSchemaArrayType.ARRAY;
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItems
            listAvailableAutomationTriggersItemFieldSchemaArrayItems() {
        return new GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItems(
                listAvailableAutomationTriggersItemFieldSchemaArrayItemsType(), List.of("low", "high"), 1);
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsType
            listAvailableAutomationTriggersItemFieldSchemaArrayItemsType() {
        return GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsType.STRING;
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaInteger
            listAvailableAutomationTriggersItemFieldSchemaInteger() {
        return new GitHubListAvailableAutomationTriggersItemFieldSchemaInteger(
                listAvailableAutomationTriggersItemFieldSchemaIntegerType(), List.of(1, 2), 0, 10);
    }

    static GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerType
            listAvailableAutomationTriggersItemFieldSchemaIntegerType() {
        return GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerType.INTEGER;
    }

    static GitHubGetAutomationResponse getAutomationResponse() {
        return new GitHubGetAutomationResponse(
                "automation-1",
                getAutomationResponseRepository(),
                "Nightly triage",
                true,
                getAutomationResponseOwnerType(),
                getAutomationResponseWriteScope(),
                true,
                getAutomationResponseTriggers(),
                "Triage new issues and label them.",
                List.of("issues_read"),
                List.of("triage-agent"),
                "gpt-5",
                "medium",
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                getAutomationResponseCreatedBy());
    }

    static GitHubGetAutomationResponseRepository getAutomationResponseRepository() {
        return new GitHubGetAutomationResponseRepository(1L, "octocat", "Nightly triage");
    }

    static GitHubGetAutomationResponseOwnerType getAutomationResponseOwnerType() {
        return GitHubGetAutomationResponseOwnerType.USER;
    }

    static GitHubGetAutomationResponseWriteScope getAutomationResponseWriteScope() {
        return GitHubGetAutomationResponseWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggers getAutomationResponseTriggers() {
        return new GitHubGetAutomationResponseTriggers(
                getAutomationResponseTriggersInterval(),
                getAutomationResponseTriggersIssueOpened(),
                getAutomationResponseTriggersIssueLabeled(),
                getAutomationResponseTriggersWorkflowRun(),
                getAutomationResponseTriggersPullRequestOpened(),
                getAutomationResponseTriggersPullRequestSynchronize(),
                getAutomationResponseTriggersPullRequestMerged(),
                getAutomationResponseTriggersIssueComment(),
                getAutomationResponseTriggersPullRequestComment(),
                getAutomationResponseTriggersDiscussionOpened(),
                getAutomationResponseTriggersDiscussionUpdated(),
                getAutomationResponseTriggersDiscussionComment(),
                getAutomationResponseTriggersIssueAssigned(),
                getAutomationResponseTriggersPullRequestAssigned(),
                getAutomationResponseTriggersSubIssueAdded(),
                getAutomationResponseTriggersPullRequestReadyForReview(),
                getAutomationResponseTriggersPullRequestLabeled(),
                getAutomationResponseTriggersPullRequestMergeConflict(),
                getAutomationResponseTriggersPullRequestReviewSubmitted(),
                getAutomationResponseTriggersPullRequestReviewRequested(),
                getAutomationResponseTriggersAutomationCompleted());
    }

    static GitHubGetAutomationResponseTriggersInterval getAutomationResponseTriggersInterval() {
        return new GitHubGetAutomationResponseTriggersInterval(
                List.of(getAutomationResponseTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                getAutomationResponseTriggersIntervalWriteScope());
    }

    static GitHubGetAutomationResponseTriggersIntervalCadence getAutomationResponseTriggersIntervalCadence() {
        return GitHubGetAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubGetAutomationResponseTriggersIntervalWriteScope getAutomationResponseTriggersIntervalWriteScope() {
        return GitHubGetAutomationResponseTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersIssueOpened getAutomationResponseTriggersIssueOpened() {
        return new GitHubGetAutomationResponseTriggersIssueOpened(
                "is:open label:bug", getAutomationResponseTriggersIssueOpenedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersIssueOpenedWriteScope
            getAutomationResponseTriggersIssueOpenedWriteScope() {
        return GitHubGetAutomationResponseTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersIssueLabeled getAutomationResponseTriggersIssueLabeled() {
        return new GitHubGetAutomationResponseTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), getAutomationResponseTriggersIssueLabeledWriteScope());
    }

    static GitHubGetAutomationResponseTriggersIssueLabeledWriteScope
            getAutomationResponseTriggersIssueLabeledWriteScope() {
        return GitHubGetAutomationResponseTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersWorkflowRun getAutomationResponseTriggersWorkflowRun() {
        return new GitHubGetAutomationResponseTriggersWorkflowRun(
                List.of("CI"),
                List.of(getAutomationResponseTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                getAutomationResponseTriggersWorkflowRunWriteScope());
    }

    static GitHubGetAutomationResponseTriggersWorkflowRunConclusion
            getAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubGetAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubGetAutomationResponseTriggersWorkflowRunWriteScope
            getAutomationResponseTriggersWorkflowRunWriteScope() {
        return GitHubGetAutomationResponseTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestOpened getAutomationResponseTriggersPullRequestOpened() {
        return new GitHubGetAutomationResponseTriggersPullRequestOpened(
                "is:open label:bug", List.of("src/**"), getAutomationResponseTriggersPullRequestOpenedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestOpenedWriteScope
            getAutomationResponseTriggersPullRequestOpenedWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestSynchronize
            getAutomationResponseTriggersPullRequestSynchronize() {
        return new GitHubGetAutomationResponseTriggersPullRequestSynchronize(
                "is:open label:bug",
                List.of("src/**"),
                getAutomationResponseTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestSynchronizeWriteScope
            getAutomationResponseTriggersPullRequestSynchronizeWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestMerged getAutomationResponseTriggersPullRequestMerged() {
        return new GitHubGetAutomationResponseTriggersPullRequestMerged(
                "is:open label:bug", List.of("src/**"), getAutomationResponseTriggersPullRequestMergedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestMergedWriteScope
            getAutomationResponseTriggersPullRequestMergedWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersIssueComment getAutomationResponseTriggersIssueComment() {
        return new GitHubGetAutomationResponseTriggersIssueComment(
                "/triage", getAutomationResponseTriggersIssueCommentWriteScope());
    }

    static GitHubGetAutomationResponseTriggersIssueCommentWriteScope
            getAutomationResponseTriggersIssueCommentWriteScope() {
        return GitHubGetAutomationResponseTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestComment getAutomationResponseTriggersPullRequestComment() {
        return new GitHubGetAutomationResponseTriggersPullRequestComment(
                List.of(getAutomationResponseTriggersPullRequestCommentCommentType()),
                "/triage",
                getAutomationResponseTriggersPullRequestCommentWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestCommentCommentType
            getAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubGetAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubGetAutomationResponseTriggersPullRequestCommentWriteScope
            getAutomationResponseTriggersPullRequestCommentWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersDiscussionOpened getAutomationResponseTriggersDiscussionOpened() {
        return new GitHubGetAutomationResponseTriggersDiscussionOpened(
                List.of("General"), "/triage", getAutomationResponseTriggersDiscussionOpenedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersDiscussionOpenedWriteScope
            getAutomationResponseTriggersDiscussionOpenedWriteScope() {
        return GitHubGetAutomationResponseTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersDiscussionUpdated getAutomationResponseTriggersDiscussionUpdated() {
        return new GitHubGetAutomationResponseTriggersDiscussionUpdated(
                List.of("General"), "/triage", getAutomationResponseTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersDiscussionUpdatedWriteScope
            getAutomationResponseTriggersDiscussionUpdatedWriteScope() {
        return GitHubGetAutomationResponseTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersDiscussionComment getAutomationResponseTriggersDiscussionComment() {
        return new GitHubGetAutomationResponseTriggersDiscussionComment(
                List.of(getAutomationResponseTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                getAutomationResponseTriggersDiscussionCommentWriteScope());
    }

    static GitHubGetAutomationResponseTriggersDiscussionCommentCommentType
            getAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubGetAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubGetAutomationResponseTriggersDiscussionCommentWriteScope
            getAutomationResponseTriggersDiscussionCommentWriteScope() {
        return GitHubGetAutomationResponseTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersIssueAssigned getAutomationResponseTriggersIssueAssigned() {
        return new GitHubGetAutomationResponseTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), getAutomationResponseTriggersIssueAssignedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersIssueAssignedWriteScope
            getAutomationResponseTriggersIssueAssignedWriteScope() {
        return GitHubGetAutomationResponseTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestAssigned getAutomationResponseTriggersPullRequestAssigned() {
        return new GitHubGetAutomationResponseTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                getAutomationResponseTriggersPullRequestAssignedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestAssignedWriteScope
            getAutomationResponseTriggersPullRequestAssignedWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersSubIssueAdded getAutomationResponseTriggersSubIssueAdded() {
        return new GitHubGetAutomationResponseTriggersSubIssueAdded(
                "is:open label:bug", getAutomationResponseTriggersSubIssueAddedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersSubIssueAddedWriteScope
            getAutomationResponseTriggersSubIssueAddedWriteScope() {
        return GitHubGetAutomationResponseTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestReadyForReview
            getAutomationResponseTriggersPullRequestReadyForReview() {
        return new GitHubGetAutomationResponseTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                getAutomationResponseTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestReadyForReviewWriteScope
            getAutomationResponseTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestLabeled getAutomationResponseTriggersPullRequestLabeled() {
        return new GitHubGetAutomationResponseTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                getAutomationResponseTriggersPullRequestLabeledWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestLabeledWriteScope
            getAutomationResponseTriggersPullRequestLabeledWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestMergeConflict
            getAutomationResponseTriggersPullRequestMergeConflict() {
        return new GitHubGetAutomationResponseTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                getAutomationResponseTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestMergeConflictWriteScope
            getAutomationResponseTriggersPullRequestMergeConflictWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestReviewSubmitted
            getAutomationResponseTriggersPullRequestReviewSubmitted() {
        return new GitHubGetAutomationResponseTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(getAutomationResponseTriggersPullRequestReviewSubmittedState()),
                getAutomationResponseTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState
            getAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedWriteScope
            getAutomationResponseTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersPullRequestReviewRequested
            getAutomationResponseTriggersPullRequestReviewRequested() {
        return new GitHubGetAutomationResponseTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                getAutomationResponseTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersPullRequestReviewRequestedWriteScope
            getAutomationResponseTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubGetAutomationResponseTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseTriggersAutomationCompleted getAutomationResponseTriggersAutomationCompleted() {
        return new GitHubGetAutomationResponseTriggersAutomationCompleted(
                List.of("automation-2"), getAutomationResponseTriggersAutomationCompletedWriteScope());
    }

    static GitHubGetAutomationResponseTriggersAutomationCompletedWriteScope
            getAutomationResponseTriggersAutomationCompletedWriteScope() {
        return GitHubGetAutomationResponseTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubGetAutomationResponseCreatedBy getAutomationResponseCreatedBy() {
        return new GitHubGetAutomationResponseCreatedBy(1L);
    }

    static GitHubListAutomationTasksResponse listAutomationTasksResponse() {
        return new GitHubListAutomationTasksResponse(List.of(listAutomationTasksTask()));
    }

    static GitHubListAutomationTasksTask listAutomationTasksTask() {
        return new GitHubListAutomationTasksTask(
                "automation-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Nightly triage",
                listAutomationTasksTaskCreator(),
                listAutomationTasksTaskCreatorType(),
                List.of(listAutomationTasksTaskUserCollaborator()),
                listAutomationTasksTaskOwner(),
                listAutomationTasksTaskRepository(),
                listAutomationTasksTaskState(),
                1,
                List.of(listAutomationTasksTaskArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                listAutomationTasksTaskCustomAgent());
    }

    static GitHubListAutomationTasksTaskCreator listAutomationTasksTaskCreator() {
        return new GitHubListAutomationTasksTaskCreator(1L);
    }

    static GitHubListAutomationTasksTaskCreatorType listAutomationTasksTaskCreatorType() {
        return GitHubListAutomationTasksTaskCreatorType.USER;
    }

    static GitHubListAutomationTasksTaskUserCollaborator listAutomationTasksTaskUserCollaborator() {
        return new GitHubListAutomationTasksTaskUserCollaborator(1L);
    }

    static GitHubListAutomationTasksTaskOwner listAutomationTasksTaskOwner() {
        return new GitHubListAutomationTasksTaskOwner(1L);
    }

    static GitHubListAutomationTasksTaskRepository listAutomationTasksTaskRepository() {
        return new GitHubListAutomationTasksTaskRepository(1L);
    }

    static GitHubListAutomationTasksTaskState listAutomationTasksTaskState() {
        return GitHubListAutomationTasksTaskState.QUEUED;
    }

    static GitHubListAutomationTasksTaskArtifact listAutomationTasksTaskArtifact() {
        return new GitHubListAutomationTasksTaskArtifact(
                listAutomationTasksTaskArtifactProvider(),
                listAutomationTasksTaskArtifactType(),
                listAutomationTasksTaskArtifactDataById());
    }

    static GitHubListAutomationTasksTaskArtifactProvider listAutomationTasksTaskArtifactProvider() {
        return GitHubListAutomationTasksTaskArtifactProvider.GITHUB;
    }

    static GitHubListAutomationTasksTaskArtifactType listAutomationTasksTaskArtifactType() {
        return GitHubListAutomationTasksTaskArtifactType.PULL;
    }

    static GitHubListAutomationTasksTaskArtifactDataById listAutomationTasksTaskArtifactDataById() {
        return new GitHubListAutomationTasksTaskArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubListAutomationTasksTaskArtifactDataByRef listAutomationTasksTaskArtifactDataByRef() {
        return new GitHubListAutomationTasksTaskArtifactDataByRef("feature/fix", "main");
    }

    static GitHubListAutomationTasksTaskCustomAgent listAutomationTasksTaskCustomAgent() {
        return new GitHubListAutomationTasksTaskCustomAgent("automation-1");
    }

    static GitHubListRepoAutomationsResponse listRepoAutomationsResponse() {
        return new GitHubListRepoAutomationsResponse(List.of(listRepoAutomationsAutomation()), 1);
    }

    static GitHubListRepoAutomationsAutomation listRepoAutomationsAutomation() {
        return new GitHubListRepoAutomationsAutomation(
                "automation-1",
                "Nightly triage",
                true,
                listRepoAutomationsAutomationOwnerType(),
                listRepoAutomationsAutomationWriteScope(),
                true,
                "gpt-5",
                "medium",
                listRepoAutomationsAutomationTriggers(),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                listRepoAutomationsAutomationCreatedBy());
    }

    static GitHubListRepoAutomationsAutomationOwnerType listRepoAutomationsAutomationOwnerType() {
        return GitHubListRepoAutomationsAutomationOwnerType.USER;
    }

    static GitHubListRepoAutomationsAutomationWriteScope listRepoAutomationsAutomationWriteScope() {
        return GitHubListRepoAutomationsAutomationWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggers listRepoAutomationsAutomationTriggers() {
        return new GitHubListRepoAutomationsAutomationTriggers(
                listRepoAutomationsAutomationTriggersInterval(),
                listRepoAutomationsAutomationTriggersIssueOpened(),
                listRepoAutomationsAutomationTriggersIssueLabeled(),
                listRepoAutomationsAutomationTriggersWorkflowRun(),
                listRepoAutomationsAutomationTriggersPullRequestOpened(),
                listRepoAutomationsAutomationTriggersPullRequestSynchronize(),
                listRepoAutomationsAutomationTriggersPullRequestMerged(),
                listRepoAutomationsAutomationTriggersIssueComment(),
                listRepoAutomationsAutomationTriggersPullRequestComment(),
                listRepoAutomationsAutomationTriggersDiscussionOpened(),
                listRepoAutomationsAutomationTriggersDiscussionUpdated(),
                listRepoAutomationsAutomationTriggersDiscussionComment(),
                listRepoAutomationsAutomationTriggersIssueAssigned(),
                listRepoAutomationsAutomationTriggersPullRequestAssigned(),
                listRepoAutomationsAutomationTriggersSubIssueAdded(),
                listRepoAutomationsAutomationTriggersPullRequestReadyForReview(),
                listRepoAutomationsAutomationTriggersPullRequestLabeled(),
                listRepoAutomationsAutomationTriggersPullRequestMergeConflict(),
                listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted(),
                listRepoAutomationsAutomationTriggersPullRequestReviewRequested(),
                listRepoAutomationsAutomationTriggersAutomationCompleted());
    }

    static GitHubListRepoAutomationsAutomationTriggersInterval listRepoAutomationsAutomationTriggersInterval() {
        return new GitHubListRepoAutomationsAutomationTriggersInterval(
                List.of(listRepoAutomationsAutomationTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                listRepoAutomationsAutomationTriggersIntervalWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersIntervalCadence
            listRepoAutomationsAutomationTriggersIntervalCadence() {
        return GitHubListRepoAutomationsAutomationTriggersIntervalCadence.HOURLY;
    }

    static GitHubListRepoAutomationsAutomationTriggersIntervalWriteScope
            listRepoAutomationsAutomationTriggersIntervalWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueOpened listRepoAutomationsAutomationTriggersIssueOpened() {
        return new GitHubListRepoAutomationsAutomationTriggersIssueOpened(
                "is:open label:bug", listRepoAutomationsAutomationTriggersIssueOpenedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueOpenedWriteScope
            listRepoAutomationsAutomationTriggersIssueOpenedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueLabeled listRepoAutomationsAutomationTriggersIssueLabeled() {
        return new GitHubListRepoAutomationsAutomationTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), listRepoAutomationsAutomationTriggersIssueLabeledWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueLabeledWriteScope
            listRepoAutomationsAutomationTriggersIssueLabeledWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersWorkflowRun listRepoAutomationsAutomationTriggersWorkflowRun() {
        return new GitHubListRepoAutomationsAutomationTriggersWorkflowRun(
                List.of("CI"),
                List.of(listRepoAutomationsAutomationTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                listRepoAutomationsAutomationTriggersWorkflowRunWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion
            listRepoAutomationsAutomationTriggersWorkflowRunConclusion() {
        return GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubListRepoAutomationsAutomationTriggersWorkflowRunWriteScope
            listRepoAutomationsAutomationTriggersWorkflowRunWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestOpened
            listRepoAutomationsAutomationTriggersPullRequestOpened() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestOpened(
                "is:open label:bug",
                List.of("src/**"),
                listRepoAutomationsAutomationTriggersPullRequestOpenedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestOpenedWriteScope
            listRepoAutomationsAutomationTriggersPullRequestOpenedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronize
            listRepoAutomationsAutomationTriggersPullRequestSynchronize() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronize(
                "is:open label:bug",
                List.of("src/**"),
                listRepoAutomationsAutomationTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronizeWriteScope
            listRepoAutomationsAutomationTriggersPullRequestSynchronizeWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestMerged
            listRepoAutomationsAutomationTriggersPullRequestMerged() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestMerged(
                "is:open label:bug",
                List.of("src/**"),
                listRepoAutomationsAutomationTriggersPullRequestMergedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestMergedWriteScope
            listRepoAutomationsAutomationTriggersPullRequestMergedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueComment listRepoAutomationsAutomationTriggersIssueComment() {
        return new GitHubListRepoAutomationsAutomationTriggersIssueComment(
                "/triage", listRepoAutomationsAutomationTriggersIssueCommentWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueCommentWriteScope
            listRepoAutomationsAutomationTriggersIssueCommentWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestComment
            listRepoAutomationsAutomationTriggersPullRequestComment() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestComment(
                List.of(listRepoAutomationsAutomationTriggersPullRequestCommentCommentType()),
                "/triage",
                listRepoAutomationsAutomationTriggersPullRequestCommentWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
            listRepoAutomationsAutomationTriggersPullRequestCommentCommentType() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestCommentWriteScope
            listRepoAutomationsAutomationTriggersPullRequestCommentWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionOpened
            listRepoAutomationsAutomationTriggersDiscussionOpened() {
        return new GitHubListRepoAutomationsAutomationTriggersDiscussionOpened(
                List.of("General"), "/triage", listRepoAutomationsAutomationTriggersDiscussionOpenedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedWriteScope
            listRepoAutomationsAutomationTriggersDiscussionOpenedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionUpdated
            listRepoAutomationsAutomationTriggersDiscussionUpdated() {
        return new GitHubListRepoAutomationsAutomationTriggersDiscussionUpdated(
                List.of("General"), "/triage", listRepoAutomationsAutomationTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionUpdatedWriteScope
            listRepoAutomationsAutomationTriggersDiscussionUpdatedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionComment
            listRepoAutomationsAutomationTriggersDiscussionComment() {
        return new GitHubListRepoAutomationsAutomationTriggersDiscussionComment(
                List.of(listRepoAutomationsAutomationTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                listRepoAutomationsAutomationTriggersDiscussionCommentWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType
            listRepoAutomationsAutomationTriggersDiscussionCommentCommentType() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionCommentWriteScope
            listRepoAutomationsAutomationTriggersDiscussionCommentWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueAssigned
            listRepoAutomationsAutomationTriggersIssueAssigned() {
        return new GitHubListRepoAutomationsAutomationTriggersIssueAssigned(
                "is:open label:bug",
                List.of("octocat"),
                listRepoAutomationsAutomationTriggersIssueAssignedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueAssignedWriteScope
            listRepoAutomationsAutomationTriggersIssueAssignedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestAssigned
            listRepoAutomationsAutomationTriggersPullRequestAssigned() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                listRepoAutomationsAutomationTriggersPullRequestAssignedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedWriteScope
            listRepoAutomationsAutomationTriggersPullRequestAssignedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersSubIssueAdded
            listRepoAutomationsAutomationTriggersSubIssueAdded() {
        return new GitHubListRepoAutomationsAutomationTriggersSubIssueAdded(
                "is:open label:bug", listRepoAutomationsAutomationTriggersSubIssueAddedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersSubIssueAddedWriteScope
            listRepoAutomationsAutomationTriggersSubIssueAddedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReview
            listRepoAutomationsAutomationTriggersPullRequestReadyForReview() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                listRepoAutomationsAutomationTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReviewWriteScope
            listRepoAutomationsAutomationTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestLabeled
            listRepoAutomationsAutomationTriggersPullRequestLabeled() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                listRepoAutomationsAutomationTriggersPullRequestLabeledWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestLabeledWriteScope
            listRepoAutomationsAutomationTriggersPullRequestLabeledWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflict
            listRepoAutomationsAutomationTriggersPullRequestMergeConflict() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                listRepoAutomationsAutomationTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflictWriteScope
            listRepoAutomationsAutomationTriggersPullRequestMergeConflictWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmitted
            listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(listRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState()),
                listRepoAutomationsAutomationTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState
            listRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedWriteScope
            listRepoAutomationsAutomationTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequested
            listRepoAutomationsAutomationTriggersPullRequestReviewRequested() {
        return new GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                listRepoAutomationsAutomationTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequestedWriteScope
            listRepoAutomationsAutomationTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationTriggersAutomationCompleted
            listRepoAutomationsAutomationTriggersAutomationCompleted() {
        return new GitHubListRepoAutomationsAutomationTriggersAutomationCompleted(
                List.of("automation-2"), listRepoAutomationsAutomationTriggersAutomationCompletedWriteScope());
    }

    static GitHubListRepoAutomationsAutomationTriggersAutomationCompletedWriteScope
            listRepoAutomationsAutomationTriggersAutomationCompletedWriteScope() {
        return GitHubListRepoAutomationsAutomationTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubListRepoAutomationsAutomationCreatedBy listRepoAutomationsAutomationCreatedBy() {
        return new GitHubListRepoAutomationsAutomationCreatedBy(1L);
    }

    static GitHubCreateRepoAutomationPayload createRepoAutomationPayload() {
        return new GitHubCreateRepoAutomationPayload(
                "Nightly triage",
                createRepoAutomationTriggers(),
                "Triage new issues and label them.",
                "gpt-5",
                GitHubCreateRepoAutomationReasoningEffort.MEDIUM,
                List.of("issues_read"),
                List.of("triage-agent"),
                true,
                createRepoAutomationOwnerType(),
                createRepoAutomationWriteScope(),
                true);
    }

    static GitHubCreateRepoAutomationTriggers createRepoAutomationTriggers() {
        return new GitHubCreateRepoAutomationTriggers(
                createRepoAutomationTriggersInterval(),
                createRepoAutomationTriggersIssueOpened(),
                createRepoAutomationTriggersIssueLabeled(),
                createRepoAutomationTriggersWorkflowRun(),
                createRepoAutomationTriggersPullRequestOpened(),
                createRepoAutomationTriggersPullRequestSynchronize(),
                createRepoAutomationTriggersPullRequestMerged(),
                createRepoAutomationTriggersIssueComment(),
                createRepoAutomationTriggersPullRequestComment(),
                createRepoAutomationTriggersDiscussionOpened(),
                createRepoAutomationTriggersDiscussionUpdated(),
                createRepoAutomationTriggersDiscussionComment(),
                createRepoAutomationTriggersIssueAssigned(),
                createRepoAutomationTriggersPullRequestAssigned(),
                createRepoAutomationTriggersSubIssueAdded(),
                createRepoAutomationTriggersPullRequestReadyForReview(),
                createRepoAutomationTriggersPullRequestLabeled(),
                createRepoAutomationTriggersPullRequestMergeConflict(),
                createRepoAutomationTriggersPullRequestReviewSubmitted(),
                createRepoAutomationTriggersPullRequestReviewRequested(),
                createRepoAutomationTriggersAutomationCompleted());
    }

    static GitHubCreateRepoAutomationTriggersInterval createRepoAutomationTriggersInterval() {
        return new GitHubCreateRepoAutomationTriggersInterval(
                List.of(createRepoAutomationTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                createRepoAutomationTriggersIntervalWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersIntervalCadence createRepoAutomationTriggersIntervalCadence() {
        return GitHubCreateRepoAutomationTriggersIntervalCadence.HOURLY;
    }

    static GitHubCreateRepoAutomationTriggersIntervalWriteScope createRepoAutomationTriggersIntervalWriteScope() {
        return GitHubCreateRepoAutomationTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersIssueOpened createRepoAutomationTriggersIssueOpened() {
        return new GitHubCreateRepoAutomationTriggersIssueOpened(
                "is:open label:bug", createRepoAutomationTriggersIssueOpenedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersIssueOpenedWriteScope createRepoAutomationTriggersIssueOpenedWriteScope() {
        return GitHubCreateRepoAutomationTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersIssueLabeled createRepoAutomationTriggersIssueLabeled() {
        return new GitHubCreateRepoAutomationTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), createRepoAutomationTriggersIssueLabeledWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersIssueLabeledWriteScope
            createRepoAutomationTriggersIssueLabeledWriteScope() {
        return GitHubCreateRepoAutomationTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersWorkflowRun createRepoAutomationTriggersWorkflowRun() {
        return new GitHubCreateRepoAutomationTriggersWorkflowRun(
                List.of("CI"),
                List.of(createRepoAutomationTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                createRepoAutomationTriggersWorkflowRunWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersWorkflowRunConclusion createRepoAutomationTriggersWorkflowRunConclusion() {
        return GitHubCreateRepoAutomationTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubCreateRepoAutomationTriggersWorkflowRunWriteScope createRepoAutomationTriggersWorkflowRunWriteScope() {
        return GitHubCreateRepoAutomationTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestOpened createRepoAutomationTriggersPullRequestOpened() {
        return new GitHubCreateRepoAutomationTriggersPullRequestOpened(
                "is:open label:bug", List.of("src/**"), createRepoAutomationTriggersPullRequestOpenedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestOpenedWriteScope
            createRepoAutomationTriggersPullRequestOpenedWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestSynchronize
            createRepoAutomationTriggersPullRequestSynchronize() {
        return new GitHubCreateRepoAutomationTriggersPullRequestSynchronize(
                "is:open label:bug", List.of("src/**"), createRepoAutomationTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestSynchronizeWriteScope
            createRepoAutomationTriggersPullRequestSynchronizeWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestMerged createRepoAutomationTriggersPullRequestMerged() {
        return new GitHubCreateRepoAutomationTriggersPullRequestMerged(
                "is:open label:bug", List.of("src/**"), createRepoAutomationTriggersPullRequestMergedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestMergedWriteScope
            createRepoAutomationTriggersPullRequestMergedWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersIssueComment createRepoAutomationTriggersIssueComment() {
        return new GitHubCreateRepoAutomationTriggersIssueComment(
                "/triage", createRepoAutomationTriggersIssueCommentWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersIssueCommentWriteScope
            createRepoAutomationTriggersIssueCommentWriteScope() {
        return GitHubCreateRepoAutomationTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestComment createRepoAutomationTriggersPullRequestComment() {
        return new GitHubCreateRepoAutomationTriggersPullRequestComment(
                List.of(createRepoAutomationTriggersPullRequestCommentCommentType()),
                "/triage",
                createRepoAutomationTriggersPullRequestCommentWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestCommentCommentType
            createRepoAutomationTriggersPullRequestCommentCommentType() {
        return GitHubCreateRepoAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestCommentWriteScope
            createRepoAutomationTriggersPullRequestCommentWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersDiscussionOpened createRepoAutomationTriggersDiscussionOpened() {
        return new GitHubCreateRepoAutomationTriggersDiscussionOpened(
                List.of("General"), "/triage", createRepoAutomationTriggersDiscussionOpenedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersDiscussionOpenedWriteScope
            createRepoAutomationTriggersDiscussionOpenedWriteScope() {
        return GitHubCreateRepoAutomationTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersDiscussionUpdated createRepoAutomationTriggersDiscussionUpdated() {
        return new GitHubCreateRepoAutomationTriggersDiscussionUpdated(
                List.of("General"), "/triage", createRepoAutomationTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersDiscussionUpdatedWriteScope
            createRepoAutomationTriggersDiscussionUpdatedWriteScope() {
        return GitHubCreateRepoAutomationTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersDiscussionComment createRepoAutomationTriggersDiscussionComment() {
        return new GitHubCreateRepoAutomationTriggersDiscussionComment(
                List.of(createRepoAutomationTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                createRepoAutomationTriggersDiscussionCommentWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType
            createRepoAutomationTriggersDiscussionCommentCommentType() {
        return GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubCreateRepoAutomationTriggersDiscussionCommentWriteScope
            createRepoAutomationTriggersDiscussionCommentWriteScope() {
        return GitHubCreateRepoAutomationTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersIssueAssigned createRepoAutomationTriggersIssueAssigned() {
        return new GitHubCreateRepoAutomationTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), createRepoAutomationTriggersIssueAssignedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersIssueAssignedWriteScope
            createRepoAutomationTriggersIssueAssignedWriteScope() {
        return GitHubCreateRepoAutomationTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestAssigned createRepoAutomationTriggersPullRequestAssigned() {
        return new GitHubCreateRepoAutomationTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                createRepoAutomationTriggersPullRequestAssignedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestAssignedWriteScope
            createRepoAutomationTriggersPullRequestAssignedWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersSubIssueAdded createRepoAutomationTriggersSubIssueAdded() {
        return new GitHubCreateRepoAutomationTriggersSubIssueAdded(
                "is:open label:bug", createRepoAutomationTriggersSubIssueAddedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersSubIssueAddedWriteScope
            createRepoAutomationTriggersSubIssueAddedWriteScope() {
        return GitHubCreateRepoAutomationTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReadyForReview
            createRepoAutomationTriggersPullRequestReadyForReview() {
        return new GitHubCreateRepoAutomationTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewWriteScope
            createRepoAutomationTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestLabeled createRepoAutomationTriggersPullRequestLabeled() {
        return new GitHubCreateRepoAutomationTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                createRepoAutomationTriggersPullRequestLabeledWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestLabeledWriteScope
            createRepoAutomationTriggersPullRequestLabeledWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestMergeConflict
            createRepoAutomationTriggersPullRequestMergeConflict() {
        return new GitHubCreateRepoAutomationTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope
            createRepoAutomationTriggersPullRequestMergeConflictWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewSubmitted
            createRepoAutomationTriggersPullRequestReviewSubmitted() {
        return new GitHubCreateRepoAutomationTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(createRepoAutomationTriggersPullRequestReviewSubmittedState()),
                createRepoAutomationTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState
            createRepoAutomationTriggersPullRequestReviewSubmittedState() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedWriteScope
            createRepoAutomationTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewRequested
            createRepoAutomationTriggersPullRequestReviewRequested() {
        return new GitHubCreateRepoAutomationTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewRequestedWriteScope
            createRepoAutomationTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationTriggersAutomationCompleted createRepoAutomationTriggersAutomationCompleted() {
        return new GitHubCreateRepoAutomationTriggersAutomationCompleted(
                List.of("automation-2"), createRepoAutomationTriggersAutomationCompletedWriteScope());
    }

    static GitHubCreateRepoAutomationTriggersAutomationCompletedWriteScope
            createRepoAutomationTriggersAutomationCompletedWriteScope() {
        return GitHubCreateRepoAutomationTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationOwnerType createRepoAutomationOwnerType() {
        return GitHubCreateRepoAutomationOwnerType.USER;
    }

    static GitHubCreateRepoAutomationWriteScope createRepoAutomationWriteScope() {
        return GitHubCreateRepoAutomationWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponse createRepoAutomationResponse() {
        return new GitHubCreateRepoAutomationResponse(
                "automation-1",
                "Nightly triage",
                true,
                createRepoAutomationResponseOwnerType(),
                createRepoAutomationResponseWriteScope(),
                true,
                createRepoAutomationResponseTriggers(),
                "Triage new issues and label them.",
                List.of("issues_read"),
                List.of("triage-agent"),
                "gpt-5",
                "medium",
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                createRepoAutomationResponseCreatedBy());
    }

    static GitHubCreateRepoAutomationResponseOwnerType createRepoAutomationResponseOwnerType() {
        return GitHubCreateRepoAutomationResponseOwnerType.USER;
    }

    static GitHubCreateRepoAutomationResponseWriteScope createRepoAutomationResponseWriteScope() {
        return GitHubCreateRepoAutomationResponseWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggers createRepoAutomationResponseTriggers() {
        return new GitHubCreateRepoAutomationResponseTriggers(
                createRepoAutomationResponseTriggersInterval(),
                createRepoAutomationResponseTriggersIssueOpened(),
                createRepoAutomationResponseTriggersIssueLabeled(),
                createRepoAutomationResponseTriggersWorkflowRun(),
                createRepoAutomationResponseTriggersPullRequestOpened(),
                createRepoAutomationResponseTriggersPullRequestSynchronize(),
                createRepoAutomationResponseTriggersPullRequestMerged(),
                createRepoAutomationResponseTriggersIssueComment(),
                createRepoAutomationResponseTriggersPullRequestComment(),
                createRepoAutomationResponseTriggersDiscussionOpened(),
                createRepoAutomationResponseTriggersDiscussionUpdated(),
                createRepoAutomationResponseTriggersDiscussionComment(),
                createRepoAutomationResponseTriggersIssueAssigned(),
                createRepoAutomationResponseTriggersPullRequestAssigned(),
                createRepoAutomationResponseTriggersSubIssueAdded(),
                createRepoAutomationResponseTriggersPullRequestReadyForReview(),
                createRepoAutomationResponseTriggersPullRequestLabeled(),
                createRepoAutomationResponseTriggersPullRequestMergeConflict(),
                createRepoAutomationResponseTriggersPullRequestReviewSubmitted(),
                createRepoAutomationResponseTriggersPullRequestReviewRequested(),
                createRepoAutomationResponseTriggersAutomationCompleted());
    }

    static GitHubCreateRepoAutomationResponseTriggersInterval createRepoAutomationResponseTriggersInterval() {
        return new GitHubCreateRepoAutomationResponseTriggersInterval(
                List.of(createRepoAutomationResponseTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                createRepoAutomationResponseTriggersIntervalWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersIntervalCadence
            createRepoAutomationResponseTriggersIntervalCadence() {
        return GitHubCreateRepoAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubCreateRepoAutomationResponseTriggersIntervalWriteScope
            createRepoAutomationResponseTriggersIntervalWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueOpened createRepoAutomationResponseTriggersIssueOpened() {
        return new GitHubCreateRepoAutomationResponseTriggersIssueOpened(
                "is:open label:bug", createRepoAutomationResponseTriggersIssueOpenedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueOpenedWriteScope
            createRepoAutomationResponseTriggersIssueOpenedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueLabeled createRepoAutomationResponseTriggersIssueLabeled() {
        return new GitHubCreateRepoAutomationResponseTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), createRepoAutomationResponseTriggersIssueLabeledWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope
            createRepoAutomationResponseTriggersIssueLabeledWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersWorkflowRun createRepoAutomationResponseTriggersWorkflowRun() {
        return new GitHubCreateRepoAutomationResponseTriggersWorkflowRun(
                List.of("CI"),
                List.of(createRepoAutomationResponseTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                createRepoAutomationResponseTriggersWorkflowRunWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion
            createRepoAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubCreateRepoAutomationResponseTriggersWorkflowRunWriteScope
            createRepoAutomationResponseTriggersWorkflowRunWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestOpened
            createRepoAutomationResponseTriggersPullRequestOpened() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestOpened(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationResponseTriggersPullRequestOpenedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedWriteScope
            createRepoAutomationResponseTriggersPullRequestOpenedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestSynchronize
            createRepoAutomationResponseTriggersPullRequestSynchronize() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestSynchronize(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope
            createRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestMerged
            createRepoAutomationResponseTriggersPullRequestMerged() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestMerged(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationResponseTriggersPullRequestMergedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestMergedWriteScope
            createRepoAutomationResponseTriggersPullRequestMergedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueComment createRepoAutomationResponseTriggersIssueComment() {
        return new GitHubCreateRepoAutomationResponseTriggersIssueComment(
                "/triage", createRepoAutomationResponseTriggersIssueCommentWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueCommentWriteScope
            createRepoAutomationResponseTriggersIssueCommentWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestComment
            createRepoAutomationResponseTriggersPullRequestComment() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestComment(
                List.of(createRepoAutomationResponseTriggersPullRequestCommentCommentType()),
                "/triage",
                createRepoAutomationResponseTriggersPullRequestCommentWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
            createRepoAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestCommentWriteScope
            createRepoAutomationResponseTriggersPullRequestCommentWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionOpened
            createRepoAutomationResponseTriggersDiscussionOpened() {
        return new GitHubCreateRepoAutomationResponseTriggersDiscussionOpened(
                List.of("General"), "/triage", createRepoAutomationResponseTriggersDiscussionOpenedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionOpenedWriteScope
            createRepoAutomationResponseTriggersDiscussionOpenedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionUpdated
            createRepoAutomationResponseTriggersDiscussionUpdated() {
        return new GitHubCreateRepoAutomationResponseTriggersDiscussionUpdated(
                List.of("General"), "/triage", createRepoAutomationResponseTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionUpdatedWriteScope
            createRepoAutomationResponseTriggersDiscussionUpdatedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionComment
            createRepoAutomationResponseTriggersDiscussionComment() {
        return new GitHubCreateRepoAutomationResponseTriggersDiscussionComment(
                List.of(createRepoAutomationResponseTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                createRepoAutomationResponseTriggersDiscussionCommentWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType
            createRepoAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionCommentWriteScope
            createRepoAutomationResponseTriggersDiscussionCommentWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueAssigned createRepoAutomationResponseTriggersIssueAssigned() {
        return new GitHubCreateRepoAutomationResponseTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), createRepoAutomationResponseTriggersIssueAssignedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueAssignedWriteScope
            createRepoAutomationResponseTriggersIssueAssignedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestAssigned
            createRepoAutomationResponseTriggersPullRequestAssigned() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                createRepoAutomationResponseTriggersPullRequestAssignedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedWriteScope
            createRepoAutomationResponseTriggersPullRequestAssignedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersSubIssueAdded createRepoAutomationResponseTriggersSubIssueAdded() {
        return new GitHubCreateRepoAutomationResponseTriggersSubIssueAdded(
                "is:open label:bug", createRepoAutomationResponseTriggersSubIssueAddedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersSubIssueAddedWriteScope
            createRepoAutomationResponseTriggersSubIssueAddedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReadyForReview
            createRepoAutomationResponseTriggersPullRequestReadyForReview() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope
            createRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestLabeled
            createRepoAutomationResponseTriggersPullRequestLabeled() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                createRepoAutomationResponseTriggersPullRequestLabeledWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestLabeledWriteScope
            createRepoAutomationResponseTriggersPullRequestLabeledWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflict
            createRepoAutomationResponseTriggersPullRequestMergeConflict() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope
            createRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmitted
            createRepoAutomationResponseTriggersPullRequestReviewSubmitted() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(createRepoAutomationResponseTriggersPullRequestReviewSubmittedState()),
                createRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState
            createRepoAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope
            createRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewRequested
            createRepoAutomationResponseTriggersPullRequestReviewRequested() {
        return new GitHubCreateRepoAutomationResponseTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                createRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope
            createRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseTriggersAutomationCompleted
            createRepoAutomationResponseTriggersAutomationCompleted() {
        return new GitHubCreateRepoAutomationResponseTriggersAutomationCompleted(
                List.of("automation-2"), createRepoAutomationResponseTriggersAutomationCompletedWriteScope());
    }

    static GitHubCreateRepoAutomationResponseTriggersAutomationCompletedWriteScope
            createRepoAutomationResponseTriggersAutomationCompletedWriteScope() {
        return GitHubCreateRepoAutomationResponseTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubCreateRepoAutomationResponseCreatedBy createRepoAutomationResponseCreatedBy() {
        return new GitHubCreateRepoAutomationResponseCreatedBy(1L);
    }

    static GitHubGetRepoAutomationResponse getRepoAutomationResponse() {
        return new GitHubGetRepoAutomationResponse(
                "automation-1",
                "Nightly triage",
                true,
                getRepoAutomationResponseOwnerType(),
                getRepoAutomationResponseWriteScope(),
                true,
                getRepoAutomationResponseTriggers(),
                "Triage new issues and label them.",
                List.of("issues_read"),
                List.of("triage-agent"),
                "gpt-5",
                "medium",
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                getRepoAutomationResponseCreatedBy());
    }

    static GitHubGetRepoAutomationResponseOwnerType getRepoAutomationResponseOwnerType() {
        return GitHubGetRepoAutomationResponseOwnerType.USER;
    }

    static GitHubGetRepoAutomationResponseWriteScope getRepoAutomationResponseWriteScope() {
        return GitHubGetRepoAutomationResponseWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggers getRepoAutomationResponseTriggers() {
        return new GitHubGetRepoAutomationResponseTriggers(
                getRepoAutomationResponseTriggersInterval(),
                getRepoAutomationResponseTriggersIssueOpened(),
                getRepoAutomationResponseTriggersIssueLabeled(),
                getRepoAutomationResponseTriggersWorkflowRun(),
                getRepoAutomationResponseTriggersPullRequestOpened(),
                getRepoAutomationResponseTriggersPullRequestSynchronize(),
                getRepoAutomationResponseTriggersPullRequestMerged(),
                getRepoAutomationResponseTriggersIssueComment(),
                getRepoAutomationResponseTriggersPullRequestComment(),
                getRepoAutomationResponseTriggersDiscussionOpened(),
                getRepoAutomationResponseTriggersDiscussionUpdated(),
                getRepoAutomationResponseTriggersDiscussionComment(),
                getRepoAutomationResponseTriggersIssueAssigned(),
                getRepoAutomationResponseTriggersPullRequestAssigned(),
                getRepoAutomationResponseTriggersSubIssueAdded(),
                getRepoAutomationResponseTriggersPullRequestReadyForReview(),
                getRepoAutomationResponseTriggersPullRequestLabeled(),
                getRepoAutomationResponseTriggersPullRequestMergeConflict(),
                getRepoAutomationResponseTriggersPullRequestReviewSubmitted(),
                getRepoAutomationResponseTriggersPullRequestReviewRequested(),
                getRepoAutomationResponseTriggersAutomationCompleted());
    }

    static GitHubGetRepoAutomationResponseTriggersInterval getRepoAutomationResponseTriggersInterval() {
        return new GitHubGetRepoAutomationResponseTriggersInterval(
                List.of(getRepoAutomationResponseTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                getRepoAutomationResponseTriggersIntervalWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersIntervalCadence getRepoAutomationResponseTriggersIntervalCadence() {
        return GitHubGetRepoAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubGetRepoAutomationResponseTriggersIntervalWriteScope
            getRepoAutomationResponseTriggersIntervalWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersIssueOpened getRepoAutomationResponseTriggersIssueOpened() {
        return new GitHubGetRepoAutomationResponseTriggersIssueOpened(
                "is:open label:bug", getRepoAutomationResponseTriggersIssueOpenedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersIssueOpenedWriteScope
            getRepoAutomationResponseTriggersIssueOpenedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersIssueLabeled getRepoAutomationResponseTriggersIssueLabeled() {
        return new GitHubGetRepoAutomationResponseTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), getRepoAutomationResponseTriggersIssueLabeledWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersIssueLabeledWriteScope
            getRepoAutomationResponseTriggersIssueLabeledWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersWorkflowRun getRepoAutomationResponseTriggersWorkflowRun() {
        return new GitHubGetRepoAutomationResponseTriggersWorkflowRun(
                List.of("CI"),
                List.of(getRepoAutomationResponseTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                getRepoAutomationResponseTriggersWorkflowRunWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion
            getRepoAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubGetRepoAutomationResponseTriggersWorkflowRunWriteScope
            getRepoAutomationResponseTriggersWorkflowRunWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestOpened
            getRepoAutomationResponseTriggersPullRequestOpened() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestOpened(
                "is:open label:bug", List.of("src/**"), getRepoAutomationResponseTriggersPullRequestOpenedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestOpenedWriteScope
            getRepoAutomationResponseTriggersPullRequestOpenedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestSynchronize
            getRepoAutomationResponseTriggersPullRequestSynchronize() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestSynchronize(
                "is:open label:bug",
                List.of("src/**"),
                getRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope
            getRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestMerged
            getRepoAutomationResponseTriggersPullRequestMerged() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestMerged(
                "is:open label:bug", List.of("src/**"), getRepoAutomationResponseTriggersPullRequestMergedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestMergedWriteScope
            getRepoAutomationResponseTriggersPullRequestMergedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersIssueComment getRepoAutomationResponseTriggersIssueComment() {
        return new GitHubGetRepoAutomationResponseTriggersIssueComment(
                "/triage", getRepoAutomationResponseTriggersIssueCommentWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersIssueCommentWriteScope
            getRepoAutomationResponseTriggersIssueCommentWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestComment
            getRepoAutomationResponseTriggersPullRequestComment() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestComment(
                List.of(getRepoAutomationResponseTriggersPullRequestCommentCommentType()),
                "/triage",
                getRepoAutomationResponseTriggersPullRequestCommentWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestCommentCommentType
            getRepoAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestCommentWriteScope
            getRepoAutomationResponseTriggersPullRequestCommentWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionOpened getRepoAutomationResponseTriggersDiscussionOpened() {
        return new GitHubGetRepoAutomationResponseTriggersDiscussionOpened(
                List.of("General"), "/triage", getRepoAutomationResponseTriggersDiscussionOpenedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionOpenedWriteScope
            getRepoAutomationResponseTriggersDiscussionOpenedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionUpdated
            getRepoAutomationResponseTriggersDiscussionUpdated() {
        return new GitHubGetRepoAutomationResponseTriggersDiscussionUpdated(
                List.of("General"), "/triage", getRepoAutomationResponseTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionUpdatedWriteScope
            getRepoAutomationResponseTriggersDiscussionUpdatedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionComment
            getRepoAutomationResponseTriggersDiscussionComment() {
        return new GitHubGetRepoAutomationResponseTriggersDiscussionComment(
                List.of(getRepoAutomationResponseTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                getRepoAutomationResponseTriggersDiscussionCommentWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType
            getRepoAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionCommentWriteScope
            getRepoAutomationResponseTriggersDiscussionCommentWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersIssueAssigned getRepoAutomationResponseTriggersIssueAssigned() {
        return new GitHubGetRepoAutomationResponseTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), getRepoAutomationResponseTriggersIssueAssignedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersIssueAssignedWriteScope
            getRepoAutomationResponseTriggersIssueAssignedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestAssigned
            getRepoAutomationResponseTriggersPullRequestAssigned() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                getRepoAutomationResponseTriggersPullRequestAssignedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestAssignedWriteScope
            getRepoAutomationResponseTriggersPullRequestAssignedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersSubIssueAdded getRepoAutomationResponseTriggersSubIssueAdded() {
        return new GitHubGetRepoAutomationResponseTriggersSubIssueAdded(
                "is:open label:bug", getRepoAutomationResponseTriggersSubIssueAddedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersSubIssueAddedWriteScope
            getRepoAutomationResponseTriggersSubIssueAddedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReview
            getRepoAutomationResponseTriggersPullRequestReadyForReview() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                getRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope
            getRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestLabeled
            getRepoAutomationResponseTriggersPullRequestLabeled() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                getRepoAutomationResponseTriggersPullRequestLabeledWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestLabeledWriteScope
            getRepoAutomationResponseTriggersPullRequestLabeledWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflict
            getRepoAutomationResponseTriggersPullRequestMergeConflict() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                getRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope
            getRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmitted
            getRepoAutomationResponseTriggersPullRequestReviewSubmitted() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(getRepoAutomationResponseTriggersPullRequestReviewSubmittedState()),
                getRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState
            getRepoAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope
            getRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequested
            getRepoAutomationResponseTriggersPullRequestReviewRequested() {
        return new GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                getRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope
            getRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseTriggersAutomationCompleted
            getRepoAutomationResponseTriggersAutomationCompleted() {
        return new GitHubGetRepoAutomationResponseTriggersAutomationCompleted(
                List.of("automation-2"), getRepoAutomationResponseTriggersAutomationCompletedWriteScope());
    }

    static GitHubGetRepoAutomationResponseTriggersAutomationCompletedWriteScope
            getRepoAutomationResponseTriggersAutomationCompletedWriteScope() {
        return GitHubGetRepoAutomationResponseTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubGetRepoAutomationResponseCreatedBy getRepoAutomationResponseCreatedBy() {
        return new GitHubGetRepoAutomationResponseCreatedBy(1L);
    }

    static GitHubEditRepoAutomationPayload editRepoAutomationPayload() {
        return new GitHubEditRepoAutomationPayload(
                "Nightly triage",
                true,
                editRepoAutomationWriteScope(),
                true,
                "gpt-5",
                GitHubEditRepoAutomationReasoningEffort.MEDIUM,
                editRepoAutomationTriggers(),
                "Triage new issues and label them.",
                List.of("issues_read"),
                List.of("triage-agent"));
    }

    static GitHubEditRepoAutomationWriteScope editRepoAutomationWriteScope() {
        return GitHubEditRepoAutomationWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggers editRepoAutomationTriggers() {
        return new GitHubEditRepoAutomationTriggers(
                editRepoAutomationTriggersInterval(),
                editRepoAutomationTriggersIssueOpened(),
                editRepoAutomationTriggersIssueLabeled(),
                editRepoAutomationTriggersWorkflowRun(),
                editRepoAutomationTriggersPullRequestOpened(),
                editRepoAutomationTriggersPullRequestSynchronize(),
                editRepoAutomationTriggersPullRequestMerged(),
                editRepoAutomationTriggersIssueComment(),
                editRepoAutomationTriggersPullRequestComment(),
                editRepoAutomationTriggersDiscussionOpened(),
                editRepoAutomationTriggersDiscussionUpdated(),
                editRepoAutomationTriggersDiscussionComment(),
                editRepoAutomationTriggersIssueAssigned(),
                editRepoAutomationTriggersPullRequestAssigned(),
                editRepoAutomationTriggersSubIssueAdded(),
                editRepoAutomationTriggersPullRequestReadyForReview(),
                editRepoAutomationTriggersPullRequestLabeled(),
                editRepoAutomationTriggersPullRequestMergeConflict(),
                editRepoAutomationTriggersPullRequestReviewSubmitted(),
                editRepoAutomationTriggersPullRequestReviewRequested(),
                editRepoAutomationTriggersAutomationCompleted());
    }

    static GitHubEditRepoAutomationTriggersInterval editRepoAutomationTriggersInterval() {
        return new GitHubEditRepoAutomationTriggersInterval(
                List.of(editRepoAutomationTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                editRepoAutomationTriggersIntervalWriteScope());
    }

    static GitHubEditRepoAutomationTriggersIntervalCadence editRepoAutomationTriggersIntervalCadence() {
        return GitHubEditRepoAutomationTriggersIntervalCadence.HOURLY;
    }

    static GitHubEditRepoAutomationTriggersIntervalWriteScope editRepoAutomationTriggersIntervalWriteScope() {
        return GitHubEditRepoAutomationTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersIssueOpened editRepoAutomationTriggersIssueOpened() {
        return new GitHubEditRepoAutomationTriggersIssueOpened(
                "is:open label:bug", editRepoAutomationTriggersIssueOpenedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersIssueOpenedWriteScope editRepoAutomationTriggersIssueOpenedWriteScope() {
        return GitHubEditRepoAutomationTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersIssueLabeled editRepoAutomationTriggersIssueLabeled() {
        return new GitHubEditRepoAutomationTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), editRepoAutomationTriggersIssueLabeledWriteScope());
    }

    static GitHubEditRepoAutomationTriggersIssueLabeledWriteScope editRepoAutomationTriggersIssueLabeledWriteScope() {
        return GitHubEditRepoAutomationTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersWorkflowRun editRepoAutomationTriggersWorkflowRun() {
        return new GitHubEditRepoAutomationTriggersWorkflowRun(
                List.of("CI"),
                List.of(editRepoAutomationTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                editRepoAutomationTriggersWorkflowRunWriteScope());
    }

    static GitHubEditRepoAutomationTriggersWorkflowRunConclusion editRepoAutomationTriggersWorkflowRunConclusion() {
        return GitHubEditRepoAutomationTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubEditRepoAutomationTriggersWorkflowRunWriteScope editRepoAutomationTriggersWorkflowRunWriteScope() {
        return GitHubEditRepoAutomationTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestOpened editRepoAutomationTriggersPullRequestOpened() {
        return new GitHubEditRepoAutomationTriggersPullRequestOpened(
                "is:open label:bug", List.of("src/**"), editRepoAutomationTriggersPullRequestOpenedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestOpenedWriteScope
            editRepoAutomationTriggersPullRequestOpenedWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestSynchronize editRepoAutomationTriggersPullRequestSynchronize() {
        return new GitHubEditRepoAutomationTriggersPullRequestSynchronize(
                "is:open label:bug", List.of("src/**"), editRepoAutomationTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestSynchronizeWriteScope
            editRepoAutomationTriggersPullRequestSynchronizeWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestMerged editRepoAutomationTriggersPullRequestMerged() {
        return new GitHubEditRepoAutomationTriggersPullRequestMerged(
                "is:open label:bug", List.of("src/**"), editRepoAutomationTriggersPullRequestMergedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestMergedWriteScope
            editRepoAutomationTriggersPullRequestMergedWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersIssueComment editRepoAutomationTriggersIssueComment() {
        return new GitHubEditRepoAutomationTriggersIssueComment(
                "/triage", editRepoAutomationTriggersIssueCommentWriteScope());
    }

    static GitHubEditRepoAutomationTriggersIssueCommentWriteScope editRepoAutomationTriggersIssueCommentWriteScope() {
        return GitHubEditRepoAutomationTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestComment editRepoAutomationTriggersPullRequestComment() {
        return new GitHubEditRepoAutomationTriggersPullRequestComment(
                List.of(editRepoAutomationTriggersPullRequestCommentCommentType()),
                "/triage",
                editRepoAutomationTriggersPullRequestCommentWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestCommentCommentType
            editRepoAutomationTriggersPullRequestCommentCommentType() {
        return GitHubEditRepoAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubEditRepoAutomationTriggersPullRequestCommentWriteScope
            editRepoAutomationTriggersPullRequestCommentWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersDiscussionOpened editRepoAutomationTriggersDiscussionOpened() {
        return new GitHubEditRepoAutomationTriggersDiscussionOpened(
                List.of("General"), "/triage", editRepoAutomationTriggersDiscussionOpenedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersDiscussionOpenedWriteScope
            editRepoAutomationTriggersDiscussionOpenedWriteScope() {
        return GitHubEditRepoAutomationTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersDiscussionUpdated editRepoAutomationTriggersDiscussionUpdated() {
        return new GitHubEditRepoAutomationTriggersDiscussionUpdated(
                List.of("General"), "/triage", editRepoAutomationTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersDiscussionUpdatedWriteScope
            editRepoAutomationTriggersDiscussionUpdatedWriteScope() {
        return GitHubEditRepoAutomationTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersDiscussionComment editRepoAutomationTriggersDiscussionComment() {
        return new GitHubEditRepoAutomationTriggersDiscussionComment(
                List.of(editRepoAutomationTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                editRepoAutomationTriggersDiscussionCommentWriteScope());
    }

    static GitHubEditRepoAutomationTriggersDiscussionCommentCommentType
            editRepoAutomationTriggersDiscussionCommentCommentType() {
        return GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubEditRepoAutomationTriggersDiscussionCommentWriteScope
            editRepoAutomationTriggersDiscussionCommentWriteScope() {
        return GitHubEditRepoAutomationTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersIssueAssigned editRepoAutomationTriggersIssueAssigned() {
        return new GitHubEditRepoAutomationTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), editRepoAutomationTriggersIssueAssignedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersIssueAssignedWriteScope editRepoAutomationTriggersIssueAssignedWriteScope() {
        return GitHubEditRepoAutomationTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestAssigned editRepoAutomationTriggersPullRequestAssigned() {
        return new GitHubEditRepoAutomationTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                editRepoAutomationTriggersPullRequestAssignedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestAssignedWriteScope
            editRepoAutomationTriggersPullRequestAssignedWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersSubIssueAdded editRepoAutomationTriggersSubIssueAdded() {
        return new GitHubEditRepoAutomationTriggersSubIssueAdded(
                "is:open label:bug", editRepoAutomationTriggersSubIssueAddedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersSubIssueAddedWriteScope editRepoAutomationTriggersSubIssueAddedWriteScope() {
        return GitHubEditRepoAutomationTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestReadyForReview
            editRepoAutomationTriggersPullRequestReadyForReview() {
        return new GitHubEditRepoAutomationTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestReadyForReviewWriteScope
            editRepoAutomationTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestLabeled editRepoAutomationTriggersPullRequestLabeled() {
        return new GitHubEditRepoAutomationTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                editRepoAutomationTriggersPullRequestLabeledWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestLabeledWriteScope
            editRepoAutomationTriggersPullRequestLabeledWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestMergeConflict
            editRepoAutomationTriggersPullRequestMergeConflict() {
        return new GitHubEditRepoAutomationTriggersPullRequestMergeConflict(
                "is:open label:bug", List.of("src/**"), editRepoAutomationTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestMergeConflictWriteScope
            editRepoAutomationTriggersPullRequestMergeConflictWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewSubmitted
            editRepoAutomationTriggersPullRequestReviewSubmitted() {
        return new GitHubEditRepoAutomationTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(editRepoAutomationTriggersPullRequestReviewSubmittedState()),
                editRepoAutomationTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState
            editRepoAutomationTriggersPullRequestReviewSubmittedState() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedWriteScope
            editRepoAutomationTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewRequested
            editRepoAutomationTriggersPullRequestReviewRequested() {
        return new GitHubEditRepoAutomationTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewRequestedWriteScope
            editRepoAutomationTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationTriggersAutomationCompleted editRepoAutomationTriggersAutomationCompleted() {
        return new GitHubEditRepoAutomationTriggersAutomationCompleted(
                List.of("automation-2"), editRepoAutomationTriggersAutomationCompletedWriteScope());
    }

    static GitHubEditRepoAutomationTriggersAutomationCompletedWriteScope
            editRepoAutomationTriggersAutomationCompletedWriteScope() {
        return GitHubEditRepoAutomationTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponse editRepoAutomationResponse() {
        return new GitHubEditRepoAutomationResponse(
                "automation-1",
                "Nightly triage",
                true,
                editRepoAutomationResponseOwnerType(),
                editRepoAutomationResponseWriteScope(),
                true,
                editRepoAutomationResponseTriggers(),
                "Triage new issues and label them.",
                List.of("issues_read"),
                List.of("triage-agent"),
                "gpt-5",
                "medium",
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                editRepoAutomationResponseCreatedBy());
    }

    static GitHubEditRepoAutomationResponseOwnerType editRepoAutomationResponseOwnerType() {
        return GitHubEditRepoAutomationResponseOwnerType.USER;
    }

    static GitHubEditRepoAutomationResponseWriteScope editRepoAutomationResponseWriteScope() {
        return GitHubEditRepoAutomationResponseWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggers editRepoAutomationResponseTriggers() {
        return new GitHubEditRepoAutomationResponseTriggers(
                editRepoAutomationResponseTriggersInterval(),
                editRepoAutomationResponseTriggersIssueOpened(),
                editRepoAutomationResponseTriggersIssueLabeled(),
                editRepoAutomationResponseTriggersWorkflowRun(),
                editRepoAutomationResponseTriggersPullRequestOpened(),
                editRepoAutomationResponseTriggersPullRequestSynchronize(),
                editRepoAutomationResponseTriggersPullRequestMerged(),
                editRepoAutomationResponseTriggersIssueComment(),
                editRepoAutomationResponseTriggersPullRequestComment(),
                editRepoAutomationResponseTriggersDiscussionOpened(),
                editRepoAutomationResponseTriggersDiscussionUpdated(),
                editRepoAutomationResponseTriggersDiscussionComment(),
                editRepoAutomationResponseTriggersIssueAssigned(),
                editRepoAutomationResponseTriggersPullRequestAssigned(),
                editRepoAutomationResponseTriggersSubIssueAdded(),
                editRepoAutomationResponseTriggersPullRequestReadyForReview(),
                editRepoAutomationResponseTriggersPullRequestLabeled(),
                editRepoAutomationResponseTriggersPullRequestMergeConflict(),
                editRepoAutomationResponseTriggersPullRequestReviewSubmitted(),
                editRepoAutomationResponseTriggersPullRequestReviewRequested(),
                editRepoAutomationResponseTriggersAutomationCompleted());
    }

    static GitHubEditRepoAutomationResponseTriggersInterval editRepoAutomationResponseTriggersInterval() {
        return new GitHubEditRepoAutomationResponseTriggersInterval(
                List.of(editRepoAutomationResponseTriggersIntervalCadence()),
                9,
                1,
                GitHubAutomationMinuteUtc.FIFTEEN,
                editRepoAutomationResponseTriggersIntervalWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersIntervalCadence editRepoAutomationResponseTriggersIntervalCadence() {
        return GitHubEditRepoAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubEditRepoAutomationResponseTriggersIntervalWriteScope
            editRepoAutomationResponseTriggersIntervalWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersIntervalWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersIssueOpened editRepoAutomationResponseTriggersIssueOpened() {
        return new GitHubEditRepoAutomationResponseTriggersIssueOpened(
                "is:open label:bug", editRepoAutomationResponseTriggersIssueOpenedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersIssueOpenedWriteScope
            editRepoAutomationResponseTriggersIssueOpenedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersIssueOpenedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersIssueLabeled editRepoAutomationResponseTriggersIssueLabeled() {
        return new GitHubEditRepoAutomationResponseTriggersIssueLabeled(
                "is:open label:bug", List.of("bug"), editRepoAutomationResponseTriggersIssueLabeledWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersIssueLabeledWriteScope
            editRepoAutomationResponseTriggersIssueLabeledWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersIssueLabeledWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersWorkflowRun editRepoAutomationResponseTriggersWorkflowRun() {
        return new GitHubEditRepoAutomationResponseTriggersWorkflowRun(
                List.of("CI"),
                List.of(editRepoAutomationResponseTriggersWorkflowRunConclusion()),
                "is:open label:bug",
                editRepoAutomationResponseTriggersWorkflowRunWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion
            editRepoAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubEditRepoAutomationResponseTriggersWorkflowRunWriteScope
            editRepoAutomationResponseTriggersWorkflowRunWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersWorkflowRunWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestOpened
            editRepoAutomationResponseTriggersPullRequestOpened() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestOpened(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationResponseTriggersPullRequestOpenedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestOpenedWriteScope
            editRepoAutomationResponseTriggersPullRequestOpenedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestOpenedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestSynchronize
            editRepoAutomationResponseTriggersPullRequestSynchronize() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestSynchronize(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope
            editRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestMerged
            editRepoAutomationResponseTriggersPullRequestMerged() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestMerged(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationResponseTriggersPullRequestMergedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestMergedWriteScope
            editRepoAutomationResponseTriggersPullRequestMergedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestMergedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersIssueComment editRepoAutomationResponseTriggersIssueComment() {
        return new GitHubEditRepoAutomationResponseTriggersIssueComment(
                "/triage", editRepoAutomationResponseTriggersIssueCommentWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersIssueCommentWriteScope
            editRepoAutomationResponseTriggersIssueCommentWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersIssueCommentWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestComment
            editRepoAutomationResponseTriggersPullRequestComment() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestComment(
                List.of(editRepoAutomationResponseTriggersPullRequestCommentCommentType()),
                "/triage",
                editRepoAutomationResponseTriggersPullRequestCommentWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType
            editRepoAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestCommentWriteScope
            editRepoAutomationResponseTriggersPullRequestCommentWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestCommentWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionOpened
            editRepoAutomationResponseTriggersDiscussionOpened() {
        return new GitHubEditRepoAutomationResponseTriggersDiscussionOpened(
                List.of("General"), "/triage", editRepoAutomationResponseTriggersDiscussionOpenedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionOpenedWriteScope
            editRepoAutomationResponseTriggersDiscussionOpenedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionOpenedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionUpdated
            editRepoAutomationResponseTriggersDiscussionUpdated() {
        return new GitHubEditRepoAutomationResponseTriggersDiscussionUpdated(
                List.of("General"), "/triage", editRepoAutomationResponseTriggersDiscussionUpdatedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionUpdatedWriteScope
            editRepoAutomationResponseTriggersDiscussionUpdatedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionUpdatedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionComment
            editRepoAutomationResponseTriggersDiscussionComment() {
        return new GitHubEditRepoAutomationResponseTriggersDiscussionComment(
                List.of(editRepoAutomationResponseTriggersDiscussionCommentCommentType()),
                List.of("General"),
                "/triage",
                editRepoAutomationResponseTriggersDiscussionCommentWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType
            editRepoAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionCommentWriteScope
            editRepoAutomationResponseTriggersDiscussionCommentWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionCommentWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersIssueAssigned editRepoAutomationResponseTriggersIssueAssigned() {
        return new GitHubEditRepoAutomationResponseTriggersIssueAssigned(
                "is:open label:bug", List.of("octocat"), editRepoAutomationResponseTriggersIssueAssignedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersIssueAssignedWriteScope
            editRepoAutomationResponseTriggersIssueAssignedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersIssueAssignedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestAssigned
            editRepoAutomationResponseTriggersPullRequestAssigned() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestAssigned(
                "is:open label:bug",
                List.of("src/**"),
                List.of("octocat"),
                editRepoAutomationResponseTriggersPullRequestAssignedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestAssignedWriteScope
            editRepoAutomationResponseTriggersPullRequestAssignedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestAssignedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersSubIssueAdded editRepoAutomationResponseTriggersSubIssueAdded() {
        return new GitHubEditRepoAutomationResponseTriggersSubIssueAdded(
                "is:open label:bug", editRepoAutomationResponseTriggersSubIssueAddedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersSubIssueAddedWriteScope
            editRepoAutomationResponseTriggersSubIssueAddedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersSubIssueAddedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReview
            editRepoAutomationResponseTriggersPullRequestReadyForReview() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReview(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope
            editRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReviewWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestLabeled
            editRepoAutomationResponseTriggersPullRequestLabeled() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestLabeled(
                "is:open label:bug",
                List.of("src/**"),
                List.of("bug"),
                editRepoAutomationResponseTriggersPullRequestLabeledWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestLabeledWriteScope
            editRepoAutomationResponseTriggersPullRequestLabeledWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestLabeledWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflict
            editRepoAutomationResponseTriggersPullRequestMergeConflict() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflict(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope
            editRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflictWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmitted
            editRepoAutomationResponseTriggersPullRequestReviewSubmitted() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmitted(
                "is:open label:bug",
                List.of("src/**"),
                List.of(editRepoAutomationResponseTriggersPullRequestReviewSubmittedState()),
                editRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState
            editRepoAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope
            editRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequested
            editRepoAutomationResponseTriggersPullRequestReviewRequested() {
        return new GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequested(
                "is:open label:bug",
                List.of("src/**"),
                editRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope
            editRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequestedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseTriggersAutomationCompleted
            editRepoAutomationResponseTriggersAutomationCompleted() {
        return new GitHubEditRepoAutomationResponseTriggersAutomationCompleted(
                List.of("automation-2"), editRepoAutomationResponseTriggersAutomationCompletedWriteScope());
    }

    static GitHubEditRepoAutomationResponseTriggersAutomationCompletedWriteScope
            editRepoAutomationResponseTriggersAutomationCompletedWriteScope() {
        return GitHubEditRepoAutomationResponseTriggersAutomationCompletedWriteScope.TRIGGER;
    }

    static GitHubEditRepoAutomationResponseCreatedBy editRepoAutomationResponseCreatedBy() {
        return new GitHubEditRepoAutomationResponseCreatedBy(1L);
    }

    static GitHubListAutomationsOwnerType listAutomationsOwnerType() {
        return GitHubListAutomationsOwnerType.USER;
    }

    static GitHubListAutomationsFilter listAutomationsFilter() {
        return new GitHubListAutomationsFilter(listAutomationsOwnerType());
    }

    static GitHubListAutomationTasksSort listAutomationTasksSort() {
        return GitHubListAutomationTasksSort.UPDATED_AT;
    }

    static GitHubListAutomationTasksDirection listAutomationTasksDirection() {
        return GitHubListAutomationTasksDirection.ASC;
    }

    static GitHubListAutomationTasksFilter listAutomationTasksFilter() {
        return new GitHubListAutomationTasksFilter(
                listAutomationTasksSort(), listAutomationTasksDirection(), "completed", false);
    }

    static GitHubListRepoAutomationsOwnerType listRepoAutomationsOwnerType() {
        return GitHubListRepoAutomationsOwnerType.USER;
    }

    static GitHubListRepoAutomationsFilter listRepoAutomationsFilter() {
        return new GitHubListRepoAutomationsFilter(listRepoAutomationsOwnerType());
    }
}
