package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Sort order for budget user states. */
@CatalogSchema("operations/billing/get-budget-user-states/parameters/4")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetBudgetUserStatesSortOrder {
    DESCENDING("0"),
    ASCENDING("1");

    private static final EnumUtils.WireIndex<GitHubGetBudgetUserStatesSortOrder> WIRE_INDEX =
            EnumUtils.index(GitHubGetBudgetUserStatesSortOrder.class, GitHubGetBudgetUserStatesSortOrder::wireValue);

    private final String wireValue;

    GitHubGetBudgetUserStatesSortOrder(final String wireValue) {
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
    public static GitHubGetBudgetUserStatesSortOrder fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHubGetBudgetUserStatesSortOrder value: " + value));
    }
}
