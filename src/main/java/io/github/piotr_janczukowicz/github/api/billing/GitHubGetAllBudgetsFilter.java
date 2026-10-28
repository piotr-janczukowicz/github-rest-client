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
 * Query filters for {@code GET /enterprises/{enterprise}/settings/billing/budgets}, excluding pagination.
 *
 * @param scope Filter budgets by scope type.
 * @param user Filter consumed amount details for budgets by the specified user login.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-all-budgets/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetAllBudgetsFilter(
        @Nullable GitHubGetAllBudgetsScope scope, @Nullable String user) {
    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetAllBudgetsFilter empty() {
        return GitHubGetAllBudgetsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();
        if (scope != null) {
            query.put("scope", scope.wireValue());
        }
        if (user != null) {
            query.put("user", user);
        }
        return Collections.unmodifiableMap(query);
    }
}
