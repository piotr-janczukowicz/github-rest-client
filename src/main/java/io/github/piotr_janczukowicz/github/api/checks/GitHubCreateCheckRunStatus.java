package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The current status of the check run when creating it. */
@CatalogSchema("operations/checks/create/requestBody/properties/status")
public enum GitHubCreateCheckRunStatus {
    QUEUED("queued"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    WAITING("waiting"),
    REQUESTED("requested"),
    PENDING("pending");

    private static final EnumUtils.WireIndex<GitHubCreateCheckRunStatus> WIRE_INDEX =
            EnumUtils.index(GitHubCreateCheckRunStatus.class, GitHubCreateCheckRunStatus::wireValue);

    private final String wireValue;

    GitHubCreateCheckRunStatus(final String wireValue) {
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
     * Resolves create check run status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCreateCheckRunStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known create check run status
     */
    @JsonCreator
    public static GitHubCreateCheckRunStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub create check run status: " + value));
    }
}
