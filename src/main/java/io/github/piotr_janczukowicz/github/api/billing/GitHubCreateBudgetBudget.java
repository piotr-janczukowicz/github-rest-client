package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * Budget created by a create request.
 *
 * @param id ID of the budget.
 * @param budgetScope The type of scope for the budget
 * @param budgetEntityName The name of the entity to apply the budget to
 * @param budgetAmount The budget amount in whole dollars. For license-based products, this represents the number of
 *     licenses.
 * @param preventFurtherUsage Whether to prevent additional spending once the budget is exceeded
 * @param budgetProductSku A single product or sku to apply the budget to.
 * @param budgetType The type of pricing for the budget
 * @param budgetAlerting Budget Alerting.
 * @param expiresAt The date the budget will expire in `YYYY-MM-DD` format. Only dates in the future are accepted. If
 *     not provided, the budget will not expire. Only supported for budgets with `budget_scope` of `user`
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/create-budget/properties/budget")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateBudgetBudget(
        @Nullable String id,
        @Nullable GitHubBudgetBudgetScope budgetScope,
        @Nullable String budgetEntityName,
        @Nullable Integer budgetAmount,
        @Nullable Boolean preventFurtherUsage,
        @Nullable String budgetProductSku,
        @Nullable GitHubBudgetBudgetType budgetType,
        @Nullable GitHubCreateBudgetBudgetBudgetAlerting budgetAlerting,
        @Nullable LocalDate expiresAt) {

    public GitHubCreateBudgetBudget {
        requireAtLeastIfPresent(budgetAmount, 0, "budgetAmount");
    }
}
