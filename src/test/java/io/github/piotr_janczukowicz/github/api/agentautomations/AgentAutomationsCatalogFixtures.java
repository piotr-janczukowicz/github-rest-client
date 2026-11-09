package io.github.piotr_janczukowicz.github.api.agentautomations;

import java.time.Instant;
import java.util.List;

final class AgentAutomationsCatalogFixtures {

    private AgentAutomationsCatalogFixtures() {}

    static GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString
            listAvailableRepoAutomationTriggersItemFieldSchemaString() {
        return new GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString(
                automationTriggerFieldSchemaArrayItemsType(), List.of("low", "high"), 1);
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
                automationTriggerFieldSchemaArrayItemsType(), List.of("low", "high"), 1);
    }

    static GitHubAutomationTriggerFieldSchemaArrayItemsType automationTriggerFieldSchemaArrayItemsType() {
        return GitHubAutomationTriggerFieldSchemaArrayItemsType.STRING;
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

    static GitHubAutomationIntervalCadenceInput automationIntervalCadenceInput() {
        return GitHubAutomationIntervalCadenceInput.HOURLY;
    }

    static GitHubAutomationWorkflowRunConclusionInput automationWorkflowRunConclusionInput() {
        return GitHubAutomationWorkflowRunConclusionInput.FAILURE;
    }

    static GitHubAutomationPullRequestCommentCommentTypeInput automationPullRequestCommentCommentTypeInput() {
        return GitHubAutomationPullRequestCommentCommentTypeInput.TIMELINE_COMMENT;
    }

    static GitHubAutomationDiscussionCommentCommentTypeInput automationDiscussionCommentCommentTypeInput() {
        return GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT;
    }

    static GitHubAutomationPullRequestReviewSubmittedStateInput automationPullRequestReviewSubmittedStateInput() {
        return GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED;
    }

    static GitHubAutomationIntervalCadence automationIntervalCadence() {
        return GitHubAutomationIntervalCadence.HOURLY;
    }

    static GitHubAutomationWorkflowRunConclusion automationWorkflowRunConclusion() {
        return GitHubAutomationWorkflowRunConclusion.FAILURE;
    }

    static GitHubAutomationPullRequestCommentCommentType automationPullRequestCommentCommentType() {
        return GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT;
    }

    static GitHubAutomationDiscussionCommentCommentType automationDiscussionCommentCommentType() {
        return GitHubAutomationDiscussionCommentCommentType.COMMENT;
    }

    static GitHubAutomationPullRequestReviewSubmittedState automationPullRequestReviewSubmittedState() {
        return GitHubAutomationPullRequestReviewSubmittedState.APPROVED;
    }

    static GitHubAutomationListType automationListType() {
        return GitHubAutomationListType.PERSONAL;
    }

    static GitHubListAutomationsFilter listAutomationsFilter() {
        return new GitHubListAutomationsFilter(automationListType());
    }

    static GitHubListRepoAutomationsFilter listRepoAutomationsFilter() {
        return new GitHubListRepoAutomationsFilter(automationListType());
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

    static GitHubAutomationIntervalTriggerPayload automationIntervalTriggerPayload() {
        return GitHubAutomationIntervalTriggerPayloadBuilder.builder()
                .cadence(List.of(GitHubAutomationIntervalCadenceInput.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueLabeledTriggerPayload automationIssueLabeledTriggerPayload() {
        return GitHubAutomationIssueLabeledTriggerPayloadBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationWorkflowRunTriggerPayload automationWorkflowRunTriggerPayload() {
        return GitHubAutomationWorkflowRunTriggerPayloadBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubAutomationWorkflowRunConclusionInput.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueCommentTriggerPayload automationIssueCommentTriggerPayload() {
        return GitHubAutomationIssueCommentTriggerPayloadBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestCommentTriggerPayload automationPullRequestCommentTriggerPayload() {
        return GitHubAutomationPullRequestCommentTriggerPayloadBuilder.builder()
                .commentTypes(List.of(GitHubAutomationPullRequestCommentCommentTypeInput.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationDiscussionTriggerPayload automationDiscussionTriggerPayload() {
        return GitHubAutomationDiscussionTriggerPayloadBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationDiscussionCommentTriggerPayload automationDiscussionCommentTriggerPayload() {
        return GitHubAutomationDiscussionCommentTriggerPayloadBuilder.builder()
                .commentTypes(List.of(GitHubAutomationDiscussionCommentCommentTypeInput.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueAssignedTriggerPayload automationIssueAssignedTriggerPayload() {
        return GitHubAutomationIssueAssignedTriggerPayloadBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestAssignedTriggerPayload automationPullRequestAssignedTriggerPayload() {
        return GitHubAutomationPullRequestAssignedTriggerPayloadBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestLabeledTriggerPayload automationPullRequestLabeledTriggerPayload() {
        return GitHubAutomationPullRequestLabeledTriggerPayloadBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestReviewSubmittedTriggerPayload
            automationPullRequestReviewSubmittedTriggerPayload() {
        return GitHubAutomationPullRequestReviewSubmittedTriggerPayloadBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubAutomationPullRequestReviewSubmittedStateInput.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueTriggerPayload automationIssueTriggerPayload() {
        return GitHubAutomationIssueTriggerPayloadBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestTriggerPayload automationPullRequestTriggerPayload() {
        return GitHubAutomationPullRequestTriggerPayloadBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationCompletedTriggerPayload automationCompletedTriggerPayload() {
        return GitHubAutomationCompletedTriggerPayloadBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationFailedTriggerPayload automationFailedTriggerPayload() {
        return GitHubAutomationFailedTriggerPayloadBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubAutomationFailedReasonInput.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationAlertTriggerPayload automationAlertTriggerPayload() {
        return GitHubAutomationAlertTriggerPayloadBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationTriggerPayload automationTriggerPayload() {
        return GitHubAutomationTriggerPayloadBuilder.builder()
                .interval(automationIntervalTriggerPayload())
                .issueOpened(automationIssueTriggerPayload())
                .issueLabeled(automationIssueLabeledTriggerPayload())
                .workflowRun(automationWorkflowRunTriggerPayload())
                .pullRequestOpened(automationPullRequestTriggerPayload())
                .pullRequestSynchronize(automationPullRequestTriggerPayload())
                .pullRequestMerged(automationPullRequestTriggerPayload())
                .issueComment(automationIssueCommentTriggerPayload())
                .pullRequestComment(automationPullRequestCommentTriggerPayload())
                .discussionOpened(automationDiscussionTriggerPayload())
                .discussionUpdated(automationDiscussionTriggerPayload())
                .discussionComment(automationDiscussionCommentTriggerPayload())
                .issueAssigned(automationIssueAssignedTriggerPayload())
                .pullRequestAssigned(automationPullRequestAssignedTriggerPayload())
                .subIssueAdded(automationIssueTriggerPayload())
                .pullRequestReadyForReview(automationPullRequestTriggerPayload())
                .pullRequestLabeled(automationPullRequestLabeledTriggerPayload())
                .pullRequestMergeConflict(automationPullRequestTriggerPayload())
                .pullRequestReviewSubmitted(automationPullRequestReviewSubmittedTriggerPayload())
                .pullRequestReviewRequested(automationPullRequestTriggerPayload())
                .issueClosed(automationIssueTriggerPayload())
                .issueReopened(automationIssueTriggerPayload())
                .pullRequestClosed(automationPullRequestTriggerPayload())
                .pullRequestReopened(automationPullRequestTriggerPayload())
                .pullRequestConvertedToDraft(automationPullRequestTriggerPayload())
                .pullRequestReviewDismissed(automationPullRequestTriggerPayload())
                .pullRequestReviewThreadResolved(automationPullRequestTriggerPayload())
                .pullRequestReviewThreadUnresolved(automationPullRequestTriggerPayload())
                .automationCompleted(automationCompletedTriggerPayload())
                .automationFailed(automationFailedTriggerPayload())
                .codeScanningAlert(automationAlertTriggerPayload())
                .secretScanningAlert(automationAlertTriggerPayload())
                .dependabotAlert(automationAlertTriggerPayload())
                .build();
    }

    static GitHubCreateRepoAutomationPayload createRepoAutomationPayload() {
        return GitHubCreateRepoAutomationPayloadBuilder.builder()
                .name("name")
                .triggers(automationTriggerPayload())
                .prompt("prompt")
                .model("model")
                .reasoningEffort(GitHubAutomationReasoningEffort.NONE)
                .tools(List.of("tools"))
                .enabled(true)
                .type(GitHubCreateRepoAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .build();
    }

    static GitHubAutomationIntervalTrigger automationIntervalTrigger() {
        return GitHubAutomationIntervalTriggerBuilder.builder()
                .cadence(List.of(GitHubAutomationIntervalCadence.HOURLY))
                .hourUtc(1)
                .dayOfWeek(1)
                .minuteUtc(GitHubAutomationMinuteUtc.ZERO)
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueLabeledTrigger automationIssueLabeledTrigger() {
        return GitHubAutomationIssueLabeledTriggerBuilder.builder()
                .query("query")
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationWorkflowRunTrigger automationWorkflowRunTrigger() {
        return GitHubAutomationWorkflowRunTriggerBuilder.builder()
                .workflows(List.of("workflows"))
                .conclusions(List.of(GitHubAutomationWorkflowRunConclusion.FAILURE))
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueCommentTrigger automationIssueCommentTrigger() {
        return GitHubAutomationIssueCommentTriggerBuilder.builder()
                .query("query")
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestCommentTrigger automationPullRequestCommentTrigger() {
        return GitHubAutomationPullRequestCommentTriggerBuilder.builder()
                .commentTypes(List.of(GitHubAutomationPullRequestCommentCommentType.TIMELINE_COMMENT))
                .query("query")
                .paths(List.of("paths"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationDiscussionTrigger automationDiscussionTrigger() {
        return GitHubAutomationDiscussionTriggerBuilder.builder()
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationDiscussionCommentTrigger automationDiscussionCommentTrigger() {
        return GitHubAutomationDiscussionCommentTriggerBuilder.builder()
                .commentTypes(List.of(GitHubAutomationDiscussionCommentCommentType.COMMENT))
                .categories(List.of("categories"))
                .bodySubstring("body_substring")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueAssignedTrigger automationIssueAssignedTrigger() {
        return GitHubAutomationIssueAssignedTriggerBuilder.builder()
                .query("query")
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestAssignedTrigger automationPullRequestAssignedTrigger() {
        return GitHubAutomationPullRequestAssignedTriggerBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .assignees(List.of("assignees"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestLabeledTrigger automationPullRequestLabeledTrigger() {
        return GitHubAutomationPullRequestLabeledTriggerBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .labels(List.of("labels"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestReviewSubmittedTrigger automationPullRequestReviewSubmittedTrigger() {
        return GitHubAutomationPullRequestReviewSubmittedTriggerBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .states(List.of(GitHubAutomationPullRequestReviewSubmittedState.APPROVED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationIssueTrigger automationIssueTrigger() {
        return GitHubAutomationIssueTriggerBuilder.builder()
                .query("query")
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationPullRequestTrigger automationPullRequestTrigger() {
        return GitHubAutomationPullRequestTriggerBuilder.builder()
                .query("query")
                .paths(List.of("paths"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationCompletedTrigger automationCompletedTrigger() {
        return GitHubAutomationCompletedTriggerBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationFailedTrigger automationFailedTrigger() {
        return GitHubAutomationFailedTriggerBuilder.builder()
                .automationIds(List.of("automation_ids"))
                .reasons(List.of(GitHubAutomationFailedReason.REPORTED))
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationAlertTrigger automationAlertTrigger() {
        return GitHubAutomationAlertTriggerBuilder.builder()
                .writeScope(GitHubAutomationWriteScope.TRIGGER)
                .allowCopilotTriggers(true)
                .build();
    }

    static GitHubAutomationTriggers automationTriggers() {
        return GitHubAutomationTriggersBuilder.builder()
                .interval(automationIntervalTrigger())
                .issueOpened(automationIssueTrigger())
                .issueLabeled(automationIssueLabeledTrigger())
                .workflowRun(automationWorkflowRunTrigger())
                .pullRequestOpened(automationPullRequestTrigger())
                .pullRequestSynchronize(automationPullRequestTrigger())
                .pullRequestMerged(automationPullRequestTrigger())
                .issueComment(automationIssueCommentTrigger())
                .pullRequestComment(automationPullRequestCommentTrigger())
                .discussionOpened(automationDiscussionTrigger())
                .discussionUpdated(automationDiscussionTrigger())
                .discussionComment(automationDiscussionCommentTrigger())
                .issueAssigned(automationIssueAssignedTrigger())
                .pullRequestAssigned(automationPullRequestAssignedTrigger())
                .subIssueAdded(automationIssueTrigger())
                .pullRequestReadyForReview(automationPullRequestTrigger())
                .pullRequestLabeled(automationPullRequestLabeledTrigger())
                .pullRequestMergeConflict(automationPullRequestTrigger())
                .pullRequestReviewSubmitted(automationPullRequestReviewSubmittedTrigger())
                .pullRequestReviewRequested(automationPullRequestTrigger())
                .issueClosed(automationIssueTrigger())
                .issueReopened(automationIssueTrigger())
                .pullRequestClosed(automationPullRequestTrigger())
                .pullRequestReopened(automationPullRequestTrigger())
                .pullRequestConvertedToDraft(automationPullRequestTrigger())
                .pullRequestReviewDismissed(automationPullRequestTrigger())
                .pullRequestReviewThreadResolved(automationPullRequestTrigger())
                .pullRequestReviewThreadUnresolved(automationPullRequestTrigger())
                .automationCompleted(automationCompletedTrigger())
                .automationFailed(automationFailedTrigger())
                .codeScanningAlert(automationAlertTrigger())
                .secretScanningAlert(automationAlertTrigger())
                .dependabotAlert(automationAlertTrigger())
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
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .triggers(automationTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(createRepoAutomationResponseCreatedBy())
                .build();
    }

    static GitHubEditRepoAutomationPayload editRepoAutomationPayload() {
        return GitHubEditRepoAutomationPayloadBuilder.builder()
                .name("name")
                .enabled(true)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .model("model")
                .reasoningEffort(GitHubAutomationReasoningEffort.NONE)
                .triggers(automationTriggerPayload())
                .prompt("prompt")
                .tools(List.of("tools"))
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
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .triggers(automationTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(editRepoAutomationResponseCreatedBy())
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
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .continuousHistory(true)
                .triggers(automationTriggers())
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

    static GitHubListAutomationsItemCreatedBy listAutomationsItemCreatedBy() {
        return GitHubListAutomationsItemCreatedByBuilder.builder().id(1L).build();
    }

    static GitHubListAutomationsItem listAutomationsItem() {
        return GitHubListAutomationsItemBuilder.builder()
                .tools(List.of("tools"))
                .id("id")
                .repository(listAutomationsItemRepository())
                .name("name")
                .enabled(true)
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .model("model")
                .reasoningEffort("reasoning_effort")
                .triggers(automationTriggers())
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(listAutomationsItemCreatedBy())
                .build();
    }

    static GitHubListRepoAutomationsAutomationCreatedBy listRepoAutomationsAutomationCreatedBy() {
        return GitHubListRepoAutomationsAutomationCreatedByBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubListRepoAutomationsAutomation listRepoAutomationsAutomation() {
        return GitHubListRepoAutomationsAutomationBuilder.builder()
                .tools(List.of("tools"))
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .model("model")
                .reasoningEffort("reasoning_effort")
                .triggers(automationTriggers())
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

    static GitHubGetAutomationRevisionResponseCreator getAutomationRevisionResponseCreator() {
        return GitHubGetAutomationRevisionResponseCreatorBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubGetAutomationRevisionResponseEditor getAutomationRevisionResponseEditor() {
        return GitHubGetAutomationRevisionResponseEditorBuilder.builder().id(1L).build();
    }

    static GitHubListAutomationRevisionsRevisionCreator listAutomationRevisionsRevisionCreator() {
        return GitHubListAutomationRevisionsRevisionCreatorBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubListAutomationRevisionsRevisionEditor listAutomationRevisionsRevisionEditor() {
        return GitHubListAutomationRevisionsRevisionEditorBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubGetAutomationRevisionResponseSnapshotCreatedBy getAutomationRevisionResponseSnapshotCreatedBy() {
        return GitHubGetAutomationRevisionResponseSnapshotCreatedByBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubGetAutomationRevisionResponseSnapshot getAutomationRevisionResponseSnapshot() {
        return GitHubGetAutomationRevisionResponseSnapshotBuilder.builder()
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .triggers(automationTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(getAutomationRevisionResponseSnapshotCreatedBy())
                .build();
    }

    static GitHubGetAutomationRevisionResponse getAutomationRevisionResponse() {
        return GitHubGetAutomationRevisionResponseBuilder.builder()
                .id("id")
                .automationId("automation_id")
                .revisionNumber(1L)
                .changeType(GitHubAutomationRevisionChangeType.CREATED)
                .creator(getAutomationRevisionResponseCreator())
                .editor(getAutomationRevisionResponseEditor())
                .editorType(GitHubAutomationRevisionEditorType.USER)
                .changedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .restoredFromRevisionId("restored_from_revision_id")
                .snapshot(getAutomationRevisionResponseSnapshot())
                .build();
    }

    static GitHubListAutomationRevisionsRevisionSnapshotCreatedBy listAutomationRevisionsRevisionSnapshotCreatedBy() {
        return GitHubListAutomationRevisionsRevisionSnapshotCreatedByBuilder.builder()
                .id(1L)
                .build();
    }

    static GitHubListAutomationRevisionsRevisionSnapshot listAutomationRevisionsRevisionSnapshot() {
        return GitHubListAutomationRevisionsRevisionSnapshotBuilder.builder()
                .id("id")
                .name("name")
                .enabled(true)
                .type(GitHubAutomationType.PERSONAL)
                .requireActorWritePermission(true)
                .triggers(automationTriggers())
                .prompt("prompt")
                .tools(List.of("tools"))
                .model("model")
                .reasoningEffort("reasoning_effort")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .createdBy(listAutomationRevisionsRevisionSnapshotCreatedBy())
                .build();
    }

    static GitHubListAutomationRevisionsRevision listAutomationRevisionsRevision() {
        return GitHubListAutomationRevisionsRevisionBuilder.builder()
                .id("id")
                .automationId("automation_id")
                .revisionNumber(1L)
                .changeType(GitHubAutomationRevisionChangeType.CREATED)
                .creator(listAutomationRevisionsRevisionCreator())
                .editor(listAutomationRevisionsRevisionEditor())
                .editorType(GitHubAutomationRevisionEditorType.USER)
                .changedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .restoredFromRevisionId("restored_from_revision_id")
                .snapshot(listAutomationRevisionsRevisionSnapshot())
                .build();
    }

    static GitHubListAutomationRevisionsResponse listAutomationRevisionsResponse() {
        return new GitHubListAutomationRevisionsResponse(List.of(listAutomationRevisionsRevision()));
    }
}
