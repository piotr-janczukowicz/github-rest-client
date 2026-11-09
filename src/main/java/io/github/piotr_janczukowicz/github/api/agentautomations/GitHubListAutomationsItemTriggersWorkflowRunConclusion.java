package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** conclusions */
@CatalogSchema(
        "operations/agent-automations/list-automations/200/items/properties/triggers/properties/workflow_run/properties/conclusions/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAutomationsItemTriggersWorkflowRunConclusion {
    FAILURE("failure"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubListAutomationsItemTriggersWorkflowRunConclusion> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListAutomationsItemTriggersWorkflowRunConclusion.class,
                    GitHubListAutomationsItemTriggersWorkflowRunConclusion::wireValue);

    private final String wireValue;

    GitHubListAutomationsItemTriggersWorkflowRunConclusion(final String wireValue) {
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
    public static GitHubListAutomationsItemTriggersWorkflowRunConclusion fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
