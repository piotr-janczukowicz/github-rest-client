package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/commits/{ref}/check-runs}, excluding pagination.
 *
 * @param checkName Returns check runs with the specified {@code name}.
 * @param status Returns check runs with the specified {@code status}.
 * @param filter Filters check runs by their {@code completed_at} timestamp.
 * @param appId Filters check runs by GitHub App {@code id}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/list-for-ref/parameters")
public record GitHubListForRefFilter(
        @Nullable String checkName,
        @Nullable GitHubCheckRunQueryStatus status,
        @Nullable GitHubCheckRunFilterMode filter,
        @Nullable Long appId) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForRefFilter empty() {
        return GitHubListForRefFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (checkName != null) {
            query.put("check_name", checkName);
        }
        if (status != null) {
            query.put("status", status.wireValue());
        }
        if (filter != null) {
            query.put("filter", filter.wireValue());
        }
        if (appId != null) {
            query.put("app_id", appId.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
