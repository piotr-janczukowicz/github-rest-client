package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Alerting settings for an enterprise budget update.
 *
 * @param willAlert Whether alerts are enabled for this budget.
 * @param alertRecipients Array of user login names who will receive alerts.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/update-budget/requestBody/properties/budget_alerting")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateBudgetPayloadBudgetAlerting(
        @Nullable Boolean willAlert, @Nullable List<String> alertRecipients) {
    public GitHubUpdateBudgetPayloadBudgetAlerting {
        if (alertRecipients != null) {
            alertRecipients = List.copyOf(alertRecipients);
        }
    }
}
