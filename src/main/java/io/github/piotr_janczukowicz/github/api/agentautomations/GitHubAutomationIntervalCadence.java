package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Interval cadence. Shared by the 5 schemas that have this shape: automation_triggers_interval_cadence,
 * interval_cadence, item_triggers_interval_cadence, revision_snapshot_triggers_interval_cadence,
 * snapshot_triggers_interval_cadence.
 */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/201/properties/triggers/properties/interval/properties/cadence/items",
    "operations/agent-automations/edit-repo-automation/200/properties/triggers/properties/interval/properties/cadence/items",
    "operations/agent-automations/get-automation-revision/200/properties/snapshot/properties/triggers/properties/interval/properties/cadence/items",
    "operations/agent-automations/get-repo-automation/200/properties/triggers/properties/interval/properties/cadence/items",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/snapshot/properties/triggers/properties/interval/properties/cadence/items",
    "operations/agent-automations/list-automations/200/items/properties/triggers/properties/interval/properties/cadence/items",
    "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/interval/properties/cadence/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationIntervalCadence {
    HOURLY("hourly"),
    DAILY("daily"),
    WEEKLY("weekly"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubAutomationIntervalCadence> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationIntervalCadence.class, GitHubAutomationIntervalCadence::wireValue);

    private final String wireValue;

    GitHubAutomationIntervalCadence(final String wireValue) {
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
    public static GitHubAutomationIntervalCadence fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
