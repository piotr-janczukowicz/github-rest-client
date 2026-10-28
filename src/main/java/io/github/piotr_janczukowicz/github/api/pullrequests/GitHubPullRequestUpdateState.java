package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request update state. */
@CatalogSchema({
    "operations/pulls/update/requestBody/properties/state",
    "components/schemas/pull-request/properties/state"
})
public enum GitHubPullRequestUpdateState {
    OPEN("open"),
    CLOSED("closed");

    private static final EnumUtils.WireIndex<GitHubPullRequestUpdateState> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestUpdateState.class, GitHubPullRequestUpdateState::wireValue);

    private final String wireValue;

    GitHubPullRequestUpdateState(final String wireValue) {
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
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubPullRequestUpdateState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub pull request update state: " + value));
    }
}
