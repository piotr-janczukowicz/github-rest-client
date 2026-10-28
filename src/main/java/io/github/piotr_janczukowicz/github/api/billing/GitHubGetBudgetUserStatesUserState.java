package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * State of one user within a multi-user budget.
 *
 * @param user The login of the user, when the user record is available.
 * @param consumedAmount The amount currently consumed by this user against the budget.
 * @param targetAmount The target amount allocated to this user within the budget.
 * @param overrideBudgetId The ID of a user-scoped budget that overrides the per-user allocation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-budget-user-states/properties/user_states/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetBudgetUserStatesUserState(
        @Nullable String user,
        double consumedAmount,
        double targetAmount,
        @Nullable String overrideBudgetId) {}
