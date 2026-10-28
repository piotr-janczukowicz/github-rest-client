package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * Budget configured for an organization.
 *
 * @param id The unique identifier for the budget
 * @param budgetType The type of pricing for the budget
 * @param budgetAmount The budget amount limit in whole dollars. For license-based products, this represents the number
 *     of licenses.
 * @param preventFurtherUsage The type of limit enforcement for the budget
 * @param budgetScope The scope of the budget
 * @param budgetEntityName The name of the entity for the budget (enterprise does not require a name).
 * @param user The user login when the budget is scoped to a single user (`user` scope).
 * @param consumedAmount The amount consumed for a user-scoped budget, or for a multi-user budget when filtering by
 *     user.
 * @param budgetProductSku A single product or sku to apply the budget to.
 * @param budgetAlerting Budget Alerting.
 * @param expiresAt The date the budget will expire in `YYYY-MM-DD` format. Only dates in the future are accepted. If
 *     not provided, the budget will not expire. Only supported for budgets with `budget_scope` of `user`
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({"components/schemas/budget", "components/schemas/get_all_budgets/properties/budgets/items"})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubBudget(
        String id,
        GitHubBudgetBudgetType budgetType,
        int budgetAmount,
        boolean preventFurtherUsage,
        GitHubBudgetBudgetScope budgetScope,
        @Nullable String budgetEntityName,
        @Nullable String user,
        @Nullable Double consumedAmount,
        String budgetProductSku,
        GitHubBudgetBudgetAlerting budgetAlerting,
        @Nullable LocalDate expiresAt) {

    public GitHubBudget {
        requireNonNull(id, "id");
        requireNonNull(budgetType, "budgetType");
        requireNonNull(budgetScope, "budgetScope");
        requireNonNull(budgetProductSku, "budgetProductSku");
        requireNonNull(budgetAlerting, "budgetAlerting");
    }
}
