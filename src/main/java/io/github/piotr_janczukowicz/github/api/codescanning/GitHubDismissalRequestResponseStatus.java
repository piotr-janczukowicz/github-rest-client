package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The response status to the dismissal request until dismissed. */
@CatalogSchema("components/schemas/dismissal-request-response/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubDismissalRequestResponseStatus {
    /** Approved. */
    APPROVED("approved"),
    /** Denied. */
    DENIED("denied"),
    /** Dismissed. */
    DISMISSED("dismissed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubDismissalRequestResponseStatus> WIRE_INDEX = EnumUtils.index(
            GitHubDismissalRequestResponseStatus.class, GitHubDismissalRequestResponseStatus::wireValue);

    private final String wireValue;

    GitHubDismissalRequestResponseStatus(final String wireValue) {
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
     * Resolves the response status of a dismissal request from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubDismissalRequestResponseStatus
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubDismissalRequestResponseStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
