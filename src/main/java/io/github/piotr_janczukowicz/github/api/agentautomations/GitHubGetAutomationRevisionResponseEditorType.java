package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The kind of actor that made the change. */
@CatalogSchema("operations/agent-automations/get-automation-revision/200/properties/editor_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetAutomationRevisionResponseEditorType {
    USER("user"),
    SYSTEM("system"),
    UNKNOWN("unknown"),
    /** A value this library version does not know. */
    UNRECOGNIZED("unrecognized");

    private static final EnumUtils.WireIndex<GitHubGetAutomationRevisionResponseEditorType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubGetAutomationRevisionResponseEditorType.class,
                    GitHubGetAutomationRevisionResponseEditorType::wireValue);

    private final String wireValue;

    GitHubGetAutomationRevisionResponseEditorType(final String wireValue) {
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
    public static GitHubGetAutomationRevisionResponseEditorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNRECOGNIZED);
    }
}
