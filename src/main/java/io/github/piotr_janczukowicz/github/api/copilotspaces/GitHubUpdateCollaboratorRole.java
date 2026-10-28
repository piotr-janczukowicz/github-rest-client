package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The new role to grant to the collaborator. Use {@code no_access} to remove the collaborator. */
@CatalogSchema({
    "operations/copilot-spaces/update-collaborator-for-org/requestBody/properties/role",
    "operations/copilot-spaces/update-collaborator-for-user/requestBody/properties/role"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubUpdateCollaboratorRole {
    READER("reader"),
    WRITER("writer"),
    ADMIN("admin"),
    NO_ACCESS("no_access");

    private static final EnumUtils.WireIndex<GitHubUpdateCollaboratorRole> WIRE_INDEX =
            EnumUtils.index(GitHubUpdateCollaboratorRole.class, GitHubUpdateCollaboratorRole::wireValue);

    private final String wireValue;

    GitHubUpdateCollaboratorRole(final String wireValue) {
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
     * Resolves update Copilot Space collaborator role from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubUpdateCollaboratorRole
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known update Copilot Space collaborator role
     */
    @JsonCreator
    public static GitHubUpdateCollaboratorRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub update Copilot Space collaborator role: " + value));
    }
}
