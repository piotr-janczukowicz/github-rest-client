package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Failed reason. Shared by the 5 schemas that have this shape: automation_failed_reason,
 * automation_triggers_automation_failed_reason, item_triggers_automation_failed_reason,
 * revision_snapshot_triggers_automation_failed_reason, snapshot_triggers_automation_failed_reason.
 */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/automation_failed/properties/reasons/items",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/automation_failed/properties/reasons/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationFailedReason {
    REPORTED("reported"),
    CRASHED("crashed"),
    TIMED_OUT("timed_out"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubAutomationFailedReason> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationFailedReason.class, GitHubAutomationFailedReason::wireValue);

    private final String wireValue;

    GitHubAutomationFailedReason(final String wireValue) {
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
    public static GitHubAutomationFailedReason fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
