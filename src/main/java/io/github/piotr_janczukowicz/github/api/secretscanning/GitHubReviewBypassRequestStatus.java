package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The review action to perform on the bypass request. */
@CatalogSchema("operations/secret-scanning/review-bypass-request/requestBody/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubReviewBypassRequestStatus {
    /** Approve. */
    APPROVE("approve"),
    /** Reject. */
    REJECT("reject");

    private static final EnumUtils.WireIndex<GitHubReviewBypassRequestStatus> WIRE_INDEX =
            EnumUtils.index(GitHubReviewBypassRequestStatus.class, GitHubReviewBypassRequestStatus::wireValue);

    private final String wireValue;

    GitHubReviewBypassRequestStatus(final String wireValue) {
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
     * Resolves the review bypass request status from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubReviewBypassRequestStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known review bypass request status
     */
    @JsonCreator
    public static GitHubReviewBypassRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");
        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub review bypass request status: " + value));
    }
}
