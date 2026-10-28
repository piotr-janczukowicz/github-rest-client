package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Usage line item in an organization billing usage report.
 *
 * @param date Date of the usage line item.
 * @param product Product name.
 * @param sku SKU name.
 * @param quantity Quantity of the usage line item.
 * @param unitType Unit type of the usage line item.
 * @param pricePerUnit Price per unit of the usage line item.
 * @param grossAmount Gross amount of the usage line item.
 * @param discountAmount Discount amount of the usage line item.
 * @param netAmount Net amount of the usage line item.
 * @param organizationName Name of the organization.
 * @param repositoryName Name of the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/billing-usage-report/properties/usageItems/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubBillingUsageReportUsageItem(
        String date,
        String product,
        String sku,
        int quantity,
        @JsonProperty("unitType") String unitType,
        @JsonProperty("pricePerUnit") double pricePerUnit,
        @JsonProperty("grossAmount") double grossAmount,
        @JsonProperty("discountAmount") double discountAmount,
        @JsonProperty("netAmount") double netAmount,
        @JsonProperty("organizationName") String organizationName,
        @JsonProperty("repositoryName") @Nullable String repositoryName) {

    public GitHubBillingUsageReportUsageItem {
        requireNonNull(date, "date");
        requireNonNull(product, "product");
        requireNonNull(sku, "sku");
        requireNonNull(unitType, "unitType");
        requireNonNull(organizationName, "organizationName");
    }
}
