package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of the team collaborator. */
@CatalogSchema("components/schemas/copilot-space-collaborator/anyOf/1/properties/type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceTeamCollaboratorType {
    TEAM("Team");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceTeamCollaboratorType> WIRE_INDEX = EnumUtils.index(
            GitHubCopilotSpaceTeamCollaboratorType.class, GitHubCopilotSpaceTeamCollaboratorType::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceTeamCollaboratorType(final String wireValue) {
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
     * Resolves Copilot Space team collaborator type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceTeamCollaboratorType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space team collaborator type
     */
    @JsonCreator
    public static GitHubCopilotSpaceTeamCollaboratorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub Copilot Space team collaborator type: " + value));
    }
}
