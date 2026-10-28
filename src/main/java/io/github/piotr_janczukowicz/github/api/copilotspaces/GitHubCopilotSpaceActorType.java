package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of actor (user or team). */
@CatalogSchema({
    "operations/copilot-spaces/add-collaborator-for-org/requestBody/properties/actor_type",
    "operations/copilot-spaces/add-collaborator-for-user/requestBody/properties/actor_type",
    "operations/copilot-spaces/update-collaborator-for-org/parameters/2",
    "operations/copilot-spaces/remove-collaborator-for-org/parameters/2",
    "operations/copilot-spaces/update-collaborator-for-user/parameters/2",
    "operations/copilot-spaces/remove-collaborator-for-user/parameters/2"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceActorType {
    USER("User"),
    TEAM("Team");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceActorType> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotSpaceActorType.class, GitHubCopilotSpaceActorType::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceActorType(final String wireValue) {
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
     * Resolves Copilot Space actor type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceActorType
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space actor type
     */
    @JsonCreator
    public static GitHubCopilotSpaceActorType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Copilot Space actor type: " + value));
    }
}
