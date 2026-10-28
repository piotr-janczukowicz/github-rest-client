package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The current status of the dismissal request. */
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request-simple/properties/status")
public enum GitHubDependabotAlertDismissalRequestStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected"),
    CANCELLED("cancelled");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertDismissalRequestStatus> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotAlertDismissalRequestStatus.class, GitHubDependabotAlertDismissalRequestStatus::wireValue);

    private final String wireValue;

    GitHubDependabotAlertDismissalRequestStatus(final String wireValue) {
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
     * Resolves Dependabot alert dismissal request status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertDismissalRequestStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert dismissal request status
     */
    @JsonCreator
    public static GitHubDependabotAlertDismissalRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub Dependabot alert dismissal request status: " + value));
    }
}
