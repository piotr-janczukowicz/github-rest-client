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
        "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/interval/properties/cadence/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListRepoAutomationsAutomationTriggersIntervalCadence {
    HOURLY("hourly"),
    DAILY("daily"),
    WEEKLY("weekly"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubListRepoAutomationsAutomationTriggersIntervalCadence> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListRepoAutomationsAutomationTriggersIntervalCadence.class,
                    GitHubListRepoAutomationsAutomationTriggersIntervalCadence::wireValue);

    private final String wireValue;

    GitHubListRepoAutomationsAutomationTriggersIntervalCadence(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubListRepoAutomationsAutomationTriggersIntervalCadence fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
