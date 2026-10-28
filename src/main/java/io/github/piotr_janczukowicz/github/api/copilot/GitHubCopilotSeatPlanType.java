package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Copilot plan of a seat assignee's organization or parent enterprise. */
@CatalogSchema("components/schemas/copilot-seat-details/properties/plan_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSeatPlanType {
    BUSINESS("business"),
    ENTERPRISE("enterprise"),
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubCopilotSeatPlanType> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotSeatPlanType.class, GitHubCopilotSeatPlanType::wireValue);

    private final String wireValue;

    GitHubCopilotSeatPlanType(final String wireValue) {
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
     * Seat plan type from a wire value.
     *
     * @param value JSON value
     * @return matching plan type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known plan type
     */
    @JsonCreator
    public static GitHubCopilotSeatPlanType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown Copilot seat plan type: " + value));
    }
}
