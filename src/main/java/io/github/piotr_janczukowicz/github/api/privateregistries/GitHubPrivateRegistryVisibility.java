package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Private registry visibility. */
@CatalogSchema({
    "components/schemas/org-private-registry-configuration/properties/visibility",
    "components/schemas/org-private-registry-configuration-with-selected-repositories/properties/visibility",
    "operations/private-registries/create-org-private-registry/requestBody/properties/visibility",
    "operations/private-registries/update-org-private-registry/requestBody/properties/visibility"
})
public enum GitHubPrivateRegistryVisibility {
    ALL("all"),
    PRIVATE("private"),
    SELECTED("selected");

    private static final EnumUtils.WireIndex<GitHubPrivateRegistryVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubPrivateRegistryVisibility.class, GitHubPrivateRegistryVisibility::wireValue);

    private final String wireValue;

    GitHubPrivateRegistryVisibility(final String wireValue) {
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
     * Visibility from a wire value.
     *
     * @param value JSON value
     * @return matching visibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known visibility
     */
    @JsonCreator
    public static GitHubPrivateRegistryVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown GitHub private registry visibility: " + value));
    }
}
