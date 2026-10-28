package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State of the cost center after deletion. */
@CatalogSchema("components/schemas/delete-cost-center/properties/costCenterState")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubDeleteCostCenterCostCenterState {
    COST_CENTER_ARCHIVED("CostCenterArchived");

    private static final EnumUtils.WireIndex<GitHubDeleteCostCenterCostCenterState> WIRE_INDEX = EnumUtils.index(
            GitHubDeleteCostCenterCostCenterState.class, GitHubDeleteCostCenterCostCenterState::wireValue);

    private final String wireValue;

    GitHubDeleteCostCenterCostCenterState(final String wireValue) {
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
    public static GitHubDeleteCostCenterCostCenterState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHubDeleteCostCenterCostCenterState value: " + value));
    }
}
