package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Workflow conclusions to match. The current trigger supports failure. */
@CatalogSchema(
        "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/workflow_run/properties/conclusions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion {
    FAILURE("failure");

    private static final EnumUtils.WireIndex<GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion>
            WIRE_INDEX = EnumUtils.index(
                    GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion.class,
                    GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion::wireValue);

    private final String wireValue;

    GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion(final String wireValue) {
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
    public static GitHubListRepoAutomationsAutomationTriggersWorkflowRunConclusion fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub list repo automations automation triggers workflow run conclusion: " + value));
    }
}
