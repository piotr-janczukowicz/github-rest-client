package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reasoning effort to use for sessions triggered by this automation. */
@CatalogSchema("operations/agent-automations/create-repo-automation/requestBody/properties/reasoning_effort")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCreateRepoAutomationReasoningEffort {
    EMPTY(""),
    NONE("none"),
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    XHIGH("xhigh"),
    MAX("max");

    private static final EnumUtils.WireIndex<GitHubCreateRepoAutomationReasoningEffort> WIRE_INDEX = EnumUtils.index(
            GitHubCreateRepoAutomationReasoningEffort.class, GitHubCreateRepoAutomationReasoningEffort::wireValue);

    private final String wireValue;

    GitHubCreateRepoAutomationReasoningEffort(final String wireValue) {
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
    public static GitHubCreateRepoAutomationReasoningEffort fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHubCreateRepoAutomationReasoningEffort value: " + value));
    }
}
