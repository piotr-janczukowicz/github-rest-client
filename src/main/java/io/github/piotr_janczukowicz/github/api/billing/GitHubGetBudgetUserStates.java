package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Per-user states of a multi-user budget.
 *
 * @param userStates Per-user state entries for the budget.
 * @param hasNextPage Indicates if there are more pages of results available.
 * @param totalCount Total number of user state entries matching the query.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-budget-user-states")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetBudgetUserStates(
        List<GitHubGetBudgetUserStatesUserState> userStates, boolean hasNextPage, int totalCount) {
    public GitHubGetBudgetUserStates {
        requireNonNull(userStates, "userStates");
    }
}
