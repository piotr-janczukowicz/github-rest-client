package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Failed reason input. Shared by the 1 schemas that have this shape: automation_failed_reason. */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/automation_failed/properties/reasons/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationFailedReasonInput {
    REPORTED("reported"),
    CRASHED("crashed"),
    TIMED_OUT("timed_out");

    private static final EnumUtils.WireIndex<GitHubAutomationFailedReasonInput> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationFailedReasonInput.class, GitHubAutomationFailedReasonInput::wireValue);

    private final String wireValue;

    GitHubAutomationFailedReasonInput(final String wireValue) {
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
    public static GitHubAutomationFailedReasonInput fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub automation failed reason input: " + value));
    }
}
