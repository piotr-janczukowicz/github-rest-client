package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Effective user-level budget details.
 *
 * @param id The unique identifier of the effective budget.
 * @param budgetAmount The budget amount for the effective budget.
 * @param consumedAmount The consumed amount for the specified user within the effective budget.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get_all_budgets/properties/effective_budget")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAllBudgetsEffectiveBudget(String id, int budgetAmount, double consumedAmount) {

    public GitHubGetAllBudgetsEffectiveBudget {
        requireNonNull(id, "id");
    }
}
