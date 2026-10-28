package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The review action to perform on the dismissal request. */
@CatalogSchema("operations/secret-scanning/review-dismissal-request/requestBody/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubReviewDismissalRequestStatus {
    /** Approve. */
    APPROVE("approve"),
    /** Deny. */
    DENY("deny");

    private static final EnumUtils.WireIndex<GitHubReviewDismissalRequestStatus> WIRE_INDEX =
            EnumUtils.index(GitHubReviewDismissalRequestStatus.class, GitHubReviewDismissalRequestStatus::wireValue);

    private final String wireValue;

    GitHubReviewDismissalRequestStatus(final String wireValue) {
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
     * Resolves the review dismissal request status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubReviewDismissalRequestStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known review dismissal request status
     */
    @JsonCreator
    public static GitHubReviewDismissalRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub review dismissal request status: " + value));
    }
}
