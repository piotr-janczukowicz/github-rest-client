package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Budgets for an organization.
 *
 * @param budgets Array of budget objects for the enterprise
 * @param user User login included when the response is scoped with the `user` query parameter.
 * @param effectiveBudget Effective user-level budget details returned when the response is scoped with the `user` query
 *     parameter.
 * @param hasNextPage Indicates if there are more pages of results available
 * @param totalCount Total number of budgets matching the query
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get_all_budgets")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAllBudgets(
        List<GitHubBudget> budgets,
        @Nullable String user,
        @Nullable GitHubGetAllBudgetsEffectiveBudget effectiveBudget,
        @Nullable Boolean hasNextPage,
        @Nullable Integer totalCount) {

    public GitHubGetAllBudgets {
        requireNonNull(budgets, "budgets");
    }
}
