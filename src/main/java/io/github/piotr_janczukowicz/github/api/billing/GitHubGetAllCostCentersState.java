package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State to filter cost centers by. */
@CatalogSchema("operations/billing/get-all-cost-centers/parameters/1")
@Flavors(GitHubFlavor.GHEC)
public enum GitHubGetAllCostCentersState {
    ACTIVE("active"),
    DELETED("deleted");

    private static final EnumUtils.WireIndex<GitHubGetAllCostCentersState> WIRE_INDEX =
            EnumUtils.index(GitHubGetAllCostCentersState.class, GitHubGetAllCostCentersState::wireValue);

    private final String wireValue;

    GitHubGetAllCostCentersState(final String wireValue) {
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
    public static GitHubGetAllCostCentersState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubGetAllCostCentersState value: " + value));
    }
}
