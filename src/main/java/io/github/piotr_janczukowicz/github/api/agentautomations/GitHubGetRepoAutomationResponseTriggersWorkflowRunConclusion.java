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
        "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/workflow_run/properties/conclusions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion {
    FAILURE("failure");

    private static final EnumUtils.WireIndex<GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion.class,
                    GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion::wireValue);

    private final String wireValue;

    GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion(final String wireValue) {
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
    public static GitHubGetRepoAutomationResponseTriggersWorkflowRunConclusion fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub get repo automation response triggers workflow run conclusion: " + value));
    }
}
