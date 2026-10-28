package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Alerting configuration for a created budget.
 *
 * @param willAlert Whether alerts are enabled for this budget. Rejected for user-scope as alerting is always disabled
 *     for them.
 * @param alertRecipients Array of user login names who will receive alerts. Rejected for user-scope as alerting is
 *     always disabled for them.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/create-budget/properties/budget/properties/budget_alerting")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateBudgetBudgetBudgetAlerting(
        @Nullable Boolean willAlert, @Nullable List<String> alertRecipients) {}
