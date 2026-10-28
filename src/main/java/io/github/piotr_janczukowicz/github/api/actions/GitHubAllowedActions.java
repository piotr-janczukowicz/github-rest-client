package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The permissions policy that controls the actions and reusable workflows that are allowed to run. */
@CatalogSchema("components/schemas/allowed-actions")
public enum GitHubAllowedActions {
    ALL("all"),
    LOCAL_ONLY("local_only"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubAllowedActions> WIRE_INDEX =
            EnumUtils.index(GitHubAllowedActions.class, GitHubAllowedActions::wireValue);

    private final String wireValue;

    GitHubAllowedActions(final String wireValue) {
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
     * Type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubAllowedActions fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubAllowedActions value: " + value));
    }
}
