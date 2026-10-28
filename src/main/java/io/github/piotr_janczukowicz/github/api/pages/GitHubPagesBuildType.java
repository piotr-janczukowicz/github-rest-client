package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** GitHub Pages build type. */
@CatalogSchema({
    "components/schemas/page/properties/build_type",
    "operations/repos/create-pages-site/requestBody/properties/build_type",
    "operations/repos/update-information-about-pages-site/requestBody/properties/build_type"
})
public enum GitHubPagesBuildType {
    LEGACY("legacy"),
    WORKFLOW("workflow");

    private static final EnumUtils.WireIndex<GitHubPagesBuildType> WIRE_INDEX =
            EnumUtils.index(GitHubPagesBuildType.class, GitHubPagesBuildType::wireValue);

    private final String wireValue;

    GitHubPagesBuildType(final String wireValue) {
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
     * Build type from a wire value.
     *
     * @param value JSON value
     * @return matching type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known type
     */
    @JsonCreator
    public static GitHubPagesBuildType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub Pages build type: " + value));
    }
}
