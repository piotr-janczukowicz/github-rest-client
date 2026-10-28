package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /agents/automations/{automation_id}/tasks}, excluding pagination.
 *
 * @param sort The field to sort results by. Can be {@code updated_at} or {@code created_at}.
 * @param direction The direction to sort results. Can be {@code asc} or {@code desc}.
 * @param state Comma-separated list of task states to filter by. Can be any combination of: {@code queued},
 *     {@code in_progress}, {@code completed}, {@code failed}, {@code idle}, {@code waiting_for_user},
 *     {@code timed_out}, {@code cancelled}.
 * @param isArchived Filter by archived status. When {@code true}, returns only archived tasks. When {@code false} or
 *     omitted, returns only non-archived tasks. Defaults to {@code false}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-automation-tasks/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationTasksFilter(
        @Nullable GitHubListAutomationTasksSort sort,
        @Nullable GitHubListAutomationTasksDirection direction,
        @Nullable String state,
        @Nullable Boolean isArchived) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAutomationTasksFilter empty() {
        return GitHubListAutomationTasksFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (sort != null) {
            query.put("sort", sort.wireValue());
        }

        if (direction != null) {
            query.put("direction", direction.wireValue());
        }

        if (state != null) {
            query.put("state", state);
        }

        if (isArchived != null) {
            query.put("is_archived", isArchived.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
