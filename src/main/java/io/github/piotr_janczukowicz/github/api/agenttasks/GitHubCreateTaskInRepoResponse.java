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
 * Agent task created in a repository.
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
@CatalogSchema("operations/agent-tasks/create-task-in-repo/201")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateTaskInRepoResponse(
        String id,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String name,
        @Nullable GitHubCreateTaskInRepoResponseCreator creator,
        @Nullable GitHubCreateTaskInRepoResponseCreatorType creatorType,
        @Deprecated @Nullable List<GitHubCreateTaskInRepoResponseUserCollaborator> userCollaborators,
        @Nullable GitHubCreateTaskInRepoResponseOwner owner,
        @Nullable GitHubCreateTaskInRepoResponseRepository repository,
        GitHubCreateTaskInRepoResponseState state,
        @Nullable Integer sessionCount,
        @Nullable List<GitHubCreateTaskInRepoResponseArtifact> artifacts,
        @Nullable Instant archivedAt,
        @Nullable Instant updatedAt,
        Instant createdAt,
        @Nullable GitHubCreateTaskInRepoResponseCustomAgent customAgent) {
    public GitHubCreateTaskInRepoResponse {
        requireNonNull(id, "id");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
    }
}
