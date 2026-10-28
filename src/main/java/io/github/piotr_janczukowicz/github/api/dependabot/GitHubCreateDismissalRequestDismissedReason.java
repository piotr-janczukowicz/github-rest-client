package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reason the alert is being dismissed. */
@CatalogSchema("operations/dependabot/create-dismissal-request-for-repo/requestBody/properties/dismissed_reason")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubCreateDismissalRequestDismissedReason {
    /** fix started. */
    FIX_STARTED("fix_started"),
    /** no bandwidth. */
    NO_BANDWIDTH("no_bandwidth"),
    /** tolerable risk. */
    TOLERABLE_RISK("tolerable_risk"),
    /** inaccurate. */
    INACCURATE("inaccurate"),
    /** not used. */
    NOT_USED("not_used");

    private static final EnumUtils.WireIndex<GitHubCreateDismissalRequestDismissedReason> WIRE_INDEX = EnumUtils.index(
            GitHubCreateDismissalRequestDismissedReason.class, GitHubCreateDismissalRequestDismissedReason::wireValue);

    private final String wireValue;

    GitHubCreateDismissalRequestDismissedReason(final String wireValue) {
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
     * Resolves the reason the alert is being dismissed from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateDismissalRequestDismissedReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create dismissal request dismissed reason: " + value));
    }
}
