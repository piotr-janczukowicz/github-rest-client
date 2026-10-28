package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

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
 * Query filters for {@code GET /enterprises/{enterprise}/audit-log}, excluding pagination.
 *
 * @param phrase A search phrase of the audit log entries
 * @param include The events to include in the audit log
 * @param order The order of the audit log entries
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-audit-log/parameters")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubGetAuditLogFilter(
        @Nullable String phrase,
        @Nullable GitHubGetAuditLogInclude include,
        @Nullable GitHubGetAuditLogOrder order) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetAuditLogFilter empty() {
        return GitHubGetAuditLogFilterBuilder.builder().build();
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
        if (include != null) {
            query.put("include", include.wireValue());
        }
        if (order != null) {
            query.put("order", order.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
