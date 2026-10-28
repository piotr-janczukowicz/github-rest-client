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
 * Query filters for {@code GET /enterprise-installation/{enterprise_or_org}/server-statistics}, excluding pagination.
 *
 * @param dateStart A cursor, as given in the Link header, that limits the query to events after it
 * @param dateEnd A cursor, as given in the Link header, that limits the query to events before it
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/get-server-statistics/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetServerStatisticsFilter(
        @Nullable String dateStart, @Nullable String dateEnd) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetServerStatisticsFilter empty() {
        return GitHubGetServerStatisticsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (dateStart != null) {
            query.put("date_start", dateStart);
        }
        if (dateEnd != null) {
            query.put("date_end", dateEnd);
        }

        return Collections.unmodifiableMap(query);
    }
}
