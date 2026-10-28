package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /enterprises/{enterprise}/code-scanning/alerts}, excluding pagination.
 *
 * @param toolName The name of a code scanning tool.
 * @param toolGuid The GUID of a code scanning tool.
 * @param direction The direction to sort the results by.
 * @param state If specified, only code scanning alerts with this state will be returned.
 * @param sort The property by which to sort the results.
 * @param assignees Filter alerts by assignees.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/list-alerts-for-enterprise/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubListAlertsForEnterpriseFilter(
        @Nullable String toolName,
        @Nullable String toolGuid,
        @Nullable GitHubDirection direction,
        @Nullable GitHubCodeScanningAlertStateQuery state,
        @Nullable GitHubListAlertsForEnterpriseSort sort,
        @Nullable String assignees) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAlertsForEnterpriseFilter empty() {
        return GitHubListAlertsForEnterpriseFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (toolName != null) {
            query.put("tool_name", toolName);
        }
        if (toolGuid != null) {
            query.put("tool_guid", toolGuid);
        }
        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (state != null) {
            query.put("state", state.wireValue());
        }
        if (sort != null) {
            query.put("sort", sort.wireValue());
        }
        if (assignees != null) {
            query.put("assignees", assignees);
        }
        return Collections.unmodifiableMap(query);
    }
}
