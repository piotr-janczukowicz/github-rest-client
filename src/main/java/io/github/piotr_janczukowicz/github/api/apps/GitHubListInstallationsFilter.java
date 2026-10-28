package io.github.piotr_janczukowicz.github.api.apps;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /app/installations}, excluding pagination.
 *
 * @param since Only show results that were last updated after the given time.
 * @param outdated Filter installations by outdated status.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/list-installations/parameters")
public record GitHubListInstallationsFilter(
        @Nullable Instant since, @Nullable String outdated) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListInstallationsFilter empty() {
        return GitHubListInstallationsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (since != null) {
            query.put("since", since.toString());
        }

        if (outdated != null) {
            query.put("outdated", outdated);
        }

        return Collections.unmodifiableMap(query);
    }
}
