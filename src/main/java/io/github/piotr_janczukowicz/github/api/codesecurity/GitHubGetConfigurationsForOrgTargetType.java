package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Target type of code security configurations to list. */
@CatalogSchema("operations/code-security/get-configurations-for-org/parameters/1")
public enum GitHubGetConfigurationsForOrgTargetType {
    GLOBAL("global"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubGetConfigurationsForOrgTargetType> WIRE_INDEX = EnumUtils.index(
            GitHubGetConfigurationsForOrgTargetType.class, GitHubGetConfigurationsForOrgTargetType::wireValue);

    private final String wireValue;

    GitHubGetConfigurationsForOrgTargetType(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubGetConfigurationsForOrgTargetType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubGetConfigurationsForOrgTargetType value: " + value));
    }
}
