package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason that the alert was dismissed. */
@CatalogSchema({
    "components/schemas/dependabot-alert/properties/dismissed_reason",
    "components/schemas/dependabot-alert-with-repository/properties/dismissed_reason",
    "operations/dependabot/update-alert/requestBody/properties/dismissed_reason"
})
public enum GitHubDependabotAlertDismissedReason {
    FIX_STARTED("fix_started"),
    INACCURATE("inaccurate"),
    NO_BANDWIDTH("no_bandwidth"),
    NOT_USED("not_used"),
    TOLERABLE_RISK("tolerable_risk");

    private static final EnumUtils.WireIndex<GitHubDependabotAlertDismissedReason> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotAlertDismissedReason.class, GitHubDependabotAlertDismissedReason::wireValue);

    private final String wireValue;

    GitHubDependabotAlertDismissedReason(final String wireValue) {
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
     * Resolves Dependabot alert dismissed reason from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDependabotAlertDismissedReason
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Dependabot alert dismissed reason
     */
    @JsonCreator
    public static GitHubDependabotAlertDismissedReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub Dependabot alert dismissed reason: " + value));
    }
}
