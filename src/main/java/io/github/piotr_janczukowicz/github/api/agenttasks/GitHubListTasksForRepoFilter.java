package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /agents/repos/{owner}/{repo}/tasks}, excluding pagination.
 *
 * @param sort The field to sort results by. Can be {@code updated_at} or {@code created_at}.
 * @param direction The direction to sort results. Can be {@code asc} or {@code desc}.
 * @param state Comma-separated list of task states to filter by. Can be any combination of: {@code queued},
 *     {@code in_progress}, {@code completed}, {@code failed}, {@code idle}, {@code waiting_for_user},
 *     {@code timed_out}, {@code cancelled}.
 * @param isArchived Filter by archived status. When {@code true}, returns only archived tasks. When {@code false} or
 *     omitted, returns only non-archived tasks. Defaults to {@code false}.
 * @param since Only show tasks updated at or after this time (ISO 8601 timestamp)
 * @param creatorId Filter tasks by creator user ID. Accepts one or more user IDs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/list-tasks-for-repo/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListTasksForRepoFilter(
        @Nullable GitHubListTasksForRepoSort sort,
        @Nullable GitHubListTasksForRepoDirection direction,
        @Nullable String state,
        @Nullable Boolean isArchived,
        @Nullable Instant since,
        @Nullable List<Long> creatorId) {

    public GitHubListTasksForRepoFilter {
        if (creatorId != null) {
            creatorId = List.copyOf(creatorId);
        }
    }

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListTasksForRepoFilter empty() {
        return GitHubListTasksForRepoFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter, with the {@code creator_id} values joined by commas.
     *
     * <p>The spec declares {@code creator_id} as an array, which OpenAPI sends as repeated keys; the endpoints use
     * {@link #toQueryValues()} for that. This map is for callers that need one value per name.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        toQueryValues().forEach((name, values) -> query.put(name, String.join(",", values)));
        return Collections.unmodifiableMap(query);
    }

    /**
     * Non-null query parameters for this filter, with {@code creator_id} repeated once per value as the spec's array
     * parameter requires.
     *
     * @return query map
     */
    public Map<String, List<String>> toQueryValues() {
        final Map<String, List<String>> query = new LinkedHashMap<>();

        if (sort != null) {
            query.put("sort", List.of(sort.wireValue()));
        }

        if (direction != null) {
            query.put("direction", List.of(direction.wireValue()));
        }

        if (state != null) {
            query.put("state", List.of(state));
        }

        if (isArchived != null) {
            query.put("is_archived", List.of(isArchived.toString()));
        }

        if (since != null) {
            query.put("since", List.of(since.toString()));
        }

        if (creatorId != null) {
            query.put("creator_id", creatorId.stream().map(String::valueOf).toList());
        }

        return Collections.unmodifiableMap(query);
    }
}
