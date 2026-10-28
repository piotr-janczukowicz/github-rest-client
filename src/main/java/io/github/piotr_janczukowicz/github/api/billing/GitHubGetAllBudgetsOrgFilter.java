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
 * Query filters for {@code GET /organizations/{org}/settings/billing/budgets}, excluding pagination.
 *
 * @param scope Filter budgets by scope type. - `organization`: Budgets scoped to the organization. - `repository`:
 *     Budgets scoped to a repository. - `multi_user_customer`: Universal budgets that apply to all users in the
 *     organization. - `user`: Budgets scoped to an individual user.
 * @param user Filter consumed amount details for budgets by the specified user login.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/get-all-budgets-org/parameters")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubGetAllBudgetsOrgFilter(
        @Nullable GitHubGetAllBudgetsOrgScope scope,
        @Nullable String user) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubGetAllBudgetsOrgFilter empty() {
        return GitHubGetAllBudgetsOrgFilterBuilder.builder().build();
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
