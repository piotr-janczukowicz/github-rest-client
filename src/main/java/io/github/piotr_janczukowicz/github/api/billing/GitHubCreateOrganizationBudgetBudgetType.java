package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of pricing model used by an organization budget. */
@CatalogSchema("operations/billing/create-organization-budget/requestBody/properties/budget_type")
@Flavors(GitHubFlavor.CLOUD)
public enum GitHubCreateOrganizationBudgetBudgetType {
    BUNDLE_PRICING("BundlePricing"),
    PRODUCT_PRICING("ProductPricing"),
    SKU_PRICING("SkuPricing");

    private static final EnumUtils.WireIndex<GitHubCreateOrganizationBudgetBudgetType> WIRE_INDEX = EnumUtils.index(
            GitHubCreateOrganizationBudgetBudgetType.class, GitHubCreateOrganizationBudgetBudgetType::wireValue);

    private final String wireValue;

    GitHubCreateOrganizationBudgetBudgetType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Resolves type of pricing model used by an organization budget from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCreateOrganizationBudgetBudgetType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateOrganizationBudgetBudgetType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubCreateOrganizationBudgetBudgetType value: " + value));
    }
}
