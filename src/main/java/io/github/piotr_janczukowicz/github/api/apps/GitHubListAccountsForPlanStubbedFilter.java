package io.github.piotr_janczukowicz.github.api.apps;

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
 * Query filters for {@code GET /marketplace_listing/stubbed/plans/{plan_id}/accounts}, excluding pagination.
 *
 * @param sort The property to sort the results by.
 * @param direction To return the oldest accounts first, set to {@code asc}. Ignored without the {@code sort} parameter.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/apps/list-accounts-for-plan-stubbed/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAccountsForPlanStubbedFilter(
        @Nullable GitHubSort sort, @Nullable GitHubListAccountsForPlanDirection direction) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAccountsForPlanStubbedFilter empty() {
        return GitHubListAccountsForPlanStubbedFilterBuilder.builder().build();
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

        return Collections.unmodifiableMap(query);
    }
}
