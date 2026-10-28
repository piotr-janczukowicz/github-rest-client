package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Type of pricing model used by a budget. */
@CatalogSchema("operations/billing/create-budget/requestBody/properties/budget_type")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubCreateBudgetPayloadBudgetType {
    BUNDLE_PRICING("BundlePricing"),
    PRODUCT_PRICING("ProductPricing"),
    SKU_PRICING("SkuPricing");

    private static final EnumUtils.WireIndex<GitHubCreateBudgetPayloadBudgetType> WIRE_INDEX =
            EnumUtils.index(GitHubCreateBudgetPayloadBudgetType.class, GitHubCreateBudgetPayloadBudgetType::wireValue);

    private final String wireValue;

    GitHubCreateBudgetPayloadBudgetType(final String wireValue) {
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
     * Resolves the constant of this enum from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateBudgetPayloadBudgetType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHubCreateBudgetPayloadBudgetType value: " + value));
    }
}
