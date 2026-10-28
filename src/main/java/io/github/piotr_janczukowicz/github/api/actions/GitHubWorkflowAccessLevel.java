package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Level of access that workflows outside of the repository have to actions and reusable workflows. */
@CatalogSchema("components/schemas/actions-workflow-access-to-repository/properties/access_level")
public enum GitHubWorkflowAccessLevel {
    NONE("none"),
    USER("user"),
    ORGANIZATION("organization"),
    ENTERPRISE("enterprise");

    private static final EnumUtils.WireIndex<GitHubWorkflowAccessLevel> WIRE_INDEX =
            EnumUtils.index(GitHubWorkflowAccessLevel.class, GitHubWorkflowAccessLevel::wireValue);

    private final String wireValue;

    GitHubWorkflowAccessLevel(final String wireValue) {
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
    public static GitHubWorkflowAccessLevel fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHubWorkflowAccessLevel value: " + value));
    }
}
