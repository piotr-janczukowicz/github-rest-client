package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Workflow run conclusion. Shared by the 5 schemas that have this shape: automation_triggers_workflow_run_conclusion,
 * item_triggers_workflow_run_conclusion, revision_snapshot_triggers_workflow_run_conclusion,
 * snapshot_triggers_workflow_run_conclusion, workflow_run_conclusion.
 */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/workflow_run/properties/conclusions/items",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/workflow_run/properties/conclusions/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationWorkflowRunConclusion {
    FAILURE("failure"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubAutomationWorkflowRunConclusion> WIRE_INDEX = EnumUtils.index(
            GitHubAutomationWorkflowRunConclusion.class, GitHubAutomationWorkflowRunConclusion::wireValue);

    private final String wireValue;

    GitHubAutomationWorkflowRunConclusion(final String wireValue) {
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
     * @return matching constant, or {@link #UNRECOGNIZED} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubAutomationWorkflowRunConclusion fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
