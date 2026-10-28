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
 * Request body for updating an organization budget.
 *
 * @param budgetAmount The budget amount in whole dollars. For license-based products, this represents the number of
 *     licenses.
 * @param preventFurtherUsage Whether to prevent additional spending once the budget is exceeded. For budgets with
 *     {@code user} or {@code multi_user_customer} scope, this must remain {@code true}.
 * @param budgetAlerting Alerting settings for the budget.
 * @param expiresAt The date the budget will expire in {@code YYYY-MM-DD} format. Only dates in the future are accepted.
 *     Setting to JSON {@code null} removes the expiration date from a budget if set. The integer {@code 0} form is not
 *     modeled. Only supported for existing user-scoped budgets.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/update-budget-org/requestBody")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubUpdateBudgetOrgPayload(
        @Nullable Integer budgetAmount,
        @Nullable Boolean preventFurtherUsage,
        @Nullable GitHubCreateOrganizationBudgetBudgetAlerting budgetAlerting,

        @SuppressSchemaTypeMismatch("dump is string date or integer 0; integer 0 is not modeled") @Nullable OptionalNull<LocalDate> expiresAt) {}
