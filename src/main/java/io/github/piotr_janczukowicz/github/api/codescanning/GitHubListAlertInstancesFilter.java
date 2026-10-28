package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/instances}, excluding
 * pagination.
 *
 * @param ref The Git reference for the results you want to list.
 * @param pr The number of the pull request for the results you want to list.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/list-alert-instances/parameters")
public record GitHubListAlertInstancesFilter(
        @Nullable String ref, @Nullable Integer pr) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAlertInstancesFilter empty() {
        return GitHubListAlertInstancesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (ref != null) {
            query.put("ref", ref);
        }
        if (pr != null) {
            query.put("pr", pr.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
