package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Issue list {@code state} query parameter. */
@CatalogSchema({
    "operations/issues/list/parameters/1",
    "operations/issues/list-for-org/parameters/2",
    "operations/issues/list-for-repo/parameters/3",
    "operations/issues/list-for-authenticated-user/parameters/1",
    "operations/issues/list-milestones/parameters/2"
})
public enum GitHubListIssuesState {
    OPEN("open"),
    CLOSED("closed"),
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubListIssuesState> WIRE_INDEX =
            EnumUtils.index(GitHubListIssuesState.class, GitHubListIssuesState::wireValue);

    private final String wireValue;

    GitHubListIssuesState(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * List state from a wire value.
     *
     * @param value query parameter value
     * @return matching list state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known list state
     */
    @JsonCreator
    public static GitHubListIssuesState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub list issues state: " + value));
    }
}
