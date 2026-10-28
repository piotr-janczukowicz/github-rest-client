package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The base role that determines default permissions. */
@CatalogSchema({
    "components/schemas/copilot-space/properties/base_role",
    "operations/copilot-spaces/create-for-org/requestBody/properties/base_role",
    "operations/copilot-spaces/update-for-org/requestBody/properties/base_role"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceBaseRole {
    READER("reader"),
    WRITER("writer"),
    ADMIN("admin"),
    NO_ACCESS("no_access");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceBaseRole> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotSpaceBaseRole.class, GitHubCopilotSpaceBaseRole::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceBaseRole(final String wireValue) {
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
     * Resolves Copilot Space base role from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceBaseRole
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space base role
     */
    @JsonCreator
    public static GitHubCopilotSpaceBaseRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Copilot Space base role: " + value));
    }
}
