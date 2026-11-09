package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Workflow run conclusion input. Shared by the 1 schemas that have this shape: workflow_run_conclusion. */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/workflow_run/properties/conclusions/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationWorkflowRunConclusionInput {
    FAILURE("failure");

    private static final EnumUtils.WireIndex<GitHubAutomationWorkflowRunConclusionInput> WIRE_INDEX = EnumUtils.index(
            GitHubAutomationWorkflowRunConclusionInput.class, GitHubAutomationWorkflowRunConclusionInput::wireValue);

    private final String wireValue;

    GitHubAutomationWorkflowRunConclusionInput(final String wireValue) {
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
    public static GitHubAutomationWorkflowRunConclusionInput fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub automation workflow run conclusion input: " + value));
    }
}
