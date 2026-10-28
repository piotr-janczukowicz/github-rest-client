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
 * Paginated list of agent tasks.
 *
 * @param tasks Tasks
 * @param totalActiveCount Total active count
 * @param totalArchivedCount Total archived count
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/list-tasks/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListTasksResponse(
        List<GitHubListTasksTask> tasks,
        @Nullable Integer totalActiveCount,
        @Nullable Integer totalArchivedCount) {
    public GitHubListTasksResponse {
        requireNonNull(tasks, "tasks");
    }
}
