package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The response status to the bypass request until dismissed. */
@CatalogSchema("components/schemas/bypass-response/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubBypassResponseStatus {
    /** Approved. */
    APPROVED("approved"),
    /** Denied. */
    DENIED("denied"),
    /** Dismissed. */
    DISMISSED("dismissed"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubBypassResponseStatus> WIRE_INDEX =
            EnumUtils.index(GitHubBypassResponseStatus.class, GitHubBypassResponseStatus::wireValue);

    private final String wireValue;

    GitHubBypassResponseStatus(final String wireValue) {
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
     * Resolves the response status of a bypass request from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubBypassResponseStatus
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubBypassResponseStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
