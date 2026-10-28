package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of the bypass requests to filter on. */
@CatalogSchema("operations/repos/list-repo-push-bypass-requests/parameters/5")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubBypassRequestStatus {
    /** completed. */
    COMPLETED("completed"),
    /** cancelled. */
    CANCELLED("cancelled"),
    /** approved. */
    APPROVED("approved"),
    /** expired. */
    EXPIRED("expired"),
    /** deleted. */
    DELETED("deleted"),
    /** denied. */
    DENIED("denied"),
    /** open. */
    OPEN("open"),
    /** all. */
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubBypassRequestStatus> WIRE_INDEX =
            EnumUtils.index(GitHubBypassRequestStatus.class, GitHubBypassRequestStatus::wireValue);

    private final String wireValue;

    GitHubBypassRequestStatus(final String wireValue) {
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
     * Resolves the status of the bypass requests to filter on from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubBypassRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub bypass request status: " + value));
    }
}
