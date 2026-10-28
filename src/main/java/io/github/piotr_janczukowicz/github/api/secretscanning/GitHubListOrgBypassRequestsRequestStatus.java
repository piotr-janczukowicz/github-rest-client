package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of the bypass request to filter on. */
@CatalogSchema("operations/secret-scanning/list-org-bypass-requests/parameters/5")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubListOrgBypassRequestsRequestStatus {
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

    private static final EnumUtils.WireIndex<GitHubListOrgBypassRequestsRequestStatus> WIRE_INDEX = EnumUtils.index(
            GitHubListOrgBypassRequestsRequestStatus.class, GitHubListOrgBypassRequestsRequestStatus::wireValue);

    private final String wireValue;

    GitHubListOrgBypassRequestsRequestStatus(final String wireValue) {
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
     * Resolves the secret scanning bypass request status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubListOrgBypassRequestsRequestStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known secret scanning bypass request status
     */
    @JsonCreator
    public static GitHubListOrgBypassRequestsRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub secret scanning bypass request status: " + value));
    }
}
