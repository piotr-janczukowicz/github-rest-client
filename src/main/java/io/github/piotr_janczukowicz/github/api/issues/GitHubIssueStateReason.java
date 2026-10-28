package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Reason for the current issue state. */
@CatalogSchema("components/schemas/issue/properties/state_reason")
public enum GitHubIssueStateReason {
    COMPLETED("completed"),
    REOPENED("reopened"),
    NOT_PLANNED("not_planned"),
    DUPLICATE("duplicate");

    private static final EnumUtils.WireIndex<GitHubIssueStateReason> WIRE_INDEX =
            EnumUtils.index(GitHubIssueStateReason.class, GitHubIssueStateReason::wireValue);

    private final String wireValue;

    GitHubIssueStateReason(final String wireValue) {
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
     * State reason from a wire value.
     *
     * @param value JSON value
     * @return matching reason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known reason
     */
    @JsonCreator
    public static GitHubIssueStateReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue state reason: " + value));
    }
}
