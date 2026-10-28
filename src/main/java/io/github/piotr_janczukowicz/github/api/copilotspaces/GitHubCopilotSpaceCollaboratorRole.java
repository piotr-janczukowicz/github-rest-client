package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The role granted to the collaborator. */
@CatalogSchema({
    "components/schemas/copilot-space-collaborator/anyOf/0/allOf/1/properties/role",
    "components/schemas/copilot-space-collaborator/anyOf/1/properties/role",
    "operations/copilot-spaces/add-collaborator-for-org/requestBody/properties/role",
    "operations/copilot-spaces/add-collaborator-for-user/requestBody/properties/role"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceCollaboratorRole {
    READER("reader"),
    WRITER("writer"),
    ADMIN("admin");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceCollaboratorRole> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotSpaceCollaboratorRole.class, GitHubCopilotSpaceCollaboratorRole::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceCollaboratorRole(final String wireValue) {
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
     * Resolves Copilot Space collaborator role from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceCollaboratorRole
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space collaborator role
     */
    @JsonCreator
    public static GitHubCopilotSpaceCollaboratorRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub Copilot Space collaborator role: " + value));
    }
}
