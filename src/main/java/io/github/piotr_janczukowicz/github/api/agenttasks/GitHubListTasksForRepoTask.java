package io.github.piotr_janczukowicz.github.api.agenttasks;

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
 * Agent task in a repository.
 *
 * @param id Id
 * @param url Url
 * @param htmlUrl Html url
 * @param name Name
 * @param creator Creator
 * @param creatorType Creator type
 * @param userCollaborators User collaborators
 * @param owner Owner
 * @param repository Repository
 * @param state State
 * @param sessionCount Session count
 * @param artifacts Artifacts
 * @param archivedAt Archived at
 * @param updatedAt Updated at
 * @param createdAt Created at
 * @param customAgent Custom agent
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/list-tasks-for-repo/200/properties/tasks/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListTasksForRepoTask(
        String id,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String name,
        @Nullable GitHubListTasksForRepoTaskCreator creator,
        @Nullable GitHubListTasksForRepoTaskCreatorType creatorType,
        @Deprecated @Nullable List<GitHubListTasksForRepoTaskUserCollaborator> userCollaborators,
        @Nullable GitHubListTasksForRepoTaskOwner owner,
        @Nullable GitHubListTasksForRepoTaskRepository repository,
        GitHubListTasksForRepoTaskState state,
        @Nullable Integer sessionCount,
        @Nullable List<GitHubListTasksForRepoTaskArtifact> artifacts,
        @Nullable Instant archivedAt,
        @Nullable Instant updatedAt,
        Instant createdAt,
        @Nullable GitHubListTasksForRepoTaskCustomAgent customAgent) {
    public GitHubListTasksForRepoTask {
        requireNonNull(id, "id");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
    }
}
