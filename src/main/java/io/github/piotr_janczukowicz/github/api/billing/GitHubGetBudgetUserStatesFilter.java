package io.github.piotr_janczukowicz.github.api.billing;

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
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/budgets/{budget_id}/user-states}, excluding
 * pagination.
 *
 * @param sortOrder Sort order for results.
 * @param user Filter user states to a specific user login.
 * @param thresholdLowerBound Filter user states whose threshold percentage is at or above this value.
 * @param thresholdUpperBound Filter user states whose threshold percentage is at or below this value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-budget-user-states/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetBudgetUserStatesFilter(
        @Nullable GitHubGetBudgetUserStatesSortOrder sortOrder,
        @Nullable String user,
        @Nullable Integer thresholdLowerBound,
        @Nullable Integer thresholdUpperBound) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetBudgetUserStatesFilter empty() {
        return GitHubGetBudgetUserStatesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (sortOrder != null) {
            query.put("sort_order", sortOrder.wireValue());
        }
        if (user != null) {
            query.put("user", user);
        }
        if (thresholdLowerBound != null) {
            query.put("threshold_lower_bound", thresholdLowerBound.toString());
        }
        if (thresholdUpperBound != null) {
            query.put("threshold_upper_bound", thresholdUpperBound.toString());
        }
        return Collections.unmodifiableMap(query);
    }
}
