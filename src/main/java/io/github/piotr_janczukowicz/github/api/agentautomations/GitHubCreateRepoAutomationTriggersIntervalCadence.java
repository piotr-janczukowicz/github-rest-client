package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** One or more schedules on which the Automation runs. */
@CatalogSchema(
        "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/interval/properties/cadence/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationTriggersIntervalCadence {
    HOURLY("hourly"),
    DAILY("daily"),
    WEEKLY("weekly");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationTriggersIntervalCadence> WIRE_INDEX =
            EnumUtils.index(
                    GitHubCreateRepoAutomationTriggersIntervalCadence.class,
                    GitHubCreateRepoAutomationTriggersIntervalCadence::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationTriggersIntervalCadence(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubCreateRepoAutomationTriggersIntervalCadence fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub create repo automation triggers interval cadence: " + value));
    }
}
