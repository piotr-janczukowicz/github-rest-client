package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility of a runner group. */
@CatalogSchema({
    "operations/actions/create-self-hosted-runner-group-for-org/requestBody/properties/visibility",
    "operations/actions/update-self-hosted-runner-group-for-org/requestBody/properties/visibility"
})
public enum GitHubRunnerGroupVisibility {
    SELECTED("selected"),
    ALL("all"),
    PRIVATE("private");

    private static final EnumUtils.WireIndex<GitHubRunnerGroupVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubRunnerGroupVisibility.class, GitHubRunnerGroupVisibility::wireValue);

    private final String wireValue;

    GitHubRunnerGroupVisibility(final String wireValue) {
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
    public static GitHubRunnerGroupVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubRunnerGroupVisibility value: " + value));
    }
}
