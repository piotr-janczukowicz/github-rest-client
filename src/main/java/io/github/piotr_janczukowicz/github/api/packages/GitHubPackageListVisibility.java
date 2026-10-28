package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Visibility filter for listing GitHub packages. */
@CatalogSchema("components/parameters/package-visibility/schema")
public enum GitHubPackageListVisibility {
    PUBLIC("public"),
    PRIVATE("private"),
    INTERNAL("internal");

    private static final EnumUtils.WireIndex<GitHubPackageListVisibility> WIRE_INDEX =
            EnumUtils.index(GitHubPackageListVisibility.class, GitHubPackageListVisibility::wireValue);

    private final String wireValue;

    GitHubPackageListVisibility(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * List visibility filter from a wire value.
     *
     * @param value query parameter value
     * @return matching visibility filter
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known visibility filter
     */
    @JsonCreator
    public static GitHubPackageListVisibility fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub package list visibility: " + value));
    }
}
