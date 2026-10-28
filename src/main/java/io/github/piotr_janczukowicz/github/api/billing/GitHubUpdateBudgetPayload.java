package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * Request body for updating a budget of an enterprise.
 *
 * @param budgetAmount The budget amount in whole dollars.
 * @param preventFurtherUsage Whether to prevent additional spending once the budget is exceeded.
 * @param budgetAlerting Alerting settings for the budget.
 * @param expiresAt The date the budget will expire in YYYY-MM-DD format.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/update-budget/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubUpdateBudgetPayload(
        @Nullable Integer budgetAmount,
        @Nullable Boolean preventFurtherUsage,
        @Nullable GitHubUpdateBudgetPayloadBudgetAlerting budgetAlerting,

        @SuppressSchemaTypeMismatch("dump is string date or integer 0; integer 0 is not modeled") @Nullable OptionalNull<LocalDate> expiresAt) {}
