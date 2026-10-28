package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/commits/{ref}/check-suites}, excluding pagination.
 *
 * @param appId Filters check suites by GitHub App {@code id}.
 * @param checkName Filters checks suites by the name of the check run.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/list-suites-for-ref/parameters")
public record GitHubListSuitesForRefFilter(
        @Nullable Long appId, @Nullable String checkName) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSuitesForRefFilter empty() {
        return GitHubListSuitesForRefFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (appId != null) {
            query.put("app_id", appId.toString());
        }
        if (checkName != null) {
            query.put("check_name", checkName);
        }

        return Collections.unmodifiableMap(query);
    }
}
