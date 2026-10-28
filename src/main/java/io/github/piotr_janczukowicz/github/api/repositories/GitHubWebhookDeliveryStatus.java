package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Webhook delivery outcome filter. */
@CatalogSchema("components/parameters/webhook-delivery-status/schema")
public enum GitHubWebhookDeliveryStatus {
    SUCCESS("success"),
    FAILURE("failure");

    private static final EnumUtils.WireIndex<GitHubWebhookDeliveryStatus> WIRE_INDEX =
            EnumUtils.index(GitHubWebhookDeliveryStatus.class, GitHubWebhookDeliveryStatus::wireValue);

    private final String wireValue;

    GitHubWebhookDeliveryStatus(final String wireValue) {
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
     * Webhook delivery outcome filter from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubWebhookDeliveryStatus fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub webhook delivery status: " + value));
    }
}
