package io.github.piotr_janczukowicz.github.api.agentautomations;

import java.time.Instant;
import java.util.List;

final class AgentAutomationsCatalogFixtures {

    private AgentAutomationsCatalogFixtures() {}

    static GitHubListAutomationsItemTriggersIntervalCadence listAutomationsItemTriggersIntervalCadence() {
        return GitHubListAutomationsItemTriggersIntervalCadence.HOURLY;
    }

    static GitHubListAutomationsItemTriggersWorkflowRunConclusion listAutomationsItemTriggersWorkflowRunConclusion() {
        return GitHubListAutomationsItemTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubListAutomationsItemTriggersPullRequestCommentCommentType
            listAutomationsItemTriggersPullRequestCommentCommentType() {
        return GitHubListAutomationsItemTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubListAutomationsItemTriggersDiscussionCommentCommentType
            listAutomationsItemTriggersDiscussionCommentCommentType() {
        return GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState
            listAutomationsItemTriggersPullRequestReviewSubmittedState() {
        return GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString
            listAvailableRepoAutomationTriggersItemFieldSchemaString() {
        return new GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString(
                listAvailableRepoAutomationTriggersItemFieldSchemaStringType(), List.of("low", "high"), 1);
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringType
            listAvailableRepoAutomationTriggersItemFieldSchemaStringType() {
        return GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringType.STRING;
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArray
            listAvailableRepoAutomationTriggersItemFieldSchemaArray() {
        return new GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArray(
                listAvailableRepoAutomationTriggersItemFieldSchemaArrayType(),
                listAvailableRepoAutomationTriggersItemFieldSchemaArrayItems(),
                1,
                1,
                true);
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayType
            listAvailableRepoAutomationTriggersItemFieldSchemaArrayType() {
        return GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayType.ARRAY;
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItems
            listAvailableRepoAutomationTriggersItemFieldSchemaArrayItems() {
        return new GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItems(
                listAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType(), List.of("low", "high"), 1);
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType
            listAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType() {
        return GitHubListAvailableRepoAutomationTriggersItemFieldSchemaArrayItemsType.STRING;
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaInteger
            listAvailableRepoAutomationTriggersItemFieldSchemaInteger() {
        return new GitHubListAvailableRepoAutomationTriggersItemFieldSchemaInteger(
                listAvailableRepoAutomationTriggersItemFieldSchemaIntegerType(), List.of(1, 2), 0, 10);
    }

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaIntegerType
            listAvailableRepoAutomationTriggersItemFieldSchemaIntegerType() {
        return GitHubListAvailableRepoAutomationTriggersItemFieldSchemaIntegerType.INTEGER;
    }

    static GitHubListRepoAutomationsResponse listRepoAutomationsResponse() {
        return new GitHubListRepoAutomationsResponse(List.of(listRepoAutomationsAutomation()), 1);
    }

    static GitHubListRepoAutomationsAutomationTriggersIntervalCadence
            listRepoAutomationsAutomationTriggersIntervalCadence() {
        return GitHubListRepoAutomationsAutomationTriggersIntervalCadence.HOURLY;
    }

    static GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion
            listRepoAutomationsAutomationTriggersWorkflowRunConclusion() {
        return GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType
            listRepoAutomationsAutomationTriggersPullRequestCommentCommentType() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType
            listRepoAutomationsAutomationTriggersDiscussionCommentCommentType() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState
            listRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubCreateRepoAutomationTriggersIntervalCadence createRepoAutomationTriggersIntervalCadence() {
        return GitHubCreateRepoAutomationTriggersIntervalCadence.HOURLY;
    }

    static GitHubCreateRepoAutomationTriggersWorkflowRunConclusion createRepoAutomationTriggersWorkflowRunConclusion() {
        return GitHubCreateRepoAutomationTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestCommentCommentType
            createRepoAutomationTriggersPullRequestCommentCommentType() {
        return GitHubCreateRepoAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType
            createRepoAutomationTriggersDiscussionCommentCommentType() {
        return GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState
            createRepoAutomationTriggersPullRequestReviewSubmittedState() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubCreateRepoAutomationResponseTriggersIntervalCadence
            createRepoAutomationResponseTriggersIntervalCadence() {
        return GitHubCreateRepoAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion
            createRepoAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType
            createRepoAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType
            createRepoAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState
            createRepoAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubGetRepoAutomationResponseTriggersIntervalCadence getRepoAutomationResponseTriggersIntervalCadence() {
        return GitHubGetRepoAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion
            getRepoAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestCommentCommentType
            getRepoAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType
            getRepoAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState
            getRepoAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubEditRepoAutomationTriggersIntervalCadence editRepoAutomationTriggersIntervalCadence() {
        return GitHubEditRepoAutomationTriggersIntervalCadence.HOURLY;
    }

    static GitHubEditRepoAutomationTriggersWorkflowRunConclusion editRepoAutomationTriggersWorkflowRunConclusion() {
        return GitHubEditRepoAutomationTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubEditRepoAutomationTriggersPullRequestCommentCommentType
            editRepoAutomationTriggersPullRequestCommentCommentType() {
        return GitHubEditRepoAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubEditRepoAutomationTriggersDiscussionCommentCommentType
            editRepoAutomationTriggersDiscussionCommentCommentType() {
        return GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState
            editRepoAutomationTriggersPullRequestReviewSubmittedState() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubEditRepoAutomationResponseTriggersIntervalCadence editRepoAutomationResponseTriggersIntervalCadence() {
        return GitHubEditRepoAutomationResponseTriggersIntervalCadence.HOURLY;
    }

    static GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion
            editRepoAutomationResponseTriggersWorkflowRunConclusion() {
        return GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType
            editRepoAutomationResponseTriggersPullRequestCommentCommentType() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType
            editRepoAutomationResponseTriggersDiscussionCommentCommentType() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT;
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState
            editRepoAutomationResponseTriggersPullRequestReviewSubmittedState() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubListAutomationsType listAutomationsType() {
        return GitHubListAutomationsType.PERSONAL;
    }

    static GitHubListAutomationsFilter listAutomationsFilter() {
        return new GitHubListAutomationsFilter(listAutomationsType());
    }

    static GitHubListRepoAutomationsType listRepoAutomationsType() {
        return GitHubListRepoAutomationsType.PERSONAL;
    }

    static GitHubListRepoAutomationsFilter listRepoAutomationsFilter() {
        return new GitHubListRepoAutomationsFilter(listRepoAutomationsType());
    }

    static GitHubListAvailableRepoAutomationTriggersItem listAvailableRepoAutomationTriggersItem() {
        return new GitHubListAvailableRepoAutomationTriggersItem(
                "automation-1",
                "Issue tools",
                "Reads issues and pull requests.",
                List.of(listAvailableRepoAutomationTriggersItemField()));
    }

    static GitHubListAvailableRepoAutomationTriggersItemField listAvailableRepoAutomationTriggersItemField() {
        return new GitHubListAvailableRepoAutomationTriggersItemField(
                "automation-1",
                "Branch",
                true,
                "Reads issues and pull requests.",
                listAvailableRepoAutomationTriggersItemFieldSchemaString());
    }

    static GitHubListRepoAutomationTasksResponse listRepoAutomationTasksResponse() {
        return new GitHubListRepoAutomationTasksResponse(List.of(listRepoAutomationTasksTask()));
    }

    static GitHubListRepoAutomationTasksTask listRepoAutomationTasksTask() {
        return new GitHubListRepoAutomationTasksTask(
                "automation-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Nightly triage",
                List.of(listRepoAutomationTasksTaskClient()),
                listRepoAutomationTasksTaskCreator(),
                listRepoAutomationTasksTaskCreatorType(),
                List.of(listRepoAutomationTasksTaskUserCollaborator()),
                listRepoAutomationTasksTaskOwner(),
                listRepoAutomationTasksTaskRepository(),
                listRepoAutomationTasksTaskState(),
                1,
                List.of(listRepoAutomationTasksTaskArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                listRepoAutomationTasksTaskCustomAgent());
    }

    static GitHubListRepoAutomationTasksTaskCreator listRepoAutomationTasksTaskCreator() {
        return new GitHubListRepoAutomationTasksTaskCreator(1L);
    }

    static GitHubListRepoAutomationTasksTaskCreatorType listRepoAutomationTasksTaskCreatorType() {
        return GitHubListRepoAutomationTasksTaskCreatorType.USER;
    }

    static GitHubListRepoAutomationTasksTaskUserCollaborator listRepoAutomationTasksTaskUserCollaborator() {
        return new GitHubListRepoAutomationTasksTaskUserCollaborator(1L);
    }

    static GitHubListRepoAutomationTasksTaskOwner listRepoAutomationTasksTaskOwner() {
        return new GitHubListRepoAutomationTasksTaskOwner(1L);
    }

    static GitHubListRepoAutomationTasksTaskRepository listRepoAutomationTasksTaskRepository() {
        return new GitHubListRepoAutomationTasksTaskRepository(1L);
    }

    static GitHubListRepoAutomationTasksTaskState listRepoAutomationTasksTaskState() {
        return GitHubListRepoAutomationTasksTaskState.QUEUED;
    }

    static GitHubListRepoAutomationTasksTaskArtifact listRepoAutomationTasksTaskArtifact() {
        return new GitHubListRepoAutomationTasksTaskArtifact(
                listRepoAutomationTasksTaskArtifactProvider(),
                listRepoAutomationTasksTaskArtifactType(),
                listRepoAutomationTasksTaskArtifactDataById());
    }

    static GitHubListRepoAutomationTasksTaskArtifactProvider listRepoAutomationTasksTaskArtifactProvider() {
        return GitHubListRepoAutomationTasksTaskArtifactProvider.GITHUB;
    }

    static GitHubListRepoAutomationTasksTaskArtifactType listRepoAutomationTasksTaskArtifactType() {
        return GitHubListRepoAutomationTasksTaskArtifactType.PULL;
    }

    static GitHubListRepoAutomationTasksTaskArtifactDataById listRepoAutomationTasksTaskArtifactDataById() {
        return new GitHubListRepoAutomationTasksTaskArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubListRepoAutomationTasksTaskArtifactDataByRef listRepoAutomationTasksTaskArtifactDataByRef() {
        return new GitHubListRepoAutomationTasksTaskArtifactDataByRef("feature/fix", "main");
    }

    static GitHubListRepoAutomationTasksTaskCustomAgent listRepoAutomationTasksTaskCustomAgent() {
        return new GitHubListRepoAutomationTasksTaskCustomAgent("automation-1");
    }

    static GitHubListRepoAutomationTasksSort listRepoAutomationTasksSort() {
        return GitHubListRepoAutomationTasksSort.UPDATED_AT;
    }

    static GitHubListRepoAutomationTasksDirection listRepoAutomationTasksDirection() {
        return GitHubListRepoAutomationTasksDirection.ASC;
    }

    static GitHubListRepoAutomationTasksFilter listRepoAutomationTasksFilter() {
        return new GitHubListRepoAutomationTasksFilter(
                listRepoAutomationTasksSort(), listRepoAutomationTasksDirection(), "completed", false);
    }

    static GitHubListRepoAutomationTasksTaskClient listRepoAutomationTasksTaskClient() {
        return GitHubListRepoAutomationTasksTaskClientBuilder.builder()
                .integrationId("github-mobile-ios")
                .slug("copilot-mobile-ios")
                .creator(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersInterval createRepoAutomationTriggersInterval() {
        return GitHubCreateRepoAutomationTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubCreateRepoAutomationTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersIssueOpened createRepoAutomationTriggersIssueOpened() {
        return GitHubCreateRepoAutomationTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersIssueLabeled createRepoAutomationTriggersIssueLabeled() {
        return GitHubCreateRepoAutomationTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersWorkflowRun createRepoAutomationTriggersWorkflowRun() {
        return GitHubCreateRepoAutomationTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubCreateRepoAutomationTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestOpened createRepoAutomationTriggersPullRequestOpened() {
        return GitHubCreateRepoAutomationTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestSynchronize
            createRepoAutomationTriggersPullRequestSynchronize() {
        return GitHubCreateRepoAutomationTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestMerged createRepoAutomationTriggersPullRequestMerged() {
        return GitHubCreateRepoAutomationTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersIssueComment createRepoAutomationTriggersIssueComment() {
        return GitHubCreateRepoAutomationTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestComment createRepoAutomationTriggersPullRequestComment() {
        return GitHubCreateRepoAutomationTriggersPullRequestCommentBuilder.builder()
                .commentTypes(List.of(GitHubCreateRepoAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersDiscussionOpened createRepoAutomationTriggersDiscussionOpened() {
        return GitHubCreateRepoAutomationTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersDiscussionUpdated createRepoAutomationTriggersDiscussionUpdated() {
        return GitHubCreateRepoAutomationTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersDiscussionComment createRepoAutomationTriggersDiscussionComment() {
        return GitHubCreateRepoAutomationTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubCreateRepoAutomationTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersIssueAssigned createRepoAutomationTriggersIssueAssigned() {
        return GitHubCreateRepoAutomationTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestAssigned createRepoAutomationTriggersPullRequestAssigned() {
        return GitHubCreateRepoAutomationTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersSubIssueAdded createRepoAutomationTriggersSubIssueAdded() {
        return GitHubCreateRepoAutomationTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReadyForReview
            createRepoAutomationTriggersPullRequestReadyForReview() {
        return GitHubCreateRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestLabeled createRepoAutomationTriggersPullRequestLabeled() {
        return GitHubCreateRepoAutomationTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestMergeConflict
            createRepoAutomationTriggersPullRequestMergeConflict() {
        return GitHubCreateRepoAutomationTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewSubmitted
            createRepoAutomationTriggersPullRequestReviewSubmitted() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubCreateRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewRequested
            createRepoAutomationTriggersPullRequestReviewRequested() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersIssueClosed createRepoAutomationTriggersIssueClosed() {
        return GitHubCreateRepoAutomationTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersIssueReopened createRepoAutomationTriggersIssueReopened() {
        return GitHubCreateRepoAutomationTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestClosed createRepoAutomationTriggersPullRequestClosed() {
        return GitHubCreateRepoAutomationTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReopened createRepoAutomationTriggersPullRequestReopened() {
        return GitHubCreateRepoAutomationTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraft
            createRepoAutomationTriggersPullRequestConvertedToDraft() {
        return GitHubCreateRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewDismissed
            createRepoAutomationTriggersPullRequestReviewDismissed() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewThreadResolved
            createRepoAutomationTriggersPullRequestReviewThreadResolved() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersPullRequestReviewThreadUnresolved
            createRepoAutomationTriggersPullRequestReviewThreadUnresolved() {
        return GitHubCreateRepoAutomationTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersAutomationCompleted createRepoAutomationTriggersAutomationCompleted() {
        return GitHubCreateRepoAutomationTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersAutomationFailed createRepoAutomationTriggersAutomationFailed() {
        return GitHubCreateRepoAutomationTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubCreateRepoAutomationTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersCodeScanningAlert createRepoAutomationTriggersCodeScanningAlert() {
        return GitHubCreateRepoAutomationTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersSecretScanningAlert createRepoAutomationTriggersSecretScanningAlert() {
        return GitHubCreateRepoAutomationTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggersDependabotAlert createRepoAutomationTriggersDependabotAlert() {
        return GitHubCreateRepoAutomationTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationTriggers createRepoAutomationTriggers() {
        return GitHubCreateRepoAutomationTriggersBuilder.builder()
                .interval(createRepoAutomationTriggersInterval())
                .issueOpened(createRepoAutomationTriggersIssueOpened())
                .issueLabeled(createRepoAutomationTriggersIssueLabeled())
                .workflowRun(createRepoAutomationTriggersWorkflowRun())
                .pullRequestOpened(createRepoAutomationTriggersPullRequestOpened())
                .pullRequestSynchronize(createRepoAutomationTriggersPullRequestSynchronize())
                .pullRequestMerged(createRepoAutomationTriggersPullRequestMerged())
                .issueComment(createRepoAutomationTriggersIssueComment())
                .pullRequestComment(createRepoAutomationTriggersPullRequestComment())
                .discussionOpened(createRepoAutomationTriggersDiscussionOpened())
                .discussionUpdated(createRepoAutomationTriggersDiscussionUpdated())
                .discussionComment(createRepoAutomationTriggersDiscussionComment())
                .issueAssigned(createRepoAutomationTriggersIssueAssigned())
                .pullRequestAssigned(createRepoAutomationTriggersPullRequestAssigned())
                .subIssueAdded(createRepoAutomationTriggersSubIssueAdded())
                .pullRequestReadyForReview(createRepoAutomationTriggersPullRequestReadyForReview())
                .pullRequestLabeled(createRepoAutomationTriggersPullRequestLabeled())
                .pullRequestMergeConflict(createRepoAutomationTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(createRepoAutomationTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(createRepoAutomationTriggersPullRequestReviewRequested())
                .issueClosed(createRepoAutomationTriggersIssueClosed())
                .issueReopened(createRepoAutomationTriggersIssueReopened())
                .pullRequestClosed(createRepoAutomationTriggersPullRequestClosed())
                .pullRequestReopened(createRepoAutomationTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(createRepoAutomationTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(createRepoAutomationTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(createRepoAutomationTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(createRepoAutomationTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(createRepoAutomationTriggersAutomationCompleted())
                .automationFailed(createRepoAutomationTriggersAutomationFailed())
                .codeScanningAlert(createRepoAutomationTriggersCodeScanningAlert())
                .secretScanningAlert(createRepoAutomationTriggersSecretScanningAlert())
                .dependabotAlert(createRepoAutomationTriggersDependabotAlert())
                .build();
    }

    static GitHubCreateRepoAutomationPayload createRepoAutomationPayload() {
        return GitHubCreateRepoAutomationPayloadBuilder.builder()
                .name("name")
                .triggers(createRepoAutomationTriggers())
                .prompt("prompt")
                .model("model")
                .reasoningEffort(GitHubCreateRepoAutomationReasoningEffort.NONE)
                .tools(List.of("tools"))
                .enabled(true)
                .type(GitHubCreateRepoAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersInterval createRepoAutomationResponseTriggersInterval() {
        return GitHubCreateRepoAutomationResponseTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubCreateRepoAutomationResponseTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueOpened createRepoAutomationResponseTriggersIssueOpened() {
        return GitHubCreateRepoAutomationResponseTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueLabeled createRepoAutomationResponseTriggersIssueLabeled() {
        return GitHubCreateRepoAutomationResponseTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersWorkflowRun createRepoAutomationResponseTriggersWorkflowRun() {
        return GitHubCreateRepoAutomationResponseTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubCreateRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestOpened
            createRepoAutomationResponseTriggersPullRequestOpened() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestSynchronize
            createRepoAutomationResponseTriggersPullRequestSynchronize() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestMerged
            createRepoAutomationResponseTriggersPullRequestMerged() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueComment createRepoAutomationResponseTriggersIssueComment() {
        return GitHubCreateRepoAutomationResponseTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestComment
            createRepoAutomationResponseTriggersPullRequestComment() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestCommentBuilder.builder()
                .commentTypes(List.of(
                        GitHubCreateRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionOpened
            createRepoAutomationResponseTriggersDiscussionOpened() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionUpdated
            createRepoAutomationResponseTriggersDiscussionUpdated() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersDiscussionComment
            createRepoAutomationResponseTriggersDiscussionComment() {
        return GitHubCreateRepoAutomationResponseTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubCreateRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueAssigned createRepoAutomationResponseTriggersIssueAssigned() {
        return GitHubCreateRepoAutomationResponseTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestAssigned
            createRepoAutomationResponseTriggersPullRequestAssigned() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersSubIssueAdded createRepoAutomationResponseTriggersSubIssueAdded() {
        return GitHubCreateRepoAutomationResponseTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReadyForReview
            createRepoAutomationResponseTriggersPullRequestReadyForReview() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestLabeled
            createRepoAutomationResponseTriggersPullRequestLabeled() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflict
            createRepoAutomationResponseTriggersPullRequestMergeConflict() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmitted
            createRepoAutomationResponseTriggersPullRequestReviewSubmitted() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubCreateRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewRequested
            createRepoAutomationResponseTriggersPullRequestReviewRequested() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueClosed createRepoAutomationResponseTriggersIssueClosed() {
        return GitHubCreateRepoAutomationResponseTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersIssueReopened createRepoAutomationResponseTriggersIssueReopened() {
        return GitHubCreateRepoAutomationResponseTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestClosed
            createRepoAutomationResponseTriggersPullRequestClosed() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReopened
            createRepoAutomationResponseTriggersPullRequestReopened() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestConvertedToDraft
            createRepoAutomationResponseTriggersPullRequestConvertedToDraft() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewDismissed
            createRepoAutomationResponseTriggersPullRequestReviewDismissed() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewThreadResolved
            createRepoAutomationResponseTriggersPullRequestReviewThreadResolved() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved
            createRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved() {
        return GitHubCreateRepoAutomationResponseTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersAutomationCompleted
            createRepoAutomationResponseTriggersAutomationCompleted() {
        return GitHubCreateRepoAutomationResponseTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersAutomationFailed
            createRepoAutomationResponseTriggersAutomationFailed() {
        return GitHubCreateRepoAutomationResponseTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubCreateRepoAutomationResponseTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersCodeScanningAlert
            createRepoAutomationResponseTriggersCodeScanningAlert() {
        return GitHubCreateRepoAutomationResponseTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersSecretScanningAlert
            createRepoAutomationResponseTriggersSecretScanningAlert() {
        return GitHubCreateRepoAutomationResponseTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggersDependabotAlert
            createRepoAutomationResponseTriggersDependabotAlert() {
        return GitHubCreateRepoAutomationResponseTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubCreateRepoAutomationResponseTriggers createRepoAutomationResponseTriggers() {
        return GitHubCreateRepoAutomationResponseTriggersBuilder.builder()
                .interval(createRepoAutomationResponseTriggersInterval())
                .issueOpened(createRepoAutomationResponseTriggersIssueOpened())
                .issueLabeled(createRepoAutomationResponseTriggersIssueLabeled())
                .workflowRun(createRepoAutomationResponseTriggersWorkflowRun())
                .pullRequestOpened(createRepoAutomationResponseTriggersPullRequestOpened())
                .pullRequestSynchronize(createRepoAutomationResponseTriggersPullRequestSynchronize())
                .pullRequestMerged(createRepoAutomationResponseTriggersPullRequestMerged())
                .issueComment(createRepoAutomationResponseTriggersIssueComment())
                .pullRequestComment(createRepoAutomationResponseTriggersPullRequestComment())
                .discussionOpened(createRepoAutomationResponseTriggersDiscussionOpened())
                .discussionUpdated(createRepoAutomationResponseTriggersDiscussionUpdated())
                .discussionComment(createRepoAutomationResponseTriggersDiscussionComment())
                .issueAssigned(createRepoAutomationResponseTriggersIssueAssigned())
                .pullRequestAssigned(createRepoAutomationResponseTriggersPullRequestAssigned())
                .subIssueAdded(createRepoAutomationResponseTriggersSubIssueAdded())
                .pullRequestReadyForReview(createRepoAutomationResponseTriggersPullRequestReadyForReview())
                .pullRequestLabeled(createRepoAutomationResponseTriggersPullRequestLabeled())
                .pullRequestMergeConflict(createRepoAutomationResponseTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(createRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(createRepoAutomationResponseTriggersPullRequestReviewRequested())
                .issueClosed(createRepoAutomationResponseTriggersIssueClosed())
                .issueReopened(createRepoAutomationResponseTriggersIssueReopened())
                .pullRequestClosed(createRepoAutomationResponseTriggersPullRequestClosed())
                .pullRequestReopened(createRepoAutomationResponseTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(createRepoAutomationResponseTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(createRepoAutomationResponseTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(createRepoAutomationResponseTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(
                        createRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(createRepoAutomationResponseTriggersAutomationCompleted())
                .automationFailed(createRepoAutomationResponseTriggersAutomationFailed())
                .codeScanningAlert(createRepoAutomationResponseTriggersCodeScanningAlert())
                .secretScanningAlert(createRepoAutomationResponseTriggersSecretScanningAlert())
                .dependabotAlert(createRepoAutomationResponseTriggersDependabotAlert())
                .build();
    }

    static GitHubCreateRepoAutomationResponseCreatedBy createRepoAutomationResponseCreatedBy() {
        return GitHubCreateRepoAutomationResponseCreatedByBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubCreateRepoAutomationResponse createRepoAutomationResponse() {
        return GitHubCreateRepoAutomationResponseBuilder.builder()
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubCreateRepoAutomationResponseType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .triggers(createRepoAutomationResponseTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(createRepoAutomationResponseCreatedBy())
                .build();
    }

    static GitHubEditRepoAutomationTriggersInterval editRepoAutomationTriggersInterval() {
        return GitHubEditRepoAutomationTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubEditRepoAutomationTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersIssueOpened editRepoAutomationTriggersIssueOpened() {
        return GitHubEditRepoAutomationTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersIssueLabeled editRepoAutomationTriggersIssueLabeled() {
        return GitHubEditRepoAutomationTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersWorkflowRun editRepoAutomationTriggersWorkflowRun() {
        return GitHubEditRepoAutomationTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubEditRepoAutomationTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestOpened editRepoAutomationTriggersPullRequestOpened() {
        return GitHubEditRepoAutomationTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestSynchronize editRepoAutomationTriggersPullRequestSynchronize() {
        return GitHubEditRepoAutomationTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestMerged editRepoAutomationTriggersPullRequestMerged() {
        return GitHubEditRepoAutomationTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersIssueComment editRepoAutomationTriggersIssueComment() {
        return GitHubEditRepoAutomationTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestComment editRepoAutomationTriggersPullRequestComment() {
        return GitHubEditRepoAutomationTriggersPullRequestCommentBuilder.builder()
                .commentTypes(List.of(GitHubEditRepoAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersDiscussionOpened editRepoAutomationTriggersDiscussionOpened() {
        return GitHubEditRepoAutomationTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersDiscussionUpdated editRepoAutomationTriggersDiscussionUpdated() {
        return GitHubEditRepoAutomationTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersDiscussionComment editRepoAutomationTriggersDiscussionComment() {
        return GitHubEditRepoAutomationTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubEditRepoAutomationTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersIssueAssigned editRepoAutomationTriggersIssueAssigned() {
        return GitHubEditRepoAutomationTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestAssigned editRepoAutomationTriggersPullRequestAssigned() {
        return GitHubEditRepoAutomationTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersSubIssueAdded editRepoAutomationTriggersSubIssueAdded() {
        return GitHubEditRepoAutomationTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReadyForReview
            editRepoAutomationTriggersPullRequestReadyForReview() {
        return GitHubEditRepoAutomationTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestLabeled editRepoAutomationTriggersPullRequestLabeled() {
        return GitHubEditRepoAutomationTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestMergeConflict
            editRepoAutomationTriggersPullRequestMergeConflict() {
        return GitHubEditRepoAutomationTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewSubmitted
            editRepoAutomationTriggersPullRequestReviewSubmitted() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubEditRepoAutomationTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewRequested
            editRepoAutomationTriggersPullRequestReviewRequested() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersIssueClosed editRepoAutomationTriggersIssueClosed() {
        return GitHubEditRepoAutomationTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersIssueReopened editRepoAutomationTriggersIssueReopened() {
        return GitHubEditRepoAutomationTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestClosed editRepoAutomationTriggersPullRequestClosed() {
        return GitHubEditRepoAutomationTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReopened editRepoAutomationTriggersPullRequestReopened() {
        return GitHubEditRepoAutomationTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestConvertedToDraft
            editRepoAutomationTriggersPullRequestConvertedToDraft() {
        return GitHubEditRepoAutomationTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewDismissed
            editRepoAutomationTriggersPullRequestReviewDismissed() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewThreadResolved
            editRepoAutomationTriggersPullRequestReviewThreadResolved() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersPullRequestReviewThreadUnresolved
            editRepoAutomationTriggersPullRequestReviewThreadUnresolved() {
        return GitHubEditRepoAutomationTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersAutomationCompleted editRepoAutomationTriggersAutomationCompleted() {
        return GitHubEditRepoAutomationTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersAutomationFailed editRepoAutomationTriggersAutomationFailed() {
        return GitHubEditRepoAutomationTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubEditRepoAutomationTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersCodeScanningAlert editRepoAutomationTriggersCodeScanningAlert() {
        return GitHubEditRepoAutomationTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersSecretScanningAlert editRepoAutomationTriggersSecretScanningAlert() {
        return GitHubEditRepoAutomationTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggersDependabotAlert editRepoAutomationTriggersDependabotAlert() {
        return GitHubEditRepoAutomationTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationTriggers editRepoAutomationTriggers() {
        return GitHubEditRepoAutomationTriggersBuilder.builder()
                .interval(editRepoAutomationTriggersInterval())
                .issueOpened(editRepoAutomationTriggersIssueOpened())
                .issueLabeled(editRepoAutomationTriggersIssueLabeled())
                .workflowRun(editRepoAutomationTriggersWorkflowRun())
                .pullRequestOpened(editRepoAutomationTriggersPullRequestOpened())
                .pullRequestSynchronize(editRepoAutomationTriggersPullRequestSynchronize())
                .pullRequestMerged(editRepoAutomationTriggersPullRequestMerged())
                .issueComment(editRepoAutomationTriggersIssueComment())
                .pullRequestComment(editRepoAutomationTriggersPullRequestComment())
                .discussionOpened(editRepoAutomationTriggersDiscussionOpened())
                .discussionUpdated(editRepoAutomationTriggersDiscussionUpdated())
                .discussionComment(editRepoAutomationTriggersDiscussionComment())
                .issueAssigned(editRepoAutomationTriggersIssueAssigned())
                .pullRequestAssigned(editRepoAutomationTriggersPullRequestAssigned())
                .subIssueAdded(editRepoAutomationTriggersSubIssueAdded())
                .pullRequestReadyForReview(editRepoAutomationTriggersPullRequestReadyForReview())
                .pullRequestLabeled(editRepoAutomationTriggersPullRequestLabeled())
                .pullRequestMergeConflict(editRepoAutomationTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(editRepoAutomationTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(editRepoAutomationTriggersPullRequestReviewRequested())
                .issueClosed(editRepoAutomationTriggersIssueClosed())
                .issueReopened(editRepoAutomationTriggersIssueReopened())
                .pullRequestClosed(editRepoAutomationTriggersPullRequestClosed())
                .pullRequestReopened(editRepoAutomationTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(editRepoAutomationTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(editRepoAutomationTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(editRepoAutomationTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(editRepoAutomationTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(editRepoAutomationTriggersAutomationCompleted())
                .automationFailed(editRepoAutomationTriggersAutomationFailed())
                .codeScanningAlert(editRepoAutomationTriggersCodeScanningAlert())
                .secretScanningAlert(editRepoAutomationTriggersSecretScanningAlert())
                .dependabotAlert(editRepoAutomationTriggersDependabotAlert())
                .build();
    }

    static GitHubEditRepoAutomationPayload editRepoAutomationPayload() {
        return GitHubEditRepoAutomationPayloadBuilder.builder()
                .name("name")
                .enabled(true)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .model("model")
                .reasoningEffort(GitHubEditRepoAutomationReasoningEffort.NONE)
                .triggers(editRepoAutomationTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersInterval editRepoAutomationResponseTriggersInterval() {
        return GitHubEditRepoAutomationResponseTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubEditRepoAutomationResponseTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersIssueOpened editRepoAutomationResponseTriggersIssueOpened() {
        return GitHubEditRepoAutomationResponseTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersIssueLabeled editRepoAutomationResponseTriggersIssueLabeled() {
        return GitHubEditRepoAutomationResponseTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersWorkflowRun editRepoAutomationResponseTriggersWorkflowRun() {
        return GitHubEditRepoAutomationResponseTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubEditRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestOpened
            editRepoAutomationResponseTriggersPullRequestOpened() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestSynchronize
            editRepoAutomationResponseTriggersPullRequestSynchronize() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestMerged
            editRepoAutomationResponseTriggersPullRequestMerged() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersIssueComment editRepoAutomationResponseTriggersIssueComment() {
        return GitHubEditRepoAutomationResponseTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestComment
            editRepoAutomationResponseTriggersPullRequestComment() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestCommentBuilder.builder()
                .commentTypes(
                        List.of(GitHubEditRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionOpened
            editRepoAutomationResponseTriggersDiscussionOpened() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionUpdated
            editRepoAutomationResponseTriggersDiscussionUpdated() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersDiscussionComment
            editRepoAutomationResponseTriggersDiscussionComment() {
        return GitHubEditRepoAutomationResponseTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubEditRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersIssueAssigned editRepoAutomationResponseTriggersIssueAssigned() {
        return GitHubEditRepoAutomationResponseTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestAssigned
            editRepoAutomationResponseTriggersPullRequestAssigned() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersSubIssueAdded editRepoAutomationResponseTriggersSubIssueAdded() {
        return GitHubEditRepoAutomationResponseTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReview
            editRepoAutomationResponseTriggersPullRequestReadyForReview() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestLabeled
            editRepoAutomationResponseTriggersPullRequestLabeled() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflict
            editRepoAutomationResponseTriggersPullRequestMergeConflict() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmitted
            editRepoAutomationResponseTriggersPullRequestReviewSubmitted() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubEditRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequested
            editRepoAutomationResponseTriggersPullRequestReviewRequested() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersIssueClosed editRepoAutomationResponseTriggersIssueClosed() {
        return GitHubEditRepoAutomationResponseTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersIssueReopened editRepoAutomationResponseTriggersIssueReopened() {
        return GitHubEditRepoAutomationResponseTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestClosed
            editRepoAutomationResponseTriggersPullRequestClosed() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReopened
            editRepoAutomationResponseTriggersPullRequestReopened() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestConvertedToDraft
            editRepoAutomationResponseTriggersPullRequestConvertedToDraft() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewDismissed
            editRepoAutomationResponseTriggersPullRequestReviewDismissed() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewThreadResolved
            editRepoAutomationResponseTriggersPullRequestReviewThreadResolved() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved
            editRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved() {
        return GitHubEditRepoAutomationResponseTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersAutomationCompleted
            editRepoAutomationResponseTriggersAutomationCompleted() {
        return GitHubEditRepoAutomationResponseTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersAutomationFailed
            editRepoAutomationResponseTriggersAutomationFailed() {
        return GitHubEditRepoAutomationResponseTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubEditRepoAutomationResponseTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersCodeScanningAlert
            editRepoAutomationResponseTriggersCodeScanningAlert() {
        return GitHubEditRepoAutomationResponseTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersSecretScanningAlert
            editRepoAutomationResponseTriggersSecretScanningAlert() {
        return GitHubEditRepoAutomationResponseTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggersDependabotAlert editRepoAutomationResponseTriggersDependabotAlert() {
        return GitHubEditRepoAutomationResponseTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubEditRepoAutomationResponseTriggers editRepoAutomationResponseTriggers() {
        return GitHubEditRepoAutomationResponseTriggersBuilder.builder()
                .interval(editRepoAutomationResponseTriggersInterval())
                .issueOpened(editRepoAutomationResponseTriggersIssueOpened())
                .issueLabeled(editRepoAutomationResponseTriggersIssueLabeled())
                .workflowRun(editRepoAutomationResponseTriggersWorkflowRun())
                .pullRequestOpened(editRepoAutomationResponseTriggersPullRequestOpened())
                .pullRequestSynchronize(editRepoAutomationResponseTriggersPullRequestSynchronize())
                .pullRequestMerged(editRepoAutomationResponseTriggersPullRequestMerged())
                .issueComment(editRepoAutomationResponseTriggersIssueComment())
                .pullRequestComment(editRepoAutomationResponseTriggersPullRequestComment())
                .discussionOpened(editRepoAutomationResponseTriggersDiscussionOpened())
                .discussionUpdated(editRepoAutomationResponseTriggersDiscussionUpdated())
                .discussionComment(editRepoAutomationResponseTriggersDiscussionComment())
                .issueAssigned(editRepoAutomationResponseTriggersIssueAssigned())
                .pullRequestAssigned(editRepoAutomationResponseTriggersPullRequestAssigned())
                .subIssueAdded(editRepoAutomationResponseTriggersSubIssueAdded())
                .pullRequestReadyForReview(editRepoAutomationResponseTriggersPullRequestReadyForReview())
                .pullRequestLabeled(editRepoAutomationResponseTriggersPullRequestLabeled())
                .pullRequestMergeConflict(editRepoAutomationResponseTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(editRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(editRepoAutomationResponseTriggersPullRequestReviewRequested())
                .issueClosed(editRepoAutomationResponseTriggersIssueClosed())
                .issueReopened(editRepoAutomationResponseTriggersIssueReopened())
                .pullRequestClosed(editRepoAutomationResponseTriggersPullRequestClosed())
                .pullRequestReopened(editRepoAutomationResponseTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(editRepoAutomationResponseTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(editRepoAutomationResponseTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(editRepoAutomationResponseTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(
                        editRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(editRepoAutomationResponseTriggersAutomationCompleted())
                .automationFailed(editRepoAutomationResponseTriggersAutomationFailed())
                .codeScanningAlert(editRepoAutomationResponseTriggersCodeScanningAlert())
                .secretScanningAlert(editRepoAutomationResponseTriggersSecretScanningAlert())
                .dependabotAlert(editRepoAutomationResponseTriggersDependabotAlert())
                .build();
    }

    static GitHubEditRepoAutomationResponseCreatedBy editRepoAutomationResponseCreatedBy() {
        return GitHubEditRepoAutomationResponseCreatedByBuilder.builder().id(1L).build();
    }

    static GitHubEditRepoAutomationResponse editRepoAutomationResponse() {
        return GitHubEditRepoAutomationResponseBuilder.builder()
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubEditRepoAutomationResponseType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .triggers(editRepoAutomationResponseTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(editRepoAutomationResponseCreatedBy())
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersInterval getRepoAutomationResponseTriggersInterval() {
        return GitHubGetRepoAutomationResponseTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubGetRepoAutomationResponseTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersIssueOpened getRepoAutomationResponseTriggersIssueOpened() {
        return GitHubGetRepoAutomationResponseTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersIssueLabeled getRepoAutomationResponseTriggersIssueLabeled() {
        return GitHubGetRepoAutomationResponseTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersWorkflowRun getRepoAutomationResponseTriggersWorkflowRun() {
        return GitHubGetRepoAutomationResponseTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestOpened
            getRepoAutomationResponseTriggersPullRequestOpened() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestSynchronize
            getRepoAutomationResponseTriggersPullRequestSynchronize() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestMerged
            getRepoAutomationResponseTriggersPullRequestMerged() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersIssueComment getRepoAutomationResponseTriggersIssueComment() {
        return GitHubGetRepoAutomationResponseTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestComment
            getRepoAutomationResponseTriggersPullRequestComment() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestCommentBuilder.builder()
                .commentTypes(
                        List.of(GitHubGetRepoAutomationResponseTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionOpened getRepoAutomationResponseTriggersDiscussionOpened() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionUpdated
            getRepoAutomationResponseTriggersDiscussionUpdated() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersDiscussionComment
            getRepoAutomationResponseTriggersDiscussionComment() {
        return GitHubGetRepoAutomationResponseTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubGetRepoAutomationResponseTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersIssueAssigned getRepoAutomationResponseTriggersIssueAssigned() {
        return GitHubGetRepoAutomationResponseTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestAssigned
            getRepoAutomationResponseTriggersPullRequestAssigned() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersSubIssueAdded getRepoAutomationResponseTriggersSubIssueAdded() {
        return GitHubGetRepoAutomationResponseTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReview
            getRepoAutomationResponseTriggersPullRequestReadyForReview() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestLabeled
            getRepoAutomationResponseTriggersPullRequestLabeled() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflict
            getRepoAutomationResponseTriggersPullRequestMergeConflict() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmitted
            getRepoAutomationResponseTriggersPullRequestReviewSubmitted() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubGetRepoAutomationResponseTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequested
            getRepoAutomationResponseTriggersPullRequestReviewRequested() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersIssueClosed getRepoAutomationResponseTriggersIssueClosed() {
        return GitHubGetRepoAutomationResponseTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersIssueReopened getRepoAutomationResponseTriggersIssueReopened() {
        return GitHubGetRepoAutomationResponseTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestClosed
            getRepoAutomationResponseTriggersPullRequestClosed() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReopened
            getRepoAutomationResponseTriggersPullRequestReopened() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestConvertedToDraft
            getRepoAutomationResponseTriggersPullRequestConvertedToDraft() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewDismissed
            getRepoAutomationResponseTriggersPullRequestReviewDismissed() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewThreadResolved
            getRepoAutomationResponseTriggersPullRequestReviewThreadResolved() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved
            getRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved() {
        return GitHubGetRepoAutomationResponseTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersAutomationCompleted
            getRepoAutomationResponseTriggersAutomationCompleted() {
        return GitHubGetRepoAutomationResponseTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersAutomationFailed getRepoAutomationResponseTriggersAutomationFailed() {
        return GitHubGetRepoAutomationResponseTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubGetRepoAutomationResponseTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersCodeScanningAlert
            getRepoAutomationResponseTriggersCodeScanningAlert() {
        return GitHubGetRepoAutomationResponseTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersSecretScanningAlert
            getRepoAutomationResponseTriggersSecretScanningAlert() {
        return GitHubGetRepoAutomationResponseTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggersDependabotAlert getRepoAutomationResponseTriggersDependabotAlert() {
        return GitHubGetRepoAutomationResponseTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubGetRepoAutomationResponseTriggers getRepoAutomationResponseTriggers() {
        return GitHubGetRepoAutomationResponseTriggersBuilder.builder()
                .interval(getRepoAutomationResponseTriggersInterval())
                .issueOpened(getRepoAutomationResponseTriggersIssueOpened())
                .issueLabeled(getRepoAutomationResponseTriggersIssueLabeled())
                .workflowRun(getRepoAutomationResponseTriggersWorkflowRun())
                .pullRequestOpened(getRepoAutomationResponseTriggersPullRequestOpened())
                .pullRequestSynchronize(getRepoAutomationResponseTriggersPullRequestSynchronize())
                .pullRequestMerged(getRepoAutomationResponseTriggersPullRequestMerged())
                .issueComment(getRepoAutomationResponseTriggersIssueComment())
                .pullRequestComment(getRepoAutomationResponseTriggersPullRequestComment())
                .discussionOpened(getRepoAutomationResponseTriggersDiscussionOpened())
                .discussionUpdated(getRepoAutomationResponseTriggersDiscussionUpdated())
                .discussionComment(getRepoAutomationResponseTriggersDiscussionComment())
                .issueAssigned(getRepoAutomationResponseTriggersIssueAssigned())
                .pullRequestAssigned(getRepoAutomationResponseTriggersPullRequestAssigned())
                .subIssueAdded(getRepoAutomationResponseTriggersSubIssueAdded())
                .pullRequestReadyForReview(getRepoAutomationResponseTriggersPullRequestReadyForReview())
                .pullRequestLabeled(getRepoAutomationResponseTriggersPullRequestLabeled())
                .pullRequestMergeConflict(getRepoAutomationResponseTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(getRepoAutomationResponseTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(getRepoAutomationResponseTriggersPullRequestReviewRequested())
                .issueClosed(getRepoAutomationResponseTriggersIssueClosed())
                .issueReopened(getRepoAutomationResponseTriggersIssueReopened())
                .pullRequestClosed(getRepoAutomationResponseTriggersPullRequestClosed())
                .pullRequestReopened(getRepoAutomationResponseTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(getRepoAutomationResponseTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(getRepoAutomationResponseTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(getRepoAutomationResponseTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(getRepoAutomationResponseTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(getRepoAutomationResponseTriggersAutomationCompleted())
                .automationFailed(getRepoAutomationResponseTriggersAutomationFailed())
                .codeScanningAlert(getRepoAutomationResponseTriggersCodeScanningAlert())
                .secretScanningAlert(getRepoAutomationResponseTriggersSecretScanningAlert())
                .dependabotAlert(getRepoAutomationResponseTriggersDependabotAlert())
                .build();
    }

    static GitHubGetRepoAutomationResponseCreatedBy getRepoAutomationResponseCreatedBy() {
        return GitHubGetRepoAutomationResponseCreatedByBuilder.builder().id(1L).build();
    }

    static GitHubGetRepoAutomationResponse getRepoAutomationResponse() {
        return GitHubGetRepoAutomationResponseBuilder.builder()
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubGetRepoAutomationResponseType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .triggers(getRepoAutomationResponseTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(getRepoAutomationResponseCreatedBy())
                .build();
    }

    static GitHubListAutomationsItemRepository listAutomationsItemRepository() {
        return GitHubListAutomationsItemRepositoryBuilder.builder()
                .id(1L)
                .owner("owner")
                .name("name")
                .build();
    }

    static GitHubListAutomationsItemTriggersInterval listAutomationsItemTriggersInterval() {
        return GitHubListAutomationsItemTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubListAutomationsItemTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersIssueOpened listAutomationsItemTriggersIssueOpened() {
        return GitHubListAutomationsItemTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersIssueLabeled listAutomationsItemTriggersIssueLabeled() {
        return GitHubListAutomationsItemTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersWorkflowRun listAutomationsItemTriggersWorkflowRun() {
        return GitHubListAutomationsItemTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubListAutomationsItemTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestOpened listAutomationsItemTriggersPullRequestOpened() {
        return GitHubListAutomationsItemTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestSynchronize listAutomationsItemTriggersPullRequestSynchronize() {
        return GitHubListAutomationsItemTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestMerged listAutomationsItemTriggersPullRequestMerged() {
        return GitHubListAutomationsItemTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersIssueComment listAutomationsItemTriggersIssueComment() {
        return GitHubListAutomationsItemTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestComment listAutomationsItemTriggersPullRequestComment() {
        return GitHubListAutomationsItemTriggersPullRequestCommentBuilder.builder()
                .commentTypes(List.of(GitHubListAutomationsItemTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersDiscussionOpened listAutomationsItemTriggersDiscussionOpened() {
        return GitHubListAutomationsItemTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersDiscussionUpdated listAutomationsItemTriggersDiscussionUpdated() {
        return GitHubListAutomationsItemTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersDiscussionComment listAutomationsItemTriggersDiscussionComment() {
        return GitHubListAutomationsItemTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubListAutomationsItemTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersIssueAssigned listAutomationsItemTriggersIssueAssigned() {
        return GitHubListAutomationsItemTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestAssigned listAutomationsItemTriggersPullRequestAssigned() {
        return GitHubListAutomationsItemTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersSubIssueAdded listAutomationsItemTriggersSubIssueAdded() {
        return GitHubListAutomationsItemTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReadyForReview
            listAutomationsItemTriggersPullRequestReadyForReview() {
        return GitHubListAutomationsItemTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestLabeled listAutomationsItemTriggersPullRequestLabeled() {
        return GitHubListAutomationsItemTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestMergeConflict
            listAutomationsItemTriggersPullRequestMergeConflict() {
        return GitHubListAutomationsItemTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewSubmitted
            listAutomationsItemTriggersPullRequestReviewSubmitted() {
        return GitHubListAutomationsItemTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubListAutomationsItemTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewRequested
            listAutomationsItemTriggersPullRequestReviewRequested() {
        return GitHubListAutomationsItemTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersIssueClosed listAutomationsItemTriggersIssueClosed() {
        return GitHubListAutomationsItemTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersIssueReopened listAutomationsItemTriggersIssueReopened() {
        return GitHubListAutomationsItemTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestClosed listAutomationsItemTriggersPullRequestClosed() {
        return GitHubListAutomationsItemTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReopened listAutomationsItemTriggersPullRequestReopened() {
        return GitHubListAutomationsItemTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestConvertedToDraft
            listAutomationsItemTriggersPullRequestConvertedToDraft() {
        return GitHubListAutomationsItemTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewDismissed
            listAutomationsItemTriggersPullRequestReviewDismissed() {
        return GitHubListAutomationsItemTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewThreadResolved
            listAutomationsItemTriggersPullRequestReviewThreadResolved() {
        return GitHubListAutomationsItemTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersPullRequestReviewThreadUnresolved
            listAutomationsItemTriggersPullRequestReviewThreadUnresolved() {
        return GitHubListAutomationsItemTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersAutomationCompleted listAutomationsItemTriggersAutomationCompleted() {
        return GitHubListAutomationsItemTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersAutomationFailed listAutomationsItemTriggersAutomationFailed() {
        return GitHubListAutomationsItemTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubListAutomationsItemTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersCodeScanningAlert listAutomationsItemTriggersCodeScanningAlert() {
        return GitHubListAutomationsItemTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersSecretScanningAlert listAutomationsItemTriggersSecretScanningAlert() {
        return GitHubListAutomationsItemTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggersDependabotAlert listAutomationsItemTriggersDependabotAlert() {
        return GitHubListAutomationsItemTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListAutomationsItemTriggers listAutomationsItemTriggers() {
        return GitHubListAutomationsItemTriggersBuilder.builder()
                .interval(listAutomationsItemTriggersInterval())
                .issueOpened(listAutomationsItemTriggersIssueOpened())
                .issueLabeled(listAutomationsItemTriggersIssueLabeled())
                .workflowRun(listAutomationsItemTriggersWorkflowRun())
                .pullRequestOpened(listAutomationsItemTriggersPullRequestOpened())
                .pullRequestSynchronize(listAutomationsItemTriggersPullRequestSynchronize())
                .pullRequestMerged(listAutomationsItemTriggersPullRequestMerged())
                .issueComment(listAutomationsItemTriggersIssueComment())
                .pullRequestComment(listAutomationsItemTriggersPullRequestComment())
                .discussionOpened(listAutomationsItemTriggersDiscussionOpened())
                .discussionUpdated(listAutomationsItemTriggersDiscussionUpdated())
                .discussionComment(listAutomationsItemTriggersDiscussionComment())
                .issueAssigned(listAutomationsItemTriggersIssueAssigned())
                .pullRequestAssigned(listAutomationsItemTriggersPullRequestAssigned())
                .subIssueAdded(listAutomationsItemTriggersSubIssueAdded())
                .pullRequestReadyForReview(listAutomationsItemTriggersPullRequestReadyForReview())
                .pullRequestLabeled(listAutomationsItemTriggersPullRequestLabeled())
                .pullRequestMergeConflict(listAutomationsItemTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(listAutomationsItemTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(listAutomationsItemTriggersPullRequestReviewRequested())
                .issueClosed(listAutomationsItemTriggersIssueClosed())
                .issueReopened(listAutomationsItemTriggersIssueReopened())
                .pullRequestClosed(listAutomationsItemTriggersPullRequestClosed())
                .pullRequestReopened(listAutomationsItemTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(listAutomationsItemTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(listAutomationsItemTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(listAutomationsItemTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(listAutomationsItemTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(listAutomationsItemTriggersAutomationCompleted())
                .automationFailed(listAutomationsItemTriggersAutomationFailed())
                .codeScanningAlert(listAutomationsItemTriggersCodeScanningAlert())
                .secretScanningAlert(listAutomationsItemTriggersSecretScanningAlert())
                .dependabotAlert(listAutomationsItemTriggersDependabotAlert())
                .build();
    }

    static GitHubListAutomationsItemCreatedBy listAutomationsItemCreatedBy() {
        return GitHubListAutomationsItemCreatedByBuilder.builder().id(1L).build();
    }

    static GitHubListAutomationsItem listAutomationsItem() {
        return GitHubListAutomationsItemBuilder.builder()
                .id("id")
                .repository(listAutomationsItemRepository())
                .name("name")
                .enabled(true)
                .type(GitHubListAutomationsItemType.PERSONAL)
                .requireActorWritePermission(true)
                .model("model")
                .reasoningEffort("reasoning_effort")
                .triggers(listAutomationsItemTriggers())
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(listAutomationsItemCreatedBy())
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersInterval listRepoAutomationsAutomationTriggersInterval() {
        return GitHubListRepoAutomationsAutomationTriggersIntervalBuilder.builder()
                .cadence(List.of(GitHubListRepoAutomationsAutomationTriggersIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueOpened listRepoAutomationsAutomationTriggersIssueOpened() {
        return GitHubListRepoAutomationsAutomationTriggersIssueOpenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueLabeled listRepoAutomationsAutomationTriggersIssueLabeled() {
        return GitHubListRepoAutomationsAutomationTriggersIssueLabeledBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersWorkflowRun listRepoAutomationsAutomationTriggersWorkflowRun() {
        return GitHubListRepoAutomationsAutomationTriggersWorkflowRunBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestOpened
            listRepoAutomationsAutomationTriggersPullRequestOpened() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestOpenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronize
            listRepoAutomationsAutomationTriggersPullRequestSynchronize() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestSynchronizeBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestMerged
            listRepoAutomationsAutomationTriggersPullRequestMerged() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestMergedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueComment listRepoAutomationsAutomationTriggersIssueComment() {
        return GitHubListRepoAutomationsAutomationTriggersIssueCommentBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestComment
            listRepoAutomationsAutomationTriggersPullRequestComment() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestCommentBuilder.builder()
                .commentTypes(List.of(
                        GitHubListRepoAutomationsAutomationTriggersPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionOpened
            listRepoAutomationsAutomationTriggersDiscussionOpened() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionOpenedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionUpdated
            listRepoAutomationsAutomationTriggersDiscussionUpdated() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionUpdatedBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersDiscussionComment
            listRepoAutomationsAutomationTriggersDiscussionComment() {
        return GitHubListRepoAutomationsAutomationTriggersDiscussionCommentBuilder.builder()
                .commentTypes(List.of(GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueAssigned
            listRepoAutomationsAutomationTriggersIssueAssigned() {
        return GitHubListRepoAutomationsAutomationTriggersIssueAssignedBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestAssigned
            listRepoAutomationsAutomationTriggersPullRequestAssigned() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestAssignedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersSubIssueAdded
            listRepoAutomationsAutomationTriggersSubIssueAdded() {
        return GitHubListRepoAutomationsAutomationTriggersSubIssueAddedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReview
            listRepoAutomationsAutomationTriggersPullRequestReadyForReview() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReadyForReviewBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestLabeled
            listRepoAutomationsAutomationTriggersPullRequestLabeled() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestLabeledBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflict
            listRepoAutomationsAutomationTriggersPullRequestMergeConflict() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestMergeConflictBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmitted
            listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubListRepoAutomationsAutomationTriggersPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequested
            listRepoAutomationsAutomationTriggersPullRequestReviewRequested() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewRequestedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueClosed listRepoAutomationsAutomationTriggersIssueClosed() {
        return GitHubListRepoAutomationsAutomationTriggersIssueClosedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersIssueReopened
            listRepoAutomationsAutomationTriggersIssueReopened() {
        return GitHubListRepoAutomationsAutomationTriggersIssueReopenedBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestClosed
            listRepoAutomationsAutomationTriggersPullRequestClosed() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestClosedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReopened
            listRepoAutomationsAutomationTriggersPullRequestReopened() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReopenedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestConvertedToDraft
            listRepoAutomationsAutomationTriggersPullRequestConvertedToDraft() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestConvertedToDraftBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewDismissed
            listRepoAutomationsAutomationTriggersPullRequestReviewDismissed() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewDismissedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewThreadResolved
            listRepoAutomationsAutomationTriggersPullRequestReviewThreadResolved() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewThreadResolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersPullRequestReviewThreadUnresolved
            listRepoAutomationsAutomationTriggersPullRequestReviewThreadUnresolved() {
        return GitHubListRepoAutomationsAutomationTriggersPullRequestReviewThreadUnresolvedBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersAutomationCompleted
            listRepoAutomationsAutomationTriggersAutomationCompleted() {
        return GitHubListRepoAutomationsAutomationTriggersAutomationCompletedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersAutomationFailed
            listRepoAutomationsAutomationTriggersAutomationFailed() {
        return GitHubListRepoAutomationsAutomationTriggersAutomationFailedBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubListRepoAutomationsAutomationTriggersAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersCodeScanningAlert
            listRepoAutomationsAutomationTriggersCodeScanningAlert() {
        return GitHubListRepoAutomationsAutomationTriggersCodeScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersSecretScanningAlert
            listRepoAutomationsAutomationTriggersSecretScanningAlert() {
        return GitHubListRepoAutomationsAutomationTriggersSecretScanningAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggersDependabotAlert
            listRepoAutomationsAutomationTriggersDependabotAlert() {
        return GitHubListRepoAutomationsAutomationTriggersDependabotAlertBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubListRepoAutomationsAutomationTriggers listRepoAutomationsAutomationTriggers() {
        return GitHubListRepoAutomationsAutomationTriggersBuilder.builder()
                .interval(listRepoAutomationsAutomationTriggersInterval())
                .issueOpened(listRepoAutomationsAutomationTriggersIssueOpened())
                .issueLabeled(listRepoAutomationsAutomationTriggersIssueLabeled())
                .workflowRun(listRepoAutomationsAutomationTriggersWorkflowRun())
                .pullRequestOpened(listRepoAutomationsAutomationTriggersPullRequestOpened())
                .pullRequestSynchronize(listRepoAutomationsAutomationTriggersPullRequestSynchronize())
                .pullRequestMerged(listRepoAutomationsAutomationTriggersPullRequestMerged())
                .issueComment(listRepoAutomationsAutomationTriggersIssueComment())
                .pullRequestComment(listRepoAutomationsAutomationTriggersPullRequestComment())
                .discussionOpened(listRepoAutomationsAutomationTriggersDiscussionOpened())
                .discussionUpdated(listRepoAutomationsAutomationTriggersDiscussionUpdated())
                .discussionComment(listRepoAutomationsAutomationTriggersDiscussionComment())
                .issueAssigned(listRepoAutomationsAutomationTriggersIssueAssigned())
                .pullRequestAssigned(listRepoAutomationsAutomationTriggersPullRequestAssigned())
                .subIssueAdded(listRepoAutomationsAutomationTriggersSubIssueAdded())
                .pullRequestReadyForReview(listRepoAutomationsAutomationTriggersPullRequestReadyForReview())
                .pullRequestLabeled(listRepoAutomationsAutomationTriggersPullRequestLabeled())
                .pullRequestMergeConflict(listRepoAutomationsAutomationTriggersPullRequestMergeConflict())
                .pullRequestReviewSubmitted(listRepoAutomationsAutomationTriggersPullRequestReviewSubmitted())
                .pullRequestReviewRequested(listRepoAutomationsAutomationTriggersPullRequestReviewRequested())
                .issueClosed(listRepoAutomationsAutomationTriggersIssueClosed())
                .issueReopened(listRepoAutomationsAutomationTriggersIssueReopened())
                .pullRequestClosed(listRepoAutomationsAutomationTriggersPullRequestClosed())
                .pullRequestReopened(listRepoAutomationsAutomationTriggersPullRequestReopened())
                .pullRequestConvertedToDraft(listRepoAutomationsAutomationTriggersPullRequestConvertedToDraft())
                .pullRequestReviewDismissed(listRepoAutomationsAutomationTriggersPullRequestReviewDismissed())
                .pullRequestReviewThreadResolved(listRepoAutomationsAutomationTriggersPullRequestReviewThreadResolved())
                .pullRequestReviewThreadUnresolved(
                        listRepoAutomationsAutomationTriggersPullRequestReviewThreadUnresolved())
                .automationCompleted(listRepoAutomationsAutomationTriggersAutomationCompleted())
                .automationFailed(listRepoAutomationsAutomationTriggersAutomationFailed())
                .codeScanningAlert(listRepoAutomationsAutomationTriggersCodeScanningAlert())
                .secretScanningAlert(listRepoAutomationsAutomationTriggersSecretScanningAlert())
                .dependabotAlert(listRepoAutomationsAutomationTriggersDependabotAlert())
                .build();
    }

    static GitHubListRepoAutomationsAutomationCreatedBy listRepoAutomationsAutomationCreatedBy() {
        return GitHubListRepoAutomationsAutomationCreatedByBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubListRepoAutomationsAutomation listRepoAutomationsAutomation() {
        return GitHubListRepoAutomationsAutomationBuilder.builder()
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubListRepoAutomationsAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .model("model")
                .reasoningEffort("reasoning_effort")
                .triggers(listRepoAutomationsAutomationTriggers())
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(listRepoAutomationsAutomationCreatedBy())
                .build();
    }

    static GitHubListAvailableRepoAutomationToolsResponseToolGroupTool
            listAvailableRepoAutomationToolsResponseToolGroupTool() {
        return GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder.builder()
                .id("id")
                .title("title")
                .description("description")
                .scope(GitHubListAvailableRepoAutomationToolsResponseToolGroupToolScope.READ)
                .build();
    }

    static GitHubListAvailableRepoAutomationToolsResponseToolGroup listAvailableRepoAutomationToolsResponseToolGroup() {
        return GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder.builder()
                .id("id")
                .title("title")
                .tools(List.of(listAvailableRepoAutomationToolsResponseToolGroupTool()))
                .build();
    }

    static GitHubListAvailableRepoAutomationToolsResponseMcpServerTool
            listAvailableRepoAutomationToolsResponseMcpServerTool() {
        return GitHubListAvailableRepoAutomationToolsResponseMcpServerToolBuilder.builder()
                .id("id")
                .build();
    }

    static GitHubListAvailableRepoAutomationToolsResponseMcpServer listAvailableRepoAutomationToolsResponseMcpServer() {
        return GitHubListAvailableRepoAutomationToolsResponseMcpServerBuilder.builder()
                .name("name")
                .tools(List.of(listAvailableRepoAutomationToolsResponseMcpServerTool()))
                .supportsWildcard(true)
                .build();
    }

    static GitHubListAvailableRepoAutomationToolsResponse listAvailableRepoAutomationToolsResponse() {
        return GitHubListAvailableRepoAutomationToolsResponseBuilder.builder()
                .toolGroups(List.of(listAvailableRepoAutomationToolsResponseToolGroup()))
                .mcpServers(List.of(listAvailableRepoAutomationToolsResponseMcpServer()))
                .build();
    }

    static GitHubCreateRepoAutomationTaskPayload createRepoAutomationTaskPayload() {
        return GitHubCreateRepoAutomationTaskPayloadBuilder.builder()
                .event(GitHubCreateRepoAutomationTaskEvent.MANUAL)
                .context("context")
                .build();
    }

    static GitHubCreateRepoAutomationTaskResponse createRepoAutomationTaskResponse() {
        return GitHubCreateRepoAutomationTaskResponseBuilder.builder()
                .taskId("task_id")
                .automationId("automation_id")
                .event(GitHubCreateRepoAutomationTaskResponseEvent.MANUAL)
                .build();
    }
}
