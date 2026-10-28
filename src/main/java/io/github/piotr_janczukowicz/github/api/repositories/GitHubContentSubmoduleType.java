package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** submodule type. */
@CatalogSchema("components/schemas/content-submodule/properties/type")
public enum GitHubContentSubmoduleType {
    SUBMODULE("submodule");

    private static final EnumUtils.WireIndex<GitHubContentSubmoduleType> WIRE_INDEX =
            EnumUtils.index(GitHubContentSubmoduleType.class, GitHubContentSubmoduleType::wireValue);

    private final String wireValue;

    GitHubContentSubmoduleType(final String wireValue) {
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
     * submodule type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubContentSubmoduleType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub content submodule type: " + value));
    }
}
