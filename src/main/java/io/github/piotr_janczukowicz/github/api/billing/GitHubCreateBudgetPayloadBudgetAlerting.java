package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Alerting configuration for a budget created for an enterprise.
 *
 * @param willAlert Whether alerts are enabled for this budget.
 * @param alertRecipients Array of user login names who will receive alerts.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-budget/requestBody/properties/budget_alerting")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateBudgetPayloadBudgetAlerting(boolean willAlert, List<String> alertRecipients) {
    public GitHubCreateBudgetPayloadBudgetAlerting {
        alertRecipients = List.copyOf(requireNonNull(alertRecipients, "alertRecipients"));
    }
}
