package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of the dismissal requests to filter on. */
@CatalogSchema({
    "operations/code-scanning/list-org-dismissal-requests/parameters/4",
    "operations/code-scanning/list-dismissal-requests-for-repo/parameters/5",
    "operations/dependabot/list-dismissal-requests-for-repo/parameters/5"
})
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubAlertDismissalRequestStatus {
    /** open. */
    OPEN("open"),
    /** approved. */
    APPROVED("approved"),
    /** expired. */
    EXPIRED("expired"),
    /** denied. */
    DENIED("denied"),
    /** all. */
    ALL("all");

    private static final EnumUtils.WireIndex<GitHubAlertDismissalRequestStatus> WIRE_INDEX =
            EnumUtils.index(GitHubAlertDismissalRequestStatus.class, GitHubAlertDismissalRequestStatus::wireValue);

    private final String wireValue;

    GitHubAlertDismissalRequestStatus(final String wireValue) {
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
     * Resolves the status of the dismissal requests to filter on from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAlertDismissalRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub alert dismissal request status: " + value));
    }
}
