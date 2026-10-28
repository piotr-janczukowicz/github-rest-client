package io.github.piotr_janczukowicz.github.api.agenttasks;

import java.time.Instant;
import java.util.List;

final class AgentTasksCatalogFixtures {

    private AgentTasksCatalogFixtures() {}

    static GitHubListTasksForRepoTaskCreator listTasksForRepoTaskCreator() {
        return new GitHubListTasksForRepoTaskCreator(1L);
    }

    static GitHubListTasksForRepoTaskCreatorType listTasksForRepoTaskCreatorType() {
        return GitHubListTasksForRepoTaskCreatorType.USER;
    }

    static GitHubListTasksForRepoTaskUserCollaborator listTasksForRepoTaskUserCollaborator() {
        return new GitHubListTasksForRepoTaskUserCollaborator(1L);
    }

    static GitHubListTasksForRepoTaskOwner listTasksForRepoTaskOwner() {
        return new GitHubListTasksForRepoTaskOwner(1L);
    }

    static GitHubListTasksForRepoTaskRepository listTasksForRepoTaskRepository() {
        return new GitHubListTasksForRepoTaskRepository(1L);
    }

    static GitHubListTasksForRepoTaskState listTasksForRepoTaskState() {
        return GitHubListTasksForRepoTaskState.QUEUED;
    }

    static GitHubListTasksForRepoTaskCustomAgent listTasksForRepoTaskCustomAgent() {
        return new GitHubListTasksForRepoTaskCustomAgent("task-1");
    }

    static GitHubListTasksForRepoTaskArtifactProvider listTasksForRepoTaskArtifactProvider() {
        return GitHubListTasksForRepoTaskArtifactProvider.GITHUB;
    }

    static GitHubListTasksForRepoTaskArtifactType listTasksForRepoTaskArtifactType() {
        return GitHubListTasksForRepoTaskArtifactType.PULL;
    }

    static GitHubListTasksForRepoTaskArtifactDataById listTasksForRepoTaskArtifactDataById() {
        return new GitHubListTasksForRepoTaskArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubListTasksForRepoTaskArtifactDataByRef listTasksForRepoTaskArtifactDataByRef() {
        return new GitHubListTasksForRepoTaskArtifactDataByRef("feature/fix", "main");
    }

    static GitHubListTasksForRepoTaskArtifact listTasksForRepoTaskArtifact() {
        return new GitHubListTasksForRepoTaskArtifact(
                listTasksForRepoTaskArtifactProvider(),
                listTasksForRepoTaskArtifactType(),
                listTasksForRepoTaskArtifactDataById());
    }

    static GitHubListTasksForRepoTask listTasksForRepoTask() {
        return new GitHubListTasksForRepoTask(
                "task-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Fix flaky test",
                listTasksForRepoTaskCreator(),
                listTasksForRepoTaskCreatorType(),
                List.of(listTasksForRepoTaskUserCollaborator()),
                listTasksForRepoTaskOwner(),
                listTasksForRepoTaskRepository(),
                listTasksForRepoTaskState(),
                1,
                List.of(listTasksForRepoTaskArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                listTasksForRepoTaskCustomAgent());
    }

    static GitHubListTasksForRepoResponse listTasksForRepoResponse() {
        return new GitHubListTasksForRepoResponse(List.of(listTasksForRepoTask()), 1, 1);
    }

    static GitHubListTasksForRepoSort listTasksForRepoSort() {
        return GitHubListTasksForRepoSort.UPDATED_AT;
    }

    static GitHubListTasksForRepoDirection listTasksForRepoDirection() {
        return GitHubListTasksForRepoDirection.ASC;
    }

    static GitHubListTasksForRepoFilter listTasksForRepoFilter() {
        return new GitHubListTasksForRepoFilter(
                listTasksForRepoSort(),
                listTasksForRepoDirection(),
                "completed",
                false,
                Instant.parse("2026-01-01T00:00:00Z"),
                List.of(1L, 2L));
    }

    static GitHubListTasksTaskCreator listTasksTaskCreator() {
        return new GitHubListTasksTaskCreator(1L);
    }

    static GitHubListTasksTaskCreatorType listTasksTaskCreatorType() {
        return GitHubListTasksTaskCreatorType.USER;
    }

    static GitHubListTasksTaskUserCollaborator listTasksTaskUserCollaborator() {
        return new GitHubListTasksTaskUserCollaborator(1L);
    }

    static GitHubListTasksTaskOwner listTasksTaskOwner() {
        return new GitHubListTasksTaskOwner(1L);
    }

    static GitHubListTasksTaskRepository listTasksTaskRepository() {
        return new GitHubListTasksTaskRepository(1L);
    }

    static GitHubListTasksTaskState listTasksTaskState() {
        return GitHubListTasksTaskState.QUEUED;
    }

    static GitHubListTasksTaskCustomAgent listTasksTaskCustomAgent() {
        return new GitHubListTasksTaskCustomAgent("task-1");
    }

    static GitHubListTasksTaskArtifactProvider listTasksTaskArtifactProvider() {
        return GitHubListTasksTaskArtifactProvider.GITHUB;
    }

    static GitHubListTasksTaskArtifactType listTasksTaskArtifactType() {
        return GitHubListTasksTaskArtifactType.PULL;
    }

    static GitHubListTasksTaskArtifactDataById listTasksTaskArtifactDataById() {
        return new GitHubListTasksTaskArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubListTasksTaskArtifactDataByRef listTasksTaskArtifactDataByRef() {
        return new GitHubListTasksTaskArtifactDataByRef("feature/fix", "main");
    }

    static GitHubListTasksTaskArtifact listTasksTaskArtifact() {
        return new GitHubListTasksTaskArtifact(
                listTasksTaskArtifactProvider(), listTasksTaskArtifactType(), listTasksTaskArtifactDataById());
    }

    static GitHubListTasksTask listTasksTask() {
        return new GitHubListTasksTask(
                "task-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Fix flaky test",
                listTasksTaskCreator(),
                listTasksTaskCreatorType(),
                List.of(listTasksTaskUserCollaborator()),
                listTasksTaskOwner(),
                listTasksTaskRepository(),
                listTasksTaskState(),
                1,
                List.of(listTasksTaskArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                listTasksTaskCustomAgent());
    }

    static GitHubListTasksResponse listTasksResponse() {
        return new GitHubListTasksResponse(List.of(listTasksTask()), 1, 1);
    }

    static GitHubListTasksSort listTasksSort() {
        return GitHubListTasksSort.UPDATED_AT;
    }

    static GitHubListTasksDirection listTasksDirection() {
        return GitHubListTasksDirection.ASC;
    }

    static GitHubListTasksFilter listTasksFilter() {
        return new GitHubListTasksFilter(
                listTasksSort(), listTasksDirection(), "completed", false, Instant.parse("2026-01-01T00:00:00Z"));
    }

    static GitHubCreateTaskInRepoResponseCreator createTaskInRepoResponseCreator() {
        return new GitHubCreateTaskInRepoResponseCreator(1L);
    }

    static GitHubCreateTaskInRepoResponseCreatorType createTaskInRepoResponseCreatorType() {
        return GitHubCreateTaskInRepoResponseCreatorType.USER;
    }

    static GitHubCreateTaskInRepoResponseUserCollaborator createTaskInRepoResponseUserCollaborator() {
        return new GitHubCreateTaskInRepoResponseUserCollaborator(1L);
    }

    static GitHubCreateTaskInRepoResponseOwner createTaskInRepoResponseOwner() {
        return new GitHubCreateTaskInRepoResponseOwner(1L);
    }

    static GitHubCreateTaskInRepoResponseRepository createTaskInRepoResponseRepository() {
        return new GitHubCreateTaskInRepoResponseRepository(1L);
    }

    static GitHubCreateTaskInRepoResponseState createTaskInRepoResponseState() {
        return GitHubCreateTaskInRepoResponseState.QUEUED;
    }

    static GitHubCreateTaskInRepoResponseCustomAgent createTaskInRepoResponseCustomAgent() {
        return new GitHubCreateTaskInRepoResponseCustomAgent("task-1");
    }

    static GitHubCreateTaskInRepoResponseArtifactProvider createTaskInRepoResponseArtifactProvider() {
        return GitHubCreateTaskInRepoResponseArtifactProvider.GITHUB;
    }

    static GitHubCreateTaskInRepoResponseArtifactType createTaskInRepoResponseArtifactType() {
        return GitHubCreateTaskInRepoResponseArtifactType.PULL;
    }

    static GitHubCreateTaskInRepoResponseArtifactDataById createTaskInRepoResponseArtifactDataById() {
        return new GitHubCreateTaskInRepoResponseArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubCreateTaskInRepoResponseArtifactDataByRef createTaskInRepoResponseArtifactDataByRef() {
        return new GitHubCreateTaskInRepoResponseArtifactDataByRef("feature/fix", "main");
    }

    static GitHubCreateTaskInRepoResponseArtifact createTaskInRepoResponseArtifact() {
        return new GitHubCreateTaskInRepoResponseArtifact(
                createTaskInRepoResponseArtifactProvider(),
                createTaskInRepoResponseArtifactType(),
                createTaskInRepoResponseArtifactDataById());
    }

    static GitHubCreateTaskInRepoResponse createTaskInRepoResponse() {
        return new GitHubCreateTaskInRepoResponse(
                "task-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Fix flaky test",
                createTaskInRepoResponseCreator(),
                createTaskInRepoResponseCreatorType(),
                List.of(createTaskInRepoResponseUserCollaborator()),
                createTaskInRepoResponseOwner(),
                createTaskInRepoResponseRepository(),
                createTaskInRepoResponseState(),
                1,
                List.of(createTaskInRepoResponseArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                createTaskInRepoResponseCustomAgent());
    }

    static GitHubCreateTaskInRepoPayload createTaskInRepoPayload() {
        return new GitHubCreateTaskInRepoPayload(
                "Fix the flaky test", "gpt-5", "reviewer", true, "main", "feature/fix");
    }

    static GitHubGetTaskByRepoAndIdResponseCreator getTaskByRepoAndIdResponseCreator() {
        return new GitHubGetTaskByRepoAndIdResponseCreator(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseCreatorType getTaskByRepoAndIdResponseCreatorType() {
        return GitHubGetTaskByRepoAndIdResponseCreatorType.USER;
    }

    static GitHubGetTaskByRepoAndIdResponseUserCollaborator getTaskByRepoAndIdResponseUserCollaborator() {
        return new GitHubGetTaskByRepoAndIdResponseUserCollaborator(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseOwner getTaskByRepoAndIdResponseOwner() {
        return new GitHubGetTaskByRepoAndIdResponseOwner(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseRepository getTaskByRepoAndIdResponseRepository() {
        return new GitHubGetTaskByRepoAndIdResponseRepository(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseState getTaskByRepoAndIdResponseState() {
        return GitHubGetTaskByRepoAndIdResponseState.QUEUED;
    }

    static GitHubGetTaskByRepoAndIdResponseCustomAgent getTaskByRepoAndIdResponseCustomAgent() {
        return new GitHubGetTaskByRepoAndIdResponseCustomAgent("task-1");
    }

    static GitHubGetTaskByRepoAndIdResponseArtifactProvider getTaskByRepoAndIdResponseArtifactProvider() {
        return GitHubGetTaskByRepoAndIdResponseArtifactProvider.GITHUB;
    }

    static GitHubGetTaskByRepoAndIdResponseArtifactType getTaskByRepoAndIdResponseArtifactType() {
        return GitHubGetTaskByRepoAndIdResponseArtifactType.PULL;
    }

    static GitHubGetTaskByRepoAndIdResponseArtifactDataById getTaskByRepoAndIdResponseArtifactDataById() {
        return new GitHubGetTaskByRepoAndIdResponseArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubGetTaskByRepoAndIdResponseArtifactDataByRef getTaskByRepoAndIdResponseArtifactDataByRef() {
        return new GitHubGetTaskByRepoAndIdResponseArtifactDataByRef("feature/fix", "main");
    }

    static GitHubGetTaskByRepoAndIdResponseArtifact getTaskByRepoAndIdResponseArtifact() {
        return new GitHubGetTaskByRepoAndIdResponseArtifact(
                getTaskByRepoAndIdResponseArtifactProvider(),
                getTaskByRepoAndIdResponseArtifactType(),
                getTaskByRepoAndIdResponseArtifactDataById());
    }

    static GitHubGetTaskByRepoAndIdResponseSessionUser getTaskByRepoAndIdResponseSessionUser() {
        return new GitHubGetTaskByRepoAndIdResponseSessionUser(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseSessionOwner getTaskByRepoAndIdResponseSessionOwner() {
        return new GitHubGetTaskByRepoAndIdResponseSessionOwner(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseSessionRepository getTaskByRepoAndIdResponseSessionRepository() {
        return new GitHubGetTaskByRepoAndIdResponseSessionRepository(1L);
    }

    static GitHubGetTaskByRepoAndIdResponseSessionState getTaskByRepoAndIdResponseSessionState() {
        return GitHubGetTaskByRepoAndIdResponseSessionState.QUEUED;
    }

    static GitHubGetTaskByRepoAndIdResponseSessionUsageType getTaskByRepoAndIdResponseSessionUsageType() {
        return GitHubGetTaskByRepoAndIdResponseSessionUsageType.AI_CREDITS;
    }

    static GitHubGetTaskByRepoAndIdResponseSessionUsage getTaskByRepoAndIdResponseSessionUsage() {
        return new GitHubGetTaskByRepoAndIdResponseSessionUsage(getTaskByRepoAndIdResponseSessionUsageType(), 1.5);
    }

    static GitHubGetTaskByRepoAndIdResponseSessionError getTaskByRepoAndIdResponseSessionError() {
        return new GitHubGetTaskByRepoAndIdResponseSessionError("Session failed");
    }

    static GitHubGetTaskByRepoAndIdResponseSession getTaskByRepoAndIdResponseSession() {
        return new GitHubGetTaskByRepoAndIdResponseSession(
                "task-1",
                "Fix flaky test",
                getTaskByRepoAndIdResponseSessionUser(),
                getTaskByRepoAndIdResponseSessionOwner(),
                getTaskByRepoAndIdResponseSessionRepository(),
                "task-1",
                getTaskByRepoAndIdResponseSessionState(),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                "Fix the flaky test",
                "feature/fix",
                "main",
                "gpt-5",
                getTaskByRepoAndIdResponseSessionUsage(),
                getTaskByRepoAndIdResponseSessionError());
    }

    static GitHubGetTaskByRepoAndIdResponse getTaskByRepoAndIdResponse() {
        return new GitHubGetTaskByRepoAndIdResponse(
                "task-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Fix flaky test",
                getTaskByRepoAndIdResponseCreator(),
                getTaskByRepoAndIdResponseCreatorType(),
                List.of(getTaskByRepoAndIdResponseUserCollaborator()),
                getTaskByRepoAndIdResponseOwner(),
                getTaskByRepoAndIdResponseRepository(),
                getTaskByRepoAndIdResponseState(),
                1,
                List.of(getTaskByRepoAndIdResponseArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                getTaskByRepoAndIdResponseCustomAgent(),
                List.of(getTaskByRepoAndIdResponseSession()));
    }

    static GitHubGetTaskByIdResponseCreator getTaskByIdResponseCreator() {
        return new GitHubGetTaskByIdResponseCreator(1L);
    }

    static GitHubGetTaskByIdResponseCreatorType getTaskByIdResponseCreatorType() {
        return GitHubGetTaskByIdResponseCreatorType.USER;
    }

    static GitHubGetTaskByIdResponseUserCollaborator getTaskByIdResponseUserCollaborator() {
        return new GitHubGetTaskByIdResponseUserCollaborator(1L);
    }

    static GitHubGetTaskByIdResponseOwner getTaskByIdResponseOwner() {
        return new GitHubGetTaskByIdResponseOwner(1L);
    }

    static GitHubGetTaskByIdResponseRepository getTaskByIdResponseRepository() {
        return new GitHubGetTaskByIdResponseRepository(1L);
    }

    static GitHubGetTaskByIdResponseState getTaskByIdResponseState() {
        return GitHubGetTaskByIdResponseState.QUEUED;
    }

    static GitHubGetTaskByIdResponseCustomAgent getTaskByIdResponseCustomAgent() {
        return new GitHubGetTaskByIdResponseCustomAgent("task-1");
    }

    static GitHubGetTaskByIdResponseArtifactProvider getTaskByIdResponseArtifactProvider() {
        return GitHubGetTaskByIdResponseArtifactProvider.GITHUB;
    }

    static GitHubGetTaskByIdResponseArtifactType getTaskByIdResponseArtifactType() {
        return GitHubGetTaskByIdResponseArtifactType.PULL;
    }

    static GitHubGetTaskByIdResponseArtifactDataById getTaskByIdResponseArtifactDataById() {
        return new GitHubGetTaskByIdResponseArtifactDataById(1L, "MDExOlB1bGxSZXF1ZXN0MQ==");
    }

    static GitHubGetTaskByIdResponseArtifactDataByRef getTaskByIdResponseArtifactDataByRef() {
        return new GitHubGetTaskByIdResponseArtifactDataByRef("feature/fix", "main");
    }

    static GitHubGetTaskByIdResponseArtifact getTaskByIdResponseArtifact() {
        return new GitHubGetTaskByIdResponseArtifact(
                getTaskByIdResponseArtifactProvider(),
                getTaskByIdResponseArtifactType(),
                getTaskByIdResponseArtifactDataById());
    }

    static GitHubGetTaskByIdResponseSessionUser getTaskByIdResponseSessionUser() {
        return new GitHubGetTaskByIdResponseSessionUser(1L);
    }

    static GitHubGetTaskByIdResponseSessionOwner getTaskByIdResponseSessionOwner() {
        return new GitHubGetTaskByIdResponseSessionOwner(1L);
    }

    static GitHubGetTaskByIdResponseSessionRepository getTaskByIdResponseSessionRepository() {
        return new GitHubGetTaskByIdResponseSessionRepository(1L);
    }

    static GitHubGetTaskByIdResponseSessionState getTaskByIdResponseSessionState() {
        return GitHubGetTaskByIdResponseSessionState.QUEUED;
    }

    static GitHubGetTaskByIdResponseSessionUsageType getTaskByIdResponseSessionUsageType() {
        return GitHubGetTaskByIdResponseSessionUsageType.AI_CREDITS;
    }

    static GitHubGetTaskByIdResponseSessionUsage getTaskByIdResponseSessionUsage() {
        return new GitHubGetTaskByIdResponseSessionUsage(getTaskByIdResponseSessionUsageType(), 1.5);
    }

    static GitHubGetTaskByIdResponseSessionError getTaskByIdResponseSessionError() {
        return new GitHubGetTaskByIdResponseSessionError("Session failed");
    }

    static GitHubGetTaskByIdResponseSession getTaskByIdResponseSession() {
        return new GitHubGetTaskByIdResponseSession(
                "task-1",
                "Fix flaky test",
                getTaskByIdResponseSessionUser(),
                getTaskByIdResponseSessionOwner(),
                getTaskByIdResponseSessionRepository(),
                "task-1",
                getTaskByIdResponseSessionState(),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                "Fix the flaky test",
                "feature/fix",
                "main",
                "gpt-5",
                getTaskByIdResponseSessionUsage(),
                getTaskByIdResponseSessionError());
    }

    static GitHubGetTaskByIdResponse getTaskByIdResponse() {
        return new GitHubGetTaskByIdResponse(
                "task-1",
                "https://api.github.com/agents/tasks/1",
                "https://github.com/octocat/Hello-World/tasks/1",
                "Fix flaky test",
                getTaskByIdResponseCreator(),
                getTaskByIdResponseCreatorType(),
                List.of(getTaskByIdResponseUserCollaborator()),
                getTaskByIdResponseOwner(),
                getTaskByIdResponseRepository(),
                getTaskByIdResponseState(),
                1,
                List.of(getTaskByIdResponseArtifact()),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                Instant.parse("2026-01-10T12:00:00Z"),
                getTaskByIdResponseCustomAgent(),
                List.of(getTaskByIdResponseSession()));
    }
}
