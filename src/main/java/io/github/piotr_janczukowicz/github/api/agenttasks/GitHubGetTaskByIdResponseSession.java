package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Session of a task.
 *
 * @param id Id
 * @param name Name
 * @param user User
 * @param owner Owner
 * @param repository Repository
 * @param taskId Task id
 * @param state State
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param completedAt Completed at
 * @param prompt Prompt
 * @param headRef Head ref
 * @param baseRef Base ref
 * @param model Model
 * @param usage Usage
 * @param error Error
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/get-task-by-id/200/properties/sessions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetTaskByIdResponseSession(
        String id,
        @Nullable String name,
        @Nullable GitHubGetTaskByIdResponseSessionUser user,
        @Nullable GitHubGetTaskByIdResponseSessionOwner owner,
        @Nullable GitHubGetTaskByIdResponseSessionRepository repository,
        @Nullable String taskId,
        GitHubGetTaskByIdResponseSessionState state,
        Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable Instant completedAt,
        @Nullable String prompt,
        @Nullable String headRef,
        @Nullable String baseRef,
        @Nullable String model,
        @Nullable GitHubGetTaskByIdResponseSessionUsage usage,
        @Nullable GitHubGetTaskByIdResponseSessionError error) {
    public GitHubGetTaskByIdResponseSession {
        requireNonNull(id, "id");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
    }
}
