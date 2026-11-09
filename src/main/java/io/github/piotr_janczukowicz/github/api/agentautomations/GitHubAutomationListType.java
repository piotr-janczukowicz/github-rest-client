package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** List type. Shared by the 1 schemas that have this shape: type. */
@CatalogSchema({
    "operations/agent-automations/list-automations/parameters/2",
    "operations/agent-automations/list-repo-automations/parameters/4"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationListType {
    PERSONAL("personal"),
    SHARED("shared");

    private static final EnumUtils.WireIndex<GitHubAutomationListType> WIRE_INDEX =
            EnumUtils.index(GitHubAutomationListType.class, GitHubAutomationListType::wireValue);

    private final String wireValue;

    GitHubAutomationListType(final String wireValue) {
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
    public static GitHubAutomationListType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub automation list type: " + value));
    }
}
