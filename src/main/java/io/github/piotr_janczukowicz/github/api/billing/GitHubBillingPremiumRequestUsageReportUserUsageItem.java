package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Usage line item in a billing premium request usage report.
 *
 * @param product Product name.
 * @param sku SKU name.
 * @param model Model name.
 * @param unitType Unit type of the usage line item.
 * @param pricePerUnit Price per unit of the usage line item.
 * @param grossQuantity Gross quantity of the usage line item.
 * @param grossAmount Gross amount of the usage line item.
 * @param discountQuantity Discount quantity of the usage line item.
 * @param discountAmount Discount amount of the usage line item.
 * @param netQuantity Net quantity of the usage line item.
 * @param netAmount Net amount of the usage line item.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-premium-request-usage-report-user/properties/usageItems/items")
@Flavors(GitHubFlavor.CLOUD)
public record GitHubBillingPremiumRequestUsageReportUserUsageItem(
        String product,
        String sku,
        String model,
        @JsonProperty("unitType") String unitType,
        @JsonProperty("pricePerUnit") double pricePerUnit,
        @JsonProperty("grossQuantity") double grossQuantity,
        @JsonProperty("grossAmount") double grossAmount,
        @JsonProperty("discountQuantity") double discountQuantity,
        @JsonProperty("discountAmount") double discountAmount,
        @JsonProperty("netQuantity") double netQuantity,
        @JsonProperty("netAmount") double netAmount) {

    public GitHubBillingPremiumRequestUsageReportUserUsageItem {
        requireNonNull(product, "product");
        requireNonNull(sku, "sku");
        requireNonNull(model, "model");
        requireNonNull(unitType, "unitType");
    }
}
