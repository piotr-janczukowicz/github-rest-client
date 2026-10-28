package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The base role that determines default permissions for a user-owned space. */
@CatalogSchema({
    "operations/copilot-spaces/create-for-user/requestBody/properties/base_role",
    "operations/copilot-spaces/update-for-user/requestBody/properties/base_role"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCopilotSpaceUserBaseRole {
    READER("reader"),
    NO_ACCESS("no_access");

    private static final EnumUtils.WireIndex<GitHubCopilotSpaceUserBaseRole> WIRE_INDEX =
            EnumUtils.index(GitHubCopilotSpaceUserBaseRole.class, GitHubCopilotSpaceUserBaseRole::wireValue);

    private final String wireValue;

    GitHubCopilotSpaceUserBaseRole(final String wireValue) {
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
     * Resolves Copilot Space user base role from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCopilotSpaceUserBaseRole
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known Copilot Space user base role
     */
    @JsonCreator
    public static GitHubCopilotSpaceUserBaseRole fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub Copilot Space user base role: " + value));
    }
}
