package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of the bypass request to filter on. */
@CatalogSchema("operations/orgs/list-push-bypass-requests/parameters/5")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubBypassRequestStatus {
    /** Completed. */
    COMPLETED("completed"),
    /** Cancelled. */
    CANCELLED("cancelled"),
    /** Approved. */
    APPROVED("approved"),
    /** Expired. */
    EXPIRED("expired"),
    /** Deleted. */
    DELETED("deleted"),
    /** Denied. */
    DENIED("denied"),
    /** Open. */
    OPEN("open"),
    /** All. */
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
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
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
