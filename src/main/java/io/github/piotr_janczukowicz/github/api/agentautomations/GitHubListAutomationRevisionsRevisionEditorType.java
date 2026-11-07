package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The kind of actor that made the change. */
@CatalogSchema(
        "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/editor_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAutomationRevisionsRevisionEditorType {
    USER("user"),
    SYSTEM("system"),
    UNKNOWN("unknown"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubListAutomationRevisionsRevisionEditorType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListAutomationRevisionsRevisionEditorType.class,
                    GitHubListAutomationRevisionsRevisionEditorType::wireValue);

    private final String wireValue;

    GitHubListAutomationRevisionsRevisionEditorType(final String wireValue) {
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
    public static GitHubListAutomationRevisionsRevisionEditorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
