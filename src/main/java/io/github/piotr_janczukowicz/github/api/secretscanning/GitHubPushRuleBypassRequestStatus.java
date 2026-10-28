package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The status of the bypass request. */
@CatalogSchema("components/schemas/push-rule-bypass-request/properties/status")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubPushRuleBypassRequestStatus {
    /** Pending. */
    PENDING("pending"),
    /** Denied. */
    DENIED("denied"),
    /** Approved. */
    APPROVED("approved"),
    /** Cancelled. */
    CANCELLED("cancelled"),
    /** Completed. */
    COMPLETED("completed"),
    /** Expired. */
    EXPIRED("expired"),
    /** Deleted. */
    DELETED("deleted"),
    /** Open. */
    OPEN("open");

    private static final EnumUtils.WireIndex<GitHubPushRuleBypassRequestStatus> WIRE_INDEX =
            EnumUtils.index(GitHubPushRuleBypassRequestStatus.class, GitHubPushRuleBypassRequestStatus::wireValue);

    private final String wireValue;

    GitHubPushRuleBypassRequestStatus(final String wireValue) {
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
     * Resolves the status of a push rule bypass request from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubPushRuleBypassRequestStatus
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known push rule bypass request status
     */
    @JsonCreator
    public static GitHubPushRuleBypassRequestStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub push rule bypass request status: " + value));
    }
}
