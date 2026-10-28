package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the workflow. */
@CatalogSchema("components/schemas/workflow/properties/state")
public enum GitHubWorkflowState {
    ACTIVE("active"),
    DELETED("deleted"),
    DISABLED_FORK("disabled_fork"),
    DISABLED_INACTIVITY("disabled_inactivity"),
    DISABLED_MANUALLY("disabled_manually"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubWorkflowState> WIRE_INDEX =
            EnumUtils.index(GitHubWorkflowState.class, GitHubWorkflowState::wireValue);

    private final String wireValue;

    GitHubWorkflowState(final String wireValue) {
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
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubWorkflowState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
