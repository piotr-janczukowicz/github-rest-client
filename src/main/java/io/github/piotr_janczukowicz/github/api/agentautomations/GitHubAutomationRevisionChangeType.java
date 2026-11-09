package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Revision change type. Shared by the 2 schemas that have this shape: change_type, revision_change_type. */
@CatalogSchema({
    "operations/agent-automations/get-automation-revision/200/properties/change_type",
    "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/change_type"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationRevisionChangeType {
    CREATED("created"),
    EDITED("edited"),
    ENABLED("enabled"),
    DISABLED("disabled"),
    DELETED("deleted"),
    ROLLED_BACK("rolled_back"),
    UNKNOWN("unknown"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubAutomationRevisionChangeType> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationRevisionChangeType.class, GitHubAutomationRevisionChangeType::wireValue);

    private final String wireValue;

    GitHubAutomationRevisionChangeType(final String wireValue) {
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
    public static GitHubAutomationRevisionChangeType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
