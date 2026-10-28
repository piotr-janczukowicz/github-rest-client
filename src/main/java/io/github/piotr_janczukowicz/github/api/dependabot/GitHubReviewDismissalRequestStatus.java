package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether to approve or deny a dismissal request. */
@CatalogSchema("operations/dependabot/review-dismissal-request-for-repo/requestBody/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubReviewDismissalRequestStatus {
    /** approve. */
    APPROVE("approve"),
    /** deny. */
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
     * Resolves whether to approve or deny a dismissal request from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
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
