package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Reasoning effort. Shared by the 1 schemas that have this shape: reasoning_effort. */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/reasoning_effort",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/reasoning_effort"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationReasoningEffort {
    EMPTY(""),
    NONE("none"),
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    XHIGH("xhigh"),
    MAX("max");

    private static final EnumUtils.WireIndex<GitHubAutomationReasoningEffort> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationReasoningEffort.class, GitHubAutomationReasoningEffort::wireValue);

    private final String wireValue;

    GitHubAutomationReasoningEffort(final String wireValue) {
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
     * Reasoning effort from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAutomationReasoningEffort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHubAutomationReasoningEffort value: " + value));
    }
}
