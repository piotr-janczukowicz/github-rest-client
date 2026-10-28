package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub package type. */
@CatalogSchema({
    "components/schemas/package/properties/package_type",
    "components/parameters/package-type/schema",
    "components/schemas/package-version/properties/metadata/properties/package_type"
})
public enum GitHubPackageType {
    NPM("npm"),
    MAVEN("maven"),
    RUBYGEMS("rubygems"),
    DOCKER("docker"),
    NUGET("nuget"),
    CONTAINER("container");

    private static final EnumUtils.WireIndex<GitHubPackageType> WIRE_INDEX =
            EnumUtils.index(GitHubPackageType.class, GitHubPackageType::wireValue);

    private final String wireValue;

    GitHubPackageType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Package type from a wire value.
     *
     * @param value JSON or query parameter value
     * @return matching package type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known package type
     */
    @JsonCreator
    public static GitHubPackageType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub package type: " + value));
    }
}
