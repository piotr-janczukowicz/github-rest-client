package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Who owns the automation. {@code user} keeps it private to its creator and its runs act as that user.
 * {@code repository} makes it belong to the repository, visible to anyone with read access to the repository, and its
 * runs act as the Copilot app rather than as a person.
 */
@CatalogSchema("operations/agent-automations/get-automation/200/properties/owner_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubGetAutomationResponseOwnerType {
    USER("user"),
    REPOSITORY("repository"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubGetAutomationResponseOwnerType> WIRE_INDEX = EnumUtils.index(
            GitHubGetAutomationResponseOwnerType.class, GitHubGetAutomationResponseOwnerType::wireValue);

    private final String wireValue;

    GitHubGetAutomationResponseOwnerType(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubGetAutomationResponseOwnerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
