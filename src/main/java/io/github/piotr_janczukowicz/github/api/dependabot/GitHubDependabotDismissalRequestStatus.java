package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of the dismissal request. */
@CatalogSchema("components/schemas/dependabot-alert-dismissal-request/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubDependabotDismissalRequestStatus {
    /** pending. */
    PENDING("pending"),
    /** denied. */
    DENIED("denied"),
    /** approved. */
    APPROVED("approved"),
    /** expired. */
    EXPIRED("expired");

    private static final EnumUtils.WireIndex<GitHubDependabotDismissalRequestStatus> WIRE_INDEX = EnumUtils.index(
            GitHubDependabotDismissalRequestStatus.class, GitHubDependabotDismissalRequestStatus::wireValue);

    private final String wireValue;

    GitHubDependabotDismissalRequestStatus(final String wireValue) {
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
     * Resolves the status of the dismissal request from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubDependabotDismissalRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub dependabot dismissal request status: " + value));
    }
}
