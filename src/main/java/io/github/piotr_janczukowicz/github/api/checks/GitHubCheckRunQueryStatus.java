package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The phase of the lifecycle that the check run is in, used as a query filter. */
@CatalogSchema("components/parameters/status/schema")
public enum GitHubCheckRunQueryStatus {
    QUEUED("queued"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    private static final EnumUtils.WireIndex<GitHubCheckRunQueryStatus> WIRE_INDEX =
            EnumUtils.index(GitHubCheckRunQueryStatus.class, GitHubCheckRunQueryStatus::wireValue);

    private final String wireValue;

    GitHubCheckRunQueryStatus(final String wireValue) {
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
     * Resolves check run query status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCheckRunQueryStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known check run query status
     */
    @JsonCreator
    public static GitHubCheckRunQueryStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub check run query status: " + value));
    }
}
