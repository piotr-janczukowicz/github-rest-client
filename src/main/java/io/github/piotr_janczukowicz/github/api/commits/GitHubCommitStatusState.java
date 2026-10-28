package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The state of the status. */
@CatalogSchema("operations/repos/create-commit-status/requestBody/properties/state")
public enum GitHubCommitStatusState {
    ERROR("error"),
    FAILURE("failure"),
    PENDING("pending"),
    SUCCESS("success");

    private static final EnumUtils.WireIndex<GitHubCommitStatusState> WIRE_INDEX =
            EnumUtils.index(GitHubCommitStatusState.class, GitHubCommitStatusState::wireValue);

    private final String wireValue;

    GitHubCommitStatusState(final String wireValue) {
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
     * Commit status state from a wire value.
     *
     * @param value JSON value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubCommitStatusState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub commit status state: " + value));
    }
}
