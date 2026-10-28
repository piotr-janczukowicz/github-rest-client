package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Whether a prebuild is currently available for a codespace machine and repository. */
@CatalogSchema("components/schemas/codespace-machine/properties/prebuild_availability")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubCodespaceMachinePrebuildAvailability {
    NONE("none"),
    READY("ready"),
    IN_PROGRESS("in_progress");

    private static final EnumUtils.WireIndex<GitHubCodespaceMachinePrebuildAvailability> WIRE_INDEX = EnumUtils.index(
            GitHubCodespaceMachinePrebuildAvailability.class, GitHubCodespaceMachinePrebuildAvailability::wireValue);

    private final String wireValue;

    GitHubCodespaceMachinePrebuildAvailability(final String wireValue) {
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
     * Resolves prebuild availability from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubCodespaceMachinePrebuildAvailability
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known prebuild availability
     */
    @JsonCreator
    public static GitHubCodespaceMachinePrebuildAvailability fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub prebuild availability: " + value));
    }
}
