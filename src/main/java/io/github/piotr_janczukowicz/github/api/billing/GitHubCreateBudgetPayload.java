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
 * Request body for creating a budget for an enterprise.
 *
 * @param budgetAmount The budget amount in whole dollars.
 * @param preventFurtherUsage Whether to prevent additional spending once the budget is exceeded.
 * @param budgetAlerting Alerting configuration.
 * @param budgetScope The scope of the budget.
 * @param budgetEntityName The entity to apply the budget to. The expected value depends on {@code budget_scope}: the
 *     organization login for {@code organization}, {@code owner/repo} for {@code repository}, the cost center UUID for
 *     {@code cost_center} and {@code multi_user_cost_center}; not used for {@code enterprise},
 *     {@code multi_user_customer} and {@code user} (set the {@code user} field instead for {@code user}).
 * @param budgetType The type of pricing model used by the budget.
 * @param budgetProductSku A single product or SKU that will be covered in the budget.
 * @param user The username of the user for user scope budgets.
 * @param expiresAt The date the budget will expire in YYYY-MM-DD format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-budget/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateBudgetPayload(
        int budgetAmount,
        boolean preventFurtherUsage,
        GitHubCreateBudgetPayloadBudgetAlerting budgetAlerting,
        GitHubCreateBudgetPayloadBudgetScope budgetScope,
        @Nullable String budgetEntityName,
        GitHubCreateBudgetPayloadBudgetType budgetType,
        @Nullable String budgetProductSku,
        @Nullable String user,
        @Nullable LocalDate expiresAt) {
    public GitHubCreateBudgetPayload {
        requireNonNull(budgetAlerting, "budgetAlerting");
        requireNonNull(budgetScope, "budgetScope");
        requireNonNull(budgetType, "budgetType");
    }
}
