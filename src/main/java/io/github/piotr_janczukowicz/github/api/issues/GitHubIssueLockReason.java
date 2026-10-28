package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Reason for locking an issue conversation. */
@CatalogSchema("operations/issues/lock/requestBody/properties/lock_reason")
public enum GitHubIssueLockReason {
    OFF_TOPIC("off-topic"),
    TOO_HEATED("too heated"),
    RESOLVED("resolved"),
    SPAM("spam");

    private static final EnumUtils.WireIndex<GitHubIssueLockReason> WIRE_INDEX =
            EnumUtils.index(GitHubIssueLockReason.class, GitHubIssueLockReason::wireValue);

    private final String wireValue;

    GitHubIssueLockReason(final String wireValue) {
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
     * Lock reason from a wire value.
     *
     * @param value JSON value
     * @return matching lock reason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known lock reason
     */
    @JsonCreator
    public static GitHubIssueLockReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub issue lock reason: " + value));
    }
}
