package io.github.piotr_janczukowicz.github.api.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Cap budget projection of a cost center AI credit pool.
 *
 * @param targetAmount The AI credit pool cap target amount, in AI Credits.
 * @param currentAmount The current-month applied amount against the AI credit pool cap.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-cost-center/properties/ai_credit_pool_state")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetCostCenterAiCreditPoolState(
        @Nullable Double targetAmount, @Nullable Double currentAmount) {}
