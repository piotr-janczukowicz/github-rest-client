package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/actions/runs/{run_id}/artifacts}, excluding pagination.
 *
 * @param name The name field of an artifact
 * @param direction The direction to sort the results by
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/actions/list-workflow-run-artifacts/parameters")
@RecordBuilder
public record GitHubListWorkflowRunArtifactsFilter(
        @Nullable String name, @Nullable GitHubDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListWorkflowRunArtifactsFilter empty() {
        return GitHubListWorkflowRunArtifactsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (name != null) {
            query.put("name", name);
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
