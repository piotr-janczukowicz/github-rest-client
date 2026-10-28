package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The collaborator actor type. */
@CatalogSchema("components/schemas/copilot-space-collaborator/anyOf/0/allOf/1/properties/actor_type")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceUserCollaboratorActorType {
    USER("User");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceUserCollaboratorActorType> WIRE_INDEX = EnumUtils.index(
            GitHubCopilotSpaceUserCollaboratorActorType.class, GitHubCopilotSpaceUserCollaboratorActorType::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceUserCollaboratorActorType(final String wireValue) {
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
     * Resolves Copilot Space user collaborator actor type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceUserCollaboratorActorType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space user collaborator actor type
     */
    @JsonCreator
    public static GitHubCopilotSpaceUserCollaboratorActorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub Copilot Space user collaborator actor type: " + value));
    }
}
