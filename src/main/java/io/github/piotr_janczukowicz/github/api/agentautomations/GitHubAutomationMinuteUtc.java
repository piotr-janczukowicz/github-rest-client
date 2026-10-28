package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** UTC minute anchor for daily and weekly schedules. */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/get-automation/200/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/interval/properties/minute_utc",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/interval/properties/minute_utc"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationMinuteUtc {
    ZERO(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY_FIVE(45);

    private final int wireValue;

    GitHubAutomationMinuteUtc(final int wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire value
     */
    @JsonValue
    public int wireValue() {
        return wireValue;
    }

    /**
     * Resolves minute anchor from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubAutomationMinuteUtc
     * @throws IllegalArgumentException if {@code value} is not a known minute anchor
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static GitHubAutomationMinuteUtc fromWire(final int value) {
        for (final GitHubAutomationMinuteUtc minute : values()) {
            if (minute.wireValue == value) {
                return minute;
            }
        }
        throw new IllegalArgumentException("Unknown GitHubAutomationMinuteUtc value: " + value);
    }
}
