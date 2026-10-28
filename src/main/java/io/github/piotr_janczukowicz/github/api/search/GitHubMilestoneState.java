package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of a milestone. */
@CatalogSchema({
    "components/schemas/milestone/properties/state",
    "operations/issues/create-milestone/requestBody/properties/state",
    "operations/issues/update-milestone/requestBody/properties/state"
})
public enum GitHubMilestoneState {
    OPEN("open"),
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubMilestoneState> WIRE_INDEX =
            EnumUtils.index(GitHubMilestoneState.class, GitHubMilestoneState::wireValue);

    private final String wireValue;

    GitHubMilestoneState(final String wireValue) {
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
     * Milestone state from a wire value.
     *
     * @param value JSON value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubMilestoneState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub milestone state: " + value));
    }
}
