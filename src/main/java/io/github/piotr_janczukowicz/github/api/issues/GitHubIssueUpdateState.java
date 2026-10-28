package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Open or closed state for issue update requests. */
@CatalogSchema("operations/issues/update/requestBody/properties/state")
public enum GitHubIssueUpdateState {
    OPEN("open"),
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubIssueUpdateState> WIRE_INDEX =
            EnumUtils.index(GitHubIssueUpdateState.class, GitHubIssueUpdateState::wireValue);

    private final String wireValue;

    GitHubIssueUpdateState(final String wireValue) {
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
     * Update state from a wire value.
     *
     * @param value JSON value
     * @return matching update state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known update state
     */
    @JsonCreator
    public static GitHubIssueUpdateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue update state: " + value));
    }
}
