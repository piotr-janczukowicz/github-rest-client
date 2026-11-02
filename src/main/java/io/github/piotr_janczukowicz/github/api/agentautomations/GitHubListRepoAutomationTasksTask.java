package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The task of the list automation tasks.
 *
 * @param id Unique task identifier
 * @param url API URL for this task
 * @param htmlUrl Web URL for this task
 * @param name Human-readable name derived from the task prompt
 * @param clients Known client products that created or participated in this task, independent of its executing agents.
 *     Omitted when no client identity is known.
 * @param creator The entity who created this task
 * @param creatorType Type of the task creator
 * @param userCollaborators User objects of collaborators on this task
 * @param owner The owner of the repository
 * @param repository The repository this task belongs to
 * @param state Current state of the task, derived from its most recent session
 * @param sessionCount Number of sessions in this task
 * @param artifacts Resources created by this task (PRs, branches, etc.)
 * @param archivedAt Timestamp when the task was archived, null if not archived
 * @param updatedAt Timestamp of the most recent update
 * @param createdAt Timestamp when the task was created
 * @param customAgent Custom agent metadata associated with this task
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-repo-automation-tasks/200/properties/tasks/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListRepoAutomationTasksTask(
        String id,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String name,
        @Nullable List<GitHubListRepoAutomationTasksTaskClient> clients,
        @Nullable GitHubListRepoAutomationTasksTaskCreator creator,
        @Nullable GitHubListRepoAutomationTasksTaskCreatorType creatorType,
        @Deprecated @Nullable List<GitHubListRepoAutomationTasksTaskUserCollaborator> userCollaborators,
        @Nullable GitHubListRepoAutomationTasksTaskOwner owner,
        @Nullable GitHubListRepoAutomationTasksTaskRepository repository,
        GitHubListRepoAutomationTasksTaskState state,
        @Nullable Integer sessionCount,
        @Nullable List<GitHubListRepoAutomationTasksTaskArtifact> artifacts,
        @Nullable Instant archivedAt,
        @Nullable Instant updatedAt,
        Instant createdAt,
        @Nullable GitHubListRepoAutomationTasksTaskCustomAgent customAgent) {
    public GitHubListRepoAutomationTasksTask {
        requireNonNull(id, "id");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
    }
}
