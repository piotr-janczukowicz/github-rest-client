package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Paginated list of agent tasks in a repository.
 *
 * @param tasks Tasks
 * @param totalActiveCount Total active count
 * @param totalArchivedCount Total archived count
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/list-tasks-for-repo/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListTasksForRepoResponse(
        List<GitHubListTasksForRepoTask> tasks,
        @Nullable Integer totalActiveCount,
        @Nullable Integer totalArchivedCount) {
    public GitHubListTasksForRepoResponse {
        requireNonNull(tasks, "tasks");
    }
}
