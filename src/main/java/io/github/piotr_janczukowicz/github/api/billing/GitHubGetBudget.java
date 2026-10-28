package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Budget returned by a single-budget lookup.
 *
 * @param id ID of the budget.
 * @param budgetScope The type of scope for the budget
 * @param budgetEntityName The name of the entity to apply the budget to
 * @param user The user login when the budget is scoped to a single user (`user` scope).
 * @param budgetAmount The budget amount in whole dollars. For license-based products, this represents the number of
 *     licenses.
 * @param preventFurtherUsage Whether to prevent additional spending once the budget is exceeded
 * @param budgetProductSku A single product or sku to apply the budget to.
 * @param budgetType The type of pricing for the budget
 * @param budgetAlerting Budget Alerting.
 * @param consumedAmount The current usage amount counted toward the budget. How usage is calculated may vary by budget
 *     type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-budget")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetBudget(
        String id,
        GitHubGetBudgetBudgetScope budgetScope,
        String budgetEntityName,
        @Nullable String user,
        int budgetAmount,
        boolean preventFurtherUsage,
        String budgetProductSku,
        GitHubGetBudgetBudgetType budgetType,
        GitHubGetBudgetBudgetAlerting budgetAlerting,
        @Nullable Double consumedAmount) {

    public GitHubGetBudget {
        requireNonNull(id, "id");
        requireNonNull(budgetScope, "budgetScope");
        requireNonNull(budgetEntityName, "budgetEntityName");
        requireNonNull(budgetProductSku, "budgetProductSku");
        requireNonNull(budgetType, "budgetType");
        requireNonNull(budgetAlerting, "budgetAlerting");
    }
}
