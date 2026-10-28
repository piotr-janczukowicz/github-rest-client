package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Alerting configuration for a budget.
 *
 * @param willAlert Whether alerts are enabled for this budget. Ignored for user-scope as alerting is disabled for them.
 * @param alertRecipients Array of user login names who will receive alerts. Ignored for user-scope as alerting is
 *     disabled for them.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/budget/properties/budget_alerting",
    "components/schemas/get_all_budgets/properties/budgets/items/properties/budget_alerting"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubBudgetBudgetAlerting(boolean willAlert, List<String> alertRecipients) {

    public GitHubBudgetBudgetAlerting {
        requireNonNull(alertRecipients, "alertRecipients");
    }
}
