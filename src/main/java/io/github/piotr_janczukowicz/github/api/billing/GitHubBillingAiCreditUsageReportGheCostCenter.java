package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Cost center of a billing usage report.
 *
 * @param id The unique identifier of the cost center.
 * @param name The name of the cost center.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-ai-credit-usage-report-ghe/properties/costCenter")
@Flavors(GitHubFlavor.GHEC)
public record GitHubBillingAiCreditUsageReportGheCostCenter(String id, String name) {
    public GitHubBillingAiCreditUsageReportGheCostCenter {
        requireNonNull(id, "id");
        requireNonNull(name, "name");
    }
}
