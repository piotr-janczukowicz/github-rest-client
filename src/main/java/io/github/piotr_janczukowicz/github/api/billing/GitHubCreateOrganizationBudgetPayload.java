package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * Request body for creating an organization budget.
 *
 * @param budgetAmount The budget amount in whole dollars. For license-based products, this represents the number of
 *     licenses.
 * @param preventFurtherUsage Whether to prevent additional spending once the budget is exceeded. For `user` and
 *     `multi_user_customer` scopes, this must be `true`.
 * @param budgetAlerting Budget Alerting.
 * @param budgetScope The scope of the budget for this organization. - `organization`: Apply the budget to the
 *     organization. - `repository`: Apply the budget to a specific repository in the organization. -
 *     `multi_user_customer`: Apply a universal budget to all users in the organization. - `user`: Apply the budget to a
 *     single user in the organization. `user` and `multi_user_customer` scopes are only supported when
 *     `budget_product_sku` is `ai_credits` or `premium_requests`.
 * @param budgetEntityName The name of the entity to apply the budget to
 * @param budgetType The type of pricing model used by the budget. Determines how `budget_product_sku` is interpreted. -
 *     `BundlePricing`: Covers all AI credit SKUs. Set `budget_product_sku` to `ai_credits`. - `ProductPricing`: Covers
 *     all SKUs that belong to a product. Set `budget_product_sku` to a product such as `actions` or `packages`. -
 *     `SkuPricing`: Covers a single, specific SKU. Set `budget_product_sku` to a SKU such as `actions_linux`.
 * @param budgetProductSku A single product or SKU that will be covered in the budget
 * @param user The username of the user for `user` scope budgets. This field is required when `budget_scope` is `user`.
 * @param expiresAt The date the budget will expire in `YYYY-MM-DD` format. Only dates in the future are accepted. If
 *     not provided, the budget will not expire. Only supported for budgets with `budget_scope` of `user`
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/billing/create-organization-budget/requestBody")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubCreateOrganizationBudgetPayload(
        @Nullable Integer budgetAmount,
        @Nullable Boolean preventFurtherUsage,
        @Nullable GitHubCreateOrganizationBudgetBudgetAlerting budgetAlerting,
        @Nullable GitHubCreateOrganizationBudgetBudgetScope budgetScope,
        @Nullable String budgetEntityName,
        @Nullable GitHubCreateOrganizationBudgetBudgetType budgetType,
        @Nullable String budgetProductSku,
        @Nullable String user,
        @Nullable LocalDate expiresAt) {}
