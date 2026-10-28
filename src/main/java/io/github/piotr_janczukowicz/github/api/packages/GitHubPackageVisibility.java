package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub package visibility in API responses. */
@CatalogSchema("components/schemas/package/properties/visibility")
public enum GitHubPackageVisibility {
    PRIVATE("private"),
    PUBLIC("public");

    private static final EnumUtils.WireIndex<GitHubPackageVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubPackageVisibility.class, GitHubPackageVisibility::wireValue);

    private final String wireValue;

    GitHubPackageVisibility(final String wireValue) {
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
     * Package visibility from a wire value.
     *
     * @param value JSON value
     * @return matching visibility
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known visibility
     */
    @JsonCreator
    public static GitHubPackageVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub package visibility: " + value));
    }
}
