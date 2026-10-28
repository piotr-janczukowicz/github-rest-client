package io.github.piotr_janczukowicz.github.api.copilot;

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
 * Query filters for {@code GET /enterprises/{enterprise}/copilot/usage-records}, excluding pagination.
 *
 * @param phrase A search phrase to filter usage records. Supported qualifiers: {@code type} (request/response),
 *     {@code user_id}, {@code created}.
 * @param order The order of usage records. Newest first is {@code desc}, oldest first is {@code asc}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-usage-records/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetUsageRecordsFilter(
        @Nullable String phrase, @Nullable GitHubGetUsageRecordsSortOrder order) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetUsageRecordsFilter empty() {
        return GitHubGetUsageRecordsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (phrase != null) {
            query.put("phrase", phrase);
        }
        if (order != null) {
            query.put("order", order.wireValue());
        }
        return Collections.unmodifiableMap(query);
    }
}
